<%
  
  String username=request.getParameter("username");
  jspclass.DBC db=new jspclass.DBC();
  db.connect("AdvancedThermo","","");
  String name_surname=db.getSelectedCell(username,"NAME_SURNAME","KisiselBilgiler");
  String email=db.getSelectedCell(username,"EMAIL","KisiselBilgiler");
%> 
<H2>Hi <%=username%> </H2>
<BR>
<P> Your current status is as follows,</P>
<TABLE cellSpacing=1 cellPadding=1 width="40%" border=1>
  <TR>
    <TD><P align=right>Name and Surname:</P></TD>
    <TD><%=name_surname%></TD>
  </TR>
  <TR>
    <TD><P align=right>E-mail=</P></TD>
    <TD><%=email%></TD>
  </TR>
</TABLE>
<BR>
<BR>
<BR>
<%
   final String TableOdevAdi="BaglantiBilgileri"; 
   String SonBaglanti3="",SonBaglanti2="",SonBaglanti="";
   java.util.Date date=new java.util.Date();
   if(!db.getSelectedCell(username,"SonBaglanti3",TableOdevAdi).equals("")){
      SonBaglanti3=db.getSelectedCell(username,"SonBaglanti3",TableOdevAdi);
      db.updateString("Number",username,"SonBaglanti3",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi));
   }
   if(db.getSelectedCell(username,"SonBaglanti3",TableOdevAdi).equals("")) db.updateString("Number",username,"SonBaglanti3",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi));
   if(!db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi).equals("")){
      SonBaglanti2=db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi);
      db.updateString("Number",username,"SonBaglanti2",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti",TableOdevAdi));
   }
  if(db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi).equals("")) db.updateString("Number",username,"SonBaglanti2",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti",TableOdevAdi));
  if(!db.getSelectedCell(username,"SonBaglanti",TableOdevAdi).equals("")){
     SonBaglanti=db.getSelectedCell(username,"SonBaglanti",TableOdevAdi);
     db.updateString("Number",username,"SonBaglanti",TableOdevAdi,date.toLocaleString ());
  }
  if(db.getSelectedCell(username,"SonBaglanti",TableOdevAdi).equals("")) db.updateString("Number",username,"SonBaglanti",TableOdevAdi,date.toString ());
  String baglantisayisi=db.getSelectedCell(username,"NumberOfLogin",TableOdevAdi);
  if(baglantisayisi.equals("")) baglantisayisi="1";
  int intNumberOfLogin=Integer.valueOf(baglantisayisi).intValue();
  intNumberOfLogin++;
  db.updateString("Number",username,"NumberOfLogin",TableOdevAdi,String.valueOf(intNumberOfLogin));
  db.close();
%>
<BR>
<TABLE cellSpacing=1 cellPadding=1 width="50%" border=1>
  <TR>
    <TD><P align=left>Last Connection=</P></TD>
    <TD><%=SonBaglanti%></TD>
  </TR>
  <TR>
    <TD><P align=left>Connection previous to last connection=</P></TD>
    <TD><%=SonBaglanti2%></TD>
  </TR>
  <TR>
    <TD><P align=left>Connection two before the last connection=</P></TD>
    <TD><%=SonBaglanti3%></TD>
  </TR>
</TABLE>
<BR>
<BR>
<P align=center> You have connected for <%=baglantisayisi%> time/times. </P>
<BR>
<BR>
<BR>
