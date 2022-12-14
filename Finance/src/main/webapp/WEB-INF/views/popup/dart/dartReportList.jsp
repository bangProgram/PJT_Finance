<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
		$("#dartReprtList").dataTable({
			lengthMenu: [ 15, 20, 25, 30, 35 ],
		 	data: ${getDartReportJson},
		 	destroy: true,
		 	ordering: true,
		 	columns: [
		  		{ data: 'SEQ' },
		  		{ data: 'CORP_NM' },
		  		{ "data": 'REPRT_NM', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	              	data = '<a href="#" onclick="openReportPop(\''+row.REPRT_NO+'\',\''+row.REPRT_NM+'\')" >'+row.REPRT_NM+'</a>';
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ data: 'FLR_NM' },
		  		{ data: 'REPORT_DT' }
		  	]
		});
	});
	
	function openReportPop(reprtNo,reprtNm){
        var url = "https://dart.fss.or.kr/dsaf001/main.do?rcpNo="+reprtNo;
        var name = reprtNm;
        var option = "width = 500, height = 1500, top = 100, left = 200, location = no"
        window.open(url, name, option);
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
            <h6 class="m-0 font-weight-bold text-primary">전자공시 보고서목록</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive" >
                <table id="dartReprtList" class="table table-bordered" id="dataTable" width="100%" >
                	<colgroup>
                		<col width="5%"/>
                		<col width="20%"/>
                		<col width="35%"/>
                		<col width="20%"/>
                		<col width="20%"/>
                	</colgroup>
                    <thead id="tableHead">
			            <tr>
			            	<td>No</td>
							<td>공시대상회사</td>
							<td>보고서명</td>
							<td>제출인</td>
							<td>접수일자</td>
						</tr>
			        </thead>
			        <tbody id="bodyList" style="font-size: 12px;">
			        </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>