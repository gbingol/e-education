<%@ page language="java" import="jspclass.*"%>
<%
    Students student=(Students)session.getValue("studentinfo");
%>
<HTML>
<HEAD>
<title>Sorular</title>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS"> 
<P align=center><STRONG><font color="#FF6666" size="+2">Termodinamik 
      �dev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu �devinizde toplam 5 soru vard�r. Sorular� 
  ald�ktan sonra cevaplar� istedi�iniz zaman girebilirsiniz.</font></P>
<P align=justify><font color="#000000" size="+1">NOT: �lk �nce 1. soruyu t�klaman�z gerekmektedir.</font></P>
<P align=justify><font color="#000000"> </font></P>
<P align=justify><font color="#000000"><em>Son giri� tarihi:</em>&nbsp; 28 / 05 
  / 2008 Saat 16:00</font></P>
<P align=justify>&nbsp;</P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
<P></P>
</FONT> 
</BODY>
</HTML>

