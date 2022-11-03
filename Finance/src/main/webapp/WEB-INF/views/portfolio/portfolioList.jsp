<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
	
	});
	
	function delInterest(corpCd,corpNm,stockCd){
		var frm = document.form;
		
		if(!confirm('\''+corpNm+'\'을 관심목록에서 삭제하시겠습니까?')) return;
		
		$("#corpCd").val(corpCd);
		$("#corpNm").val(corpNm);
		$("#stockCd").val(stockCd);
		
		frm.action = "/interest/del/cud";
		frm.submit();
	}
	
	function addPortfolio(corpCd,corpNm,stockCd){
		var url = "/portfolio/add/cud";
		
		if(!confirm('\''+corpNm+'\'을 포트폴리오에 추가하시겠습니까?')) return;
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : url,           // 요청할 서버url    
			data : {
				CORP_CODE 	: corpCd,
				CORP_NAME	: corpNm,
				STOCK_CODE 	: stockCd,
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
<title>사업장 목록</title>
</head>
<body>
<div class="container-fluid">
	<form name="searchForm" method="post">
	<input type="hidden" name="pYearList" id="pYearList" value="${yearString}" title="조회 년도 목록">
	<input type="hidden" name="pQuaterList" id="pQuaterList" value="${quaterString}" title="조회 년도 목록">
	<input type="hidden" name="pStartYear" id="pStartYear" class="pSearchYear" value="${pStartYear}" title="조회 시작년도">
	<input type="hidden" name="pEndYear" id="pEndYear" class="pSearchYear" value="${pEndYear}" title="조회 시작년도">
	
	<input type="hidden" name="CORP_CODE" id="corpCd" value="" title="삭제대상 사업장코드">
	<input type="hidden" name="CORP_NAME" id="corpNm" value="" title="삭제대상 사업장명">
	<input type="hidden" name="STOCK_CODE" id="stockCd" value="" title="삭제대상 증권코드">
    </form>
</div>

<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">관심목록</h1>
	<p class="mb-4">관심목록에 저장해 놓은 기업의 성장성을 보여준다. 
		<span id=""></span>
	</p>
	
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3" id="dataTables">
            <h6 class="m-0 font-weight-bold text-primary">관심 기업목록</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive" >
                <table id="yearReprtList" class="table table-bordered" id="dataTable" width="100%" >
                    <thead id="tableHead">
			            <tr>
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
						</tr>
			        </thead>
			        <tbody id="bodyList">
			        </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>