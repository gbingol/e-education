<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
<a href="<%=response.encodeURL("topunsogumasi.jsp")%>">Topun So�umas�</a><br>
<a href="<%=response.encodeURL("homedesign.jsp")%>">Duvardan Olan Is� Kayb�</a><br>
<a href="<%=response.encodeURL("radyator.jsp")%>">Radyat�r</a><br>
<a href="<%=response.encodeURL("kizginbuharborusu.jsp")%>">K�zg�n Buhar Borusu</a><br>

</body>
</html>
