import java.io.*;
import java.sql.*;
import java.util.*;
public class INFORMATION extends MHCommand{
  //
  private ThreadSocket ts;
  public int students_participated;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  private String[] commands;
  private DBC dbc=null;
  private String[][] temp;
  String TableToGetData,availableTables,Data;
  int nofstudents,ColumnsToInspectLength;
  //
  public INFORMATION(){};
  public INFORMATION(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String s){
    try{
      dbc=new DBC();
      dbc.connect ();
      Connection con=dbc.getConnection();
      DatabaseMetaData md=con.getMetaData ();
      ResultSet rs=md.getTables(null,null,null,new String[]{"TABLE"});
      availableTables="";
      while(rs.next ())
        availableTables=availableTables+rs.getString (3)+"\r\n";
        rs.close ();
    }catch(java.sql.SQLException jsse){}

    int numberofavailablefiles=0;
    String username[]=dbc.getSelectedColumn ("NUMBER","KisiselBilgiler");
    String password[]=dbc.getSelectedColumn ("PASSWORD","KisiselBilgiler");
    commands=ts.getCommands();
    nofstudents=username.length;
    //
    if(commands[1].equals("?")){
        out.println("INFORMATION S: Print Information to Screen");
        out.println("INFORMATION M: Mail Information to Specified E-mail Adress");
    }
    try{
      out.println("*******BILGI ALINABÝLECEK TABLO ADLARI******");
      out.println(availableTables);
      out.println("Tablo adý sonuna '*' koyabilirsiniz.");
      dataout.writeChars ("Lutfen bilgi almak istediginiz tablonun adini giriniz :");
      TableToGetData=in.readLine ();
      //
      //Eðer S parametresi girildiyse tüm verileri ekrana da yazsýn
      //
      if(commands[1].equals("S")){
        printAllTables(TableToGetData);
      }
      //
      //Eger M parametresi girildiyse mail yollasýn
      //
       if(commands[1].equals("M")) {
        dataout.writeChars ("Lutfen e-mail adresinizi giriniz:");
        String email_to=in.readLine ().trim ();
        boolean isEmailValid=MHMail.isEmailAdressSeemsValid (email_to);
        getData(TableToGetData);
        Data="";
        for(int i=0;i<nofstudents;i++){
          for(int j=0;j<ColumnsToInspectLength;j++){
            Data=Data+temp[j][i];
          }
          Data=Data+"\r\n";
        }
        if(isEmailValid) MHMail.sendEMail (email_to,Data,out);
        else out.println("MacroHard e-mail adresinizi hatali olarak kabul ediyor");
       }
       //
    }catch(Exception e){}
  }
  private void getData(String TableName){
    //
    String[] ColumnsToInspect={"NAME_SURNAME","NUMBER","NOTU"};
    ColumnsToInspectLength=ColumnsToInspect.length;
    temp=new String[ColumnsToInspectLength][nofstudents];
    for(int i=0;i<ColumnsToInspect.length;i++){
      temp[i]=dbc.getSelectedColumn (ColumnsToInspect[i],TableName);
    }
  }

  private void printData(String TableName){
    getData(TableName);
    out.println("      NAME_SURNAME      "+"     NUMBER    "+" NOTU ");
    for(int i=0;i<nofstudents;i++){
      String str="";
      for(int j=0;j<ColumnsToInspectLength;j++){
        String bosString="                                                    ";
        String tempString=temp[j][i];
        if(tempString==null) tempString="CEVAPLAMADI";
        int len=tempString.length ();
        if(len<30&&j==0) tempString=tempString+bosString.substring (1,30-len);
        if(len<10&&j==1) tempString=tempString+bosString.substring (1,10-len);
        if(len<20&&j==2) tempString=tempString+bosString.substring(1,20-len);
        str=str+tempString;
      }
      out.println (str);
    }
  }

  public void printAllTables(String TableName){
     String TableToPrint="";
     try{
      Connection con=dbc.getConnection();
      DatabaseMetaData md=con.getMetaData ();
      ResultSet rs=md.getTables(null,null,null,new String[]{"TABLE"});
      String varTableName=parseString(TableName);
      while(rs.next ()){
        TableToPrint=rs.getString (3);
        if(TableToPrint.startsWith(varTableName)){
          out.println("Yazýlan Tablonun Adý="+TableToPrint);
          out.println("");
          out.println("*************************************");
          try{
          printData(TableToPrint);
          }catch(Exception e){}
          out.println("");
          out.println("*************************************");
        }//if
      }//while
      rs.close ();
    }catch(java.sql.SQLException jsse){}
  }

  String parseString(String s){
    int len=s.length ();
    for(int i=0;i<len;i++){
      if(s.charAt(i)=='*') return s.substring(0,i);
    }
    return s;
  }

}

