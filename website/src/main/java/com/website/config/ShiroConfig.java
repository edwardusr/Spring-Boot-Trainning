package com.website.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;

import com.website.config.CustomRealm;
import com.website.config.CustomRealm;

public class ShiroConfig {
	/*
	@Bean
    public Realm customRealm() {
        return new CustomRealm();
    }
*/
	@Bean
	CustomRealm userRealm() {
		CustomRealm userRealm = new CustomRealm();
        return userRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
     // Set realm.
        securityManager.setRealm(userRealm());
        return securityManager;
    }
    
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filter = new DefaultShiroFilterChainDefinition();

        filter.addPathDefinition("/home", "authc");
        filter.addPathDefinition("/**", "anon");

        return filter;
    }
    

}
