package com.hsj.kindeditor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.hsj.kindeditor.util.FileManageUtil;

public class FileUploadServlet extends HttpServlet {

	
	private static final long serialVersionUID = 3837945092975528646L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String sn = null;
			String s = FileManageUtil.fileUpload(request, sn);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(s);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
