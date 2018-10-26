package com.example.entity;

import java.io.Serializable;
import java.util.Date;
/** 用户实体类*/
public class User implements Serializable{
    private Long id;
    private int type;
    private String user_name;
    private String user_addr;
    private Double lat;
    private Double lng;
    private Date createTime;
    public User(){
        super();
    }
    public User(Long id ,int type ,String user_name,Date createTime){
        this.id = id;
        this.type = type;
        this.user_name = user_name;
        this.createTime = createTime;
    }
    public User(Long id  ,String user_name,Double lat,Double lng){
        this.id = id;
        this.lat = lat;
        this.user_name = user_name;
        this.lng = lng;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "id="+this.id+",name="+this.user_name+
                ",type="+this.type+",createTime="+this.createTime+
                ",lat="+this.lat+",lng="+this.lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
