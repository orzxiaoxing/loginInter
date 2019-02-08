function loginJS() {
	// 定义一个存放url的变量，指定请求的接口的地址
	var AjaxURL = "http://localhost:8080/loginInter/LoginTry";
	$.ajax({
		// 方法用post
		type : "post",
		// 返回和请求的参数类型传递方式。
		dataType : "text",
		// 请求的接口地址
		url : AjaxURL,
		// 获取id=loginForm的form表单的参数
		data : $("#loginForm").serialize(),
		// 接口执行的返回，当接口调用成功时，执行success中的方法
		success : function(result) {
			// // result就是接口的返回信息，在本例中就是response.getWriter的内容
			// alert(result);
			// //用eval方法将json格式的字符串解析为json格式键值对表达
			var jsonText = eval("(" + result + ")");
			// //遍历json中的键值对，在alert中输出
			// for(var key in jsonText){
			// alert(key+":"+jsonText[key])
			// }
			// 在info上显示登录内容
			document.getElementById("info").innerText = jsonText["msg"];
			// $('#info')[0].innerText=result["msg"];
			// 判断登录接口返回信息的status是否为200，如果是，则跳转到用户信息界面
			if (jsonText["status"] == 200) {
				window.location.href = ("./user.html");
			}

		},
		// 接口调用出错时，执行该方法
		error : function() {
			alert("服务器忙！请稍后重试。");
		}
	});
}

/**
 * logout()方法在user.html中的注销按钮通过onclick事件响应 调用Logout接口，完成注销的操作。
 */
function LoginOut() {
	// 定义一个存放url的变量，指定请求的接口的地址
	var AjaxURL = "http://localhost:8080/loginInter/LoginOut";
	$.ajax({
		// 方法用post
		type : "post",
		// 返回和请求的参数类型传递方式。
		dataType : "json",
		// 请求的接口地址
		url : AjaxURL,
		// 接口执行的返回，当接口调用成功时，执行success中的方法
		success : function(result) {
			alert(result["msg"]);
			window.location.href = "index.html";
		},
		// 接口调用出错时，执行该方法
		error : function() {
			alert("服务器忙！请稍后重试。");
		}
	});
}

/**
 * getUser函数，通过user.html中body的onload事件响应异步提交
 * 在访问user.html时就调用Userinfo接口获取用户信息并且写到对应的元素中。
 */
function getUser() {
	// 定义一个存放url的变量，指定请求的接口的地址
	var AjaxURL = "http://localhost:8080/loginInter/UserInfo";
	$.ajax({
		// 方法用post
		type : "post",
		// 返回和请求的参数类型传递方式。
		dataType : "text",
		// 请求的接口地址
		url : AjaxURL,
		// 接口执行的返回，当接口调用成功时，执行success中的方法
		success : function(result) {
			// 将返回结果解析出来的对应内容填写到对应的元素中
			var jsonText = eval("(" + result + ")");
//			 for(var key in jsonText){
//			 alert(key+":"+jsonText[key])
//			 }
			document.getElementById("userid").innerHTML = jsonText["id"];
			document.getElementById("nickname").innerHTML = jsonText["nickname"];
			document.getElementById("describe").innerHTML = jsonText["describe"];
		},
		// 接口调用出错时，执行该方法
		error : function() {
			alert("服务器忙！请稍后重试。");
		}
	});
}