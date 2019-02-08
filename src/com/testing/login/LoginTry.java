package com.testing.login;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testing.Mysql.ConnectMysql;
import com.testing.Mysql.UseMysql;
import com.testing.TestJunit.LoginSample;

/**
 * Servlet implementation class LoginTry
 */
@WebServlet("/LoginTry")
public class LoginTry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginTry() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 返回值编码的修改
		response.setContentType("text/html;charset=UTF-8");
		// 收到参数的编码
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("loginname");
		String pwd = request.getParameter("password");
//		System.out.println(name + pwd);
		LoginSample ls = new LoginSample();
		boolean result = ls.login(name, pwd);
		String info = "get:{";
		if (result) {
			info += "\"status\":200,\"msg\":\"恭喜你登录成功\"}";
		} else {
			info += "\"status\":500,\"msg\":\"抱歉登录失败\"}";
		}
		response.getWriter().append(info);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		// 返回值编码的修改
		response.setContentType("text/html;charset=UTF-8");
		// 收到参数的编码
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("loginname");
		String pwd = request.getParameter("password");
		// 正则过滤特殊字符
		String regx = "[^A-Za-z0-9_-]";
		Pattern p = Pattern.compile(regx);
		Matcher mname = p.matcher(name);
		Matcher mpwd = p.matcher(pwd);
//		System.out.println("post方法被调用");
//		LoginSample ls = new LoginSample();
//		boolean result = ls.login(name, pwd);
		String info = "{";
		//设置session的生命周期为30秒
		request.getSession().setMaxInactiveInterval(30);
//		Cookie loginCookie = new Cookie("loginname",name);
//		response.addCookie(loginCookie);
		if (name != null && pwd != null) {
			// 判断长度
			if (name.length() > 2 && name.length() < 17 && pwd.length() > 2 && pwd.length() < 17) {
				// 判断不包含特殊字符
				if (!mname.find() && !mpwd.find()) {
					if (request.getSession().getAttribute("loginName") == null) {
						// 创建sql连接以及实例化usemysql类型
						ConnectMysql connSql = new ConnectMysql();
						UseMysql mySql = new UseMysql(connSql.conn);
						if (mySql.Login(name, pwd)) {
							info += "\"status\":200,\"msg\":\"恭喜您，登录成功!\"}";
							// 在session当中记录本次登录的用户名
							request.getSession().setAttribute("loginName", name);
						} else {
							info += "\"status\":3000,\"msg\":\"用户名密码不匹配！\"}";
						}
					}
					// session当中有相应的loginName记录
					else {
						if (request.getSession().getAttribute("loginName").equals(name)) {
							info += "\"status\":3001,\"msg\":\"用户已经登录不能重复登录！\"}";
						} else {
							info += "\"status\":3002,\"msg\":\"已经有其他用户登录，不能重复登录！\"}";
						}
					}
				} else {
					info += "\"status\":3003,\"msg\":\"用户名密码不能包含特殊字符！\"}";
				}
			} else {
				info += "\"status\":3004,\"msg\":\"用户名密码长度必须是3至16位！\"}";
			}
		} else {
			info += "\"status\":3005,\"msg\":\"用户名密码长度不能为空！\"}";
		}
		// 返回值中显示本次登录的sessionID
//		response.getWriter().append(info + "sessionID:" + request.getSession().getId());
		response.getWriter().append(info);
//		response.getWriter().append("post方法被调用"+":"+name+pwd);
	}

}
