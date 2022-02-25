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
						<li class="breadcrumb-item active">Điểm thi</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title">Cập nhật điểm thi</h3>
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
				<form action="<%out.print(request.getContextPath() + "/admin/score/update");%>?id=${ student.id }&subject=${ subject.id }"
					method="post" id="form-add">
					<div class="row">
						<div class="col-md-1">
							<div class="form-group">
								<label for="exampleFormControlInput1">Mã sinh viên</label> 
								<input type="text" class="form-control form-control-lg" name="studentId" value="${ student.id }" readonly="readonly">
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label for="exampleFormControlInput1">Tên sinh viên</label> 
								<input type="text" class="form-control form-control-lg" value="${ student.name }" readonly="readonly">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="exampleFormControlSelect1">Môn học</label> 
								<select class="form-control form-control-lg" name="subjectId">
									<option value="${ subject.id }" selected="selected">${ subject.name }</option>
								</select>
							</div>
						</div>
						<div class="col-md-2">
							<div class="form-group">
								<label for="exampleFormControlInput1">Điểm thi</label> 
								<input type="text" class="form-control form-control-lg" name="score" placeholder="Điểm" value="${ subjectScore.score }">
							</div>
						</div>
						<div class="col-md-2 text-right" style="padding-top: 30px;">
							<button type="submit" class="btn btn-lg btn-dark">
								<i class="fa fa-check"></i>
							</button>
							<a href="<%out.print(request.getContextPath());%>/admin/score" class="btn btn-lg btn-dark"><i class="fa fa-window-close"></i></a>
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
						window.location = res.content
					}
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
