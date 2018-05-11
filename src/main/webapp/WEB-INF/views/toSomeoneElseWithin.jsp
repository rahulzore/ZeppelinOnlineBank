<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
				
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer-home">Home</a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Accounts </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/account/primaryAccountDetails">Primary Account</a> <a
							class="dropdown-item" href="${pageContext.request.contextPath}/account/savingsAccountDetails">Savings Account</a>
					</div></li>
					
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Transfer </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/transfer/toSomeoneElseWithin">To Someone else from Zeppelin Bank</a> 
						<a class="dropdown-item" href="${pageContext.request.contextPath}/transfer/toSomeoneElseNotWithin">To Someone else from Other Bank</a>
						<div class="dropdown-divider"></div>
          				<a class="dropdown-item" href="${pageContext.request.contextPath}/transfer/betweenAccounts">Between Accounts</a>
					</div></li>	
					
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Add Recipients </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/transfer/internalrecipient/save">Add Recipient from Zeppelin Bank</a> <a
							class="dropdown-item" href="${pageContext.request.contextPath}/transfer/externalrecipient/save">Add Recipient from another Bank</a>
					</div></li>
				
			</ul>
			
		


		<form:form action="${pageContext.request.contextPath}/logout"
			method="POST">

			<button class="btn btn-danger" type="submit">Logout</button>

		</form:form>
		</div>
	</nav>
	
	<div class="container">
	<br>
	<h3>Transfer Amount to Accounts which belong to Zeppelin Bank</h3>
	<br>
	<form:form action="${pageContext.request.contextPath}/transfer/toSomeoneElseWithin" method="POST">
	<p>
		Please select the account you would like to transfer from:
		<select value="${accountType}" required="required" name="accountType" id="accountType" required="required">
			<option disabled="disabled" selected="selected">--Select your Account--</option>
			<option>Primary</option>
			<option>Savings</option>
		</select>
		<br><br>
		Please select the recipient:
		<select value="${recipientName}" required="required" name="recipientName" id="recipientName" required="required">
			<option disabled="disabled" selected="selected">--Select your Account--</option>
			<c:forEach items="${recipientList}" var="list">
				 <c:if test="${list != selected}">
				<option value="${list.name}">${list.name}</option>
				</c:if>
				
			
			</c:forEach>
		</select>
		
		<br><br>
		Please enter the amount you would like to withdraw:
		<input type="number" value="${amount}" name="amount" required>
		<br>
		<br>
		<button class="btn btn-primary" type="submit">Transfer</button>
	</p>
	
	
	 </form:form>
	
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