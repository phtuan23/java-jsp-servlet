<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../header.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Danh sách Môn học</h1>
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
				<h3 class="card-title">
					<a
						href="<%out.print(request.getContextPath() + "/admin/subject/create");%>"
						class="btn btn-sm btn-dark"><i class="fa fa-plus-circle"></i>
					</a>
				</h3>

				<div class="card-tools">
					<form id="form-search">
						<div class="input-group input-group" style="width: 250px;">
							<input type="text" name="search" class="form-control float-right"
								placeholder="Tìm kiếm" value="${ search }">

							<div class="input-group-append">
								<button type="submit" class="btn btn-default">
									<i class="fas fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- /.card-header -->
			<div class="card-body table-responsive p-0">
				<table class="table table-hover text-nowrap">
					<thead>
						<tr>
							<th>#</th>
							<th>Tên Môn học</th>
							<th>Số buổi học</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${ pagination.lists }" var="subject">
						 	<tr>
						 		<td>${ subject.id }</td>
						 		<td>${ subject.name }</td>
						 		<td>${ subject.session }</td>
						 		<td class="text-right">
						 			<a href="<%out.print(request.getContextPath() + "/admin/subject/update");%>?id=${subject.id}" class="btn btn-warning"><i class="fa fa-edit"></i></a>
						 			<a href="<%out.print(request.getContextPath() + "/admin/subject/delete");%>?id=${subject.id}" class="btn btn-danger btn-del"><i class="fa fa-trash"></i></a>
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
						<c:otherwise>
							<ul class="pagination">
								<li class="page-item ${ pagination.currentPage == 1 ? 'disabled' : '' }"><a
									class="page-link" href="?page=${ currentPage - 1 }"
									aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
								</a></li>
								<c:forEach begin="1" end="${ pagination.totalPage }" var="i">
									<li class="page-item"><a class="page-link"
										href="?page=${ i }">${ i }</a></li>
								</c:forEach>
								<li
									class="page-item ${ pagination.currentPage == pagination.totalPage ? 'disabled' : '' }"><a
									class="page-link" href="?page=${ pagination.currentPage + 1 }"
									aria-label="Next"> <span aria-hidden="true">&raquo;</span>
								</a></li>
							</ul>
						</c:otherwise>
					</c:choose>
				</nav>
			</div>
		</div>
	</section>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script>
	$(document).ready(function() {
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

		$(document).on("click",".btn-del",function(e) {
			e.preventDefault();
			var href = $(this).attr("href");
			Swal.fire({
			  title: 'Bạn có chắc muốn xoá?',
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Xoá',
			  cancelButtonText: 'Huỷ'
			}).then((result) => {
			  if (result.isConfirmed) {
				  $.ajax({
					url : href,
					type : "get",
					success : function(res) {
						if (res.type == "failed") {
							Swal.fire({
								icon : res.icon,
								text : res.content
							});
						} else {
							Swal.fire({
								icon : res.icon,
								text : res.content
							});
							$(".content").load(window.location+ " .content>*");
						}
					}
				});
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
					console.log(url)
					$(".content").load(url + " .content>*");
				}
			});
		});
	});
</script>
<%@include file="../footer.jsp"%>
