package com.fwlog.james.analysis.eventAnalysis;

/**
 * 事件处理类，作用是异常访问的情况转变成为攻击事件的过程
 * 异常事件的确定分两步
 * 1.根据阈值的问题判断出访问异常的源IP地址和被访问异常的目的IP地址
 * 2.将得到的数据返回到原始的日志数据当中，判断是否真的为异常访问事件
 */
public class EventAnalysis {
    //阈值根据时间范围的不同而不同,我们这边将其分为24类
    private final long[] VARIANCE_THRESOLD = new long[24];
    private final long[] AVERAGE_THRESHOLD = new long[24];




}
