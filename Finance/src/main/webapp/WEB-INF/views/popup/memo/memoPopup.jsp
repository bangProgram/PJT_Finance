<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){

	});
	
	function addMemo(){
		var url = "/popup/memo/cud";
		
		$("#dataTables").css("animation-name","");
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : url,           // 요청할 서버url    
			data : {
				CORP_CODE 	: $("#CORP_CODE").val(),
				MEMO 		: $("#MEMO").val(),
				webPath		: $("#webPath").val()
			},    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수        
				$('#dataTables').css("animation-name","add");
				$('#dataTables').css("animation-duration","2.5s");
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
	}
</script>

<style>
@keyframes add {
	from{background : lightgreen;}
	to{background : transparent;}
}

@keyframes del {
	from{background : indianred;}
	to{background : transparent;}
}

@keyframes search {
	from{background : lightsteelblue;}
	to{background : transparent;}
}

</style>
<html>
<head>
<title>메모 팝업</title>
</head>
<body>
<div class="container-fluid">
	<form name="searchForm" method="post">
		<input type="hidden" name="CORP_CODE" id="CORP_CODE" value="<c:out value="${resultData.CORP_CODE }"/>" title="현재 CORP_CODE" >
		<input type="hidden" name="webPath" id="webPath" value="${webPath}" title="현재 webPath" >
    </form>
</div>

<div class="container-fluid">

	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3" id="dataTables" style="display: table;">
            <h6 class="m-0 font-weight-bold text-primary" style="display: table-cell; width: 95%; padding-left: 10px;"><c:out value="${resultData.CORP_NAME }"/> 메모장</h6>
            <div >
				<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" style="width: 70px;" tabindex="0" aria-controls="portCorpList" onclick="addMemo(); return false;">
					<span>
						<c:if test="${resultData.MEMO != null}">수정</c:if>
						<c:if test="${resultData.MEMO == null}">등록</c:if>
					</span>
				</button>                	
			</div>
        </div>
        <div class="card-body">
            <div class="table-responsive" >
            	<textarea rows="26" cols="54" name="content" id="MEMO" style="font-size: 13px;"><c:out value="${resultData.MEMO }"/>
				</textarea>
            </div>
        </div>
    </div>
</div>
</body>
</html>