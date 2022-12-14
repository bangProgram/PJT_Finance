<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
		$("#yearReprtList").dataTable({
			lengthMenu: [ 12, 18, 24, 30, 36 ],
		 	data: ${yearReprtJson},
		 	destroy: true,
		 	ordering: true,
		 	columns: [
		  		{ data: 'RNUM' },
		  		{ data: 'CORP_NAME' },
		  		{ data: 'ACCOUNT_NM' },
		  		{ "data": 'RATE_0', 
		  			"render": function(data, type, row, meta){
		  				var index = row.SEQ;
		  				if(type === 'display'){
		  					if(index == 1){
		  						data += "<span style='float:right;'>(억원)</span>";				  						
		  					}else{
		  						data += "<span style='float:right;'>(%)</span>";
		  					}
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'RATE_1', 
		  			"render": function(data, type, row, meta){
		  				var index = row.SEQ;
		  				if(type === 'display'){
		  					if(index == 1){
		  						data += "<span style='float:right;'>(억원)</span>";				  						
		  					}else{
		  						data += "<span style='float:right;'>(%)</span>";
		  					}
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'RATE_2', 
		  			"render": function(data, type, row, meta){
		  				var index = row.SEQ;
		  				if(type === 'display'){
		  					if(index == 1){
		  						data += "<span style='float:right;'>(억원)</span>";				  						
		  					}else{
		  						data += "<span style='float:right;'>(%)</span>";
		  					}
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'RATE_3', 
		  			"render": function(data, type, row, meta){
		  				var index = row.SEQ;
		  				if(type === 'display'){
		  					if(index == 1){
		  						data += "<span style='float:right;'>(억원)</span>";				  						
		  					}else{
		  						data += "<span style='float:right;'>(%)</span>";
		  					}
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'RATE_4', 
		  			"render": function(data, type, row, meta){
		  				var index = row.SEQ;
		  				if(type === 'display'){
		  					if(index == 1){
		  						data += "<span style='float:right;'>(억원)</span>";				  						
		  					}else{
		  						data += "<span style='float:right;'>(%)</span>";
		  					}
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	data = '<a href="#" onclick="openNaverFinancePop(\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	data = '<a href="#" onclick="openMemoPop(\''+row.CORP_CODE+'\'); return false;" style="border-color: #FFD232; background: #FFD232;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>';
		  	            }
		  	            return data;
		  	         }	
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	data = '<a href="" onclick="addPortfolio(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-check"></i></a>';
		  	            }
		  	            return data;
		  	         }	 
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '<a href="#" onclick="delInterest(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
		  	            }
		  	            return data;
		  	         }	 
		  		}
		  	]
		});
	});
	
	function toggleTabel(){
		$('#tableHead').css("animation-name","");
		$('#bodyList').css("animation-name","");
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
				lengthMenu: [ 12, 18, 24, 30, 36 ],
			 	data: ${quaterReprtJson},
			 	destroy: true,
			 	ordering: true,
			 	columns: [
			  		{ data: 'RNUM' },
			  		{ data: 'CORP_NAME' },
			  		{ data: 'ACCOUNT_NM' },
			  		{ "data": 'RATE_0', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_1', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_2', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_3', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_4', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="openNaverFinancePop(\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="openMemoPop(\''+row.CORP_CODE+'\'); return false;" style="border-color: #FFD232; background: #FFD232;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '',
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="" onclick="addPortfolio(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-check"></i></a>';
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
				lengthMenu: [ 12, 18, 24, 30, 36 ],
			 	data: ${yearReprtJson},
			 	destroy: true,
			 	ordering: true,
			 	columns: [
			  		{ data: 'RNUM' },
			  		{ data: 'CORP_NAME' },
			  		{ data: 'ACCOUNT_NM' },
			  		{ "data": 'RATE_0', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_1', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_2', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_3', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": 'RATE_4', 
			  			"render": function(data, type, row, meta){
			  				var index = row.SEQ;
			  				if(type === 'display'){
			  					if(index == 1){
			  						data += "<span style='float:right;'>(억원)</span>";				  						
			  					}else{
			  						data += "<span style='float:right;'>(%)</span>";
			  					}
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="openNaverFinancePop(\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '', 
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="#" onclick="openMemoPop(\''+row.CORP_CODE+'\'); return false;" style="border-color: #FFD232; background: #FFD232;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>';
			  	            }
			  	            return data;
			  	         }	
			  		},
			  		{ "data": '',
			  			"defaultContent" : '',
			  			"render": function(data, type, row, meta){
			  	            if(type === 'display'){
			  	            	data = '<a href="" onclick="addPortfolio(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-check"></i></a>';
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
		
		$('#tableHead').css("animation-name","search");
		$('#tableHead').css("animation-duration","2.5s");
	}
	
	function delInterest(corpCd,corpNm,stockCd){
		var frm = document.searchForm;
		
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
		$('#bodyList').css("animation-name","");
		
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
				$('#bodyList').css("animation-name","add");
				$('#bodyList').css("animation-duration","2.5s");
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
	}
	
	function openNaverFinancePop(corpCd){
        var url = "https://finance.naver.com/item/main.nhn?code="+corpCd;
        var name = "네이버 크롤링 페이지";
        var option = "width = 1000, height = 1500, top = 100, left = 200, location = no"
        window.open(url, name, option);
    }
	
	function openMemoPop(corpCd){
        var url = "/popup/interest/memo?pCorpCd="+corpCd;
        var name = "관심기업 메모 팝업";
        var option = "width = 505, height = 780, top = 100, left = 200, location = no"
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
            <div class="table-responsive" style="position: relative;">
            	<div style="position: absolute; right: 275px; z-index: 1;">
					<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" tabindex="0" aria-controls="yearReprtList" onclick="toggleTabel(); return false;">
						<span>년도/반기</span>
					</button>                	
                </div>
                <table id="yearReprtList" class="table table-bordered" id="dataTable" width="100%" >
                	<colgroup>
                		<col width="2%"/>
                		<col width="9%"/>
                		<col width="9%"/>
                		<col width="12%"/>
                		<col width="12%"/>
                		<col width="12%"/>
                		<col width="12%"/>
                		<col width="12%"/>
                		<col width="5%"/>
                		<col width="5%"/>
                		<col width="5%"/>
                		<col width="5%"/>
                	</colgroup>
                    <thead id="tableHead">
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
							<td>정보</td>
							<td>메모</td>
							<td>추가</td>
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