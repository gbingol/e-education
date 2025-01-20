<%@ page contentType="text/html; charset=windows-1254" language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<html>
<head>
<title>Bilgi</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
</head>

<body>
<TABLE cellSpacing=1 cellPadding=1 width="40%" bgColor=aqua border=1>
 <%
  	Students student=(Students)session.getValue("studentinfo");
  	DBC dbc=new DBC();
    dbc.connect ("AdvancedThermo","","");
  	String varTableName=request.getParameter("TableName");
	if((varTableName.equals(null)))
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
	if(!(TableName.equals(null)))
	{
		out.println("<TABLE cellSpacing=1 cellPadding=1 bgColor=aqua border=1>");
		String querystring="";
		if(student.isAdmin) querystring="SELECT * FROM "+TableName;
		if(!student.isAdmin) querystring="SELECT NAME_SURNAME,NUMBER,NumberOfAnswer,NOTU FROM "+TableName;
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
			out.println("<TR>");
			do{
				try{
					columnindex++;
					columnvalue=rs2.getString(columnindex);
					if(!columnvalue.equals(null)) columnexist=true;
					else columnexist=false;
					out.println("<TD>"+columnvalue+"</TD>");
				}catch(Exception e){break;}
			}while(columnexist);
			out.println("</TR>");
		}//while
	out.println("</TABLE>");
	}//if
%>
</body>
</html>
