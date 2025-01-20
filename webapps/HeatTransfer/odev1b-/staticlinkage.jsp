<%@ page language="java" import="jspclass.*"%>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
<a href="<%=response.encodeURL("topunsogumasi.jsp")%>">Topun Soğuması</a><br>
<a href="<%=response.encodeURL("homedesign.jsp")%>">Duvardan Olan Isı Kaybı</a><br>
<a href="<%=response.encodeURL("radyator.jsp")%>">Radyatör</a><br>
<a href="<%=response.encodeURL("kizginbuharborusu.jsp")%>">Kızgın Buhar Borusu</a><br>

</body>
</html>
