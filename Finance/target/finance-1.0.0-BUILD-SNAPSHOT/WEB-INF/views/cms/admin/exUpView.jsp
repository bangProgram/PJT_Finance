<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
$(document).ready(function(){
	$("select[name=REPRT_CODE]").change(function(){
		var reprtCd = this.value;
		if(reprtCd == "0301"){
			$("input[name=REPRT_NM]").val("사업보고서");
		}else{
			$("input[name=REPRT_NM]").val("반기보고서");
		}
	});
	
	$("select[name=SJ_DIV]").change(function(){
		var reprtCd = this.value;
		if(reprtCd == "0401"){
			$("input[name=SJ_NM]").val("손익계산서");
		}else{
			$("input[name=SJ_NM]").val("재무보고서");
		}
	});
});


function imageView(imageName) {
	if(imageName == 'loading'){
		LoadingWithMask('/css/images/views/pngwing.png');	
	}
}
 
function LoadingWithMask(gif) {
    //화면의 높이와 너비를 구합니다.
    var maskHeight = $(document).height();
    var maskWidth  = window.document.body.clientWidth;
     
    //화면에 출력할 마스크를 설정해줍니다.
    var mask       = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
    var loadingImg = '';
    
    loadingImg += "<div id='imgDiv' style='position:absolute;'>";
    loadingImg += "<img src='"+ gif + "' id='loadingImg'/>";
    loadingImg += "</div>";  
  
    //화면에 레이어 추가
    $('body')
        .append(mask).append(loadingImg);
 
    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    $('#mask').css({
            'width' : maskWidth,
            'height': maskHeight,
            'opacity' : '0.3'
    });
    
    $('#imgDiv').css({
    	'left':maskWidth/2,
    	'top':maskHeight/2,
	});
    
    $('#loadingImg').css({
    	'width': '100px',
    	'height': '100px',
	});
  
    //마스크 표시
    $('#mask').show();
  
    //로딩중 이미지 표시
    $('#loadingImg').show();
}
 
function closeLoadingWithMask() {
    $('#mask, #loadingImg').hide();
    $('#mask, #loadingImg').empty();  
}

function requiredChk(){
	for(var i=0; i<$('.requiredChk').length; i++){
		var valChk = $('.requiredChk').eq(i).val();
		if(valChk == ''){
			var title = $('.requiredChk').eq(i).attr('title');
			alert(title + '의 값을 입력해주세요.');
			return false;
		}
	}
	return true;
}

function goSave(){
	var frm = document.exform;
	if(!requiredChk()){
		return;
	};
	imageView('loading');
	frm.action = '/cms/admin/exUp/cud';
	frm.submit();
}
</script>
<style>

</style>
<html>
<head>
<title>(관리자)엑셀 업데이트</title>
</head>
<body>
<div class="container-fluid">
업로드
<form name="exform" id="exform" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="REPRT_NM" value=""/>
	<input type="hidden" name="SJ_NM" value=""/>
	<table>
		<tr>
			<td colspan="2"><input type="text" name="BSNS_YEAR" id="BSNS_YEAR" class="requiredChk" value="" title="보고서 년도"></td>
		</tr>
		<tr>
			<td>
				<select name="REPRT_CODE" id="REPRT_CODE" class="requiredChk" title="보고서 구분 선택" style="width: 200px;" > 
						<option value = "">선택</option>
						<option value = "0301">사업보고서</option>
						<option value = "0302">반기보고서</option>
				</select>
			</td>
			<td>
				<select name="SJ_DIV" id="SJ_DIV" class="requiredChk" title="보고서 계정 선택" style="width: 200px;" > 
						<option value = "">선택</option>
						<option value = "0401">손익계산서</option>
						<option value = "0402">재무보고서</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="file" name="file" /></td>
		</tr>
		<tr>
			<td><a href="" onclick="goSave();return false;" >엑셀 업로드</a></td>
		</tr>
	
  
	
	</table>
  
</form>

</div>

</body>
</html>