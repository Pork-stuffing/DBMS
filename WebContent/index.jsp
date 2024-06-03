<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if (session.getAttribute("userDB") == null){
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}	
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>图书馆管理系统</title>
	<jsp:include page="common/css.jsp"></jsp:include>	
	<style>
		body {
			background-image: url("static/images/03.jpg");
		}
	</style>
</head>

<body class="bootstrap-admin-with-small-navbar">
	<jsp:include page="common/top.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<c:if test="${userDB.role == 1}">
				<jsp:include page="common/user_left.jsp"></jsp:include>			
			</c:if>
			<c:if test="${userDB.role == 2}">
				<jsp:include page="common/left.jsp"></jsp:include>			
			</c:if>
			<!-- content -->
			<div class="col-md-10">
				<div class="row">
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="text-muted bootstrap-admin-box-title">图书查询</div>
							</div>
							<div class="bootstrap-admin-panel-content">
								<ul>
									<li>根据图书名称、作者、分类查询图书信息</li>
									<li>可查询图书的编号、名称、分类、作者、在馆数量等</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- 判断用户是否登录 -->
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="text-muted bootstrap-admin-box-title">借阅信息</div>
							</div>
							<div class="bootstrap-admin-panel-content">
								<ul>
									<li>展示所借图书的基本信息，借阅日期、截止还书日期等</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="text-muted bootstrap-admin-box-title">借阅历史</div>
							</div>
							<div class="bootstrap-admin-panel-content">
								<ul>
									<li>查询借阅历史，借阅时长等具体信息</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
	
			</div>
		</div>
	</div>

	<!-------------------------------------------------------------->

	<jsp:include page="common/userInfo.jsp"></jsp:include>
	<jsp:include page="common/js.jsp"></jsp:include>
</body>
</html>