<%@ page language="java" import="jspclass.*" %>
<%
    Students student=(Students)session.getValue("studentinfo");
%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS"> 
<P align=center><STRONG><font color="#FF6666" size="+2">Is� Transferi �dev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu �devinizde toplam 4 soru vard�r. Sorular�
    ald�ktan sonra cevaplar� istedi�iniz zaman girebilirsiniz.</font></P>
<P align=justify>&nbsp;</P>
<P align=justify><font color="#000000"> <font color="#0080FF">�devinizin son giri� 
  tarihi:</font> 21/11/2007 Saat 16:00</font></P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
<P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

