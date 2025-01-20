<%@ page language="java" %>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import="webassignmentsys.*" %>
<%
  	final String TableName="ConnectionInfo";
  	String username=request.getParameter("username");
  	DBC dbc=new DBC();
	dbc.Connect("WebAssignment","","");    
  	String name_surname=dbc.GetCellValue(username,"NAME_SURNAME","Persons");
  	String strusername=dbc.GetCellValue(username,"USERNAME","ConnectionInfo");
  	String addr=request.getRemoteAddr();
  	java.util.Date date=new java.util.Date();
  	String today=Integer.toString(date.getDate())+"/"+Integer.toString(date.getMonth()+1)+" "+Integer.toString(date.getHours())+":"+Integer.toString(date.getMinutes());
  	
  	//Insert into ConnectionInfo (NAME_SURNAME, USERNAME, CONNECTIONIP) VALUES('Gokhan Bingol','b','127.0.0.1');
  	String SQLQuery="INSERT INTO "+TableName+" (NAME_SURNAME,USERNAME,CONNECTIONIP,CONNECTIONDATE) "+" VALUES ('"+name_surname+"',"+"'"+username+"','"+addr+"','"+today+"')";
  	dbc.ExecuteUpdate(SQLQuery);
  
%> 

<P></P>
<table border="1" width="40%">
  <tr>
    <th>IP Address</th>
    <th>Time</th>
  </tr>
 <%
	SQLQuery="SELECT CONNECTIONIP, CONNECTIONDATE FROM "+TableName+ " WHERE USERNAME ='"+username+"'";
 	ResultSet rs=dbc.ExecuteQuery(SQLQuery);
 	while(rs.next())
 	{
 %>
  <tr>
    <td align="center"><%=rs.getString("CONNECTIONIP") %></td>
    <td align="center"><%=rs.getString("CONNECTIONDATE")%></td>
  </tr>
  <%
 	} //while(rs.next())
  %>
</table>
<BR>
<BR>
