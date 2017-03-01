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

<!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<style>
table, th, tr, td {
	border: 1px solid black;
	text-align: center;
}

div {
	margin-top: 15px;
	margin-bottom: 15px;
}
</style>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Home</a></li>
					<!--  <li><a href="Test">test</a></li> -->
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<c:if test="${not empty stockInfo}">
			<div align="center">

				<table style="width: 100%;">
					<tr>
						<th colspan="5">基 本 資 料 (Basic Info)</th>
					</tr>
					<tr>
						<td colspan="2">股票代號 (Stock ID)</td>
						<td colspan="3">${stockInfo.stockID }</td>
					</tr>
					<tr>
						<td colspan="2">公司簡稱 (Company Name)</td>
						<td colspan="3">${stockInfo.stockName }</td>
					</tr>
					<tr>
						<td colspan="2">產業類別 (Industry)</td>
						<td colspan="3">${stockInfo.businessType }</td>
					</tr>
					<tr>
						<td colspan="2">董事長 (Chairman)</td>
						<td colspan="3">${stockInfo.president }</td>
					</tr>
					<tr>
						<td colspan="2">股本 (Capital)</td>
						<td colspan="3">${stockInfo.capital }</td>
					</tr>
					<tr>
						<td colspan="2">更新日期 (Updated)</td>
						<td colspan="3">${stockInfo.lastUpdate }</td>
					</tr>

				</table>
				
			</div>
			<div
				style="width: 100%; height:150px; background:url(${pageContext.servletContext.contextPath}/getImageMonth/${stockInfo.stockID}) no-repeat center;">
			</div>

			<div
				style="width:100%; height:150px; background:url(${pageContext.servletContext.contextPath}/getImageQuart/${stockInfo.stockID}) no-repeat center;">
			</div>
		</c:if>

		<div align="center">

			<table style="width: 100%;">
				<tr>
					<th colspan="9">每 月 營 收 變 化 (Monthly revenue changes)</th>
				</tr>
				<tr>
					<th colspan="3">105 年 度</th>
					<th colspan="6">106 年 度</th>
				</tr>
				<tr>
					<td>月</td>
					<td>營 收</td>
					<td>年增率</td>
					<td>月</td>
					<td>營 收</td>
					<td>年增率</td>
					<td>累計營收</td>
					<td>年增率</td>
					<td>達成率</td>

				</tr>
				<c:forEach items="${stockInfo.stockMonthlyBeans}" var="monthInfo"
					varStatus="status">
					<tr>
						<td>${status.count}</td>
						<c:choose>
							<c:when test="${monthInfo.revenueIn105 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.revenueIn105}</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${monthInfo.increaseRateIn105 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.increaseRateIn105}%</td>
							</c:otherwise>
						</c:choose>

						<td>${status.count}</td>

						<c:choose>
							<c:when test="${monthInfo.revenueIn106 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.revenueIn106}</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${monthInfo.increaseRateIn106 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.increaseRateIn106}%</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${monthInfo.accumulateRevenueIn106 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.accumulateRevenueIn106}</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${monthInfo.accumulateIncreaseRateIn106 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${monthInfo.accumulateIncreaseRateIn106}%</td>
							</c:otherwise>
						</c:choose>

						<td>${monthInfo.achieveRateIn106}</td>
					</tr>
				</c:forEach>

			</table>
		</div>

		<div align="center">

			<table style="width: 100%;">
				<tr>
					<th colspan="7">每 季 稅 後 盈 餘 變 化 (Quarterly after-tax surplus
						changes)</th>
				</tr>
				<tr>
					<th colspan="3">104 年 度</th>
					<th colspan="4">105 年 度</th>
				</tr>
				<tr>
					<td>季</td>
					<td>稅 後 盈 餘</td>
					<td>年 增 率</td>
					<td>季</td>
					<td>稅 後 盈 餘</td>
					<td>年 增 率</td>
					<td>達 成 率</td>
				</tr>
				<c:forEach items="${stockInfo.stockQuarterlyBeans}" var="quartInfo"
					varStatus="status">
					<tr>
						<td>${status.count}</td>
						<c:choose>
							<c:when test="${quartInfo.profitAT104 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${quartInfo.profitAT104}</td>
							</c:otherwise>
						</c:choose>

						<td>${quartInfo.profitRateAT104}</td>
						<td>${status.count}</td>

						<c:choose>
							<c:when test="${quartInfo.profitAT105 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${quartInfo.profitAT105}</td>
							</c:otherwise>
						</c:choose>

						<td>${quartInfo.profitRateAT105}</td>
						<td>${quartInfo.achieveRateAT105}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<div align="center">

			<table style="width: 100%;">
				<tr>
					<th colspan="7">每 季 稅 前 盈 餘 變 化 (Quarterly before-tax surplus
						changes)</th>
				</tr>
				<tr>
					<th colspan="3">104 年 度</th>
					<th colspan="4">105 年 度</th>
				</tr>
				<tr>
					<td>季</td>
					<td>稅 前 盈 餘</td>
					<td>年 增 率</td>
					<td>季</td>
					<td>稅 前 盈 餘</td>
					<td>年 增 率</td>
					<td>達 成 率</td>
				</tr>
				<c:forEach items="${stockInfo.stockQuarterlyBeans}" var="quartInfo"
					varStatus="status">
					<tr>
						<td>${status.count}</td>
						<c:choose>
							<c:when test="${quartInfo.profitBT104 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${quartInfo.profitBT104}</td>
							</c:otherwise>
						</c:choose>

						<td>${quartInfo.profitRateBT104}</td>
						<td>${status.count}</td>

						<c:choose>
							<c:when test="${quartInfo.profitBT105 eq 0}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td>${quartInfo.profitBT105}</td>
							</c:otherwise>
						</c:choose>

						<td>${quartInfo.profitRateBT105}</td>
						<td>${quartInfo.achieveRateBT105}</td>
					</tr>
				</c:forEach>
			</table>
		</div>


	</div>

	<!--  <a href="/" class="btn btn-default logout">LogOut</a>-->
	<script src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</body>

</html>