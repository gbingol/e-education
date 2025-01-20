<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="Odev1b_4";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[2];
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
  	try{
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
  double m,P,dV,Q;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
		 m=(double)(int)(Math.random()*100+150);     //gr olarak
         P=(double)(int)(Math.random()*100+100);     //kPa olarak
		//
		double vg=0,vf=0,T=0,_m=0,hf=0,hg=0;
		jspclass.JFluids.SuOzellikleri su=new jspclass.JFluids.SuOzellikleri();
        T=su.T(P);
        vf=su.vf(T);
        vg=su.vg(T);
        _m=m/1000;
        dV=(_m*(vg-vf))*1000;
        hf=su.hf(T);
        hg=su.hg(T);
        Q=_m*(hg-hf);
		//
       values[0]=m;values[1]=P;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(dV));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(Q));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  m=values[0];P=values[1];
}

%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=iso-8859-9">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<script language="JavaScript" src="../../StringFunctions.js" >
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
<P align=center><font face="Comic Sans MS"><STRONG><FONT size=5 color="#ff0080">Sabit 
  Bas�n�ta Buharla�an S�v�</FONT></STRONG></font></P>
<P align="justify"><font face="Comic Sans MS"><%=m%> (gr) k�tlesindeki doymu� s�v� su, <%=P%> (kPa) sabit bas�n�ta t�m�yle buharla�t�r�lmaktad�r. Buna g�re,</font></P>
<P align="justify"><font face="Comic Sans MS"> A) Hacim de�i�ikli�ini (lt),<br>
  B) Suya verilen enerjiyi (kJ) hesaplay�n�z.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("buharlasansivi.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=379 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=184><font face="Comic Sans MS">Hacim de�i�ikli�i=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name="useranswer0">
          </font></TD>
        <TD width=33><font face="Comic Sans MS">lt</font></TD>
      </TR>
	  <TR> 
        <TD width=184><font face="Comic Sans MS">Suya verilen enerji=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name="useranswer1">
          </font></TD>
        <TD width=33><font face="Comic Sans MS">kJ</font></TD>
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
<jsp:include page="questionlinks.jsp" flush="true"/>
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
      double vardV=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double varQ=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
          lowerlimit[0]=vardV*(1-QuesRange);upperlimit[0]=vardV*(1+QuesRange);
		  lowerlimit[1]=varQ*(1-QuesRange);upperlimit[1]=varQ*(1+QuesRange);
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
			  out.println(String.valueOf(totalpoint));
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
  
