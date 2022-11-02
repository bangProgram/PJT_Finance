<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
		$("#yearReprtList").dataTable({
			lengthMenu: [ 10, 15, 20, 25, 30 ],
		 	data: ${yearReprtJson},
		 	destroy: true,
		 	ordering: true,
		 	columns: [
		  		{ data: 'RNUM' },
		  		{ data: 'CORP_NAME' },
		  		{ data: 'ACCOUNT_ID' },
		  		{ data: 'RATE_0' },
		  		{ data: 'RATE_1' },
		  		{ data: 'RATE_2' },
		  		{ data: 'RATE_3' },
		  		{ data: 'RATE_4' },
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '<a href="/report/detail/list" target="_blank" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '<a href="#" onclick="delInterest(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" target="_blank" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
		  	            }
		  	            return data;
		  	         }	 
		  		}
		  	]
		});
	});
	
	function toggleTabel(){
		if(toggleChk == true){
			toggleChk = false;
			$('.quaterHaeder').css('display','');
			$('.yearHaeder').css('display','none');
			$("#yearReprtList").dataTable({
				buttons: [{
	                text: '년도/분기',
	                action: function ( e, dt, node, config ) {
	                	toggleTabel();
	                }
		        }],
				lengthMenu: [ 10, 15, 20, 25, 30 ],
			 	data: ${quaterReprtJson},
			 	destroy: true,
			 	ordering: true,
			 	columns: [
			  		{ data: 'RNUM' },
			  		{ data: 'CORP_NAME' },
			  		{ data: 'ACCOUNT_ID' },
			  		{ data: 'RATE_0' },
			  		{ data: 'RATE_1' },
			  		{ data: 'RATE_2' },
			  		{ data: 'RATE_3' },
			  		{ data: 'RATE_4' },
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	                data = '<a href="/report/detail/list" target="_blank" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '',
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="delInterest(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" target="_blank" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
			  	            }
			  	            return data;
			  	         }	 
			  		}
			  	]
			});
		}else{
			toggleChk = true;
			$('.quaterHaeder').css('display','none');
			$('.yearHaeder').css('display','');
			$("#yearReprtList").dataTable({
				buttons: [{
	                text: '년도/분기',
	                action: function ( e, dt, node, config ) {
	                	toggleTabel();
	                }
		        }],
				lengthMenu: [ 10, 15, 20, 25, 30 ],
			 	data: ${yearReprtJson},
			 	destroy: true,
			 	ordering: true,
			 	columns: [
			  		{ data: 'RNUM' },
			  		{ data: 'CORP_NAME' },
			  		{ data: 'ACCOUNT_ID' },
			  		{ data: 'RATE_0' },
			  		{ data: 'RATE_1' },
			  		{ data: 'RATE_2' },
			  		{ data: 'RATE_3' },
			  		{ data: 'RATE_4' },
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	                data = '<a href="/report/detail/list" target="_blank" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '',
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="delInterest(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" target="_blank" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
			  	            }
			  	            return data;
			  	         }	 
			  		}
			  	]
			});
			
		}
	}
	
function delInterest(corpCd, corpNm, stockCd){
	
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
	<input type="hidden" name="pQuaterList" id="pQuaterList" value="${quaterString}" title="조회 년도 목록">
	<input type="hidden" name="pStartYear" id="pStartYear" class="pSearchYear" value="${pStartYear}" title="조회 시작년도">
	<input type="hidden" name="pEndYear" id="pEndYear" class="pSearchYear" value="${pEndYear}" title="조회 시작년도">
	
	<a href="" onclick="toggleTabel(); return false;">토글버튼</a>
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
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive" style="position: relative;">
            	<div style="position: absolute; right: 275px; z-index: 1;">
					<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" tabindex="0" aria-controls="yearReprtList" onclick="toggleTabel(); return false;">
						<span>년도/반기</span>
					</button>                	
                </div>
                <table id="yearReprtList" class="table table-bordered" id="dataTable" width="100%" >
                	<colgroup>
                		<col width="3%"/>
                		<col width="10%"/>
                		<col width="10%"/>
                		<col width="13%"/>
                		<col width="13%"/>
                		<col width="13%"/>
                		<col width="13%"/>
                		<col width="13%"/>
                		<col width="6%"/>
                		<col width="6%"/>
                	</colgroup>
                    <thead>
			            <tr>
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
							<c:forEach items="${yearList}" var="list">
								<td class="yearHaeder">${list.HAEDER_NM}<span style="float: right;">(년도)</span></td>
							</c:forEach>
							<c:forEach items="${quaterList}" var="list">
								<td class="quaterHaeder" style="display: none;">${list.HAEDER_NM}<span style="float: right;">(반기)</span></td>
							</c:forEach>
							<td>상세</td>
							<td>제거</td>
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