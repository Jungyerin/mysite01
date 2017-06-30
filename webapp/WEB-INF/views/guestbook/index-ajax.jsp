<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
	pageContext.setAttribute("newLine", "\n"); //\n을 저장할수없어서 변수에 저장해줌.
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset {
	float: none;
	text-align: center;
}

.ui-dialog .ui-dialog-buttonpane button {
	margin-left: 10px;
	margin-right:auto;
}

#dialog-message p {
	padding: 20px 0;
	font-weight : bold;
	font-size : 1.0em;
}

#dialog-delete-form p{
	padding : 10px;
	font-weight : bold;
	font-size : 1.0em;
}

#dialog-delete-form p.error {
	color:#f00;
}

#dialog-delete-form input[type="password"] {
	padding : 5px;
	border : 1px solid #888;
	outline : none;
	width : 180px;
}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var isEnd = false;

	var messageBox = function(title, message, callback) {
		$("#dialog-message").attr("title", title);
		$("#dialog-message p").text(message);
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				"확인" : function() {
					$(this).dialog("close");
				},
				Cancel : function() {
					console.log("Cancel clicked");
				}
			},
			close : callback || function() { //||앞의 명제가 참이면 function을 실행하지 않고 거짓이면 function실행

			}
		});
	}
	var render = function(vo, mode) {
		//상용 app에서는 template library를 사용한다 ex)ejs, leaf 
		var html = "<li data-no='"+vo.no+"'>" + "<strong>" + vo.name
				+ "</strong>" + "<p>" + vo.message.replace(/\n/gi, "<br>")
				+ "</p>" + //g는 global로 전체 개행에 적용 
				"<a href='#dialog-delete-form' data-no='"+ vo.no +"'>삭제</a>"
				+ "</li>";

		if (mode === true) {
			$("#list-guestbook").prepend(html);
		} else {
			$("#list-guestbook").append(html);
		}

	}

	var fetchList = function() {
		if (isEnd === true) {
			return;
		}
		var startNo = $("#list-guestbook li").last().data("no") || 0; //false이면(startNo값이 null이면) 0을 실행
		/*if(startNo !== null){
			startNo=0;
		}*/
		console.log("-------->" + startNo);

		$.ajax({
			url : "${pageContext.request.contextPath }/guestbook/api/list?sno="
					+ startNo,
			type : "get",
			dataType : "json",
			data : "",
			//contentType: 'application/json', //JSON타입으로 데이터를 보낼 때 사용.
			success : function(response) {
				if (response.result === "fail") {
					console.error(response.message);
					return;
				}

				//detect end
				if (response.data.length < 5) {
					isEnd = true;
					$("#btn-next").prop("disabled", true);
				}
				//rendering
				$.each(response.data, function(index, vo) {
					render(vo);
				});

			},
			error : function(jqXHR, status, e) {
				console.error(status + " : " + e);
			}
		});
	}

	$(function() {
		
		var dialogDelete = $( "#dialog-delete-form" ).dialog({
		      autoOpen: false,
		      height: 180,
		      width: 300,
		      modal: true,
		      buttons: {
		        "삭제": function(){
		        	var no = $("#delete-no").val();
		        	var password = $("#delete-password").val();
		        	
					//console.log(no+":"+password);
					//ajax 통신
		        	$.ajax({
						url : "${pageContext.request.contextPath }/guestbook/api/delete",
						type : "post",
						dataType : "json",
						data : "no="+no+"&"+
						"pwd="+password,
					success : function(response) {
							if (response.result === "fail") {
								console.error(response.message);
								return;
							}
							
							//삭제 실패
							if(response.data === -1){
								$("#dialog-delete-form .validateTips.normal").hide();
								$("#dialog-delete-form .validateTips.error").show();
								$("#delete-password").val("");
								return;
							}
							
							//삭제 성공
							$("#list-guestbook li[data-no='"+response.data+"']").remove();
							dialogDelete.dialog("close");
							
					},
					error : function(jqXHR, status, e) {
							console.error(status + " : " + e);
						}
					});
					
		        },
		        "취소": function() {
		          	dialogDelete.dialog( "close" );
		        }
		      },
		      close: function() {
					$("#dialog-delete-form .validateTips.normal").show();
					$("#dialog-delete-form .validateTips.error").hide();
					$("#delete-password").val("");
		      }
		    });

		//live event
		$(document).on("click", "#list-guestbook li a", function(event) {
			event.preventDefault();
			var no = $(this).data("no");
			$("#delete-no").val(no);
			dialogDelete.dialog("open");
		});

		$("#add-form")
				.submit(
						function(event) {
							//submit event 기본 동작을 막음
							//posting을 막음 함수로 통신하기 위해서
							event.preventDefault();

							//validate form data 
							var name = $("#input-name").val();
							if (name === "") {
								//alert("이름은 필수 입력 항목입니다.");
								messageBox("방명록에 글 남기기", "이름은 필수 입력 항목입니다.",
										function() {
											$("#input-name").focus();
										});
								$("#input-name").focus();
								return;
							}

							var password = $("#input-password").val();
							if (password === "") {
								messageBox("방명록에 글 남기기", "비밀번호는 필수 입력 항목입니다.",
										function() {
											$("#input-name").focus();
										});
								return;
							}

							var message = $("#ta-message").val();
							if (message == "") {
								messageBox("방명록에 글 남기기", "내용은 필수 입력 항목입니다.",
										function() {
											$("#ta-message").focus();
										});
								return;
							}

							console.log("validation.ok");

							$.ajax({
									url : "${pageContext.request.contextPath }/guestbook/api/add",
									type : "post",
									dataType : "json",
									data : "name=" + name + "&" + "pwd="
											+ password + "&" + "message="
											+ message,
								success : function(response) {
										if (response.result === "fail") {
											console.error(response.message);
											return;
										}
											//rendering
										console.log(response.data.name);
										render(response.data, true);
											//reset form
										$("#add-form")[0].reset();
								},
								error : function(jqXHR, status, e) {
										console.error(status + " : " + e);
									}
								});
						});

		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			//scrollbar thumb가 바닥전 10px까지 왔을 때...
			if (scrollTop + windowHeight + 10 > documentHeight) {
				fetchList();
			}
		});
		$("#btn-next").click(function() {
			fetchList();
		});

		//최초 리스트 가져오기
		fetchList();
	})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" name="name" placeholder="이름">
					<input type="password" name="pwd" id="input-password"
						placeholder="비밀번호">
					<textarea id="ta-message" name="message" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>

				<hr />

				<ul id="list-guestbook">

				</ul>
				<div style="margin: 20px 0; text-align: center">
					<button id="btn-next" style="padding: 10px 20px">다음</button>
				</div>

			</div>

			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>

			<div id="dialog-delete-form" title="메세지 삭제" style="display : none">
				<p class="validateTips normal">
					작성시 입력했던 비밀번호를 입력하세요.
				</p>
				<p class="validateTips error" style="display:none">
					비밀번호가 틀립니다.
				</p>
				<form>
					<input type="hidden" name="no" id="delete-no" value=""/>
					<input type="password" name="password" id="delete-password" value="" class="text ui-widget-content ui-corner-all">			
					<!-- Allow form submission with keyboard without duplicating the dialog button -->
					<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
				</form>
			</div>

		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>