<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>User Management</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	margin: 0;
	padding: 0;
	text-align: center;
	background: #FFFBEB;
}

h1 {
	margin-top: 5%;
	margin-bottom: 2%;
	color: #251749;
}

.div_form {
	wusernameth: 40%;
	margin-left: auto;
	margin-right: auto;
	color: #263159;
}

.input-group-text, .input-group mb-3 {
	color: #263159;
}

.div_role {
	display: flex;
}

.div_role>input, .div_role>label {
	margin-left: 0.3rem;
}

.table {
	wusernameth: 60%;
	margin-left: auto;
	margin-right: auto;
}

.alert {
	wusernameth: 60%;
	margin-left: auto;
	margin-right: auto;
}

a {
	color: #263159;
	margin-left: 0.2rem;
}

.delete {
	color: #DC3535;
}
</style>
</head>
<body>
	<h1><a href="<c:url value="/HomeController"/>" style="text-decoration: none">Video Manager</a></h1>



	<div class="alert">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
	</div>
	<form class="div_form" action="/asmjv4/video/index" method="post">
	
	<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Poster</span>
			<img src="${form.poster }" name="poster" id="poster"
					class="img-fluid" alt="Poster video" width="300px">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Title</span>
			<input name="title" type="text" class="form-control"
				value="${form.title}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Link</span>
			<input name="href" type="text" class="form-control"
				value="${form.href}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Views</span>
			<input name="views" type="number" class="form-control"
				value="${form.views}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default" >
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Shares</span>
			<input name="shares" type="number" class="form-control"
				value="${form.shares}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default" >
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Description</span>
			<input name="description" type="text" class="form-control"
				value="${form.description}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default" >
		</div>
		
		<div class="div_active">
			<label>Active:</label> <input id="active" type="checkbox"
				name="active" ${form.isActive ? 'checked' : ''}
				aria-label="Sizing example input"> <label for="active">Active</label>
		</div>
		<button formaction="/asmjv4/video/create"
			class="btn btn-outline-success">Create</button>
		<button formaction="/asmjv4/video/update"
			class="btn btn-outline-warning">Update</button>
		<button formaction="/asmjv4/video/delete"
			class="btn btn-outline-danger">Delete</button>
		<button formaction="/asmjv4/video/reset" class="btn btn-outline-info">Reset</button>
	</form>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Title</th>
				<th scope="col">Link</th>
				<th scope="col">Poster</th>
				<th scope="col">Views</th>
				<th scope="col">Shares</th>
				<th scope="col">Description</th>
				<th scope="col">Active</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${items}">
				<tr>
					<td>${item.title}</td>
					<td>${item.href}</td>
					<td>${item.poster}</td>
					<td>${item.views}</td>
					<td>${item.shares}</td>
					<td>${item.description}</td>
					<td>${item.isActive?'Enable':'Disable'}</td>
					<td><a href="/asmjv4/video/edit?href=${item.href}">
							edit </a> <a class="delete"
						href="/asmjv4/video/delete?href=${item.href}"> delete </a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>