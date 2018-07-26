package com.example.chapter2;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/7/26 18:05
 * @package_name com.example.chapter2
 * @user Administrator
 */
public class Prediction implements Serializable, Comparable<Prediction> {
    private String who;     // personi
    private String what;    // his/her prediction
    private int id;         // identifier used as lookup key

    public Prediction() {
    }

    public Prediction(String who, String what, int id) {
        this.who = who;
        this.what = what;
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Prediction other) {
        return this.id - other.id;
    }
}
