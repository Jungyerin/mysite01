package com.jx372.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.BoardService;
import com.jx372.mysite.vo.BoardVo;
import com.jx372.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("/list")
	public String list(HttpSession session, Model model, @ModelAttribute BoardVo boardvo,
			@RequestParam(value = "pageno", required = true, defaultValue = "1") int pageno) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}

		List<BoardVo> list = boardService.getList(pageno);
		int count = boardService.getCount();
		model.addAttribute("list", list);
		model.addAttribute("count", count);

		return "board/list";
	}

	@RequestMapping("/view")
	public String view(HttpSession session, Model model,
			@RequestParam(value = "bno") Long bno) {

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo boardvo = boardService.getBoard(bno);
		// boardService.hit(bno); 조회수 올리는 것 테스트 다 한 후 주석 처리 없애기
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("authUser", authUser);
		return "board/view";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Model model,HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		model.addAttribute("authUser", authUser);
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardvo) {
		
		boardService.write(boardvo);
		return "redirect:/board/list";

	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(HttpSession session, Model model,  
			@RequestParam(value = "bno") Long bno) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		BoardVo boardvo = boardService.getBoard(bno);
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("userno", authUser.getNo());
		return "board/reply";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo boardvo) {
		
		boardService.reply(boardvo);
		return "redirect:/board/list";

	}	
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Model model,  
			@RequestParam(value = "bno") Long bno) {
		
		BoardVo boardvo = boardService.getBoard(bno);
		model.addAttribute("boardvo", boardvo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardvo) {
		
		boardService.update(boardvo);
		return "redirect:/board/list";

	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam(value="bno") Long bno){
		
		boardService.delete(bno);		
		return "redirect:/board/list";
	}



}
