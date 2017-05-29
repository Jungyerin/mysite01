package com.jx372.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.BoardDao;
import com.jx372.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;

	public List<BoardVo> getList(int bno) {

		return boardDao.getList(bno);
	}

	public int getCount() {
		
		return boardDao.getCount();
	}

	public BoardVo getBoard(Long bno) {
		
		return boardDao.get(bno);
	}

	public void hit(Long bno) {
		boardDao.updatehit(bno);
		
	}

	public void write(BoardVo boardvo) {
		boardDao.insert(boardvo);		
	}

	public void delete(Long bno) {
		boardDao.delete(bno);
		
	}

	public void reply(BoardVo boardvo) {
		boardDao.insertC(boardvo);
		
	}

	public void update(BoardVo boardvo) {
		
		boardDao.update(boardvo);
	}

}
