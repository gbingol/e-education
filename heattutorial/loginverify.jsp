<%@ page language="java" import="jspclass.*" %>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
</HEAD>
<BODY background="/images/background.gif">
<FONT face="Comic Sans MS">
<%
   //
   boolean isAdmin=false;
   String username=request.getParameter("username");
   String password=request.getParameter("password");
   //
   //Check username and password
   Students student=new Students(username,password);
   boolean loginverified=false;
   DBC dbc=new DBC();
   String realpassword=dbc.getSelectedCell(username,"PASSWORD","KisiselBilgiler");
   String name_surname=dbc.getSelectedCell(username,"NAME_SURNAME","KisiselBilgiler");
   String email=dbc.getSelectedCell(username,"EMAIL","KisiselBilgiler");
   isAdmin=dbc.IsValueTaken("ISADMIN","NUMBER","KisiselBilgiler",username);
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

<jsp:include page="personalinfo.jsp" flush="true"/>
<A href="<%=response.encodeURL("questions.jsp")%>">Click Here To Continue</A>

<%
   }
   else
   out.println("<H1>Kullanýcý adýnýz veya þifreniz hatalý olabilir.</H1>");
%>

</FONT>
</BODY>
</HTML>
