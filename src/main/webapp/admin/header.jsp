<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% if(session.getAttribute("admin") == null){
	request.getRequestDispatcher("/login").forward(request, response);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quản lý điểm sinh viên</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.2.1/sweetalert2.css"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
<style>
     <%@ include file="public/dist/css/adminlte.min.css" %>
</style>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<nav
			class="main-header navbar navbar-expand navbar-white navbar-light">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
			</ul>
		</nav>

		<aside class="main-sidebar sidebar-dark-primary elevation-4">
			<a href="<% out.print(request.getContextPath()); %>/admin" class="brand-link"> <img
				src="<% out.print(request.getContextPath()); %>/admin/public/dist/img/AdminLTELogo.png" alt="AdminLTE Logo"
				class="brand-image img-circle elevation-3" style="opacity: .8">
				<span class="brand-text font-weight-light">Quản trị</span>
			</a>

			<div class="sidebar">
				<div class="user-panel mt-3 pb-3 mb-3 d-flex">
					<div class="image">
						<img src="<% out.print(request.getContextPath()); %>/admin/public/dist/img/user2-160x160.jpg"
							class="img-circle elevation-2" alt="User Image">
					</div>
					<div class="info">
						<a href="#" class="d-block">${ sessionScope.admin.name }</a>
						<a href="<% out.print(request.getContextPath());%>/logout" class="d-block mt-3"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a>
					</div>
				</div>

				<nav class="mt-2">
					<ul class="nav nav-pills nav-sidebar flex-column"
						data-widget="treeview" role="menu" data-accordion="false">
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/score/create"); %>" class="nav-link"><i class="nav-icon fas fa-user-cog"></i>
								<p>Nhập điểm thi</p>
							</a>
						</li>
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/classroom"); %>" class="nav-link"><i class="nav-icon fas fa-school"></i>
								<p>Quản lý Lớp học</p>
							</a>
						</li>
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/student"); %>" class="nav-link"><i class="nav-icon fas fa-user-graduate"></i>
								<p>Quản lý Sinh viên</p>
							</a>
						</li>
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/subject"); %>" class="nav-link"><i class="nav-icon fas fa-book-open"></i>
								<p>Quản lý môn học</p>
							</a>
						</li>
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/score"); %>" class="nav-link"><i class="nav-icon fas fa-user-graduate"></i>
								<p>Quản lý Điểm môn học</p>
							</a>
						</li>
						<li class="nav-item">
							<a href="<% out.print(request.getContextPath() + "/admin/account"); %>" class="nav-link"><i class="nav-icon fas fa-user-cog"></i>
								<p>Quản lý Tài khoản</p>
							</a>
						</li>
					</ul>
				</nav>
			</div>
		</aside>