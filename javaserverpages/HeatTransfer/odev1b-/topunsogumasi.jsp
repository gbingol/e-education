<%@ page  language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="1b_Conduction6";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[5];
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
  double D=0,To=0,Tortam=0,h=0,Tson=0,t=0;
  double Biot=0.0;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
        do{
          D=(double)(int)(Math.random()*5+3);
          To=(double)(int)(Math.random()*100+350);    //Topun ba�lang�� s�cakl���
          Tortam=(double)(int)(Math.random()*50+50);  //Topun b�rak�ld��� ortam�n s�cakl���
          h=(double)(int)(Math.random()*10+5);        //Y�zey ta��n�m �s� transfer katsay�s�
          Tson=Tortam+(double)(int)(Math.random()*50+50);  //Topun t (saniye) sonraki s�cakl���
		  double r=(D/2)/100.0;
   		  double V=4.0/3.0*Math.PI*Math.pow(r,3);
		  double A=4*Math.PI*Math.pow(r,2);
		  Biot=h*(V/A)/35;
		  double temp1=-h*A/(7800*0.46*1000/*Cp birimden dolay� 1000 le �arp�l�r*/*V);
		  double temp2=(Tson-Tortam)/(To-Tortam);
		  t=Math.log(temp2)/temp1;
        }while(!(Biot<0.1-0.02));    //Biot'un 0.1'den k���k olma �art�na bak
      values[0]=D;values[1]=To;values[2]=Tortam;values[3]=h;values[4]=Tson;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  
  D=values[0];To=values[1];Tortam=values[2];h=values[3];Tson=values[4];
}
	double r=(D/2)/100.0;
    double V=4.0/3.0*Math.PI*Math.pow(r,3);
    double A=4*Math.PI*Math.pow(r,2);
    Biot=h*(V/A)/35;
    double temp1=-h*A/(7800*0.46*1000/*Cp birimden dolay� 1000 le �arp�l�r*/*V);
    double temp2=(Tson-Tortam)/(To-Tortam);
    t=Math.log(temp2)/temp1;
	dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(t));
	//
	dbc.close();
%>

<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
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
<P align=center><STRONG><FONT size=5 color="#ff0080">Topun So�umas�</FONT></STRONG> </P>

<P align=justify style="line height: 200%"> <%=D%>(cm) �ap�ndaki top (Cp=0.46 kJ/kgK; k=35 W/m�C) ba�lang��ta uniform olarak <%=To%>�C'de iken aniden <%=Tortam%>�C s�cakl���nda sabit tutulan bir ortama
b�rak�lmaktad�r. Y�zey ta��n�m �s� transfer katsay�s� <%=h%> (W/m<sup>2</sup>�C)'dir. Topun s�cakl���n�n <%=Tson%>�C'ye d��mesi i�in ge�ecek zaman� (saniye) hesaplay�n�z.
<br><font color="#FF0000">NOT:</font> Topun yo�unlu�u=7800 (kg/m<sup>3</sup>).
</P>

<P align=center><STRONG><FONT face="Comic Sans MS">CEVAPLAMA B�L�M�</font></STRONG></P>
<FORM action="<%=response.encodeURL("topunsogumasi.jsp")%>" method="POST">
<P align=center>
<TABLE cellSpacing=1 cellPadding=1 width=390 border=1>
  <TBODY bgColor=aqua>
  <TR>
        <TD width=156><font face="Comic Sans MS">S�re</font>=</TD>
        <TD width="144" align=middle><INPUT name=useranswer0></TD>
    <TD width=72>(<font face="Comic Sans MS">saniye</font>)</TD>
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
      double vart=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
          lowerlimit[0]=vart*(1-QuesRange);upperlimit[0]=vart*(1+QuesRange);
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

}//class Grade

%>
  
