package com.king.web.controller;

import com.king.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping("/grab")
public class GrabRedPacketController {

    @Autowired
    private UserRedPacketService packetService;

    @RequestMapping("/start")
    public String startGrabRedPacket() {
        return "grabRedPacket";
    }

    @RequestMapping("/packet")
    public ModelAndView grabRedPacket(Long redPacketId,Long userId) {
        ModelAndView mv=new ModelAndView();
        int result=packetService.grabRedPacket(redPacketId,userId);
        mv.addObject("Success " + result);
        mv.addObject("Message ", result!=0 ? "抢红包成功" : "抢红包失败");
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

}
