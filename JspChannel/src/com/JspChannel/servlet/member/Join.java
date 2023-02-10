package com.JspChannel.servlet.member;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.member.MemberDTO;

@WebServlet("/member/Join")
public class Join extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
		
		//회원가입 실패한 경우 안내하는 부분
		String notice = "회원가입";
		if(session.getAttribute("joinMessage") != null){ //회원가입 실패 메시지가 넘어온 경우
			notice = (String)session.getAttribute("joinMessage");
			if(notice.equals("false")) { //회원가입 실패 메시지 넘어왔을 때
				notice = "회원가입 실패! 다시 시도해 주세요 ㅠㅠ";
			}else if(notice.equals("id")) {
				notice = "아이디를 확인해 주세요";
			}
			session.removeAttribute("joinMessage"); //해당 세션 삭제
		}
		req.setAttribute("notice", notice);

		// 회원가입 페이지로 이동
		req.getRequestDispatcher("../member/Join.jsp").forward(req, resp);
		return;
	}
}
