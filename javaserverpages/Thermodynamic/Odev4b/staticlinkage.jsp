<%@ page language="java"  import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("sogutmacevrimi.jsp")%>">So�utma �evrimi-1</a><br>
  <a href="<%=response.encodeURL("sogutmacevrimi2.jsp")%>">So�utma �evrimi-2</a><br>
  <a href="<%=response.encodeURL("sogutmacevrimi3.jsp")%>">So�utma �evrimi-3</a><br>
  <a href="<%=response.encodeURL("sogutmacevrimi4.jsp")%>">So�utma �evrimi-4</a><br>
  <a href="<%=response.encodeURL("sogutmacevrimi5.jsp")%>">So�utma �evrimi-5</a><br>
</body>
</html>
