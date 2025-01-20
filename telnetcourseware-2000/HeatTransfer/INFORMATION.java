import java.io.*;
public class INFORMATION extends MHCommand{
  //
  private ThreadSocket ts;
  public int students_participated;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  //
  public INFORMATION(){};
  public INFORMATION(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String s){
    MHTable mhtable=null;
    int numberofavailablefiles=0;
    String username[]=MHFileReader.getUsername ();
    String password[]=MHFileReader.getPassword ();
    int nofstudents=MHFileReader.getNumberOfStudents (); //Sisteme kayýtlý öðrenci sayýsý
    int NumberofQuestions=ts.ques.length;               //Öðrencilere açýlan soru sayýsý
    //
    double questions_total_value[]=new double[NumberofQuestions];
    int who_answered_this_question[]=new int[NumberofQuestions];
    for(int i=0;i<questions_total_value.length;i++) {
      questions_total_value[i]=0;
      who_answered_this_question[i]=0;
    }
    //

    Students students[]=new Students[nofstudents];
    String data[]=new String[nofstudents];
    int counter=0;
    for(int i=1;i<username.length;i++){
      students[i]=new Students(username[i],password[i]);
      File f=new File(username[i]+".dat");
      if(f.exists ()){
        counter++;
        try{
          mhtable=students[i].getMHTable ();
          mhtable.readFile(username[i]);
        }catch(Exception e){}
        String str="Ogrenci numarasý:"+username[i]+"\r\n";
        String str1="";
        String str2="***********************************************"+"\r\n";

        for(int k=0;k<NumberofQuestions;k++){
          String temp=mhtable.getCellValue (k+1,22);     //Soru notlarýný okuyalým
          if(!temp.equals("")){
            questions_total_value[k]=questions_total_value[k]+Double.valueOf(mhtable.getCellValue (k+1,22)).doubleValue ();
            temp=mhtable.getCellValue (k+1,22)+" aldý.";
            who_answered_this_question[k]++;
          }else{
            questions_total_value[k]=questions_total_value[k]+0.0;
            temp="0 aldý."+ "  NOT: Öðrenci soruyu cevaplandýrmadý.";
          }
          str1=str1+String.valueOf(k+1)+".nýncý sorudan "+temp+"\r\n";
        }

        data[counter]=str+str1+str2;
        numberofavailablefiles++;

      }
      students_participated=numberofavailablefiles;
    }

    String all_information="";
    String average_information="",average_information1="",average_information2="",average_information3="";

    String temp1=String.valueOf(nofstudents-1)+" ogrenci sisteme kayýtlý"+"\r\n";     //-1 expresses admin
    String temp2=String.valueOf(students_participated)+" ogrenci sisteme baglandi"+"\r\n"+"\r\n";
    String temp=temp1+temp2;

    for(int k=0;k<NumberofQuestions;k++){
      temp=temp+String.valueOf(k+1)+".inci soru "+String.valueOf(who_answered_this_question[k])+" kisi tarafýndan cevaplanmýþtýr"+"\r\n";
      //
      String str="Katýlan ögrenciler arasýnda "+String.valueOf(k+1)+".ýncý sorunun ortalama degeri="+String.valueOf (questions_total_value[k]/students_participated)+"\r\n";
      average_information1=average_information1+str;
      //
      str="Tüm kayýtlý ögrenciler arasýnda "+String.valueOf(k+1)+".ýncý sorunun ortalama degeri="+String.valueOf (questions_total_value[k]/nofstudents)+"\r\n";
      average_information2=average_information2+str;
      //
      str="Cevaplayan ögrenciler arasýnda "+String.valueOf(k+1)+".ýncý sorunun ortalama degeri="+String.valueOf (questions_total_value[k]/who_answered_this_question[k])+"\r\n";
      average_information3=average_information3+str;
    }
    average_information=average_information1+"\r\n"+average_information2+"\r\n"+average_information3+"\r\n";
    all_information=temp+"\r\n"+average_information+"\r\n";
    for(int i=1;i<=students_participated;i++) all_information=all_information+data[i];
    //
    //
    try{
      if(s.equals("")){
        PrintWriter pwout=new PrintWriter(new FileWriter("Information.txt"));
        pwout.println (all_information);
        pwout.close();
      }
      //
      //Eðer S parametresi girildiyse tüm verileri ekrana da yazsýn
      //
      if(s.equals("S")) out.println(all_information);
      //
      //Eger M parametresi girildiyse mail yollasýn
      //
       if(s.equals("M")) {
        dataout.writeChars ("Lutfen e-mail adresinizi giriniz:");
        String email_to=in.readLine ().trim ();
        boolean isEmailValid=MHMail.isEmailAdressSeemsValid (email_to);
        if(isEmailValid) MHMail.sendEMail (email_to,all_information,out);
        else out.println("MacroHard e-mail adresinizi hatali olarak kabul ediyor");
       }
       //
    }catch(Exception e){}
  }
}
