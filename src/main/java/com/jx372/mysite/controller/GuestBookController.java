package com.jx372.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.GuestBookService;
import com.jx372.mysite.vo.GuestBookVo;
import com.jx372.mysite.vo.UserVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired

	private GuestBookService gbService;

	@RequestMapping("/list")
	public String list(HttpSession session,
			// @ModelAttribute List<GuestBookVo> list,
			Model model, @ModelAttribute UserVo authUser) {
		List<GuestBookVo> list = gbService.getList();
		authUser = (UserVo) session.getAttribute("authUser");
		model.addAttribute("list", list);

		return "guestbook/list";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVo gbVo) {

		gbService.add(gbVo);
		return "redirect:/guestbook/list";
	}

	@RequestMapping(value = "/delete/{no}/{name}", method = RequestMethod.GET)
	public String delete(Model model,
				@PathVariable ("no") Long no,
				@PathVariable ("name") String name) {
		
		model.addAttribute("no",no);
		model.addAttribute("name",name);
		
		return "guestbook/delete";
	}
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpSession session, @RequestParam(value = "no") Long no,
			@RequestParam(value = "pwd") String pwd, @RequestParam(value = "name") String name) {

		gbService.delete(no, pwd, name);

		return "redirect:/guestbook/list";

	}

}
