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
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource datasource;

	public List<BoardVo> getList(int pageno) {

		int begin = ((pageno - 1) * 10) + 1;
		int end = pageno * 10;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);

		List<BoardVo> list = sqlSession.selectList("board.getlist", map);

		return list;
	}

	public BoardVo get(Long no) {

		BoardVo vo = sqlSession.selectOne("board.getByNo", no);
		return vo;
	}

	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public boolean delete(Long no) {
		int count = sqlSession.delete("board.delete", no);
		return count == 1;
	}

	public int getCount() {
		return sqlSession.selectOne("board.getCount");
	}

	public boolean updateHit(Long no) {

		int count = sqlSession.update("board.updateHit", no);
		return count == 1;
	}

	public boolean update(BoardVo vo) {

		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}

	public boolean insertC(BoardVo vo) {

		int count = sqlSession.insert("board.insertC", vo);

		BoardVo vo2 = sqlSession.selectOne("board.getC");
		// System.out.println(vo2.getNo());
		updateC(vo2);

		return count == 1;

	}

	private boolean updateC(BoardVo vo) {
		int count = sqlSession.update("board.updateC", vo);
		return count == 1;
	}

}
