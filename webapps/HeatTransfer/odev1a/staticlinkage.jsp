<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
<a href="<%=response.encodeURL("bakirlevha.jsp")%>">Bakır Levha</a><br>
<a href="<%=response.encodeURL("celiklevha.jsp")%>">Çelik Levha</a><br>
<a href="<%=response.encodeURL("duvardanisikaybi.jsp")%>">Duvardan Isı Kaybı</a><br>
<a href="<%=response.encodeURL("celikboru.jsp")%>">Çelik Borudan Isı Kaybı</a><br>
<A href="<%=response.encodeURL("direncinyuzeysicakligi.jsp")%>">Direncin Yüzey Sıcaklığı</A><BR>
<A href="<%=response.encodeURL("sonsuzlevha.jsp")%>">SonsuzLevha</A><BR>
</body>
</html>
