<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="css/default.css">
<link href="css/fileinput.css" media="all" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
<title>录入商城数据</title>
</head>
<body>
	<form enctype="multipart/form-data" action=UploadShopPic method="post">

		<div class="htmleaf-container" style="min-height: 300px">
			<div class="container kv-main">
				<br> <input id="file-1" class="file" type="file" name="picture"><br>  

				<div class="form-group">
					<label for="name">--商城入口选择--</label> <select class="form-control"
						name="ShopType">
					 	 <option value="clothes">服饰</option>
				         <option value="shoes">跑鞋</option>
				         <option value="hotel">住宿</option>
				         <option value="bus">交通</option>
				         <option value="freePic">免费照片</option>
					</select>
				</div>
				<div class="form-group">
					<label for="name">--衣服子入口--</label> <select class="form-control"
						name="ClothesType">
					 	 <option value="女装">女装</option>
				         <option value="男装">男装</option>
				         <option value="童装">童装</option>
				         <option value="国际大牌">国际大牌</option>
				         <option value="精品特卖">精品特卖</option>
				         <option value="达人推荐">达人推荐</option>
					</select>
				</div>
				<div class="form-group">
					<label>品牌</label>
	                <input type="text"  class="form-control" name="brand"/>
	                <label>衣服名</label>
	                <input type="text"  class="form-control" name="name"/>
	                <label>描述</label>
	                <input type="text"  class="form-control" name="describe"/>
	                <label>所需积分</label>
	                <input type="text"  class="form-control" name="price"/>
				</div>
				<button type="submit" class="btn btn-primary">提交</button>
			</div>
		</div>

	</form>

	<script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js"></script>
	<script src="js/fileinput.js" type="text/javascript"></script>
	<script src="js/fileinput_locale_zh.js" type="text/javascript"></script>
	<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script>
	    $("#file-0").fileinput({
	        'allowedFileExtensions' : ['jpg', 'png','gif'],
	    });
	    $("#file-1").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        allowedFileExtensions : ['jpg', 'png','gif'],
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFilesNum: 10,
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
	    /*
	    $(".file").on('fileselect', function(event, n, l) {
	        alert('File Selected. Name: ' + l + ', Num: ' + n);
	    });
	    */
		$("#file-3").fileinput({
			showUpload: false,
			showCaption: false,
			browseClass: "btn btn-primary btn-lg",
			fileType: "any",
	        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
		});
		$("#file-4").fileinput({
			uploadExtraData: {kvId: '10'}
		});
	    $(".btn-warning").on('click', function() {
	        if ($('#file-4').attr('disabled')) {
	            $('#file-4').fileinput('enable');
	        } else {
	            $('#file-4').fileinput('disable');
	        }
	    });    
	    $(".btn-info").on('click', function() {
	        $('#file-4').fileinput('refresh', {previewClass:'bg-info'});
	    });
	    /*
	    $('#file-4').on('fileselectnone', function() {
	        alert('Huh! You selected no files.');
	    });
	    $('#file-4').on('filebrowse', function() {
	        alert('File browse clicked for #file-4');
	    });
	    */
	    $(document).ready(function() {
	        $("#test-upload").fileinput({
	            'showPreview' : false,
	            'allowedFileExtensions' : ['jpg', 'png','gif'],
	            'elErrorContainer': '#errorBlock'
	        });
	        /*
	        $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
	            alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
	        });
	        */
	    });
		</script>
</body>
</html>