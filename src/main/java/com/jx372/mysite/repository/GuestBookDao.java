package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource datasource;

	public boolean insert(GuestBookVo vo) {
		int count=sqlSession.insert("guestbook.insert", vo);
		return count==1;
	}

	public List<GuestBookVo> getList() {


		List<GuestBookVo> list = sqlSession.selectList("guestbook.getlist");
		
		return list;
	}

	public boolean delete(Long no, String pwd, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("pwd", pwd);
		map.put("name", name);
		int count = sqlSession.delete("guestbook.delete",map);
		return count==1;
	}

}
