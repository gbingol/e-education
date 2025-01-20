<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="Odev2_4";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=40;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[7];
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
  double D,V,P1,T1,RH1,dT_serpantin,T2,RH2=100;
  double Q,m_serpantin;
  final double Ra=287.055;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  		double ma=0,h1=0,W1=0,va=0,Pa=0,W2=0,h2=0,P2=0;
		double Pws=0,Pw=0;
		double Q_serpantin=0;
	 	PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
		D=(double)(int)(25+Math.random()*10);				//cm
		V=(double)(int)(100+Math.random()*50);				//m/dakika
		P1=(double)(int)(95+Math.random()*10);     			//kPa
		T1=(double)(int)(30+Math.random()*10);				//�C
		RH1=(double)(int)(55+Math.random()*10);				//%
		dT_serpantin=(double)(int)(5+Math.random()*5);		//�C
		T2=(double)(int)(15+Math.random()*5);				//�C
		//
		//Giri� durumunda bilinenler P1,T1,%RH1 
		//
		Pws=pf.Pws(T1);							//Pa
		Pw=Pws*(RH1/100);						//Pa
		W1=0.62198*Pw/(P1*1000-Pw);				//kg/kg kh
		h1=T1+W1*(2501+1.805*T1);				//kJ/kg
		Pa=P1-Pw/1000;							//kPa
		va=0.287*(T1+273.15)/Pa;				//m3/kg
		ma=(1/va)*(V/60.0)*(Math.PI*Math.pow(D/100,2.0)/4.0);	//kg kuru hava/sn
		//
		//��k�� durumunda bilinenler ise P2,T2,RH2
		//
		P2=P1;									//Bas�n� de�i�miyor
		Pws=pf.Pws(T2);							//Pa
		Pw=Pws*(RH2/100);						//Pa
		W2=0.62198*Pw/(P2*1000-Pw);				//kg/kg kh
		h2=T2+W2*(2501+1.805*T2);				//kJ/kg, Bas�n�lar Pa
		//
		//
		Q=ma*(h2-h1);							//kJ/sn
		Q=Q*60;									//kJ/dak		(Havan�n �s� kayb� oldu�u i�in sonu� -
		Q_serpantin=-Q;							//serpantin havan�n kaybetti�i �s�y� ald��� i�in sonu� +
		m_serpantin=Q_serpantin/(dT_serpantin*4.18);	//kg/dakika
		//							
       values[0]=D;values[1]=V;values[2]=P1;values[3]=T1;values[4]=RH1;values[5]=dT_serpantin;values[6]=T2;
       //
       //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Q));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(m_serpantin));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  D=values[0];V=values[1];P1=values[2];T1=values[3];RH1=values[4];dT_serpantin=values[5];T2=values[6];
}

%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><strong>Serpantin 
  �zerinden Akan Hava</strong></font></P>
<P align="justify"><font face="Comic Sans MS"><%=D%> (cm) �ap�nda bir kanalda <%=V%> (m/dakika) h�zla akan hava, kanal i�indeki so�utma b�l�m�ne <%=P1%> (kPa) bas�n�, <%=T1%> �C s�cakl�k ve y�zde <%=RH1%> ba��l nemde girmektedir. So�utma amac�yla i�inden so�uk su akan bir serpantin kullan�lmaktad�r. Serpantinde akan suyun s�cakl�k art��� <%=dT_serpantin%> �C olmaktad�r. Hava so�utma b�l�m�nden <%=T2%> �C s�cakl�kta, doymu� olarak ��kmaktad�r.</font></P>
<P align="justify"><font face="Comic Sans MS"> A) Havadan birim zamanda olan �s� 
  ge�i�ini (kJ/dakika)<br>
  B) Serpantinde akan suyun debisini (kg/dakika) hesaplay�n�z.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("serpantinuzerindenhava.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=352 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=126><font face="Comic Sans MS">Q=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=64><font face="Comic Sans MS">kJ/dak</font></TD>
      </TR>
	  <TR> 
        <TD width=126><font face="Comic Sans MS">Debi=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer1>
          </font></TD>
        <TD width=64><font face="Comic Sans MS">kg/dak</font></TD>
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
        <TD width=126><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=64><font face="Comic Sans MS">puan</font></TD>
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
      double varQ=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double varm_serpantin=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
          lowerlimit[0]=varQ*(1-QuesRange);upperlimit[0]=varQ*(1+QuesRange);
		  lowerlimit[1]=varm_serpantin*(1-QuesRange);upperlimit[1]=varm_serpantin*(1+QuesRange);
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
  
