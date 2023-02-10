package com.JspChannel.channel;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.dbconnect.JDBConnect;
import com.JspChannel.member.MemberDAO;
import com.JspChannel.member.MemberDTO;
import com.mysql.cj.Session;

public class PostDAO extends JDBConnect{
	public static PostDAO postDAO = new PostDAO();
	public PostDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static PostDAO getInstance() {
		if (postDAO == null) {
			postDAO = new PostDAO();
		}
		return postDAO;
	}


    //게시물 수를 카운트 해준다.
    public int selectCount(String keyword) {
        int totalCount = 0;
         query = new StringBuffer();
  		 query.append("SELECT COUNT(*) FROM posttb WHERE ( post_title like '%");
  		 query.append(keyword + "%' or post_contents LIKE '%  " +keyword + "%')");
  		 query.append("order by post_num");
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query.toString());
            rs.next();
            totalCount = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }
    // 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
    public ArrayList<PostDTO> selectListPage(int idx , String keyword , int page , int pageCount) {
        ArrayList<PostDTO> boardList = new ArrayList<PostDTO>();;
        
        query = new StringBuffer();
        
		 query.append("select * from (SELECT distinct ");
		 query.append("post_num , post_title , user_id , post_contents , first_day , vcount , ");
		 query.append(" row_number() over(order by post_num) as num from posttb where ch_num = ? And ");
		 query.append("(post_title like '%" + keyword + "%' or post_contents like '%" + keyword + " " + "%')");
		 query.append("ORDER BY post_num) as temp WHERE num between ? and ?");
		 
		 
        try {
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setInt(1, idx);
            pstmt.setInt(2, (page - 1) * pageCount + 1);
			pstmt.setInt(3, (page) * pageCount);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PostDTO dto = new PostDTO();

                dto.setPost_num(rs.getInt("post_num"));
                dto.setUser_id(rs.getString("user_id"));
                dto.setPost_title(rs.getString("post_title"));
                dto.setPost_contents(rs.getString("post_contents"));
                dto.setVcount(rs.getInt("vcount"));
                dto.setFirst_day(rs.getTimestamp("first_day"));

                boardList.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return boardList;
    }
    //채널의 관리자와 이름 구독자 수를 가져온다
    public List<MVCBoardDTO> selectListChannelDate(int idx) {			
	   	 List<MVCBoardDTO> chboard2 = new Vector<MVCBoardDTO>();
	   	 query = new StringBuffer();
			 query.append("SELECT channel.ch_num,channel.ch_name,channel.user_id, COUNT(*) as cs FROM ");
			 query.append("sub_ch INNER JOIN channel ON sub_ch.ch_num =channel.ch_num where channel.ch_num=?");
	       
			 try {
	    	   conn=dbConn.getConn();
	           pstmt = conn.prepareStatement(query.toString());
	           pstmt.setInt(1, idx);
	           rs = pstmt.executeQuery();

	           while (rs.next()) {
	               MVCBoardDTO dto = new MVCBoardDTO();
	               dto.setCh_num(rs.getInt("ch_num"));
	               dto.setCh_name(rs.getString("ch_name"));;
	               dto.setUser_id(rs.getString("user_id"));
	               dto.setCs(rs.getInt("cs"));
	               chboard2.add(dto);
	           }
	       }
	       catch (Exception e) {
	           System.out.println("게시물 조회 중 예외 발생");
	           e.printStackTrace();
	       }try {            
	           if (pstmt != null) pstmt.close();
	           System.out.println("JDBC �ڿ� ����");
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	       }
	       return chboard2;
	   }
    
    //현재 로그인한 멤버의 아이디랑 현재 들어간 채널의 아이디기 둘다 존재할 경우에 1을 반환하여 
    //channel.jsp에서 리턴값이 1일떄 구독 취소를 1이 아닐때 구독을 띄운다
    public synchronized boolean selectListChannelUser(int idx,String userId) throws SQLException{
	   	
	   	 query = new StringBuffer();
			 query.append("select * from sub_ch where user_id = ? AND ch_num = ? ");
	       try {
	       	   conn=dbConn.getConn();
	           pstmt = conn.prepareStatement(query.toString());
	           pstmt.setString(1, userId);
	           pstmt.setInt(2, idx);
	           rs = pstmt.executeQuery();
	           int count=0;
	           while (rs.next()) {
	        	   count++;
	           }
		       System.out.println(count);
	           if (count == 1) {
					return true;
				}
				
		       return false;
				
	       }
	       catch (Exception e) {
	    	   System.out.println("게시물 조회 중 예외 발생");
	           e.printStackTrace();
	           return false;
	       }
	       finally {
	    	   disconnectPstmt();
	       }
	   }
    //구독 기능
    public synchronized boolean channelSubscribe(int idx,String userId) throws SQLException{
	   	
	   	 query = new StringBuffer();
			 query.append("INSERT INTO sub_ch (user_id,ch_num) VALUES (?,?)");
	       try {
	       	   conn=dbConn.getConn();
	           pstmt = conn.prepareStatement(query.toString());
	           pstmt.setString(1, userId);
	           pstmt.setInt(2, idx);
	           
	           rs = pstmt.executeQuery();
	       }
	       catch (Exception e) {
	    	   System.out.println("게시물 조회 중 예외 발생");
	           e.printStackTrace();
	           return false;
	       }
	       finally {
	    	   disconnectPstmt();
	       }
		return true;
	   }
    //구독 취소 기능
    public synchronized boolean channelSubscribeCancle(int idx,String userId) throws SQLException{
	   	
	   	 query = new StringBuffer();
			 query.append("DELETE FROM sub_ch WHERE user_id = ? AND ch_num = ?");
	       try {
	       	   conn=dbConn.getConn();
	           pstmt = conn.prepareStatement(query.toString());
	           pstmt.setString(1, userId);
	           pstmt.setInt(2, idx);
	           
	           rs = pstmt.executeQuery();
	       }
	       catch (Exception e) {
	    	   System.out.println("게시물 조회 중 예외 발생");
	           e.printStackTrace();
	           return false;
	       }
	       finally {
	    	   disconnectPstmt();
	       }
		return true;
	   }
    //게시글 추가
    public synchronized boolean addPost(PostDTO postDTO , String user_id , int idx) throws SQLException {
		try {
			conn = dbConn.getConn();
			StringBuffer query = new StringBuffer();

			query.append("INSERT INTO posttb(user_id, post_title, first_day, post_contents, post_file, ch_num)");
			query.append("VALUES(?,?,?,?,?,?) ");

			pstmt = conn.prepareStatement(query.toString());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			
			pstmt.setString(1, user_id);
			pstmt.setString(2, postDTO.getPost_title());
			pstmt.setTimestamp(3, ts);
			pstmt.setString(4, postDTO.getPost_contents());
			pstmt.setString(5, postDTO.getPost_file());
			pstmt.setInt(6, idx);
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가게추가오류 " + e.getMessage());
			return false;
		} finally {
			disconnectPstmt();
		}
	}
    //게시글 보기
    public synchronized ArrayList<PostDTO> PostView(int post_num) throws SQLException {
    	
    	ArrayList<PostDTO> postboard = new ArrayList<PostDTO>();
    	try {
			conn = dbConn.getConn();
			query = new StringBuffer();
		
			query.append("SELECT user_id, post_title, post_contents, post_file, vcount FROM posttb ");
			query.append("WHERE post_num = ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, post_num);
			rs = pstmt.executeQuery();

			

			int cnt = 0;
			while (rs.next()) {
				PostDTO postDTO = new PostDTO();
				postDTO.setPost_num(post_num);
				postDTO.setUser_id(rs.getString("user_id"));
				postDTO.setPost_title(rs.getString("post_title"));
				postDTO.setPost_contents(rs.getString("post_contents"));
				postDTO.setPost_file(rs.getString("post_file"));
				postDTO.setVcount(rs.getInt("vcount"));
				postboard.add(postDTO);
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}

			return postboard;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DAO_게시글정보한번에가져오기ERROR" + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}
    //조회수 증가
    public void updateVisitCount(int post_num) {
    	conn = dbConn.getConn();
    	query = new StringBuffer();
    	query.append("UPDATE posttb SET vcount=vcount+1 WHERE post_num=?");
        try {
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setInt(1, post_num);
            pstmt.executeQuery();
        }
        catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }

    
		/*
		 * public List<PostDTO> selectListChannel() { List<PostDTO> chboard = new
		 * Vector<PostDTO>(); query = new StringBuffer(); query.
		 * append("SELECT post_num,user_id,post_title,vcount,first_day FROM posttb ");
		 * try { conn=dbConn.getConn(); pstmt = conn.prepareStatement(query.toString());
		 * rs = pstmt.executeQuery();
		 * 
		 * while (rs.next()) { PostDTO dto = new PostDTO();
		 * 
		 * dto.setPost_num(rs.getInt("post_num"));;
		 * dto.setUser_id(rs.getString("user_id"));
		 * dto.setPost_title(rs.getString("post_title"));;
		 * dto.setVcount(rs.getInt("vcount"));
		 * dto.setFirst_day(rs.getTimestamp("first_day"));
		 * 
		 * chboard.add(dto); } } catch (Exception e) {
		 * System.out.println("게시물 조회 중 예외 발생"); e.printStackTrace(); }try { if (pstmt
		 * != null) pstmt.close(); System.out.println("JDBC �ڿ� ����"); } catch
		 * (Exception e) { e.printStackTrace(); } return chboard; }
		 */
}