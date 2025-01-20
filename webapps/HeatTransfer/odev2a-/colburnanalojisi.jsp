<%@ page language="java" import="jspclass.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="2a_ColburnAnalojisi";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=30;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   HavaOzellikleri hava=new HavaOzellikleri();
   double values[]=new double[5];
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
  double Thava,Tlevha,V,x1,x2;
  double Re1,Re2,Pr,k,Cp,Ro,Viskozite,Tf;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
      do{
          Thava=(double)(int)(Math.random()*5+25);   //Hava s�cakl�g� �C
          V=(double)(int)(Math.random()*4+1);    //Havan�n h�z�  (m/s)
          Tlevha=(double)(int)(Math.random()*20+40);   //Levha s�cakl�g� �C
          x1=(double)(int)(Math.random()*10+10);        //Ilk a cm lik uzakl�k
          x2=x1+(double)(int)(Math.random()*10+10);  //Ilk b cm lik uzakl�k b=a+herhangi bir say�
          //
          Tf=(Tlevha+Thava)/2;
          Viskozite=hava.Viskozite(Tf);
          Ro=hava.Ro(Tf);
          k=hava.k(Tf);
          Pr=hava.Pr(Tf);
          Cp=hava.Cp(Tf);
          Re2=V*Ro*(x2/100)/Viskozite;
          Re1=V*Ro*(x1/100)/Viskozite;
          //
        }while(!(Re2<5*Math.pow(10,5)));    //Laminer kalma �art�na bak
      values[0]=Thava;values[1]=V;values[2]=Tlevha;values[3]=x1;values[4]=x2;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   //
    double Nu,hx,hort,A,q1,q2;
      //
      //�lk x1 cm i�in
      //
      Nu=0.332*Math.pow(Re1,1.0/2.0)*Math.pow(Pr,1.0/3.0);
      hx=Nu*k/(x1/100);
      hort=2*hx;
      A=(x1/100)*1;      //z y�n�nde birim derinlik al�nm��t�r
      q1=hort*A*(Tlevha-Thava);
      //
      //�lk x2 cm i�in
      //
      Nu=0.332*Math.pow(Re2,1.0/2.0)*Math.pow(Pr,1.0/3.0);
      hx=Nu*k/(x2/100);
      hort=2*hx;
      A=(x2/100)*1;      //z y�n�nde birim derinlik al�nm��t�r
      q2=hort*A*(Tlevha-Thava);
      //
      //Ilk x2 cm de COLBURN ANALOJISI
      //
      double St_ort=hort/(Ro*(Cp*1000)*V);
      double Cf_ort=2*St_ort*Math.pow(Pr,2.0/3.0); 	//Surtunme katsayisi
      double Tow_ort=Cf_ort*Ro*V*V/2;     		//Yuzeydeki kayma direnci (N/m2)
      double SurtunmeDirenci=Tow_ort*(x2/100);      	//SurtunmeDirenci(N)=Kayma Direnci * Alan
      SurtunmeDirenci=SurtunmeDirenci*1000;		//mN'a �evriliyor
   //
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(q1));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(q2));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer3",TableName,String.valueOf(SurtunmeDirenci));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  Thava=values[0];V=values[1];Tlevha=values[2];x1=values[3];x2=values[4];
}

%>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254"></HEAD>
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

<FONT face="Comic Sans MS">
<P align=center><STRONG><FONT size=5 color="#ff0080">COLBURN ANALOJ�S�</FONT></STRONG> </P>

<P align=justify style="line height: 200%"> <%=Thava%>�C s�cakl���nda bulunan hava <%=V%>(m/s) h�zla d�z bir levha �zerinden  akmaktad�r.
Levha t�m uzunlu�u boyunca <%=Tlevha%>�C'ye �s�t�lm��t�r. Buna g�re,
<BR>
A)�lk x<SUB>1</SUB>=<%=x1%> ve x<SUB>2</SUB>=<%=x2%> (cm)'lik b�lgelerde transfer edilen �s� miktar�n� (W),
<BR>
B)�lk x<SUB>2</SUB>=<%=x2%> (cm)'de COLBURN ANALOJ�S� kullanarak, ortaya ��kan s�rt�nme direncini (mN) hesaplay�n�z.
</P>

<P align=center><STRONG><FONT face="Comic Sans MS">CEVAPLAMA B�L�M�</font></STRONG></P>
<FORM action="<%=response.encodeURL("colburnanalojisi.jsp")%>" method="POST">
<P align=center>
<TABLE cellSpacing=1 cellPadding=1 width=470 border=1>
  <TBODY bgColor=aqua>
  <TR>
        <TD width=380>x<SUB>1</SUB> cm'lik b�lgedeki <font face="Comic Sans MS">�</font>s<font face="Comic Sans MS">�</font> 
          transferi=</TD>
    <TD align=middle><INPUT name=useranswer0></TD>
    <TD width=40>(W)</TD></TR>
  <TR>
        <TD width=380>x<SUB>2</SUB> cm'lik b�lgedeki <font face="Comic Sans MS">�</font>s<font face="Comic Sans MS">�</font> 
          transferi=</TD>
    <TD align=middle><INPUT name=useranswer1></TD>
    <TD width=40>(W)</TD></TR>
  <TR>
    <TD width=380>S�rt�nme direnci=</TD>
    <TD align=middle><INPUT name=useranswer2></TD>
    <TD width=40>(mN)</TD></TR>
  <TR>
    <TD colSpan=3>
      <P 
      align=center><INPUT type=submit value=CEVAPLA name=submit1></P></TD></TR>
<TR>
    <TD colSpan=3><P></P></TD></TR>
<TR>
    <TD width=200>Sorunun de�eri=</TD>
    <TD align=middle><%=strvalueforquestion%></TD>
    <TD width=40>puan</TD>
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
      double varq1=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      double varq2=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
      double varSurtunmeDirenci=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer3",TableName)).doubleValue ();
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
          lowerlimit[0]=varq1*(1-QuesRange);upperlimit[0]=varq1*(1+QuesRange);
          lowerlimit[1]=varq2*(1-QuesRange);upperlimit[1]=varq2*(1+QuesRange);
          lowerlimit[2]=varSurtunmeDirenci*(1-QuesRange);upperlimit[2]=varSurtunmeDirenci*(1+QuesRange);
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
  
