<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<%!
	final String TableName="HeatTransfer";
	final String DatabaseName="Messages";
%>
<html>
<head>
<title>Mesaj Olu�tur</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>
<body background="/images/background.gif">
<%
if(request.getMethod().equals("POST"))
{
	String strDate=request.getParameter("txtDate");
	String strName=request.getParameter("txtNameSurname");
	String strMessage=request.getParameter("txtMessage");
	DBC dbc=new DBC();
	dbc.connect(DatabaseName,"","");
	String SQLQuery="INSERT INTO "+TableName+" VALUES ('"+strDate+"'"+",'"+strName+"','"+strMessage+"')";
	dbc.runSQL(SQLQuery);
	dbc.close();
	out.println("<p align=center><font size=\"+2\">Mesaj�n�z ba�ar�yla kaydedilmi�tir</font></p>");
	out.println("<p>&nbsp;</p>");
	out.println("<a href=\"viewmessages.jsp\">Mesajlar� G�r�nt�le</A>");
	return; 
}//if(...POST)	

%>
<P align="center"><font color="#FF0000" face="Comic Sans MS" size="+3">MESAJ YAZMA SAYFASI</font></P>
<P align="justify"><font face="Comic Sans MS">Bu sayfaya yazd���n�z mesajlar
    veritaban�na  kaydedilip <a href="viewmessages.jsp">Mesajlar�
    G�r�nt�le</a> linki vas�tas�yla derse kay�tl� herkes taraf�ndan 
  g�r�nt�lenecektir. Mesaj�n�z sistemle veya dersle ilgili olabilir. Mesaj�n�za
     gelen cevaplar� Mesajlar� G�r�nt�le linki ile takip edebilirsiniz. Mesaj�n�z
    
  maksimum 250 karakter olabilir (250 karakter veritaban�na kaydedilecektir).</font></P>
<form name="message" action="postmessage.jsp" method="post">
  <table bgcolor="#00CCFF" width="50%" border="1">
    <tr> 
      <td ><font face="Comic Sans MS">Tarih :</font></td>
      <td ><input name="txtDate" type="text" id="txtDate"></td>
    </tr>
    <tr> 
      <td><font face="Comic Sans MS">Ad Soyad :</font></td>
      <td><input name="txtNameSurname" type="text" id="txtNameSurname"></td>
    </tr>
    <tr> 
      <td><font face="Comic Sans MS">Mesaj :</font></td>
      <td><textarea rows="5" cols="40" name="txtMessage" id="txtMessage" ></textarea></td>
    </tr>
    <tr> 
      <td colspan="2" align="center"><input type="submit" name="Submit" value="G�nder"></td>
    </tr>
  </table>
</form>
</body>
</html>
