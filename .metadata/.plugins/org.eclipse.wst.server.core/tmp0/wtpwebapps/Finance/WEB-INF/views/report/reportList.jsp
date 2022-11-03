<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>﻿
<script type="text/javascript">
	$(document).ready(function(){
		
		var chkYear = pEndYear;
		var yearString = $("#pYearList").val();
		var pYearList = yearString.split(',');
		var quaterString = $("#pQuaterList").val();
		var pQuaterList = quaterString.split(',');
		
		var pEndYear = $("#pEndYear").val();
		
		var html = ''
		var chkYearList = ''
		for(var i=0; i<5; i++){
			if(i==0){
				chkYearList += pYearList[i];
				html += '<option value="'+chkYearList+'">'+ pYearList[i] +' </option>'
			}else{
				chkYearList += ','+pYearList[i];
				html += '<option value="'+chkYearList+'">'+ (pEndYear-1)+' ~ '+ pYearList[i]  +' </option>'
			}
		}
		$('#chkYearList').append(html);
		
		$("input[name=pReportCd]").change(function(){
			$('#chkYearList').empty();
			var reprtCd = $(this).val();
			html = '<option value = "">개년 선택</option>'
			chkYearList = '';
			if(reprtCd == "11011"){
				for(var i=0; i<5; i++){
					if(i==1){
						chkYearList += pYearList[i];
						html += '<option value="'+chkYearList+'">'+ pYearList[i] +' </option>'
					}else{
						chkYearList += ','+pYearList[i];
						html += '<option value="'+chkYearList+'">'+ pEndYear+' ~ '+ pYearList[i]  +' </option>'
					}
				}
				$('#chkYearList').append(html);
			}else{
				for(var i=0; i<5; i++){
					if(i==0){
						chkYearList += pQuaterList[i];
						html += '<option value="'+chkYearList+'">'+ pQuaterList[i] +' </option>'
					}else{
						chkYearList += ','+pQuaterList[i];
						html += '<option value="'+chkYearList+'">'+ pQuaterList[0]+' ~ '+ pQuaterList[i]  +' </option>'
					}
				}
				$('#chkYearList').append(html);
			}
		});
		
	});

	function goSearch(){
		var frm = document.searchForm;
		var pAccountIds = [];
		$("input[name=pAccountId]:checked").each(function(){
			pAccountIds.push($(this).val());
		});
		$("#pAccountIds").val(pAccountIds);
		
		var reprtCd = $("input[name=pReportCd]:checked").val();
		if(reprtCd == '11011'){
			$("#yearHaeder").css('display', 'table-row');
			$("#quaterHaeder").css('display', 'none');
		}else{
			$("#yearHaeder").css('display', 'none');
			$("#quaterHaeder").css('display', 'table-row');
		}
		
		$.ajax({    
			type : 'post',           // 타입 (get, post, put 등등)    
			url : '/report/select',           // 요청할 서버url    
			data : {
				pYearList : $("#pYearList").val(),
				pQuaterList : $("#pQuaterList").val(),
				pStartYear : $("#pStartYear").val(),
				pEndYear : $("#pEndYear").val(),
				pCorpName : $("#pCorpName").val(),
				pReportCd : reprtCd,
				chkAccRate : $("#chkAccRate").val(),
				chkYearList : $("#chkYearList").val(),
				pAccountIds : $("#pAccountIds").val()
			},    
			dataType : 'json',    
			success : function(data) { // 결과 성공 콜백함수        
				var resultList = data.resultList;
				
				 $("#reprtList").dataTable({
					lengthMenu: [ 12, 18, 24, 30, 36 ],
				 	data: resultList,
				 	destroy: true,
				 	columns: [
				  		{ data: 'RNUM' },
				  		{ data: 'CORP_NAME' },
				  		{ data: 'ACCOUNT_NM' },
				  		{ "data": 'RATE_0', 
				  			"render": function(data, type, row, meta){
				  				var index = row.SEQ;
				  				console.log("JB : "+index);
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
				  				console.log("JB : "+index);
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
				  				console.log("JB : "+index);
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
				  				console.log("JB : "+index);
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
				  				console.log("JB : "+index);
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
				  	                data = '<a href="/report/detail/list" target="_blank" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
				  	            }

				  	            return data;
				  	         }	
				  		},
				  		{ "data": '',
				  			"defaultContent" : '',
				  			"render": function(data, type, row, meta){
				  	            if(type === 'display'){
				  	            	//console.log('row : '+row.CORP_CODE+" / "+row.CORP_NAME);
				  	                data = '<a href="" onclick="addInterest(\''+row.CORP_CODE+'\',\''+row.CORP_NAME+'\',\''+row.STOCK_CODE+'\'); return false;" class="btn btn-success btn-circle btn-sm"><i class="fas fa-check"></i></a>';
				  	            }

				  	            return data;
				  	         }	 }
				  	]
				});
					
				$('#dataTables').css("animation-name","search");
				$('#dataTables').css("animation-duration","2.5s");
				
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
		
	}
	
	function addInterest(corpCd,corpNm,stockCd){
		var url = "/interest/add/cud";
		
		if(!confirm('\''+corpNm+'\'을 관심목록에 추가하시겠습니까?')) return;
		
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
				$('#bodyList').css("animation-duration","2s");
			},    
			error : function(request, status, error) {       
				console.log(error)    
			}
		})
	}
</script>
<style>

@keyframes search {
	from{background : lightsteelblue;}
	to{background : transparent;}
}

@keyframes add {
	from{background : lightgreen;}
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
	
	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">종목찾기</h1>
	<p class="mb-4"></p>

	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">조회조건</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
            
                <table id="searchTable" class="table table-bordered"  width="100%">
                	<colgroup>
                		<col width="18%"/>
                		<col width="32%"/>
                		<col width="18%"/>
                		<col width="32%"/>
                	</colgroup>
                    <tbody>
                    	<tr>
                            <td>사업장명</td>
							<td><input type="text" name="pCorpName" id="pCorpName" value="" title="사업장명">  </td>
                        </tr>
                        <tr>
                            <td>기준 성장률</td>
							<td><input type="text" name="chkAccRate" id="chkAccRate" value="" title="기준 성장률">  </td>
							<td>기준 개년</td>
							<td>
								<select name="chkYearList" id="chkYearList" title="기준 년도 선택" style="width: 200px;"> 
									<option value = "">개년 선택</option>
								</select> 
							</td>
                        </tr>
                        <tr>
                            <td>조회 계정</td>
							<td>
								<input type="hidden" name="pAccountIds" id="pAccountIds" value="" title="계정 목록">
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_Revenue" title="매출액"> 매출액 
								<input type="checkbox" name="pAccountId" id="pAccountId" value="dart_OperatingIncomeLoss" title="영업이익"> 영업이익
								<input type="checkbox" name="pAccountId" id="pAccountId" value="ifrs-full_ProfitLoss" title="당기순이익"> 당기순이익 
							</td>
							<td>보고서 구분</td>
							<td><input type="radio" name="pReportCd" id="pReportCd" value="11011" title="보고서 구분" checked="checked"> 년도 <input type="radio" name="pReportCd" id="pReportCd" value="11012" title="보고서 구분"> 반기 </td>
                        </tr>
                    </tbody>
                </table>
                <a href="" onclick="goSearch(); return false;" class="btn btn-light btn-icon-split btn-sm">
                     <span class="icon text-white-50">
                         <i class="fas fa-flag"></i>
                     </span>
                     <span class="text">조 회</span>
               	</a>
            </div>
        </div>
    </div>
    </form>
</div>

<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">조회 결과</h1>
	<p class="mb-4">조회 결과에 따른 사업장명 및 년도별 성장성을 보여준다. 
		<span id=""></span>
	</p>
	
	<!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3" id="dataTables">
            <h6 class="m-0 font-weight-bold text-primary">조회 데이터</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table id="reprtList" class="table table-bordered" id="dataTable" width="100%" >
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
			            <tr id="yearHaeder">
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
							<c:forEach items="${yearList}" var="list">
								<td>${list.HAEDER_NM}<span style="float: right;">(년도)</span></td>
							</c:forEach>
							<td>상세</td>
							<td>등록</td>
						</tr>
						<tr id="quaterHaeder" style="display: none;">
			            	<td>No</td>
							<td>사업장명</td>
							<td>계정명</td>
							<c:forEach items="${quaterList}" var="list">
								<td>${list.HAEDER_NM}<span style="float: right;">(반기)</span></td>
							</c:forEach>
							<td>상세</td>
							<td>등록</td>
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