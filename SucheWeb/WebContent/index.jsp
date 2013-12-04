<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="wir6.suche.test.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<title>GlassFish JSP Page</title>
  </head>
  <body>
    <h1>Hello World!</h1>
    <%
    TestBeanRemote test = (TestBeanRemote) new InitialContext().lookup("ejb/test");
    %>
    <%=test.getMessage() %>
    <h3></h3>
  </body>
</html> 
