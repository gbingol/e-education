<%@ page language="java" import="jspclass.*,java.io.*,java.util.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="3_ISITRANSFERKATSAYISI";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=20;
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
  double Thava,Vhava,Dboru,Tboru,Lboru,h;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  double Tf,Rof,beta,Viskozitef,ViskoziteW,kf,Pr,Tb,ViskoziteB,Ref,Gr,Temp;
  double Temp2,Gz,Temp3,Temp4,Nu,P;
  HavaOzellikleri hava=new HavaOzellikleri();
        do{
          Thava=(double)(int)(Math.random()*10+20);   //Hava s�cakl�g� �C
          Vhava=(double)(int)(Math.random()*15+10);    //Havan�n h�z�  (cm/s)
          Dboru=(double)(int)(Math.random()*20+15);   //borunun �ap� (mm)
          Tboru=(double)(int)(Math.random()*100+100);   //Borunun s�cakl���
          Lboru=(double)(int)(Math.random()*3+1);  //Borunun uzunlu�u (m)
          Tf = (Tboru+Thava)/2;
          P=1*101325;   //Tablo okumada aksilik ��kmas�n diye herkese 1 atm veriliyor
          Rof = P/ (287 * (Tf+273.15));
          beta = 1.0/(Tf+273.15);
          Viskozitef = hava.Viskozite(Tf);
          ViskoziteW = hava.Viskozite(Tboru);
          kf = hava.k(Tf);
          Pr = hava.Pr(Tf);
          Tb = Thava;
		  double KinematikViskozite=Viskozitef/Rof;
          ViskoziteB = hava.Viskozite(Tb);
          Ref = (Rof * (Vhava/100)*(Dboru/1000)) /Viskozitef;
          Gr = (Math.pow(Rof,2) * 9.81 * beta * (Tboru - Thava) *Math.pow(Dboru/1000,3))/Math.pow(KinematikViskozite,2);
          Temp = Gr * Pr * (Dboru/ 1000) / Lboru;
        }while(!(Temp > 4 && Temp<Math.pow(10,6) && Ref<Math.pow(10,3))); //Gr*Pr*D/L k�s�t�n� grafikte m�teakip form�le denk getirmek i�in
        Temp2 = Math.pow((ViskoziteB / ViskoziteW),0.14);
        Gz = Ref * Pr * (Dboru/1000)/Lboru;
        Temp3 = (Gz + 0.012 * Math.pow((Gz * Math.pow(Gr,0.333333333)),(4.0/3.0)));
        Temp4 = Math.pow(Temp3,(1.0/3.0));
        Nu = 1.75 * Temp2 * Temp4;
        h=kf * Nu/(Dboru/1000);
       values[0]=Thava;values[1]=Vhava;values[2]=Dboru;values[3]=Tboru;values[4]=Lboru;
      //
	  File file=new File(TableName+"_"+username+".txt");
	  if(!file.exists())
	 {
	 try
		{
		FileOutputStream fo=new FileOutputStream(file);
		DataOutputStream outData = new DataOutputStream ( fo ) ;
		outData .writeChars("Film sicakliginda viskozite="+String.valueOf(Viskozitef)+"\n");
		outData .writeChars("Duvar sicakliginda viskozite="+String.valueOf(ViskoziteW)+"\n");
		outData .writeChars("Prandtl="+String.valueOf(Pr)+"\n");
		outData .writeChars("Grashof="+String.valueOf(Gr)+"\n");
		outData .writeChars("Reynolds="+String.valueOf(Ref)+"\n");
		outData .writeChars("Graetz="+String.valueOf(Gz)+"\n");
		outData .writeChars("Nusselt="+String.valueOf(Nu)+"\n");
		outData .writeChars("h="+String.valueOf(h)+"\n");
		outData.close();
		fo.close();
		}catch(Exception e){System.out.println("Exception occurred at filenotexist part"+" "+e);}
	 }//file.exists()
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(h));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  Thava=values[0];Vhava=values[1];Dboru=values[2];Tboru=values[3];Lboru=values[4];
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
<P align=center><font face="Comic Sans MS"><STRONG><FONT size=5 color="#ff0080">ISI 
  TRANSFER KATSAYISI</FONT></STRONG></font></P>
<P align="justify"><font face="Comic Sans MS">1 (atm), <%=Thava%> �C ko�ullar�nda bulunan hava <%=Vhava%> (cm/s) ortalama h�z ile <%=Dboru%> (mm) �ap�ndaki boru i�inden ge�irilmektedir. Boru y�zeyi <%=Tboru%>�C'de sabit tutulmaktad�r. E�er boru <%=Lboru%> (m) uzunlu�unda ise, �s� transfer katsay�s�n� h (W/m<sup>2</sup>�C) 
  hesaplay�n�z.</font></P>
<P align="justify"><font face="Comic Sans MS">Not: Yo�unluk de�erini form�lden 
  hesaplay�n�z.</font></P>
<P align="justify"><font face="Comic Sans MS"><strong><u>�PUCU:</u></strong> Hava 
  h�z� �ok d���k !!!</font></P>
<P align="justify">&nbsp;</P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("isitransferkatsayisi.jsp")%>" method="POST">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=425 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=208><font face="Comic Sans MS">Is� transfer katsay�s� (h)=</font></TD>
        <TD width="150" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=49><font face="Comic Sans MS">(W/m<sup>2</sup>�C)</font></TD>
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
        <TD width=208><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=49><font face="Comic Sans MS">puan</font></TD>
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
      double varh=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
  
