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
	<h1><a href="<c:url value="/HomeController"/>" style="text-decoration: none">User Manager</a></h1>



	<div class="alert">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
	</div>
	<form class="div_form" action="/asmjv4/user/index" method="post">
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Username</span>
			<input name="username" type="text" class="form-control"
				value="${form.username}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Password</span>
			<input name="password" type="password" class="form-control"
				value="${form.password}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="input-group mb-3">
			<span class="input-group-text" id="inputGroup-sizing-default">Email</span>
			<input name="email" type="email" class="form-control"
				value="${form.email}" aria-label="Sizing example input"
				aria-describedby="inputGroup-sizing-default">
		</div>
		<div class="div_role">
			<label>Role:</label> <input id="admin" type="checkbox" name="admin"
				${form.isAdmin ? 'checked' : ''} aria-label="Sizing example input">
			<label for="admin">Admin</label>
		</div>
		<div class="div_active">
			<label>Active:</label> <input id="active" type="checkbox"
				name="active" ${form.isActive ? 'checked' : ''}
				aria-label="Sizing example input"> <label for="active">Active</label>
		</div>
		<button formaction="/asmjv4/user/create"
			class="btn btn-outline-success">Create</button>
		<button formaction="/asmjv4/user/update"
			class="btn btn-outline-warning">Update</button>
		<button formaction="/asmjv4/user/delete"
			class="btn btn-outline-danger">Delete</button>
		<button formaction="/asmjv4/user/reset" class="btn btn-outline-info">Reset</button>
	</form>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Username</th>
				<th scope="col">Password</th>
				<th scope="col">Email</th>
				<th scope="col">Role</th>
				<th scope="col">Active</th>
				<th scope="col">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${items}">
				<tr>
					<td>${item.username}</td>
					<td>${item.password}</td>
					<td>${item.email}</td>
					<td>${item.isAdmin?'Admin':'User'}</td>
					<td>${item.isActive?'Enable':'Disable'}</td>
					<td><a href="/asmjv4/user/edit?username=${item.username}">
							edit </a> <a class="delete"
						href="/asmjv4/user/delete?username=${item.username}"> delete </a></td>
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