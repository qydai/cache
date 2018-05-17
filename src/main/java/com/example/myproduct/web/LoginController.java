package com.example.myproduct.web;

import com.example.myproduct.test.User;
import com.example.myproduct.test.UserMockDBData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author: qydai
 * Created on @date: 2018/4/25
 */
@Controller
@RequestMapping("login")
public class LoginController {
  @RequestMapping("index")
  public String index(){
    return "login/index";
  }
  @RequestMapping("login")
  public String login(String name,String password, HttpServletRequest request){
    String result = "login/index";
    if (password != null && name != null){
      User dbUser = UserMockDBData.getUserByNameAndPassword(name,password);
      if (dbUser != null){
        request.getSession().setAttribute("user",dbUser);
        result = "index";
      }
    }
    return result;
  }
}
