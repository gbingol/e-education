<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" %>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS">
<%
   //
   boolean isAdmin=false;
   String username=request.getParameter("username");
   String password=request.getParameter("password");
   
   Students student=new Students(username,password);
   boolean loginverified=false;
   DBC dbc=new DBC();
   dbc.connect ("Thermodynamics","","");
   String realpassword=dbc.getSelectedCell(username,"PASSWORD","KisiselBilgiler");
   String name_surname=dbc.getSelectedCell(username,"NAME_SURNAME","KisiselBilgiler");
   String email=dbc.getSelectedCell(username,"EMAIL","KisiselBilgiler");
   isAdmin=dbc.IsValueTaken("ISADMIN","STUDENTNUMBER","KisiselBilgiler",username);
   
   
  
   if(realpassword.equals(password)) 
   {
     loginverified=true;
     student.isAdmin=isAdmin;
     student.username=username;
     student.email=email;
     student.name_surname=name_surname;

   }
   dbc.close();

   //
   HttpSession oldsession=request.getSession(false);
   if(oldsession!=null) oldsession.invalidate();
   //
   session=request.getSession(true);
   session.putValue("studentinfo",student);

%>

<%
   if(loginverified)
   {
%>
<p align="center"><font face="comic Sans MS" color="#FF0000" size="+3">�lerle 
  linkini kullanarak a�a��daki bilgilerin bana ait oldu�unu, aksi takdirde bili�im 
  su�u i�leyece�imi ve kar��l���nda da <a href="#" onclick="window.open('http://www.iem.gov.tr/iem/?m=4&s=51','Bili�im Su�lar�','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=700, height=450');">cezaland�r�laca��m�</a> 
  kabul ediyorum. </font></p>
<p align="center">&nbsp;</p>
<jsp:include page="personalinfo.jsp" flush="true"/>
<A href="<%=response.encodeURL("links.jsp?un="+username)%>">�lerle</A>
<%
   }
   else
   	{
   		out.println("<H1>Kullan�c� ad�n�z veya �ifreniz hatal� olabilir.</H1>");
   		out.println("<BR>");
	}
%>

</FONT>
</BODY>
</HTML>
