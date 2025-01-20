<%@ page language="java" import="jspclass.*"%>
<%
  //Tablo isimlerini bir diziye girelim
  final String TableName[]={"Odev1b_1","Odev1b_2","Odev1b_3","Odev1b_4","Odev1b_5","Odev1b_6"};
  final String SoruAdi[]={"Sabit Hacimde Su","R-12 Ak��kan�n�n �zellikleri","Odadaki Havan�n K�tlesi","Sabit Bas�n�te Buharla�an S�v�","Piston-Silindir ��inde Su Buhar�","Tablo Okuma"};
  double Not[]=new double[TableName.length];  //Not dizisinin uzunlu?u soru uzunlu?u kadar olmaly
  double ToplamNot=0;                         
  Students student=(Students)session.getValue("studentinfo");
  String username=student.username;
  DBC dbc=new DBC();
  dbc.connect("Thermodynamics","","");  //veritabanyna ba�lan
%>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<TITLE>Not Analizi</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS">
<P align=center><STRONG>Not Da��l�m�n�z ve Toplam Notunuz</STRONG></P>
<P>
<TABLE cellSpacing=1 cellPadding=1 width="40%" bgColor=aqua border=1>
<% 
  for(int i=0;i<TableName.length;i++)
  {
     String Notu=dbc.getSelectedCell(username,"NOTU",TableName[i]);
     if(!Notu.equals(""))
     {
       Not[i]=Double.valueOf(Notu).doubleValue();
       ToplamNot=ToplamNot+Not[i];
       out.println("<TR>");
       out.println("<TD>"+SoruAdi[i]+" sorusundan :"+"</TD>");
       out.println("<TD>"+Notu+"</TD>");
       out.println("<TD>puan</TD>");
       out.println("</TR>");
     }else{
       out.println("<TR>");
       out.println("<TD colspan=3>"+SoruAdi[i]+" sorusunu hen�z cevaplamad�n�z.</TD>");
       out.println("</TR>");
       Not[i]=0;
    }
  }
  out.println("<TR>");
  out.println("<TD>Toplam Notunuz=</TD>");
  out.println("<TD>"+String.valueOf(ToplamNot)+"</TD>");
  out.println("<TD>puan</TD>");
  out.println("</TR>");
 dbc.close();
%>
</TABLE>
</P>
<P></P>
<BR>
<jsp:include page="questionlinks.jsp" flush="true"/>

</FONT>
</BODY>
</HTML>
  