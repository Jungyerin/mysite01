package com.jx372.mysite.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.BoardDao;
import com.jx372.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	private static final int LIST_SIZE=10;
	private static final int PAGE_SIZE=3;

	public Map<String, Object> getList(int pageno, String keyword) {

		
		int count = boardDao.getCount(keyword);
		int pageC = count/LIST_SIZE+1;
		int blockC = pageC/PAGE_SIZE+1;
		int currentBlock=pageno/PAGE_SIZE+1;
			
		if(pageno < 1){
			pageno = 1;
			currentBlock = 1;
		}
		else if(pageno > pageC)
		{
			pageno=pageC;
			currentBlock=pageno/PAGE_SIZE+1;
		}
		
		int begin = ((pageno - 1) * LIST_SIZE) + 1;
		int end = pageno * LIST_SIZE;
		
		int beginP = currentBlock == 0 ? 1 : (currentBlock - 1)*PAGE_SIZE + 1;
		int prevP = (pageno > 1) ? (pageno-1) * PAGE_SIZE : 0;
		int nextP = (pageno < blockC) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endP = (nextP > 0) ? (beginP - 1) + LIST_SIZE : pageC;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageno", pageno);
		map.put("keyword", keyword);
		map.put("begin", begin);
		map.put("end", end);
		List<BoardVo> list = boardDao.getList(map);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		
		map2.put("list", list);
		map2.put("count", count);
		map2.put("listsize", LIST_SIZE);
		map2.put("pageno", pageno);
		map2.put("beginP", beginP);
		map2.put("endP", endP);
		map2.put("prevP", prevP);
		map2.put("nextP", nextP);
		map2.put("keyword", keyword);			

		return map2;
	}

	public int getCount(String keyword) {
		return boardDao.getCount(keyword);
	}

	public BoardVo getBoard(Long bno) {
		
		return boardDao.get(bno);
	}

	public void hit(Long bno) {
		boardDao.updateHit(bno);
		
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
