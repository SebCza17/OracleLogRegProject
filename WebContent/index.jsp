<%@page import="entities.LogonEntity"%>
<%@page import="dao.LogonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="model.ConDB"%>
<%@ page import="model.Hash"%>
<%@ page import="dao.UserDAO"%>
<%@ page import="entities.UserEntity"%>
<%@ page import="entities.AllowedIPEnitity"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@ page import="java.net.InetAddress" %>
   

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style type="text/css"> <%@include file="/WEB-INF/css/main.css" %></style>
<script>
$(document).ready(function(){
  $('.message a').click(function(){
   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
	});
});
</script>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
<%if(session.getAttribute("user") == null){%>
	<div class="login-page">
		<div class="form">
			<form action="Login" method="post" class="login-form">
				<input type="email" name="email" placeholder="email" required="required"><br>
				<input type="password" name="password" placeholder="password" required="required"><br>
				<button>Submit</button>
				<p class="message">Not registered? <a href="#">Create an account</a></p>
			</form>
			<%if(request.getParameter("b") != null){%>
				<%if(request.getParameter("b").equals("ERROR")){%>
					<h5>Wrong Email!</h5>
				<%}else if(request.getParameter("b").equals("PASSWORD")){%>
					<h5>Wrong Password!</h5>
				<%}else if(request.getParameter("b").equals("BLOCKED")){%>
					<h5>Your account is Blocked, need wait max 15 minutes.</h5>
				<%}else{%>
					<h5>Not allowed IP: <%=request.getParameter("b")%></h5>
				<%}%>
			<%}%>
			<form action="Register" method="post" class="register-form">
				<input type="text" name="username" placeholder="username" required="required"><br>
				<input type="email" name="email" placeholder="email" required="required"><br>
				<input type="password" name="password" placeholder="password" required="required"><br>
				<input type="password" name="rePassword" placeholder="re-password" required="required"><br>
				<input type="date" name="birthDay"><br>
				<button>Submit</button>
		   		<p class="message">Already registered? <a href="#">Sign In</a></p>
			</form>
			<%if(request.getParameter("b") != null){%>
				<%if(request.getParameter("b").equals("PASSWORD2")){%>
					<h5>Password NOT same!</h5>
				<%}else if(request.getParameter("b").equals("EMAIL")){%>
					<h5>Email already Used!</h5>
				<%}%>
			<%}%>
		</div>
	</div>
<%}else{%>
	<%UserEntity userEntity = (UserEntity) session.getAttribute("user");%>
	<div class="includePart">
		<div class="navbar" style="text-align: center;">
			<form action="Logout" method="post">
				<div class="dropdown">
					<button class="dropbtn" type="submit">Logout</button>
				</div>
			</form>
			<form action="Menu" method="post">
				<input type="hidden" name="type" value="ip">
				<div class="dropdown">
					<button class="dropbtn" type="submit">IP manager</button>
				</div>
			</form>
			<form action="Menu" method="post">
				<input type="hidden" name="type" value="history">
				<div class="dropdown">
					<button class="dropbtn" type="submit">History</button>
				</div>
			</form>
			<form action="" method="get">
				<input type="hidden" name="m" value="password">
				<div class="dropdown">
					<button class="dropbtn" type="submit">Change Password</button>
				</div>
			</form>
		</div>
	</div>
	<div class="includePart">
		<table id="myTable">
			<tr class="header">
				<th>Username</th>
				<th>Birth Day</th>
			</tr>
			<tr>
				<td><%=userEntity.getUsername()%></td>
				<td><%=userEntity.getBirthDay().toString()%></td>
			</tr>
		</table>
	</div>
	<%if(request.getParameter("m") != null){%>
	
	<div class="includePart">
		<%if(request.getParameter("m").equals("ip")){%>
		<div>
			<table id="myTable">
				<tr class="header">
					<th>IP</th>
					<th>Add Date</th>
					<th>Action</th>
				</tr>
				<%ArrayList<AllowedIPEnitity> ipList = (ArrayList<AllowedIPEnitity>) session.getAttribute("ipList");%>
				<%for(AllowedIPEnitity ipElement : ipList){%>
					<tr>
						<form action="DelIP" method="POST">
							<input type="hidden" value="<%=ipElement.getId()%>" name="idIP">
							<td><%=ipElement.getIp()%></td>
							<td><%=ipElement.getTime_stamp()%></td>
							<td>
								<div class="dropdown">
									<button class="dropbtn" type="Del"	>Delete</button>
								</div>
							</td>
						</form>
					</tr>
				<%}%>
				<tr>
					<td><input type="text" form="formAddIP" placeholder="1.1.1.1" name="newIP" required="required"></td>
					<td></td>
					<td>
						<div class="dropdown">
							<button class="dropbtn" type="Add"form="formAddIP" >Add</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<%} else if(request.getParameter("m").equals("history")){%>
		<div>
			<table id="myTable">
				<tr class="header">
					<th>IP</th>
					<th>Add Date</th>
					<th>Status</th>
				</tr>
				<%ArrayList<LogonEntity> logonEntityList = (ArrayList<LogonEntity>) session.getAttribute("logonEntityList");%>
				<%for(LogonEntity logonElement : logonEntityList){%>
					<tr>
						<td><%=logonElement.getIp()%></td>
						<td><%=logonElement.getTime_stamp()%></td>
						<td><%=logonElement.getStatus()%></td>
					</tr>
				<%}%>
			</table>
		</div>
		<%} else if(request.getParameter("m").equals("password")){%>
		<div>
			<table id="myTable">
				<tr class="header">
					<form action="Menu" method="POST">
						<input type="hidden" value="password" name="type">
						<td><input type="password" name="oldPassword" placeholder="Old Password"></td>
						<td><input type="password" name="newPassword" placeholder="New Password"></td>
						<td><input type="password" name="reNewPassword" placeholder="Re New Password"></td>
						<td>
							<div class="dropdown">
								<button class="dropbtn" type="submit">Submit</button>
							</div>
						</td>
					</form>
				</tr>
			</table>
		</div>
		<%}%>
	<%}%>
	</div>
	<%if(request.getParameter("b") != null){%>
		<%if(request.getParameter("b").equals("")){%>
			<h5>Password Changed</h5>
		<%}else if(request.getParameter("b").equals("PASSWORD")){%>
			<h5>Wrong Old Password!</h5>
		<%}else if(request.getParameter("b").equals("NEWPASSWORD")){%>
			<h5>You can't use last 5 Password</h5>
		<%}else if(request.getParameter("b").equals("SAME")){%>
			<h5>You can't use last 5 Password</h5>
		<%}%>
	<%}%>
<%}%>
<form action="AddIP" method="POST" id="formAddIP" style="display: none"></form>

<%
		Hash hash = new Hash();
		System.out.println(hash.hashPassword("Seba")); %>

</body>
</html>