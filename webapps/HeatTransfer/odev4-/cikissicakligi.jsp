<%@ page language="java" import="jspclass.*,jspclass.JFluids.*,java.io.*,java.util.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="4_CIKISSICAKLIGI";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=40;
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
  double Tgiris=0,Dboru=0,Vsu=0.0,Lboru=0,Tboru=0,Tcikis=0.0;
  SuOzellikleri su=new SuOzellikleri();
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  double Tbort=0,Tb1=0,Tb2,TempTb2,Ro,Cp,Viskozite,k,Pr,Re,Sart,ViskoziteW;
  double Temp1,Temp2,Temp3,Nu=0,h,Debi;
  File file=new File(TableName+"_"+username+".txt");
  if(!file.exists())
  {
		try
		{
			FileOutputStream fo=new FileOutputStream(file);
			DataOutputStream outData = new DataOutputStream ( fo ) ;
  			if(!IsValuesGiven)
			{
       			TempTb2=0;Tb2=0;
        		int iter=1;
        		end_it:
        		do
				{
          		if (iter == 1)
		  		{
            		Tgiris=(double)(int)(Math.random()*70+30);
            		Dboru=(Math.random()* 3 + 2)/100.0;
            		Vsu=(double)(int)(Math.random()* 8 + 2); // m/s 
            		Lboru=(double)(int)(Math.random()*5 + 1);
            		Tboru=(double)(int)(Math.random()*50+50+Tgiris); //Boru s�cakl��� her zaman suyun giris s�cakl���ndan y�ksek olmal� +20 g�venlik nedeniyle eklenmi�tir
            		Tbort = Tgiris;
            		Tb1 = Tgiris;
         		}
        		TempTb2 = Tb2;
        		Ro = su.Ro(Tbort);
        		Cp = su.Cp(Tbort);
        		Viskozite=su.Viskozite(Tbort);
        		k = su.k(Tbort);
        		Pr = su.Pr(Tbort);
        		Re = (Ro * Vsu* Dboru)/Viskozite;
        		Sart = Re * Pr * Dboru/Lboru;
        		ViskoziteW = su.Viskozite(Tboru);
        		if (Re < 2300)
				{   //E�er laminer b�lgedeyse
            		if (Sart < 10) break end_it;
            		Temp1 = Math.pow((Re * Pr),(1.0 /3.0));
            		Temp2 = Math.pow((Dboru/Lboru),(1.0 /3.0));
            		Temp3 = Math.pow((Viskozite / ViskoziteW),(0.14));
            		Nu = 1.86 * Temp1 * Temp2 * Temp3;
        		}
        		else if (Re > 2300)
				{
            		Nu = 0.023 * Math.pow(Re,0.8) * Math.pow(Pr,0.4);
        		}
        		h = k * Nu / Dboru;
        		Debi = Ro * Math.PI *Math.pow(Dboru,2)*Vsu/4.0;      //kg/s
        		Temp1 = h * Math.PI * Dboru * Lboru;
        		Temp2 = Debi * Cp * 1000;
        		Temp3 = Temp1 * Tboru- Temp1*Tb1/2 + Temp2 * Tb1;
        		Tb2 = Temp3/(Temp2 + Temp1 / 2);
        		Tbort = (Tb1 + Tb2) / 2;
				//
				outData .writeChars(String.valueOf(iter)+".iterasyon"+" Pr="+String.valueOf(Pr)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+" k="+String.valueOf(k)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+" Debi="+String.valueOf(Debi)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+" h="+String.valueOf(h)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+"Re="+String.valueOf(Re)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+"Nu="+String.valueOf(Nu)+"\n");
				outData .writeChars(String.valueOf(iter)+".iterasyon"+"T��k��="+String.valueOf(Tb2)+"\n");
				outData .writeChars("***************************************************************\n");
				//
				iter++;
        	}while(!( (Math.abs(TempTb2 - Tb2)<0.001) && (Tboru/Tb2 > 1.25) ));
        	Tcikis=Tb2;
			outData.close();
			fo.close();
       		values[0]=Tgiris;values[1]=Dboru;values[2]=Vsu;values[3]=Lboru;values[4]=Tboru;
   			for(int i=0;i<values.length;i++)
			{
     			String ColumnToSet="Value"+String.valueOf(i+1);
     			dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   			}//for
   			dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   			dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Tcikis));
 	}//if(!IsValuesGiven)
	}catch(Exception e){System.out.println("Exception occurred at filenotexist part"+" "+e);}
  }//file.exists()
 if(IsValuesGiven)
 {
   for(int i=0;i<values.length;i++)
   {
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 	}
  	dbc.close();
  	Tgiris=values[0];Dboru=values[1];Vsu=values[2];Lboru=values[3];Tboru=values[4];
}//if

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
<P align=center><font face="Comic Sans MS"><STRONG><FONT size=5 color="#ff0080">SUYUN 
  �IKI� SICAKLI�I</FONT></STRONG></font></P>
<P align="justify"><font face="Comic Sans MS"><%=Tgiris%>�C s�cakl���ndaki su <%=Dboru%> (m) �ap�ndaki boruya <%=Vsu%> (m/s) ortalama h�z ile girmektedir. E�er boru <%=Lboru%> (m) uzunlu�unda ve y�zey s�cakl��� sabit <%=Tboru%>�C ise ��k�� s�cakl���n� hesaplay�n�z.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("cikissicakligi.jsp")%>" method="POST">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=379 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=184><font face="Comic Sans MS">��k�� s�cakl��� =</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=33><font face="Comic Sans MS">�C</font></TD>
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
      double varTcikis=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
          lowerlimit[0]=varTcikis*(1-QuesRange);upperlimit[0]=varTcikis*(1+QuesRange);
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
  
