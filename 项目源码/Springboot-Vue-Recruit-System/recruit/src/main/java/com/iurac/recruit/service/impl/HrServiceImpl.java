package com.iurac.recruit.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.exception.ServiceException;
import com.iurac.recruit.mapper.*;
import com.iurac.recruit.service.HrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Service("hrService")
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements HrService {

    @Autowired
    private HrMapper hrMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CompanyMapper companyMapper;

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void unbind(String hrId) throws ServiceException {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("create_hr_id",hrId);
        if(jobMapper.selectCount(jobQueryWrapper)!=0){
            throw new ServiceException("该员工还有发布的工作，请取消后再试");
        }

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role","manager");
        Hr hr = hrMapper.selectById(hrId);
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",hr.getUserId()).eq("role_id",roleMapper.selectOne(roleQueryWrapper).getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        if(ObjectUtil.isNotNull(userRole)){
            if(userRoleMapper.deleteById(userRole.getId())!=1){
                throw new ServiceException("删除该员工权限时发送错误");
            }
        }

        if(hrMapper.deleteById(hrId)!=1){
            throw new ServiceException("删除该员工信息时发送错误");
        }
    }

    @Override
    public void bind(String companyId,String username, String phone,String position) throws ServiceException {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);
        if(ObjectUtil.isNull(user)){
            throw new ServiceException("该用户不存在");
        }


        QueryWrapper<Hr> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.eq("user_id",user.getId());
        if(ObjectUtil.isNotNull(hrMapper.selectOne(hrQueryWrapper))){
            throw new ServiceException("该用户已被关联");
        }

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role","hr").select("id");
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",user.getId()).eq("role_id",roleMapper.selectOne(roleQueryWrapper).getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        if(ObjectUtil.isNull(userRole)){
            throw new ServiceException("该员工不存在");
        }
        if(!phone.equals(user.getPhone())){
            throw new ServiceException("验证失败");
        }

        Hr hr = new Hr();
        hr.setId(IdUtil.simpleUUID());
        hr.setPosition(position);
        hr.setUserId(user.getId());
        hr.setCompanyId(companyId);
        hr.setName("hr");
        hr.setSex("保密");
        hr.setAge("0");
        if(hrMapper.insert(hr)!=1){
            throw new ServiceException("添加该员工信息时发送错误");
        }

    }

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public void deleteHr(String userId) throws ManageException {
        QueryWrapper<Hr> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.eq("user_id",userId);
        Hr hr = hrMapper.selectOne(hrQueryWrapper);
        if(ObjectUtil.isNull(hr)){
            return;
        }

        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("create_hr_id",hr.getId());
        if(jobMapper.selectCount(jobQueryWrapper)!=jobMapper.delete(jobQueryWrapper)){
            throw new ManageException("删除该hr发布工作时发生错误");
        }

        if(hrMapper.deleteById(hr.getId())!=1){
            throw new ManageException("删除该hr数据时发生错误");
        }
    }

    @Override
    @Transactional(rollbackFor = ManageException.class)
    public void deleteManager(String userId) throws ManageException {
        QueryWrapper<Hr> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.eq("user_id",userId);
        Hr hr = hrMapper.selectOne(hrQueryWrapper);
        if(ObjectUtil.isNull(hr)){
            return;
        }

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role","manager");
        Role role = roleMapper.selectOne(roleQueryWrapper);

        QueryWrapper<Hr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id",hr.getCompanyId());
        List<Hr> hrList = hrMapper.selectList(queryWrapper);

        Boolean latestManager = false;
        for(Hr h : hrList){
            if(!hr.getUserId().equals(h.getUserId())){
                QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("role_id",role.getId()).eq("user_id",hr.getUserId());
                int count = userRoleMapper.selectCount(userRoleQueryWrapper);
                if(count==1){
                    latestManager = true;
                }
            }
        }

        if(latestManager){
            if(companyMapper.deleteById(hr.getCompanyId())!=1){
                throw new ManageException("删除公司数据时发生错误");
            };
            QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
            jobQueryWrapper.eq("company_id",hr.getCompanyId());
            jobMapper.delete(jobQueryWrapper);
            hrMapper.delete(queryWrapper);
        }

    }
}
