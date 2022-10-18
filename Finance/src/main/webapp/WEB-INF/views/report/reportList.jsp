<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>사업장 목록</title>
</head>
<body>
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
		<a href="/create">생성</a>
	</p>
</body>
</html>
