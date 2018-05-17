package com.example.myproduct.web;

import com.example.myproduct.test.SocketMessage;
import com.example.myproduct.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by @author: qydai
 * Created on @date: 2018/4/24
 */
@Controller
@EnableScheduling
public class WebSocketController {
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @GetMapping("/")
  public String index(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      return "/login/index";
    }
    return "index";
  }

  @MessageMapping("/send")
  @SendTo("/topic/send")
  public SocketMessage send(SocketMessage message) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    message.setDate(df.format(new Date()));
    return message;
  }

  @Scheduled(fixedRate = 1000)
  @SendTo("/topic/callback")
  public Object callback() throws Exception {
    // 发现消息
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
    return "callback";
  }
}
