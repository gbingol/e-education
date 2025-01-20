<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("buharlasansivi.jsp")%>">Buharlaşan Sıvı</a><br>
  <a href="<%=response.encodeURL("odadahava.jsp")%>">Odadaki Havanın Kütlesi</a><br>
  <a href="<%=response.encodeURL("pistonsilindir.jsp")%>">Piston-Silindir İçinde Su Buharı</a><br>
  <a href="<%=response.encodeURL("r12ozellikleri.jsp")%>">R-12 Akışkanının Özellikleri</a><br>
  <a href="<%=response.encodeURL("sabithacimdesu.jsp")%>">Sabit Hacimde Su</a><br>
  <a href="<%=response.encodeURL("tableevaluation.jsp")%>">Tablo Okuma</a><br>

</body>
</html>
