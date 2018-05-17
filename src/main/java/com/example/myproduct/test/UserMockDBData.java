package com.example.myproduct.test;

import com.example.myproduct.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by @author: qydai
 * Created on @date: 2018/4/25
 */
public class UserMockDBData {
  private static List<User> users = new ArrayList<>();
  static {
    users.add(new User(1,"dqy","dqy"));
    users.add(new User(1,"wy","wy"));
    users.add(new User(1,"zfl","zfl"));
    users.add(new User(1,"hjj","hjj"));
    users.add(new User(1,"yhj","yhj"));
  }
  public static User getUserByNameAndPassword(String name,String password){
    return CollectionUtil.findFirst(users,x-> Objects.equals(x.getName(),name) && Objects.equals(x.getPassword(),password));
  }
}
