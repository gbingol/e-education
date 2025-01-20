<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="RANKINECYCLE";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;
   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[3];
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
  double Tyog,Tturbin,Pturbin,farkTyog,farkTturbin,farkPturbin,Wturbin,Wnet,Qkazan,Verim;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);
  if(!IsValuesGiven){
		MHFluids fluid=null;
		MHFluidsSaturated sfluid=null;
      	MHFluidsSuperHeated shfluid=null;
		sfluid=new SuOzellikleri();
		shfluid=new KizginSuBuhari();
		//
        double P1,h1,v1,P2,s2,s1,Wpompa,h2,P3,T3,h3,h4,s3,P4,T4,s4,x4;
		do{
          Tyog=(double)(int)(Math.random()*40+60);
		  farkTyog=(double)(int)(Math.random()*10+10);
          Tturbin=(double)(int)(Math.random()*300+200);
		  farkTturbin=(double)(int)(100+Math.random ()*100);
          Pturbin=(double)(int)(Math.random()*3+2);
		  farkPturbin=(double)(int)(2+Math.random()*3);
		  //
          T4=Tyog;
          P1=sfluid.P(T4);  //kPa
          h1=sfluid.hf(T4);v1=sfluid.vf(T4);
          P3=Pturbin;     //MPa
          T3=Tturbin;
          P2=P3*1000;   //kPa
          Wpompa=v1*(P2-P1);
          h2=h1+Wpompa;
          h3=shfluid.h(P3,T3);s3=shfluid.s(P3,T3);
          P4=P1;    //kPa
          s4=s3;
          T4=sfluid.T(P4);
          double sf4=sfluid.sf(T4);
          double sg4=sfluid.sg(T4);
          double sfg4=sg4-sf4;
          x4=(s4-sf4)/sfg4;
          h4=sfluid.hf(T4)+x4*(sfluid.hg(P4)-sfluid.hf(T4));
          Wturbin=h3-h4;
          Wnet=Wturbin-Wpompa;
          Qkazan=h3-h2;
          Verim=Wnet/Qkazan*100;
        }while(!((x4>0.75)&&(x4<0.90)&&(Wpompa>2)&&(Wturbin>400)&&(Verim>20)&&(Verim<30)));
								
		//						
       values[0]=Tyog;values[1]=Tturbin;values[2]=Pturbin;
       //
       //
  	 for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("Number",username,"SystemAnswer1",TableName,String.valueOf(x4*100));
   dbc.updateString("Number",username,"SystemAnswer2",TableName,String.valueOf(Verim));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  dbc.close();
  Tyog=values[0];Tturbin=values[1];Pturbin=values[2];
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><strong>Rankine 
  �evrimi</strong></font></P>
<P align="justify"><font face="Comic Sans MS">Consider a steam power plant operating
    on the simple ideal Rankine cycle. Condenser temperature is <%=Tyog%> �C and steam enter the turbine at <%=Tturbin%> �C temperature and<%=Pturbin%> (MPa)
    pressure. With the given values calculate,<br>
  A) Vapor quality of steam exiting turbine,<br>
B ) % Efficiency.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("rankinecevrimi.jsp")%>" method="POST">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=414 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=179><font face="Comic Sans MS">%Vapor quality=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=70>&nbsp;</TD>
      </TR> 
    <TD width=179><font face="Comic Sans MS">%Efficiency=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer1>
          </font></TD>
        
      <TD width=70>&nbsp;</TD>
    </TR>
      <TR> 
        <TD colSpan=3> <P 
      align=center> <font face="Comic Sans MS"> 
            <INPUT type=submit value=GRADE name=submit1>
            </font></P></TD>
      </TR>
      <TR> 
        <TD colSpan=3><P></P></TD>
      </TR>
      <TR> 
        <TD width=179><font face="Comic Sans MS">Value of the question=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=70><font face="Comic Sans MS">points</font></TD>
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
      double varVerim=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
		  lowerlimit[1]=varVerim*(1-QuesRange);upperlimit[1]=varVerim*(1+QuesRange);
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
              out.println(String.valueOf(useranswer[i])+" value is true.");
            }else{
              if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
              if(isConverted[1]) upperlimit[i]=-upperlimit[i];
              if(isConverted[2]) useranswer[i]=-useranswer[i];
              out.println("<BR>");
              out.println(String.valueOf(useranswer[i])+" value is false.");
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
  
