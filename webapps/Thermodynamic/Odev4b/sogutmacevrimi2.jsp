<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="Odev4b_2";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
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
  String name_surname=student.name_surname;
  dbc.connect("Thermodynamics","","");
  
  String strusername=dbc.getSelectedCell(username,"STUDENTNUMBER",TableName);
  if(strusername.equals(""))
  {
  	try
	{
  		String SQLQuery="INSERT INTO "+TableName+" (NAME_SURNAME,STUDENTNUMBER) "+" VALUES ('"+name_surname+"',"+"'"+username+"')";
		dbc.runSQL(SQLQuery);
	}catch(Exception e){out.println("Hata olustu. Bu soru icin verilerinizi tekrar almaniz gerekiyor!!!");}
   }	
  
  String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);
  if(numberofanswer.equals("")) numberofanswer="1";
  int intnumberofanswer=Integer.valueOf(numberofanswer).intValue(); 
  double doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
  String strvalueforquestion=String.valueOf(doublevalueforquestion);
%>
<%
  double Tevaporator,Kizdirma,Tyogunlastirici,AsiriSogutma,x4,Qyog,Qevap,Wkomp,COP;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      double T1,P1,h1,P3,T3,h3,hf3,T4,hf4,hg4,h4,s1,sg1,s2,T2,P2,h2,P4;
	  MHFluids fluid=null;
      MHFluidsSaturated sfluid=null;
	  MHFluidsSuperHeated shfluid=null;
	  sfluid=new Doymus134A();shfluid=new Kizgin134A();
	  //
      Tevaporator=Double.valueOf(dbc.getSelectedCell(username,"Value1",TableName)).doubleValue ();
      Kizdirma=Double.valueOf(dbc.getSelectedCell(username,"Value2",TableName)).doubleValue ();
      Tyogunlastirici=Double.valueOf(dbc.getSelectedCell(username,"Value3",TableName)).doubleValue ();
      AsiriSogutma=Double.valueOf(dbc.getSelectedCell(username,"Value4",TableName)).doubleValue ();
      T3=Tyogunlastirici;
      hf3=sfluid.hf(T3);
      h3=hf3;
      T4=Tevaporator;
      P4=sfluid.P(T4);
      P1=P4;
      hf4=sfluid.hf(T4);hg4=sfluid.hg(T4);
      h4=h3-AsiriSogutma;
      x4=(h4-hf4)/(hg4-hf4);
      h1=sfluid.hg(T4)+Kizdirma; //In superheated region
      double temph,tempT=T4;
      do{
        tempT=tempT+0.1;
        temph=shfluid.h(P1/1000,tempT);
      }while(!(Math.abs(temph-h1)<0.1));
      double XX=tempT;double YY=tempT+10;
      double ort;
      do{
        ort=(XX+YY)/2;
        temph=shfluid.h(P4/1000,ort);
        if((temph-h1)>0) YY=ort;
        if((temph-h1)<0) XX=ort;
      }while(!(Math.abs(temph-h1)<0.0001));
      T1=ort;
      s1=shfluid.s(P1/1000,T1);
      s2=s1;
      P3=sfluid.P(T3)/1000; //MPa
      P2=P3;              //MPa
      double temps;
      tempT=5;
      do{
        tempT=tempT+0.5;
        temps=shfluid.s(P2,tempT);
      }while(!(Math.abs(temps-s2)<0.01));
      XX=tempT;YY=tempT+5;
      do{
        ort=(XX+YY)/2;
        temps=shfluid.s(P2,ort);
        if((temps-s2)>0) YY=ort;
        if((temps-s2)<0) XX=ort;
      }while(!(Math.abs(temps-s2)<0.0001));
      T2=ort;
      h2=shfluid.h(P2,T2);
      Qyog=h2-h3;
      Qevap=h1-h4;
      Wkomp=h2-h1;
      COP=Qevap/Wkomp;
								
		//						
       values[0]=Tevaporator;values[1]=Kizdirma;values[2]=Tyogunlastirici;values[3]=AsiriSogutma;
       //
       //
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(x4*100));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(COP));
 }//isValuesGiven
 else{
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
<BODY background="/images/background.gif">
<script language="JavaScript" >
var isSubmit=true;
function checkDigitSeperator(frm)
{
	var i=0;
	var formElements=frm.elements;
	for(i=0;i<formElements.length;i++)
	{
		var j=0;
		if(formElements[i].type!="text") continue;
		txtValue=formElements[i].value;
		if(txtValue=="")
		{
			alert("T�m metin kutular�n� doldurman�z gerekmektedir."); return false;
		}
		while(txtValue.charAt(j)!="")
		{
			if(txtValue.charAt(j)==",")
			{
			alert("Ondalik ayraci olarak nokta kullan�nz");
			isSubmit=false;
			return false;
			}
			j++;
		}
	}
	return true;
	
}
</script>
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><strong>So�utma
       �evrimi-2</strong></font></P>
<P align="justify"><font face="Comic Sans MS">R134a'nin i� g�ren ak��kan olarak 
  kullan�ld��� bir so�utma �evriminde, buharla�t�r�c� s�cakl��� <%=Tevaporator%>�C ve yo�unla�t�r�c� s�cakl��� <%=Tyogunlastirici%>�C'dir. �evrimde K�zd�rma=<%=Kizdirma%> kJ/kg ve A��r� So�utma=<%=AsiriSogutma%>kJ/kg'd�r. Verilen de�erleri kullanarak,<br>
  A) Genle�me vanas� ��k���ndaki kuruluk derecesini,<br>
  B ) COP'yi hesaplay�n�z.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("sogutmacevrimi2.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=370 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=139><font face="Comic Sans MS">Kuruluk Derecesi=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=66><font face="Comic Sans MS">%</font></TD>
      </TR> 
      <TD width=139><font face="Comic Sans MS">COP=</font></TD>
        <TD width="147" align=middle><font face="Comic Sans MS"> 
          <INPUT name=useranswer1>
          </font></TD>
        
      <TD width=66>&nbsp;</TD>
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
        <TD width=139><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=66><font face="Comic Sans MS">puan</font></TD>
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
      dbc.connect("Thermodynamics","","");
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

}//grade class
%>
  
