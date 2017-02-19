<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<style>
	table, th, tr, td {
		border: 1px solid black;
		text-align: center;
	}
	div{
		margin-top:15px;
		margin-bottom:15px;
	}
</style>

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Home</a></li>
					<!-- <li><a href="Test">test</a></li> -->
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container">
		<div>${recordCount } </div>
		<div class="form">
			<form action="UserSubmit" method="post">
				<div class="field-wrap">
					<label> Stock ID<span class="req">*</span>
					</label> <input name="stockID" type="text" required autocomplete="off" maxlength="4" />
				</div>
				<button type="submit" class="button button-block">Search</button>
			</form>

		</div>
	</div>
	<c:if test="${not empty(noResult)}">
		<script type="text/javascript">
			alert("${noResult}");
		</script>
	</c:if>

	<script src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/main.js"></script>

</body>

</html>