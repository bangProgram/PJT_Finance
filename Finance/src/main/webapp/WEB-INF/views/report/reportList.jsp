<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	$(document).ready(function(){
		$(".pSearchYear").change(function(){
			var pStartYear = $("#pStartYear").val();
			var pEndYear = $("#pEndYear").val();
			var chkYear = pEndYear;
			var pYearList = [];
			
			$('#chkYearList').children('option').remove();
			$('#pYearList').val('');
			var html = '<option value = "">개년 선택</option>'
			var td = ''
			var chkYearList = ''
			for(var i=1; pStartYear <= chkYear; i++){
				pYearList.push(chkYear);
				if(i==1){
					chkYearList += chkYear
					html += '<option value="'+chkYearList+'">'+ (chkYear) +' </option>'
					td += '<td>'+(chkYear--)+'</td>'
				}else{
					chkYearList += ','+chkYear
					html += '<option value="'+chkYearList+'">'+ pEndYear+' ~ '+(chkYear) +' </option>'
					td += '<td>'+(chkYear--)+'</td>'
				}
				
			}
			$('#pYearList').val(pYearList);
			$('#chkYearList').append(html);
			$('#head').append(td);
		});
	});

	function goSearch(){
		var frm = document.searchForm;
		var pAccountIds = [];
		$("input[name=pAccountId]:checked").each(function(){
			pAccountIds.push($(this).val());
		});
		$("#pAccountIds").val(pAccountIds);
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : '/report/select',           // 요청할 서버url    
			data : {
				pYearList : $("#pYearList").val(),
				pStartYear : $("#pStartYear").val(),
				pEndYear : $("#pEndYear").val(),
				pReportCd : $("#pReportCd").val(),
				chkAccRate : $("#chkAccRate").val(),
				chkYearList : $("#chkYearList").val(),
				pAccountIds : $("#pAccountIds").val()
			},    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수        
				var resultList = data.resultList;
				var yearList = data.pYearList;
				 $("#userList").dataTable({
				 	 data: resultList,
				 	 columns: [
				  		{ data: 'RNUM' },
				  		{ data: 'CORP_NAME' },
				       	{ data: 'ACCOUNT_ID' },
				       	for(var j=0; j<yearList.length; j++){
							var name = 'RATE_'+yearList[j];
							{ data: name },
						}
				  	]
				});
				 /* 
				for(var i=0; i<resultList.length; i++){
					var html = ''
					html += '<tr>'
					html +=	'<td>'+resultList[i].RNUM+'</td>'
					html +=	'<td>'+resultList[i].CORP_NAME+'</td>'
					html +=	'<td>'+resultList[i].ACCOUNT_ID+'</td>'
					for(var j=0; j<yearList.length; j++){
						var name = 'RATE_'+yearList[j];
						console.log("JB1 : "+name);
						html +=	'<td>'+resultList[i].name+'</td>'
						console.log("JB21 : "+resultList[i].name);
						console.log("JB22 : "+resultList[i].RATE_2021);
					}
					html += '</tr>'
					$('#bodyList').append(html);
				}
				 */
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
                            <td>조회 년도</td>
							<td>
								<input type="hidden" name="pYearList" id="pYearList" value="" title="조회 년도 목록">
								<input type="text" name="pStartYear" id="pStartYear" class="pSearchYear" value="" title="조회 시작년도"> ~ <input type="text" name="pEndYear" id="pEndYear" class="pSearchYear" value="${curYear-1}" title="조회 시작년도">  
							</td>
							<td>보고서 구분</td>
							<td><input type="radio" name="pReportCd" id="pReportCd" value="11011" title="보고서 구분"> 년도 <input type="radio" name="pReportCd" id="pReportCd" value="11012" title="보고서 구분"> 반기 </td>
                        </tr>
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
							<td rowspan="3">
								<input type="hidden" name="pAccountIds" id="pAccountIds" value="" title="계정 목록">
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_Revenue" title="매출액"> 매출액 
								<input type="checkbox" name="pAccountId" id="pAccountId" value="dart_OperatingIncomeLoss" title="영업이익"> 영업이익
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_ProfitLoss" title="당기순이익"> 당기순이익 
							</td>
                        </tr>
                    </tbody>
                </table>
                <a href="" onclick="goSearch(); return false;" class="btn btn-primary btn-icon-split btn-sm">
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
	<h1 class="h3 mb-2 text-gray-800">Tables</h1>
	<p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below.
	    For more information about DataTables, please visit the <a target="_blank"
	        href="https://datatables.net">official DataTables documentation</a>.</p>
	
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table id="userList" class="table table-bordered" id="dataTable" width="100%" >
                    <thead>
			            <tr id="head">
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
	<%-- 
	<h1>사업장 목록</h1>
	<table>
		<thead>
			<tr>
				<td>사업장명</td>
				<td>매출액</td>
				<td>매출증가율</td>
				<td>영업이익</td>
				<td>영업이익증가율</td>
				<td>순이익</td>
				<td>순이익증가율</td>
			</tr>
		</thead>
		<tbody>
		<tr>
			<c:forEach var="row" items="${data}">
					<td>${row.corp_name}</td>
					<td>${row.category}</td>
					<td>${row.category}</td>
					<td>${row.category}</td>
					<td>${row.category}</td>
					<td>${row.category}</td>
					<td>${row.category}</td>
			</c:forEach>
		</tr>
		</tbody>
		
	</table>
	 --%>
	<p>
		<a onclick="goSearch(); return false;">검색123</a>
	</p>
</body>
</html>