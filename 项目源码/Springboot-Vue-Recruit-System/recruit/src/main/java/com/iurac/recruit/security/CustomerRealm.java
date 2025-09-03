package com.iurac.recruit.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iurac.recruit.entity.Role;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取身份信息
        User primaryPrincipal = (User) principals.getPrimaryPrincipal();

        //根据主身份信息获取角色 和 权限信息
        List<Role> roleList = userService.findRolesByUsername(primaryPrincipal.getUsername());

        //角色信息
        if(!CollectionUtils.isEmpty(roleList)){

            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

            roleList.forEach(role->{
                simpleAuthorizationInfo.addRole(role.getRole()); //添加角色信息

            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //根据身份信息//从传过来的token获取到的用户名
        String principal = (String) token.getPrincipal();

        //根据身份信息查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",principal);
        User user = userService.getOne(queryWrapper);
        System.out.println("User:"+user);

        //用户不为空
        if(!ObjectUtils.isEmpty(user)){
            if(!"0".equals(user.getLocked())){
                throw new AuthenticationException("账户已被锁定，请联系客服");
            }
            System.out.println(user.getPassword());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                    new MyByteSource(user.getSalt()), this.getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }
}
