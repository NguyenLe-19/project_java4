<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Page Loader -->
<div id="loader-wrapper">
	<div id="loader"></div>

	<div class="loader-section section-left"></div>
	<div class="loader-section section-right"></div>

</div>
<nav class="navbar navbar-expand-lg">
	<div class="container-fluid">
		<a class="navbar-brand" href="<c:url value='/HomeController'></c:url>">
			<i class="fas fa-film mr-2"></i> ONLINE ENTERTAIMENT
		</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<i class="fas fa-bars"></i>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto mb-2 mb-lg-0">
				<c:choose>
					<c:when test="${not empty sessionScope.currentUser}">

						<li class="nav-item"><a class="nav-link nav-link-1 active"
							data-bs-toggle="modal" data-bs-target="#changePassModal">Welcome,
								${sessionScope.currentUser.username}</a></li>
						<li class="nav-item"><a class="nav-link nav-link-1"
							href="favorites">My favorites</a></li>
						<li class="nav-item"><a class="nav-link nav-link-2"
							href="history">History</a></li>
						<li class="nav-item"><a class="nav-link nav-link-3"
							href="logout">Logout</a></li>
						<c:if test="${sessionScope.currentUser.isAdmin}">
							<li class="nav-item"><a class="nav-link nav-link-4"
								href="/asmjv4/admin">Manager</a></li>
						</c:if>


					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class="nav-link nav-link-2"
							href="login">Login</a></li>
						<li class="nav-item"><a class="nav-link nav-link-3"
							href="register">Register</a></li>
						<li class="nav-item"><a class="nav-link nav-link-4"
							href="forgotPass">Forgot password</a></li>

					</c:otherwise>
				</c:choose>



			</ul>
		</div>
	</div>
</nav>

<div class="tm-hero d-flex justify-content-center align-items-center"
	data-parallax="scroll"
	data-image-src="<c:url value='/templates/user/img/hero.jpg' />">
	<form class="d-flex tm-search-form">
		<input class="form-control tm-search-input" type="search"
			placeholder="Search" aria-label="Search">
		<button class="btn btn-outline-success tm-search-btn" type="submit">
			<i class="fas fa-search"></i>
		</button>
	</form>
</div>



<!-- Modal -->
<div class="modal fade" id="changePassModal" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="staticBackdropLabel">Change
					Password</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			 <form class="div_form" action="/asmjv4/changePass" method="post"> 
			<div class="modal-body">
				<div class="form-group">
					<input type="password" name="currentPass" id="currentPass" class="form-control rounded-0"
						placeholder="Current Password" required />
				</div>
				<div class="form-group">
					<input type="password" name="newPass" id="newPass" class="form-control rounded-0"
						placeholder="New Password" required />
				</div>
				
				<h5 style="color: red" id="messageChangePass">${alert}</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-warning"
					data-bs-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-warning" id="changePassBtn" formaction="/asmjv4/changePass">Save changes</button>
			</div>
			</form>
		</div>
	</div>
</div>
