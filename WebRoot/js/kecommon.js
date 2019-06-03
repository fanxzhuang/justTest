var d = {
	base: "/KindEditor" //注意此处的工程名，如果部署的项目是 ROOT文件夹下，则此处 就为： base:""
}
/**
 * 调用图片上传组建,页面需要如下格式的 标签，当然 主要重要的是标签中的属性：
 * k_type="u_img" 此属性是调用_k.baseUpload()初始化标签的关键
 * img标签的 k=img 属性是上传成功后显示图片的img标签，必须得有，要不然图片显示不出来了
 * input标签的 k=val 属性，标识着图片上传成功过后，图片的服务器地址会相应的保存在此input标签中
 * <DIV k_type="u_img">
		<DIV class="controls">
			<img k="img" id="img" src="" style="max-height:100px;max-width:200px;">
			<input k="val" id="imgSrc">
			<input type="button" value="选择图片" k="btn" /> 
		</DIV>
	</DIV>
 */
var _k = {
	//调用这个，就会把页面中所有$("[k_type=u_img]")初始化图片上传组建功能
	baseUpload : function(){
		$("[k_type=u_img]").each(function(){
			var i = $(this).find("[k=img]");
			var v = $(this).find("[k=val]");
			$(this).find("[k=btn]").unbind("click");
			$(this).find("[k=btn]").bind("click", function(){
				K_E.upload.image(i, v);
			});
		});
	},
	//给指定eid初始化图片上传组件功能  eid可以为：#id , .className等
	upload : function(eid){
		var i = $(eid).find("[k=img]");
		var v = $(eid).find("[k=val]");
		$(eid).find("[k=btn]").unbind("click");
		$(eid).find("[k=btn]").bind("click", function(){
			K_E.upload.image(i, v);
		});
	}
}
var K_E = {}
KindEditor.ready(function(K) {
	_k.baseUpload();
	K_E = {
	    upload: {
	    	image : function (i, v){
	    		var editor = K.editor({
	    			allowFileManager : true,
	    			fileManagerJson : d.base+'/manage',
	    			uploadJson: d.base+'/upload'
	    		});
				editor.loadPlugin('image', function() {
					editor.plugin.imageDialog({
						imageUrl : K('#'+i).val(),
						clickFn : function(url, title, width, height, border, align) {
							v.val(url);
							i.attr("src",url);
							editor.hideDialog();
						}
					});
				});
	    	}
	    }
	};
});

