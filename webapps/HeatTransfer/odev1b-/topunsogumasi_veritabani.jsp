<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="Conduction6";
   String username=new String();
   DBC dbc=new DBC();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
  dbc.connect("HeatTransfer","","");
%>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
<BODY background="/images/background.gif">

<%

  if(request.getMethod().equals("POST"))
  {
  	String values[]=new String[5];
  	String D=request.getParameter("D");
  	String To=request.getParameter("To");
  	String Tortam=request.getParameter("Tortam");
  	String h=request.getParameter("h");
  	String Tson=request.getParameter("Tson");
  	//
  	values[0]=D;values[1]=To;values[2]=Tortam;values[3]=h;values[4]=Tson;
  	//
   	for(int i=0;i<values.length;i++)
	{
    	 String ColumnToSet="Value"+String.valueOf(i+1);
     	dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,values[i]);
   	}
    dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,"1");
	out.print("<A href=\""+response.encodeURL("topunsogumasi.jsp")+"\">Devam etmek i�in t�klay�n�z</A>");
	return;
  }
  
%>

<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT color="#ff0080" size=5 face="Comic Sans MS">Topun So�umas�</FONT></STRONG><font face="Comic Sans MS"> </font></P>
<FORM action="<%=response.encodeURL("topunsogumasi_veritabani.jsp")%>" method="POST">
<P align=justify style="line height: 200%"><font face="Comic Sans MS">
  <input type="text" name="D" width="50"> 
  (cm) �ap�ndaki top (Cp=0.46 kJ/kgK; k=35 W/m�C) ba�lang��ta uniform olarak 
  <input type="text" name="To" width="50">
  �C'de iken aniden 
  <input type="text" name="Tortam" width="50">
  �C s�cakl���nda sabit tutulan bir ortama
b�rak�lmaktad�r. Y�zey ta��n�m �s� transfer katsay�s� 
  <input type="text" name="h" width="50"> 
  (W/m<sup>2</sup>�C)'dir. Topun s�cakl���n�n 
  <input type="text" name="Tson" width="50">
  �C'ye d��mesi i�in ge�ecek zaman� (saniye) hesaplay�n�z.
  <br>
  <font color="#FF0000">NOT:</font> Topun yo�unlu�u=7800 (kg/m<sup>3</sup>).
</font></P>


<P align="center"><input type="submit" value="Verilerimi G�ncelle" label="Veritaban�n� G�ncelle">
</P>
</FORM>
<p align="justify"><font color="#FF0000" face="Comic Sans MS"><em>NOT:</em></font><em><font face="Comic Sans MS"> Bo�
      b�rak�lan yerlere, sistemin size verdi�i ve sorular� ��zd���n�z verileri
  girerek sorunuzu yeniden olu�turunuz.</font></em></p>
<p align="justify"><font color="#FF0000" face="Comic Sans MS"><em><font size="+2">NOT:</font></em></font><font size="+2"><em><font face="Comic Sans MS"> Sistemden
      ilk olarak ald���n�z verilerle, sonradan ba�land���n�z veriler FARKLI DE��LSE
      bu sayfay� KULLANMAYINIZ!!!!</font></em></font></p>
</BODY>
</HTML>