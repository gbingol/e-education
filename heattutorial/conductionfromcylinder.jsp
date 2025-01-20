<%@ page language="java" import="jspclass.*,jspclass.JFluids.*"%>
<%!
   //Declare instance variables, becareful these are all same for all 
   //threads except newly created classes like results

   final String TableName="CONDUCTIONFROMCYLINDER";
   final double QuesRange=0.01;

   //Declare new instances of class double to avoid multiple threads
   //to access the same data
   String values[]=new String[10];
   String results[]=new String[4];
   String username=new String();
   DBC dbc=new DBC();
%>
<%
  Students student=(Students)session.getValue("studentinfo");
  username=student.username;
%>
<%
  String material="",Dboru="",dx1="",Lboru="",isolationmaterial="";		//values that will be entered by user as an input
  String dx2="",Tic="",hic="",Tdis="",hdis="";					//values that will be entered by user as an input
  String strMaterial="",strisolationMaterial="";
  String r2="",r3="",Aic="",Adis="";		//values that will be entered by user as an answer
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
    Dboru=values[1];
    dx1=values[2];   
	Lboru=values[3];       
	isolationmaterial=values[4];
	dx2=values[5];
	Tic=values[6];
	hic=values[7];
	Tdis=values[8];
	hdis=values[9];
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
		var varTic="",varTdis="";
		var rangeOK=true;
		var strWarn="HATA!!!";
		varTic=parseFloat(this.form1.Tic.value);
		varTdis=parseFloat(this.form1.Tdis.value);
		//	
		if(varTic>varTdis){rangeOK=rangeOK&&true;} else {rangeOK=rangeOK&&false;strWarn=strWarn+"\n"+"�� s�cakl�k>D�� S�cakl�k olmal�d�r.";}
	
		if(rangeOK==false) alert(strWarn); 
		
		return rangeOK;
	}
}

function showMaterial()
{
	windowObject=window.open("","MaterialList","toolbar=0,width=450,height=380,resizeable=1");
	windowObject.document.writeln("<font face=\"comic sans ms\" color=\"#FF0000\">");
	windowObject.document.writeln("MATERYALLER�N �LET�M ISI TRANSFER KATSAYILARI<br>");
	windowObject.document.writeln("</font>");
	windowObject.document.writeln("<font face=\"comic sans ms\" color=\"#000000\">");
	windowObject.document.writeln("Aluminum=237 (W/mK)<br>");
	windowObject.document.writeln("Iron=80.2 (W/mK)<br>");
	windowObject.document.writeln("Soft rubber=0.13 (W/mK)<br>");
	windowObject.document.writeln("Glass fiber=0.043 (W/mK)<br>");
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
		//Re-take butonuna bas�ld�ysa
		dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,false);
		out.print("<A href=\""+response.encodeURL("conductionfromcylinder.jsp")+"\">Devam etmek i�in t�klay�n�z</A>");
		return;
	}
  	if(!IsValuesTaken)
	{
	//If not values taken before read them with request method
	//else do not read values just use them from the database read above
   		material=request.getParameter("material");values[0]=material;
   		Dboru=request.getParameter("Dboru");values[1]=Dboru;
		dx1=request.getParameter("dx1");values[2]=dx1;
		Lboru=request.getParameter("Lboru");values[3]=Lboru;
		isolationmaterial=request.getParameter("isolationmaterial");values[4]=isolationmaterial;
		dx2=request.getParameter("dx2");values[5]=dx2;
		Tic=request.getParameter("Tic");values[6]=Tic;
		hic=request.getParameter("hic");values[7]=hic;
		Tdis=request.getParameter("Tdis");values[8]=Tdis;
		hdis=request.getParameter("hdis");values[9]=hdis;
	}
	
	//Read user answers for this section of the question
	
	r2=request.getParameter("r2");
	r3=request.getParameter("r3");
	Aic=request.getParameter("Aic");
	Adis=request.getParameter("Adis");
	
		
	//Record the userinput to results[] array to be checked within Grade class
	
	results[0]=r2;
	results[1]=r3;
	results[2]=Aic;
	results[3]=Adis;
	
	//Record to database
	if(!IsValuesTaken&&request.getParameter("Submit")!=null)
	{
		for(int i=0;i<values.length;i++)
		{
	    	String ColumnToSet="Value"+String.valueOf(i+1);
    		dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
   		}
   		dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
		out.print("<A href=\""+response.encodeURL("conductionfromcylinder.jsp")+"\">Devam etmek i�in t�klay�n�z</A>");
		return;
	}
	
  }
	if(material.equals("1")) strMaterial="Aluminum";
	if(material.equals("2")) strMaterial="Iron";
	if(material.equals("3")) strMaterial="Soft rubber";
	if(material.equals("4")) strMaterial="Glass fiber";
	if(material.equals("5")) strMaterial="Urethane, rigid foam";
	//
	if(isolationmaterial.equals("1")) strisolationMaterial="Aluminum";
	if(isolationmaterial.equals("2")) strisolationMaterial="Iron";
	if(isolationmaterial.equals("3")) strisolationMaterial="Soft rubber";
	if(isolationmaterial.equals("4")) strisolationMaterial="Glass fiber";
	if(isolationmaterial.equals("5")) strisolationMaterial="Urethane, rigid foam";
%>
<P align=center><font color="#ff0080" size="5" face="Comic Sans MS"><STRONG>Silindirden
      Is� �letimi</STRONG></font></P>
<P align=center><a href="javascript:showExample()">��z�ml� �rnek G�rmek �stiyorum</a></P>
<P align=center><img src="/images/tutorial/conductionfromcyclinder.gif" width="228" height="221"></P>
<FORM name="form1" action="<%=response.encodeURL("conductionfromcylinder.jsp")%>" method="POST" onSubmit="return checkLimits()">
<%
if(!IsValuesTaken)
{//if not values taken give the user an input chance
%>
  <p><font face="Comic Sans MS"> <select name="material">
	  <option value="1">Aluminum
	  <option value="2">Iron
	  <option value="3">Soft rubber
	  <option value="4">Glass fiber
	  <option value="5">Urethane, rigid foam      
	  </select> 
  <a href="javascript:showMaterial()">?</a> yap�lm�� olan �ap� 
  <input type="text" name="Dboru" width="50">
  (cm) ve et kal�nl��� <input type="text" name="dx1" width="50"> 
  (cm),   uzunlu�u 
  <input type="text" name="Lboru" width="50"> (m) olan silindirik bir boru
  izolasyon amac�yla 
  <select name="isolationmaterial">
    <option value="1">Aluminum
    <option value="2">Iron
    <option value="3">Soft rubber
    <option value="4">Glass fiber
    <option value="5">Urethane, rigid foam
  </select>
  <a href="javascript:showMaterial()">?</a>
   malzemeyle <input type="text" name="dx2" width="50"> (cm) kaplanm��t�r. Silindir i�inden ge�en havan�n s�cakl��� <input type="text" name="Tic" width="50">�C
   ve ta��n�m �s� transfer katsay�s� ise <input type="text" name="hic" width="50"> 
   <sub>(30-50)</sub>(W/m<sup>2</sup>K)'dir.
   D�� ortam s�cakl��� 
   <input type="text" name="Tdis" width="50">
   �C ve ta��n�m �s� transfer katsay�s� 
   <input type="text" name="hdis" width="50"> <sub>(5-15)</sub> (W/m<sup>2</sup>�C)'dir.
   Silindirden olan �s� transferini hesaplay�n�z.
      
  </font></p>
  <%
}
if(IsValuesTaken)
{
%>
  <p><font face="Comic Sans MS">
  <a href="javascript:showMaterial()"><%=strMaterial%></a>'dan yap�lm�� olan �ap� <%=Dboru%> (cm) ve et kal�nl��� <%=dx1%> (cm),
  uzunlu�u <%=Lboru%> (m) olan
  silindirik bir boru izolasyon amac�yla <a href="javascript:showMaterial()"><%=strisolationMaterial%></a> malzemeyle <%=dx2%> (cm) kaplanm��t�r. Silindir i�inden ge�en havan�n s�cakl���
   <%=Tic%>�C ve ta��n�m �s� transfer katsay�s� ise <%=hic%> (W/m<sup>2</sup>K)'dir. D�� ortam s�cakl��� <%=Tdis%>�C ve ta��n�m �s� transfer katsay�s� 
   <%=hdis%> (W/m<sup>2</sup>�C)'dir. Silindirden olan �s� transferini hesaplay�n�z. </font></p>
  <%	
}
%>
  <P align=center><font face="Comic Sans MS"><STRONG>CEVAPLAMA B�L�M�</STRONG></font></P>
  <table width="20%" border="0" align="center">
    <tr> 
      <td width="32%"><table style="HEIGHT: 61px" cellspacing=1 cellpadding=1 width=220 
border=1>
          <tbody bgcolor=aqua>
            <tr> 
              <td colspan="4"><div align="center"><font face="Comic Sans MS">Is�
                    Aktar�m�</font></div></td>
            </tr>
            <tr>
              <td>r<sub>2</sub></td>
              <td align=middle><font face="Comic Sans MS">
                <input name="r2" type="text" width="50">
              </font></td>
              <td align=middle>cm</td>
            </tr>
            <tr>
              <td>r<sub>3</sub></td>
              <td align=middle><input type="text" name="r3" width="50"></td>
              <td align=middle>cm</td>
            </tr>
            <tr>
              <td>A<sub>i�</sub></td>
              <td align=middle><input type="text" name="Aic" width="50"></td>
              <td align=middle>m<sup>2</sup></td>
            </tr>
            <tr> 
              <td width=31>A<sub>d��</sub></td>
              <td width="78" align=middle><input type="text" name="Adis" width="50"></td>
              <td width="93" align=middle>m<sup>2</sup></td>
            </tr>
          </tbody>
        </table></td>
    </tr>
  </table>
  <P align=center> 
    <input type="submit" name="Submit" value="Kaydet">
    <input type="submit" name="Submit2" value="Tekrar-Al" onClick="javascript:shouldcheckLimits=false">
	<input type="submit" name="Submit3" value="��zd�m" onClick="javascript:shouldcheckLimits=false">
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
	<P align="center"><a href="<%=response.encodeURL("conductionfromcylinder2.jsp")%>">Devam etmek i�in t�klay�n�z</a> </P>
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
			String material="",isolationmaterial="";
		  	double k1=0,k2=0,r1=0,vark=0;  						//temp variable
			double Dboru=0,dx1=0,Lboru=0,dx2=0,Tic=0,hic=0,Tdis=0,hdis=0;
		  	double varr2=0,varr3=0,varAic=0,varAdis=0;		//user answer variable
		  	double useranswer[]=new double[results.length];
		  	//
	      	material=dbc.getSelectedCell(username,"Value1",TableName);
		  	Dboru=Double.valueOf(dbc.getSelectedCell(username,"Value2",TableName)).doubleValue ();
		  	dx1=Double.valueOf(dbc.getSelectedCell(username,"Value3",TableName)).doubleValue ();
			Lboru=Double.valueOf(dbc.getSelectedCell(username,"Value4",TableName)).doubleValue ();
			isolationmaterial=dbc.getSelectedCell(username,"Value5",TableName);
			dx2=Double.valueOf(dbc.getSelectedCell(username,"Value6",TableName)).doubleValue ();
			Tic=Double.valueOf(dbc.getSelectedCell(username,"Value7",TableName)).doubleValue ();
			hic=Double.valueOf(dbc.getSelectedCell(username,"Value8",TableName)).doubleValue ();
			Tdis=Double.valueOf(dbc.getSelectedCell(username,"Value9",TableName)).doubleValue ();
			hdis=Double.valueOf(dbc.getSelectedCell(username,"Value10",TableName)).doubleValue ();
			
			if(material.equals("1")) vark=237;			//strMaterial="Aluminum";
			if(material.equals("2")) vark=80.2;			//strMaterial="Iron";
			if(material.equals("3")) vark=0.13;			//strMaterial="Soft rubber";
			if(material.equals("4")) vark=0.043;		//strMaterial="Glass fiber";
			if(material.equals("5")) vark=0.026;		//strMaterial="Urethane, rigid foam";
			//
			//Calculate delta and then the roots
			//
			r1=Dboru/2.0;
			varr2=r1+dx1;
			varr3=varr2+dx2;
			varAic=Math.PI*(Dboru/100.0)*Lboru;
			varAdis=Math.PI*(2*varr3/100.0)*Lboru;
			
			//
			dbc.updateString("Number",username,"Value11",TableName,String.valueOf(varr2));
			dbc.updateString("Number",username,"Value12",TableName,String.valueOf(varr3));
			dbc.updateString("Number",username,"Value13",TableName,String.valueOf(varAic));
			dbc.updateString("Number",username,"Value14",TableName,String.valueOf(varAdis));
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
          		lowerlimit[0]=varr2*(1-QuesRange);upperlimit[0]=varr2*(1+QuesRange);
				lowerlimit[1]=varr3*(1-QuesRange);upperlimit[1]=varr3*(1+QuesRange);
				lowerlimit[2]=varAic*(1-QuesRange);upperlimit[2]=varAic*(1+QuesRange);
				lowerlimit[3]=varAdis*(1-QuesRange);upperlimit[3]=varAdis*(1+QuesRange);
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
                  out.println(String.valueOf(useranswer[i])+" cevab�n�z do�ru.");
                 }
				 else
				 {
				   isNextStep=false;		//on ANY false value set isNextStep=false
	               if(isConverted[0]) lowerlimit[i]=-lowerlimit[i];
    	           if(isConverted[1]) upperlimit[i]=-upperlimit[i];
        	       if(isConverted[2]) useranswer[i]=-useranswer[i];
            	   out.println("<BR>");
              	   out.println(String.valueOf(useranswer[i])+" cevab�n�z yanl��.");
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