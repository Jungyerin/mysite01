<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<div id="navigation">
			<c:choose>
				<c:when test="${param.menu == 'main'}">
					<ul>
						<li class="selected"><a href="${pageContext.request.contextPath }/admin/main">메인페이지 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/guestbook">방명록 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/board">게시판 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/user">사용자 관리</a></li>
					</ul>
				</c:when>
				<c:when test="${param.menu == 'guestbook'}">
					<ul>
						<li><a href="${pageContext.request.contextPath }/admin/main">메인페이지 관리</a></li>
						<li class="selected"><a href="${pageContext.request.contextPath }/admin/guestbook">방명록 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/board">게시판 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/user">사용자 관리</a></li>
					</ul>
				</c:when>
				<c:when test="${param.menu == 'board'}">
					<ul>
						<li><a href="${pageContext.request.contextPath }/admin/main">메인페이지 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/guestbook">방명록 관리</a></li>
						<li class="selected"><a href="${pageContext.request.contextPath }/admin/board">게시판 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/user">사용자 관리</a></li>
					</ul>
				</c:when>		
				<c:when test="${param.menu == 'user'}">
					<ul>
						<li><a href="${pageContext.request.contextPath }/admin/main">메인페이지 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/guestbook">방명록 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/board">게시판 관리</a></li>
						<li class="selected"><a href="${pageContext.request.contextPath }/admin/user">사용자 관리</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<li><a href="${pageContext.request.contextPath }/admin/main">메인페이지 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/guestbook">방명록 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/board">게시판 관리</a></li>
						<li><a href="${pageContext.request.contextPath }/admin/user">사용자 관리</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
			</div>
			<div id="footer">&nbsp;</div>