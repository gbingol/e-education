<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("isitma.jsp")%>">�nsan Konforu ve �klimlendirme</a><br>
  <a href="<%=response.encodeURL("psikrometritablo.jsp")%>">Psikrometrik De�erleri Bulma-1</a><br>
  <a href="<%=response.encodeURL("psikrometritablo2.jsp")%>">Psikrometrik De�erleri Bulma-2</a><br>
  <a href="<%=response.encodeURL("serpantinuzerindenhava.jsp")%>">Serpantin �zerinden Akan Hava</a><br>

</body>
</html>
