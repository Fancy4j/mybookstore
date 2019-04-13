<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
		<script>
            /**
			 * 用户输完用户名之后去发ajax请
             */
            function isUsernameExists() {
				var usernameInput = document.getElementsByName("username");
				//发送ajax异步请求
				// 1 创建XMLHttpRequest对象
				var xhr = new XMLHttpRequest();

				//2 注册状态监控回调函数：
				//什么时候调用? 当收到服务器发回来的响应报文我们在这里去处理响应报文
				xhr.onreadystatechange = function () {

				    if (xhr.readyState == 4 && xhr.status == 200) {
				        var resptext = xhr.responseText;

						var msgSpan = document.getElementById("msgUsername");
				        if (resptext == "exist") {
				            msgSpan.innerText = "已经被占用"
						} else {
				            msgSpan.innerText = "可以用"
						}
					}
				}

				//3 建立与服务器的异步连接
				xhr.open("GET","http://localhost:8081/AjaxServlet?op=isUsernameValid&username="+usernameInput.value);

				//4 发出异步请求
				xhr.send();
            }

            /**
			 * 判断邮箱是否符合要求
             */
/*

            function isEmailValid() {
                var emailInput = document.getElementsByName("email");
                //发送ajax异步请求
                // 1 创建XMLHttpRequest对象
                var xhr = new XMLHttpRequest();

                //2 注册状态监控回调函数：
                //什么时候调用? 当收到服务器发回来的响应报文我们在这里去处理响应报文
                xhr.onreadystatechange = function () {

                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var resptext = xhr.responseText;

                        var msgSpan = document.getElementById("msgEmail");
                        if (resptext == "isValid") {
                            msgSpan.innerText = "邮箱格式正确"
                        } else {
                            msgSpan.innerText = "邮箱格式有误"
                        }
                    }
                }

                //3 建立与服务器的异步连接
                xhr.open("GET","http://localhost:8081/AjaxServlet?op=isEmailValid&email="+emailInput.value);

                //4 发出异步请求
                xhr.send();
            }
*/
/*
            function isBirthdayValid() {
                var birthdayInput = document.getElementsByName("birthday");
                //发送ajax异步请求
                // 1 创建XMLHttpRequest对象
                var xhr = new XMLHttpRequest();

                //2 注册状态监控回调函数：
                //什么时候调用? 当收到服务器发回来的响应报文我们在这里去处理响应报文
                xhr.onreadystatechange = function () {

                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var resptext = xhr.responseText;

                        var msgSpan = document.getElementById("msgBirthday");
                        if (resptext == "isValid") {
                            msgSpan.innerText = "日期格式正确"
                        } else {
                            msgSpan.innerText = "日期格式有误"
                        }
                    }
                }

                //3 建立与服务器的异步连接
                xhr.open("GET","http://localhost:8081/AjaxServlet?op=isBirthdayValid&birthday="+birthdayInput.value);

                //4 发出异步请求
                xhr.send();
            }*/
		</script>
	</head>

	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/UserServlet">
				<input type="hidden" name="op" value="regist" />

				<ul class="left-form">
					<li>
						<span id="msgUsername" style="color: red;"></span>
						${msg.error.username }<br/>
						<input type="text" name="username" placeholder="用户名" value="${msg.username}" required="required" onblur="isUsernameExists()"/>

						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"></div>
					</li>
					<li>
						<%--<span id="msgEmail" style="color: red;"></span>--%>
						${msg.error.email }<br/>
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required" <%--onblur="isEmailValid()--%>"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<%--<span id="msgBirthday" style="color: red;"></span>--%>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15" <%--onblur="isBirthdayValid()--%>"/>
						<div class="clear"> </div>
					</li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>