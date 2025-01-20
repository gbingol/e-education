<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("buzdolabi.jsp")%>">Buzdolabı</a><br>
  <a href="<%=response.encodeURL("cevreninentropidegisimi.jsp")%>">Çevrenin Entropi Değişimi</a><br>
  <a href="<%=response.encodeURL("isipompasi.jsp")%>">Isı pompası</a><br>
  <a href="<%=response.encodeURL("suyunentropidegisimi.jsp")%>">Suyun Entropi Değişimi</a><br>

</body>
</html>
