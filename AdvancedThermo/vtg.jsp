<%@ page language="java" import="jspclass.*" errorPage="" %>
<%
  //Tablo isimlerini bir diziye girelim
  final String TableName[]={"TABLEEVALUATION","PSYCHROMETRICCALCULATION1","PSYCHROMETRICCALCULATION2","CARNOTCYCLE","RANKINECYCLE"};
  final String SoruAdi[]={"Evaluating Thermodynamic Properties","Psychrometric Calculation -1","Psychrometric Calculation-2","Carnot Cycle","Rankine Cycle"};
  double Not[]=new double[TableName.length];  //Not dizisinin uzunlu�u soru uzunlu�u kadar olmal�
  double ToplamNot=0;                         
  Students student=(Students)session.getValue("studentinfo");
  String username=student.username;
  DBC dbc=new DBC();
  dbc.connect("AdvancedThermo","","");  //veritabanyna ba?lan
%>
<HTML>
<HEAD>
<TITLE>Score Analysis</TITLE>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<BODY background="../images/background.gif">
<FONT face="Comic Sans MS">
<P align=center><STRONG>Score Distribution and Total Score</STRONG></P>
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
       out.println("<TD>From"+SoruAdi[i]+" you get :"+"</TD>");
       out.println("<TD>"+Notu+"</TD>");
       out.println("<TD>points</TD>");
       out.println("</TR>");
     }else{
       out.println("<TR>");
       out.println("<TD colspan=3>You havent answered"+SoruAdi[i]+" yet.</TD>");
       out.println("</TR>");
       Not[i]=0;
    }
  }
  out.println("<TR>");
  out.println("<TD>Total score=</TD>");
  out.println("<TD>"+String.valueOf(ToplamNot)+"</TD>");
  out.println("<TD>points</TD>");
  out.println("</TR>");
%>
</TABLE>
</P>
<P></P>
<BR>
<jsp:include page="questionlinks.jsp" flush="true"/>

</FONT>
</BODY>
</HTML>
