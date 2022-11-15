<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	var toggleChk = true;
	$(document).ready(function(){
	
	});
	
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
	            <div class="chart-pie pt-4 pb-2"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
	                <canvas id="myPieChart" width="470" height="245" style="display: block; width: 470px; height: 245px;" class="chartjs-render-monitor"></canvas>
	            </div>
	            <div class="mt-4 text-center small">
	                <span class="mr-2">
	                    <i class="fas fa-circle text-primary"></i> 예치금
	                </span>
	                <span class="mr-2">
	                    <i class="fas fa-circle text-success"></i> 투자금
	                </span>
	                <span class="mr-2">
	                    <i class="fas fa-circle text-info"></i> 예비금
	                </span>
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