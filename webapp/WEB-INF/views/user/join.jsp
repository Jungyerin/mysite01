<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	window.addEventListener("load", function() {		

		document.getElementById("join-form").onsubmit = function() {

			var inputName = document.getElementById("name");
			if (inputName.value === "") {
				alert("이름은 필수 항목입니다.");
				inputName.focus();
				return false;
			}
			else if(inputName.value.length <= 1){
				alert("이름은 2~8자 이상 입니다.");
				inputName.focus();
				return false;
			}
			
			var inputEmail = document.getElementById("email");
			if (inputEmail.value === "") {
				alert("이메일은 필수 항목입니다!");
				inputEmail.focus();
				return false;
			}

			var imageCheck = document.getElementById("check-image");
			if (imageCheck.style.display === "none") {
				alert("이메일 중복체크를 해주세요!");
				return false;
			}

			var inputPwd = document.getElementById("pwd");
			if (inputPwd.value === "") {
				alert("비밀번호는 필수 항목입니다!");
				inputPwd.focus();
				return false;
			}

			var inputCheck = document.getElementById("agree-prov");
			if (inputCheck.checked === false) {
				alert("약관에 동의하여 주십시오.");
				return false;
			}

			return true;
		};
		
		document.getElementById("email").addEventListener("change", function(){
			var imageCheck = document.getElementById("check-image");
			var checkButton = document.getElementById("check-button");			
			imageCheck.style.display = "none";
			checkButton.style.display = "";
		});

		document.getElementById("check-button").addEventListener("click",
				function() {

					$.ajax({
						url : "/mysite01/user/api/checkemail?email=" + email.value,
						type : "get",
						dataType : "json",
						data : "",
						success : function(response) {
							console.log(response);
							if (response.data == true) {
								alert("이미 존재하는 이메일 입니다. 다른 이메일을 사용해주세요.");
								//이메일 입력창에 포커스 주기
								console.log("이메일 중복");
								inputEmail.focus();

							} else {
								var imageCheck = document.getElementById("check-image");
								var checkButton = document.getElementById("check-button");
								//체크된 이미지를 보여지게 하고 중복체크 안보이게
								imageCheck.style.display = "";
								checkButton.style.display = "none";
							}
						},
						error : function(jqXHR, status, error) {
							console.error(status + " : " + error);
						}
					});
				})
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="user">
			
				<form:form modelAttribute="userVo" id="join-form" name="joinForm"
					method="post"
					action="${pageContext.servletContext.contextPath }/user/join">

					<label class="block-label" for="name"><spring:message
							code="name" text="이름" /></label>
					<input id="name" name="name" type="text" value="${userVo.name }">
					<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('name') }">
							<p style="text-align: left; color: red">
								<strong> <spring:message
										code="${errors.getFieldError( 'name' ).codes[0] }"
										text="${errors.getFieldError( 'name' ).defaultMessage }" />
								</strong>
							</p>
						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					<img src="${pageContext.servletContext.contextPath }/assets/images/email-check (2).png"
						style="display:none" id="check-image" />
					<input type="button" id="check-button" value="중복체크" style="display:;">
					<p style="margin:0; padding:0; color:red; text-align:left">
						<form:errors path="email" />		
					</p>

					<label class="block-label">패스워드</label>
					<form:password path="pwd"/>

					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female"
							checked="checked"> <label>남</label> <input type="radio"
							name="gender" value="male">
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					<input type="submit" value="가입하기">
				</form:form>
				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>