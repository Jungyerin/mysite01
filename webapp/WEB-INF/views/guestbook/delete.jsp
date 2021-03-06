<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook" class="delete-form">
				<form method="post" action="${pageContext.servletContext.contextPath }/guestbook/delete">
					<input type="hidden" name="a" value="delete">
					<input type='hidden' name="no" value="${no }">
					<input type='hidden' name="name" value="${name }">
					<label>비밀번호</label>
					<input type="password" name="pwd">
					<input type="submit" value="확인">
				</form>
				<a href="">방명록 리스트</a>
			</div>
		</div>
			<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>