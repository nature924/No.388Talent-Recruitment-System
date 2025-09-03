package com.iurac.recruit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.mapper.*;
import com.iurac.recruit.service.CompanyService;
import com.iurac.recruit.service.HrService;
import com.iurac.recruit.service.ResumeService;
import com.iurac.recruit.service.UserService;
import com.iurac.recruit.util.SaltUtil;
import com.iurac.recruit.vo.PageResultVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private HrService hrService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public void register(User user, String role) throws ManageException{
        if(selectUserByUsername(user.getUsername())!=null){
            throw new ManageException("该用户已存在");
        }

        //添加用户
        user.setId(IdUtil.simpleUUID());
        user.setCreateTime(DateUtil.now());
        user.setLocked("0");
        String salt = SaltUtil.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        user.setSalt(salt);
        user.setPassword(md5Hash.toHex());
        int insert1 = userMapper.insert(user);

        //授权
        UserRole userRole = new UserRole();
        userRole.setId(IdUtil.simpleUUID());
        userRole.setUserId(user.getId());
        userRole.setRoleId(role);
        int insert2 = userRoleMapper.insert(userRole);

        if(insert1!=1 || insert2!=1){
            throw new ManageException("添加数据失败");
        }

    }

    @Override
    public List<Role> findRolesByUsername(String username) {
        return userMapper.findRolesByUsername(username);
    }

    @Override
    public PageResultVo<User> getByCondition(Long page, Long limit, String username, String role, String locked, String startDate, String endDate) {
        PageResultVo<User> pageResultVo = new PageResultVo<>();
        List<User> userList = userMapper.getByCondition((page - 1) * limit, limit, username, role, locked, startDate, endDate);
        userList.forEach(user -> {
            user.setRoleList(findRolesByUsername(user.getUsername()));
        });
        pageResultVo.setRecords(userList);
        pageResultVo.setTotal(userMapper.getCountByCondition(username,role,locked,startDate,endDate));
        return pageResultVo;
    }

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public void saveUser(User user, String[] role) throws ManageException {
        if(selectUserByUsername(user.getUsername())!=null){
            throw new ManageException("该用户已存在");
        }

        if (userMapper.insert(user)!=1) {
            throw new ManageException("添加用户数据时发生错误");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        for (int i = 0; i < role.length; i++) {
            userRole.setId(IdUtil.simpleUUID());
            userRole.setRoleId(role[i]);
            if(userRoleMapper.insert(userRole)!=1){
                throw new ManageException("用户授权时发生错误");
            }
        }
    }

    @Override
    public void updateUser(User user, String[] role) throws ManageException {
        if (userMapper.updateById(user)!=1) {
            throw new ManageException("修改用户数据时发生错误");
        }

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        List<UserRole> list = userRoleMapper.selectList(queryWrapper);
        if(userRoleMapper.delete(queryWrapper) <= 0) {
            throw new ManageException("用户授权时发生错误");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        for (int i = 0; i < role.length; i++) {
            userRole.setId(IdUtil.simpleUUID());
            userRole.setRoleId(role[i]);
            if(userRoleMapper.insert(userRole)!=1){
                throw new ManageException("用户授权时发生错误");
            }
            list.remove(userRole);
        }

        String message = deleteRoleInfo(list);
        if(!"".equals(message)){
            throw new ManageException(message);
        }

    }

    @Override
    public void deleteUser(String id) throws ManageException {
        if (userMapper.deleteById(id)!=1) {
            throw new ManageException("修改用户数据时发生错误");
        }

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UserRole> list = userRoleMapper.selectList(queryWrapper);
        if(userRoleMapper.delete(queryWrapper) <= 0) {
            throw new ManageException("用户授权时发生错误");
        }

        String message = deleteRoleInfo(list);
        if(!"".equals(message)){
            throw new ManageException(message);
        }

    }

    @Transactional
    public String deleteRoleInfo(List<UserRole> list){
        for (UserRole userRole : list) {
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.eq("id", userRole.getRoleId()).orderByAsc("order_no");
            Role role = roleMapper.selectOne(roleQueryWrapper);

            switch (role.getRole()){
                case "user":
                    QueryWrapper<Resume> resumeQueryWrapper = new QueryWrapper<>();
                    resumeQueryWrapper.eq("user_id",userRole.getUserId());
                    resumeService.remove(resumeQueryWrapper);
                    break;
                case "manager":
                    try {
                        hrService.deleteManager(userRole.getUserId());
                    } catch (ManageException e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                    break;
                case "hr":
                    try {
                        hrService.deleteHr(userRole.getUserId());
                    } catch (ManageException e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                    break;
                default:break;
            }
        }
        return "";
    }

    private User selectUserByUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
            return userMapper.selectOne(queryWrapper);
    }

}
