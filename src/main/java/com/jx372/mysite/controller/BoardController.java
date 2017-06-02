package com.jx372.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.jx372.security.Auth;
import com.jx372.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Log LOG = LogFactory.getLog( com.jx372.mysite.controller.UserController.class );

	@Autowired
	private BoardService boardService;
	
	public void log(String str){

		LOG.warn( "#"+str+" - warn log" );
		LOG.error( "#"+str+" - error log" );		
	}
	

	@RequestMapping("/list")
	public String list(Model model, @ModelAttribute BoardVo boardvo,
			@RequestParam(value = "pageno", required = true, defaultValue = "1") int pageno,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword) {

		Map<String, Object> map=boardService.getList(pageno, keyword);
		model.addAttribute("map", map);
		log("board-list");

		return "board/list";
	}

	@RequestMapping("/view")
	public String view(@AuthUser UserVo authUser, Model model,
			@RequestParam(value = "bno") Long bno,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword,
			@RequestParam(value = "pageno", required = true, defaultValue = "1") int pageno) {

		BoardVo boardvo = boardService.getBoard(bno);
		boardService.hit(bno); 
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("authUser", authUser);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageno", pageno);
		
		log("board-view");
		return "board/view";
	}

	//@Auth
/*	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Model model,HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		model.addAttribute("authUser", authUser);
		return "board/write";
	}*/
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		log("board-write");
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Model model, 
			@ModelAttribute BoardVo boardvo,
			@RequestParam(value = "keyword", required = true, defaultValue = "") String keyword,
			@RequestParam(value = "pageno", required = true, defaultValue = "1") int pageno) {
		
		Map<String, Object> map = boardService.getList( pageno, keyword );
		model.addAttribute( "map", map );
		boardService.write(boardvo);		
		log("board-write");
		return "redirect:/board/list";

	}

	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, Model model,  
			@RequestParam(value = "bno") Long bno) {
		log("board-reply");
		BoardVo boardvo = boardService.getBoard(bno);
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("userno", authUser.getNo());
		return "board/reply";
	}
	
	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo boardvo) {
		log("board-reply");
		boardService.reply(boardvo);
		return "redirect:/board/list";

	}	
	
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Model model,  
			@RequestParam(value = "bno") Long bno) {
		
		BoardVo boardvo = boardService.getBoard(bno);
		model.addAttribute("boardvo", boardvo);
		return "board/modify";
	}
	
	@Auth
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
