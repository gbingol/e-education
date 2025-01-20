import java.io.*;
import java.util.*;
public class SET extends MHCommand{
  //
  private String[] commands;
  public int time;
  private ThreadSocket ts;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  //
  public SET(){};
  public SET(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String stu){
    commands=ts.getCommands();
    DBC dbc=ts.getStudent().getDBC ();
    String str1="",str2="";
    String username=ts.getStudent().getUsername ();
    try{//Ogrenci komut satirina sadece set yazarsa
      if(commands[1].toUpperCase ().equals("?")){
        out.println("SET PW: Sifrenizi degistirmek icin kullanilir");
        out.println("SET EM: E-mail adresi girmek ve/veya degistirmek icin kullanilir");
      }
    }catch(Exception e){
      out.println("SET PW: Sifrenizi degistirmek icin kullanilir");
      out.println("SET EM: E-mail adresi girmek ve/veya degistirmek icin kullanilir");
      return;
    }
    if(commands[1].toUpperCase ().equals("PW")){
      try{
        dataout.writeChars ("Lutfen yeni sifrenizi giriniz :");
        str1=in.readLine ();
        if(str1.equals("")) return;
        dataout.writeChars ("Lutfen yeni sifrenizi tekrar giriniz :");
        str2=in.readLine();
        if(str2.equals("")) return;
      }catch(IOException ioe){out.println ("Sistem hatasi. Lutfen yeniden deneyiniz.");}
      if(str1.equals(str2)){
        dbc.updateString ("Number",username,"PASSWORD","KisiselBilgiler",str2);
        out.println("Sifre degistirme isleminiz basariyla gerceklesti");
        out.println("Yeni sifreniz="+str2);
      }
      else{
        out.println("Lutfen girdiginiz sifrelerin ayni olmasýna dikkat ediniz");
      }
    }//if(PW)

    if(commands[1].toUpperCase ().equals("EM")){
      try{
        dataout.writeChars ("Lutfen e-mail adresinizi giriniz:");
        str1=in.readLine();
        if(str1.equals("")) return;
        dataout.writeChars ("Lutfen yeni e-mail adresinizi tekrar giriniz :");
        str2=in.readLine();
        if(str2.equals("")) return;
      }catch(IOException ioe){out.println ("Sistem hatasi. Lutfen yeniden deneyiniz.");}
      if(str1.equals(str2)){
        dbc.updateString ("Number",username,"EMAIL","KisiselBilgiler",str2);
        out.println("E-mail adresi degistirme/girme isleminiz basariyla gerceklesti");
        out.println("Yeni e-mail adresiniz="+str2);
      }
      else{
        out.println("Lutfen girdiginiz e-mail adreslerinin ayni olmasýna dikkat ediniz");
      }
    }//EM

  dbc=null;
  System.gc (); //Call garbage collector
  }//void execute
}
