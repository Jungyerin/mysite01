package com.jx372.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.GalleryDao;
import com.jx372.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao gallerydao;

	public List<GalleryVo> getList() {
		List<GalleryVo> list = gallerydao.getList();
		return list;
	}

	public void update(GalleryVo galleryvo) {
		
		gallerydao.update(galleryvo);	
		
	}

	public void delete(Long no) {
		gallerydao.delete(no);
		
	}

}
