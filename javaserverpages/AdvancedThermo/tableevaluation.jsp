<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="TABLEEVALUATION";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=10;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[10];
   String results[]=new String[5];
   String username=new String();
   DBC dbc=new DBC();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
  dbc.connect("AdvancedThermo","","");
%>
<%
  double Twater_1=0,Pwater_1=0,Twater_2=0,Xwater_2=0,TR12_1=0,PR12_1=0,XR12_2=0,PR12_2=0,TR134A=0,PR134A=0;
  double hwater=0,swater=0,vR12=0,hR12=0,sR134A=0;
  MHFluids fluid=null;
  MHFluidsSaturated sfluid=null;
  MHFluidsSuperHeated shfluid=null;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);
  if(!IsValuesGiven){
  		Twater_1=(double)(int)(Math.random()*200+300);
		Pwater_1=(double)(int)(Math.random()*1000+2000);		//k�zg�n halde olsun
		shfluid=new KizginSuBuhari();
		hwater=shfluid.h(Pwater_1/1000.0,Twater_1);
		//
		//
		Twater_2=(double)(int)(Math.random()*100+200);
		Xwater_2=(double)(int)(Math.random()*30+50);
		sfluid=new SuOzellikleri();
		swater=sfluid.sf(Twater_2)+(Xwater_2/100.0)*sfluid.sg(Twater_2);
		//
		//
		TR12_1=(double)(int)(Math.random()*50+50);
		PR12_1=(double)(int)(Math.random()*100+150);
		shfluid=new KizginR12();
		vR12=shfluid.v(PR12_1/1000.0,TR12_1);
		//
		//
		PR12_2=(double)(int)(Math.random()*200+100);
		XR12_2=(double)(int)(Math.random()*30+50);
		sfluid=new DoymusR12();
		double T=sfluid.T(PR12_2);
		hR12=sfluid.hf(T)+(XR12_2/100.0)*sfluid.hg(T);
		//
		//
		TR134A=(double)(int)(Math.random()*50+50);
		PR134A=(double)(int)(Math.random()*100+150);
		shfluid=new Kizgin134A();
		sR134A=shfluid.s(PR134A/1000.0,TR134A);
		//
       values[0]=Twater_1;values[1]=Pwater_1;
	   values[2]=Twater_2;values[3]=Xwater_2;
	   values[4]=TR12_1; values[5]=PR12_1;
	   values[6]=PR12_2; values[7]=XR12_2;
	   values[8]=TR134A; values[9]=PR134A;
       //
       //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("Number",username,"SystemAnswer1",TableName,String.valueOf(hwater));
   dbc.updateString("Number",username,"SystemAnswer2",TableName,String.valueOf(swater));
   dbc.updateString("Number",username,"SystemAnswer3",TableName,String.valueOf(vR12));
   dbc.updateString("Number",username,"SystemAnswer4",TableName,String.valueOf(hR12));
   dbc.updateString("Number",username,"SystemAnswer5",TableName,String.valueOf(sR134A));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  
  Twater_1=values[0];Pwater_1=values[1];
  Twater_2=values[2];Xwater_2=values[3];
  TR12_1=values[4]; PR12_1=values[5];
  PR12_2=values[6]; XR12_2=values[7];
  TR134A=values[8]; PR134A=values[9];
}

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
    if(IsValuesGiven&&request.getParameter("Water_1")!=null)
	{
	  String str_result=request.getParameter("hwater");
	  Grade gr=new Grade();
	  gr.rownumber=1;
	  gr.GradeIt(out,str_result);
	}
	//
	//
	if(IsValuesGiven&&request.getParameter("Water_2")!=null)
	{
	  String str_result=request.getParameter("swater");
	  Grade gr=new Grade();
	  gr.rownumber=2;
	  gr.GradeIt(out,str_result);
	}
	//
	//
	if(IsValuesGiven&&request.getParameter("R12_1")!=null)
	{
	  String str_result=request.getParameter("vR12");
	  Grade gr=new Grade();
	  gr.rownumber=3;
	  gr.GradeIt(out,str_result);
	}
	//
	//
	if(IsValuesGiven&&request.getParameter("R12_2")!=null)
	{
	  String str_result=request.getParameter("hR12");
	  Grade gr=new Grade();
	  gr.rownumber=4;
	  gr.GradeIt(out,str_result);
	}
	//
	//
	if(IsValuesGiven&&request.getParameter("R134A")!=null)
	{	  
	  String str_result=request.getParameter("sR134A");
	  Grade gr=new Grade();
	  gr.rownumber=5;
	  gr.GradeIt(out,str_result);
	}
}

%>
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><STRONG>Evaluating 
  Thermodynamic Properties</STRONG></font></P>
<FORM action="<%=response.encodeURL("tableevaluation.jsp")%>" method="POST">
  <table width="80%" border="1">
    <tr> 
      <td width="14%"><div align="center"><font color="#FF0000" face="Comic Sans MS"><strong>REFRIGERANT</strong></font></div></td>
      <td width="20%"><div align="center"><font color="#FF0000" face="Comic Sans MS"><strong>TEMPERATURE 
          �C </strong></font></div></td>
      <td width="16%"><div align="center"><font color="#FF0000" face="Comic Sans MS"><strong>PRESSURE 
          (kPa)</strong></font></div></td>
      <td width="38%"><div align="center"><font color="#FF0000" face="Comic Sans MS"><strong>VAPOR 
          QUALITY (%)</strong></font></div></td>
      <td width="38%"><div align="center"><font color="#FF0000"><strong><font face="Comic Sans MS">PROPERTY 
          TO BE EVALUATED</font></strong></font></div></td>
      <td width="12%"><div align="center"><font color="#FF0000"><strong><font face="Comic Sans MS">EVALUTE</font></strong></font></div></td>
    </tr>
    <tr> 
      <td><div align="center"><font face="Comic Sans MS">Water</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=Twater_1%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=Pwater_1%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">-</font></div></td>
      <td><div align="center"><font face="Comic Sans MS">h</font><font face="Comic Sans MS">=&nbsp; 
          <input name="hwater" type="text" id="hwater">
          </font></div></td>
      <td><div align="center"><font face="Comic Sans MS"> 
          <input name="Water_1" type="submit" id="Water_1" value="Evaluate">
          </font></div></td>
    </tr>
    <tr> 
      <td><div align="center"><font face="Comic Sans MS">Water</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=Twater_2%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">&nbsp;-</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=Xwater_2%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">s=&nbsp; 
          <input name="swater" type="text" id="swater">
          </font></div></td>
      <td><div align="center"><font face="Comic Sans MS"> 
          <input name="Water_2" type="submit" id="Water_2" value="Evaluate">
          </font></div></td>
    </tr>
    <tr> 
      <td><div align="center"><font face="Comic Sans MS">R-12</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=TR12_1%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=PR12_1%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">-</font></div></td>
      <td><div align="center"><font face="Comic Sans MS">v=&nbsp; 
          <input name="vR12" type="text" id="vR12">
          </font></div></td>
      <td><div align="center"><font face="Comic Sans MS"> 
          <input name="R12_1" type="submit" value="Evaluate">
          </font></div></td>
    </tr>
    <tr> 
      <td><div align="center"><font face="Comic Sans MS">R-12</font></div></td>
      <td><div align="center"><font face="Comic Sans MS">&nbsp;-</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=PR12_2%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=XR12_2%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">&nbsp;h= 
          <input name="hR12" type="text" id="hR12">
          </font></div></td>
      <td><div align="center"><font face="Comic Sans MS"> 
          <input name="R12_2" type="submit" id="R12_2" value="Evaluate">
          </font></div></td>
    </tr>
    <tr> 
      <td><div align="center"><font face="Comic Sans MS">R-134A</font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=TR134A%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS"><%=PR134A%></font></div></td>
      <td><div align="center"><font face="Comic Sans MS">-</font></div></td>
      <td><div align="center"><font face="Comic Sans MS">s=&nbsp; 
          <input name="sR134A" type="text" id="sR134A">
          </font></div></td>
      <td><div align="center"><font face="Comic Sans MS"> 
          <input name="R134A" type="submit" id="R134A" value="Evaluate">
          </font></div></td>
    </tr>
  </table>
</FORM>
<P align="justify"><font color="#0080FF" face="Comic Sans MS">Legend</font><font face="Comic Sans MS">: 
  <em>Enthalpy</em> h (kJ/kg), <em>Entropy</em>: s(kJ/kgK), <em>Specific Volume</em> 
  v: m3/kg;</font><font face="Comic Sans MS"> <BR>
<P><font face="Comic Sans MS" color="#FF0000">Below point of the each row is tabulated</font></P>
<TABLE border="1">
<%
  out.println("<TR>");
  for(int i=1;i<=results.length;i++)
  {
  	String strNumberofAnswer="NumberOfAnswer_"+String.valueOf(i);
  	String numberofanswer=dbc.getSelectedCell(username,strNumberofAnswer,TableName);
  	if(numberofanswer.equals("")) numberofanswer="1";
  	int intnumberofanswer=Integer.valueOf(numberofanswer).intValue(); 
  	double doublevalueforquestion=SorununNotDegeri/5.0*NotDusumAraligi[intnumberofanswer-1];
  	String strvalueforquestion=String.valueOf(doublevalueforquestion);
	out.println("<TD>"+"For "+String.valueOf(i)+". row "+strvalueforquestion+" points"+"</TD>");
 }
  out.println("</TR>");
  dbc.close();
%>
</TABLE>
<jsp:include page="questionlinks.jsp" flush="true"/>
</font> 
</BODY>
</HTML>

<%!
class Grade
{
	int rownumber=0;	//Default row number
    void GradeIt(JspWriter out,String results)
 {
      dbc.connect("AdvancedThermo","","");
      double useranswer=Double.valueOf(results).doubleValue();
      double totalpoint=0;
	  double ara_puan=0.0;
      double doublevalueforquestion=0;
	  String SystemAnswerNumber="SystemAnswer"+String.valueOf(rownumber);
      double varResult=Double.valueOf(dbc.getSelectedCell(username,SystemAnswerNumber,TableName)).doubleValue ();
      boolean isanswered=false;
	  String strNumberofAnswer="NumberOfAnswer_"+String.valueOf(rownumber);
      String numberofanswer=dbc.getSelectedCell(username,strNumberofAnswer,TableName);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {
        numberofanswer="1";
        dbc.updateString("Number",username,strNumberofAnswer,TableName,String.valueOf(1));
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
          dbc.updateString("Number",username,strNumberofAnswer,TableName,String.valueOf(intnumberofanswer)); //
     	  String ColumnToSet="UserAnswer"+String.valueOf(rownumber)+"_"+String.valueOf(intnumberofanswer-1);
		  dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(useranswer));
          //
          double upperlimit=0,lowerlimit=0;
          //
          lowerlimit=varResult*(1-QuesRange);upperlimit=varResult*(1+QuesRange);
          //
          //Check limits and useranswer for negativity
          //
          boolean isConverted[]=new boolean[3];
            isConverted[0]=false;isConverted[1]=false;isConverted[2]=false;
            if(lowerlimit<0) {lowerlimit=-lowerlimit;isConverted[0]=true;}
            if(upperlimit<0) {upperlimit=-upperlimit;isConverted[1]=true;}
            if(useranswer<0) {useranswer=-useranswer;isConverted[2]=true;}
            //
            if(useranswer<upperlimit&&useranswer>lowerlimit){
              if(isConverted[0]) lowerlimit=-lowerlimit;
              if(isConverted[1]) upperlimit=-upperlimit;
              if(isConverted[2]) useranswer=-useranswer;
              ara_puan=ara_puan+doublevalueforquestion/5.0;		//5.0 sorulan ��klar�n say�s�d�r. ��k say�s� artarsa de�i�tirilmeli. Kod dinamik de�il !!!
              out.println("<BR>");
              out.println(String.valueOf(useranswer)+" value is true.");
            }else{
              if(isConverted[0]) lowerlimit=-lowerlimit;
              if(isConverted[1]) upperlimit=-upperlimit;
              if(isConverted[2]) useranswer=-useranswer;
              out.println("<BR>");
              out.println(String.valueOf(useranswer)+" value is false.");
            }//if-else
          //
		  String StringNOTU="NOTU_"+String.valueOf(rownumber);
          dbc.updateString("Number",username,StringNOTU,TableName,String.valueOf(ara_puan));
		  //
		  for(int i=1;i<=5;i++)
		  {
		  	String StrColumnNumber="NOTU_"+String.valueOf(i);
			String ColumnNotu=dbc.getSelectedCell(username,StrColumnNumber,TableName);
			if(ColumnNotu.equals("")) ColumnNotu="0.0";
		  	double column_not=Double.valueOf(ColumnNotu).doubleValue ();
			totalpoint=totalpoint+column_not;
		  }
		  //
		  dbc.updateString("Number",username,"NOTU",TableName,String.valueOf(totalpoint));
		  //
          out.println("<BR>");
          out.println("You get "+String.valueOf(ara_puan)+" points.");
		  out.println("<BR>");
		  out.println("Your total score for this question is "+String.valueOf(totalpoint)+" points.");
          out.println("</FONT>");
          out.println("</P>");
          //
        }//if
    }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
}//GradeIt

}//grade class

%>
  
