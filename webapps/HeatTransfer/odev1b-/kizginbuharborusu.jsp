<%@ page language="java" import="jspclass.*,java.util.*,java.sql.*" errorPage="" %>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="1b_KizginBuharBorusu";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[6];
   String results[]=new String[2];
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
  double R=0,L=0,x1=0,x2=0,Tdis=0,Tyuzey=0,Q=0,T_yalitkan_curuf=0;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      //do{
          R=(double)(int)(Math.random()*50+75);
          L=(double)(int)(Math.random()*50+50);
          x1=(double)(int)(Math.random()*10+15);  //Yal�tkan tu�la
          x2=(double)(int)(Math.random()*10+35);  //C�ruf elyaf
          Tdis=(double)(int)(Math.random()*200+400);  //Boru d�� y�zey metal s�cakl���
          Tyuzey=(double)(int)(Math.random()*10+25);  //Yal�tkan y�zey s�cakl���
		  double r1=0,r2=0,r3=0,pay=0,payda=0;
	      final double k1=0.14,k2=0.06; //k1 (yal�tkan tu�la), k2( c�ruf elyaf)
	      r1=R/2;
    	  r2=r1+x1;
	      r3=r2+x2;
	      pay=2*Math.PI*L*(Tdis-Tyuzey);
	      payda=Math.log(r2/r1)/k1+Math.log(r3/r2)/k2;
    	  Q=pay/payda;
      	  //
      	  T_yalitkan_curuf=Tdis-(Q*Math.log(r2/r1))/(k1*2*Math.PI*L);
      //}while(!(q>1));    //Laminer kalma �art�na bak
      values[0]=R;values[1]=L;values[2]=x1;values[3]=x2;values[4]=Tdis;values[5]=Tyuzey;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Q));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(T_yalitkan_curuf));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  R=values[0];L=values[1];x1=values[2];x2=values[3];Tdis=values[4];Tyuzey=values[5];
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
<P align=center><STRONG><FONT size=5 color="#ff0080">K�zg�n Buhar Borusu</FONT></STRONG> </P>

<P align=justify style="line height: 200%">Bir k�zg�n buhar borusu <%=R%> (mm) �ap�nda ve <%=L%>(m) uzunlu�unda olup, iki tabaka halinde yal�t�lm��t�r. Birinci tabaka <%=x1%>(mm) kal�nl���nda yal�tkan tu�las�yla (k=0.14 W/m�C), ikinci tabaka <%=x2%> (mm) kal�nl���nda c�ruf elyafla
(k=0.06 W/m�C) yal�t�lm��t�r. Boru d�� y�zey metal s�cakl��� <%=Tdis%>�C, yal�tkan y�zey s�cakl��� <%=Tyuzey%> oldu�una g�re,
<br>A) Borudan �s� kayb�n� (W),
<br>B) Yal�tkan tu�la ile c�ruf aras�ndaki s�cakl��� hesaplay�n�z.
</P>

<P align=center><STRONG><FONT face="Comic Sans MS">CEVAPLAMA B�L�M�</font></STRONG></P>
<FORM action="<%=response.encodeURL("kizginbuharborusu.jsp")%>" method="POST">
<P align=center>
<TABLE cellSpacing=1 cellPadding=1 width=390 border=1>
  <TBODY bgColor=aqua>
  <TR>
    <TD><font face="Comic Sans MS">Is� Kayb�=</font></TD>
    <TD align=middle><input name=useranswer0></TD>
    <TD>W</TD>
  </TR>
  <TR>
        <TD width=156><font face="Comic Sans MS">S</font>�cakl�k</TD>
    <TD width="144" align=middle><input name=useranswer1></TD>
    <TD width=72>�C</TD></TR>
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
      double varQ=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double varT=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
          lowerlimit[0]=varQ*(1-QuesRange);upperlimit[0]=varQ*(1+QuesRange);
		  lowerlimit[1]=varT*(1-QuesRange);upperlimit[1]=varT*(1+QuesRange);
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
  
