<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	document.addEventListener("DOMContentLoaded", function () {
		
		const datas = [
			<c:out value=" ${portAmount.DEPOSIT_AMOUNT}"/>,
			<c:out value=" ${portAmount.RESERVE_AMOUNT}"/>,
			<c:out value=" ${portAmount.INVEST_AMOUNT}"/>
		];
		
		const ctx = document.getElementById('myPieChart');
		
		new Chart(ctx, {
			  type: 'pie',
			  
			  data: {
				  labels: ['예수금', '예비금','투자금'],
			    datasets: [{
			    	labels: ['예수금', '예비금','투자금'],
			      data: datas,
			    }]
			  },
			  options: {
					maintainAspectRatio: false,
				},
			});
	});
	
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
		  						var style = '';
		  						if(row.INVEST_OPINION == '매수'){
		  							style = "style='color: red;'";
		  						}else if(row.INVEST_OPINION == '비중확대'){
		  							style = "style='color: palevioletred;'";
		  						}else if(row.INVEST_OPINION == '중립'){
		  							style = "style='color: black;'";
		  						}
		  						else if(row.INVEST_OPINION == '비중축소'){
		  							style = "style='color: cornflowerblue;'";
		  						}
		  						else if(row.INVEST_OPINION == '매도'){
		  							style = "style='color: blue;'";
		  						}else{
		  							style = "";
		  						}
			  	                data = '<span '+style+'>'+row.INVEST_OPINION+'( ~ '+row.INVEST_OPINION_AMOUNT+' )</span>';
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
	  						var style = '';
	  						if(row.CURRENT_PER > row.AVR_PER){
	  							style = "style='color: blue;'";
	  						}else if(row.CURRENT_PER == row.AVR_PER){
	  							style = "style='color: black;'";
	  						}else {
	  							style = "style='color: red;'";
	  						}
		  	                data = '<span '+style+'>'+row.CURRENT_PER+'</span> / '+row.AVR_PER;
		  	            }		  					

		  	            return data;
		  	         }	
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
	  					if(type === 'display'){
	  						var style = '';
	  						var cagr = row.ESTIMATE_CAGR*1;
	  						
	  						if(cagr >= 15){
	  							style = "style='color: red;'";
	  						}else if(15 > cagr && cagr >= 5){
	  							style = "style='color: palevioletred;'";
	  						}else if( 5 > cagr && cagr > 0 ){
	  							style = "style='color: cornflowerblue;'";
	  						}else {
	  							style = "style='color: black;'";
	  						}
		  	                data = '<span '+style+'>'+row.ESTIMATE_CAGR+'</span>';
		  	            }		  					

		  	            return data;
		  	         }	
		  		},
		  		{ data: 'CORP_NAME' },
		  		{ "data": 'BEF_CLS_PRICE', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  				if(row.BEF_CLS_PRICE != null && row.BEF_CLS_PRICE != ''){
		  					if(type === 'display'){
			  	                data = row.BEF_CLS_PRICE;
			  	            }		  					
		  				}else{
		  					data = "없음";
		  				}

		  	            return data;
		  	         }	
		  		},
		  		{ "data": 'AVR_PRICE', 
		  			"render": function(data, type, row, meta){
		  				if(row.AVR_PRICE != null && row.AVR_PRICE != ''){
		  					if(type === 'display'){
		  						var style = '';
		  						if(row.AVR_PRICE < row.BEF_CLS_PRICE){
		  							style = "style='color: palevioletred;'";
		  						}else if(row.AVR_PRICE > row.BEF_CLS_PRICE){
		  							style = "style='color: cornflowerblue;'";
		  						}
		  						else{
		  							style = "";
		  						}
			  	                data = '<span '+style+'>'+row.AVR_PRICE+'</span>';
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
		  		{ "data": 'CHANGE_DT', 
		  			"render": function(data, type, row, meta){
	  					if(type === 'display'){
		  	                data = row.CHANGE_DT;
		  	            }		  					

		  	            return data;
		  	         }	
		  		},
		  		{ "data": '', 
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	                data = '<a href="#" onclick="openReportPop(\''+row.REPRT_NO+'\',\''+row.REPRT_NM+'\'); return false;" >'+row.REPRT_NM+'</a>';
		  	            }

		  	            return data;
		  	         }	
		  		},
		  		{ "data": '',
		  			"defaultContent" : '',
		  			"render": function(data, type, row, meta){
		  	            if(type === 'display'){
		  	            	 data = '<a href="#" onclick="openReportList(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\'); return false;" class="btn btn-light btn-icon-split"><span class="icon text-gray-600"><i class="fas fa-flag"></i></span></a>';
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
		  	            	data = '<a href="#" onclick="goPortfolioDetail(\''+row.CORP_CODE+'\'); return false;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
		  	            }

		  	            return data;
		  	         }	 
		  		},
		  		{ "data": 'MEMO', 
		  			"render": function(data, type, row, meta){
		  				if(row.MEMO != null && row.MEMO != ''){
		  					if(type === 'display'){
		  						data = '<a href="#" onclick="openMemoPop(\''+row.CORP_CODE+'\'); return false;" style="border-color: #FFD232; background: #FFD232;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>';
			  	            }		  					
		  				}else{
		  					data = '<a href="#" onclick="openMemoPop(\''+row.CORP_CODE+'\'); return false;" style="border-color: #aeafb9; background: #aeafb9;" class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>';
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
	
	function openMemoPop(corpCd){
        var url = "/popup/portfolio/memo?pCorpCd="+corpCd;
        var name = "포트폴리오 메모 팝업";
        var option = "width = 505, height = 780, top = 100, left = 200, location = no"
        window.open(url, name, option);
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
        var option = "width = 1000, height = 700, top = 100, left = 200, location = no"
        window.open(url, name, option);
    }
	
	function delPortCorp(){
		var frm = document.searchForm;
		var delChks = "";
		
		var i = 0;
		$("input[name=del_chk]:checked").each(function(){
			if(i==0){
				delChks += $(this).val();
			}else{
				delChks += ","+$(this).val();
			}
			i++;
		});
		
		if(i == 0){
			alert("삭제할 사업장을 선택해 주세요.");
			return;
		}
		
		var corpCd = "";
		
		if(!confirm('체크된 사업장을 포트폴리오에서 삭제하시겠습니까?')) return;
		
		$("#corpCds").val(delChks);
		
		frm.action = "/portfolio/del/cud";
		frm.submit();
	}
	
	function regAsset(gu){
		var url = "/portfolio/regasset/cud";
		
		var guTitle = "";
		var guVal = "";
		if(gu == 'deposit'){
			guVal = $("#DEPOSIT_AMOUNT").val();
			guTitle = $("#DEPOSIT_AMOUNT").attr("title");
		}else{
			guVal = $("#RESERVE_AMOUNT").val();
			guTitle =  $("#RESERVE_AMOUNT").attr("title");
		}

		guVal = guVal.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
		
		alert(guTitle + " : "+guVal+" 원 등록 하시겠습니까?");
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : url,           // 요청할 서버url    
			data : {
				DEPOSIT_AMOUNT 	: $("#DEPOSIT_AMOUNT").val(),
				RESERVE_AMOUNT	: $("#RESERVE_AMOUNT").val(),
			},    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수
				var resultData = data.resultData;
				if(gu == 'deposit'){
					$("btn_DEPOSIT_AMOUNT").text('수정');
					$("#DEPOSIT_AMOUNT").val(resultData.DEPOSIT_AMOUNT);
				}else{
					$("btn_RESERVE_AMOUNT").text('수정');
					$("#RESERVE_AMOUNT").val(resultData.RESERVE_AMOUNT);
				}
				$('#cardDiv').css("animation-name","add");
				$('#cardDiv').css("animation-duration","2.5s");
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
	}
	
	function goPortfolioDetail(corpCd){
		var frm = document.searchForm;
		
		$("#pCorpCd").val(corpCd);
		
		frm.action = "/portfolio/detail";
		frm.target = "_blank"
		frm.submit();
	}
	
	function openNaverFinancePop(corpCd){
        var url = "https://finance.naver.com/item/main.nhn?code="+corpCd;
        var name = "네이버 크롤링 페이지";
        var option = "width = 1000, height = 1500, top = 100, left = 200, location = no"
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
	<input type="hidden" name="CORP_CODE" id="CORP_CODE" value="" title="기업 코드">
	<input type="hidden" name="STOCK_CODE" id="STOCK_CODE" value="" title="기업 코드">
	
	<input type="hidden" name="pCorpCd" id="pCorpCd" value="" title="검색용 기업 코드">
	
	<input type="hidden" name="corpCds" id="corpCds" value="" title="삭제대상 사업장코드(복수)">
    </form>
</div>

<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">포트폴리오</h1>
	<p class="mb-4">본인이 보유하고 있는 기업에 대한 포트폴리오 리스트 
		<span id=""></span>
	</p>
	
	<div class="col-xl-4 col-lg-5">
	    <div class="card shadow mb-4" id="cardDiv">
	        <!-- Card Header - Dropdown -->
	        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	            <h6 class="m-0 font-weight-bold text-primary">포트폴리오 자산 분배비율</h6>
	            <div class="dropdown no-arrow">
	                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                </a>
	                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                    <div class="dropdown-header" style="font-size: 12px;">- 자산 등록 -</div>
	                    <div class="dropdown-divider"></div>
	                    <div style="padding: 5px 10px 5px 10px;">
		                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;">
			                    예수금 : <input type="text" name="DEPOSIT_AMOUNT" id="DEPOSIT_AMOUNT" style="width : 135px;" value="${getPortfolio.DEPOSIT_AMOUNT }"  title="예수금" placeholder="예수금"> 
			                    <button class="btn btn-outline-primary" style="height: 30px; font-size: 13px; width: 55px;" id="btn_DEPOSIT_AMOUNT" onclick="regAsset('deposit'); return false;">
			                    	<c:if test="${getPortfolio.DEPOSIT_AMOUNT != null }">수정</c:if>
			                    	<c:if test="${getPortfolio.DEPOSIT_AMOUNT == null }">등록</c:if>
			                    </button>
		                    </div>
		                    <div class="dropdown-divider"></div>
		                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;">
			                    예비금 : <input type="text" name="RESERVE_AMOUNT" id="RESERVE_AMOUNT" style="width : 135px;" value="${getPortfolio.RESERVE_AMOUNT }"  title="예비금" placeholder="예비금"> 
			                    <button class="btn btn-outline-primary" style="height: 30px; font-size: 13px; width: 55px;" id="btn_RESERVE_AMOUNT" onclick="regAsset('reserve'); return false;">
			                    	<c:if test="${getPortfolio.RESERVE_AMOUNT != null }">수정</c:if>
			                    	<c:if test="${getPortfolio.RESERVE_AMOUNT == null }">등록</c:if> 
			                    </button>
		                    </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <!-- Card Body -->
	        <div class="card-body">
	            <div class="chart-container" style="position: relative; height:320px;">
					<canvas id="myPieChart"></canvas>
				</div>
	        </div>
	    </div>
	</div>
		
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3" id="dataTables" style="display: table;">
            <h6 class="m-0 font-weight-bold text-primary" style="display: table-cell; width: 95%; padding-left: 10px;">포트폴리오 기업목록</h6>
            <div >
				<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" style="width: 70px;" tabindex="0" aria-controls="portCorpList" onclick="delPortCorp(); return false;">
					<span>삭제</span>
				</button>                	
			</div>
        </div>
        <div class="card-body">
            <div class="table-responsive" >
                <table id="portCorpList" class="table table-bordered" id="dataTable" width="100%" >
                    <thead id="tableHead">
			            <tr>
			            	<td></td>
							<td>투자의견</td>
							<td>PER</td>
							<td>CAGR</td>
							<td>사업장명</td>
							<td>전일종가</td>
							<td>평균단가</td>
							<td>보유수량</td>
							<td>수정일</td>
							<td>최근공시명</td>
							<td>공시</td>
							<td>정보</td>
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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>