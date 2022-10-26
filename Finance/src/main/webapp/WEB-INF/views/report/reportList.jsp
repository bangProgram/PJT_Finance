<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	$(document).ready(function(){
		var chkYear = pEndYear;
		var yearString = $("#pYearList").val();
		var pYearList = yearString.split(',');
		$("#pStartYear").val(pYearList[4]);
		$("#pEndYear").val(pYearList[0]);
		var pEndYear = $("#pEndYear").val();
		
		var html = ''
		var chkYearList = ''
		for(var i=0; i<pYearList.length; i++){
			if(i==0){
				chkYearList += pYearList[i];
				html += '<option value="'+chkYearList+'">'+ pYearList[i] +' </option>'
			}else{
				chkYearList += ','+pYearList[i];
				html += '<option value="'+chkYearList+'">'+ pEndYear+' ~ '+ pYearList[i]  +' </option>'
			}
			
		}
		$('#chkYearList').append(html);
		
	});

	function goSearch(){
		var frm = document.searchForm;
		var pAccountIds = [];
		$("input[name=pAccountId]:checked").each(function(){
			pAccountIds.push($(this).val());
		});
		$("#pAccountIds").val(pAccountIds);
		
		var reprtCd = $("input[name=pReportCd]:checked").val();
		if(reprtCd == '11011'){
			$("#head1").css('display', 'table-row');
			$("#head2").css('display', 'none');
		}else{
			$("#head1").css('display', 'none');
			$("#head2").css('display', 'table-row');
		}
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : '/report/select',           // 요청할 서버url    
			data : {
				pYearList : $("#pYearList").val(),
				pStartYear : $("#pStartYear").val(),
				pEndYear : $("#pEndYear").val(),
				pReportCd : reprtCd,
				chkAccRate : $("#chkAccRate").val(),
				chkYearList : $("#chkYearList").val(),
				pAccountIds : $("#pAccountIds").val()
			},    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수        
				var resultList = data.resultList;
				var yearList = data.pYearList;
				 $("#reprtList").dataTable({
				 	data: resultList,
				 	destroy: true,
				 	columns: [
				  		{ data: 'RNUM' },
				  		{ data: 'CORP_NAME' },
				  		{ data: 'ACCOUNT_ID' },
				  		{ data: 'RATE_0' },
				  		{ data: 'RATE_1' },
				  		{ data: 'RATE_2' },
				  		{ data: 'RATE_3' },
				  		{ data: 'RATE_4' }
				  	]
				});
				alert("조회완료");
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
		
	}
</script>

<html>
<head>
<title>사업장 목록</title>
</head>
<body>
<div class="container-fluid">
	<form name="searchForm" method="post">
	<input type="hidden" name="pYearList" id="pYearList" value="${yearString}" title="조회 년도 목록">
	<input type="hidden" name="pStartYear" id="pStartYear" class="pSearchYear" value="" title="조회 시작년도">
	<input type="hidden" name="pEndYear" id="pEndYear" class="pSearchYear" value="" title="조회 시작년도">
	
	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">종목발굴</h1>
	<p class="mb-4"></p>

	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">조회조건</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
            
                <table class="table table-bordered"  width="100%" cellspacing="0">
                    <tbody>
                        <tr>
                            <td>기준 성장률</td>
							<td><input type="text" name="chkAccRate" id="chkAccRate" value="" title="기준 성장률">  </td>
							<td>기준 개년</td>
							<td>
								<select name="chkYearList" id="chkYearList" title="기준 년도 선택"> 
									<option value = "">개년 선택</option>
								</select> 
							</td>
                        </tr>
                        <tr>
                            <td>조회 계정</td>
							<td>
								<input type="hidden" name="pAccountIds" id="pAccountIds" value="" title="계정 목록">
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_Revenue" title="매출액"> 매출액 
								<input type="checkbox" name="pAccountId" id="pAccountId" value="dart_OperatingIncomeLoss" title="영업이익"> 영업이익
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_ProfitLoss" title="당기순이익"> 당기순이익 
							</td>
							<td>보고서 구분</td>
							<td><input type="radio" name="pReportCd" id="pReportCd" value="11011" title="보고서 구분" checked="checked"> 년도 <input type="radio" name="pReportCd" id="pReportCd" value="11012" title="보고서 구분"> 반기 </td>
                        </tr>
                    </tbody>
                </table>
                <a href="" onclick="goSearch(); return false;" class="btn btn-light btn-icon-split btn-sm">
                     <span class="icon text-white-50">
                         <i class="fas fa-flag"></i>
                     </span>
                     <span class="text">조 회</span>
               	</a>
            </div>
        </div>
    </div>
    </form>
</div>

<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">조회 결과</h1>
	<p class="mb-4">조회 결과에 따른 사업장명 및 년도별 성장성을 보여준다. 
		<span id=""></span>
	</p>
	
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table id="reprtList" class="table table-bordered" id="dataTable" width="100%" >
                    <thead>
			            <tr id="head1">
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
							<c:forEach items="${yearList}" var="list">
								<td>${list} 년</td>
							</c:forEach>
						</tr>
						<tr id="head2" style="display: none;">
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
							<c:forEach items="${quaterList}" var="list">
								<td>${list}</td>
							</c:forEach>
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