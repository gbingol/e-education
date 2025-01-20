<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="SIMPLECONDUCTION";
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   String values[]=new String[4];
   String results[]=new String[1];
   String username=new String();
   DBC dbc=new DBC();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
%>
<%
  String T1="",T2="",dx="",strMaterial="",material="";		//values that will be entered by user as an input
  String Q="";		//values that will be entered by user as an answer
  //
  //
  boolean IsValuesTaken=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);
  if(IsValuesTaken){
  //if values have been taken before read them from database
  	for(int i=0;i<values.length;i++)
	{
    	String ColumnToGet="Value"+String.valueOf(i+1);
	   	String str=dbc.getSelectedCell(username,ColumnToGet,TableName);
    	values[i]=str;
 	}
	material=values[0];
    T1=values[1];
    T2=values[2];          
	dx=values[3];
  }
   
%>
<HTML>
<HEAD>
<meta HTTP-EQUIV="Content-Type" Content="text/html; charset=windows-1254">
</HEAD>
<BODY background="/images/background.gif">
<script language="JavaScript" type="text/JavaScript">
var shouldcheckLimits=true;
var windowObject;
function checkLimits()
{
	if(shouldcheckLimits)
	{
		var vardx="",varT1="",varT2="";
		var rangeOK=true;
		var strWarn="HATA!!!";
		vardx=parseFloat(this.form1.dx.value);
		varT1=parseFloat(this.form1.T1.value);
		varT2=parseFloat(this.form1.T2.value);
		//
		if(vardx>0){rangeOK=rangeOK&&true;} else {rangeOK=rangeOK&&false;strWarn=strWarn+"\n"+"Materyalin uzunluðu sýfýrdan büyük olmalýdýr.";}
		if(varT1>varT2){rangeOK=rangeOK&&true;} else {rangeOK=rangeOK&&false;strWarn=strWarn+"\n"+"T1>T2 olmalýdýr.";}
	
		if(rangeOK==false) alert(strWarn); 
		
		return rangeOK;
	}
}

function showMaterial()
{
	windowObject=window.open("","MaterialList","toolbar=0,width=450,height=380,resizeable=1");
	windowObject.document.writeln("<font face=\"comic sans ms\" color=\"#FF0000\">");
	windowObject.document.writeln("MATERYALLERÝN ÝLETÝM ISI TRANSFER KATSAYILARI<br>");
	windowObject.document.writeln("</font>");
	windowObject.document.writeln("<font face=\"comic sans ms\" color=\"#000000\">");
	windowObject.document.writeln("Silver=429 (W/mK)<br>");
	windowObject.document.writeln("Copper=401 (W/mK)<br>");
	windowObject.document.writeln("Gold=317 (W/mK)<br>");
	windowObject.document.writeln("Aluminum=237 (W/mK)<br>");
	windowObject.document.writeln("Iron=80.2 (W/mK)<br>");
	windowObject.document.writeln("Glass=1.4 (W/mK)<br>");
	windowObject.document.writeln("Brick=0.72 (W/mK)<br>");
	windowObject.document.writeln("Water (liquid)=0.72 (W/mK)<br>");
	windowObject.document.writeln("Wood (oak)=0.17 (W/mK)<br>");
	windowObject.document.writeln("Soft rubber=0.13 (W/mK)<br>");
	windowObject.document.writeln("Glass fiber=0.043 (W/mK)<br>");
	windowObject.document.writeln("Air (gas)=0.026 (W/mK)<br>");
	windowObject.document.writeln("Urethane, rigid foam=0.026 (W/mK)<br>");
	windowObject.document.writeln("</font>");
}

function showExample()
{
	var windowObject;
	windowObject=window.open("simpleconduction.pdf","Solution","toolbar=0,width=850,height=700,resizeable=1");
}

</script>
<%
  if(request.getMethod().equals("POST"))
  {
  //Delete values from the database
  	if(IsValuesTaken&&request.getParameter("Submit2")!=null)
	{
		//Re-take butonuna basýldýysa
		dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,false);
		out.print("<A href=\""+response.encodeURL("simpleconduction.jsp")+"\"> Click Here to Continue</A>");
		return;
	}
  	if(!IsValuesTaken)
	{
	//If not values taken before read them with request method
	//else do not read values just use them from the database read above
   		material=request.getParameter("material");values[0]=material;
   		T1=request.getParameter("T1");values[1]=T1;
		T2=request.getParameter("T2");values[2]=T2;
		dx=request.getParameter("dx");values[3]=dx;
	}
	
	//Read user answers for this section of the question
	
	Q=request.getParameter("Q");
	
		
	//Record the userinput to results[] array to be checked within Grade class
	
	results[0]=Q;
	
	//Record to database
	if(!IsValuesTaken&&request.getParameter("Submit")!=null)
	{
		for(int i=0;i<values.length;i++)
		{
	    	String ColumnToSet="Value"+String.valueOf(i+1);
    		dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
   		}
   		dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
		out.print("<A href=\""+response.encodeURL("simpleconduction.jsp")+"\"> Click Here to Continue</A>");
		return;
	}
	
  }
  
	if(material.equals("1")) strMaterial="Silver";
	if(material.equals("2")) strMaterial="Copper";
	if(material.equals("3")) strMaterial="Gold";
	if(material.equals("4")) strMaterial="Aluminum";
	if(material.equals("5")) strMaterial="Iron";
	if(material.equals("7")) strMaterial="Glass";
	if(material.equals("8")) strMaterial="Brick";
	if(material.equals("9")) strMaterial="Water (liquid)";
	if(material.equals("11")) strMaterial="Wood (oak)";
	if(material.equals("13")) strMaterial="Soft rubber";
	if(material.equals("15")) strMaterial="Glass fiber";
	if(material.equals("16")) strMaterial="Air (gas)";
	if(material.equals("17")) strMaterial="Urethane, rigid foam";
%>
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><STRONG>Duvardan
      Isý Ýletimi</STRONG></font></P>
<P align=center><a href="javascript:showExample()">Çözümlü Örnek Görmek Ýstiyorum</a></P>
<P align=center><img src="/images/tutorial/simple_conduction.GIF" width="182" height="221"></P>
<FORM name="form1" action="<%=response.encodeURL("simpleconduction.jsp")%>" method="POST" onSubmit="return checkLimits()">
<%
if(!IsValuesTaken)
{//if not values taken give the user an input chance
%>
  <p><font face="Comic Sans MS"> <select name="material">
	  <option value="1" selected>Silver
	  <option value="2">Copper
	  <option value="3">Gold
	  <option value="4">Aluminum
	  <option value="5">Iron
	  <option value="7">Glass
	  <option value="8">Brick
	  <option value="9">Water (liquid)
	  <option value="11">Wood (oak)
	  <option value="13">Soft rubber
	  <option value="15">Glass fiber
	  <option value="16">Air (gas)
	  <option value="17">Urethane, rigid foam      
	  </select> <a href="javascript:showMaterial()">?</a> yapýlmýþ olan duvarýn bir yüzü T<sub>1</sub>=
      <input name="T1" type="text" id="T1" width="50px">°C, diðer yüzü ise T<sub>2</sub>=
      <input name="T2" type="text" id="T2" width="50">
      °C'dir. Duvarýn kalýnlýðý dx=<input type="text" name="dx" width="50"> (cm) ise duvardan olan ýsý aktarýmýný bulunuz.
  </font></p>
  <%
}
if(IsValuesTaken)
{
%>
  <p><font face="Comic Sans MS"><a href="javascript:showMaterial()"><%=strMaterial%></a>'dan yapýlmýþ olan duvarýn bir yüzü T<sub>1</sub>=<%=T1%> °C'de diðer yüzü ise T<sub>2</sub>=<%=T2%> °C'dir. 
  Duvarýn kalýnlýðý dx=<%=dx%> (cm) ise duvardan olan ýsý aktarýmýný hesaplayýnýz. </font></p>
  <%	
}
%>
  <P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA BÖLÜMÜ</STRONG></font></P>
  <table width="20%" border="0" align="center">
    <tr> 
      <td width="32%"><table style="HEIGHT: 61px" cellspacing=1 cellpadding=1 width=220 
border=1>
          <tbody bgcolor=aqua>
            <tr> 
              <td colspan="4"><div align="center"><font face="Comic Sans MS">Isý
                    Aktarýmý</font></div></td>
            </tr>
            <tr> 
              <td width=31><font face="Comic Sans MS">Q </font></td>
              <td width="78" align=middle><font face="Comic Sans MS"> 
                <input name="Q" type="text" id="h1" width="70">
                </font></td>
              <td width="93" align=middle><font face="Comic Sans MS">( Watt )</font></td>
            </tr>
          </tbody>
        </table></td>
    </tr>
  </table>
  <P align=center> 
    <input type="submit" name="Submit" value="Kaydet">
    <input type="submit" name="Submit2" value="Tekrar-Al" onClick="javascript:shouldcheckLimits=false">
	<input type="submit" name="Submit3" value="Çözdüm" onClick="javascript:shouldcheckLimits=false">
  </P>  
</FORM>
<font face="Comic Sans MS">
<%
	boolean NextStep=false;
  if(request.getMethod().equals("POST"))
  {
  	if(request.getParameter("Submit3")!=null)
	{
		Grade gr=new Grade();
		gr.material=material;
		gr.GradeIt(out,results);
		NextStep=gr.isNextStep;
	}
  }
%>
<BR>
<jsp:include page="questionlinks.jsp" flush="true"/>
<%
   if(NextStep)
   {
%>
	<P align="center"><font size="+2" color="#FF0000">Soruyu baþarýlý bir þekilde çözdünüz.</font></P>
<%
   }//end of if(nextStep)
%>
</font> 
</BODY>
</HTML>
<%!

	class Grade
	{
		boolean isNextStep=true;	//initialize it to true, on any false value set it to false
		String material="-1";
		public void GradeIt(JspWriter out,String[] results)
		{
		  	double vark=0,varT1=0,varT2=0,vardx=0;  //user input variable
		  	double varQ=0;		//user answer variable
		  	double useranswer[]=new double[results.length];
		  	//
	      	material=dbc.getSelectedCell(username,"Value1",TableName);
		  	varT1=Double.valueOf(dbc.getSelectedCell(username,"Value2",TableName)).doubleValue ();
		  	varT2=Double.valueOf(dbc.getSelectedCell(username,"Value3",TableName)).doubleValue ();
			vardx=Double.valueOf(dbc.getSelectedCell(username,"Value4",TableName)).doubleValue ();
			
			if(material.equals("1")) vark=429;			//strMaterial="Silver";
			if(material.equals("2")) vark=401;			//strMaterial="Copper";
			if(material.equals("3")) vark=317;			//strMaterial="Gold";
			if(material.equals("4")) vark=237;			//strMaterial="Aluminum";
			if(material.equals("5")) vark=80.2;			//strMaterial="Iron";
			if(material.equals("7")) vark=1.4;			//strMaterial="Glass";
			if(material.equals("8")) vark=0.72;			//strMaterial="Brick";
			if(material.equals("9")) vark=0.613;		//strMaterial="Water (liquid)";
			if(material.equals("11")) vark=0.17;		//strMaterial="Wood (oak)";
			if(material.equals("13")) vark=0.13;		//strMaterial="Soft rubber";
			if(material.equals("15")) vark=0.043;		//strMaterial="Glass fiber";
			if(material.equals("16")) vark=0.026;		//strMaterial="Air (gas)";
			if(material.equals("17")) vark=0.026;		//strMaterial="Urethane, rigid foam";
			//
			//Calculate delta and then the roots
			//
			varQ=vark*(varT1-varT2)/(vardx/100);
			//
			dbc.updateString("Number",username,"Value5",TableName,String.valueOf(varQ));
			//
			
      		for(int j=0;j<results.length;j++)
      		{
        		useranswer[j]=Double.valueOf(results[j]).doubleValue();
      		}
      		try{
         		out.println("<P align=center>");
            	out.println("<FONT color=blue>");
            	//
           		double upperlimit[]=new double[results.length];
           		double lowerlimit[]=new double[results.length];
           		//
          		lowerlimit[0]=varQ*(1-QuesRange);upperlimit[0]=varQ*(1+QuesRange);
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
            	 if(useranswer[i]<=upperlimit[i]&&useranswer[i]>=lowerlimit[i])
				 {
              	  if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
              	  if(isConverted[1]) upperlimit[i]=-upperlimit[i];
	              if(isConverted[2]) useranswer[i]=-useranswer[i];
                  out.println("<BR>");
                  out.println(String.valueOf(useranswer[i])+" cevabýnýz doðru.");
                 }
				 else
				 {
				   isNextStep=false;		//on ANY false value set isNextStep=false
	               if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
    	           if(isConverted[1]) upperlimit[i]=-upperlimit[i];
        	       if(isConverted[2]) useranswer[i]=-useranswer[i];
            	   out.println("<BR>");
              	   out.println(String.valueOf(useranswer[i])+" cevabýnýz yanlýþ.");
				   out.println("The answer should be "+String.valueOf((upperlimit[i]+lowerlimit[i])/2.0));
                 }//if-else
                }//for
             //
            out.println("<BR>");
            out.println("</FONT>");
            out.println("</P>");
            //
         }catch(Exception e){System.out.println("Error occured in "+TableName+" "+e);}
      }//GradeIt
	  
}//end of class

%>