package com.league.bean;

/**
 * Created by pfy on 2015/11/8.
 */
public class Kind {
    private int id;
    private String name;

    public Kind(){
    }

    public Kind(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
