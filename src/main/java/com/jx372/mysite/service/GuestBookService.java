package com.jx372.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.GuestBookDao;
import com.jx372.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookDao gbDao;

	public List<GuestBookVo> getList(Long startNo) {
		return gbDao.getList(startNo);
	}
	
	public List<GuestBookVo> getList() {
		return gbDao.getList();
	}

	public void add(GuestBookVo gbVo) {
		gbDao.insert(gbVo);
	}

	public void delete(Long no, String pwd, String name) {
		gbDao.delete(no, pwd, name);
		
	}

}
