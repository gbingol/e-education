<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<%
  final String TableName="BaglantiBilgileri";
  String username=request.getParameter("username");
  jspclass.DBC db=new jspclass.DBC();
  db.connect("HeatTransfer","","");
  String name_surname=db.getSelectedCell(username,"NAME_SURNAME","KisiselBilgiler");
  String email=db.getSelectedCell(username,"EMAIL","KisiselBilgiler");
  String strusername=db.getSelectedCell(username,"STUDENTNUMBER","BaglantiBilgileri");
  if(strusername.equals(""))
  {
  		String SQLQuery="INSERT INTO "+TableName+" (NAME_SURNAME,STUDENTNUMBER) "+" VALUES ('"+name_surname+"',"+"'"+username+"')";
		db.runSQL(SQLQuery);
   }	
  
%> 
<H2>Merhaba <%=username%> </H2>
<BR>
<P> Sisteme kay�tl� ki�isel bilgileriniz a�a��daki gibidir. </P>
<TABLE cellSpacing=1 cellPadding=1 width="40%" border=1>
  <TR>
    <TD><P align=right>Ad Soyad=</P></TD>
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
      db.updateString("StudentNumber",username,"SonBaglanti3",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi));
   }
   if(db.getSelectedCell(username,"SonBaglanti3",TableOdevAdi).equals("")) db.updateString("StudentNumber",username,"SonBaglanti3",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi));
   if(!db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi).equals("")){
      SonBaglanti2=db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi);
      db.updateString("StudentNumber",username,"SonBaglanti2",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti",TableOdevAdi));
   }
  if(db.getSelectedCell(username,"SonBaglanti2",TableOdevAdi).equals("")) db.updateString("StudentNumber",username,"SonBaglanti2",TableOdevAdi,db.getSelectedCell(username,"SonBaglanti",TableOdevAdi));
  if(!db.getSelectedCell(username,"SonBaglanti",TableOdevAdi).equals("")){
     SonBaglanti=db.getSelectedCell(username,"SonBaglanti",TableOdevAdi);
     db.updateString("StudentNumber",username,"SonBaglanti",TableOdevAdi,date.toLocaleString ());
  }
  if(db.getSelectedCell(username,"SonBaglanti",TableOdevAdi).equals("")) db.updateString("StudentNumber",username,"SonBaglanti",TableOdevAdi,date.toString ());
  String baglantisayisi=db.getSelectedCell(username,"NumberOfLogin",TableOdevAdi);
  if(baglantisayisi.equals("")) baglantisayisi="1";
  int intNumberOfLogin=Integer.valueOf(baglantisayisi).intValue();
  intNumberOfLogin++;
  db.updateString("StudentNumber",username,"NumberOfLogin",TableOdevAdi,String.valueOf(intNumberOfLogin));
  db.close();
 
%>
<BR>
<P> Sisteme kay�tl� ba�lant� bilgileriniz a�a��daki gibidir. </P>
<TABLE cellSpacing=1 cellPadding=1 width="50%" border=1>
  <TR>
    <TD><P align=left>Son Ba�lant�=</P></TD>
    <TD><%=SonBaglanti%></TD>
  </TR>
  <TR>
    <TD><P align=left>Sondan 2. Ba�lant�=</P></TD>
    <TD><%=SonBaglanti2%></TD>
  </TR>
  <TR>
    <TD><P align=left>Sondan 3. Ba�lant�=</P></TD>
    <TD><%=SonBaglanti3%></TD>
  </TR>
</TABLE>
<BR>
<BR>
<P align=center> Sisteme �u ana kadar <%=baglantisayisi%> kez ba�land�n�z. </P>
<BR>
<BR>
<BR>
