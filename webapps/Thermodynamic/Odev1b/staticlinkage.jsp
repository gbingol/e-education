<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("buharlasansivi.jsp")%>">Buharla�an S�v�</a><br>
  <a href="<%=response.encodeURL("odadahava.jsp")%>">Odadaki Havan�n K�tlesi</a><br>
  <a href="<%=response.encodeURL("pistonsilindir.jsp")%>">Piston-Silindir ��inde Su Buhar�</a><br>
  <a href="<%=response.encodeURL("r12ozellikleri.jsp")%>">R-12 Ak��kan�n�n �zellikleri</a><br>
  <a href="<%=response.encodeURL("sabithacimdesu.jsp")%>">Sabit Hacimde Su</a><br>
  <a href="<%=response.encodeURL("tableevaluation.jsp")%>">Tablo Okuma</a><br>

</body>
</html>
