package com.JspChannel.servlet.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;

import com.JspChannel.channel.MVCBoardDAO;
import com.JspChannel.channel.MVCBoardDTO;
import com.JspChannel.channel.PostDAO;
import com.JspChannel.channel.PostDTO;
import com.JspChannel.member.*;

/**
 * 메인 페이지와 연결된 서블릿
 */
@WebServlet("/member/Subscribe.do")
public class subscribe extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		PostDAO dao2 = new PostDAO();
		int idx = Integer.parseInt(req.getParameter("index"));
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			 if (memberDTO.getType() == 1) { // 일반 회원인 경우
				 
				req.setAttribute("login", 1); // 1 : 로그인 된 상태
			   	String userId = memberDTO.getId();
				try {
						dao2.channelSubscribe(idx, userId);
						System.out.println("Subscribe 실행");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			resp.sendRedirect("../login/Login.jsp");
			return;
		}
		
		System.out.println(idx);
		resp.sendRedirect("../member/Channel.do?idx=" + idx);
		return;
	}


}