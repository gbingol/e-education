<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="1a_DirencinYuzeySicakligi";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=10;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[3];
   String results[]=new String[1];
   String username=new String();
   DBC dbc=new DBC();
   Integer intnumberofanswer_=new Integer(1);
   int intnumberofanswer=intnumberofanswer_.intValue();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
  String name_surname=student.name_surname;
  dbc.connect("HeatTransfer","","");
  
  String strusername=dbc.getSelectedCell(username,"STUDENTNUMBER",TableName);
  if(strusername.equals(""))
  {
  		String SQLQuery="INSERT INTO "+TableName+" (NAME_SURNAME,STUDENTNUMBER) "+" VALUES ('"+name_surname+"',"+"'"+username+"')";
		dbc.runSQL(SQLQuery);
   }	
  
  String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
  if(numberofanswer.equals("")) numberofanswer="1";
  int intnumberofanswer=Integer.valueOf(numberofanswer).intValue(); 
  double doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
  String strvalueforquestion=String.valueOf(doublevalueforquestion);
%>
<%
  double L,D,Tyuzey,q;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      L =(double)(int)(Math.random() * 10 + 10);   //Direncin uzunlugu (cm)
      D =(double)(int)(Math.random() * 3 + 1);   //Direncin capi
      Tyuzey =(double)(int)(Math.random() * 30 + 105);    //Direncin yuzey sicakligi
      values[0]=L;values[1]=D;values[2]=Tyuzey;
      //
      //Sorunun cevab�n� hesaplayal�m
      //
      double A;
      A=Math.PI*(D/1000)*(L/100);
      q=5000*A*(Tyuzey-100);
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(q));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  L=values[0];D=values[1];Tyuzey=values[2];
}

%>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254"></HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">

<%

  if(request.getMethod().equals("POST"))
  {
    if(intnumberofanswer<=5)
    {
      doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer];
      strvalueforquestion=String.valueOf(doublevalueforquestion);
    }
    else
    {
     strvalueforquestion="Maalesef Art�k 0 :((";
    }
  }
%>

<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT size=5 color="#ff0080">D�RENC�N Y�ZEY SICAKLI�I</FONT></STRONG> 
</P>
<P align=justify style="line height=200%"><%=L%> (cm) uzunlu�unda, <%=D%> (mm) �ap�ndaki bir diren�ten ak�m ge�mektedir. Diren� atmosfer 
  bas�nc�nda bulunan suya bat�r�lm�� olup, su kaynay�ncaya kadar ak�m verilmektedir. 
  Bu durum i�in h=5000 W/m<SUP>2</SUP>�C ve su s�cakl��� 100�C olarak al�nabilmektedir. 
  Direncin y�zey s�cakl���n� <%=Tyuzey%>�C'de tutabilmek i�in ne kadarlik bir g�� harcanmal�d�r?</P>
<P align=center><STRONG>CEVAPLAMA B�L�M�</STRONG></P>
<FORM action="<%=response.encodeURL("direncinyuzeysicakligi.jsp")%>" method="POST">
<P align=center>
<TABLE style="WIDTH: 325px; HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=325 
border=1>
  <TBODY bgColor=aqua>
  <TR>
    <TD width=200>Harcanmas� gerekli g��=</TD>
    <TD align=middle><INPUT name=useranswer0></TD>
    <TD width=40>(W)</TD></TR>
  <TR>
    <TD colSpan=3>
      <P 
      align=center><INPUT type=submit value=CEVAPLA name=submit1></P></TD></TR>
<TR>
    <TD colSpan=3><P></P></TD></TR>
<TR>
    <TD width=200>Sorunun de�eri=</TD>
    <TD align=middle><%=strvalueforquestion%></TD>
    <TD width=40>puan</TD>
</TR>
</TBODY></TABLE></P>
</FORM>
<P align=justify>&nbsp;</P>

<%
for(int i=0;i<results.length;i++)
{
  String useranswer=request.getParameter("useranswer"+String.valueOf(i));
  if(useranswer==null) results[i]="";
  else results[i]=useranswer;
}
  if(request.getMethod().equals("POST"))
  {
  	Grade gr=new Grade();
    gr.GradeIt(out,results);
  }
%>

</FONT>
</BODY>
</HTML>

<%!
class Grade
{
    void GradeIt(JspWriter out,String[] results)
 {
      dbc.connect("HeatTransfer","","");
      double useranswer[]=new double[results.length];
      for(int j=0;j<results.length;j++)
      {
        useranswer[j]=Double.valueOf(results[j]).doubleValue();
      }
      double totalpoint=0;
      double doublevalueforquestion=0;
      double varq=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      boolean isanswered=false;
      String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {
        numberofanswer="1";
        dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,String.valueOf(1));
      }
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //
      try{
        if(!isanswered){
          out.println("<P align=center>");
          out.println("<FONT color=blue>");
          doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
          String strvalueforquestion=String.valueOf(doublevalueforquestion);
          intnumberofanswer++;
          dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); //
          for(int i=0;i<results.length;i++)
		  {
		  	String ColumnToSet="UserAnswer"+String.valueOf(i+1)+"_"+String.valueOf(intnumberofanswer-1);
		  	dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(useranswer[i]));
		  }
          //
          double upperlimit[]=new double[results.length];
          double lowerlimit[]=new double[results.length];
          //
          lowerlimit[0]=varq*(1-QuesRange);upperlimit[0]=varq*(1+QuesRange);
          //
          //Check limits and useranswer for negativity
          //
          boolean isConverted[]=new boolean[3];
          for(int i=0;i<results.length;i++){
            isConverted[0]=false;isConverted[1]=false;isConverted[2]=false;
            if(lowerlimit[i]<0) {lowerlimit[i]=-lowerlimit[i];isConverted[0]=true;}
            if(upperlimit[i]<0) {upperlimit[i]=-upperlimit[i];isConverted[1]=true;}
            if(useranswer[i]<0) {useranswer[i]=-useranswer[i];isConverted[2]=true;}
            //
            if(useranswer[i]<upperlimit[i]&&useranswer[i]>lowerlimit[i]){
              if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
              if(isConverted[1]) upperlimit[i]=-upperlimit[i];
              if(isConverted[2]) useranswer[i]=-useranswer[i];
              totalpoint=totalpoint+doublevalueforquestion/results.length;
              out.println("<BR>");
              out.println(String.valueOf(useranswer[i])+" cevab�n�z do�ru.");
            }else{
              if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
              if(isConverted[1]) upperlimit[i]=-upperlimit[i];
              if(isConverted[2]) useranswer[i]=-useranswer[i];
              out.println("<BR>");
              out.println(String.valueOf(useranswer[i])+" cevab�n�z yanl��.");
            }//if-else
          }//for
          //
          dbc.updateString("STUDENTNUMBER",username,"NOTU",TableName,String.valueOf(totalpoint));
          out.println("<BR>");
          out.println("Bu sorudan "+String.valueOf(totalpoint)+" ald�n�z.");
          out.println("</FONT>");
          out.println("</P>");
          //
        }//if
		dbc.close();
    }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
}//GradeIt
}//Class Grade
%>
  
