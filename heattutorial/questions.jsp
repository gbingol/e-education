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
<P align=center><STRONG><font color="#FF6666" size="+2">Isı Transferi Robot Asistanı
      (RA)</font></STRONG></P>
<P align=justify><font color="#000000">Bu sistemdeki soruları kendi istediğiniz
    verilere göre hazırlayıp daha sonra istediğiniz zaman çözebilirsiniz. Verilerinizin
    belli bir aralıkta olması gerekmektedir. Aksi halde sistem sizi uyaracaktır.
    Aşağıda sayfalarda bulunan düğmelerin işlevleri tarif edilmiştir.</font></P>
<P align=justify><font color="#FF0000">Kaydet:</font><font color="#000000"> Verilerinizi bırakılan boşluklara
    girdikten sonra bu butona basarak verilerinizin sisteme kayıt edilmesi sağlanır.
    </font></P>
<P align=justify><font color="#FF0000">Tekrar-Al :</font><font color="#000000"> Sistemde bulunan verilerinizin
    silinmesini sağlar. Bu sayede farklı verilerle sorunuzu tekrardan hazırlama
    imkanı elde edebilirsiniz.</font></P>
<P align=justify><font color="#FF0000">Çözdüm :</font><font color="#000000"> Sorunuzu çözdükten sonra, sisteme
    girdiğiniz cevaplarınızın doğruluğunu veya yanlışlığını kontrol edebilirsiniz.</font></P>
</FONT><FONT face="Comic Sans MS"></FONT><FONT face="Comic Sans MS">
</FONT><FONT face="Comic Sans MS"><P></P>
<jsp:include page="questionlinks.jsp" flush="true"/>
</FONT> 
</BODY>
</HTML>

