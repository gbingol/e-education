<%@ page language="java"  import="jspclass.*" %>
<%
    Students student=(Students)session.getValue("studentinfo");
%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS"> 
<P align=center><STRONG><font color="#FF6666" size="+2">Termodinamik 
      Ödev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu ödevinizde toplam 5 soru vardır. Soruları
    aldıktan sonra cevapları istediğiniz zaman girebilirsiniz. İlk olarak Soğutma
    Çevrimi-1 sorusunu almanız gerekmektedir.</font></P>
<P align=justify><font color="#000000"><em> Son giriş tarihi:</em>&nbsp; 28 / 
  05 / 2008 Saat 16:00</font></P>
<P align=justify>&nbsp;</P>
<P></P>
</FONT> 
</BODY>
</HTML>

