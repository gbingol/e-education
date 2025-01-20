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
<P align=center><STRONG><font color="#FF6666" size="+2">Termodinamik Ödev Sistemi</font></STRONG></P>
<P align=justify><font color="#000000">Bu ödevinizde toplam 4 soru vardır. Soruları 
  aldıktan sonra cevapları istediğiniz zaman girebilirsiniz.</font></P>
<P align=justify><font color="#000000"><em> Son giriş tarihi:</em>&nbsp; 05 / 
  05 / 2008 Saat 17:00</font></P>
<P align=justify>&nbsp;</P>
<P align=justify><font color="#FF0000">NOT:</font><font color="#000000"> Serpantin 
  Üzerinden Akan Hava sorusunda, giren havanın sıcaklığı serpantin içerisinden 
  geçirilen su ile düşürülmektedir. Dolayısıyla,</font></P>
<P align=justify><font color="#000000" face="comic Sans MS">Sisteme giren enerji=Serpantine verilen 
  enerji + Sistemden çıkan enerji<br>
  Sisteme hava ile getirilen enerji (m<sub>h</sub>*h<sub>1</sub>) = Serpantine 
  verilen enerji(mCdT) +Sistemden hava ile çıkan enerji (m<sub>h</sub>*h<sub>2</sub>)<br>
m<sub>h</sub>(h<sub>1</sub>-h<sub>2</sub>)=mC<sub>su</sub>dT</font></P>
<P align=justify>&nbsp;</P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS"> 
<P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

