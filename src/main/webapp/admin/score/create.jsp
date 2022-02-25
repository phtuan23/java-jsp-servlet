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
				<h3 class="card-title">
					<select name="classId" class="form-control form-control-lg select-class">
						<option>-- Chọn lớp --</option>
						<c:forEach items="${ classrooms }" var="cls">
							<c:if test="${ classDao.getTotalStudent(cls.id) > 0 }">
								<option value="${ cls.id }">${ cls.className }</option>
							</c:if>
						</c:forEach>
					</select>
				</h3>
				<h3 class="card-title ml-2 choose-subject">
					<c:if test="${ students != null }">
						<select name="subjectId" class="form-control form-control-lg subject-id">
							<c:forEach items="${ subjects }" var="sub">
								<option value="${ sub.id }">${ sub.name }</option>
							</c:forEach>
						</select>
					</c:if>
				</h3>
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
			<div class="card-body main-form">
				<c:if test="${ students != null }">
					<form action="<%out.print(request.getContextPath() + "/admin/score/create");%>" method="post" id="form-create">
						<c:forEach items="${ students }" var="std">
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<input type="text" class="form-control form-control-lg student-id" name="studentId" value="${ std.id }" readonly="readonly">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<input type="text" class="form-control form-control-lg" value="${ std.name }" readonly="readonly">
									</div>
								</div>
								<div class="col-md-5">
									<div class="form-group">
										<input type="text" class="form-control form-control-lg score" name="score" placeholder="Điểm">
										<p class="text-danger err-score-${ std.id }">${ errors.get("std.id") }</p>
									</div>
								</div>
							</div>
						</c:forEach>
						<div class="row">
							<div class="col-md-12 text-right" style="padding-top: 30px;">
								<button type="submit" class="btn btn-lg btn-dark">
									<i class="fa fa-check"></i>
								</button>
								<a href="<%out.print(request.getContextPath());%>/admin/score" class="btn btn-lg btn-dark"><i class="fa fa-window-close"></i></a>
							</div>
						</div>
					</form>
				</c:if>
			</div>
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

		$(document).on("change",".select-class",function(){
			var classId = $(this).val();
			var href = window.location + "?classId=" + classId;
			$.ajax({
				url: href,
				type: "get",
				success: function(){
					$(".choose-subject").load(href + " .choose-subject>*");
					$(".main-form").load(href + " .main-form>*");
				}
			});
		});

		$(document).on("submit","#form-create",function(e){
			e.preventDefault();
			var url = $(this).attr("action");
			var subjectId = $(".subject-id").val();
			var studentId = [];
			var score = [];

			$(".student-id").each(function(){
				studentId.push($(this).val());
			});

			$(".score").each(function(){
				score.push($(this).val());
			});
			
			$.ajax({
				url: url,
				type: "post",
				dataType:'json',
				data : {
					studentId : studentId,
					subjectId : subjectId,
					score : score
				},
				success: function(res){
					if (res.type == "success") {
						window.location = res.content
					}else{
						$.each(res, function(index, value) {
						   $(".err-score-"+index).text(value);
						});
					}
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
