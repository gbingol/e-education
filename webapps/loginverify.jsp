<%@ page language="java" %>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import="webassignmentsys.*" %>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
<BODY background="images/background.gif">
<script language="JavaScript" src="jsscripts/Toggle.js"></script>
<%
//
	int AccessRight=0;
	String username=request.getParameter("username");
	String password=request.getParameter("password");

	User webuser=new User(username,password);
	boolean loginverified=false;
	DBC dbc=new DBC();
	dbc.Connect("WebAssignment","","");
	String realpassword=dbc.GetCellValue(username,"Password","Persons");
	String name_surname=dbc.GetCellValue(username,"NAME_SURNAME","Persons");
	AccessRight=Integer.valueOf(dbc.GetCellValue(username,"ACCESSRIGHT","Persons"));



	if(realpassword.equals(password)) 
	{
  		loginverified=true;
  		webuser.SetUserAccessRight(AccessRight);
  		webuser.SetUsername(username);
  		dbc.close();
	} else
	{
		out.println("<P>Sorry either your username or password is not valid</P>");
		return;
	}

	//
	HttpSession oldsession=request.getSession(false);
	if(oldsession!=null) oldsession.invalidate();
	//
	session=request.getSession(true);
	session.setAttribute("userinfo", webuser);

%>
<H2>Hello <%=username%> </H2>
<BR>
<P> Your information in the system is as follows. </P>
<TABLE cellPadding=1 width="40%" border=1>
  <TR>
    <TD><P align=right>Name Surname:</P></TD>
    <TD><%=name_surname%></TD>
  </TR>
  <TR>
    <TD><P align=right>E-mail=</P></TD>
    <TD></TD>
  </TR>
</TABLE>

<P><a href="javascript:Toggle('ConnectionInfo')">Click to see your connection information</a></P>
<div id="ConnectionInfo" style="display:none">
<jsp:include page="connectioninfo.jsp" /> 
</div>
</BODY>
</HTML>
