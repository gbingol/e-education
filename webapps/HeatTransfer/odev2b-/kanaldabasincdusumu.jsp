<%@ page language="java" import="jspclass.*,jspclass.JFluids.*,java.io.*,java.util.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2b_KANALDABASINCDUSUMU";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=25;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[5];
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
  double a,b,V,L,Tsu_ort,h=0,dP;
  double T_bulk,Ro,Cp,Viskozite,k,Pr,Nu,DH,f;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  double Re=0;
      do{
	  		
	  		SuOzellikleri su=new SuOzellikleri();
			//
            a =(double)(int)(Math.random() * 5 + 5);  //cm
            b=(double)(int)(Math.random() * 5 + 5);   //cm
            V=(double)(int)(Math.random()*5+5); //m/s
            L =(double)(int)(Math.random() * 5 +5);         //Kanal�n uzunlugu (m)
            Tsu_ort=(double)(int)(Math.random()*10+20);            //Suyun ortalama sicakligi
            T_bulk=Tsu_ort;   //bulk s�cakl�k ortalama s�cakl�ga esittir.
            Ro=su.Ro (T_bulk);
            Viskozite=su.Viskozite(T_bulk);
            k=su.k(T_bulk);
            Pr=su.Pr(T_bulk);
            //Hidrolik �ap DH=4A/P  P=�slak �evre  (metre)
            DH=(4*(a/100*b/100))/(2*(a/100+b/100));
            Re=Ro*V*DH/Viskozite;
            Nu=0.023*Math.pow(Re,0.8)*Math.pow(Pr,0.4);
            h=Nu*k/DH;    //Yuzey film katsayisi
            f=0.316/(Math.pow(Re,1.0/4.0));
            dP=f*L/DH*Ro*(Math.pow(V,2)/2);		//Pa
        }while(!(Re>2300+1000));  //1000 guvenlik payi
        values[0]=a;values[1]=b;values[2]=L;values[3]=V;values[4]=Tsu_ort;
      //
	  File file=new File(TableName+"_"+username+".txt");
	  if(!file.exists())
	 {
	 try
		{
		FileOutputStream fo=new FileOutputStream(file);
		DataOutputStream outData = new DataOutputStream ( fo ) ;
		outData .writeChars("Ro="+String.valueOf(Ro)+"\n");
		outData .writeChars("Hidrolik Cap="+String.valueOf(DH)+"\n");
		outData .writeChars("Re="+String.valueOf(Re)+"\n");
		outData .writeChars("Nu="+String.valueOf(Nu)+"\n");
		outData .writeUTF("h="+String.valueOf(h)+"\n");
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
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(h));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(dP));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  a=values[0];b=values[1];L=values[2];V=values[3];Tsu_ort=values[4];
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
<P align=center><font face="Comic Sans MS"><STRONG><FONT size=5 color="#ff0080">KANAL 
  ���NDE BASIN� D���M�</FONT></STRONG> </font></P>
<P align="justify"><font face="Comic Sans MS">Demir sactan <%=a%> * <%=b%> (cm)'lik bir kanalda su <%=V%> (m/s) ortalma h�z ile akmaktad�r. Kanal <%=L%> (m) uzunlu�unda olup, suyun ortalama s�cakl��� <%=Tsu_ort%> �C'dir. Su kanal� tamamen doldurmaktad�r. Y�zey film katsay�s�n� 
  h (W/m<sup>2</sup>�C) ve boru boyunca bas�n� d���m�n� (Pa) hesaplay�n�z.</font></P>
<table width="37%" border="0">
  <tr>
    <td width="66%"><font face="Comic Sans MS">S�rt�nme katsay�s� i�in :</font></td>
    <td width="34%"><img src="/images/frictioncoefficient.gif" width="82" height="45"></td>
  </tr>
</table>
<P align=justify style="line height=200%">&nbsp;</P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("kanaldabasincdusumu.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=381 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=135><div align="right"><font face="Comic Sans MS">h=</font></div></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=84><font face="Comic Sans MS">(W/m<sup>2</sup>�C)</font></TD>
      </TR>
	  <TR> 
        <TD width=135><div align="right"><font face="Comic Sans MS">Bas�n� D���m�n�=</font></div></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer1>
          </font></TD>
        <TD width=84><font face="Comic Sans MS">(Pa)</font></TD>
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
        <TD width=135><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=84><font face="Comic Sans MS">puan</font></TD>
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
      double varh=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double vardP=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
          lowerlimit[0]=varh*(1-QuesRange);upperlimit[0]=varh*(1+QuesRange);
		  lowerlimit[1]=vardP*(1-QuesRange);upperlimit[1]=vardP*(1+QuesRange);
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
  
