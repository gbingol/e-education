<%@ page language="java" contentType="text/html;charset=iso-8859-9" import="jspclass.*" %>
<%
	final String TableName="KisiselBilgiler";
	Students student=(Students)session.getValue("studentinfo");
  	String username=student.username;

%>
<html>
<head>
<title>�ifre De�i�tirme</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>

<body background="/images/background.gif">
<script language="JavaScript">
function checkPasswords()
{
	var txt1=document.Password.txtpwd1.value;
	var txt2=document.Password.txtpwd2.value;
	if(txt1=="") {alert("L�tfen de�i�ecek olan �ifrenizi giriniz"); return false;}
	var i=0;
	while(txt1.charAt(i)!="")
	{
	i++;
	};
	if(i<4){alert("Girdi�iniz karakter say�s� en az 4 olmal�d�r"); return false;}
	
	if(txt1==txt2) return true;
	else
	{
	alert("Girdi�iniz �ifreler birbirinin ayn�s� olmal�d�r"); return false;
	}
}
</script>
<%
	if(request.getMethod().equals("POST"))
	{
		DBC dbc=new DBC();
		dbc.connect("Thermodynamics","","");
		String newpwd=request.getParameter("txtpwd1");
	    dbc.updateString("STUDENTNUMBER",username,"PASSWORD",TableName,newpwd);
		dbc.close();
		out.print("<p align=center>");
		out.print("<font size=+2 face=comic sans ms color=#0000FF>");
		out.print("�ifreniz Ba�ar�yla De�i�tirilmi�tir.");
		out.print("</font>");
		out.print("</p>");
	}
	
%>

<p align="center"><font color="#FF0000" size="+2" face="Comic Sans MS">�ifre De�i�tirme</font></p>
<p align="justify"><font color="#000000" face="Comic Sans MS">�ifrenizi, en az 
  4 karakter ve en fazla 8 karakter olacak �ekilde se�iniz. �ifrenizde T�rk�e 
  karakter &quot;�,�,�,�,�,�&quot; kullanmay�n�z. �ifreniz harfe duyarl�d�r (case-sensitive) 
  ( &quot;T&quot; harfi &quot;t&quot; harfinden farkl�d�r).</font></p>
<form name="Password" action="pwdchange.jsp" method="post" onSubmit="return checkPasswords()">
  <table bgcolor="#00FFFF" align="left" width="40%" border="1">
    <tr> 
      <td><font face="Comic Sans MS">Yeni �ifreniz :</font></td>
      <td><font face="Comic Sans MS"> 
        <input name="txtpwd1" type="password" id="txtpwd1" maxlength="8">
        </font></td>
    </tr>
    <tr> 
      <td><font face="Comic Sans MS">Yeni �ifreniz (Tekrar) :</font></td>
      <td> <font face="Comic Sans MS"> 
        <input name="txtpwd2" type="password" id="txtpwd2" maxlength="8">
        </font></td>
    </tr>
    <tr> 
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="2" align="center"><input type="submit" name="Submit" value="Submit"></td>
    </tr>
  </table>
</form>
<p align="justify"><font color="#FF0000" size="+2" face="Comic Sans MS"></font></p>

</body>
</html>
