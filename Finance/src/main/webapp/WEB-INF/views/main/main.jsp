<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <!-- 차트 링크 -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<script>
function signUp(){
	alert('회원가입 할까말까?');
	return;
}
</script>

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
                                	
                                	<div class="p-5">
	                                    <div class="text-center">
	                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
	                                    </div>
	                                    <form class="user" name="loginForm" id="loginForm">
	                                        <div class="form-group">
	                                            <input type="text" name="userId" class="form-control form-control-user" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter User Id">
	                                        </div>
	                                        <div class="form-group">
	                                            <input type="password" name="passWd" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
	                                        </div>
	                                        <div class="form-group">
	                                            <div class="custom-control custom-checkbox small">
	                                                <input type="checkbox" class="custom-control-input" id="customCheck">
	                                                <label class="custom-control-label" for="customCheck">Remember
	                                                    Me</label>
	                                            </div>
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
	                                    </form>
	                                    <hr>
	                                    <div class="text-center">
	                                        <a class="small" href="forgot-password.html">Forgot Password?</a>
	                                    </div>
	                                    <div class="text-center">
	                                        <a class="small" href="register.html">Create an Account!</a>
	                                    </div>
	                                </div>
                                	
                                </div>
                            </div>
                        </div>
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



