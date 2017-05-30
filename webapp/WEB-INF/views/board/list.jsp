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
					<form id="search_form" action="${pageContext.request.contextPath }/board/list" method="get">
					<input type="text" id="kwd" name="keyword" value="${map.keyword }">					
					<input type="submit" value="찾기">
				</form>

				<c:if test="${map.keyword!=''}">
					<a href="${pageContext.servletContext.contextPath }/board/list">전체글보기</a>
				</c:if>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${map.count }" />
					<c:forEach items="${map.list }" var="vo" varStatus="status">
						<tr>
							<td>${map.count-(vo.rownum-1) }</td>
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
							<a
								href="${pageContext.servletContext.contextPath }/board/view?bno=${vo.no }&keyword=${map.keyword }&pageno=${map.pageno }">${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.date }</td>
							<td><c:if test="${vo.userno==authUser.no}">
									<a
										href="${pageContext.servletContext.contextPath }/board/delete?bno=${vo.no }"
										class="del">삭제</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>


				<div class="pager">
					<ul>
						<c:if test="${map.prevP > 0 }" >
							<li><a href="${pageContext.servletContext.contextPath }/board/list?pageno=${map.prevP }&keyword=${map.keyword }">◀</a></li>
						</c:if>
						
						<c:forEach var="i" begin="1" end="${map.count/map.listsize + 1  }">
							<c:choose>
								<c:when test="${map.endP < i }">
									<li>${i }</li>
								</c:when>
								<c:when test="${map.pageno == i }">
									<li class="selected">${i }</li>
								</c:when>
								<c:otherwise> 
									<li><a href="${pageContext.request.contextPath }/board/list?pageno=${i }&keyword=${map.keyword }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:if test="${map.nextP > 0 }" >
							<li><a href="${pageContext.request.contextPath }/board/list?pageno=${map.nextP }&keyword=${map.keyword }">▶</a></li>
						</c:if>

					</ul>
				</div>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/write?pageno=${map.pageno }&keyword=${keyword }" id="new-book">글쓰기</a>
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