<%@ page language="java" import="jspclass.*" %>
<%
    Students student=(Students)session.getValue("studentinfo");
%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<BODY background="../images/background.gif">
<FONT face="Comic Sans MS"> 
<P align=center><STRONG><font color="#FF6666" size="+2">WELCOME TO ASSIGNMENT
      SYSTEM</font></STRONG></P>
<P align=justify><font color="#000000">There are 4 questions to answer. You may
    select any question to begin. After you have taken viewed your question,
    you may answer it any time before the deadline. </font></P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
<P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

