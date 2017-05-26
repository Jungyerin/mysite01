<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>


				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list2) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count-(vo.rownum-1) }</td>
							<c:choose>
								<c:when test="${vo.depth > 0}">
									<td class="left" style="padding-left:${vo.depth * 20}px">
										<img
										src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:when>
								<c:otherwise>
									<td class="left">
								</c:otherwise>
							</c:choose>
							<a href="${pageContext.servletContext.contextPath }/board?a=view&bno=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.date }</td>
							<td><a
								href="${pageContext.servletContext.contextPath }/board?a=delete&userno=${no }&bno=${vo.no }"
								class="del">삭제</a></td>
						</tr>
					</c:forEach>
				</table>


				<div class="pager">
					<ul>

						<li><c:choose>
								<c:when test="${pageno < 1 }">
									<a href="${pageContext.servletContext.contextPath }/board?a=list&pageno=${pageno-1}">◀</a>
								</c:when>
								<c:otherwise>
									<a href="">◀</a>
								</c:otherwise>
							</c:choose></li>
						<!-- for문을 이용해서 해야함 -->

						<c:forEach var="i" begin="1" end="${fn:length(list2)/10 +1 }">
							<c:if test="${pageno==i }">
								<li class="selected">
							</c:if>
							<a href="${pageContext.servletContext.contextPath }/board?a=list&pageno=${i}">${i }</a>
							</li>
						</c:forEach>
						<li><c:choose>
								<c:when test="${pageno < i }">
									<a href="${pageContext.servletContext.contextPath }/board?a=list&pageno=${pageno+1}">▶</a>
								</c:when>
								<c:otherwise>
									<a href="">▶</a>
								</c:otherwise>
							</c:choose></li>
					</ul>
				</div>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>