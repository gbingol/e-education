import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;

public class SocketConduction implements LogOffListener, Timed {
	ServerSocket s;
	Timer timer;

	public static void main(String arg[]) 
	{
		SocketConduction st = new SocketConduction();
		MHFileReader.readFile("Pass.txt"); //Read at the beginning in order not to wait half an hour
		SystemSettings.readFile("SystemSettings.txt"); //Read systemsettings
		st.runIt();
	}

	public void runIt() {
		try {
			int PORT_NUMBER = Integer.valueOf(SystemSettings.getCommandParameter("PORT_NUMBER").trim())
					.intValue();
			s = new ServerSocket(PORT_NUMBER);
			//Start a timer
			timer = new Timer(this, 24 * 60 * 60 * 1000);
			timer.start(); //Check if Pass.txt is updated for daily
			for (;;) {
				Socket incoming = s.accept();
				new ThreadSocket(incoming, this).start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//
	public void timeElapsed() {
		MHFileReader.readFile("Pass.txt");
	}

	public void ShutDown() {
		//Check if more than one user connected or not
		//if more than one user connected do not shut down the server
		//else shut down the server for further purposes
		if (ThreadManager.getNumberofConnections() <= 1) {
			System.exit(0);
		}
	}
}

//
//
class ThreadSocket extends Thread{
  private Socket s;
  public LogOffListener logofflistener;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  public Students st[]; private int stindex;
  Questions ques[]=new Questions[5];private int quesindex;
  public MHTable mhtable;
  private String username,password;
  //
  public ThreadSocket(){};
  public ThreadSocket(Socket i,MHEventListener mhlistener){
    s=i;
    logofflistener=(LogOffListener)mhlistener;
  }
  //
  boolean done=false;
	public void run(){
    String uname[]=MHFileReader.getUsername ();
    String pword[]=MHFileReader.getPassword ();
    int numberofstudents=MHFileReader.getNumberOfStudents ();
    st=new Students[numberofstudents];
    for(int i=0;i<numberofstudents;i++) st[i]=new Students(uname[i],pword[i]);
    //
    ques[0]=new Conduction1();
    ques[1]=new Conduction2();
    ques[2]=new Conduction3();
    ques[3]=new Conduction4();
    ques[4]=new Conduction5();
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
			out.println("MacroHard Server Station'a Ho�geldiniz. ��kmak i�in EXIT yaz�n�z.");
      out.println(GREETING_MESSAGE);
      out.println("***********Bu odevinizde toplam "+NUMBER_OF_QUESTION_ANNOUNCEMENT+  " soru vardir.************  ");
      out.println("****************************************************************");
      //
      dataout.writeChars("Kullanici Isminizi Giriniz: ");
      username=in.readLine().trim();
      if(!username.equals("")){
        try{
        for(int i=0;i<st.length;i++){
          if(username.equals(st[i].getUsername())){ stindex=i;break;}
          else stindex=-1;
        }//for
      }catch(Exception e){stindex=-1;}
      }
      if(username.equals("")) stindex=-1;
      if(stindex<0) {out.println("UZGUNUM. Sizi tan�yamad�m.");done=true;break end_it;}
      //
      if(!(stindex<0)) ThreadManager.addStudent(this,st[stindex]);
      if(ThreadManager.studentconnected){out.println("MacroHard Server Station'la ge�erli bir ba�lant�n�z zaten var."); done=true;break end_it;}
      //
      dataout.writeChars("Sifrenizi Giriniz         : ");
      password=in.readLine().trim();
      if(!(password.equals(st[stindex].getPassword()))) {out.println("UZGUNUM. Sifreniz hatali.");done=true;ThreadManager.removeStudent(st[stindex]);break end_it;}
      //
      //
      for(int i=0;i<ques.length;i++){
        ques[i].setUsername(st[stindex]);
      }
      //
      mhtable=st[stindex].getMHTable();
      //Detect if user have ever connected before
      File file=new File(username+".dat");
      try{
        if(file.exists()){
          mhtable.readFile(username);
        }else{out.println("MacroHard Servis Istasyonu'yla ilk baglantiniz. Eger degilse, sistemden cikiniz ve sistem yoneticisi ile temas kurunuz.");}
      }catch(Exception e){out.println(e);out.println("Surekli Hata Olustu. Lutfen Sistem Yoneticiniz Ile Temasa Geciniz.");}
      //
      ViewLastThreeLogin(out);
      //
      //This PrintMenu(out) is used when the user first logs on to MH Server Station
      PrintMenu(out);
			while(!done){ //inner
        dataout.writeChars(st[stindex].getUsername()+" >");
        String str=in.readLine().trim();
        try{
          doCommand(str);
          out.println("");  //Print out an empty line
        }catch(NumberFormatException e){out.println("<---------DIKKAT: Komutunuz gecersiz bir islem yuruttu.---------->");}
        catch(NullPointerException e){out.println("<-------DIKKAT: Lutfen soru numarisini dogru giriniz.------->");}
        catch(Exception e){out.println("<-------DIKKAT: Tan�mlanmam�� hata olu�tu------->");}
				if(str.trim().toUpperCase().equals("EXIT")){done=true;mhtable.writeFile(username);ThreadManager.removeStudent(st[stindex]);}
			}//while(!done) //inner
      }//while(!done)
			s.close();
      this.stop();
		}//try
    catch(SocketException e){
      if(username!=null){
        ThreadManager.removeStudent(st[stindex]);
        ThreadManager.studentconnected=false;
        mhtable.writeFile(username);
      }
    }
    catch(NullPointerException npe){
      mhtable.writeFile(username);
      ThreadManager.removeStudent(st[stindex]);
      ThreadManager.studentconnected=false;
    }
    catch(Exception e){
    System.out.println(e);
    mhtable.writeFile(username);
    ThreadManager.removeStudent(st[stindex]);
    ThreadManager.studentconnected=false;
    }
		}
    //
    //
    private void PrintMenu(PrintWriter o){
      o.println("");
      o.println("<*************************************************************>");
      o.println("<***************PROGRAMDA KULLANILACAK KOMUTLAR***************>");
      o.println("VQ No     :Soruyu Goruntule Soru No");
      o.println("           Birinci soruyu g�rmek icin: VQ 1 yaz�lacakt�r.");
      o.println("GV No     :Deger ver Soru No");
      o.println("           Birinci sorunun de�erlerini almak icin:GV 1 yaz�lacakt�r.");
      o.println("GR No     :Sorumu notlandir Soru No");
      o.println("           Birinci sorunun cevabinin kontrol edilmesi icin:GR 1 yaz�lacakt�r.");
      o.println("VTG       :�u ana kadar olan toplam notumu g�r�nt�le");
      o.println("LS        :�u anda Macrohard Server'a ba�l� ki�ileri g�r�nt�le");
      o.println("?         :Herhangi bir anda men�y� g�r�nt�lemek icin kullanilir");
      o.println("<*************************************************************>");
      o.println("");
    }
    private void doCommand(String command) throws NumberFormatException,NullPointerException{
    int rowindex=0;
    MHCommand mhcommand=null;   //Initiliaze MHCommand
    command=command.toUpperCase().trim ();
    String commands[]=MHCommand.parseCommand(command);
    int numberofcommands=commands.length;
    for(int i=0;i<commands.length;i++)commands[i]=commands[i].toUpperCase ().trim ();
    //
    //
    if(commands[0].equals("VTG")) mhcommand=new VTG(in,out,dataout,this);
    if(commands[0].equals("MHREMOVE")) mhcommand=new MHREMOVE(in,out,dataout,this);
    if(commands[0].equals("LS")) mhcommand=new LS(in,out,dataout,this);
     if(commands[0].equals("INFORMATION")) mhcommand=new INFORMATION(in,out,dataout,this);
    //
    //
      if((!(commands[0].equals("EXIT")))&&(!commands[0].equals(""))){
          String studenttoremove="";
          //
          //Eger iki tane komut girmediyse Exception olu�uyor. Bu nedenle degisken[1] dizisi hata veriyor
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
                if(commands[0].equals("MHVR")) {MHVR(rowindex);return;}
                if(commands[0].equals("MHREMOVE")) {mhcommand.execute(studenttoremove);return;}
                if(commands[0].equals("INFORMATION")) {mhcommand.execute(commands[1]);return;}
          }
          //
          //
          if(commands[0].toUpperCase().trim().equals("?")) PrintMenu(out);
          if(commands[0].toUpperCase().trim().equals("VTG")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("LS")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("INFORMATION")) mhcommand.execute("");
          if(commands[0].toUpperCase().trim().equals("MHSHUTDOWN")) {logofflistener.ShutDown();}
          if(commands[0].toUpperCase().trim().equals("MHSEND")) {MHMail.sendEMail("gbingol@hotmail.com",getMailData(),out);}
      }
    }
    public Students getStudent(){
      return st[stindex];
    }
    public Students[] getStudentArray(){
      return st;
    }
    private void ViewLastThreeLogin(PrintWriter pw){
      //Views last three login time of the user
      Date date=new Date();
      pw.println("");
      if(!mhtable.getCellValue(0,3).equals ("")){
        pw.println("Sondan 3. sisteme giri� tarihiniz:"+mhtable.getCellValue(0,3));
        mhtable.setCellValue(0,3,mhtable.getCellValue(0,4));
      }
      if(mhtable.getCellValue(0,3).equals (""))mhtable.setCellValue(0,3,mhtable.getCellValue(0,4));
      //
      if(!mhtable.getCellValue(0,4).equals ("")){
        pw.println("Sondan 2. sisteme giri� tarihiniz:"+mhtable.getCellValue(0,4));
        mhtable.setCellValue(0,4,mhtable.getCellValue(0,5));
      }
      if(mhtable.getCellValue(0,4).equals (""))mhtable.setCellValue(0,4,mhtable.getCellValue(0,5));
      //
      if(!mhtable.getCellValue(0,5).equals ("")){
        pw.println("Son giri� tarihiniz              :"+mhtable.getCellValue(0,5));
        mhtable.setCellValue(0,5,date.toString());
      }
      if(mhtable.getCellValue(0,5).equals (""))mhtable.setCellValue(0,5,date.toString());
    }
    //
    //
    public void MHVR(int varrowindex){
      try{
        for(int column=0;column<mhtable.columns;column++){
          dataout.writeChars(String.valueOf(column)+". sutun="+ mhtable.getCellValue(varrowindex,column));
          out.println("  "+mhtable.getCellNote(varrowindex,column));
        }
      }catch(Exception e){}
    }
    //
    //
    public String getMailData(){
      String data="";
      for(int i=0;i<ques.length;i++){
        double value[]=ques[i].values;
        for(int j=0;j<value.length;j++)data=data+String.valueOf(value[j]);
      }
      return data;
    }
	}
//




