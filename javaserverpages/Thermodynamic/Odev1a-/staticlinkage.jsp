<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("duduklutencere.jsp")%>">D�d�kl� Tencere</a><br>
  <a href="<%=response.encodeURL("kure.jsp")%>">K�re</a><br>
  <a href="<%=response.encodeURL("manometre.jsp")%>">Manometre</a><br>
  <a href="<%=response.encodeURL("piston.jsp")%>">Piston</a><br>
  <a href="<%=response.encodeURL("silindir.jsp")%>">Silindir</a><br>

</body>
</html>
