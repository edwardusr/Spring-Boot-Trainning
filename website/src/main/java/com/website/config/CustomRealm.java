package com.website.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRealm extends AuthorizingRealm {
    
    private Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    private Map<String, String> credentials = new HashMap<String, String>();
    private Map<String, Set<String>> roles = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> permissions = new HashMap<String, Set<String>>();

    {
        credentials.put("Tom", "password");
        credentials.put("Jerry", "password");

        roles.put("Jerry", new HashSet<String>(Arrays.asList("ADMIN")));
        roles.put("Tom", new HashSet<String>(Arrays.asList("USER")));

        permissions.put("ADMIN", new HashSet<String>(Arrays.asList("READ", "WRITE")));
        permissions.put("USER", new HashSet<String>(Arrays.asList("READ")));
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	System.out.println("======doGetAuthenticationInfo=========================================");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        System.out.println("======doGetAuthenticationInfo========================================="+userToken.getUsername());
        if (userToken.getUsername() == null || userToken.getUsername()
            .isEmpty() || !credentials.containsKey(userToken.getUsername())) {
            throw new UnknownAccountException("User doesn't exist");
        }

        return new SimpleAuthenticationInfo(userToken.getUsername(), credentials.get(userToken.getUsername()), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	System.out.println("=======doGetAuthorizationInfo========================================");
        Set<String> roles = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();

        for (Object user : principals) {
            try {
                roles.addAll(getRoleNamesForUser(null, (String) user));
                permissions.addAll(getPermissions(null, null, roles));
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(roles);
        authInfo.setStringPermissions(permissions);
        return authInfo;
    }

    //@Override
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
    	System.out.println("====getRoleNamesForUser===========================================");
        if (!roles.containsKey(username)) {
            throw new SQLException("User doesn't exist");
        }
        return roles.get(username);
    }

   // @Override
    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roles) throws SQLException {
    	System.out.println("=====getPermissions==========================================");
    	Set<String> userPermissions = new HashSet<String>();

        for (String role : roles) {
            if (!permissions.containsKey(role)) {
                throw new SQLException("Role doesn't exist");
            }
            userPermissions.addAll(permissions.get(role));
        }
        return userPermissions;
    }

    
    
    
}