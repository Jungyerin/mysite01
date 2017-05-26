package com.jx372.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.jx372.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
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

	public boolean insert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt=null;

		try {
			conn = getConnection();
			
			String sql="insert into guest_book values(null,?,?,?,now())";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getMessage());	
			
			int count = pstmt.executeUpdate();
			
			return count==1;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//생성된 것의 역순위로 자원정리
				if(pstmt==null){
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	
	public List<GuestBookVo> getList(){
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<GuestBookVo> list=new ArrayList<GuestBookVo>();
		
		try{
			conn=getConnection();
			
			stmt=conn.createStatement();
			
			String sql="select no, name, message, date_format(date, '%Y-%m-%d')"
					+ " from guest_book"
					+ " order by no desc";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Long no=rs.getLong(1);
				String name=rs.getString(2);
				String message=rs.getString(3);
				String date=rs.getString(4);
				
				GuestBookVo vo=new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage(message);
				vo.setDate(date);
				
				list.add(vo);
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}
				
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public boolean delete(Long no, String pwd, String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from guest_book"
					+ " where no="+no
					+ " and pwd="+"'"+pwd+"'"
					+ " and name="+"'"+name+"'";

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


}
