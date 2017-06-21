<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newLine", "\n"); //\n을 저장할수없어서 변수에 저장해줌.
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<title>mysite</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<form
					action="${pageContext.servletContext.contextPath }/guestbook/add"
					method="post">
					<!--input type="hidden" name="a" value="add"-->
					<input type="hidden" name="guestbook" value="noajax">
					<table border="1" width="500">
						<tr>
							<td>이름</td>
							<td><input type="text" name="name" value="${authUser.name }"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pwd"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="message" cols="60" rows="5"></textarea></td>
						</tr>
						<tr>
							<td colspan="4" align="right"><input type="submit"
								VALUE=" 등록 "></td>
						</tr>
					</table>
				</form>
				<br>
				<c:set var="count" value="${fn:length(list) }" />
				<!-- int i=list.size(); -->
				<c:forEach items="${list }" var="vo" varStatus="status">
					<table width="510" border="1">
						<tr>
							<td>[${count-status.index }]</td>
							<!-- no값이 아니라 전체 list 수의 번호 순 -->
							<td>${vo.name }</td>
							<td>${vo.date }</td>
							<td><a
								href="${pageContext.servletContext.contextPath }/guestbook/delete/${vo.no }/${authUser.name }">삭제</a></td>
						</tr>
						<tr>
							<td colspan="4">${fn:replace(vo.message, newLine,"<br>") }</td>
							<!-- \n을 쓸수없기때문에 변수에 저장하여 넣어주어야 함. -->
						</tr>
					</table>
					<br>
				</c:forEach>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>

</body>
</html>