<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="CARNOTCYCLE";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;
   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[4];
   String results[]=new String[2];
   String username=new String();
   DBC dbc=new DBC();
   Integer intnumberofanswer_=new Integer(1);
   int intnumberofanswer=intnumberofanswer_.intValue();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
  dbc.connect("AdvancedThermo","","");
  String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
  if(numberofanswer.equals("")) numberofanswer="1";
  int intnumberofanswer=Integer.valueOf(numberofanswer).intValue(); 
  double doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
  String strvalueforquestion=String.valueOf(doublevalueforquestion);
%>
<%
  double Tevaporator,Kizdirma,Tyogunlastirici,AsiriSogutma,x4,Qyog,Qevap,Wkomp,COP;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);
  if(!IsValuesGiven){
        double h1,P3,T3,h3,hf3,T4,hf4,hg4,h4,s1,sg1,s2,T2,P2,h2;
		MHFluids fluid=null;
      	MHFluidsSaturated sfluid=null;
		MHFluidsSuperHeated shfluid=null;
		sfluid=new Doymus134A();shfluid=new Kizgin134A();
        do{
          Tevaporator=-5-(int)(Math.random()*25);
          Kizdirma=0;
          Tyogunlastirici=(double)(int)(Math.random()*30+20);
          AsiriSogutma=0;
          T3=Tyogunlastirici;
          hf3=sfluid.hf(T3);
          h3=hf3;
          T4=Tevaporator;
          hf4=sfluid.hf(T4);hg4=sfluid.hg(T4);
          h4=h3-AsiriSogutma;
          x4=(h4-hf4)/(hg4-hf4);
          h1=sfluid.hg(T4)+Kizdirma;
          s1=sfluid.sg(T4);
          s2=s1;
          P3=sfluid.P(T3)/1000; //MPa
          P2=P3;              //MPa
          double temps,tempT=5;
          do{
            tempT=tempT+0.5;
            temps=shfluid.s(P2,tempT);
          }while(!(Math.abs(temps-s2)<0.01));
          double XX=tempT;double YY=tempT+5;
          double ort;
          do{
            ort=(XX+YY)/2;
            temps=shfluid.s(P2,ort);
            if((temps-s2)>0) YY=ort;
            if((temps-s2)<0) XX=ort;
          }while(!(Math.abs(temps-s2)<0.0001));
          T2=ort;
          h2=shfluid.h(P2,T2);
          Qyog=h2-h3;
          Qevap=sfluid.hg(T4)-h4;
          Wkomp=h2-h1;
          COP=Qevap/Wkomp;
        }while(!((COP>2)&&(COP<5)));
								
		//						
       values[0]=Tevaporator;values[1]=Kizdirma;values[2]=Tyogunlastirici;values[3]=AsiriSogutma;
       //
       //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("Number",username,"SystemAnswer1",TableName,String.valueOf(x4));
   dbc.updateString("Number",username,"SystemAnswer2",TableName,String.valueOf(COP));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  Tevaporator=values[0];Kizdirma=values[1];Tyogunlastirici=values[2];AsiriSogutma=values[3];
}//else

%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="../images/background.gif">
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><strong>Refrigeration
Cycle</strong></font></P>
<P align="justify"><font face="Comic Sans MS">In a refrigeration cycle that uses
    R134a as refrigerant, temperature of evaporator is <%=Tevaporator%>�C and temperature of condenser is <%=Tyogunlastirici%>�C.
    Using the given values calculate,<br>
  A) The quality of vapor exiting the expansion valve,<br>
B ) COP.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>ANSWERING SECTION</STRONG></font></P>
<FORM action="<%=response.encodeURL("sogutmacevrimi.jsp")%>" method="POST">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=392 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=139><font face="Comic Sans MS">Quality of Vapor=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=88>&nbsp;</TD>
      </TR> 
    <TD width=139><font face="Comic Sans MS">COP=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer1>
          </font></TD>
        
      <TD width=88>&nbsp;</TD>
    </TR>
      <TR> 
        <TD colSpan=3> <P 
      align=center> <font face="Comic Sans MS"> 
            <INPUT name=submit1 type=submit value=GRADE>
            </font></P></TD>
      </TR>
      <TR> 
        <TD colSpan=3><P></P></TD>
      </TR>
      <TR> 
        <TD width=139><font face="Comic Sans MS">Value of question=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=88><font face="Comic Sans MS">points</font></TD>
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
<jsp:include page="questionlinks.jsp" flush="true"/>
</font> 
</BODY>
</HTML>

<%!
class Grade
{
    void GradeIt(JspWriter out,String[] results)
 {
      dbc.connect("AdvancedThermo","","");
      double useranswer[]=new double[results.length];
      for(int j=0;j<results.length;j++)
      {
        useranswer[j]=Double.valueOf(results[j]).doubleValue();
      }
      double totalpoint=0;
      double doublevalueforquestion=0;
      double varx4=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      double varCOP=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
      boolean isanswered=false;
      String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {
        numberofanswer="1";
        dbc.updateString("Number",username,"NumberOfAnswer",TableName,String.valueOf(1));
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
          dbc.updateString("Number",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); //
          for(int i=0;i<results.length;i++)
		  {
		  	String ColumnToSet="UserAnswer"+String.valueOf(i+1)+"_"+String.valueOf(intnumberofanswer-1);
		  	dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(useranswer[i]));
		  }
          //
          double upperlimit[]=new double[results.length];
          double lowerlimit[]=new double[results.length];
          //
          lowerlimit[0]=varx4*(1-QuesRange);upperlimit[0]=varx4*(1+QuesRange);
		  lowerlimit[1]=varCOP*(1-QuesRange);upperlimit[1]=varCOP*(1+QuesRange);
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
              out.println(String.valueOf(useranswer[i])+" is true.");
            }else{
              if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
              if(isConverted[1]) upperlimit[i]=-upperlimit[i];
              if(isConverted[2]) useranswer[i]=-useranswer[i];
              out.println("<BR>");
              out.println(String.valueOf(useranswer[i])+" is false.");
            }//if-else
          }//for
          //
          dbc.updateString("Number",username,"NOTU",TableName,String.valueOf(totalpoint));
          out.println("<BR>");
          out.println("You get "+String.valueOf(totalpoint)+" points.");
          out.println("</FONT>");
          out.println("</P>");
          //
        }//if
    }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
}//GradeIt

}//grade class
%>
  
