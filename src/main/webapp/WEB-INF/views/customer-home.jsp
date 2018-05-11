<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark"> <a
		class="navbar-brand" href="${pageContext.request.contextPath}/">Zeppelin
		Bank</a>

	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">

			<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer-home">Home</a></li>
			
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> Accounts </a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/account/primaryAccountDetails">Primary
						Account</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/account/savingsAccountDetails">Savings
						Account</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> Transfer </a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/transfer/toSomeoneElseWithin">To
						Someone else from Zeppelin Bank</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/transfer/toSomeoneElseNotWithin">To
						Someone else from Other Bank</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/transfer/betweenAccounts">Between
						Accounts</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> Add Recipients </a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/transfer/internalrecipient/save">Add
						Recipient from Zeppelin Bank</a> <a class="dropdown-item"
						href="${pageContext.request.contextPath}/transfer/externalrecipient/save">Add
						Recipient from another Bank</a>
				</div></li>

		</ul>




		<form:form action="${pageContext.request.contextPath}/logout"
			method="POST">

			<button class="btn btn-danger" type="submit">Logout</button>

		</form:form>
	</div>
	</nav>
	<br>
	<div class="container">
		<p>
			<h3>Welcome: <b><security:authentication
					property="principal.username" /></h3></b> <br> <br>
		<div class="row">
			<div class="col-sm-6">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Primary Account</h5>
						<p class="card-text">$ ${primaryAccount.accountBalance}</p>
						<a
							href="${pageContext.request.contextPath}/account/primaryAccountDetails"
							class="btn btn-primary">View Details</a>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Savings Account</h5>
						<p class="card-text">$ ${savingsAccount.accountBalance}</p>
						<a
							href="${pageContext.request.contextPath}/account/savingsAccountDetails"
							class="btn btn-primary">View Details</a>
					</div>
				</div>
			</div>
		</div>
		<br> <br>
		<div class="row">
			<div class="col-sm-6">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Deposit</h5>
						<p class="card-text">Deposit amount to your accounts</p>
						<a href="${pageContext.request.contextPath}/account/depositAmount"
							class="btn btn-primary">Go to deposits</a>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Withdraw</h5>
						<p class="card-text">Withdraw amount from your accounts</p>
						<a
							href="${pageContext.request.contextPath}/account/withdrawAmount"
							class="btn btn-primary">Go to withdrawal</a>
					</div>
				</div>
			</div>
		</div>


		<%-- <div>
			<p>
				Primary Account Balance: ${primaryAccount.accountBalance} <br>
				<a
					href="${pageContext.request.contextPath}/account/primaryAccountDetails">View
					Details</a>
			</p>
		</div>

		<div>
			<p>
				Savings Account Balance: ${savingsAccount.accountBalance} <br>
				<a
					href="${pageContext.request.contextPath}/account/savingsAccountDetails">View
					Details</a>
			</p>
		</div>



		<div>
			<p>
				Withdraw Amount<br> <a
					href="${pageContext.request.contextPath}/account/withdrawAmount">Goto
					Withdrawal</a>
			</p>
		</div>

		<div>
			<p>
				Deposit Amount<br> <a
					href="${pageContext.request.contextPath}/account/depositAmount">Goto
					Deposit</a>
			</p>
		</div>

		<div>
			<p>
				Transfer Amount<br> <a
					href="${pageContext.request.contextPath}/transfer/betweenAccounts">Between
					Accounts</a><br> <a
					href="${pageContext.request.contextPath}/transfer/toSomeoneElseWithin">To
					Someone else from Zeppelin Bank</a><br> <a
					href="${pageContext.request.contextPath}/transfer/toSomeoneElseNotWithin">To
					Someone else from Other Bank</a>
			</p>
		</div>

		<div>
			<p>
				Add Recipients<br> <a
					href="${pageContext.request.contextPath}/transfer/externalrecipient/save">Add
					Recipient from another Bank</a><br> <a
					href="${pageContext.request.contextPath}/transfer/internalrecipient/save">Add
					Recipient from Zeppelin Bank</a>
			</p>
		</div>

		<div>
			<p>

				<a href="${pageContext.request.contextPath}/account/recipient">Add/Edit
					Recipients</a>
			</p>
		</div>



		<a href="${pageContext.request.contextPath}/">Back to Home Page</a> <br>

		<form:form action="${pageContext.request.contextPath}/logout"
			method="POST">

			<input type="submit" value="Logout" />

		</form:form> --%>
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