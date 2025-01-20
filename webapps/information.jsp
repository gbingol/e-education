<%@ page language="java" %>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import="webassignmentsys.*" %>

<html>
<head>
<title>Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9">
</head>
<body background="/images/background.gif">
<%
	User webuser=(User)session.getAttribute("userinfo");
  	DBC dbc=new DBC();
    dbc.connect ("HeatTransfer","","");
%>
<%
	String user_number="";
	String column_name="";
	String Table_Name="";
	String ISHREF="";
if(student.isAdmin)
{
	ISHREF=request.getParameter("ISHREF"); if(ISHREF==null) ISHREF="";	//Linke bas�l�p bas�lmad���n� kontrol etsin
	user_number=request.getParameter("usernumber"); if(user_number==null) user_number="";
	column_name=request.getParameter("columnname"); if(column_name==null) column_name="";
	Table_Name=request.getParameter("TableName"); if(Table_Name==null) Table_Name="";
	if(!ISHREF.equals(""))
	{
	
	out.println("<form name=ChangeValue action=information.jsp method=POST>");
	out.println("<p align=left>Enter New Value: <input type=text name=txtValue></P>");
	out.println("<input type=hidden name=usernumber value="+user_number+">");
	out.println("<input type=hidden name=columnname value="+column_name+">");
	out.println("<input type=hidden name=TableName value="+Table_Name+">");
	out.println("<input type=submit>");
	out.println("</form>");
	return;
	}//if(username!=NULL)
	
	if(request.getMethod().equals("POST"))
	{
		out.println("OK");
		dbc.updateString("STUDENTNUMBER",user_number,column_name,Table_Name,request.getParameter("txtValue"));
	}
}//if(student.isAdmin)*/
%>
<TABLE cellSpacing=1 cellPadding=1 width="40%" bgColor=aqua border=1>
 <%
  	String varTableName=request.getParameter("TableName");
	if(varTableName==null) varTableName="";
	if((varTableName.equals("")))
	{
	String availableTables="";
    Connection con=dbc.getConnection();
    DatabaseMetaData md=con.getMetaData ();
    ResultSet rs=md.getTables(null,null,null,new String[]{"TABLE"});
    while(rs.next ())
	{
		availableTables=rs.getString (3);
		out.println("<TR>");
		out.println("<TD><A href=information.jsp?TableName="+availableTables+">"+availableTables+"</A></TD>");
        out.println("</TR>");
	}
    rs.close ();
	}
%>
</TABLE>
<%
	String TableName=request.getParameter("TableName");
	if(TableName==null) TableName="";
	if(!(TableName.equals("")))
	{
		out.println("<TABLE cellSpacing=1 cellPadding=1 bgColor=aqua border=1>");
		String querystring="";
		if(student.isAdmin) querystring="SELECT * FROM "+TableName;
		if(!student.isAdmin) querystring="SELECT NAME_SURNAME,STUDENTNUMBER,NOTU FROM "+TableName;
		Connection con1=dbc.getConnection();
		Statement stmt=con1.createStatement();
		ResultSet rs2=stmt.executeQuery(querystring);
		ResultSetMetaData rsmd=rs2.getMetaData();
		out.println("<TR>");
		for(int i=1;i<=rsmd.getColumnCount();i++)
		{
			String columnname=rsmd.getColumnLabel(i);
			out.println("<TD><STRONG>"+columnname+"</STRONG></TD>");
		}
		out.println("</TR>");
		while(rs2.next())
		{
			int columnindex=0;
			boolean columnexist=false;
			String columnvalue="";
			String usernumber="";
			String columnname="";
			out.println("<TR>");
			do{
				try{
					columnindex++;
					columnname=rsmd.getColumnLabel(columnindex);
					columnvalue=rs2.getString(columnindex);
					if(columnindex==2) usernumber=columnvalue;	//get usernumber to use at below link
					if(columnvalue==null) columnvalue="";
					//else columnexist=false;
					out.println("<TD><a href=information.jsp?columnname="+columnname+"&usernumber="+usernumber+"&TableName="+TableName+"&ISHREF=TRUE"+">"+columnvalue+"</a></TD>");
				}catch(Exception e){break;}
			}while(columnindex<=rsmd.getColumnCount());
			out.println("</TR>");
		}//while
	out.println("</TABLE>");
	}//if
%>

</body>
</html>
