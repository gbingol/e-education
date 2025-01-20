<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="HomeDesign";
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
  	String values[]=new String[7];
  	String Tic=request.getParameter("Tic");
  	String Tdis=request.getParameter("Tdis");
  	String Ld=request.getParameter("Ld");
  	String hd=request.getParameter("hd");
  	String hk=request.getParameter("hk");
	String hc1=request.getParameter("hc1");
	String Lc1=request.getParameter("Lc1");
  	//
  	values[0]=Tic;values[1]=Tdis; values[2]=Ld;values[3]=hd;values[4]=hk;values[5]=hc1;values[6]=Lc1;
  	//
   	for(int i=0;i<values.length;i++)
	{
    	 String ColumnToSet="Value"+String.valueOf(i+1);
     	dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,values[i]);
   	}
    dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,"1");
	out.print("<A href=\""+response.encodeURL("homedesign.jsp")+"\">Devam etmek i�in t�klay�n�z</A>");
	return;
  }
  
%>

<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT color="#ff0080" size=5 face="Comic Sans MS">Duvardan Olan Is� Kayb�</FONT></STRONG><font face="Comic Sans MS"> </font></P>
<FORM action="<%=response.encodeURL("homedesign_veritabani.jsp")%>" method="POST">
<P align=justify style="line height: 200%"><font face="Comic Sans MS">Yukar�da bir odan�n d�� ortama bakan duvar�n�n �ekli g�r�lmektedir. Odan�n s�cakl��� <input type="text" name="Tic" width="50">�C
ve d�� ortam s�cakl��� ise <input type="text" name="Tdis" width="50">�C'dir. Duvar�n eni <input type="text" name="Ld" width="50">(m) ve y�ksekli�i ise <input type="text" name="hd" width="50"> (m)'dir. Kap�n�n cams�z k�sm�n�n y�ksekli�i
<input type="text" name="hk" width="50">(m) ve cam�n y�ksekli�i <input type="text" name="hc1" width="50">(m) ve geni�li�i ise <input type="text" name="Lc1" width="50"> (m)'dir. Bu plandan yararlanarak 1-3 ko�ullar� i�in odadan olan 
toplam 1 g�nl�k �s� kayb�n� TL cinsinden hesaplay�n�z.
</font></P>


<P align="center"><input type="submit" value="Verilerimi G�ncelle" label="Veritaban�n� G�ncelle">
</P>
</FORM>
<p align="justify"><font color="#FF0000" face="Comic Sans MS"><em>NOT:</em></font><em><font face="Comic Sans MS"> Bo�
      b�rak�lan yerlere, sistemin size verdi�i ve sorular� ��zd���n�z verileri
  girerek sorunuzu yeniden olu�turunuz.</font></em></p>
<p align="justify"><font color="#FF0000" face="Comic Sans MS"><em><font size="+2">NOT:</font></em></font><font size="+2"><em><font face="Comic Sans MS"> Sistemden
        ilk olarak ald���n�z verilerle, sonradan ba�land���n�z veriler FARKLI
DE��LSE bu sayfay� KULLANMAYINIZ!!!!</font></em></font></p>
</BODY>
</HTML>