package cn.itcast.domain;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String money;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public User(Integer id, String name, String money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public User() {
    }
}
