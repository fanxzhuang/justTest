package com.hsj.kindeditor.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;


/**
 * 文件上传、文件管理工具类
 * @ClassName FileManageUtil.java
 * @Description 
 * @author Caizongyou 
 * @date 2015-4-24
 */
public class FileManageUtil {
	
	/**
	 * 文件管理，返回Kindeditor 需要的 文件格式字符串
	 * @param request
	 * @param cSn
	 * @return
	 */
	public static String fileManage(HttpServletRequest request, String cSn){
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = request.getRealPath("/") + "upload/"+cSn+"/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = request.getContextPath() + "/upload/"+cSn+"/";
		if(StringUtil.isEmpty(cSn))
			rootPath = request.getRealPath("/")+"upload/";
		if(StringUtil.isEmpty(cSn))
			rootUrl = request.getContextPath()+"/upload/";
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				return "Invalid Directory name.";
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			return "Access is not allowed.";
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			return "Parameter is not valid.";
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			return "Directory does not exist.";
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		return (result.toJSONString());
	}
	
	/**
	 * 上传文件，支持图片、视频、音频
	 * @param request
	 * @param cSn
	 * @return
	 * @throws FileUploadException
	 */
	public static String fileUpload(HttpServletRequest request,String cSn) throws FileUploadException{
		//文件保存目录路径
		String savePath = request.getRealPath("/") + "upload/"+cSn+"/";
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/upload/"+cSn+"/";

		if(StringUtil.isEmpty(cSn))
			savePath = request.getRealPath("/") + "upload/";
		if(StringUtil.isEmpty(cSn))
			saveUrl = request.getContextPath()+"/upload/";
		String imgPath="";
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv,rmvb,wmv,avi,mpg,webm,rm");
//		extMap.put("media", "mp3,mp4,swf,flv,rmvb,wmv,avi,mpg,webm,rm");
		extMap.put("media", "mp3");
		extMap.put("template", "zip,rar");
        extMap.put("video", "avi,wmv,mpeg,mp4,mov,mkv,flv,f4v,m4v,rmvb,rm,3gp,dat,ts,mts,vob");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		//最大文件大小
		long maxSize_img = 1048676;
		long maxSize_mp3 = 1048576 * 500;
		long maxSize_mp4 = 1048576 * 500;

		if(!ServletFileUpload.isMultipartContent(request)){
			return getError("请选择文件。");
		}

		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.exists()) uploadDir.mkdirs();
		if(!uploadDir.isDirectory()){
			return getError("上传目录不存在。");
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return getError("上传目录没有写权限。");
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			return getError("目录名不正确。");
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		String s = request.getParameter("s");
		if(!StringUtil.isEmpty(s)){
			savePath += s + "/";
			saveUrl += s + "/";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
				}
				if(extMap.get("video").contains(fileExt)&&!fileExt.equals("mp3")){
					if(item.getSize() > maxSize_mp4){
						return getError("上传文件大小超过限制。最大只能上传"+maxSize_mp4/(1024*1024)+"M的文件");
					}
				}else if(fileExt.equals("mp3")){
					if(item.getSize() > maxSize_mp3){
						return getError("上传文件大小超过限制。最大只能上传"+maxSize_mp3/(1024*1024)+"M的文件");
					}
				}else{
					if(item.getSize() > maxSize_img){
						return getError("上传文件大小超过限制。最大只能上传"+maxSize_img/(1024*1024)+"M的文件");
					}
				}


				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
					if(extMap.get("video").contains(fileExt)&&!fileExt.equals("mp3")){
						String nFileName=df.format(new Date())+"_"+new Random().nextInt(1000)+".mp4";
						
						imgPath=df.format(new Date())+"_"+new Random().nextInt(1000)+".jpg";

						//获取文件真实路径
						String sourceFile=savePath+newFileName;
						String filePath=savePath+nFileName;
						
						//获取工具真实路径
//						String toolsPath="/usr/local/ffmpeg/ffmpeg/ffmpeg";
						String toolsPath = request.getRealPath("/WEB-INF/ffmpeg/bin/ffmpeg.exe");
						
						FFMpegUtil m = new FFMpegUtil(toolsPath, sourceFile);
						//m.makeScreenCut(savePath+imgPath, "640x480"); //获取截图
						new Thread(new FFmpeg(m, filePath, sourceFile, toolsPath)).start();//开线程视频转换
						newFileName=nFileName;
					}else{
						try{
							CompressPic mypic = new CompressPic(); 
							mypic.compressPic( savePath, savePath+"/small/", newFileName, newFileName,200,200,true);
							mypic.compressPic( savePath, savePath+"/middle/", newFileName, newFileName,400,400,true);
						}catch(Exception e){}
					}

					
				}catch(Exception e){
					e.printStackTrace();
					return getError("上传文件失败。");
				}
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				/*if(extMap.get("video").contains(fileExt)&&!fileExt.equals("mp3")){
					obj.put("imgPath", saveUrl+imgPath);//回传截图地址
				}*/
				return obj.toJSONString();
			}
		}
		return getError("未知错误");
	}
	
	public static class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	
	public static class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	public static class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
	private static String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
}

/**
 * 视频转换线程
 * @ClassName FileManageUtil.java
 * @Description 
 * @author Caizongyou 
 * @date 2015-4-24
 */
class FFmpeg implements Runnable{
	FFMpegUtil m;
	String filePath;
	String sourcePath;
	String totalPath;
	public FFmpeg(FFMpegUtil m, String filePath, String sourcePath, String totalPath){
		this.m = m;
		this.totalPath = totalPath;
		this.sourcePath = sourcePath;
		this.filePath = filePath;
	}
	
	public void run() {
		FFMpegUtil m = new FFMpegUtil(totalPath, sourcePath);
		m.videoTransfer(filePath, "160x120", 20, 20, 100, 15);//转换视频
	}
}

