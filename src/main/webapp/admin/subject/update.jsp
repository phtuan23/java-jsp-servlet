<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../header.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1></h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Trang chủ</a></li>
						<li class="breadcrumb-item active">Cập nhật</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title">Cập nhật Môn học</h3>
				<div class="card-tools">
					<button type="button" class="btn btn-tool"
						data-card-widget="collapse" title="Collapse">
						<i class="fas fa-minus"></i>
					</button>
					<button type="button" class="btn btn-tool"
						data-card-widget="remove" title="Remove">
						<i class="fas fa-times"></i>
					</button>
				</div>
			</div>
			<div class="card-body">
				<form
					action="<%out.print(request.getContextPath() + "/admin/subject/update");%>"
					method="post" id="form-add">
					<input type="hidden" name="id" value="${ subject.id }">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="exampleFormControlInput1">Tên Môn học</label> <input
									type="text" class="form-control form-control-lg" name="name"
									placeholder="Tên Môn học" value="${ subject.name }">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Số buổi học</label>
								<input type="text" class="form-control form-control-lg" name="session"
									placeholder="Số buổi học" value="${ subject.session }">
							</div>
						</div>
						<div class="col-md-2 text-right" style="padding-top: 30px;">
							<button type="submit" class="btn btn-lg btn-dark">
								<i class="fa fa-check"></i>
							</button>
							<a href="<%out.print(request.getContextPath());%>/admin/classroom" class="btn btn-lg btn-dark"><i class="fa fa-window-close"></i></a>
						</div>
					</div>
				</form>
			</div>
			<%-- <div class="card-footer">Hello</div> --%>
		</div>
	</section>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script>
	$(document).ready(function() {
		$("#form-add").submit(function(e) {
			e.preventDefault();
			var href = $(this).attr("action");
			var data = $(this).serialize();
			$.ajax({
				url : href,
				type : "post",
				data : data,
				success : function(res) {
					if (res.type == "failed") {
						Swal.fire({
							icon : res.icon,
							html : res.content
						});
					}else{
						window.location.href = res;
					}
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
