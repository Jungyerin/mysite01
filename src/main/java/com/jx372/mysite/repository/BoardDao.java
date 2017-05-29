package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	private Connection getConnection() throws SQLException {

		Connection conn = null;

		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2.connection 하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("sql driver를 찾을 수 없습니다.");
		}
		return conn;

	}

	public List<BoardVo> getList(int pageno) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
		int begin = ((pageno - 1) * 10) + 1;
		int end = pageno * 10;

		try {
			conn = getConnection();

			stmt = conn.createStatement();

			String sql = "SELECT r.rownum, r.no, r.user_no, r.name, r.title, r.date, r.hit, r.g_no, r.o_no, r.depth"
					+ " FROM (SELECT @RNUM:=@RNUM+1 'ROWNUM' , a.*"
					+ " FROM (SELECT @RNUM:=0) R, (select b.no, b.user_no, u.name, b.title, b.date, b.hit, b.g_no, b.o_no, b.depth"
					+ " from board b, user u" + " where b.user_no=u.no" + " order by b.g_no desc, b.o_no asc) a) r"
					+ " WHERE ROWNUM BETWEEN " + begin + " AND " + end;

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int rownum = rs.getInt(1);
				Long no = rs.getLong(2);
				Long userNo = rs.getLong(3);
				String name = rs.getString(4);
				String title = rs.getString(5);
				String date = rs.getString(6);
				int hit = rs.getInt(7);
				int gNo = rs.getInt(8);
				int oNo = rs.getInt(9);
				int depth = rs.getInt(10);

				BoardVo vo = new BoardVo();

				vo.setRownum(rownum);
				vo.setNo(no);
				vo.setUserno(userNo);
				vo.setName(name);
				vo.setTitle(title);
				vo.setDate(date);
				vo.setHit(hit);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<BoardVo> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			stmt = conn.createStatement();

			String sql = "select b.no, b.user_no, u.name, b.title, b.date, b.hit, b.g_no, b.o_no, b.depth"
					+ " from board b, user u" + " where b.user_no=u.no" + " order by b.g_no desc, b.o_no asc";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				Long userNo = rs.getLong(2);
				String name = rs.getString(3);
				String title = rs.getString(4);
				String date = rs.getString(5);
				int hit = rs.getInt(6);
				int gNo = rs.getInt(7);
				int oNo = rs.getInt(8);
				int depth = rs.getInt(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUserno(userNo);
				vo.setName(name);
				vo.setTitle(title);
				vo.setDate(date);
				vo.setHit(hit);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public BoardVo get(Long no) {

		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// System.out.println("vo 가지러 오기" + "no : "+no);
		try {
			conn = getConnection();

			String sql = "select no, user_no, title, content, hit, g_no, o_no, depth" + " from board" + " where no="
					+ no;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long no2 = rs.getLong(1);
				Long userNo = rs.getLong(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				vo = new BoardVo();

				vo.setNo(no2);
				vo.setUserno(userNo);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	public boolean update(BoardVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set" + " title=?," + " content=?," + " date=now()" + " where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();

			return (count == 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean updatehit(Long no) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set" + " hit=hit+1" + " where no=" + no;

			pstmt = conn.prepareStatement(sql);

			int count = pstmt.executeUpdate();

			return (count == 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			// System.out.println("insert 작업");

			String sql = "insert into board" + " values ( null, ?, ?, ?, now(), 0,"
					+ " (select * from (select ifnull(max(g_no),0)+1 from board) a), 1, 0)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUserno());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());

			int count = pstmt.executeUpdate();

			return count == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean insertC(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			// System.out.println("insert 작업 "+vo.getUserno()+" "+vo.getNo()+"
			// "+vo.getGno()+" "+vo.getOno()+" "+vo.getDepth());

			String sql = "insert into board" + " values ( null, ?, ?, ?, now(), 0, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUserno());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getGno());
			pstmt.setInt(5, vo.getOno() + 1);
			pstmt.setInt(6, vo.getDepth() + 1);

			int count = pstmt.executeUpdate();

			BoardVo vo2 = new BoardDao().getC();
			System.out.println(vo2.getNo());
			updateC(vo2);

			return count == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return false;
	}

	private BoardVo getC() {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// System.out.println("vo 가지러 오기" + "no : "+no);
		try {
			conn = getConnection();

			String sql = "select b.no, b.user_no, b.title, b.content, b.g_no, b.o_no, b.depth"
					+ " from board b, (select max(no) 'no' from board) a" + " where b.no=a.no";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long no2 = rs.getLong(1);
				Long userNo = rs.getLong(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				int gNo = rs.getInt(5);
				int oNo = rs.getInt(6);
				int depth2 = rs.getInt(7);

				vo = new BoardVo();

				vo.setNo(no2);
				vo.setUserno(userNo);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setGno(gNo);
				vo.setOno(oNo);
				vo.setDepth(depth2);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	private boolean updateC(BoardVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// System.out.println("자식 업데이트 확인 게시물 번호 : "+vo.getNo()+"그룹번호 :
			// "+vo.getGno()+"깊이 : "+vo.getDepth());

			String sql = "update board" + " as b inner join" + " (select a.no 'no', a.o_no+1 'num'" + " from(select *"
					+ "		 from board" + " where no != " + vo.getNo() + " and g_no=" + vo.getGno() + " and depth="
					+ vo.getDepth() + ") a) as a on (b.no=a.no)" + " set b.o_no=a.num" + " where b.no !=" + vo.getNo()
					+ " and b.g_no=" + vo.getGno() + " and b.depth=" + vo.getDepth();

			pstmt = conn.prepareStatement(sql);
			int count = pstmt.executeUpdate();

			return (count >= 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean delete(Long bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board" + " where no=" + bno;

			pstmt = conn.prepareStatement(sql);

			int count = pstmt.executeUpdate();

			return (count == 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=0;
		try {
			conn = getConnection();

			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
		

			if (rs.next()) {
				count=rs.getInt(1);
			}
				
		} catch (SQLException e) {
			System.out.println("error" + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	

}
