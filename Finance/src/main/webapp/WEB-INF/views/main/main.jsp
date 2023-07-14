<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <!-- 차트 링크 -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<script>
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
	frm.action = "/login";
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

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Main test</h1>
                    <p class="mb-4">test</p>

                    <!-- DataTales Example -->
                    <div class="row">
	                   <div class="col-xl-8 col-lg-7">
                           <div class="card shadow mb-4">
                               <!-- Card Header - Dropdown -->
                               <div class="card-header py-3 d-flex flex-row align-items-center justify-content-around">
                                   <div><h6 class="m-0 font-weight-bold text-primary">KOSPI</h6></div>
                                   <div><h6 class="m-0 font-weight-bold text-primary">KOSDAQ</h6></div>
                                   <div><h6 class="m-0 font-weight-bold text-primary">다우</h6></div>
                                   <div><h6 class="m-0 font-weight-bold text-primary">S&P</h6></div>
                                   <div><h6 class="m-0 font-weight-bold text-primary">NASDAQ</h6></div>
                               </div>
                               <!-- Card Body -->
                               <div class="card-body">
	                                   <div class="card-body">
			                            <div class="container">
									      <div class="row my-3">
									          <div class="col">
									              <h4>Bootstrap 4 Chart.js - Line Chart</h4>
									          </div>
									      </div>
									      <div class="row my-2">
									          <div class="col">
									              <div class="card">
									                  <div class="card-body">
									                      <canvas id="myChart" height="100"></canvas>
									                  </div>
									              </div>
									          </div>
									      </div>
									  </div>
			                        </div>
	                                   
                               </div>
                           </div>
                       </div>
                       <div class="col-xl-4 col-lg-5">
                            <div class="card shadow mb-4">
                                <!-- Card Body -->
                                <div class="card-body">
                                <form class="user" name="loginForm" id="loginForm" method="post">	
                                	<div class="p-5">
	                                    <div class="text-center">
	                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
	                                    </div>
	                                    <!-- <form class="user" name="loginForm" id="loginForm"> -->
	                                        <div class="form-group">
	                                            <input type="text" name="userId" class="form-control form-control-user" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter User Id">
	                                        </div>
	                                        <div class="form-group">
	                                            <input type="password" name="passWd" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
	                                        </div>
	                                        <a href="" onclick="Login(); return false;" class="btn btn-primary btn-user btn-block">
	                                            Login
	                                        </a>
	                                        <hr>
	                                        <a href="index.html" class="btn btn-google btn-user btn-block">
	                                            <i class="fab fa-google fa-fw"></i> Login with Google
	                                        </a>
	                                        <a href="" onclick="signUp(); return false;" class="btn btn-primary btn-user btn-block">
	                                            회원가입
	                                        </a>
	                                    <!-- </form> -->
	                                    <hr>
	                                    <div class="text-center">
	                                        <a class="small" href="forgot-password.html">Forgot Password?</a>
	                                    </div>
	                                    <div class="text-center">
	                                        <a class="small" href="register.html">Create an Account!</a>
	                                    </div>
	                                </div>
                                </form>	
                                </div>
                            </div>
                        </div>
					</div>
                </div>
                
                
                <div id='signUpDiv' style='position:absolute; left:0; top:0;'>
                	
                	<div id='mask' style='position:absolute; background-color:#000000; display: none;'>
                	
                	</div>
                	<div class="card o-hidden border-0 shadow-lg" id="signUpForm" style="display: none;">
                	<form class="user" name="signUpForm" id="signUpForm" method="post">
	                    <div id="">
	                    	<div style="position: absolute; top: 0; right: 0; margin: 20px;">
                                <a onclick="maskClose(); return false;" style="cursor: pointer;" > X </a>
                            </div>
	                        <div class="p-5">
	                            <div class="text-center">
	                                <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
	                            </div>
	                            	<div class="form-group">
	                                    <input type="text" name="userId" id="userId" class="form-control form-control-user" id="exampleInputEmail" placeholder="User ID" required="required">
	                                </div>
	                                <div class="form-group">
	                                    <input type="email" name="email" id="email" class="form-control form-control-user" id="exampleInputEmail" placeholder="Email Address" required="required">
	                                </div>
	                                <div class="form-group">
	                                    <input type="text" name="userNick" id="userNick" class="form-control form-control-user" id="exampleInputEmail" placeholder="User Nick Name" required="required">
	                                </div>
	                                <div class="form-group row">
	                                    <div class="col-sm-6 mb-3 mb-sm-0">
	                                        <input type="password" name="password" id="password" class="Input-Password form-control form-control-user" placeholder="Password" required="required">
	                                    </div>
	                                    <div class="col-sm-6">
	                                        <input type="password" name="chkPassword" id="chkPassword" class="Input-Password form-control form-control-user" placeholder="Repeat Password" required="required">
	                                    </div>
	                                </div>
	                                <a onclick="goSignUp(); return false;" class="btn btn-primary btn-user btn-block">
	                                    Register Account
	                                </a>
                                <hr>
                                <div id="errorDiv">
                                	
                                </div>
	                            <hr>
	                            <div class="text-center">
	                                <a class="small" href="forgot-password.html">Forgot Password?</a>
	                            </div>
	                            <div class="text-center">
	                                <a class="small" href="login.html">Already have an account? Login!</a>
	                            </div>
	                        </div>
	                    </div>
                    </form>
					</div>
				</div>
                
                
                
 </body>
</html>
<script type="text/javascript">
		var ctx = document.getElementById('myChart').getContext('2d');
		var chart = new Chart(ctx, {
		   // 챠트 종류를 선택
			type: 'line',
		
		   // 챠트를 그릴 데이타
			data: {
		        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
		        datasets: [{
		          label: 'My First dataset',
		          backgroundColor: 'transparent',
		          borderColor: 'red',
		          data: [0, 10, 5, 2, 20, 30, 45]
		        }]
		      },
		      // 옵션
		      options: {
		        legend: {
		          display: false
		        },
		        title: {
		          display : true,
		          text: '라인차트 제목'
		        }
		      }
	    });
	 </script>



