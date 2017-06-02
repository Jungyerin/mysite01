package com.jx372.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.AdminDao;
import com.jx372.mysite.vo.AdminVo;

@Service
public class AdminService {

	@Autowired
	private AdminDao admindao;

	public AdminVo get() {

		AdminVo adminvo = admindao.get();
		return adminvo;

	}

	public void insert(AdminVo adminvo) {
		
		admindao.insert(adminvo);		
	}
	

	public void update(AdminVo adminvo) {
		
		admindao.update(adminvo);

	}


}
