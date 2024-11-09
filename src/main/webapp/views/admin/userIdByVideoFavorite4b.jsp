<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!doctype html>
<html>
<head>

</head>
<body>
	<h1>List users by favorite video</h1>
	<form class="form-floating" id="videoForm"
		action="usersByVideoFavorite" method="post">
		<select class="form-select" id="selectVideo" name="href"
			onchange="submitForm()">
			<option selected disabled>Open this select menu</option>
			<c:forEach items="${videoLikedInfo}" var="item">
				<option class="text-dark" value="${item.href}" name="href">${item.title}</option>
			</c:forEach>
		</select> <label for="selectVideo">Title video</label>
	

	<script>
    function submitForm() {
        document.getElementById('videoForm').submit();
    }
</script>

	<table class="table caption-top" id="userTable">
		<thead>
			<tr>
				<th scope="col">Username</th>
				<th scope="col">Email</th>
				<th scope="col">Favorite Date</th>

			</tr>
		</thead>
		<tbody class="table-group-divider">
			<c:forEach var="item" items="${userVideoHref}">
				<tr>
					<td>${item.username}</td>
					<td>${item.email}</td>
					<td>${item.likedDate}</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>






</body>
</html>
