<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2a_SinirTabaka";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   HavaOzellikleri hava=new HavaOzellikleri();
   double values[]=new double[5];
   String results[]=new String[3];
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
  double T,P,V,x1,x2;
  double Ro,Re1,Re2,Sigma1,Sigma2,dm;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      do{
        T=(double)(int)(Math.random()*10+25);   //Hava s�cakl�g� �C
        P=(double)(int)(Math.random()*10+95); //Havan�n bas�nc� (kPa)
        V=(double)(int)(Math.random()*4+1);    //Havan�n h�z�  (m/s)
        x1=(double)(int)(Math.random()*10+10);        //Ilk a cm lik uzakl�k
        x2=x1+(double)(int)(Math.random()*10+10);  //Ilk b cm lik uzakl�k b=a+herhangi bir say�
        //
        Ro=(P*1000)/(287.0*(T+273.15));
        double Viskozite=hava.Viskozite(T);
        Re1=Ro*V*(x1/100)/Viskozite;
        Re2=Ro*V*(x2/100)/Viskozite;
        Sigma1=(4.64*(x1/100)/Math.sqrt (Re1))*100;    //metre*100=cm
        Sigma2=(4.64*(x2/100)/Math.sqrt (Re2))*100;    //metre*100=cm
        dm=5.0/8.0*Ro*V*(Sigma2/100-Sigma1/100)*1000;        //gr/s    kg/s �ok k���k oldu�undan aral�k zor tutturulur
          //
      }while(!(Re2<5*Math.pow(10,5)));    //Laminer kalma �art�na bak
      values[0]=T;values[1]=P;values[2]=V;values[3]=x1;values[4]=x2;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Sigma1));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(Sigma2));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer3",TableName,String.valueOf(dm));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  T=values[0];P=values[1];V=values[2];x1=values[3];x2=values[4];
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
<P align=center><STRONG><FONT size=5 color="#ff0080">SINIR TABAKA</FONT></STRONG> </P>

<P align="justify" style="line height: 200%"><%=T%>�C,<%=P%> (kPa) ko�ullar�ndaki hava bir levha �zerinden <%=V%> (m/s) h�zla akmaktad�r.
<BR>
A) Levhan�n ba�lang�c�ndan x<SUB>1</SUB>=<%=x1%> ve x<SUB>2</SUB>=<%=x2%> (cm) uzakl���ndaki s�n�r tabaka kal�nl���n� (cm),
<BR>
B) x<SUB>1</SUB>=<%=x1%> ve x<SUB>2</SUB>=<%=x2%> (cm) aras�ndan s�n�r tabakaya giren debiyi (g/s) hesaplay�n�z.
<BR>
<STRONG>NOT1:</STRONG>Yo�unluk hari� ak��kan�n fiziksel �zelliklerini P=1 (atm) kabul ederek hesaplayabilirsiniz
<BR>
<STRONG>NOT2:</STRONG>T(K)=T�C+273.15 �eklinde hesaplay�n�z.
</P>

<P align=center><STRONG>CEVAPLAMA B�L�M�</STRONG></P>
<FORM action="<%=response.encodeURL("sinirtabaka.jsp")%>" method="POST">
<P align=center>
<TABLE cellSpacing=1 cellPadding=1 width=470 border=1>
  <TBODY bgColor=aqua>
  <TR>
    <TD width=380>x<SUB>1</SUB> cm uzakl���ndaki s�n�r tabaka kal�nl���=</TD>
    <TD align=middle><INPUT name=useranswer0></TD>
    <TD width=40>(cm)</TD></TR>
  <TR>
    <TD width=380>x<SUB>2</SUB> cm uzakl���ndaki s�n�r tabaka kal�nl���=</TD>
    <TD align=middle><INPUT name=useranswer1></TD>
    <TD width=40>(cm)</TD></TR>
  <TR>
    <TD width=380>S�n�r tabakaya giren debi=</TD>
    <TD align=middle><INPUT name=useranswer2></TD>
    <TD width=40>(g/s)</TD></TR>
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
      double varSigma1=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      double varSigma2=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
      double vardm=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer3",TableName)).doubleValue ();
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
          lowerlimit[0]=varSigma1*(1-QuesRange);upperlimit[0]=varSigma1*(1+QuesRange);
          lowerlimit[1]=varSigma2*(1-QuesRange);upperlimit[1]=varSigma2*(1+QuesRange);
          lowerlimit[2]=vardm*(1-QuesRange);upperlimit[2]=vardm*(1+QuesRange);
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
}//Class grade
%>
  
