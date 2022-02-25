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
						<li class="breadcrumb-item active">Thêm mới</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title">Thêm mới Sinh viên</h3>
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
					action="<%out.print(request.getContextPath() + "/admin/student/create");%>"
					method="post" id="form-add">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="exampleFormControlInput1">Mã Sinh viên</label> 
								<input type="text" class="form-control form-control-lg" name="id" placeholder="Mã Sinh viên">
								<p class="text-danger mt-1 error-id"></p>
							</div>
							<div class="form-group">
								<label for="exampleFormControlInput1">Tên Sinh viên</label> 
								<input type="text" class="form-control form-control-lg" name="name" placeholder="Tên Sinh viên">
								<p class="text-danger mt-1 error-name"></p>
							</div>
							<div class="form-group">
								<label for="exampleFormControlInput1">Ngày sinh</label> 
								<input type="date" class="form-control form-control-lg" name="birthday">
								<p class="text-danger mt-1 error-birthday"></p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Trạng thái</label> <select
									class="form-control form-control-lg" name="status">
									<option value="0">Ẩn</option>
									<option value="1">Hiển thị</option>
								</select>
							</div>
							<div class="form-group">
								<label for="exampleFormControlSelect1">Giới tính</label> 
								<select class="form-control form-control-lg" name="sex">
									<option value="">Chọn giới tính</option>
									<option value="0">Nữ</option>
									<option value="1">Nam</option>
								</select>
								<p class="text-danger mt-1 error-sex"></p>
							</div>
							<div class="form-group">
								<label for="exampleFormControlSelect1">Lớp</label> 
								<select class="form-control form-control-lg" name="classId">
									<option value="0">--Chọn lớp--</option>
									<c:forEach items="${ classes }" var="cls">
										<option value="${ cls.id }">${ cls.className }</option>
									</c:forEach>
								</select>
								<p class="text-danger mt-1 error-classId"></p>
							</div>
						</div>
						
					</div>
					<div class="col-md-12 text-right" style="padding-top: 30px;">
							<button type="submit" class="btn btn-lg btn-dark">
								<i class="fa fa-check"></i>
							</button>
							<a href="<%out.print(request.getContextPath());%>/admin/student" class="btn btn-lg btn-dark"><i class="fa fa-window-close"></i></a>
						</div>
				</form>
			</div>
		</div>
	</section>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script>
	$(document).ready(function() {
		$(document).on("submit","#form-add",function(e) {
			e.preventDefault();
			var href = $(this).attr("action");
			var data = $(this).serialize();
			$.ajax({
				url : href,
				type : "post",
				data : data,
				success : function(res) {
					if(res.type == "success"){
						window.location.href = res.content;
					}else{
						$.each(res, (key,err) => {
							$(".error-"+key).text(err);
						});
					}
					if (res.type == "failed") {
						Swal.fire({
							icon : res.icon,
							html : res.content
						});
					}
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
