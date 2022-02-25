<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tra cứu điểm</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.2.2/sweetalert2.css" />
</head>
<body style="width: 100%">
	<div id="title" class="text-center p-3">
		<h1 class="text-success">TRA CỨU ĐIỂM THI</h1>
	</div>
	<div id="header" class="container">
		<form action="<%out.print(request.getContextPath());%>/check-score"
			id="check-score">
			<div class="col-md-8">
				<div class="form-group row">
					<label for="id" class="col-md-3 col-form-label font-weight-bold">Mã sinh viên</label>
					<div class="col-md-4"> 
						<input type="text" class="form-control" id="id" placeholder="Mã sinh viên" name="id">
							
						<div id="errors">
							<p class="text-block text-danger mt-1">${ errors.get("error") }</p>
						</div>
					</div>
					<div class="col-md-2">
						<button type="submit" class="btn btn-success mb-2 btn-check">Tra cứu</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<hr>
	<div id="info" class="container">
		<c:if test="${ student != null }">
			<div class="row p-3">
				<div class="col-md-3">
					<table>
						<tr>
							<th>Mã sinh viên:</th>
							<td class="font-weight-bold pl-3">${ student.id }</td>
						</tr>
						<tr>
							<th>Họ tên:</th>
							<td class="font-weight-bold pl-3">${ student.name }</td>
						</tr>

						<tr>
							<th>Lớp:</th>
							<td class="font-weight-bold pl-3">${ className }</td>
						</tr>
						<tr>
							<th>Giới tính:</th>
							<td class="font-weight-bold pl-3">${ student.sex ? "Nam" : "Nữ" }</td>
						</tr>
						<tr>
							<th>Ngày sinh:</th>
							<td class="font-weight-bold pl-3"><fmt:formatDate value="${ student.birthday }" pattern="dd-MM-yyyy"/></td>
						</tr>
					</table>
				</div>
			</div>
			<hr>
			<div class="row">
				<table class="table">
					<thead>
						<tr style="background-color: #28a745!important;color: white">
							<th scope="col">Tên môn học</th>
							<th scope="col" class="text-center">Điểm thi</th>
							<th scope="col" class="text-center">Điểm tối đa</th>
							<th scope="col" class="text-center">Kết quả</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ subjectScore }" var="ss">
							<tr>
								<th>${ subjectDao.getById(ss.subjectId).name }</th>
								<td class="text-center">${ ss.score }</td>
								<td class="text-center">10</td>
								<td class="text-center ${ ss.score < 5 ? 'text-danger' : '' }">${ ss.score > 5 ? "Đạt" : "Không đạt" }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script>
		$(document).ready(function() {
			$(document).on("click", ".btn-check", function(e) {
				e.preventDefault();
				var id = $("input[name='id']").val();
				var url = window.location + "?id=" + id;
				$.ajax({
					url : url,
					type: "GET",
					success : function(res){
						$("#info").load(url + " #info>*");
						$("#errors").load(url + " #errors>*");
					}
				});
			});
		});
	</script>
</body>
</html>