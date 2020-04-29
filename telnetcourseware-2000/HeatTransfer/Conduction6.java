import java.io.*;
//
//*********************ÝLETÝM UYGULAMA 14. SORU **********************
//
class Conduction6 extends Questions{
    double D,To,Tortam,h,Tson,t;
    private final int rownumber=1;
    private final double SorununNotDegeri=20;
    public Conduction6(){
      super();
      values=new double[5];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Bir D(cm) çapýndaki top (Cp=0.46 kJ/kgK; k=35 W/m°C) baþlangýçta uniform");
      pw.println("olarak To°C'de iken aniden Tortam°C sýcaklýðýnda sabit tutulan bir ortama ");
      pw.println("býrakýlmaktadýr. Yüzey taþýným ýsý transfer katsayýsý h W/m2°C'dir. Topun");
      pw.println("sýcaklýðýnýn Tson°C'ye düþmesi için geçecek zamaný (saniye) hesaplayýnýz.");
      pw.println("Topun yoðunluðu=7800 kg/m3");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        double Biot;
        do{
          D=(double)(int)(Math.random()*5+3);
          To=(double)(int)(Math.random()*100+350);    //Topun baþlangýç sýcaklýðý
          Tortam=(double)(int)(Math.random()*50+50);  //Topun býrakýldýðý ortamýn sýcaklýðý
          h=(double)(int)(Math.random()*10+5);        //Yüzey taþýným ýsý transfer katsayýsý
          Tson=Tortam+(double)(int)(Math.random()*50+50);  //Topun t (saniye) sonraki sýcaklýðý
          double r=(D/2)/100.0;
          double V=4.0/3.0*Math.PI*Math.pow(r,3);
          double A=4*Math.PI*Math.pow(r,2);
          Biot=h*(V/A)/35;
          double temp1=-h*A/(7800*0.46*1000/*Cp birimden dolayý 1000 le çarpýlýr*/*V);
          double temp2=(Tson-Tortam)/(To-Tortam);
          t=Math.log(temp2)/temp1;
        }while(!(Biot<0.1));    //Biot'un 0.1'den küçük olma þartýna bak
        //
        pw.println("D (cm)= "+String.valueOf(D));values[0]=D;
        pw.println("To°C= "+String.valueOf(To));values[1]=To;
        pw.println("Tortam°C= "+String.valueOf(Tortam));values[2]=Tortam;
        pw.println("h (W/m2°C)= "+String.valueOf(h));values[3]=h;
        pw.println("Tson°C= "+String.valueOf(Tson));values[4]=Tson;
        //
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(rownumber,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(rownumber,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(rownumber,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println(String.valueOf(rownumber)+". Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(rownumber,i+1)).doubleValue();
        }
        D=values[0];pw.println("D (cm)= "+String.valueOf(D));
        To=values[1];pw.println("To°C= "+String.valueOf(To));
        Tortam=values[2];pw.println("Tortam°C="  +String.valueOf(Tortam));
        h=values[3];pw.println("h (W/m2°C)= "+String.valueOf(h));
        Tson=values[4];pw.println("Tson°C= "+String.valueOf(Tson));
      }
    }
    protected void CalculateWriteResults(){
      double vart;
      vart=t;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(vart));mhtable.setCellNote(rownumber,11,"Topun sýcaklýðýnýn Tson'a düþmesi için geçen süre");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double vart=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama sayýsýna göre soru deðerini hesapla
      //
      //double doublevalueforquestion=SorununNotDegeri-((intnumberofanswer-1)/5)*SorununNotDegeri;
      //yapýldýðýnda doublevalueforquestion deðeri hep 10 çýkmakta çünkü parantez içindeki deðer
      //integer sayýlýp bölüm 0.xx çýkýnca sýfýr yapýlmakta
      //
      double doublevalueforquestion=SorununNotDegeri-(intnumberofanswer-1)*SorununNotDegeri/5;
      String strvalueforquestion=String.valueOf(doublevalueforquestion);
      //
      try{
        if(!isanswered){
          boolean isaccepted=false;
          do{
          //If user does not enter a value end just presses enter key
          //java throws java.lang.NumberFormatException: empty String
          //and hereby user have the chance not to enter any value and quit this session for
          //further chance of value input
          //
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+String.valueOf(doublevalueforquestion));
            out.writeChars("Topun sicakliginin "+String.valueOf(Tson)+"'a dusmesi icin gecen sureyi (saniye) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn toplam isi transferi icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=vart*(1-lowerRange);upperlimit[0]=vart*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Gecen sure icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
