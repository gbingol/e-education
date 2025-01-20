<%@ page language="java" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <A href="<%=response.encodeURL("kritikcap.jsp")%>">Kritik Çap</A><BR>
  <A href="<%=response.encodeURL("sinirtabaka.jsp")%>">Sınır Tabaka</A><BR>
  <A href="<%=response.encodeURL("colburnanalojisi.jsp")%>">Colburn Analojisi</A><br>
  <A href="<%=response.encodeURL("boruicindehava.jsp")%>">Boru İçindeki Hava</A><br>

</body>
</html>
