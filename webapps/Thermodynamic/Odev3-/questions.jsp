<%@ page language="java" import="jspclass.*" %>
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
<P align=center><STRONG><font color="#FF6666" size="+2">Termodinamik �dev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu �devinizde toplam 4 soru vard�r. Sorular� 
  ald�ktan sonra cevaplar� istedi�iniz zaman girebilirsiniz.</font></P>
<P align=justify><font color="#000000"><em> Son giri� tarihi:</em>&nbsp; 05 / 
  05 / 2008 Saat 17:00</font></P>
<P align=justify>&nbsp;</P>
<P align=justify><font color="#FF0000">NOT:</font><font color="#000000"> Serpantin 
  �zerinden Akan Hava sorusunda, giren havan�n s�cakl��� serpantin i�erisinden 
  ge�irilen su ile d���r�lmektedir. Dolay�s�yla,</font></P>
<P align=justify><font color="#000000" face="comic Sans MS">Sisteme giren enerji=Serpantine verilen 
  enerji + Sistemden ��kan enerji<br>
  Sisteme hava ile getirilen enerji (m<sub>h</sub>*h<sub>1</sub>) = Serpantine 
  verilen enerji(mCdT) +Sistemden hava ile ��kan enerji (m<sub>h</sub>*h<sub>2</sub>)<br>
m<sub>h</sub>(h<sub>1</sub>-h<sub>2</sub>)=mC<sub>su</sub>dT</font></P>
<P align=justify>&nbsp;</P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS"> 
<P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

