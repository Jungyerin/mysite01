package com.jx372.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource datasource;

	public List<GalleryVo> getList() {
		List<GalleryVo> list = sqlSession.selectList("gallery.getlist");
		return list;
	}

	public boolean update(GalleryVo galleryvo) {
		
		int count = sqlSession.update("gallery.update", galleryvo);
		return count==1;
		
	}

	public boolean delete(Long no) {
		int count = sqlSession.update("gallery.delete", no);
		return count==1;		
	}

}
