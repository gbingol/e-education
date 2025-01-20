<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
  <a href="<%=response.encodeURL("isitma.jsp")%>">İnsan Konforu ve İklimlendirme</a><br>
  <a href="<%=response.encodeURL("psikrometritablo.jsp")%>">Psikrometrik Değerleri Bulma-1</a><br>
  <a href="<%=response.encodeURL("psikrometritablo2.jsp")%>">Psikrometrik Değerleri Bulma-2</a><br>
  <a href="<%=response.encodeURL("serpantinuzerindenhava.jsp")%>">Serpantin Üzerinden Akan Hava</a><br>

</body>
</html>
