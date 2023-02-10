package com.JspChannel.servlet.member;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.channel.ChannelDAO;
import com.JspChannel.channel.ChannelDTO;
import com.JspChannel.channel.PostDAO;
import com.JspChannel.member.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class JoinProc
 */
@WebServlet("/member/CreatechProc")
public class CreatechProc extends HttpServlet {
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
		
		
		//채널 생성 처리
		
		//channel 객체 가져오기
		ChannelDAO channelDAO = ChannelDAO.getInstance();
		ChannelDTO channelDTO = new ChannelDTO();
	
		memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		String user_id = memberDTO.getId();
		channelDTO.setUser_id(user_id);
		channelDTO.setCh_name(req.getParameter("ch_name"));
		channelDTO.setCh_image(req.getParameter("ch_image"));
		

		String realFolder = ""; // 실제 경로
		String saveFolder = "main/postFolder"; //postFolser 폴더 안에 저장
		String encoding = "utf-8";
		int maxFileSize = 10 * 1024 * 1024; // 최대 업로드 10mb

		String uploadFileName = null; // 업로드된 경로+파일명

		ServletContext context = req.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		MultipartRequest mr = new MultipartRequest(req, realFolder, maxFileSize, encoding,
				new DefaultFileRenamePolicy());





		try {
			if (mr.getParameter("ch_name") == null || mr.getParameter("ch_name") == "") {
				resp.sendRedirect("../main/Createch.jsp");
				return;
			}
			channelDTO.setCh_name(mr.getParameter("ch_name"));
				Enumeration e = mr.getFileNames(); // 폼의 이름 반환
				while (e.hasMoreElements()) {
					String eleName = (String) e.nextElement();
					String fileName = mr.getFilesystemName(eleName);
					String fileRealName = mr.getOriginalFileName(eleName);
					if (fileName != null) {
						// 이름 바꿔 업로드
						File originFile = new File(realFolder + "/" + fileName);
						String originFileName = originFile.getName();
						String ext = originFileName.substring(originFileName.lastIndexOf("."));
						String fileTempName = String.valueOf(channelDTO.getCh_num()) + System.currentTimeMillis() + ext;
						long fileSize = originFile.length();
						File tempFile = new File(realFolder + "/" + fileTempName);

						if (!originFile.renameTo(tempFile)) {
							System.out.println("파일명변경 실패");
						} else {
							uploadFileName = "../main/postFolder/" + fileTempName; // 저장된 경로(프로젝트 상대경로) + 파일명
						}

					}
				}
				// 새로운 파일이 들어왔을 때
				if (uploadFileName != null) {
					channelDTO.setCh_image(uploadFileName); // 파일경로 + 파일명 저장
				}
				channelDAO.getInstance().addChannel(channelDTO , user_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		resp.sendRedirect("../member/Main");
		return;
	}
}


