<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <!-- 차트 링크 -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<script>
$(function(){
	if(${not empty memberVO}){
		$('#login-body').hide();
		$('#user-body').show();
	}else{
		$('#login-body').show();
		$('#user-body').hide();
	}
});

function signUp(){
	alert('회원가입 할까말까?');
	LoadingWithMask();
	return;
}

function LoadingWithMask() {
    //화면의 높이와 너비를 구합니다.
    var maskHeight = $(document).height();
    var maskWidth  = window.document.body.clientWidth;
    
    $('#signUpForm').show();
    $('#mask').show();
    
    var signHeight = $('#signUpForm').height();
    var signWidth  = $('#signUpForm').width();
        
    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    $('#mask').css({
            'width' : maskWidth,
            'height': maskHeight,
            'opacity' : '0.8'
    });
    
    $('#signUpForm').css({
        'left' : (maskWidth/2)-(signWidth/2),
        'top': (maskHeight/2)-(signHeight/2),
	});
    
  
}

function maskClose(){
	$('#signUpForm').hide();
	$('#mask').hide();
}

function passwordChk() {
	var url = "/web/member/passwordChk";

	var test = false;
	
	$.ajax({    
		type : 'post',           // 타입 (get, post, put 등등)    
		url : url,   	// 요청할 서버url
		async : false,
		data : {
			password 	: $('#password').val(),
			chkPassword	: $('#chkPassword').val()
		},    
		dataType : 'json',    
		success : function(data) { // 결과 성공 콜백함수
			test = data.chkRst;
		},    
		error : function(request, status, error) {       
			console.log(error)    
		}
	})
	return test;
}

function goSignUp(){
	var frm = document.signUpForm;
	
	$('#errorMsg').remove();
	
	if(!passwordChk()){
		$('.Input-Password').css({
			  'border-color': 'rgb(228 100 100 / 50%)',
			  'outline': '0' ,
		  	'box-shadow': '0 0 0 0.2rem rgb(228 100 100 / 50%)',
		}) ;
		setTimeout(() => {$('.Input-Password').removeAttr("style");}, 2500);
		
		$('#errorDiv').append('<div id="errorMsg">비밀번호 다름</div>');
		return;
	}else{
		$('.Input-Password').css({
			  'border-color': 'rgb(113 236 98 / 50%)',
			  'outline': '0' ,
		  'box-shadow': '0 0 0 0.2rem rgb(113 236 98 / 50%)',
		}) ;
		setTimeout(() => {$('.Input-Password').removeAttr("style");}, 2500);
	}
	
	
	frm.action = "/web/member/cud";
	frm.submit();
}

function Login(){
	var frm = document.loginForm;
	frm.action = "/web/login";
	frm.submit();
}

function goSubmit(){
	frm = document.form;
	frm.submit();
}
</script>

<style>

#errorMsg {
    color: red;
    text-decoration: none;
    background-color: transparent;
}

</style>

<html>
 <head>
  <title>메인화면 20221010 수정
 </title>
 </head>
 <body>
	 
 
  <div class="container-fluid">
  <form action="/test" method="get" name="form">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Main test</h1>
                    <p class="mb-4">test</p>

                    <!-- DataTales Example -->
                    <a href="#" onclick="goSubmit(); return false;">확인</a>
                    </form>
                 </div>
                
 </body>
</html>