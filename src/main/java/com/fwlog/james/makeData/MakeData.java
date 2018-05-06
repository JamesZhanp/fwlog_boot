package com.fwlog.james.makeData;

import com.fwlog.james.service.AdviceService;
import com.fwlog.james.service.EventsService;
import com.fwlog.james.service.SuspectedeventsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *此函数是用来造数据使用
 * 造数据的内容：1.各种类型的访问事件
 * 攻击事件的类型有：1.病毒攻击，2.异常流量，3.扫描攻击，4.DDos攻击
 */
public class MakeData {
    @Autowired
    private EventsService eventsService;

    @Autowired
    private SuspectedeventsService suspectedeventsService;

    @Autowired
    private AdviceService adviceService;
    public static void makeEvent(){

    }

    public static void main(String[] args) {

    }
}
