<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Zeppelin Online Bank</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

		<a class="navbar-brand" href="${pageContext.request.contextPath}/">Zeppelin
			Bank</a>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">



			</ul>




			<form:form action="${pageContext.request.contextPath}/logout"
				method="POST">

				<button class="btn btn-danger" type="submit">Logout</button>

			</form:form>
		</div>
	</nav>

	<br>
	<div class="container">
		<h3>What would you like to do today?</h3>

		<hr>

		<p><div>
			<a
				href="${pageContext.request.contextPath}/register/showEmployeeRegistrationForm"
				class="btn btn-primary" role="button" aria-pressed="true">Register New Employee</a>
			</div>
			<br>
			<div>
				<a
				href="${pageContext.request.contextPath}/register/showManagerRegistrationForm"
				class="btn btn-primary" role="button" aria-pressed="true">Register New Manager</a>
			</div>
</p>
		

<hr>

<a href="${pageContext.request.contextPath}/">Back to Home Page</a>
</div>
<div class="footer p-3 mb-2 bg-light text-dark" style=" position: fixed;
   left: 0;
   bottom: 0;
   width: 100%;
  
  
   text-align: center;">
		<p>© 2018 Copyright:
        <a href="https://github.com/rahulzore"> Rahul Zore </a></p>
	</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
		integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
		integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
		crossorigin="anonymous"></script>
</body>


</html>









