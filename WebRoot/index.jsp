<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=base%>">

<title>My JSP 'index.jsp' starting page</title>
<script type="text/javascript" src="<%=base%>js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>js/ke/kindeditor-all.js"></script>
<script type="text/javascript" src="<%=base%>js/ke/lang/zh-CN.js"></script>
<script type="text/javascript" src="<%=base%>js/kecommon.js"></script>
<link href="<%=base%>js/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=base%>js/uploadify/swfobject.js"></script> 
<script type="text/javascript" src="<%=base%>js/uploadify/jquery.uploadify.v2.1.4.js"></script> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
<Br/>

<h1>KindEditor示例：</h1><br/>
	<textarea class="content">
		<%--<h2>
			大家好，我是<b style="color:Red;">虹妹子</b>,本示例包含KindEditor和ffmpeg视频工具 的使用方法
		</h2>
		<p>
			<br />
		</p>
		<h2>
			大家可以查看源码。有注释，方便大家理解。如果有什么问题欢迎加本人QQ：307364451联系
		</h2>
		<h2>
			<br />
		</h2>
		<h2>
			<br />
		</h2>
		<h2>
			<span style="color:#E53333;">*****特别声明：本人不是妹子,我媳妇是 妹子 :</span><span style="color:#E53333;"></span><span style="color:#E53333;font-size:12px;font-weight:normal;line-height:1.5;"></span><span style="color:#E53333;font-size:12px;font-weight:normal;line-height:1.5;"><img src="http://localhost:8080/js/ke/plugins/emoticons/images/30.gif" border="0" alt="" /><img src="http://localhost:8080/js/ke/plugins/emoticons/images/21.gif" border="0" alt="" /></span>
		</h2>
		<p>
			<br />
		</p>--%>
	</textarea>
<%--<h1>KindEditor图片上传组件单独使用</h1><br/>
<DIV k_type="u_img" s="">
	<DIV class="controls">
		<img k="img" id="img" src="" style="max-height:100px;max-width:200px;">
		<input k="val" id="imgSrc">
		<input type="button" value="选择图片" k="btn" />
	</DIV>
</DIV>--%>
<h1>视频格式转换示例：</h1><br/>
	<DIV class="control-group">
		<LABEL class="control-label" for="title"> </LABEL>
		<DIV class="controls">
			<input type="file" name="fileupload" id="fileupload" />
			<br/>视频截图地址：<br/><textarea id="imgPaht" cols="100"></textarea>
			<br/>视频地址：<br/><textarea id="filePath" cols="100"></textarea>
			<div id="fileQueue"></div> 
			<ol class=files></ol> 
			<img src="" style="max-height:200px;max-width:300px;" id="priview">
		</DIV>
	</DIV>
</body>

<script type="text/javascript">
$().ready(function() {
	function initEditor() {
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea.content', {
				resizeType : 1,
				allowImageUpload : true,
				allowFileManager : true,
				fileManagerJson : '<%=base%>manage',
				width : "100%",
				height : "350px",
				uploadJson : '<%=base%>upload'
				// items : [ 'source', 'fontname', 'fontsize', '|',
				// 		'forecolor', 'hilitecolor', 'bold',
				// 		'italic', 'underline', 'removeformat', '|',
				// 		'justifyleft', 'justifycenter',
				// 		'justifyright', 'insertorderedlist',
				// 		'insertunorderedlist', '|', 'emoticons',
				// 		'image', 'link' ]
			});
		});
	}
	initEditor();
	
	<%--$().uploadify({--%>
    <%--     /*注意前面需要书写path的代码*/--%>
	<%--	'uploader':'<%=base%>js/uploadify/uploadify.swf',--%>
	<%--	'script' : '<%=base%>upload?dir=media',--%>
	<%--	'cancelImg' : '<%=base%>js/uploadify/cancel.png',--%>
	<%--	'queueID' : 'fileQueue', //和存放队列的DIV的id一致--%>
	<%--	'fileDataName' : 'fileupload', //和以下input的name属性一致--%>
	<%--	'auto' : true, //是否自动开始--%>
	<%--	'multi' : false, //是否支持多文件上传--%>
	<%--	'buttonText' : '啥', //按钮上的文字--%>
	<%--	'buttonImg' : '<%=base%>images/select_file.png',--%>
	<%--	'hideButton' : false,--%>
	<%--	'width' : 90,--%>
	<%--	'height' : 28,--%>
	<%--	'keys': true,--%>
	<%--	'wmode' : 'transparent',--%>
	<%--	'simUploadLimit' : 1, //一次同步上传的文件数目--%>
	<%--	'sizeLimit' : 524288000, //设置单个文件大小限制--%>
	<%--	'queueSizeLimit' : 1, //队列中同时存在的文件个数限制--%>
	<%--	onComplete: function (event, queueID, fileObj, response, data) {--%>
	<%--		var json_d = eval( "(" + response + ")" );--%>
	<%--		if(json_d.error == 0){--%>
	<%--			$("#filePath").val(json_d.url);--%>
	<%--			$("#imgPaht").val(json_d.imgPath);--%>
	<%--			$("#priview").attr("src", "${base}"+json_d.imgPath);--%>
	<%--			$("#priview").show();--%>
	<%--			alert("上传成功!");--%>
	<%--		}else{--%>
	<%--			alert(json_d.message);--%>
	<%--		}--%>
	<%--	},--%>
	<%--	onError: function(event, queueID, fileObj) {--%>
	<%--		alert("文件:" + fileObj.name + "上传失败");--%>
	<%--	}--%>
	<%--});--%>
});
</script>
</html>
