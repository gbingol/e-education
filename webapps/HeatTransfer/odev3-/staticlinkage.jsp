<%@ page language="java" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
   <a href="<%=response.encodeURL("borudandogaltasinim.jsp")%>">Borudan Do�al Ta��n�m</a><br>
  <a href="<%=response.encodeURL("isinimkalkani.jsp")%>">I��n�m Kalkan�</a><br>
  <a href="<%=response.encodeURL("isitransferkatsayisi.jsp")%>">Is� Transfer Katsay�s�</a><br>
  <a href="<%=response.encodeURL("silindirikmetaleleman.jsp")%>">Silindirik Metal Eleman</a><br>
  <a href="<%=response.encodeURL("somine.jsp")%>">��mine</a><br>
</body>
</html>
