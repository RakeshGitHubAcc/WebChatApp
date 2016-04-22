<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.webchatapp.hibernatebeans.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<spring:url value="/js/jquery-1.9.1.js" var="jqueryJs" />
<script src="${jqueryJs}"></script>
<script type="text/javascript">
              $(document).ready(function() {
                     getOnlineUsers();
                     getAllUsers();
                     getMyMessages();
                     setInterval(function(){getMyMessages()},10000);
                     setInterval(function(){getOnlineUsers()},20000);
              });
             
              function selectUser(string){
                     $("#users").val( string ).attr('selected',true);
              }
             
              function sendMessage(){
                     $.ajax({
                           type: "POST",
                          url: "sendMessage.htm",
                          data:"to="+$("#users option:selected").val()+"&message="+$("#message").val(),
                          success: function() {
                             $("#msgStatus").html("Message Sent Successfully...");
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                             $("#msgStatus").html("Error : "+e);
                          }
                     });
              }
             
              function getOnlineUsers(){
                     $.ajax({
                           type: "POST",
                          url: "getOnlineUsers.htm",
                          dataType: "json",
                          success: function(result) {
                             for(var i=0; i<result.length; i++){
                                    var oo = "<a href=javascript:selectUser('"+result[i].userId+"')><font color='blue'>"+result[i].userId +"</font></a>";
                                    if(i==0){
                                            $("#onlineUsers").html(oo);    
                                    }else{
                                            $("#onlineUsers").append(", "+oo);    
                                    }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getMyMessages(){
                     $.ajax({
                           type: "POST",
                          url: "getMyMessages.htm",
                          dataType: "json",
                          success: function(result) {
                             for(var i=0; i<result.length; i++){
                                    if(i  == 0){
                                           $("#maxVal").val(result[i].id);
                                    }else if(i == result.length-1){
                                           $("#minVal").val(result[i].id);
                                    }
                                    var time = "";
                                    time = result[i].time;
                                    var oo = "<tr bgcolor='gray' border='2' >"+
                                                              "<td>"+
                                                                            "<tr><td>"+result[i].message+"</td></tr>"+
                                                                            "<tr><td> by : "+result[i].senderName+" on "+time.day+"/"+time.month+"/"+time.year+" - "+time.hour+
                                                                                            ":"+time.min+":"+time.sec+"</td>"+
                                                                            "</tr>"+
                                                              "</td>"+
                                                  "</tr>"; 
                                     if(i==0){
                                            $("#inbox").html(oo);   
                                     }else{
                                            $("#inbox").append(oo); 
                                     }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getMyPrevMessages(){
                     $.ajax({
                           type: "POST",
                          url: "getPrev.htm",
                          dataType: "json",
                          data:"minVal="+$("#minVal").val(),
                          success: function(result) {
                             for(var i=0; i<result.length; i++){
                                    if(i  == 0){
                                           $("#maxVal").val(result[i].id);
                                    }else if(i == result.length-1){
                                           $("#minVal").val(result[i].id);
                                    }
                                    var time = "";
                                    time = result[i].time;
                                    var oo = "<tr bgcolor='gray' border='2' >"+
                                                              "<td>"+
                                                                            "<tr><td>"+result[i].message+"</td></tr>"+
                                                                            "<tr><td> by : "+result[i].senderName+" on "+time.day+"/"+time.month+"/"+time.year+" - "+time.hour+
                                                                              ":"+time.min+":"+time.sec+"</td>"+
                                                                                   "</tr>"+
                                                                     "</td>"+
                                                  "</tr>";
                                     if(i==0){
                                            $("#inbox").html(oo);   
                                     }else{
                                            $("#inbox").append(oo); 
                                     }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getMyNextMessages(){
                     $.ajax({
                           type: "POST",
                          url: "getMyMessages.htm",
                          dataType: "json",
                          data:"maxVal="+$("#maxVal").val(),
                          success: function(result) {
                             for(var i=0; i<result.length; i++){
                                    if(i  == 0){
                                           $("#maxVal").val(result[i].id);
                                    }else if(i == result.length-1){
                                           $("#minVal").val(result[i].id);
                                    }
                                    var time = "";
                                    time = result[i].time;
                                    var oo = "<tr bgcolor='gray' border='2' >"+
                                                              "<td>"+
                                                                            "<tr><td>"+result[i].message+"</td></tr>"+
                                                                            "<tr><td> by : "+result[i].senderName+" on "+time.day+"/"+time.month+"/"+time.year+" - "+time.hour+
                                                                              ":"+time.min+":"+time.sec+"</td>"+
                                                                                   "</tr>"+
                                                                     "</td>"+
                                                  "</tr>";
                                                  
                                     if(i==0){
                                            $("#inbox").html(oo);   
                                     }else{
                                            $("#inbox").append(oo); 
                                     }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
              function getAllUsers(){
                     $.ajax({
                           type: "POST",
                          url: "getAllUsers.htm",
                          dataType: "json",
                          success: function(result) {
                             for(var i=0; i<result.length; i++){
                                    var oo = "<option name='"+result[i].userName+"'>"+
                                                                     result[i].userName
                                                  "</option>";
                                                  if(i==0){
                                                         $("#users").html(oo);   
                                                  }else{
                                                         $("#users").append(oo); 
                                                  }
                             }
                          },
                          error: function(e){
                             alert("error"+e.toSource());
                          }
                     });
              }
             
       </script>
</head>
<body>
	<h1>
		Welcome..
		<% User user =  (User) request.getSession().getAttribute("USER"); %>
		<%=user.getUserName() %>
	</h1>
	<table>
		<tr>
			<td>
				<form>
					<table>
						<tr>
							<td>Select User :</td>
							<td><select id="users" name="to"></select></td>
							<td><a href="javascript:getAllUsers();"><font
									color="red">refresh Userlist</font></a></td>
						</tr>
						<tr>
							<td><textarea name="message" id="message" COLS="50" ROWS="8"></textarea>
							</td>
							<td>
								<div id="msgStatus"></div>
							</td>
						</tr>
						<tr>
							<td><a href="javascript:sendMessage();"><font
									color="blue">SEND</font></a></td>
						</tr>
					</table>
				</form>
			</td>
			<td><a href="logout.htm">LogOut</a></td>
		</tr>

		<tr>
			<td>
				<h3>Your Inbox</h3>
				<table id="inbox" border="2" height="400px" width="600px"
					bgcolor="green">

				</table>


			</td>
		</tr>

		<tr>
			<td>
				<form action="getPrev.htm">
					<table>
						<tr>
							<td><input type="hidden" id="minVal" name="minVal" /></td>
							<td><input type="button" value="previous"
								onclick="getMyPrevMessages();" /></td>
						</tr>
					</table>
				</form>
			</td>
			<td>
				<form action="getNext.htm">
					<table>
						<tr>
							<td><input type="hidden" id="maxVal" name="maxVal" /></td>
							<td><input type="button" value="next"
								onclick="getMyNextMessages();" /></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>

		<tr>
			<table border="2">
				<tr bordercolor="blue">
					<td bgcolor="green">Online Users</td>
				</tr>
				<tr bordercolor="blue">
					<td bgcolor="red">
						<div id="onlineUsers"></div>
					</td>
				</tr>
			</table>
		</tr>

	</table>
</body>
</html>