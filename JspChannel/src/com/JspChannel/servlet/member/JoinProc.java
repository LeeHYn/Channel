package com.JspChannel.servlet.member;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.member.*;

/**
 * Servlet implementation class JoinProc
 */
@WebServlet("/member/JoinProc")
public class JoinProc extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 1) { // 일반 회원인 경우
				req.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}
		
		
		//회원가입 처리
		
		//memberDAO 객체 가져오기
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDTO = new MemberDTO();
	
		//request 값 가져오기

		//memberDTO에 값 저장
		memberDTO.setName(req.getParameter("name"));
		memberDTO.setId(req.getParameter("id"));
		memberDTO.setPw(req.getParameter("pw"));
		memberDTO.setEmail(req.getParameter("email"));
		memberDTO.setType(1);
		
		//회원가입 메서드 수행
		try {
			if(!memberDAO.idCheck(memberDTO.getId())) {
				session.setAttribute("loginMessage", "id"); //login 페이지로 넘길 메시지
				resp.sendRedirect("../member/Join");
				return;
			}
			if(memberDAO.joinMember(memberDTO)) { //성공 시
				session.setAttribute("loginMessage", "join"); //login 페이지로 넘길 메시지
				resp.sendRedirect("../member/Login");
				return;
			} else { //실패 시
				session.setAttribute("joinMessage", "false"); //join 페이지로 넘길 실패 메시지
				resp.sendRedirect("../member/Join");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
