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
			<c:out value=" ${portAmount.DIFF_INVEST_AMOUNT}"/>,
			<c:out value=" ${portAmount.CORP_INVEST_AMOUNT}"/>,
		];
		
		const ctx = document.getElementById('myPieChart');
		
		new Chart(ctx, {
			  type: 'pie',
			  
			  data: {
				  labels: ['예수금', '예비금','타사투자금', '당사투자금'],
			    datasets: [{
			    	labels: ['예수금', '예비금','타사투자금', '당사투자금'],
			      data: datas,
			    }]
			  },
			  options: {
					maintainAspectRatio: false,
				},
			});
	});
	
	$(document).ready(function(){
		$("#gubn").change(function(){
			if(this.value == "0101"){
				$("#inputTr").css("background-color","#ffc4c4");
			}else if(this.value == "0102"){
				$("#inputTr").css("background-color","#cad8ff");
			}else{
				$("#inputTr").css("background-color","");
			}
			
		});
		
		var curPer = '<c:out value="${resultData.CURRENT_PER }"/>';
		var avrPer = '<c:out value="${resultData.AVR_PER }"/>';
		
		curPer = curPer * 1;
		avrPer = avrPer * 1;
		
		if(curPer > avrPer){
			$("#perSpan").css("color","blue");
		}else if(curPer == avrPer){
			$("#perSpan").css("color","black");
		}else{
			$("#perSpan").css("color","red");
		}
	});
	

	function dateformat(date){
		var length = date.length;
		if(length == 8){
			var year = date.substr(0, 4);
			var month = date.substr(4, 2);
			var day = date.substr(6, 2);
			
			var formDate = year+"-"+month+"-"+day
			
			$("#TRADE_DATE").val(formDate);
		}
	}
	
	function portDetailSave(mode){
		var frm = document.searchForm;
		var gubn = $("#gubn").val();
		
		$("#mode").val(mode);
		
		if(gubn == "0101"){
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			var TRADE_AMOUNT = $("#TRADE_AMOUNT").val();
			$("#BUY_QUANTITY").val(TRADE_QUANTITY);
			$("#BUY_AMOUNT").val(TRADE_AMOUNT);
		}else{
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			var TRADE_AMOUNT = $("#TRADE_AMOUNT").val();
			$("#SELL_QUANTITY").val(TRADE_QUANTITY);
			$("#SELL_AMOUNT").val(TRADE_AMOUNT);
		}
		
		if(mode == 'del'){
			var i = 0;
			var delChks = '';
			$("input[name=del]:checked").each(function(){
				if(i==0){
					delChks += $(this).val();
				}else{
					delChks += ","+$(this).val();
				}
				i++;
			});
			$("#delSEQs").val(delChks);
		}
		
		$("#TRADE_AMOUNT").attr("disabled",false);
		$("#RETURN_AMOUNT").attr("disabled",false);
		frm.action = "/portfolio/detail/cud";
		frm.submit();
	}
	
	function calTradeAmount(){
		var gubn = $("#gubn").val();
		if(gubn != "" && gubn != null){
			var TRADE_PRICE = $("#TRADE_PRICE").val();
			var AVR_PRICE = $("#CAL_AVR_PRICE").val();
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			
			var calValue = TRADE_PRICE * TRADE_QUANTITY
			$("#TRADE_AMOUNT").val(calValue);
			
			
			if(gubn == "0102"){
				var calReturn = (TRADE_PRICE - AVR_PRICE) * TRADE_QUANTITY;
				$("#RETURN_AMOUNT").val(calReturn);
			}
		}else{
			alert("거래구분 먼저 선택해주세요");
			return;
		}
	}
	
	function updatePortfolio(mode){
		var url = "/portfolio/update/ajax";
		var allData = {};
		
		$('#infoDiv').css("animation-name","");
		$('#epsDiv').css("animation-name","");
		$('#memoDiv').css("animation-name","");
		
		if(mode == 'opinion'){
			allData = { 
					CORP_CODE 	: $("#CORP_CODE").val(),
					OPINION_AMOUNT1 	: $("#OPINION_AMOUNT1").val(),
					OPINION_AMOUNT2		: $("#OPINION_AMOUNT2").val(),
					OPINION_AMOUNT3 	: $("#OPINION_AMOUNT3").val(),
					OPINION_AMOUNT4		: $("#OPINION_AMOUNT4").val(),
					OPINION_AMOUNT5 	: $("#OPINION_AMOUNT5").val() 
			};
		}else if(mode == 'memo'){
			allData = { 
					CORP_CODE 	: $("#CORP_CODE").val(),
					MEMO 	: $("#corpMemo").val() 
			};
		}else if(mode == 'eps'){
			allData = { 
					CORP_CODE 	: $("#CORP_CODE").val(),
					ESTIMATE_EPS 	: $("#ESTIMATE_EPS").val() 
			};
		}else if(mode == 'cagr'){
			allData = { 
				CORP_CODE 	: $("#CORP_CODE").val(),
				ESTIMATE_CAGR 	: $("#ESTIMATE_CAGR").val() 
		};
		}else if(mode == 'per'){
			allData = { 
					CORP_CODE 	: $("#CORP_CODE").val(),
					AVR_PER 	: $("#AVR_PER").val() 
			};
		}else{
			return;
		}
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : url,           // 요청할 서버url    
			data : allData,    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수
				var resultData = data.resultData;
				if(mode == 'opinion'|| mode == 'per'){
					$('#infoDiv').css("animation-name","add");
					$('#infoDiv').css("animation-duration","2.5s");
				}else if(mode == 'memo'){
					$('#memoDiv').css("animation-name","add");
					$('#memoDiv').css("animation-duration","2.5s");
				}else if(mode == 'eps' ){
					$('#epsDiv').css("animation-name","add");
					$('#epsDiv').css("animation-duration","2.5s");
				}else if( mode == 'cagr' ){
					$('#cagrDiv').css("animation-name","add");
					$('#cagrDiv').css("animation-duration","2.5s");
				}
				
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
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

.clearfix {*zoom:1;}
.clearfix:before, .clearfix:after {display:block; content: ''; line-height: 0;}
.clearfix:after {clear: both;}

</style>
<html>
<head>
<title>사업장 목록</title>
</head>
<body>

<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800"><c:out value="${resultData.CORP_NAME }"/> 포트폴리오 상세</h1>
	<p class="mb-4"><c:out value="${resultData.CORP_NAME }"/> 에 대한 거래내역과 비중을 확인 할 수 있다. 
		<span id=""></span>
	</p>
	
	
	<div class="row">
         <!-- Pie Chart -->
		<div class="col-xl-4 col-lg-5">
			<div class="card shadow mb-4" id="cardDiv">
		        <!-- Card Header - Dropdown -->
		        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
		            <h6 class="m-0 font-weight-bold text-primary">포트폴리오 자산 분배비율</h6>
		        </div>
		        <!-- Card Body -->
		        <div class="card-body">
		            <div class="chart-container" style="position: relative; height:320px;">
						<canvas id="myPieChart"></canvas>
					</div>
		        </div>
		    </div>
		</div>
	
		<!-- Area Chart -->
	    <div class="col-xl-2 col-lg-5">
	        <div class="card shadow mb-4">
				<div class="card border-left-primary shadow h-100 py-2" id="epsDiv">
	            	<div class="card-body" style="padding: 10px 15px 10px 15px;">
	                	<div class="row no-gutters align-items-center">
	                    	<div class="col mr-2">
	                        	<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
	                              추정 EPS</div>
								<div class="h5 mb-0 font-weight-bold text-gray-800" style="display: -webkit-inline-box;">
									<c:out value=" ${resultData.ESTIMATE_EPS}"/>
									<div class="dropdown no-arrow" style="right: -45px; font-size: 17px;">
						                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
						                </a>
						                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
						                    <div class="dropdown-header" style="font-size: 11px;">
						                    	- 추정 EPS 등록 -
						                    	<button class="btn btn-outline-primary" style="height: 29px; font-size: 11px; width: 50px; margin-left: 30px;" id="btn_DEPOSIT_AMOUNT" onclick="updatePortfolio('eps'); return false;">
								                    	등록
								                </button>
						                    </div>
						                    <div class="dropdown-divider"></div>
						                    <div style="padding: 5px 10px 5px 10px;">
						                    <form name="opinionForm">
							                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px; text-align: right;">
								                    EPS :
								                    <input type="text" name="ESTIMATE_EPS" id="ESTIMATE_EPS" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.ESTIMATE_EPS}"/>' title="추정 EPS" placeholder="추정 EPS">
							                    </div>
							                    <div class="dropdown-divider"></div>
							                </form>
						                    </div>
						                </div>
					            	</div>
								</div>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	          	<div class="card border-left-success shadow h-100 py-2" id="cagrDiv">
	              	<div class="card-body" style="padding: 10px 15px 10px 15px;">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                        	<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
	                              CAGR </div>
								<div class="h5 mb-0 font-weight-bold text-gray-800" style="display: -webkit-inline-box;">
									<c:out value="${resultData.ESTIMATE_CAGR}"/>
									<div class="dropdown no-arrow" style="right: -45px; font-size: 17px;">
						                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
						                </a>
						                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
						                    <div class="dropdown-header" style="font-size: 11px;">
						                    	- 추정 CAGR 등록 -
						                    	<button class="btn btn-outline-primary" style="height: 29px; font-size: 11px; width: 50px; margin-left: 30px;" id="btn_DEPOSIT_AMOUNT" onclick="updatePortfolio('cagr'); return false;">
								                    	등록
								                </button>
						                    </div>
						                    <div class="dropdown-divider"></div>
						                    <div style="padding: 5px 10px 5px 10px;">
						                    <form name="opinionForm">
							                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px; text-align: right;">
								                    CAGR :
								                    <input type="text" name="ESTIMATE_CAGR" id="ESTIMATE_CAGR" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.ESTIMATE_CAGR}"/>' title="추정 연 평균 성장률" placeholder="추정 연 성장률">
							                    </div>
							                    <div class="dropdown-divider"></div>
							                </form>
						                    </div>
						                </div>
					            	</div>
								</div>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	          	<div class="card border-left-warning shadow h-100 py-2">
	              	<div class="card-body" style="padding: 10px 15px 10px 15px;">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 매출 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_REVENUE_GROWTH}"/></div>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	        	<div class="card border-left-warning shadow h-100 py-2">
	              	<div class="card-body" style="padding: 10px 15px 10px 15px;">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 영업이익 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_OPERAT_GROWTH}"/></div>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	          	<div class="card border-left-warning shadow h-100 py-2">
	              	<div class="card-body" style="padding: 10px 15px 10px 15px;">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 순이익 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_PROFIT_GROWTH}"/></div>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	        </div>
		</div>
		<!-- Info Area -->
	    <div class="col-xl-2 col-lg-5">
			<div class="card shadow mb-4">
				<div class="card-header py-3" id="infoDiv" >
					<h6 class="m-0 font-weight-bold text-primary"><c:out value="${resultData.CORP_NAME }"/> 정보</h6>
				</div>
				<div class="card-body">
					<div class="dropdown-header" style="font-size: 15px; padding: 8px 0px 8px 0px;">
                    	주식 수량 :  <span style="color: black;"><c:out value="${resultData.SHARES_AMOUNT}"/></span>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-header" style="font-size: 15px; padding: 8px 0px 8px 0px;">
                    	전일 종가 : <span style="color: black;"><c:out value="${resultData.BEF_CLS_PRICE}"/></span>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-header"style="font-size: 15px; padding: 8px 0px 8px 0px; display: -webkit-inline-box;">
                    	의견 : 
                    	<span 
                    		<c:if test="${resultData.INVEST_OPINION == '매수'}">style="color: red;"</c:if>
                    		<c:if test="${resultData.INVEST_OPINION == '비중확대'}">style="color: palevioletred;"</c:if>
                    		<c:if test="${resultData.INVEST_OPINION == '중립'}">style="color: black;"</c:if>
                    		<c:if test="${resultData.INVEST_OPINION == '비중축소'}">style="color: cornflowerblue;"</c:if>
                    		<c:if test="${resultData.INVEST_OPINION == '매도'}">style="color: blue;"</c:if>
                    	>
                    			<c:out value="${resultData.INVEST_OPINION}"/>
                    			(~<c:out value="${resultData.INVEST_OPINION_AMOUNT }"/>)
                    	</span>
                    	<div class="dropdown no-arrow" style="right: -10px;">
			                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
			                </a>
			                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
			                    <div class="dropdown-header" style="font-size: 11px;">
			                    	- 투자의견 금액 등록 -
			                    	<button class="btn btn-outline-primary" style="height: 29px; font-size: 11px; width: 50px; margin-left: 30px;" id="btn_DEPOSIT_AMOUNT" onclick="updatePortfolio('opinion'); return false;">
					                    	등록
					                </button>
			                    </div>
			                    <div class="dropdown-divider"></div>
			                    <div style="padding: 5px 10px 5px 10px;">
			                    <form name="opinionForm">
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px; text-align: right;">
					                    매수 :
					                    <input type="text" name="OPINION_AMOUNT1" id="OPINION_AMOUNT1" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.OPINION_AMOUNT1}"/>' title="예수금" placeholder="매수 per">
				                    </div>
				                    <div class="dropdown-divider"></div>
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;  text-align: right;">
					                    비중확대 : 
					                    <input type="text" name="OPINION_AMOUNT2" id="OPINION_AMOUNT2" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.OPINION_AMOUNT2}"/>' title="예수금" placeholder="비중확대 per"> 
				                    </div>
				                    <div class="dropdown-divider"></div>
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;  text-align: right;">
					                    중립 : 
					                    <input type="text" name="OPINION_AMOUNT3" id="OPINION_AMOUNT3" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.OPINION_AMOUNT3}"/>' title="예수금" placeholder="중립 per"> 
				                    </div>
				                    <div class="dropdown-divider"></div>
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;  text-align: right;">
					                    비중축소 : 
					                    <input type="text" name="OPINION_AMOUNT4" id="OPINION_AMOUNT4" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.OPINION_AMOUNT4}"/>' title="예수금" placeholder="비중축소 per"> 
				                    </div>
				                    <div class="dropdown-divider"></div>
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px;  text-align: right;">
					                    매도 : 
					                    <input type="text" name="OPINION_AMOUNT5" id="OPINION_AMOUNT5" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.OPINION_AMOUNT5}"/>' title="예수금" placeholder="매도 per"> 
				                    </div>
				                    <div class="dropdown-divider"></div>
				                </form>
			                    </div>
			                </div>
		            	</div>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-header" style="font-size: 15px; padding: 8px 0px 8px 0px; display: -webkit-inline-box;">
                    	PER : <span id="perSpan"><c:out value="${resultData.CURRENT_PER }"/></span> / <span style="color: black;"><c:out value="${resultData.AVR_PER }"/></span>
                    	<div class="dropdown no-arrow" style="right: -10px;">
			                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
			                </a>
			                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
			                    <div class="dropdown-header" style="font-size: 11px;">
			                    	- 평균 PER -
			                    	<button class="btn btn-outline-primary" style="height: 29px; font-size: 11px; width: 50px; margin-left: 30px;" id="btn_DEPOSIT_AMOUNT" onclick="updatePortfolio('per'); return false;">
					                    	등록
					                </button>
			                    </div>
			                    <div class="dropdown-divider"></div>
			                    <div style="padding: 5px 10px 5px 10px;">
			                    <form name="opinionForm">
				                    <div class="dropdown-item" style="padding: 10px 10px 10px 10px; text-align: right;">
					                    평균 PER :
					                    <input type="text" name="AVR_PER" id="AVR_PER" style="width : 120px; margin-left: 15px;" value='<c:out value="${resultData.AVR_PER}"/>' title="평균 PER" placeholder="평균 PER">
				                    </div>
				                    <div class="dropdown-divider"></div>
				                </form>
			                    </div>
			                </div>
		            	</div>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-header" style="font-size: 15px; padding: 8px 0px 8px 0px;">
                    	보유 수량 : <span style="color: black;"><c:out value="${resultData.HOLD_QUANTITY}"/></span>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-header" style="font-size: 15px; padding: 8px 0px 11px 0px;">
                    	평균 단가 : <span style="color: black;"><c:out value="${resultData.AVR_PRICE}"/></span>
                    </div>
				</div>
			</div>
		</div>
		<!-- MEMO Area -->
	    <div class="col-xl-4 col-lg-5" >
			<div class="card shadow mb-4">
				<div class="card-header py-3" id="memoDiv" style="display: table;">
		            <h6 class="m-0 font-weight-bold text-primary" style="display: table-cell; width: 95%; padding-left: 10px;"><c:out value="${resultData.CORP_NAME }"/> 메모장</h6>
		            <div >
						<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" style="width: 60px; font-size: 12px;" tabindex="0" aria-controls="portCorpList" onclick="updatePortfolio('memo'); return false;">
							<span>
								<c:if test="${resultData.MEMO != null}">수정</c:if>
								<c:if test="${resultData.MEMO == null}">등록</c:if>
							</span>
						</button>                	
					</div>
		        </div>
				<div class="card-body">
					<textarea  rows="25" cols="85" id="corpMemo" style="font-size: 13px; height: 299px; max-width: 100%;"><c:out value="${resultData.MEMO }"/></textarea>
				</div>
			</div>
		</div>
	</div>
	
	<form name="searchForm" method="post">
		<div class="container-fluid">
			<input type="hidden" name="mode" id="mode" value="" title="수정 FLAG">
			<input type="hidden" name="CORP_CODE" id="CORP_CODE" value="<c:out value='${resultData.CORP_CODE }'/>" title="현재보고있는 기업 코드">
			<input type="hidden" name="AVR_PRICE" id="AVR_PRICE" value="<c:out value='${resultData.AVR_PRICE}'/>" title="현재보고있는 기업 평균단가"/>
			<input type="hidden" name="CAL_AVR_PRICE" id="CAL_AVR_PRICE" value="<c:out value='${resultData.CAL_AVR_PRICE}'/>" title="현재보고있는 기업 평균단가 계산용"/>
			<input type="hidden" name="HOLD_QUANTITY" id="HOLD_QUANTITY" value="<c:out value='${resultData.HOLD_QUANTITY }'/>" title="현재보고있는 기업 보유 수량">
			
			<input type="hidden" name="delSEQs" id="delSEQs" value="" title="삭제대상 상세 SEQ(복수)">
		    
		</div>
		
	<!-- DataTales Example -->
	    <div class="card shadow mb-4">
	        <div class="card-header py-3" id="dataTables" style="display: table;">
	            <h6 class="m-0 font-weight-bold text-primary" style="display: table-cell; width: 91%; padding-left: 10px;">포트폴리오 기업목록</h6>
	            <div >
					<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" style="width: 55px; font-size: 12px;" tabindex="0" aria-controls="portCorpList" type="submit" onclick="portDetailSave('add'); return false;">
						<span>등록</span>
					</button>
					<button class="dt-button buttons-excel buttons-html5 btn btn-outline-primary excelBtn" style="width: 55px; font-size: 12px;" tabindex="0" aria-controls="portCorpList" type="submit" onclick="portDetailSave('del'); return false;">
						<span>삭제</span>
					</button>   
				</div>
	        </div>
	        <div class="card-body">
	            <div class="table-responsive" >
	                <table id="portCorpList" class="table table-bordered" id="dataTable"  style="font-size: 14px; width:100%" >
	                	<colgroup>
	                		<col width="3%"/>
	                		<col width="6%"/>
	                		<col width="10%"/>
	                		<col width="10%"/>
	                		<col width="6%"/>
	                		<col width="12%"/>
	                		<col width="10%"/>
	                		<col width="43%"/>
	                	</colgroup>
	                    <thead id="tableHead">
				            <tr>
				            	<td></td>
				            	<td>거래구분</td>
								<td>거래일자</td>
								<td>거래단가</td>
								<td>거래수량</td>
								<td>거래금액</td>
								<td>매매손익</td>
								<td>메모</td>
							</tr>
				        </thead>
				        <tbody id="bodyList">
				        	<tr id="inputTr">
								<td></td>
								<td>
									<select name="gubn" id="gubn" title="포트폴리오 상세 구분">
										<option value="">-선택-</option>
										<option value="0101">매수</option>
										<option value="0102">매도</option>
									</select>
								</td>
							    <td><input type="text" name="TRADE_DATE" onchange="dateformat(this.value); return false;"  id="TRADE_DATE" value="${curDate}" title="포트폴리오 거래일자" style="font-size: 14px; width:100%" /></td>
							    <td>
							    	<input type="text" name="TRADE_PRICE" onchange="calTradeAmount(); return false;" id="TRADE_PRICE" value="" placeholder="0" title="포트폴리오 거래단가" style="font-size: 14px; width:100%" />
							    </td>
								<td>
									<input type="text" name="TRADE_QUANTITY" onchange="calTradeAmount(); return false;" id="TRADE_QUANTITY" value="" placeholder="0" title="포트폴리오 거래수량" style="font-size: 14px; width:100%" />
									<input type="hidden" name="BUY_QUANTITY" id="BUY_QUANTITY" value="" title="매수 거래수량"/>
									<input type="hidden" name="SELL_QUANTITY" id="SELL_QUANTITY" value="" title="매도 거래수량"/>
								</td>
								<td>
									<input type="text" name="TRADE_AMOUNT" id="TRADE_AMOUNT" value="0" title="포트폴리오 거래금액" disabled="disabled" style="font-size: 14px; width:100%"/>
									<input type="hidden" name="BUY_AMOUNT" id="BUY_AMOUNT" value="" title="매수 거래금액"/>
									<input type="hidden" name="SELL_AMOUNT" id="SELL_AMOUNT" value="" title="매도 거래금액"/>
								</td>
								<td><input type="text" name="RETURN_AMOUNT" id="RETURN_AMOUNT" value="0" title="포트폴리오 매매손익" disabled="disabled" style="font-size: 14px; width:100%"/></td>
								<td><input type="text" name="MEMO" id="MEMO" value="" title="포트폴리오 거래 사유" style="font-size: 14px; width:100%"/></td>
							</tr>
							<c:forEach items="${resultList}" var="ls" >
								<c:if test="${ls.GUBN == '0101'}"><tr style="background-color: #ffc4c4;"></c:if>
								<c:if test="${ls.GUBN == '0102'}"><tr style="background-color: #cad8ff;"></c:if>
									<td><input type="checkbox" name="del" value="${ls.SEQ}" title="포트폴리오 상세 SEQ"/></td>
									<td><c:out value="${ls.GUBN_NM}"/></td>
									<td><c:out value="${ls.TRADE_DATE}"/></td>
									<td><c:out value="${ls.TRADE_PRICE}"/></td>
									<td>
										<c:if test="${ls.GUBN == '0101'}"><c:out value="${ls.BUY_QUANTITY}"/></c:if>
										<c:if test="${ls.GUBN == '0102'}"><c:out value="${ls.SELL_QUANTITY}"/></c:if>
									</td>
									<td>
										<c:if test="${ls.GUBN == '0101'}"><c:out value="${ls.BUY_AMOUNT}"/></c:if>
										<c:if test="${ls.GUBN == '0102'}"><c:out value="${ls.SELL_AMOUNT}"/></c:if>
									</td>
									<td><c:out value="${ls.RETURN_AMOUNT}"/></td>
									<td><c:out value="${ls.MEMO}"/></td>
								</tr>
							</c:forEach>
				        </tbody>
	                </table>
	            </div>
	        </div>
	    </div>
	</form>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script type="text/javascript">

</script>
</body>
</html>