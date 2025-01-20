<%@ page language="java" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
   <a href="<%=response.encodeURL("cikissicakligi.jsp")%>">Çıkış Sıcaklığı</a><br>
  <a href="<%=response.encodeURL("civalitermometre.jsp")%>">Civalı Termometre</a><br>
  <a href="<%=response.encodeURL("suyundengesicakligi.jsp")%>">Suyun Denge Sıcaklığı</a><br>
</body>
</html>
