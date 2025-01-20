<%@ page language="java" import="jspclass.*,java.io.*,java.util.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2b_LEVHADASINIRTABAKA";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[4];
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
  double T,V,L,Tlevha,q,Sigma1,Sigma2;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  double Tf,Ro,Viskozite,Pr,k,Cp,ReL,NuL,h;
  HavaOzellikleri hava=new HavaOzellikleri();
      do{
            T =(double)(int)(Math.random() * 15 + 15);
            V =(double)(int)(Math.random() * 30 + 40);
            L =(double)(int)(Math.random() * 40 + 50);
            Tlevha =(double)(int)(Math.random() * 30 + 50);
            Tf=(T+Tlevha)/2;    //�C
            Ro=101325/(287*(Tf+273.15));
            Viskozite=hava.Viskozite (Tf);
            Pr=hava.Pr(Tf);
            k=hava.k(Tf);
            Cp=hava.Cp(Tf);
            ReL=(Ro*V*(L/100))/Viskozite;
            NuL=Math.pow(Pr,1.0/3.0)*(0.037*Math.pow(ReL,0.8)-871);
            h=NuL*k/(L/100);
            double Area=(L/100)*1;   //1=birim uzunluk
            q=h*Area*(Tlevha-T);
            double Rex=ReL;
            //Ak�m Levhan�n ba�lang�c�nda geli�ti�inde s�n�r tabaka kal�nl���
            Sigma1=(L/100)*0.381*Math.pow(Rex,(-1.0/5.0));
            Sigma1=Sigma1*100;    //birim cm oluyor
            //Ak�m Reort=5*10^5'den ba�layarak geli�ti�inde s�n�r tabaka kal�nl���
            Sigma2=(L/100)*(0.381*Math.pow(Rex,(-1.0/5.0))-10256*Math.pow(Rex,-1));
            Sigma2=Sigma2*100;    //birim cm oluyor
           //Turbulent de�er verilmesi sa�laniyor
           //25000 degeri ��rencilerin 5*10^5'i ge�tiklerinden emin olmak i�in
      }while(!(ReL>(5*Math.pow(10,5)+25000)));
      values[0]=T;values[1]=V;values[2]=L;values[3]=Tlevha;
      //
	  File file=new File(TableName+"_"+username+".txt");
	  if(!file.exists())
	 {
	 try
		{
		FileOutputStream fo=new FileOutputStream(file);
		DataOutputStream outData = new DataOutputStream ( fo ) ;
		outData .writeChars("Pr="+String.valueOf(Pr)+"\n");
		outData .writeChars("ReL="+String.valueOf(ReL)+"\n");
		outData .writeChars("NuL="+String.valueOf(NuL)+"\n");
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
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(Sigma1));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer3",TableName,String.valueOf(Sigma2));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  T=values[0];V=values[1];L=values[2];Tlevha=values[3];
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

<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT size=5 color="#ff0080">LEVHADA SINIR TABAKA</FONT></STRONG></P>
<P align=justify style="line height=200%"><%=T%> �C, 1 (atm) ko�ullar�ndaki hava, bir d�z levha �zerinden <%= V %> (m/s) h�zla akmaktad�r. Levha <%= L%> (cm) uzunlu�unda olup <%= Tlevha %>�C'de sabit tutulmaktad�r. z y�n�nde birim uzunluk kabul edip,</P>
<P align=justify style="line height=200%">a) Levhadan havaya olan �s� transferini 
  (W), <BR>
  b) Ak�m�n levhan�n ba�lang�c�nda geli�ti�ini, <BR>
  c) Re<sub>ort</sub>=5*10<sup>5</sup>'den ba�larak geli�ti�ini kabul ederek t�rb�lant 
  s�n�r tabaka kal�nl���n� (cm) hesaplay�n�z. </P>
<P align=center><STRONG>CEVAPLAMA B�L�M�</STRONG></P>
<FORM action="<%=response.encodeURL("levhadasinirtabaka.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
<P align=center>
  <TABLE cellSpacing=1 cellPadding=1 width=473 
border=1>
    <TBODY bgColor=aqua>
  <TR>
        <TD width=270><font face="Comic Sans MS">Levhadan havaya olan �s� transferi=</font></TD>
    <TD width="144" align=middle><INPUT name=useranswer0></TD>
    <TD width=41>(W)</TD></TR>
  <TR>
        <TD width=270><font face="Comic Sans MS">Ak�m�n levhan�n ba�lang�c�nda geli�ti�i s�n�r tabaka kal�nl���=</font></TD>
    <TD align=middle><INPUT name=useranswer1></TD>
    <TD width=41>(cm)</TD></TR>
  <TR>
      <TD width=270><font face="Comic Sans MS">Ak�m�n Re<sub>ort</sub>=5*10<sup>5</sup>'den  geli�ti�i s�n�r tabaka kal�nl���=</font></TD>
      <TD align=middle><INPUT name=useranswer2></TD>
      <TD width=41>(cm)</TD></TR>
  <TR>
    <TD colSpan=3>
      <P 
      align=center><INPUT type=submit value=CEVAPLA name=submit1></P></TD></TR>
<TR>
    <TD colSpan=3><P></P></TD></TR>
<TR>
    <TD width=270>Sorunun de�eri=</TD>
    <TD align=middle><%=strvalueforquestion%></TD>
    <TD width=41>puan</TD>
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
      double varq=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double varSigma1=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
	  double varSigma2=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer3",TableName)).doubleValue ();
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
		  lowerlimit[1]=varSigma1*(1-QuesRange);upperlimit[1]=varSigma1*(1+QuesRange);
		  lowerlimit[2]=varSigma2*(1-QuesRange);upperlimit[2]=varSigma2*(1+QuesRange);
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
  
