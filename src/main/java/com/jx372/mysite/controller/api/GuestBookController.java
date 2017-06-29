package com.jx372.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.dto.JSONResult;
import com.jx372.mysite.service.GuestBookService;
import com.jx372.mysite.vo.GuestBookVo;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(@RequestParam(value="sno", required=true, defaultValue="0") Long startNo){
		
		List<GuestBookVo> list = guestbookService.getList(startNo); 
		return JSONResult.success(list);
	}
	

}
