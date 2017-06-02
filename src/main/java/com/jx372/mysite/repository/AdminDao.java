package com.jx372.mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.AdminVo;

@Repository
public class AdminDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource datasource;

	public AdminVo get() {
		AdminVo adminvo = sqlSession.selectOne("admin.get");
		return adminvo;
	}

	public void insert(AdminVo adminvo) {
		sqlSession.insert("admin.insert", adminvo);
		
	}

	public void update(AdminVo adminvo) {
		sqlSession.update("admin.update", adminvo);		
	}

}
