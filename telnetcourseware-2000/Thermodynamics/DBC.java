import java.sql.*;
import java.util.*;
import java.io.*;
public class DBC {
  public String URL,User,Password;
  public Connection con;
  public Statement stmt;
  public void connect(String varURL,String varUser,String varPassword){
    try{
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      URL=varURL;
      User=varUser;
      Password=varPassword;
      con=DriverManager.getConnection(URL,User,Password);
      stmt=con.createStatement ();
    }catch(Exception e){}
  }
  public void connect(){
    try{
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      URL="jdbc:odbc:Termodinamik";
      //User="";
      //Password="";
      con=DriverManager.getConnection(URL,User,Password);
      stmt=con.createStatement ();
    }catch(Exception e){}
  }
  public Statement getStatement(){
    return stmt;
  }

  public Connection getConnection(){
    return con;
  }

  public String[] getSelectedColumn(String ColumnName,String TableName){
    String st[]=new String[100];
    int iter=0;
    try{
      connect ();
      //Perform an SQL enquiry
      ResultSet rs=stmt.executeQuery("SELECT * FROM"+" "+TableName);
      while(rs.next ()){
        st[iter]=rs.getString(ColumnName);
        iter++;
      }
    }catch(SQLException e){System.out.println("SQL Exception occured");}
    String returnString[]=new String[iter];
    System.arraycopy(st,0,returnString,0,iter);
    return returnString;
  }
  public void reconnect(){
    try{
      stmt.close ();
    }catch(SQLException sqle){System.out.println("While closing connection SQL Exception occured");}
    connect();
  }
  //

  public void close(){
    try{
      stmt.close();
    }catch(SQLException e){System.out.println("While closing connection error occured");}
  }

  public void reconnect(String URL_,String User_,String Password_){
    try{
      stmt.close ();
    }catch(SQLException sqle){System.out.println("While closing connection SQL Exception occured");}
    connect(URL_,User_,Password_);
   }
   //

   public void updateString(String ColumnToMatch,String StringToMatch,String ColumnToSet,String TableName,String newValue){
   String cmd="";
    try{
      //Perform an SQL enquiry
     cmd="UPDATE "+TableName+" SET "+ColumnToSet+"="+"'"+newValue+"'"+" WHERE "+ColumnToMatch+"='"+StringToMatch+"'";
     int row=stmt.executeUpdate(cmd);
    }catch(SQLException e){System.out.println("SQL Exception occured");}
   }
   //

   public void updateBoolean(String ColumnToMatch,String StringToMatch,String ColumnToSet,String TableName,boolean newValue){
     String cmd;
    try{
      //Perform an SQL enquiry
     cmd="UPDATE "+TableName+" SET "+ColumnToSet+"="+newValue+" WHERE "+ColumnToMatch+"='"+StringToMatch+"'";
     int row=stmt.executeUpdate(cmd);
    }catch(SQLException e){System.out.println("SQL Exception occured");}
   }

   public boolean IsValueTaken(String ColumnName1,String ColumnName2,String TableName,String varUsername){
  //ColumnName1=�sValueTaken s�tunu ; ColumnName2=Number s�tunu
    boolean returnValue=false;
    int iter=0;
    String QueryString="";
     try{
      //Perform an SQL enquiry
      QueryString="SELECT "+ColumnName1+","+ColumnName2+" FROM "+" "+TableName+" Where "+ColumnName1+"=true AND Number='"+varUsername+"'";
      Statement stmt=this.getStatement ();
      ResultSet rs=stmt.executeQuery(QueryString);
       while(rs.next ()){
        returnValue=rs.getBoolean(ColumnName1);
        String StudentNumber=rs.getString(ColumnName2);
        iter++;
       }
    }catch(SQLException e){System.out.println("SQL Exception occured");}
    return returnValue;
  }
  //

  public String getSelectedCell(String varusername,String ColumnName,String TableName){
    String cmd="",returnString="";
    int iter=0;
    try{
      //Perform an SQL enquiry
      Statement stmt=this.getStatement ();
      cmd="SELECT "+ColumnName+ " FROM "+TableName+" WHERE NUMBER='"+varusername+"'";
      ResultSet rs=stmt.executeQuery(cmd);
      while(rs.next ()){
        returnString=rs.getString(ColumnName);
        if ((Object)returnString==null) returnString="";
        iter++;
      }
    }catch(SQLException e){System.out.println("SQL Exception occured");}
    return returnString;
  }

}