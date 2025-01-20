<%@ page language="java" contentType="text/html;charset=iso-8859-9"  import="java.sql.*" errorPage="" %>
<HTML>
<HEAD>
<title>Termodinamik Ödevi</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-9">

<style>
P.loginformat{
	border: thin dotted #3333FF;
	position: relative;
	background-position: center center;
	height: auto;
	width: auto;
	text-align: center;
	text-decoration: blink;
	cursor: crosshair;
	vertical-align: middle;
}

</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_displayStatusMsg(msgStr) { //v1.0
  status=msgStr;
  document.MM_returnValue = true;
}
function checkInput()
{
	var txtpassword=document.login.password.value;
	var txtusername=document.login.username.value;
	if(txtpassword==""){alert("Şifrenizi girmediniz!");return false;}
	if(txtusername==""){alert("Kullanıcı adınızı girmediniz!");return false;}
	return true;
}
//-->
</script>
</HEAD>
<BODY background="/images/background.gif" onLoad="submitDocument()">
<p><FONT face="Comic Sans MS" > </FONT></p>
<div align="center"><FONT face="Comic Sans MS" > </FONT> </div>
<p class="loginformat" align=left><font face="Comic Sans MS"><strong><font color="#0000FF" size=5>MACROHARD</font></strong> <br>
  <br>
  </font></p>
<FONT face="Comic Sans MS" > 
<P>
<P> 
<FORM name="login" action="loginverify.jsp" method="post" onSubmit="return checkInput()">
  <TABLE style="WIDTH: 40%" cellSpacing=1 cellPadding=1 width="40%" bgColor=aqua 
border=1>
    <TBODY>
      <TR> 
        <TD> <P align=right><font face="Comic Sans MS">Kullanıcı Adınız</font> :</P></TD>
        <TD><INPUT style="WIDTH: 108px; HEIGHT: 22px" type=textbox size=21 
      name=username> </TD>
      <TR> 
        <TD> <P align=right><font face="Comic Sans MS">Şifreniz</font> :</P></TD>
        <TD><INPUT style="WIDTH: 108px; HEIGHT: 22px" type=password 
      size=21 name=password ></TD>
      </TR>
      <TR> 
        <TD colSpan=2> <P align=center> 
            <INPUT type="submit" value="Gönder" name="Submit">
          </P></TD>
      </TR>
    </TBODY>
  </TABLE>
</FORM>
<P>&nbsp;</P>
<p align="justify">&nbsp;</p>
<p align="center">&nbsp;</p>
<p class="loginscreen">&nbsp;</p>
<p align="center"><font face="Comic Sans MS">Internet Explorer 5.0 veya üzeri tavsiye edilir</font></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p align="right">&nbsp;<A href="mailto: bingolgo@itu.edu.tr?Subject=Question and Recommendation" onMouseOver="MM_displayStatusMsg('Lütfen yorumlarınızı yazınız');return document.MM_returnValue">Sorular ve Yorumlar</a></p>
</FONT> 
</BODY>
</HTML>