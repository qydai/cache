package com.example.myproduct.test;

import com.example.myproduct.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by @author: qydai
 * Created on @date: 2018/5/11
 */
public class ListObject {
  public static void main() {
    List<A> as = new ArrayList<>();
    A a = new A();
    a.setMy("A");
    List<B> bs = new ArrayList<>();
    B b = new B();
    b.setMy("B");
    List<C> cs = new ArrayList<>();
    C c = new C();
    c.setMy("C");
    cs.add(c);
    b.setCs(cs);
    a.setBs(bs);
    as.add(a);
    C value = CollectionUtil.findFirst(
        CollectionUtil.findFirst(
            CollectionUtil.findFirst(as, ax -> ax.getMy().equals("A")).getBs(),
            bx -> bx.getMy().equals("B")).getCs(),
        cx -> cx.getMy().equals("C")
    );

  }
}

class A {
  public String getMy() {
    return my;
  }

  public void setMy(String my) {
    this.my = my;
  }

  public List<B> getBs() {
    return bs;
  }

  public void setBs(List<B> bs) {
    this.bs = bs;
  }

  private String my;
  private List<B> bs;
}

class B {
  private String my;
  private List<C> cs;

  public String getMy() {
    return my;
  }

  public void setMy(String my) {
    this.my = my;
  }

  public List<C> getCs() {
    return cs;
  }

  public void setCs(List<C> cs) {
    this.cs = cs;
  }
}

class C {
  private String my;

  public String getMy() {
    return my;
  }

  public void setMy(String my) {
    this.my = my;
  }
}
