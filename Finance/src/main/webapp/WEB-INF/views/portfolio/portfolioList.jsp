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
			lengthMenu: [ 12, 18, 24, 30, 36 ]
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
							<!-- <td>상세</td> -->
							<td>메모</td>
						</tr>
			        </thead>
			        <tbody id="bodyList">
			        	<c:forEach items="${getPortCorpList}" var="ls" >
				        	<tr>
				        		<td><input type="checkbox" name="del_chk" id="del_chk" value="${ls.CORP_CODE}" title="삭제 체크박스"></td>
				        		<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
				        			<span 
				        				<c:if test="${ls.INVEST_OPINION == '매수'}">style='color: red;'</c:if>
										<c:if test="${ls.INVEST_OPINION == '비중확대'}">style='color: palevioletred;'</c:if>
										<c:if test="${ls.INVEST_OPINION == '증립'}">style='color: black;'</c:if>
										<c:if test="${ls.INVEST_OPINION == '비중축소'}">style='color: cornflowerblue;'</c:if>
										<c:if test="${ls.INVEST_OPINION == '매도'}">style='color: blue;'</c:if>			        			
				        			>
				        				<c:out value="${ls.INVEST_OPINION}"/>
				        				( ~ <c:out value="${ls.INVEST_OPINION_AMOUNT}"/>)
				        			</span>
				        		</td>
				        		
				        		<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
				        			<span 
				        				<fmt:parseNumber value="${ls.CURRENT_PER}" var="CURRENT_PER"/>
				        				<fmt:parseNumber value="${ls.AVR_PER}" var="AVR_PER"/>
				        				<c:if test="${CURRENT_PER > AVR_PER}">style='color: blue;'</c:if>
										<c:if test="${CURRENT_PER == AVR_PER}">style='color: black;'</c:if>
										<c:if test="${CURRENT_PER < AVR_PER}">style='color: red;'</c:if>
				        			>
				        				<c:out value="${ls.CURRENT_PER}"/>
				        			</span> / <c:out value="${ls.AVR_PER}"/>
				        		</td>
								<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
									<span 
										<fmt:parseNumber value="${ls.ESTIMATE_CAGR}" var="ESTIMATE_CAGR"/>
				        				<c:if test="${ESTIMATE_CAGR >= 15}">style='color: red;'</c:if>
										<c:if test="${15 > ESTIMATE_CAGR && ESTIMATE_CAGR >= 5}">style='color: palevioletred;'</c:if>
										<c:if test="${5 > ESTIMATE_CAGR && ESTIMATE_CAGR > 0 }">style='color: cornflowerblue;'</c:if>
										<c:if test="${ESTIMATE_CAGR <= 0}">style='color: black;'</c:if>
				        			>
				        				<c:out value="${ls.ESTIMATE_CAGR}"/>
				        			</span>
								</td>
								<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
									<c:out value="${ls.CORP_NAME}"/>
								</td>	
								<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
									<c:out value="${ls.BEF_CLS_PRICE}"/>
								</td>	
								<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
				        			<span
				        				<fmt:parseNumber value="${ls.AVR_PRICE}" var="AVR_PRICE"/> 
				        				<fmt:parseNumber value="${ls.BEF_CLS_PRICE}" var="BEF_CLS_PRICE"/> 
				        				<c:if test="${AVR_PRICE < BEF_CLS_PRICE}">style='color: palevioletred;'</c:if>
										<c:if test="${AVR_PRICE >= BEF_CLS_PRICE}">style='color: cornflowerblue;'</c:if>
				        			>
				        				<c:out value="${ls.AVR_PRICE}"/>
				        			</span>
				        		</td>
				        		<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;" >
									<c:out value="${ls.HOLD_QUANTITY}"/>
								</td>
								<td onclick="goPortfolioDetail('${ls.CORP_CODE}'); return false;" style="cursor: pointer;"  >
									<c:out value="${ls.CHANGE_DT}"/>
								</td>
								<td>
									<a href="#" onclick="openReportPop('${ls.REPRT_NO}','${ls.REPRT_NM}'); return false;" ><c:out value="${ls.REPRT_NM}"/></a>
								</td>
								<td>
									<a href="#" onclick="openReportList('${ls.CORP_CODE}','${ls.CORP_NAME}'); return false;" class="btn btn-light btn-icon-split"><span class="icon text-gray-600"><i class="fas fa-flag"></i></span></a>
								</td>
								<td>
									<a href="#" onclick="openNaverFinancePop('${ls.STOCK_CODE}'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>
								</td>
								<td>
									<a href="#" onclick="openMemoPop('${ls.CORP_CODE}'); return false;" 
										<c:if test="${!empty ls.MEMO}">style="border-color: #FFD232; background: #FFD232;"</c:if> 
										<c:if test="${!empty ls.MEMO}">style="border-color: #aeafb9; background: #aeafb9;"</c:if>
									class="btn btn-info btn-circle btn-sm"><i class="fas fa-clipboard-list"></i></a>
								</td>	
							</tr>
						</c:forEach>
			        </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>