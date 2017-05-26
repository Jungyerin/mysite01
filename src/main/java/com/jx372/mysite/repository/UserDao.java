package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.jx372.mysite.exception.UserDaoException;
import com.jx372.mysite.vo.UserVo;

@Repository
public class UserDao {

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

	public UserVo get(Long no){
		
		UserVo vo = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
			
		try{
			conn= getConnection();
			
			String sql="select no,name,email,gender"
					+ " from user"
					+ " where no="+no;
				
			pstmt=conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Long no2=rs.getLong(1);
				String name=rs.getString(2);
				String email=rs.getString(3);
				String gender=rs.getString(4);
				vo = new UserVo();
				
				vo.setName(name);
				vo.setNo(no2);
				vo.setEmail(email);
				vo.setGender(gender);
				
			}
			
			System.out.println("가져온 아이 확인 : "+vo);
		}catch(SQLException e){
			
			e.printStackTrace();

			
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	} //수정 폼을 채울때 사용
	
	public UserVo get(String email,String pwd)  throws UserDaoException{ //로그인 처리 할때 사용
		UserVo vo = null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
			
		try{
			conn= getConnection();
			
			String sql="select no,name,email,gender"
					+ " from user"
					+ " where email=?"
					+ " and pwd = password(?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Long no=rs.getLong(1);
				String name=rs.getString(2);
				String email2=rs.getString(3);
				String gender=rs.getString(4);
				vo = new UserVo();
				
				vo.setName(name);
				vo.setNo(no);
				vo.setEmail(email2);
				vo.setGender(gender);
				
			}
			
			
		}catch(SQLException e){
			
			throw new UserDaoException("User Not Found"); /*checked exception*/
			
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}
	public boolean insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		//System.out.println("확인!!");
		try {
			conn = getConnection();
			
			String sql="insert "
					+ "into user "
					+ "values ( null, ?, ?, password(?), ?)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPwd());
			pstmt.setString(4, vo.getGender());
			
			int count=pstmt.executeUpdate();
			
			return count==1;
			
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
	
	public boolean update(UserVo vo, Long no) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("pw null"+vo.getPwd()+" "+vo.getName()+" "+vo.getGender());

		try {
			conn = getConnection();
			
			String sql="update user set"
					+ " name=?, gender=?"
					+ " where no="+no;

			pstmt = conn.prepareStatement(sql); // import할때 java.sql로 해워쟈 됨

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			
			int count = pstmt.executeUpdate(); // 업데이트한 갯수가 나옴

			return (count == 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			/* 자원정리 */
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
	
	public boolean updatepw(UserVo vo, Long no) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//System.out.println("pw not null"+vo.getPwd()+" "+vo.getName()+" "+vo.getGender());


		try {
			conn = getConnection();
			
			
			String sql="update user set"
					+ " name=?, gender=?, pwd=password(?)"
					+ " where no="+no;

			pstmt = conn.prepareStatement(sql); // import할때 java.sql로 해워쟈 됨

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setString(3, vo.getPwd());
			
			int count = pstmt.executeUpdate(); // 업데이트한 갯수가 나옴

			return (count == 1);

		} catch (SQLException e) {
			System.out.println("error" + e);
			return false;
		} finally {
			/* 자원정리 */
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

}
