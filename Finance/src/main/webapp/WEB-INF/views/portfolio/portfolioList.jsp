<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
	
		$("#portCorpList").dataTable({
			lengthMenu: [ 12, 18, 24, 30, 36 ],
		 	data: ${getPortCorpJson},
		 	destroy: true,
		 	columns: [
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	data = '<input type="checkbox" name="del_chk" id="del_chk" value="'+row.CORP_CODE+'" title="삭제 체크박스">';
		  	            }

		  	            return data;
		  	         }	 
		  		},
		  		{ "data": 'INVEST_OPINION', 
		  			"render": function(data, type, row, meta){
		  				if(row.INVEST_OPINION != null && row.INVEST_OPINION != ''){
		  					if(type === 'display'){
			  	                data = row.INVEST_OPINION;
			  	            }		  					
		  				}else{
		  					data = "없음";
		  				}

		  	            return data;
		  	         }	
		  		},
		  		{ data: 'CORP_NAME' },
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '';
		  	            }

		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'AVR_PRICE', 
		  			"render": function(data, type, row, meta){
		  				if(row.AVR_PRICE != null && row.AVR_PRICE != ''){
		  					if(type === 'display'){
			  	                data = row.AVR_PRICE;
			  	            }		  					
		  				}else{
		  					data = "없음";
		  				}

		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'HOLD_QUANTITY', 
		  			"render": function(data, type, row, meta){
		  				if(row.HOLD_QUANTITY != null && row.HOLD_QUANTITY != ''){
		  					if(type === 'display'){
			  	                data = row.HOLD_QUANTITY;
			  	            }		  					
		  				}else{
		  					data = "없음";
		  				}

		  	            return data;
		  	         }	
		  		},
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '<a href="#" onclick="openReportPop(\''+row.REPRT_NO+'\',\''+row.REPRT_NM+'\')" >'+row.REPRT_NM+'</a>';
		  	            }

		  	            return data;
		  	         }	
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	 data = '<a href="#" onclick="openReportList(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\')" class="btn btn-light btn-icon-split"><span class="icon text-gray-600"><i class="fas fa-flag"></i></span></a>';
		  	            }

		  	            return data;
		  	         }	 
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	data = '<a href="#" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
		  	            }

		  	            return data;
		  	         }	 
		  		},
		  		{ "data": 'MEMO', 
		  			"render": function(data, type, row, meta){
		  				if(row.MEMO != null && row.MEMO != ''){
		  					if(type === 'display'){
			  	                data = '<a href="#" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
			  	            }		  					
		  				}else{
		  					data = "없음";
		  				}

		  	            return data;
		  	         }	
		  		}
		  	]
		});
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
	
	function openReportPop(reprtNo,reprtNm){
        var url = "https://dart.fss.or.kr/dsaf001/main.do?rcpNo="+reprtNo;
        var name = reprtNm;
        var option = "width = 500, height = 1500, top = 100, left = 200, location = no"
        window.open(url, name, option);
    }
	
	function openReportList(corpCd,corpNm){
        var url = "/popup/dart/report/list?corpCd="+corpCd;
        var name = corpNm + " 공시 목록";
        var option = "width = 1500, height = 1500, top = 100, left = 200, location = no"
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
	<h1 class="h3 mb-2 text-gray-800">포트폴리오</h1>
	<p class="mb-4">본인이 보유하고 있는 기업에 대한 포트폴리오 리스트 
		<span id=""></span>
	</p>
	
	<div class="col-xl-4 col-lg-5">
	    <div class="card shadow mb-4">
	        <!-- Card Header - Dropdown -->
	        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	            <h6 class="m-0 font-weight-bold text-primary">Revenue Sources</h6>
	            <div class="dropdown no-arrow">
	                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                </a>
	                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                    <div class="dropdown-header">Dropdown Header:</div>
	                    <a class="dropdown-item" href="#">Action</a>
	                    <a class="dropdown-item" href="#">Another action</a>
	                    <div class="dropdown-divider"></div>
	                    <a class="dropdown-item" href="#">Something else here</a>	
	                </div>
	            </div>
	        </div>
	        <!-- Card Body -->
	        <div class="card-body">
	            <div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
	                <canvas id="myPieChart" width="470" height="245" style="display: block; width: 470px; height: 245px;" class="chartjs-render-monitor"></canvas>
	            </div>
	            <div class="mt-4 text-center small">
	                <span class="mr-2">
	                    <i class="fas fa-circle text-primary"></i> Direct
	                </span>
	                <span class="mr-2">
	                    <i class="fas fa-circle text-success"></i> Social
	                </span>
	                <span class="mr-2">
	                    <i class="fas fa-circle text-info"></i> Referral
	                </span>
	            </div>
	        </div>
	    </div>
	</div>
		
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3" id="dataTables">
            <h6 class="m-0 font-weight-bold text-primary">포트폴리오 기업목록</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive" >
                <table id="portCorpList" class="table table-bordered" id="dataTable" width="100%" >
                    <thead id="tableHead">
			            <tr>
			            	<td></td>
							<td>투자의견</td>
							<td>사업장명</td>
							<td>현재주가</td>
							<td>평균단가</td>
							<td>보유수량</td>
							<td>최근공시명</td>
							<td>공시목록</td>
							<td>상세</td>
							<td>메모</td>
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