package com.fwlog.james.mode;

public class PortEntity {
    private Integer portNum;
    private Integer accessNum;

    public Integer getPortNum() {
        return portNum;
    }

    public void setPortNum(Integer portNum) {
        this.portNum = portNum;
    }

    public Integer getAccessNum() {
        return accessNum;
    }

    public void setAccessNum(Integer accessNum) {
        this.accessNum = accessNum;
    }

    @Override
    public String toString() {
        return "PortEntity{" +
                "portNum=" + portNum +
                ", accessNum=" + accessNum +
                '}';
    }
}
