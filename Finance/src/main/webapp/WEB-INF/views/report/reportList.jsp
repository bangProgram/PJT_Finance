<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>사업장 목록</title>
</head>
<body>
	<form action="">
		<table>
			<tr>
				<th>조회 년도</th>
				<td><input type="text" name="pStartYear" id="pStartYear" value="" title="조회 시작년도"> ~ <input type="text" name="pEndYear" id="pEndYear" value="${curYear}" title="조회 시작년도">  </td>
			</tr>
			<tr>
				<th>기준 성장률</th>
				<td><input type="text" name="chkAccRate" id="chkAccRate" value="" title="기준 성장률">  </td>
			</tr>
			<tr>
				<th>조회 계정</th>
				<td><input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_Revenue" title="매출액"> 매출액 </td>
				<td><input type="checkbox" name="pAccountId" id="pAccountId" value="dart_OperatingIncomeLoss" title="영업이익"> 영업이익 </td>
				<td><input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_ProfitLoss" title="당기순이익"> 당기순이익 </td>
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
		<a href="/report/select">검색123</a>
	</p>
</body>
</html>
