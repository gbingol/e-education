<%@ page language="java" import="jspclass.*,jspclass.JFluids.*,java.io.*,java.util.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2b_SURTUNMESIZBORU";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[8];
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
  double L=0,x=0,Dic=0,V=0,Tsu=0,hdis=0,Tdis=0,k_boru=0,q=0,ust=0;
  double T_bulk,k,Ro,Viskozite,Pr,Nu,h,Aic,Adis,Ddis;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  	  double Red=0;
      do{
	  
			SuOzellikleri su=new SuOzellikleri();
			//
            L =(double)(int)(Math.random() * 10 + 5);
            x =(double)(int)Math.random() *2 +1 ;
            Dic =(double)(int)(Math.random() * 10 + 10);
            V=(double)(int)(Math.random()*5+10);
            Tsu =(double)(int)(Math.random() * 40 + 40);
            hdis=(double)(int)(Math.random()*20+20);
            Tdis=(double)(int)(Math.random()*5+5);
            k_boru=(double)(int)(Math.random()*20+40);
            T_bulk=Tsu;   //Ortalama sicaklik oldu�undan bulk sicaklik alinabilir
            k=su.k(T_bulk);
            Ro=su.Ro(T_bulk);
            Viskozite=su.Viskozite(T_bulk);
            Pr=su.Pr(T_bulk);
            Red=V*(Dic/100)*Ro/Viskozite;
            if(Tsu>Tdis) ust=0.3;   ////E�er boru i�indeki su so�utuluyorsa 0.3, 
            if(Tsu<Tdis) ust=0.4;  //E�er boru i�indeki �s�t�l�yorsa 0.4
            Nu=0.023*Math.pow(Red,0.8)*Math.pow(Pr,ust);
            h=k*Nu/(Dic/100);
            double ric=Dic/2;
            double rdis=Dic/2+x;
            Aic=2*Math.PI*(ric/100)*L;
            Adis=2*Math.PI*(rdis/100)*L;
            double dT=Tsu-Tdis;
            double direnc=1/(h*Aic)+Math.log(rdis/ric)/(2*Math.PI*k_boru*L)+1/(hdis*Adis);
            q=dT/direnc;
        }while(!(Red>2300+1000));  //1000 guvenlik payi
       values[0]=L;values[1]=x;values[2]=Dic;values[3]=V;
	   values[4]=Tsu;values[5]=hdis;values[6]=Tdis;values[7]=k_boru;
      //
	  File file=new File(TableName+"_"+username+".txt");
	  if(!file.exists())
	 {
	 try
		{
		FileOutputStream fo=new FileOutputStream(file);
		DataOutputStream outData = new DataOutputStream ( fo ) ;
		outData .writeChars("Ro="+String.valueOf(Ro)+"\n");
		outData .writeChars("Pr ust="+String.valueOf(ust)+"\n");
		outData .writeChars("Re="+String.valueOf(Red)+"\n");
		outData .writeChars("Nu="+String.valueOf(Nu)+"\n");
		outData .writeChars("h="+String.valueOf(h)+"\n");
		outData.close();
		fo.close();
		}catch(Exception e){System.out.println("Exception occurred at filenotexist part"+" "+e);}
	}	
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(q));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  L=values[0];x=values[1];Dic=values[2];V=values[3];
  Tsu=values[4];hdis=values[5];Tdis=values[6];k_boru=values[7];
}

%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<script language="JavaScript" src="../../StringFunctions.js">
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
     strvalueforquestion="Maalesef Artyk 0 :((";
    }
  }
%>
<P align=center><font face="Comic Sans MS"><STRONG><FONT size=5 color="#ff0080">S�RT�NMES�Z 
  BORUDAN OLAN ISI KAYBI</FONT></STRONG> </font></P>
<P align="justify"><font face="Comic Sans MS"><%=L%> (m) uzunlu�unda, <%=x%> (cm) et kal�nl���nda, <%=Dic%> (cm) i� �ap�ndaki bir boru i�inden <%=V%> (m/s) h�z ile ortalama <%=Tsu%>�C s�cakl���nda su ge�mektedir. D�� y�zey �s� ta��n�m katsay�s� 
  <%=hdis%> (W/m<sup>2</sup>K), d�� s�cakl�k <%=Tdis%>�C, borunun �s� iletim katsay�s� <%=k_boru%> (W/mK) oldu�una g�re �s� kayb�n� (W) hesaplay�n�z. </font></P>
<P align=justify style="line height=200%">&nbsp;</P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("surtunmesizboru.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=379 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=184><font face="Comic Sans MS">Borudan olan �s� kayb� =</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=33><font face="Comic Sans MS">(W)</font></TD>
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
      dbc.connect("HeatTransfer","","");
      double useranswer[]=new double[results.length];
      for(int j=0;j<results.length;j++)
      {
        useranswer[j]=Double.valueOf(results[j]).doubleValue();
      }
      double totalpoint=0;
      double doublevalueforquestion=0;
      double varq=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
          lowerlimit[0]=varq*(1-QuesRange);upperlimit[0]=varq*(1+QuesRange);
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
  
