<%@ page language="java" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
   <a href="<%=response.encodeURL("borudandogaltasinim.jsp")%>">Borudan Doğal Taşınım</a><br>
  <a href="<%=response.encodeURL("isinimkalkani.jsp")%>">Işınım Kalkanı</a><br>
  <a href="<%=response.encodeURL("isitransferkatsayisi.jsp")%>">Isı Transfer Katsayısı</a><br>
  <a href="<%=response.encodeURL("silindirikmetaleleman.jsp")%>">Silindirik Metal Eleman</a><br>
  <a href="<%=response.encodeURL("somine.jsp")%>">Şömine</a><br>
</body>
</html>
