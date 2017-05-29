package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.exception.UserDaoException;
import com.jx372.mysite.vo.GuestBookVo;
import com.jx372.mysite.vo.UserVo;

@Repository
public class UserDao {
	
    @Autowired
    private SqlSession sqlSession;
	
	@Autowired
	private DataSource datasource;

	public UserVo get(Long no){
		
		/*map을  result type으로 사용하는 예제
		Map map=sqlSession.selectOne("user.getByNo", no);
		map.get("");*/
		
		UserVo vo = sqlSession.selectOne("user.getByNo",no);
		return vo;
	}
	
	public UserVo get(String email,String pwd)  throws UserDaoException{ //로그인 처리 할때 사용
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("pwd", pwd);
		
		UserVo vo = sqlSession.selectOne("user.getByLogin", map);
		
		return vo;
	}
	
	
	public boolean insert(UserVo vo) {
		int count=sqlSession.insert("user.insert", vo);
		return count==1;
	}
	
	public boolean update(UserVo vo) {
		
		int count = sqlSession.update("user.update", vo);
		return (count == 1);

	}

}
