<%@ page  language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="1b_HomeDesign";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=40;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[7];
   String results[]=new String[4];
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
  double Tic=0,Tdis=0,hd=0,Ld=0,Lc1=0,Lk=0,Lc2=0,hc2=0,hc1=0,hk=0,OdenecekPara=0;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  boolean b1=false,b2=false,b3=false;
        do{
          Tic=(double)(int)(Math.random ()*5+20);   //Verilen deger
          Tdis=(double)(int)(3-Math.random ()*6);   //Verilen deger
          Ld=(double)(int)(3+Math.random ()*3);     //Verilen deger
          hd=(double)(int)(3+Math.random ()*2);     //Verilen deger
          hk=0.8+Math.random ()*0.5;                //Verilen deger
          hc1=0.5+Math.random ()*0.5;               //Verilen deger
          Lc1=1+Math.random()*1;                    //Verilen deger
          Lc2=Lc1;  hc2=hc1;    Lk=Lc2;
          b1=hd>(hc2+hk+0.5);                       //Duvar�n y�ksekli�inin cam+kap� dan b�y�k olma �art�
          b2=Ld>(2*Lc1+0.5);                        //Duvar�n uzunlu�unun cam+kap� dan b�y�k olma �art�
          b3=b1&&b2;                                //�ki �art beraber sa�lans�n
        }while(!b3);
      //
	  values[0]=Tic;values[1]=Tdis; values[2]=Ld;values[3]=hd;values[4]=hk;values[5]=hc1;values[6]=Lc1;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  Tic=values[0]; Tdis=values[1];Ld=values[2];hd=values[3];hk=values[4];hc1=values[5];Lc1=values[6];
  Lc2=Lc1;  hc2=hc1;    Lk=Lc2;

}
	  double Acam,Akapi,ToplamAlan,Aduvar,Qcam,Qduvar,Qkapi,Qtoplam;
      final double DogalGazFiyati=53.661;  //    TL / kcal
      ToplamAlan=Ld*hd;
      Acam=2*(Lc1*hc1);
      Akapi=Lk*hk;
      Aduvar=ToplamAlan-Acam-Akapi;
      //
      //PENCERELER TEK CAMLI OLURSA
      //
      final double Uduvar=1.428571429,Ubasitcam=4.5,Ukapi=3;
      Qduvar=Uduvar*Aduvar*(Tic-Tdis);
      Qcam=Ubasitcam*Acam*(Tic-Tdis);
      Qkapi=Ukapi*Akapi*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik �s� kayb�
      OdenecekPara=Qtoplam*DogalGazFiyati;
      dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(OdenecekPara));
      //
      //�ki cam arasi  6 mm
      //
      final double U_altimm=2.8;    //�ki cam arasi 6 mm i�in Is� Transfer katsay�s�
      //Qkapi ve Qduvar degismiyor
      Qcam=U_altimm*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik �s� kayb�
      OdenecekPara=Qtoplam*DogalGazFiyati;
      dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(OdenecekPara));
      //
      //�ki cam arasi  12 mm
      //
      final double U_onikimm=2.5;    //�ki cam arasi 12 mm i�in Is� Transfer katsay�s�
      //Qkapi ve Qduvar degismiyor
      Qcam=U_onikimm*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik �s� kayb�
      OdenecekPara=Qtoplam*DogalGazFiyati;
      dbc.updateString("STUDENTNUMBER",username,"SystemAnswer3",TableName,String.valueOf(OdenecekPara));      //
      //Duvarlar 5 cm izolasyon malzemesi (k=0.035 kcal/mh�C) ile kaplan�rsa
      //Pencereler tek caml� olarak d���n�l�yor
      //
      final double kizolasyon=0.035;
      double temp=(5.0/100.0)/kizolasyon;
      double temp2=1/Uduvar;      //dx/k + 1/Uduvar
      double U_izoleduvar=1/(temp+temp2);
      Qduvar=U_izoleduvar*Aduvar*(Tic-Tdis);
      Qcam=Ubasitcam*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik �s� kayb�
      OdenecekPara=Qtoplam*DogalGazFiyati;
      dbc.updateString("STUDENTNUMBER",username,"SystemAnswer4",TableName,String.valueOf(OdenecekPara));
//
dbc.close();

%>

<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-9"></HEAD>
<jsp:include page="questionheader.jsp" flush="true"/>
<BODY background="/images/background.gif">
<script language="JavaScript">
function openWindow()
{
	var x=window.open("homedesignkabulleri.htm","Kabuller","scrollbars=1,toolbar=0, width=750,height=500,resizable=1");
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


<P align=center><STRONG><FONT face="Comic Sans MS" size=5 color="#ff0080">Duvardan Olan Is� Kayb�</FONT></STRONG></P>

<P align=center><img src="/images/Odev2_OdaCizimi.gif" width="533" height="328" align="middle"></P>
<P align=justify style="line height: 200%"><font face="Comic Sans MS">Yukar�da 
  bir odan�n d�� ortama bakan duvar�n�n �ekli g�r�lmektedir. Odan�n s�cakl��� 
  <%=Tic%>�C ve d�� ortam s�cakl��� ise <%=Tdis%>�C'dir. Duvar�n eni <%=Ld%>(m) ve y�ksekli�i ise <%=hd%> (m)'dir. Kap�n�n cams�z k�sm�n�n y�ksekli�i <%=hk%>(m) ve cam�n y�ksekli�i <%=hc1%> (m) ve geni�li�i ise <%=Lc1%>(m)'dir. Bu plandan yararlanarak 1-3 ko�ullar� i�in odadan olan 
  toplam 1 g�nl�k �s� kayb�n� TL cinsinden hesaplay�n�z. </font></P>
<P align=justify style="line height: 200%"><font color="#FF0000" face="Comic Sans MS">1)</font><font face="Comic Sans MS"> Pencereler tek caml�,
  <br>
  <font color="#FF0000">2)</font> Pencereler �ift caml�,
  <br>
  &nbsp;<font color="#0080FF">a.</font> �ki cam aras� 6 mm,
  <br>
  &nbsp;<font color="#0080FF">b.</font> �ki cam aras� 12 mm,
  <br>
  <font color="#FF0000">3)</font> Duvarlar 5 cm'lik izolasyon malzemesi (k=0.035 kcal/mh�C) ile kaplan�rsa,
  <br>
</font>
<P align=justify style="line height: 200%"><font face="Comic Sans MS">�zolasyon olmas� durumu i�in hesaplamada a�a��daki e�itlik kullan�lacakt�r.
  </font>
<P align=center style="line height: 200%"><font face="Comic Sans MS"><img src="/images/isolation_equation.gif" width="158" height="46"></font>
<P align=justify style="line height: 200%"><font color="#FF0000" face="Comic Sans MS">NOT:</font><font face="Comic Sans MS"><a href="javascript:openWindow()">Yap�lacak olan kabuller</a> 
</font>
<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT face="Comic Sans MS">CEVAPLAMA B�L�M�</font></STRONG></P>
<FORM action="<%=response.encodeURL("homedesign.jsp")%>" method="POST">
  <P align=center>
  <TABLE align="center" cellSpacing=1 cellPadding=1 width=390 border=1>
    <TBODY bgColor=aqua>
    <TR>
        <TD width=156><font face="Comic Sans MS">Tek caml�</font></TD>
        <TD width="144" align=middle><INPUT name=useranswer0></TD>
      <TD width=72>(<font face="Comic Sans MS">TL</font>)</TD>
    </TR>
    <TR>
      <TD>�ki cam aras� 6 mm</TD>
      <TD width="144" align=middle><INPUT name=useranswer1>
      </TD>
      <TD>(TL)</TD>
    </TR>
    <TR>
      <TD>�ki cam aras� 12 mm</TD>
      <TD width="144" align=middle><INPUT name=useranswer2>
      </TD>
      <TD><font face="Comic Sans MS">(TL)</font></TD>
    </TR>
    <TR>
      <TD>�<font face="Comic Sans MS">zolasyon var</font></TD>
      <TD width="144" align=middle><INPUT name=useranswer3>
      </TD>
      <TD>(TL)</TD>
    </TR>
    <TR>
      <TD colSpan=3>
      <P 
      align=center><INPUT type=submit value=CEVAPLA name=submit1></P></TD></TR>
  <TR>
      <TD colSpan=3><P></P></TD></TR>
  <TR>
      <TD width=156>Sorunun de�eri=</TD>
      <TD align=middle><%=strvalueforquestion%></TD>
      <TD width=72>puan</TD>
  </TR>
  </TBODY></TABLE>
  </P>
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
      double varOdenecekPara_tekcam=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      double varOdenecekPara_altimm=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
      double varOdenecekPara_onikimm=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer3",TableName)).doubleValue ();
      double varOdenecekPara_izoleduvar=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer4",TableName)).doubleValue ();
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
          dbc.updateString("STUDENTNUMBER",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); 
		  for(int i=0;i<results.length;i++)
		  {
		  	String ColumnToSet="UserAnswer"+String.valueOf(i+1)+"_"+String.valueOf(intnumberofanswer-1);
		  	dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(useranswer[i]));
		  }
          //
          double upperlimit[]=new double[results.length];
          double lowerlimit[]=new double[results.length];
          //
          lowerlimit[0]=varOdenecekPara_tekcam*(1-QuesRange);upperlimit[0]=varOdenecekPara_tekcam*(1+QuesRange);
          lowerlimit[1]=varOdenecekPara_altimm*(1-QuesRange);upperlimit[1]=varOdenecekPara_altimm*(1+QuesRange);
          lowerlimit[2]=varOdenecekPara_onikimm*(1-QuesRange);upperlimit[2]=varOdenecekPara_onikimm*(1+QuesRange);
          lowerlimit[3]=varOdenecekPara_izoleduvar*(1-QuesRange);upperlimit[3]=varOdenecekPara_izoleduvar*(1+QuesRange);
          //
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
    }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
}//GradeIt

}//class Grade

%>
  
