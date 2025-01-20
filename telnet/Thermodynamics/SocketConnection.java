import java.net.*;
import java.io.*;
import java.util.Date ;
import java.sql.*;
public class SocketConnection implements LogOffListener,Timed{
  ServerSocket s;
  //Timer timer;
  public static void main(String arg[]){
    SocketConnection st=new SocketConnection();
    SystemSettings.readFile("SystemSettings.txt");  //Read systemsettings
    st.runIt();
  }
  public void runIt(){
  try{
      int PORT_NUMBER=Integer.valueOf(SystemSettings.getCommandParameter ("PORT_NUMBER").trim()).intValue ();
      s=new ServerSocket(PORT_NUMBER);
      //Start a timer
      //timer=new Timer(this,2*60*60*1000);timer.start(); //Check if Pass.txt is updated for 2 hours
      for(;;){
          Socket incoming=s.accept();
          new ThreadSocket(incoming,this).start();
      }
  }catch(Exception e){System.out.println(e);}
  }
  //
  public void timeElapsed(){
  //Check if anyone has established a connection with server
  //If so do not read Pass.txt
  //else read Pass.txt
    //if(ThreadManager.getNumberofConnections()==0) MHFileReader.readFile("Pass.txt");
  }
  //
  public void ShutDown(){
    //Check if more than one user connected or not
    //if more than one user connected do not shut down the server
    //else shut down the server for further purposes
    if(ThreadManager.getNumberofConnections()<=1){System.exit(0);}
  }
}
//
//
class ThreadSocket extends Thread{
  private Socket s;
  public LogOffListener logofflistener;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  public Students st; private int stindex;
  Questions ques[]=new Questions[3];private int quesindex;
  public DBC dbc=new DBC();
  private String username,password;
  private String uname[],pword[];
  private String CommandLine;
  private final String TableOdevAdi="BaglantiBilgileri";   //Baðlantý bilgileri adlý tablodan veriler okunur
  //
  public ThreadSocket(){};
  public ThreadSocket(Socket i,MHEventListener mhlistener){
    s=i;
    logofflistener=(LogOffListener)mhlistener;
  }
  //
  boolean done=false;
	public void run(){
    uname=dbc.getSelectedColumn ("Number","KisiselBilgiler");
    pword=dbc.getSelectedColumn ("Password","KisiselBilgiler");
    ques[0]=new Odev3b_1();
    ques[1]=new Odev3b_2();
    ques[2]=new Odev3b_3();
    end_it1:
		try{
      end_it:
      while(!done){
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			out=new PrintWriter(s.getOutputStream(),true);
      dataout=new DataOutputStream(s.getOutputStream());
      String LECTURE_NAME=SystemSettings.getCommandParameter ("LECTURE_NAME").trim ();
      String GREETING_MESSAGE=SystemSettings.getCommandParameter ("GREETING_MESSAGE").trim ();
      String NUMBER_OF_QUESTION_ANNOUNCEMENT=SystemSettings.getCommandParameter ("NUMBER_OF_QUESTION_ANNOUNCEMENT").trim ();
      out.println("****************************************************************");
      out.println("*****************"+LECTURE_NAME+ " OTOMASYON PROGRAMI****************");
			out.println("MacroHard Server Station'a Hoþgeldiniz. Çýkmak için EXIT yazýnýz.");
      out.println(GREETING_MESSAGE);
      out.println("***********Bu odevinizde toplam "+NUMBER_OF_QUESTION_ANNOUNCEMENT+  " soru vardir.************  ");
      out.println("****************************************************************");
      //
      dataout.writeChars("Kullanici Isminizi Giriniz: ");
      username=in.readLine().trim();
      if(!username.equals("")){
        try{
        for(int i=0;i<uname.length;i++){
          if(username.equals(uname[i])){stindex=i;break;}
          else stindex=-1;
        }//for
      }catch(Exception e){stindex=-1;}
      }
      if(username.equals("")) stindex=-1;
      if(stindex<0) {out.println("UZGUNUM. Sizi tanýyamadým.");done=true;break end_it;}
      //

      if(ThreadManager.isStudentConnected(uname[stindex])){out.println("MacroHard Server Station'la geçerli bir baðlantýnýz zaten var."); done=true;break end_it;}
      //
      dataout.writeChars("Sifrenizi Giriniz         : ");
      password=in.readLine().trim();
      if(!(password.equals(pword[stindex]))) {out.println("UZGUNUM. Sifreniz hatali.");done=true;ThreadManager.removeStudent(st);break end_it;}
      //

      st=new Students(uname[stindex],pword[stindex]);
      ThreadManager.addStudent(this,st);

      //
      for(int i=0;i<ques.length;i++){
        if(ques[i]!=null) ques[i].setUsername(st);
      }
      //
      String strNumberOfLogin=dbc.getSelectedCell (uname[stindex],"NumberOfLogin",TableOdevAdi);
      if(strNumberOfLogin.equals("")){
        strNumberOfLogin="1";
        dbc.updateString("Number",uname[stindex],"NumberOfLogin",TableOdevAdi,String.valueOf(1));
        out.println("MacroHard Servis Istasyonu'yla ilk baglantiniz. Eger degilse, sistemden cikiniz ve sistem yoneticisi ile temas kurunuz.");
      }
      else{
        int intNumberOfLogin=Integer.valueOf(strNumberOfLogin).intValue();
        intNumberOfLogin++;
        dbc.updateString("Number",uname[stindex],"NumberOfLogin",TableOdevAdi,String.valueOf(intNumberOfLogin));
      }
      ViewLastThreeLogin(out);
      //
      //This PrintMenu(out) is used when the user first logs on to MH Server Station
      PrintMenu(out);
			while(!done){ //inner
        dataout.writeChars(st.getUsername()+" >");
        String str=in.readLine().trim();
        try{
          doCommand(str);
          out.println("");  //Print out an empty line
        }catch(NumberFormatException e){out.println("<---------DIKKAT: Komutunuz gecersiz bir islem yuruttu.---------->");}
        catch(NullPointerException e){out.println("<-------DIKKAT: Lutfen soru numarisini dogru giriniz.------->");}
        catch(Exception e){out.println("<-------DIKKAT: Tanýmlanmamýþ hata oluþtu------->");}
				if(str.trim().toUpperCase().equals("EXIT")){done=true;ThreadManager.removeStudent(st);st.closeConnectionWithDatabase();}
			}//while(!done) //inner
      }//while(!done)
      dbc=null;
			s.close();
      this.stop();
		}//try
    catch(SocketException e){
      if(username!=null){
        st.closeConnectionWithDatabase ();
        ThreadManager.removeStudent(st);
        ThreadManager.studentconnected=false;
        this.stop ();
      }
    }
    catch(NullPointerException npe){
      st.closeConnectionWithDatabase ();
      ThreadManager.removeStudent(st);
      ThreadManager.studentconnected=false;
      this.stop ();
    }
    catch(Exception e){
    st.closeConnectionWithDatabase ();
    System.out.println(e);
    ThreadManager.removeStudent(st);
    ThreadManager.studentconnected=false;
    this.stop ();
    }
		}
    //
    //
    private void PrintMenu(PrintWriter o){
      o.println("");
      o.println("<*************************************************************>");
      o.println("<***************PROGRAMDA KULLANILACAK KOMUTLAR***************>");
      o.println("VQ No     :Soruyu Goruntule Soru No");
      o.println("           Birinci soruyu görmek icin: VQ 1 yazýlacaktýr.");
      o.println("GV No     :Deger ver Soru No");
      o.println("           Birinci sorunun deðerlerini almak icin:GV 1 yazýlacaktýr.");
      o.println("GR No     :Sorumu notlandir Soru No");
      o.println("           Birinci sorunun cevabinin kontrol edilmesi icin:GR 1 yazýlacaktýr.");
      o.println("VTG       :Þu ana kadar olan toplam notumu görüntüle");
      o.println("LS        :Þu anda Macrohard Server'a baðlý kiþileri görüntüle");
      o.println("SET ?     :Sifre ve e-mail degistirmek icin kullanilir");
      o.println("MHREMOVE #:# ile belirlenen kullanýcý (#=kullanici adi) sistemdeki ");
      o.println("          :askidan kurtarilir.");
      o.println("?         :Herhangi bir anda menüyü görüntülemek icin kullanilir");
      o.println("<*************************************************************>");
      o.println("");
    }
    private void doCommand(String command) throws NumberFormatException,NullPointerException{
    int rowindex=0;
    MHCommand mhcommand=null;   //Initiliaze MHCommand
    CommandLine=command.trim ();
    String commands[]=MHCommand.parseCommand(command);
    int numberofcommands=commands.length;
    for(int i=0;i<commands.length;i++)commands[i]=commands[i].trim ();
    commands[0]=commands[0].toUpperCase ();
    //
    //
    if(commands[0].equals("VTG")) mhcommand=new VTG(in,out,dataout,this);
    if(commands[0].equals("MHREMOVE")) mhcommand=new MHREMOVE(in,out,dataout,this);
    if(commands[0].equals("LS")) mhcommand=new LS(in,out,dataout,this);
    if(commands[0].equals("SET")) mhcommand=new SET(in,out,dataout,this);
    if(commands[0].equals("INFORMATION")) mhcommand=new INFORMATION(in,out,dataout,this);
    //
    //
      if((!(commands[0].equals("EXIT")))&&(!commands[0].equals(""))){
          String studenttoremove="";
          //
          //Eger iki tane komut girmediyse Exception oluþuyor. Bu nedenle degisken[1] dizisi hata veriyor
          //
          if(numberofcommands==2){
              try{
                quesindex=Integer.valueOf(commands[1]).intValue ();
                if(quesindex<0) {throw new NullPointerException();}
              }catch(NumberFormatException nfe){}
                studenttoremove=commands[1];
                rowindex=quesindex;
                //
                //User enters as Question 1 and Question 2 but ques[0] refers to Question 1 and ques[1] refers to
                //Question 2 so in order to execute correct question ques[quesindex-1]
                //
                if(commands[0].equals("VQ")) {ques[quesindex-1].print(out);return ;}
                if(commands[0].equals("GV")) {ques[quesindex-1].printRandomValues(out);return;}
                if(commands[0].equals("GR")) {ques[quesindex-1].GradeIt(dataout,in,out);return;}
                if(commands[0].equals("MHREMOVE")) {mhcommand.execute(studenttoremove);return;}
          }
          //
          //
          if(commands[0].toUpperCase().trim().equals("?")) PrintMenu(out);
          if(commands[0].toUpperCase().trim().equals("VTG")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("LS")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("SET")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("INFORMATION")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("MHSHUTDOWN")) {logofflistener.ShutDown();}
      }
    }
    public Students getStudent(){
      return st;
    }
    private void ViewLastThreeLogin(PrintWriter pw){
      //Views last three login time of the user
      Date date=new Date();
      pw.println("");
      if(!dbc.getSelectedCell(uname[stindex],"SonBaglanti3",TableOdevAdi).equals("")){
        pw.println("Sondan 3. sisteme giriþ tarihiniz:"+dbc.getSelectedCell(uname[stindex],"SonBaglanti3",TableOdevAdi));
        dbc.updateString("Number",uname[stindex],"SonBaglanti3",TableOdevAdi,dbc.getSelectedCell(uname[stindex],"SonBaglanti2",TableOdevAdi));
      }
      if(dbc.getSelectedCell(uname[stindex],"SonBaglanti3",TableOdevAdi).equals("")) dbc.updateString("Number",uname[stindex],"SonBaglanti3",TableOdevAdi,dbc.getSelectedCell(uname[stindex],"SonBaglanti2",TableOdevAdi));
      //
      if(!dbc.getSelectedCell(uname[stindex],"SonBaglanti2",TableOdevAdi).equals("")){
        pw.println("Sondan 2. sisteme giriþ tarihiniz:"+dbc.getSelectedCell(uname[stindex],"SonBaglanti2",TableOdevAdi));
        dbc.updateString("Number",uname[stindex],"SonBaglanti2",TableOdevAdi,dbc.getSelectedCell(uname[stindex],"SonBaglanti",TableOdevAdi));
      }
      if(dbc.getSelectedCell(uname[stindex],"SonBaglanti2",TableOdevAdi).equals("")) dbc.updateString("Number",uname[stindex],"SonBaglanti2",TableOdevAdi,dbc.getSelectedCell(uname[stindex],"SonBaglanti",TableOdevAdi));
      //
      if(!dbc.getSelectedCell(uname[stindex],"SonBaglanti",TableOdevAdi).equals("")){
        pw.println("Son giriþ tarihiniz              :"+dbc.getSelectedCell(uname[stindex],"SonBaglanti",TableOdevAdi));
        dbc.updateString("Number",uname[stindex],"SonBaglanti",TableOdevAdi,date.toString());
      }
      if(dbc.getSelectedCell(uname[stindex],"SonBaglanti",TableOdevAdi).equals("")) dbc.updateString("Number",uname[stindex],"SonBaglanti",TableOdevAdi,date.toString ());
      //
      String baglantisayisi=dbc.getSelectedCell(uname[stindex],"NumberOfLogin",TableOdevAdi);
      pw.println("");
      pw.println("******** Su ana kadar sisteme "+baglantisayisi+" kere baglandiniz ********");
    }
    //
    public DBC getDBC(){
      return this.dbc;
    }

    public String[] getCommands(){
      return MHCommand.parseCommand(CommandLine);
    }

    public Questions[] getQuestions(){
      return this.ques;
    }
	}
//




