<%@ page language="java" import="jspclass.*"%>
<%
  //Tablo isimlerini bir diziye girelim
  final String TableName[]={"Odev4a_1","Odev4a_2","Odev4a_3","Odev4a_4","Odev4a_5"};
  final String SoruAdi[]={"Rankine �evrimi-1","Rankine �evrimi-2","Rankine �evrimi-3","Rankine �evrimi-4","Rankine �evrimi-5"};
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
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254"></HEAD>
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
</FONT>
</BODY>
</HTML>
  