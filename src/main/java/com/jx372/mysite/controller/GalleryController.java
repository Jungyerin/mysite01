package com.jx372.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.service.AdminService;
import com.jx372.mysite.service.GalleryService;
import com.jx372.mysite.vo.GalleryVo;
import com.jx372.mysite.vo.UserVo;
import com.jx372.security.Auth;
import com.jx372.security.AuthUser;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryservice;
	
	@Autowired
	private AdminService adminservice;
	
	@Auth
	@RequestMapping("")
	public String index(@AuthUser UserVo authUser,
			Model model){
		
		List<GalleryVo> list = galleryservice.getList();
		model.addAttribute("authUser", authUser);
		model.addAttribute("list", list);
		
		System.out.println(list);
		
		System.out.println(authUser);
		
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(@ModelAttribute GalleryVo galleryvo, 
			@RequestParam("file") MultipartFile file){
		
		String url = adminservice.restore(file);
		
		galleryvo.setImageUrl(url);	
		galleryservice.update(galleryvo);
		
		return "redirect:/gallery";
		
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete (@PathVariable ("no") Long no){
		
		galleryservice.delete(no);		
		return "redirect:/gallery";
	}
	
	

}
