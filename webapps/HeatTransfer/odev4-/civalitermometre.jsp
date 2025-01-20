<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="4_CIVALITERMOMETRE";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
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
  double Epsilon,Toda,Tyuzey,h,Thava_gercek;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
   	double Tt,Ts,Temp1,Temp2,fark;
        do{
          Epsilon=Math.random() * 0.25 + 0.7;
          Toda= (double)(int)(Math.random()* 7 + 18);
          Tyuzey=(double)(int)(Math.random()* 5 + 5);
          h=(double)(int)(Math.random()*5+5);
          Tt = Toda+ 273.15;
          Ts = Tyuzey+ 273.15;
          Temp1 = (5.669 *Math.pow(10,-8)*Epsilon*(Math.pow(Tt,4) - Math.pow(Ts,4)));
          Temp2 = Temp1 / h;
          Thava_gercek= Tt + Temp2;
          fark= Thava_gercek- 273.15-Toda;
        }while(!(fark>5));
       values[0]=Epsilon;values[1]=Toda;values[2]=Tyuzey;values[3]=h;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Thava_gercek));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  Epsilon=values[0];Toda=values[1];Tyuzey=values[2];h=values[3];
}

%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><STRONG>C�VALI 
  TERMOMETRE </STRONG></font></P>
<P align="justify"><font face="Comic Sans MS">Cival� termometrenin yay�n�m katsay�s� 
  <%=Epsilon%> olup, metal bir odada s�cakl��� <%=Toda%> �C olarak g�stermektedir. Odan�n 
  duvarlar� zay�f izolasyonlu olup y�zey s�cakl��� <%=Tyuzey%> �C�dir. Oda havas�yla 
  termometre aras�ndaki <%=h%> (W/m<sup>2</sup>�C) (do�al ta��n�m) olarak al�nabilmektedir. Ger�ek 
  hava s�cakl���n� hesaplay�n�z. </font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("civalitermometre.jsp")%>" method="POST">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=379 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=184><font face="Comic Sans MS">Ger�ek s�cakl�k =</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=33><font face="Comic Sans MS">K</font></TD>
      </TR>
      <TR> 
        <TD colSpan=3> <P 
      align=center> <font face="Comic Sans MS">
            <INPUT type=submit value=CEVAPLA name=submit1>
            </font></P></TD>
      </TR>
      <TR> 
        <TD colSpan=3><P></P></TD>
      </TR>
      <TR> 
        <TD width=184><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=33><font face="Comic Sans MS">puan</font></TD>
      </TR>
    </TBODY>
  </TABLE>
  <font face="Comic Sans MS"></P> </font>
</FORM>
<P align=justify>&nbsp;</P>
<font face="Comic Sans MS">
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
</font> 
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
      double varThava_gercek=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
          lowerlimit[0]=varThava_gercek*(1-QuesRange);upperlimit[0]=varThava_gercek*(1+QuesRange);
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
}//class

%>
  
