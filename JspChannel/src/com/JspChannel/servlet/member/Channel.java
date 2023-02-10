package com.JspChannel.servlet.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.JspChannel.channel.MVCBoardDAO;
import com.JspChannel.channel.MVCBoardDTO;
import com.JspChannel.channel.PostDAO;
import com.JspChannel.channel.PostDTO;
import com.JspChannel.member.*;


/**
 * 메인 페이지와 연결된 서블릿
 */
@WebServlet("/member/Channel.do")
public class Channel extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException  {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=UTF-8");
		req.setAttribute("login", 0); // 0 : 로그인되지 않은 상태
		req.setAttribute("channeruser", 0);

		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		PostDAO dao = new PostDAO();
		int idx = Integer.parseInt(req.getParameter("idx"));
		
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			 if (memberDTO.getType() == 1) { // 일반 회원인 경우
				 System.out.println("타입1");
				req.setAttribute("login", 1); // 1 : 로그인 된 상태
				try {
					String userId = memberDTO.getId();
					if(dao.selectListChannelUser(idx, userId) == true) {
						System.out.println("구독조회");
						req.setAttribute("channeluser", 1);
						System.out.println(req.getAttribute("channeluser"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			}

		}
		
		String index = String.valueOf(idx);
		List<MVCBoardDTO> boardList3 = dao.selectListChannelDate(idx);

		int page = 1;
		if(req.getParameter("page") != null && req.getParameter("page") != "") {
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		//넘어온 keyword를 저장
		String keyword = "";
		if(req.getParameter("keyword") != null) {
			keyword = req.getParameter("keyword");
		}
		
		int pageCount = 10; //한 페이지에 표시되는 가게의 수
	
		
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();
		int resultCount = dao.selectCount(keyword);
		//2. 현재 페이지에 해당하는 글 목록을 가져옴
		list = dao.selectListPage(idx, keyword , page, pageCount);  
		
		//3. request에 넣어줌
		int pages = (int) Math.ceil((double) resultCount / 10);
		int end = (int) (Math.ceil((double) page/10) * 10);
		if(end > pages) {
			end = pages;
		}
		int start = 1;
		if(end > 10) {
			start = end - 9;
		}
		System.out.println(list.size());
		req.setAttribute("searchList", list);
		req.setAttribute("page", page); //현재 페이지
		req.setAttribute("pages", pages); //전체 페이지 수
		req.setAttribute("end", end); //마지막 페이지
		req.setAttribute("start", start); //시작 페이지
		req.setAttribute("keyword", keyword); //검색 키워드


	        
		// Channel.jsp로 이동
		
		req.setAttribute("index", index);
		req.setAttribute("boardList3", boardList3);
		req.getRequestDispatcher("../main/Channel.jsp").forward(req, resp);
		return;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
		return;
	}


}