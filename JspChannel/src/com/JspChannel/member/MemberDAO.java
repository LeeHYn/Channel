package com.JspChannel.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.JspChannel.dbconnect.DBConnecter;
import com.JspChannel.dbconnect.JDBConnect;
import com.JspChannel.member.MemberDTO;

import java.sql.ResultSet;

//DB를 사용해 멤버정보 관리 기능을구현한 클래스
public class MemberDAO extends JDBConnect{
	private static MemberDAO memberDAO = new MemberDAO();

	// 생성자
	private MemberDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static MemberDAO getInstance() {
		if (memberDAO == null) {
			memberDAO = new MemberDAO();
		}
		return memberDAO;
	}

	// 로그인
	public synchronized boolean memberLogin(String id, String pw) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT user_id, pw FROM user WHERE user_id = '" + 
			id + "' AND pw = '" + pw + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
			}

			if (cnt == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberLoginDAO_Login_ERROR");
			return false;
		} finally {
			disconnectStmt();
		}
	}
	
	
	
	
	//로그인한 회원의 정보를 한번에 가져오는 메서드
		public synchronized MemberDTO getMemberInfo(String id) throws SQLException {
			try {
				conn = dbConn.getConn();
				stmt = conn.createStatement();
				query = new StringBuffer();

				query.append("SELECT pw, name, email, type FROM user ");
				query.append("WHERE user_id = '" + id + "'");

				rs = stmt.executeQuery(query.toString());
				
				MemberDTO memberDTO = new MemberDTO();
				
				int cnt = 0;
				while (rs.next()) {
					memberDTO.setId(id);
					memberDTO.setPw(rs.getString("pw"));
					memberDTO.setName(rs.getString("name"));
					memberDTO.setEmail(rs.getString("email"));
					memberDTO.setType(rs.getInt("type"));
					cnt++;
				}

				if (cnt < 1) {
					return null;
				}
				
				return memberDTO;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("MemberDAO_getMemberInfo_ERROR");
				return null;
			} finally {
				disconnectStmt();
			}
		}
		
		public synchronized boolean idCheck(String id) throws SQLException {
			try {
				conn = dbConn.getConn();

				query = new StringBuffer();

				query.append("SELECT user_id FROM user ");
				query.append("WHERE user_id = ?");

				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, id);

				rs = pstmt.executeQuery();

				int cnt = 0;
				while (rs.next()) {
					cnt++;
				}

				if (cnt != 0) {
					return false;
				}

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("아이디 중복체크 에러 \n" + e.getMessage());
				return false;
			} finally {
				disconnectPstmt();
			}
		}
		public synchronized boolean joinMember(MemberDTO memberDTO) throws SQLException {
			try {
				conn = dbConn.getConn();
				query = new StringBuffer();
				query.append(
						"INSERT INTO user(user_id, pw, name, email, type) ");
				query.append("VALUES(?, ?, ?, ?, ?)");
				pstmt = conn.prepareStatement(query.toString());

				pstmt.setString(1, memberDTO.getId());
				pstmt.setString(2, memberDTO.getPw());
				pstmt.setString(3, memberDTO.getName());
				pstmt.setString(4, memberDTO.getEmail());
				pstmt.setInt(5, memberDTO.getType());

				if (pstmt.executeUpdate() != 1) {
					return false;
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("회원가입 에러\n" + e.getMessage());
				return false;
			} finally {
				disconnectPstmt();
			}
		}
	
	
	
}
