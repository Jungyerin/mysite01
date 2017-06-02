<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/admin/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/admin/include/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-form">
				<c:choose>
					<c:when test="${adminvo == null }">
						<form method="post" action="${pageContext.request.contextPath }/admin/insert">
						<label class="block-label" for="title">insert사이트 타이틀</label>
						<input id="title" name="title" type="text" value="">
						
						<label class="block-label" for="welcomeMessage">환영 메세지</label>
						<input id="welcomeMessage" name="message" type="text" value="">

						<label class="block-label">프로필 이미지</label>
						<img id="profile" src="">
						<input type="file" name="file">

						<label class="block-label">사이트 설명</label>
						<textarea name="description"></textarea>
						
						<input type="submit" value="변경" />
						</form>
					</c:when>
					<c:otherwise>
						<form method="post" action="${pageContext.request.contextPath }/admin/update">
						<label class="block-label" for="title">사이트 타이틀</label>
						<input id="title" name="title" type="text" value="${adminvo.title }">
						
						<label class="block-label" for="welcomeMessage">환영 메세지</label>
						<input id="welcomeMessage" name="message" type="text" value="${adminvo.message }">

						<label class="block-label">프로필 이미지</label>
						<img id="profile" src="${adminvo.file }">
						<input type="file" name="file">

						<label class="block-label">사이트 설명</label>
						<textarea name="description">${adminvo.description }</textarea>
						
						<input type="submit" value="변경" />
						</form>
					</c:otherwise>
				</c:choose>			

				</div>
			</div>
			<c:import url="/WEB-INF/views/admin/include/navigation.jsp">
				<c:param name="menu" value="main" />
			</c:import>
		</div>
	</div>
</body>
</html>