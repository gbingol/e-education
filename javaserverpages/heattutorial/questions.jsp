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
<P align=center><STRONG><font color="#FF6666" size="+2">Is� Transferi Robot Asistan�
      (RA)</font></STRONG></P>
<P align=justify><font color="#000000">Bu sistemdeki sorular� kendi istedi�iniz
    verilere g�re haz�rlay�p daha sonra istedi�iniz zaman ��zebilirsiniz. Verilerinizin
    belli bir aral�kta olmas� gerekmektedir. Aksi halde sistem sizi uyaracakt�r.
    A�a��da sayfalarda bulunan d��melerin i�levleri tarif edilmi�tir.</font></P>
<P align=justify><font color="#FF0000">Kaydet:</font><font color="#000000"> Verilerinizi b�rak�lan bo�luklara
    girdikten sonra bu butona basarak verilerinizin sisteme kay�t edilmesi sa�lan�r.
    </font></P>
<P align=justify><font color="#FF0000">Tekrar-Al :</font><font color="#000000"> Sistemde bulunan verilerinizin
    silinmesini sa�lar. Bu sayede farkl� verilerle sorunuzu tekrardan haz�rlama
    imkan� elde edebilirsiniz.</font></P>
<P align=justify><font color="#FF0000">��zd�m :</font><font color="#000000"> Sorunuzu ��zd�kten sonra, sisteme
    girdi�iniz cevaplar�n�z�n do�rulu�unu veya yanl��l���n� kontrol edebilirsiniz.</font></P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
</FONT><FONT face="Comic Sans MS"><P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

