<%@ page language="java" import="jspclass.*,java.io.*,java.util.*" %>
<%
  final String TableOdevAdi="BaglantiBilgileri"; 
  final String TableName="BaglantiBilgileri";
  String username=request.getParameter("username");
  jspclass.DBC db=new jspclass.DBC();
  db.connect ("Thermodynamics","","");    
  String name_surname=db.getSelectedCell(username,"NAME_SURNAME","KisiselBilgiler");
  String email=db.getSelectedCell(username,"EMAIL","KisiselBilgiler");
  String strusername=db.getSelectedCell(username,"STUDENTNUMBER","BaglantiBilgileri");
  String baglantisayisi=db.getSelectedCell(username,"NumberOfLogin",TableOdevAdi);
  if(baglantisayisi.equals("")) baglantisayisi="1";
  int intNumberOfLogin=Integer.valueOf(baglantisayisi).intValue();
  intNumberOfLogin++;
  db.updateString("StudentNumber",username,"NumberOfLogin",TableOdevAdi,String.valueOf(intNumberOfLogin));
  
  if(strusername.equals(""))
  {
  		String SQLQuery="INSERT INTO "+TableName+" (NAME_SURNAME,STUDENTNUMBER) "+" VALUES ('"+name_surname+"',"+"'"+username+"')";
		db.runSQL(SQLQuery);
   }
   
   File file=new File(username+".txt");
   Date d=new Date();
   int numberofIPAddr=0;
   if(file.exists())
   {
   		String str="";
    	try
		{
      		BufferedReader br=new BufferedReader(new FileReader(username+".txt"));
      		while((str=br.readLine())!=null)
			{
				StringTokenizer st=new StringTokenizer(str,"|");
				while(st.hasMoreTokens())
				{
					numberofIPAddr++;			
			    	out.println("Baglandiginiz IP Adresleri : "+st.nextToken()+"<br>");
				}
      		}
       		br.close();
	   		FileOutputStream fo=new FileOutputStream(username+".txt",true); //if true is added than file is opened for append
			String addr=request.getRemoteAddr();
			addr=addr+" "+d;
			addr=addr+"|";
			for(int i=0;i<addr.length();i++)
				fo.write(addr.charAt(i));
			fo.close();
	   
    	}catch(Exception e){System.out.println("Exception occurred at file exist part"+" "+e);}
	}
	
	if(numberofIPAddr>30) file.delete();
	
	if(!file.exists())
	{
		try{
		FileOutputStream fo=new FileOutputStream(username+".txt");
		String addr=request.getRemoteAddr();
		addr=addr+" "+d;
		addr=addr+"|";
		for(int i=0;i<addr.length();i++)
			fo.write(addr.charAt(i));
		fo.close();
		}catch(Exception e){System.out.println("Exception occurred at filenotexist part"+" "+e);}
	}	
  
%> 
<H2>Merhaba <%=username%> </H2>
<BR>
<P> Sisteme kay�tl� ki�isel bilgileriniz a�a��daki gibidir. </P>
<TABLE cellSpacing=1 cellPadding=1 width="40%" border=1>
  <TR>
    <TD><P align=right>Ad Soyad=</P></TD>
    <TD><%=name_surname%></TD>
  </TR>
  <TR>
    <TD><P align=right>E-mail=</P></TD>
    <TD><%=email%></TD>
  </TR>
</TABLE>
<BR>
<BR>

<BR>
<P align=center> Sisteme �u ana kadar <%=baglantisayisi%> kez ba�land�n�z. </P>
<BR>
<BR>
<BR>
