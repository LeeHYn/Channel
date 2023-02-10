package com.JspChannel.servlet.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.channel.PostDAO;
import com.JspChannel.channel.PostDTO;
import com.JspChannel.member.MemberDTO;

/**
 * 가게 추가 페이지와 연결된 서블릿
 */
@WebServlet("/member/View.do")
public class View extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=UTF-8");
		
		req.setAttribute("login", 0); // 0 : 로그인되지 않은 상태

		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 1) { // 일반 회원인 경우
				req.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}

		try {
			int post_num = Integer.parseInt(req.getParameter("post_num"));
			PostDAO dao = new PostDAO();
			dao.updateVisitCount(post_num);
			ArrayList<PostDTO> postboard = dao.PostView(post_num);
			req.setAttribute("postboard", postboard);
			System.out.println(postboard);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		req.getRequestDispatcher("/main/View.jsp").forward(req, resp);
		return;
	}
}
