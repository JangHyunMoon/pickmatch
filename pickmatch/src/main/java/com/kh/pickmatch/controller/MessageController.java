package com.kh.pickmatch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.pickmatch.model.service.MessageService;
import com.kh.pickmatch.model.vo.Message;

@Controller
public class MessageController {
	
	@Autowired
	private MessageService service;

	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping("/alarm/view")
	ModelAndView viewAlarm(String memberId) {
		logger.debug("MessageController :: memberId :::" + memberId);
		List<Message> list = service.selectMessageList(memberId);
		logger.debug("MessageController :: selectMessageList :::" + list);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("common/alarm");
		return mv;
	}
	
	
}
