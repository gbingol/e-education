<%@ page language="java" errorPage="" %>
<html>
<head>
<title>Statik Link</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body background="/images/background.gif">
<base target="mainFrame">
   <A href="<%=response.encodeURL("levhadanakanisimiktari.jsp")%>">Levhadan Akan Isı Miktarı</A> <BR>
  <A href="<%=response.encodeURL("levhadasinirtabaka.jsp")%>">Levhada Sınır Tabaka İncelemesi</A> <BR>
  <A href="<%=response.encodeURL("surtunmesizboru.jsp")%>">Sürtünmesiz Borudan Olan Isı Kaybı</A> <BR>
  <A href="<%=response.encodeURL("kanaldabasincdusumu.jsp")%>">Kanal İçinde Basınç Düşümü</A>
<p></p>
  <A href="<%=response.encodeURL("../odev1b/topunsogumasi.jsp")%>">Ödev1b-Topun Soğuması</A><br>
</body>
</html>
