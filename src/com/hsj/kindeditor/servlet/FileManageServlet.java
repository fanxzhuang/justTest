package com.hsj.kindeditor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsj.kindeditor.util.FileManageUtil;

public class FileManageServlet extends HttpServlet {

	private static final long serialVersionUID = 1756907532359600387L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sn = null;
		String s = FileManageUtil.fileManage(request, sn);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
