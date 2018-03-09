package com.fwlog.james.controller;

import com.fwlog.james.entity.Suspectedevents;
import com.fwlog.james.service.SuspectedeventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SuspectedeventsController {
    @Autowired
    private SuspectedeventsService suspectedeventsService;

    @RequestMapping(value = "/getSuspectedEvents",method = RequestMethod.GET)
    public List<Suspectedevents> getSuspectedEvents(){
        List<Suspectedevents> suspectedevents = suspectedeventsService.findAll();
        return suspectedevents;
    }
}
