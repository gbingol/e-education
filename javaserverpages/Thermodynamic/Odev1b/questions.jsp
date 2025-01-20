<%@ page language="java" import="jspclass.*" %>
<%
    Students student=(Students)session.getValue("studentinfo");
%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=iso-8859-9">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS"> 
<P align=center><STRONG><font color="#FF6666" size="+2">Termodinamik 
      Ödev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu ödevinizde toplam 6 soru vardır. Soruları
    aldıktan sonra cevapları istediğiniz zaman girebilirsiniz.</font></P>
<P align=justify><font color="#000000"><em> Son giriş tarihi:</em>&nbsp;31 / 03 
  / 2008 Saat 17:00</font></P>
<P align=justify>&nbsp;</P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
<P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

