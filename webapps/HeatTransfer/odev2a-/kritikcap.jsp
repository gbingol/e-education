<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2a_KritikCap";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
   final double QuesRange=0.01;
   

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[4];
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
  double d=0,hc=0,T=0,k=0,ro=0,BetonKalinligi=0;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
    do{
      d =(double)(int)(Math.random() * 90 + 50);values[0]=d;
      hc =(double)(int)(Math.random() * 20 + 25);values[1]=hc;
      T =(double)(int)(Math.random() * 95 + 5);values[2]=T;
      k =Math.random()*5;values[3]=k;
      ro= k/ hc;    //metre
      BetonKalinligi=ro*1000-d/2;      //mm
    }while(!(BetonKalinligi>50));    //Betonkalinligi makul bir deger olsun
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(BetonKalinligi));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  d=values[0];hc=values[1];T=values[2];k=values[3];
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
<P align=center><STRONG><FONT size=5 color="#ff0080">KR�T�K �AP</FONT></STRONG> </P>
<P align=justify style="line height: 200%">A��k havada do�al ta��n�ma a��k <%=d%>(mm) d��&nbsp;�apl� 
yatay bir �elik boru, silindirik olarak betonla kaplan�yor. Hangi beton 
kal�nl���nda (mm)&nbsp;�s� tranferi maximum olur?
<BR>�elik-hava, beton-hava i�in <%=hc%> (W/m<SUP>2</SUP> �C)'dir. Boru ile, hava arasindaki s�cakl�k fark� 
<%=T%>�C' lik bir de�erdedir. Beton <%=k%>(W/m�C)'dir.</P>
<P align=center><STRONG>CEVAPLAMA B�L�M�</STRONG></P>
<FORM action="<%=response.encodeURL("kritikcap.jsp")%>" method="POST">
<P align=center>
<TABLE style="WIDTH: 325px; HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=325 
border=1>
  <TBODY bgColor=aqua>
  <TR>
    <TD width=200>Beton Kal�nl���=</TD>
    <TD align=middle><INPUT name=useranswer0></TD>
    <TD width=40>(mm)</TD></TR>
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
<BR>
<jsp:include page="questionlinks.jsp" flush="true"/>
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
      double varBetonKalinligi=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      boolean isanswered=false;
      String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {
        numberofanswer="1";
        dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,String.valueOf(1));
      }
      intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
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
          lowerlimit[0]=varBetonKalinligi*(1-QuesRange);upperlimit[0]=varBetonKalinligi*(1+QuesRange);
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
  
