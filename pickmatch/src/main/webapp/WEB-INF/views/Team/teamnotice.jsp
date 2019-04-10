<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/_bootswatch.scss" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/_variables.scss" />
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<br><br><br>
<div>
	<h1 style="text-align: center;">팀 공지사항</h1>
</div>
<br><br>
<div id="team-notice">
	<table id="team-notice-table" class="table table-striped table-hover">
		<tr id="team-notice">
			<th id="team-notice-table-id">아이디</th>
			<th id="team-notice-table-title">제목</th>
			<th id="team-notice-table-date">날짜</th>
			
		</tr>
		<tr>
			<td id="team-notice-table-id">아이디</td>
			<td id="team-notice-table-title">제목</td>
			<td id="team-notice-table-date">날짜</td>
		</tr>
		<tr>
			<td id="team-notice-table-id">아이디</td>
			<td id="team-notice-table-title">제목</td>
			<td id="team-notice-table-date">날짜</td>
		</tr>
	
	</table>
</div>	

<div id="teamnotice-write-btn" align="right" style="margin-right: 200px; margin-top: 30px;">
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-primary" onclick="fn_write();"/>
</div>

<script>
	function fn_write(){
		location.href="${path}/team/teamnoticeWrite";
	}
</script>

<style>
	#team-notice-table{
		color:black;
		width:800px;
		border-collapse: collapse;
		margin: auto;
	}
	#team-notice-table tr,td{
		text-align:left;
		width:800px;
		height:20px;
		
	}
	
	#team-notice-table tr,th{
		text-align:center;
		width:800px;
		height:20px;
	
	}
	#team-notice-table-id{
		
		width:150px;
	
	}
	#team-notice-table-title{
		
		width:450px;

	}
		#team-notice-table-date{
		
		width:200px;

	}

</style>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>