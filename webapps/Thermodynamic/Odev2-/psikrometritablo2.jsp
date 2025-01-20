<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

	
   final String TableName="Odev2_2";
   double NotDusumAraligi[]={1.0,0.95,0.75,0.50,0.25,0};
   final double SorununNotDegeri=15;
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   double values[]=new double[3];
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
  double P,T,Tyas,W,h,Td,RH;
  boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","STUDENTNUMBER",TableName,username);
  if(!IsValuesGiven){
  	double varPwsyas,varPws,varWsyas,varW,varWs,varPw,varRH,varP;
	PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
  	do{
        //
        //D�KKAT: Prosed�rler i�ine varW g�nderilecek ��nk� hepsi Mutlak nemi (kg/kg kh) olarak okuyor
        //
        h=(double)(int)(30+Math.random()*40);
		T=(double)(int)(25+Math.random()*20);
		Tyas=T-(double)(int)(Math.random()*10)-5;
        //
		varPws=pf.Pws(T);
        varW=pf.W(h,T);
        W=varW*1000;          //kg/kg kh ----> gr/kg kh
        varPwsyas=pf.Pwsyas(Tyas);
        varWsyas=pf.Wsyas2(varW,T,Tyas);
        varP=pf.P3(varPwsyas,varWsyas);
        P=varP/1000;
        varPw=pf.Pw2(varP,varW);
        Td=pf.Td(varPw);
        varWs=pf.Ws(varP,varPws);
        varRH=pf.RH(varPw,varPws);
        RH=varRH*100;
    }while(!((P>90)&(P<130)));
		//
       values[0]=h;values[1]=T;values[2]=Tyas;
      //
      //
   for(int i=0;i<values.length;i++){
     String ColumnToSet="Value"+String.valueOf(i+1);
     dbc.updateString("STUDENTNUMBER",username,ColumnToSet,TableName,String.valueOf(values[i]));
   }
   dbc.updateBoolean("STUDENTNUMBER",username,"IsValueTaken",TableName,true);
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer1",TableName,String.valueOf(Td));
   dbc.updateString("STUDENTNUMBER",username,"SystemAnswer2",TableName,String.valueOf(P));
 }else{
   for(int i=0;i<values.length;i++){
     String ColumnToGet="Value"+String.valueOf(i+1);
     String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
     values[i]=Double.valueOf(str).doubleValue();
 }
  dbc.close();
  h=values[0];T=values[1];Tyas=values[2];
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
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><STRONG>Psikrometrik 
  De�erleri Bulma-2</STRONG></font></P>
<P align="justify"><font face="Comic Sans MS">Size verilen Kuru Termometre S�cakl���=<%=T%>�C, Ya� Termometre S�cakl���=<%=Tyas%>�C ve Entalpi=<%=h%> (kJ/kg) de�erlerini kullanarak,</font></P>
<P align="justify"><font face="Comic Sans MS"> A) �iylenme Noktas� S�cakl���n� 
  (�C),<br>
  B) Bas�nc� (kPa) hesaplay�n�z.</font></P>
<P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
<FORM action="<%=response.encodeURL("psikrometritablo2.jsp")%>" method="POST" onSubmit="return checkDigitSeperator(this)">
  <P align=center> 
  <TABLE style="HEIGHT: 61px" cellSpacing=1 cellPadding=1 width=351 
border=1>
    <TBODY bgColor=aqua>
      <TR> 
        <TD width=120><font face="Comic Sans MS">T<sub>d</sub>=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer0>
          </font></TD>
        <TD width=69><font face="Comic Sans MS">�C</font></TD>
      </TR>
	  <TR> 
        <TD width=120><font face="Comic Sans MS">P=</font></TD>
        <TD width="144" align=middle><font face="Comic Sans MS">
          <INPUT name=useranswer1>
          </font></TD>
        <TD width=69><font face="Comic Sans MS">kPa</font></TD>
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
        <TD width=120><font face="Comic Sans MS">Sorunun de�eri=</font></TD>
        <TD align=middle><font face="Comic Sans MS"><%=strvalueforquestion%></font></TD>
        <TD width=69><font face="Comic Sans MS">puan</font></TD>
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
      double varTd=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
	  double varP=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
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
          lowerlimit[0]=varTd*(1-QuesRange);upperlimit[0]=varTd*(1+QuesRange);
		  lowerlimit[1]=varP*(1-QuesRange);upperlimit[1]=varP*(1+QuesRange);
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

class PsikrometrikFonksiyonlar   
{
	//
	//Pws T s�cakl��� kullan�larak elde edilir
	//
	public double Pws(double T){
		 double alfa,a,b,c ,D,Tk,Pws=0 ;
		Tk = T + 273.15;
		if((Tk>=213.15)&&(Tk<=273.15)){
			a = -0.7297593707 * Math.pow(10,-5); b = 0.5397420727 * Math.pow(10,-2); c = 0.2069880620 *Math.pow(10,2); D = -0.6042275128 * 10000;
			alfa = a * Tk*Tk + b * Tk + c + D *(1/Tk) ;
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=273.16)&&(Tk<=322.15)){
			a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; D = -0.6344011577 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=322.16)&&(Tk<=373.15)){
			a = 0.1246732157 *Math.pow(10,-4); b = -0.1915465806 * 0.1; c = 100 * 0.2702388315; D = -0.6340941639 * 10000;
			alfa = a * Tk*Tk+b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=373.16)&&(Tk<=423.15)){
			a = 0.1204507646 *Math.pow(10,-4); b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; D = -0.6316972063 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=423.16)&&(Tk<=473.15)){
			a = 0.1069730183 * Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; D = -0.622078123 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}
		return Pws;		
	}
	//T Pws kullan�larak elde ediliyor
	//
	public double T(double Pws){
		double beta,E, F, G, H, K,Tk,T=0;
		if((Pws>=1)&&(Pws<611)){
			E = 0.1004926534 *0.01; F = 0.1392917633 *0.01; G = 0.2815151574; H = 10 * 0.7311621119; K = 1000 * 0.2125893734;
            beta = Math.log(Pws);
            Tk = E * Math.pow(beta,4)+ F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=611)&&(Pws<12350)){
			E = 0.5031062503 * 0.01; F = -0.882677938 * 0.1; G = 0.1243688446 * 10; H = 0.3388534296 * 10; K = 0.2150077993 * 1000;
            beta =Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=12350)&&(Pws<101420)){
			E = 0.1209512517 *Math.pow(10,-1); F = -0.3545542105; G = 0.5020858479 * 10;H = -0.205030105 * 100; K = 1000 * 0.2718585432;
            beta = Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3)+ G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=101420)&&(Pws<476207)){
			E = 0.1 * 0.2467291016; F = -0.9367112883; G = 100 * 0.1514142334; H = -100 * 0.9882417501; K = 1000 * 0.4995092948;
            beta = Math.log(Pws);
            Tk = E *Math.pow(beta,4)+ F *Math.pow(beta,3)+ G *Math.pow(beta,2) + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=476207)&&(Pws<1555099)){
			E = 0.2748402484 *Math.pow(10,-1); F=-10 * 0.1068661307; G = 100 * 0.1742964962; H = -0.1161208532 * 1000; K = 1000 * 0.547261812;
            beta =Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}
		return T;
	}//T(Pws) metodunun sonu
	//
	//Pw(Td) fonksiyonu yaz�lacak
	//
	public double Pw(double Td){
		double alfa2,a,b,c,D,Tdk,Pw=0;
		Tdk = Td + 273.15;   //Td derece iken Kelvine �evrilir
		if((Tdk>=213.15)&&(Tdk<=273.15)){
			a = -0.7297593707 *Math.pow(10,-5); b = 0.5397420727 *0.01; c = 0.206988062 * 100; D = -0.6042275128 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=273.16)&&(Tdk<=322.15)){
			a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; D = -0.6344011577 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=322.16)&&(Tdk<=373.15)){
			a = 0.1246732157 *Math.pow(10,-4); b= -0.1915465806 * 0.1;c =100 * 0.2702388315; D = -0.6340941639 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=373.16)&&(Tdk<=423.15)){
			a = 0.1204507646 *Math.pow(10,-4);b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; D = -0.6316972063 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=423.16)&&(Tdk<=473.15)){
			a =0.1069730183 *Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; D = -0.622078123 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}
		return Pw;
	}//Pw(Td) methodunun sonu
	//
	//Td(Pw) methodu yaz�lacak
	//
	public double Td(double Pw){
		double beta2,E,F,G,H,K,Tdk2,Td=0;
		if((Pw>=1)&&(Pw<611)){
			E = 0.1004926534 *0.01; F = 0.1392917633 *0.01; G = 0.2815151574; H = 10 * 0.7311621119; K = 1000 * 0.2125893734;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15; //Ya� termometre s�cakl��� Tdk2 ile (K) olarak elde edildi�inden Td ile (�C) �evrilir
		}else if((Pw>=611)&&(Pw<12350)){//Hata ��karsa 610.001'i 611 olarak de�i�tir ve di�erlerinede ayn�s�n� uygula
			E = 0.5031062503 * 0.01; F = -0.882677938 * 0.1; G = 0.1243688446 * 10; H = 0.3388534296 * 10; K = 0.2150077993 * 1000;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=12350)&&(Pw<101420)){
			E = 0.1209512517 *Math.pow(10,-1);F = -0.3545542105;G = 0.5020858479 * 10; H = -0.205030105 * 100; K = 1000 * 0.2718585432;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=101420)&&(Pw<476207)){
			E = 0.1 * 0.2467291016; F = -0.9367112883; G = 100 * 0.1514142334; H = -100 * 0.9882417501; K = 1000 * 0.4995092948;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=476207)&&(Pw<=1555099)){
			E = 0.2748402484 *Math.pow(10,-1); F = -10 * 0.1068661307; G = 100 * 0.1742964962; H = -0.1161208532 * 1000; K = 1000 * 0.547261812;
            beta2 =Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}
		return Td;
	}
	//Td(Pw) methodunun sonu
	public double Pwsyas(double Tyas){
	// Ya� termometre s�cakl���ndaki Bas�nc� bulan metod
		double alfa3,a,b,c,d,TyasK,Pwsyas=0;
			TyasK = Tyas + 273.15;
			if((TyasK>=213.15)&&(TyasK<=273.15)){
				a = -0.7297593707 *Math.pow(10,-5); b = 0.5397420727 *0.01; c = 0.206988062 * 100; d = -0.6042275128 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=273.16)&&(TyasK<=322.15)){
				a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; d = -0.6344011577 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=322.16)&&(TyasK<=373.15)){
				a = 0.1246732157 *Math.pow(10,-4); b = -0.1915465806 * 0.1; c = 100 * 0.2702388315; d = -0.6340941639 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=373.16)&&(TyasK<=423.15)){
				a = 0.1204507646 *Math.pow(10,-4); b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; d = -0.6316972063 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=423.16)&&(TyasK<=473.15)){
				a = 0.1069730183 *Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; d = -0.622078123 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}//if �art�n�n sonu
			return Pwsyas;
	}//Pwsyas(Tyas) methodunun sonu
	//
	double H(double varT,double varW)
  {
  	//h(kJ/kg)  W(kg/kg) T(�C)
		// S�cakl�k ve nem oran� bilinirken entalpiyi hesaplar
		double h=0;
		h=varT+varW*(2501+1.805*varT);
		return h;
	}
  //H(T,W) methodunun sonu
	//
	double T2(double varh,double varW)
  {
		// S�cakl�k �C olarak hesaplan�r h(kJ/kg)
		double varT2=0;
		varT2=(varh-2501*varW)/(1+1.805*varW);
		return varT2;
	}//T2(h,W) metodunun sonu
	//
	double W(double h,double T)
  {
		// Nem (kg/kg) h(kJ/kg) T(�C)
		double varW=0;
		varW=(h-T)/(2501+1.805*T);
		return varW;
	}// W(h,T) metodunun sonu
	//
	double W2(double P,double Pw)
  {
		//W2(kg/kg) P,Pw(Pa)
		double varW2=0;
		varW2=0.62198*Pw/(P-Pw);
		return varW2;
	}// W2(P,Pw) metodunun sonu
	//
	double P(double Pw,double W){
		// P,Pw(Pa),W(kg/kg)
		double varP=0;
		varP=(0.62198*Pw/W)+Pw;
		return varP;
	}//P(Pw,W) metodunun sonu
	//
	double Pw2(double P,double W){
		//P,Pw2 (Pa)  W(kg/kg)
		double varPw2=0;
		varPw2=(P*W)/(W+0.62198);
		return varPw2;
	}// Pw2(P,W) metodunun sonu
	//
	double Ws(double P,double Pws){
		// Ws(kg/kg)   P,Pws(Pa)
		double varWs=0;
		varWs=0.62198*Pws/(P-Pws);
		return varWs;
	}// Ws(P,Pws) metodunun sonu
	//
	double P2(double Pws,double Ws){
		//P2,Pws (Pa)   Ws(kg/kg)
		double varP2=0;
		varP2=(0.62198*Pws/Ws)+Pws;
		return varP2;
	}//P2(Pws,Ws) metodunun sonu
	//
	double Pws2(double P,double Ws){
		// P,Pws2 (Pa)   Ws(kg/kg)
		double varPws2=0;
		varPws2=0.62198*(P-Ws)/(Ws+0.62198);
		return varPws2;
	}//Pws2(P,Ws) metodunun sonu
	//
	double Wsyas(double P,double Pwsyas){
		//Wsyas (kg/kg)   P,Pwsyas(Pa)
		double varWsyas=0;
		varWsyas=0.62198*Pwsyas/(P-Pwsyas);
		return varWsyas;
	}// Wsyas(P,Pwsyas) metodunun sonu
	//
	double P3(double Pwsyas,double Wsyas){
		//P3,Pwsyas(Pa)   Wsyas(kg/kg)
		double varP3=0;
		varP3=0.62198*Pwsyas/Wsyas+Pwsyas;
		return varP3;
	}//P3(Pwsyas,Wsyas) metodunun sonu
	//
	double Pwsyas2(double P,double Wsyas){
		double varPwsyas2=0;
		varPwsyas2=(P*Wsyas)/(Wsyas+0.62198);
		return varPwsyas2;
	}//Pwsyas2(P,Wsyas) metodunun sonu)
	//
	double Wsyas3(double H,double W,double Tyas){
		// Hsyas,H,Hw (kJ/kg)   Tyas (�C)   W (kg/kg)
		double varWsyas3=0;
		double Hsyas=0,Wsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;
		Hw=4.186*Tyas;
		varWsyas3=(Hsyas-H)/Hw+W;
		return varWsyas3;
	}//Wsyas3(H,W,Tyas) metodunun sonu
	//
	double W3(double Wsyas,double H,double Tyas){
		double varW3=0;
		double Hsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;
		Hw=4.186*Tyas;
		varW3=(H-Hsyas)/Hw+Wsyas;
		return varW3;
	}//W3(Wsyas,H,Tyas) metodunun sonu
	//
	double W4(double Wsyas,double T,double Tyas){
		double varW4=0;
		double a=0,b=0,c=0;
		a=(2501-2.381*Tyas)*Wsyas;
		b=T-Tyas;
		c=2501+1.805*T-4.186*Tyas;
		varW4=(a-b)/c;
		return varW4;
	}//W4(Wsyas,T,Tyas) metodunun sonu)
	//
	double Tyas(double Wsyas,double W,double T){
		double varTyas=0;
		double a=0,b=0,c=0;
		a=2501*(Wsyas-W);
		b=T*(1+1.805*W);
		c=2.381*Wsyas-4.186*W-1;
		varTyas=(a-b)/c;
		return varTyas;
	}// Tyas(Wsyas,W,T) metodunun sonu
	//
	double Wsyas2(double W,double T,double Tyas){
		double varWsyas2=0;
		double a=0,b=0,c=0;
		a=(2501+1.805*T-2.381*Tyas)*W;
		b=(T-Tyas);
		c=2501-2.381*Tyas;
		varWsyas2=(a+b)/c;
		return varWsyas2;
	}// Wsyas2(W,T,Tyas) metodunun sonu
	//
	double T3(double Wsyas,double W,double Tyas){
		double varT3=0;
		double a=0,b=0;
		a=(2501-2.381*Tyas)*Wsyas-(2501-4.186*Tyas)*W+Tyas;
		b=1+1.805*W;
		varT3=a/b;
		return varT3;
	}// T3(Wsyas,W,Tyas) metodunun sonu)
	//
	double RH(double Pw,double Pws){
		double varRH=0;
		varRH=Pw/Pws;
		return varRH;
	}// RH(Pw,Pws) metodunun sonu
	//
	double Tyas1521(double P,double Pwsyas,double W,double T){
		double a=0,b=0,c=0,d=0,varTyas1521=0;
		a=0.62198*Pwsyas/(P-Pwsyas);
		b=2501*(a-W)-T*(1+1.805*W);
		c=2.381*a-4.186*W-1;
		varTyas1521=b/c;
		return varTyas1521;
	}//Tyas1521(P,Pwsyas,W,T)
	//
	double Tyas1521(double varP,double varW,double varT){
		// 15 nolu kombinasyon ile 21 nolu kombinasyon birle�tiriliyor
		double varsubTyas, X,Y, AA,DD1,DD2;
		double DF1,DF2,a,b,varsubWsyas,varsubW1;
		double varsubPwsyas;
		double varsubRH,varsubPw,varsubPws;
		int T;
		double iter,fark;
		varsubTyas = varT ;
		iter=1;fark=1;
		varsubPw=Pw2(varP,varW);varsubPws=Pws(varT);varsubRH=varsubPw/varsubPws;
		do{
			varsubTyas = varsubTyas - iter;
			if((varsubRH > 0.991)&&(Math.abs(varsubTyas - varT) < 1)){
				iter = 1; fark = 1;
			}//if
			varsubPwsyas = Pwsyas(varsubTyas);
			varsubWsyas = Wsyas(varP, varsubPwsyas);
			a = (2501 - 2.381 * varsubTyas) * varsubWsyas - (varT - varsubTyas);
			b = 2501 + 1.805 * varT - 4.186 * varsubTyas;
			varsubW1 = a / b;
		if(Math.abs(varW-varsubW1)<=0.0000000001) return varsubTyas;
		}while(!((varsubW1<varW)));
		Y = varsubTyas+fark; X = varsubTyas;
		do{
			AA = (Y - X) / 3;
			DD1 = X + AA; 
			double PwsStar=Pwsyas(DD1);
			varsubWsyas=Wsyas(varP,PwsStar);
			a = (2501 - 2.381 * DD1) * varsubWsyas - (varT - DD1);
			b = 2501 + 1.805 * varT - 4.186 * DD1;
			varsubW1 = a / b;
			DF1 = varsubW1 - varW;
			DD2 = Y - AA;
			PwsStar=Pwsyas(DD2);
			varsubWsyas=Wsyas(varP,PwsStar);
			a = (2501 - 2.381 * DD2) * varsubWsyas - (varT - DD2);
			b = 2501 + 1.805 * varT - 4.186 * DD2;
			varsubW1 = a / b;
			DF2 = varsubW1 - varW;
			if(Math.abs(DF1)<0.0000000001) return DD1;
			if(Math.abs(DF2)<0.0000000001) return DD2;
			if(X==Y) return DD1;
			if (Math.abs(DF1) <Math.abs(DF2)){
				if (DF1<0){X = DD1; Y = DD1 + AA;}
				if (DF1>0){X = DD1 - AA; Y = DD1;}
				if(DF1==0){ return DD2;}
			}else{
				if(DF2<0){X = DD2; Y = DD2 + AA;}
				if(DF2>0){X = DD2 - AA; Y = DD2;}
				if(DF2==0){return DD1;}
			}
		}while(!((Math.abs(DD1 - DD2) < 0.0000000001)||(Math.abs(varsubW1 - varW) < 0.0000000001)));
		return DD1;
	}//Tyas1521
	//
	public double IkinciDerecedenDenklem(double a,double b,double c){
		double delta,x1=0;
		delta=b*b-4*a*c;
		if (delta==0){x1=-b/(2*a);}
		if (delta>0){x1=(-b+Math.pow(delta,0.5))/(2*a);}
		return x1;
	}
	//
	public double LR(double x1,double y1, double x2,double y2, double aranan){
		double m,n,lr;
		m=(y2-y1)/(x2-x1);
		n=y2-m*x2;
		lr=m*aranan+n;
		return lr;
	}//LR
	public double v(double T,double P,double W){
		//Havan�n hacmini m3/kg kuru hava olarak bulur.
		double varx;
		varx=287.055*(T+273.15)*(1+1.6075*W)/P;
		return varx;
	}
		
}//PsikrometrikFonksiyonlar'�n sonu



%>
  
