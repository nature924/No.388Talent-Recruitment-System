package com.iurac.recruit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iurac.recruit.entity.Role;
import com.iurac.recruit.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<Role> findRolesByUsername(String username);

    List<User> getByCondition(@Param("skipCount") long skipCount, @Param("limit") Long limit,
                              @Param("username") String username, @Param("role") String role,
                              @Param("locked") String locked,
                              @Param("startDate") String startDate, @Param("endDate") String endDate);
    Long getCountByCondition(@Param("username") String username, @Param("role") String role,
                              @Param("locked") String locked,
                              @Param("startDate") String startDate, @Param("endDate") String endDate);
}
