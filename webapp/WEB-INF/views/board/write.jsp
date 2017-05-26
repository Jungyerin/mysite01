<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="/mysite/board">
					<input type = "hidden" name = "a" value="write">
					<input type = "hidden" name = "userno" value="${userno }">
					<input type = "hidden" name = "bno" value="${bno }">
					<input type = "hidden" name = "gno" value="${gno }">
					<input type = "hidden" name = "ono" value="${ono }">
					<input type = "hidden" name = "depth" value="${depth }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">
								<c:choose>
									<c:when test="${gno == 0 }">									
										글쓰기
									</c:when>
									<c:otherwise>
										답글쓰기
									</c:otherwise>
								</c:choose>
							</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>