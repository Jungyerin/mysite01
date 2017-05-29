package com.jx372.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.UserDao;
import com.jx372.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/*list를 paging하는 서비스를 구현가능하다.*/
	
	public void join(UserVo userVo) {
		
		//1. DB에 사용정보 저장
		userDao.insert(userVo);
		
		//2. 인증메일 보내기		
		
	}

	public UserVo getUser(String email, String pwd) {
				
		return userDao.get(email, pwd);
	}

	public void editUser(UserVo userVo) {		

			userDao.update(userVo);

	}

	public UserVo getUser(Long no) {
		return userDao.get(no);
	}
	

}
