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
			var chkYearList = ''
			for(var i=1; pStartYear <= chkYear; i++){
				pYearList.push(chkYear);
				if(i==1){
					chkYearList += chkYear
					html += '<option value="'+chkYearList+'">'+ (chkYear--) +' </option>'
				}else{
					chkYearList += ','+chkYear
					html += '<option value="'+chkYearList+'">'+ pEndYear+' ~ '+(chkYear--) +' </option>'					
				}
				
			}
			$('#pYearList').val(pYearList);
			$('#chkYearList').append(html);
			
		});
	});

	function goSearch(){
		var frm = document.searchForm;
		frm.action = "/report/select";
		
		var pAccountIds = [];
		$("input[name=pAccountId]:checked").each(function(){
			pAccountIds.push($(this).val());
		});
		$("#pAccountIds").val(pAccountIds);
		
		frm.submit();
	}
</script>

<html>
<head>
<title>사업장 목록</title>
</head>
<body>
	<form name="searchForm" method="post">
		<table>
			<tr>
				<th>조회 년도</th>
				<td>
					<input type="hidden" name="pYearList" id="pYearList" value="" title="조회 년도 목록">
					<input type="text" name="pStartYear" id="pStartYear" class="pSearchYear" value="" title="조회 시작년도"> ~ <input type="text" name="pEndYear" id="pEndYear" class="pSearchYear" value="${curYear-1}" title="조회 시작년도">  
				</td>
				<th>보고서 구분</th>
				<td><input type="radio" name="pReportCd" id="pReportCd" value="11011" title="보고서 구분"> 년도 <input type="radio" name="pReportCd" id="pReportCd" value="11012" title="보고서 구분"> 반기 </td>
			</tr>
			<tr>
				<th>기준 성장률</th>
				<td><input type="text" name="chkAccRate" id="chkAccRate" value="" title="기준 성장률">  </td>
				<th>기준 개년</th>
				<td>
					<select name="chkYearList" id="chkYearList" title="기준 년도 선택"> 
						<option value = "">개년 선택</option>
					</select> 
				</td>
			</tr>
			<tr>
				<th>조회 계정</th>
				<td rowspan="3">
					<input type="hidden" name="pAccountIds" id="pAccountIds" value="" title="계정 목록">
					<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_Revenue" title="매출액"> 매출액 
					<input type="checkbox" name="pAccountId" id="pAccountId" value="dart_OperatingIncomeLoss" title="영업이익"> 영업이익
					<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_ProfitLoss" title="당기순이익"> 당기순이익 
				</td>
			</tr>
		</table>
	
	</form>
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
	<p>
		<a onclick="goSearch(); return false;">검색123</a>
	</p>
</body>
</html>