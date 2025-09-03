package com.iurac.recruit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iurac.recruit.entity.Role;
import com.iurac.recruit.mapper.RoleMapper;
import com.iurac.recruit.service.RoleService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}




