<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="1a_Conduction3";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[7];
   String results[]=new String[3];
   String username=new String();
   DBC dbc=new DBC();
   Integer intnumberofanswer_=new Integer(1);
   int intnumberofanswer=intnumberofanswer_.intValue();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
  dbc.connect("HeatTransfer","","");
  
  String name_surname=student.name_surname;
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
  double x=0,kduvar=0,Aduvar=0,Tic=0,Tdis=0,hic=0,hdis=0,Q=0,T_duvaric=0,T_duvardis=0;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      //do{
          x=(double)(int)(Math.random()*10+15);
		  //DecimalFormat df=new DecimalFormat("0.000");
          kduvar=Math.random()*0.4+0.25;
		  //kduvar=Double.valueOf(df.format(kduvar)).doubleValue();
          Aduvar=(double)(int)(Math.random()*5+5);
          Tic=(double)(int)(Math.random()*10+10);
          Tdis=-(double)(int)(Math.random()*10+10);
          hic=(double)(int)(Math.random()*5+5);
          hdis=(double)(int)(Math.random()*10+15);
		  double payda=0,pay=0;
      	  payda=1/hic+(x/100)/kduvar+1/hdis;
	      pay=Aduvar*(Tic-Tdis);
    	  Q=pay/payda;      //W
	      T_duvaric=Tic-(Q/Aduvar)/hic;
    	  T_duvardis=Tdis+(Q/Aduvar)/hdis;
        //}while(!());    
      values[0]=x;values[1]=kduvar;values[2]=Aduvar;values[3]=Tic;values[4]=Tdis;values[5]=hic;values[6]=hdis;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Q));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(T_duvaric));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer3",TableName,String.valueOf(T_duvardis));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  x=values[0];kduvar=values[1];Aduvar=values[2];Tic=values[3];Tdis=values[4];hic=values[5];hdis=values[6];
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
<P align=center><STRONG><FONT size=5 color="#ff0080">Duvardan Is� Kayb�</FONT></STRONG></P>

<P align=justify style="line height: 200%"><%=x%> (cm) kal�nl���ndaki tu�la duvar ortalama �s�l iletkenlik katsay�s� <%=kduvar%> (W/mK) olan malzemeden in�a edilecektir. Duvar alani
<%=Aduvar%> (m<sup>2</sup>)'dir. Duvar�n i� taraf�ndaki ortam s�cakl���n�n <%=Tic%>�C, d�� taraf�ndaki ortam s�cakl���n�n <%=Tdis%>�C oldu�unu ve i� y�zeydeki �s� transfer katsay�s�n�n <%=hic%> W/m<sup>2</sup>K, d�� y�zeyindekinin <%=hdis%> W/m<sup>2</sup>K oldu�unu kabul ederek;
<br>a) Duvar boyunca gercekle�en �s� transferi miktar�n� (W),
<br>b) Duvar�n i� ve d�� y�zey s�cakl�klar�n� hesaplay�n�z.
</P>

<P align=center><STRONG><FONT face="Comic Sans MS">CEVAPLAMA B�L�M�</font></STRONG></P>
<FORM action="<%=response.encodeURL("duvardanisikaybi.jsp")%>" method="POST">
<P align=center>
<TABLE cellSpacing=1 cellPadding=1 width=390 border=1>
  <TBODY bgColor=aqua>
  <TR>
    <TD><font face="Comic Sans MS">Is� Transferi</font>=</TD>
    <TD align=middle><input name=useranswer0></TD>
    <TD>(W<font face="Comic Sans MS">)</font></TD>
  </TR>
  <TR>
    <TD>T<sub>i�</sub></TD>
    <TD align=middle><input name=useranswer1></TD>
    <TD>�C</TD>
  </TR>
  <TR>
        <TD width=156>T<sub>d��</sub></TD>
    <TD width="144" align=middle><input name=useranswer2></TD>
    <TD width=72>�C</TD>
  </TR>
  <TR>
    <TD colSpan=3>
      <P 
      align=center><INPUT type=submit value=CEVAPLA name=submit1></P></TD></TR>
<TR>
    <TD colSpan=3><P></P></TD></TR>
<TR>
    <TD width=156>Sorunun de�eri=</TD>
    <TD align=middle><%=strvalueforquestion%></TD>
    <TD width=72>puan</TD>
</TR>
</TBODY></TABLE>
</P>
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
	  double varTic=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
	  double varTdis=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer3",TableName)).doubleValue ();
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
          dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); 
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
		  lowerlimit[1]=varTic*(1-QuesRange);upperlimit[1]=varTic*(1+QuesRange);
		  lowerlimit[2]=varTdis*(1-QuesRange);upperlimit[2]=varTdis*(1+QuesRange);
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
    }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
}//GradeIt

}//class Grade

%>
  
