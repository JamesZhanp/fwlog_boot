package com.fwlog.james.mode;


/**
 * 该类是用作向前段传输数据
 * created by jamesZhan on 2018/01/29
 */
public class IPEntity {
    private String ipAdress;
    private Integer accessNum;

    public IPEntity(){

    }
    public IPEntity( String ipAdress, Integer accessNum) {
        this.ipAdress = ipAdress;
        this.accessNum = accessNum;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public Integer getAccessNum() {
        return accessNum;
    }

    public void setAccessNum(Integer accessNum) {
        this.accessNum = accessNum;
    }

    @Override
    public String toString() {
        return "IPEntity{" +
                ", ipAdress='" + ipAdress + '\'' +
                ", accessNum=" + accessNum +
                '}';
    }
}
