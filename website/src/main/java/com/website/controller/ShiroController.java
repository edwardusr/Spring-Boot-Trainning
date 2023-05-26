package com.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.website.model.UserCredentials;

import java.util.HashMap;

@Controller
public class ShiroController {
    
    private Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @GetMapping("/")
    public String getIndex() {
        return "comparison/index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "comparison/login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest req, UserCredentials credentials, RedirectAttributes attr) {

        System.out.println("bababa");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("1", "One");
        map.put("3", "Three");
        map.put("5", "Five");
        map.put("7", "Seven");
        map.put("9", "Nine");

        UsernamePasswordToken token=new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword());
    	
        Subject subject = SecurityUtils.getSubject();
        System.out.println("======doLogin=====================1====================");
        if (!subject.isAuthenticated()) {
        	System.out.println("======doLogin=================2========1================");
            //UsernamePasswordToken token = new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword());
            System.out.println("======doLogin=================2========================");
            try {
            	System.out.println("======doLogin=================2====2====================");
                subject.login(token);
                System.out.println("======doLogin=================2======3==================");
            } catch (AuthenticationException ae) {
            	System.out.println("======doLogin=================2====4====================");
                logger.error(ae.getMessage());
                System.out.println("======doLogin=================2====5====================");
                attr.addFlashAttribute("error", "Invalid Credentials");
                System.out.println("======doLogin=================2====6====================");
                return "redirect:/login";
            }
        }
        
        System.out.println("======doLogin====================3=====================");
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getMeHome(Model model) {

        addUserAttributes(model);

        return "comparison/home";
    }

    @GetMapping("/admin")
    public String adminOnly(Model model) {
        addUserAttributes(model);

        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.hasRole("ADMIN")) {
            model.addAttribute("adminContent", "only admin can view this");
        }
        return "comparison/home";
    }

    @PostMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    private void addUserAttributes(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        String permission = "";

        if (currentUser.hasRole("ADMIN")) {
            model.addAttribute("role", "ADMIN");
        } else if (currentUser.hasRole("USER")) {
            model.addAttribute("role", "USER");
        }

        if (currentUser.isPermitted("READ")) {
            permission = permission + " READ";
        }

        if (currentUser.isPermitted("WRITE")) {
            permission = permission + " WRITE";
        }
        model.addAttribute("username", currentUser.getPrincipal());
        model.addAttribute("permission", permission);
    }

}