<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../header.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Điểm môn học</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Trang chủ</a></li>
						<li class="breadcrumb-item active">Danh sách</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="card">
			<div class="card-header">
				<form class="form-select">
					<h3 class="card-title ">
						<select name="classId" class="form-control form-control-lg select">
							<option value="">Chọn lớp</option>
							<c:forEach items="${ classDao.getAll() }" var="cls" >
								<option value="${ cls.id }" ${ classId == cls.id ? 'selected' : '' }>${ cls.className }</option>
							</c:forEach>
						</select>
					</h3>
					<h3 class="card-title ml-3">
						<select name="subjectId" class="form-control form-control-lg select">
							<option value="">Chọn môn học</option>
							<c:forEach items="${ subjectDao.getAll() }" var="sub" >
								<option value="${ sub.id }" ${ subjectId == sub.id ? 'selected' : '' }>${ sub.name }</option>
							</c:forEach>
						</select>
					</h3>
				</form>
				<div class="card-tools">
					<form id="form-search">
						<div class="input-group input-group" style="width: 250px;">
							<input type="text" name="search" class="form-control form-control-lg float-right"
								placeholder="Tìm kiếm" value="${ search }">

							<div class="input-group-append">
								<button type="submit" class="btn btn-default bnt-lg">
									<i class="fas fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- /.card-header -->
			<div id="main-data">
				<c:if test="${ pagination.lists.size() > 0}">
					<div class="card-body table-responsive p-0">
						<table class="table table-hover text-nowrap">
							<thead>
								<tr>
									<th>Lớp</th>
									<th>Mã sinh viên</th>
									<th>Tên sinh viên</th>
									<th>Môn học</th>
									<th>Điểm số</th>
									<th>Cập nhật điểm</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach items="${ pagination.lists }" var="sc">
								 	<tr id="main-tr">
								 		<td>${ classDao.getById(studentDao.getById(sc.studentId).classId).className }</td>
								 		<td>${ sc.studentId }</td>
								 		<td>${ studentDao.getById(sc.studentId).name }</td>
								 		<td>${ subjectDao.getById(sc.subjectId).name }</td>
								 		<td class="sc-${sc.studentId }-${sc.subjectId}">${ sc.score }</td>
								 		<td class="text-right">
								 			<form class="form-inline" action="<%out.print(request.getContextPath());%>/admin/score/update">
								 				<input type="hidden" name="studentId" value="${ sc.studentId }">
									 			<input type="hidden" name="subjectId" value="${ sc.subjectId }">
									 			<input type="text" name="score" class="form-control w-50">
									 			<button class="btn btn-warning btn-update"><i class="fa fa-edit"></i></button>
								 			</form>
								 		</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
				<!-- /.card-body -->
					<div class="card-footer">
						<nav aria-label="Page navigation example" style='float: right;'>
							<c:choose>
								<c:when test="${ search != null}">
									<ul class="pagination">
										<li class="page-item ${ pagination.currentPage == 1 ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage - 1 }&search=${search}"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
										<c:forEach begin="1" end="${ pagination.totalPage }" var="i">
											<li class="page-item"><a class="page-link"
												href="?page=${ i }&search=${search}">${ i }</a></li>
										</c:forEach>
										<li
											class="page-item ${ pagination.currentPage == pagination.totalPage ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage + 1 }&search=${search}"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</ul>
								</c:when>
								<c:when test="${ subjectId != null}">
									<ul class="pagination">
										<li class="page-item ${ pagination.currentPage == 1 ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage - 1 }&subjectId=${subjectId}&classId="
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
										<c:forEach begin="1" end="${ pagination.totalPage }" var="i">
											<li class="page-item"><a class="page-link"
												href="?page=${ i }&subjectId=${subjectId}&classId=">${ i }</a></li>
										</c:forEach>
										<li
											class="page-item ${ pagination.currentPage == pagination.totalPage ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage + 1 }&subjectId=${subjectId}&classId="
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</ul>
								</c:when>
								<c:when test="${ classId != null}">
									<ul class="pagination">
										<li class="page-item ${ pagination.currentPage == 1 ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage - 1 }&classId=${classId}&subjectId="
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
										<c:forEach begin="1" end="${ pagination.totalPage }" var="i">
											<li class="page-item"><a class="page-link" href="?page=${ i }&classId=${classId}&subjectId=">${ i }</a></li>
										</c:forEach>
										<li class="page-item ${ pagination.currentPage == pagination.totalPage ? 'disabled' : '' }"><a
											class="page-link"
											href="?page=${ pagination.currentPage + 1 }&classId=${classId}&subjectId="
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</ul>
								</c:when>
							</c:choose>
						</nav>
					</div>
				</c:if>
			</div>
		</div>
	</section>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(document).ready(function() {
		$(document).on("click",".btn-update",function(e){
			e.preventDefault();
			var studentId = $(this).closest("form").find("input[name='studentId']").val();
			var subjectId = $(this).closest("form").find("input[name='subjectId']").val();
			var score = $(this).closest("form").find("input[name='score']").val();
			var url = $(this).closest("form").attr("action");
			$.ajax({
				url: url,
				type: "post",
				data: {
					studentId : studentId,
					subjectId : subjectId,
					score : score
				},
				success : function(res){
					if (res.type == "failed") {
						Swal.fire({
							icon : res.icon,
							text : res.content
						});
					}else{
						$(".sc-"+studentId+"-"+subjectId).text(score);
						Swal.fire({
							icon : res.icon,
							text : res.content
						});
						$("input[name='score']").trigger('reset');
					}
				}
			});
		});
		
		$(document).on("click", ".pagination a", function(e) {
			e.preventDefault();
			var href = $(this).attr("href");
			$.ajax({
				url : href,
				type : "get",
				success : function(res) {
					$(".content").load(href + " .content>*");
				}
			});
		});

		$(document).on("submit","#form-search",function(e) {
			e.preventDefault();
			var data = $(this).serialize();
			var url = window.location + "?" + data; 
			$.ajax({
				url : url,
				type : "get",
				success : function(res) {
					$(".content").load(url + " .content>*");
				}
			});
		});

		$(document).on("change",".select",function(e) {
			e.preventDefault();
			var data = $(this).closest("form").serialize();
			var url = window.location + "?" + data; 
			$.ajax({
				url : url,
				type : "get",
				success : function(res) {
					$("#main-data").load(url + " #main-data>*");
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
