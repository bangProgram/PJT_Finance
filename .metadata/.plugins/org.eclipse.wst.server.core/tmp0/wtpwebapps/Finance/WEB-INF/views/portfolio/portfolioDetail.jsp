<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
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
		
		
	});
	
	function portDetailSave(mode){
		var frm = document.searchForm;
		var gubn = $("#gubn").val();
		
		$("#mode").val(mode);
		
		if(gubn == "0101"){
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			var TRADE_AMOUNT = $("#TRADE_AMOUNT").val();
			$("#BUY_QUANTITY").val(TRADE_QUANTITY);
			$("#BUY_AMOUNT").val(TRADE_AMOUNT);
			
			calAvrPrice(gubn);
		}else{
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			var TRADE_AMOUNT = $("#TRADE_AMOUNT").val();
			$("#SELL_QUANTITY").val(TRADE_QUANTITY);
			$("#SELL_AMOUNT").val(TRADE_AMOUNT);
			
			calAvrPrice(gubn);
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
			var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
			
			var calValue = TRADE_PRICE * TRADE_QUANTITY
			$("#TRADE_AMOUNT").val(calValue);
			
			
			if(gubn == "0102"){
				var calReturn = (TRADE_PRICE - AVR_PRICE) * TRADE_QUANTITY
				$("#RETURN_AMOUNT").val(calReturn);
			}
		}else{
			alert("거래구분 먼저 선택해주세요");
			return;
		}
	}
	
	function calAvrPrice(gubn){
		var AVR_PRICE =  $("#AVR_PRICE").val();
		var HOLD_QUANTITY =  $("#HOLD_QUANTITY").val();
		var TRADE_PRICE = $("#TRADE_PRICE").val();
		var TRADE_QUANTITY = $("#TRADE_QUANTITY").val();
		
		if(gubn == "0101"){
			var sumAmount = ((AVR_PRICE * HOLD_QUANTITY) + (TRADE_PRICE * TRADE_QUANTITY)) 
			var sumQuantity = (HOLD_QUANTITY + TRADE_QUANTITY)
			
			$("#AVR_PRICE").val(sumAmount/sumQuantity);
			$("#HOLD_QUANTITY").val(sumQuantity);
		}else{
			var sumQuantity = (HOLD_QUANTITY - TRADE_QUANTITY)
			
			$("#HOLD_QUANTITY").val(sumQuantity);
		}
		
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
<form name="searchForm" method="post">
<div class="container-fluid">
	<input type="hidden" name="mode" id="mode" value="" title="수정 FLAG">
	<input type="hidden" name="CORP_CODE" id="CORP_CODE" value="<c:out value='${resultData.CORP_CODE }'/>" title="현재보고있는 기업 코드">
	<input type="hidden" name="AVR_PRICE" id="AVR_PRICE" value="<c:out value='${resultData.AVR_PRICE}'/>" title="현재보고있는 기업 평균단가"/>
	<input type="hidden" name="HOLD_QUANTITY" id="HOLD_QUANTITY" value="<c:out value='${resultData.HOLD_QUANTITY }'/>" title="현재보고있는 기업 보유 수량">
	
	<input type="hidden" name="SEQs" id="SEQs" value="" title="삭제대상 상세 SEQ(복수)">
    
</div>

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
		            <div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
		                <canvas id="myPieChart" width="470" height="245" style="display: block; width: 470px; height: 245px;" class="chartjs-render-monitor"></canvas>
		            </div>
		            <div class="mt-4 text-center small">
		                <span class="mr-2">
		                    <i class="fas fa-circle text-info"></i> 예수금
		                </span>
		                <span class="mr-2">
		                    <i class="fas fa-circle text-warning"></i> 예비금
		                </span>
		                <span class="mr-2">
		                    <i class="fas fa-circle text-secondary"></i> 타사 투자금
		                </span>
		                <span class="mr-2">
		                    <i class="fas fa-circle text-success"></i> 당사 투자금
		                </span>
		            </div>
		        </div>
		    </div>
		</div>
	
		<!-- Area Chart -->
	    <div class="col-xl-2 col-lg-5">
	        <div class="card shadow mb-4">
				<div class="card border-left-primary shadow h-100 py-2">
	            	<div class="card-body">
	                	<div class="row no-gutters align-items-center">
	                    	<div class="col mr-2">
	                        	<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
	                              추정 EPS</div>
								<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.ESTIMATE_EPS}"/></div>
	                      	</div>
	                      	<div class="col-auto">
	                          	<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	          	<div class="card border-left-info shadow h-100 py-2">
	              	<div class="card-body">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 매출 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_REVENUE_GROWTH}"/></div>
	                      	</div>
	                      	<div class="col-auto">
	                          	<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	        	<div class="card border-left-info shadow h-100 py-2">
	              	<div class="card-body">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 영업이익 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_OPERAT_GROWTH}"/></div>
	                      	</div>
	                      	<div class="col-auto">
	                          	<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	          	<div class="card border-left-info shadow h-100 py-2">
	              	<div class="card-body">
	                  	<div class="row no-gutters align-items-center">
	                      	<div class="col mr-2">
	                          	<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
	                              	평균 순이익 성장률</div>
	                          	<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value=" ${resultData.AVG_PROFIT_GROWTH}"/></div>
	                      	</div>
	                      	<div class="col-auto">
	                          	<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
	                      	</div>
	                  	</div>
	              	</div>
	          	</div>
	        </div>
		</div>
		<!-- MEMO Area -->
	    <div class="col-xl-6 col-lg-5">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Development Approach</h6>
				</div>
				<div class="card-body">
					<textarea  rows="26" cols="80"><c:out value="${resultData.MEMO }"/></textarea>
				</div>
			</div>
		</div>
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
						<tr id="inputTr">
							<td></td>
							<td>
								<select name="gubn" id="gubn" title="포트폴리오 상세 구분">
									<option value="">-선택-</option>
									<option value="0101">매수</option>
									<option value="0102">매도</option>
								</select>
							</td>
						    <td><input type="text" name="TRADE_DATE" id="TRADE_DATE" value="${curDate}" title="포트폴리오 거래일자" style="font-size: 14px; width:100%" /></td>
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
			        </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>