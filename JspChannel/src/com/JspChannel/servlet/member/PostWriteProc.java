package com.JspChannel.servlet.member;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JspChannel.member.MemberDTO;
import com.JspChannel.channel.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/member/PostWriteProc")
public class PostWriteProc extends HttpServlet {
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

		PostDTO postDTO = new PostDTO();
		int idx = Integer.parseInt(req.getParameter("idx"));
		session.getAttribute("memberDTO");
		memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		String userId = memberDTO.getId();
		// 폼 데이터를 가져오기
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
			// 제목
			if (mr.getParameter("post_title") == null || mr.getParameter("post_title") == "") {
				resp.sendRedirect("../main/Channel.jsp");
				return;
			}
			postDTO.setPost_title(mr.getParameter("post_title"));
			
			// 내용
			if (mr.getParameter("post_contents") == null || mr.getParameter("post_contents") == "") {
				resp.sendRedirect("../main/Channel.jsp");
				return;
			}
			postDTO.setPost_contents(mr.getParameter("post_contents"));

			// 파일 업로드
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
					String fileTempName = String.valueOf(postDTO.getPost_num()) + System.currentTimeMillis() + ext;
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
				postDTO.setPost_file(uploadFileName); // 파일경로 + 파일명 저장
			}
			
			//가게 추가
			PostDAO.getInstance().addPost(postDTO, userId , idx);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		req.setAttribute("idx", idx);
		resp.sendRedirect("../member/Channel.do?idx=" + idx);
		return;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
		return;
	}
}
