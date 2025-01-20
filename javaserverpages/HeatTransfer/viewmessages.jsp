<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<%!
	final String DatabaseName="Messages";
	final String TableName="HeatTransfer";
%>
<html>
<head>
<title>Mesajlar� G�r�nt�le</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>
<body background="/images/background.gif">
<p align="center"><font face="Comic Sans MS" color="#FF0000" size="+3">MESAJ G�R�NT�LEME SAYFASI</font></p>
<p align="justify"><font face="Comic Sans MS">Bu sayfada arkada�lar�n�z�n yazd��� mesajlar� a�a��daki tablodan okuyabilirsiniz.</font></p>
<table align="center" border="1">
<tr align="center">
	<td><font face="Comic Sans MS">TAR�H</font></td>
	<td><font face="Comic Sans MS">G�NDEREN</font></td>
	<td><font face="Comic Sans MS">MESAJ</font></td>

</tr>
<%
	DBC dbc=new DBC();
	dbc.connect(DatabaseName,"","");
	String strdate[]=dbc.getSelectedColumn("DATE",TableName);
	String strusername[]=dbc.getSelectedColumn("USERNAME",TableName);
	String strmessage[]=dbc.getSelectedColumn("MESSAGE",TableName);
	//
	//max returns how many messages have been posted ?
	//
	int len1=strdate.length;
	int len2=strusername.length;
	int len3=strmessage.length;
	int max=0;
	if(len1>=len2) max=len1; else max=len2;
	if(len3>=len2) max=len3; else max=len2;
	for(int i=0;i<max;i++)
	{
		out.print("<tr>");
		out.print("<td><font face=\"Comic Sans MS\">"+strdate[i]+"</font></td>");
		out.print("<td><font face=\"Comic Sans MS\">"+strusername[i]+"</font></td>");
		out.print("<td><font face=\"Comic Sans MS\">"+strmessage[i]+"</font></td>");
		out.print("</tr>");
	}
	
%>
</table>
</body>
</html>
