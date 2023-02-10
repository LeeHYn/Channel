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

public class ChannelDAO extends JDBConnect{
	public static ChannelDAO channelDAO = new ChannelDAO();
	public ChannelDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static ChannelDAO getInstance() {
		if (channelDAO == null) {
			channelDAO = new ChannelDAO();
		}
		return channelDAO;
	}

	
	 //게시물 수를 카운트 해준다.
    public int selectCount(String keyword) {
        int totalCount = 0;
         query = new StringBuffer();
  		 query.append("SELECT COUNT(*) FROM channel WHERE ( ch_name like '%");
  		 query.append(keyword + "%' )");
  		 query.append("order by ch_num");
        try {
        	conn=dbConn.getConn();
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
    public ArrayList<ChannelDTO> selectListchPage(String keyword , int page , int pageCount) {
        ArrayList<ChannelDTO> boardList = new ArrayList<ChannelDTO>();;
       
        query = new StringBuffer();
        
		 query.append("select * from (SELECT distinct ");
		 query.append("ch_num , ch_name , user_id , ch_image , ");
		 query.append(" row_number() over(order by ch_num) as num from channel where ");
		 query.append("(ch_name like '%" + keyword + "%')");
		 query.append("ORDER BY ch_num) as temp WHERE num between ? and ?");
		 
        try {
        	pstmt = conn.prepareStatement(query.toString());
            pstmt.setInt(1, (page - 1) * pageCount + 1);
			pstmt.setInt(2, (page) * pageCount);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	ChannelDTO dto = new ChannelDTO();

                dto.setCh_num(rs.getInt("ch_num"));
                dto.setUser_id(rs.getString("user_id"));
                dto.setCh_name(rs.getString("ch_name"));
                dto.setCh_image(rs.getString("ch_image"));

                boardList.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return boardList;
    }
	
	
    //채널 추가
    public synchronized boolean addChannel(ChannelDTO channelDTO , String user_id) throws SQLException {
		try {
			conn = dbConn.getConn();
			StringBuffer query = new StringBuffer();

			query.append("INSERT INTO channel(user_id, ch_name, ch_image)");
			query.append("VALUES(?,?,?) ");

			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, user_id);
			pstmt.setString(2, channelDTO.getCh_name());
			pstmt.setString(3, channelDTO.getCh_image());
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