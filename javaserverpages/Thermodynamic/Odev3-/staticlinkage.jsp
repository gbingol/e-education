<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("buzdolabi.jsp")%>">Buzdolab�</a><br>
  <a href="<%=response.encodeURL("cevreninentropidegisimi.jsp")%>">�evrenin Entropi De�i�imi</a><br>
  <a href="<%=response.encodeURL("isipompasi.jsp")%>">Is� pompas�</a><br>
  <a href="<%=response.encodeURL("suyunentropidegisimi.jsp")%>">Suyun Entropi De�i�imi</a><br>

</body>
</html>
