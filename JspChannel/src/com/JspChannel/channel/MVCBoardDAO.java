package com.JspChannel.channel;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.JspChannel.dbconnect.*;


public class MVCBoardDAO extends JDBConnect {
    public MVCBoardDAO() {
        super();
    }
    
    //Main 채널의 리스트
    public List<MVCBoardDTO> selectListCount() {			
   	 List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
   	 query = new StringBuffer();
		 query.append("SELECT channel.ch_name,channel.user_id,channel.ch_num , channel.ch_image ,COUNT(sub_ch.sub_num) as cs FROM sub_ch ");
		 query.append("RIGHT JOIN channel ON sub_ch.ch_num = channel.ch_num  GROUP BY channel.ch_num ORDER BY cs DESC limit 0,10");
       try {
       	   conn=dbConn.getConn();
           pstmt = conn.prepareStatement(query.toString());
           rs = pstmt.executeQuery();

           while (rs.next()) {
               MVCBoardDTO dto = new MVCBoardDTO();
               dto.setCh_num(rs.getInt("ch_num"));
               dto.setCh_name(rs.getString("ch_name"));
               dto.setCh_image(rs.getString("ch_image"));
               dto.setUser_id(rs.getString("user_id"));
               dto.setCs(rs.getInt("cs"));
               board.add(dto);
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
       return board;
   }
    
    public List<MVCBoardDTO> importantCount() {			
      	 List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
      	 query = new StringBuffer();
   		 query.append("SELECT channel.ch_name,channel.user_id,channel.ch_num, COUNT(sub_ch.sub_num) as cs FROM sub_ch ");
   		 query.append("RIGHT JOIN channel ON sub_ch.ch_num = channel.ch_num  GROUP BY channel.ch_num ORDER BY cs DESC limit 0,20");
          try {
          	   conn=dbConn.getConn();
              pstmt = conn.prepareStatement(query.toString());
              rs = pstmt.executeQuery();

              while (rs.next()) {
                  MVCBoardDTO dto = new MVCBoardDTO();
                  dto.setCh_num(rs.getInt("ch_num"));
                  dto.setCh_name(rs.getString("ch_name"));;
                  dto.setUser_id(rs.getString("user_id"));
                  dto.setCs(rs.getInt("cs"));
                  board.add(dto);
              }
          }
          catch (Exception e) {
              System.out.println("게시물 조회 중 예외 발생");
              e.printStackTrace();
          }try {            
              if (pstmt != null) pstmt.close();
              System.out.println("JDBC");
          }
          catch (Exception e) {
              e.printStackTrace();
          }
          return board;
      }
    
    public List<MVCBoardDTO> subscribeCount(String userId) {			
    	 List<MVCBoardDTO> board3 = new Vector<MVCBoardDTO>();
    	 query = new StringBuffer();
 		 query.append("SELECT channel.ch_name,channel.user_id,channel.ch_num FROM sub_ch ");
 		 query.append("INNER JOIN channel ON sub_ch.ch_num = channel.ch_num WHERE sub_ch.user_id = ? GROUP BY channel.ch_num");
        try {
        	 conn=dbConn.getConn();
            pstmt = conn.prepareStatement(query.toString());
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MVCBoardDTO dto = new MVCBoardDTO();
                dto.setCh_num(rs.getInt("ch_num"));
                dto.setCh_name(rs.getString("ch_name"));;
                dto.setUser_id(rs.getString("user_id"));
                board3.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }try {            
            if (pstmt != null) pstmt.close();
            System.out.println("JDBC");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return board3;
    }
  

  

}
