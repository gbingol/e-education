<%@ page language="java" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
   <A href="<%=response.encodeURL("levhadanakanisimiktari.jsp")%>">Levhadan Akan Is� Miktar�</A> <BR>
  <A href="<%=response.encodeURL("levhadasinirtabaka.jsp")%>">Levhada S�n�r Tabaka �ncelemesi</A> <BR>
  <A href="<%=response.encodeURL("surtunmesizboru.jsp")%>">S�rt�nmesiz Borudan Olan Is� Kayb�</A> <BR>
  <A href="<%=response.encodeURL("kanaldabasincdusumu.jsp")%>">Kanal ��inde Bas�n� D���m�</A>
<p></p>
  <A href="<%=response.encodeURL("../odev1b/topunsogumasi.jsp")%>">�dev1b-Topun So�umas�</A><br>
</body>
</html>
