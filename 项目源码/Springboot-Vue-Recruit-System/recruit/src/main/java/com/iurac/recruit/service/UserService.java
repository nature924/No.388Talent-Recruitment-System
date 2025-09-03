package com.iurac.recruit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iurac.recruit.entity.Role;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.vo.PageResultVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */

public interface UserService extends IService<User> {

    void register(User user, String role) throws Exception;

    List<Role> findRolesByUsername(String username);

    PageResultVo<User> getByCondition(Long page, Long limit, String username, String role, String locked, String startDate, String endDate);

    void saveUser(User user, String[] role) throws ManageException;

    void updateUser(User user, String[] role) throws ManageException;

    void deleteUser(String id) throws ManageException;
}
