import java.io.*;
import java.net.*;

public class MHMail{
  private static PrintWriter pw;
  public static void sendEMail(String email_to,String data,PrintWriter varpw){
    try{
      URL url=new URL("mailto:"+email_to);
      pw=varpw;
      URLConnection urlcon=url.openConnection();
      urlcon.setDoOutput(true);
      urlcon.connect();
      PrintWriter out =new PrintWriter(new OutputStreamWriter(urlcon.getOutputStream()));
      out.println("From:"+"MHEmailing system");
      out.println("To: " + email_to);
      out.println("Subject: " + "INFORMATION DATA");
      out.println(data);  // blank line to end the list of headers
      out.println(".");
      out.close();
    }catch(IOException e){pw.println("Errors occured");}
  }
  public static boolean isEmailAdressSeemsValid(String email){
    boolean is_there_any_at_sign=false,is_there_any_dot=false,isOK=false;
    for(int i=0;i<email.length ();i++){
      if(email.charAt (i)=='@') is_there_any_at_sign=true;
      if(email.charAt (i)=='.') is_there_any_dot=true;
      isOK=is_there_any_at_sign&&is_there_any_dot;
    }
    return isOK;
  }

}