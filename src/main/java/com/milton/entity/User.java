package com.milton.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * @author milton.zhang
 * @description
 * @date 2017-05-22 17:34
 */
@Table(name = "t_user")
public class User {

    @Id
    private int id;
    private String loginname;

    public User() {
    }

    public User(int id, String loginname) {
        this.id = id;
        this.loginname = loginname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
}
