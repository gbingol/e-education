<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
<a href="<%=response.encodeURL("bakirlevha.jsp")%>">Bak�r Levha</a><br>
<a href="<%=response.encodeURL("celiklevha.jsp")%>">�elik Levha</a><br>
<a href="<%=response.encodeURL("duvardanisikaybi.jsp")%>">Duvardan Is� Kayb�</a><br>
<a href="<%=response.encodeURL("celikboru.jsp")%>">�elik Borudan Is� Kayb�</a><br>
<A href="<%=response.encodeURL("direncinyuzeysicakligi.jsp")%>">Direncin Y�zey S�cakl���</A><BR>
<A href="<%=response.encodeURL("sonsuzlevha.jsp")%>">SonsuzLevha</A><BR>
</body>
</html>
