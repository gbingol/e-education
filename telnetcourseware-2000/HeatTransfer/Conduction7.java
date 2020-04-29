import java.io.*;
//
//*********************ODADAN OLAN ISI KAYIPLARI HESAPLANIYOR **********************
//
class HomeDesign extends Questions{
    double Tic,Tdis,hd,Ld,Lc1,Lk,Lc2,hc2,hc1,hk,OdenecekPara;
    private final int rownumber=2;
    private final double SorununNotDegeri=80;
    public HomeDesign(){
      super();
      values=new double[7];
      results=new double[4];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" http://www.devres.net'e bakýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
    boolean b1=false,b2=false,b3=false;
      if(!IsValuesGiven){
        do{
          Tic=22;//(double)(int)(Math.random ()*5+20);   //Verilen deger
          Tdis=-1;//(double)(int)(3-Math.random ()*6);   //Verilen deger
          Ld=5;//(double)(int)(3+Math.random ()*3);     //Verilen deger
          hd=3;//(double)(int)(3+Math.random ()*2);     //Verilen deger
          hk=1.214125103398;//0.8+Math.random ()*0.5;                //Verilen deger
          hc1=0.514277171374;//0.5+Math.random ()*0.5;               //Verilen deger
          Lc1=1.635981138627;//1+Math.random()*1;                    //Verilen deger
          Lc2=Lc1;  hc2=hc1;    Lk=Lc2;
          b1=hd>(hc2+hk+0.5);                       //Duvarýn yüksekliðinin cam+kapý dan büyük olma þartý
          b2=Ld>(2*Lc1+0.5);                        //Duvarýn uzunluðunun cam+kapý dan büyük olma þartý
          b3=b1&&b2;                                //Ýki þart beraber saðlansýn
        }while(!b3);
        //
        pw.println("Tic°C= "+String.valueOf(Tic));values[0]=Tic;
        pw.println("Tdis°C= "+String.valueOf(Tdis));values[1]=Tdis;
        pw.println("Ld (m)= "+String.valueOf(Ld));values[2]=Ld;
        pw.println("hd (m)= "+String.valueOf(hd));values[3]=hd;
        pw.println("hk (m)= "+String.valueOf(hk));values[4]=hk;
        pw.println("hc1 (m)= "+String.valueOf(hc1));values[5]=hc1;
        pw.println("Lc1 (m)= "+String.valueOf(Lc1));values[6]=Lc1;
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
        Tic=values[0];pw.println("Tic°C= "+String.valueOf(Tic));
        Tdis=values[1];pw.println("Tdis°C= "+String.valueOf(Tdis));
        Ld=values[2];pw.println("Ld (m)="  +String.valueOf(Ld));
        hd=values[3];pw.println("hd (m)= "+String.valueOf(hd));
        hk=values[4];pw.println("hk (m)= "+String.valueOf(hk));
        hc1=values[5];pw.println("hc1 (m)= "+String.valueOf(hc1));
        Lc1=values[6];pw.println("Lc1 (m)= "+String.valueOf(Lc1));

      }
    }
    protected void CalculateWriteResults(){
      double Acam,Akapi,ToplamAlan,Aduvar,Qcam,Qduvar,Qkapi,Qtoplam;
      ToplamAlan=Ld*hd;
      Acam=2*(Lc1*hc1);
      Akapi=Lk*hk;
      Aduvar=ToplamAlan-Acam-Akapi;
      //
      //PENCERELER TEK CAMLI OLURSA
      //
      final double Uduvar=1.428571429,Ubasitcam=4.5,Ukapi=3;
      Qduvar=Uduvar*Aduvar*(Tic-Tdis);
      Qcam=Ubasitcam*Acam*(Tic-Tdis);
      Qkapi=Ukapi*Akapi*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik ýsý kaybý
      OdenecekPara=(Qtoplam/1000.0)*20175;    //Doðal Gaz Konut (Ýstanbul) 20175 TL
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(OdenecekPara));mhtable.setCellNote(rownumber,11,"Pencereler tek camlý olursa odenecek para");
      //
      //Ýki cam arasi  6 mm
      //
      final double U_altimm=2.8;    //Ýki cam arasi 6 mm için Isý Transfer katsayýsý
      //Qkapi ve Qduvar degismiyor
      Qcam=U_altimm*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik ýsý kaybý
      OdenecekPara=(Qtoplam/1000.0)*20175;    //Doðal Gaz Konut (Ýstanbul) 20175 TL
      //Record them to MHTable
      mhtable.setCellValue(rownumber,12,String.valueOf(OdenecekPara));mhtable.setCellNote(rownumber,12,"Iki cam arasi 6 mm olursa odenecek para");
      //
      //Ýki cam arasi  12 mm
      //
      final double U_onikimm=2.5;    //Ýki cam arasi 12 mm için Isý Transfer katsayýsý
      //Qkapi ve Qduvar degismiyor
      Qcam=U_onikimm*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik ýsý kaybý
      OdenecekPara=(Qtoplam/1000.0)*20175;    //Doðal Gaz Konut (Ýstanbul) 20175 TL
      //Record them to MHTable
      mhtable.setCellValue(rownumber,13,String.valueOf(OdenecekPara));mhtable.setCellNote(rownumber,13,"Iki cam arasi 12 mm olursa odenecek para");
      //
      //Duvarlar 5 cm izolasyon malzemesi (k=0.035 kcal/mh°C) ile kaplanýrsa
      //Pencereler tek camlý olarak düþünülüyor
      //
      final double kizolasyon=0.035;
      double temp=(5.0/100.0)/kizolasyon;
      double temp2=1/Uduvar;      //dx/k + 1/Uduvar
      double U_izoleduvar=1/(temp+temp2);
      Qduvar=U_izoleduvar*Aduvar*(Tic-Tdis);
      Qcam=Ubasitcam*Acam*(Tic-Tdis);
      Qtoplam=Qduvar+Qcam+Qkapi;
      Qtoplam=Qtoplam*24;          //24 saatlik ýsý kaybý
      OdenecekPara=(Qtoplam/1000.0)*20175;    //Doðal Gaz Konut (Ýstanbul) 20175 TL
      //Record them to MHTable
      mhtable.setCellValue(rownumber,14,String.valueOf(OdenecekPara));mhtable.setCellNote(rownumber,14,"Duvarda izolasyon olursa odenecek para");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[4];
      double totalpoint=0;
      double varOdenecekPara_tekcam=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varOdenecekPara_altimm=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double varOdenecekPara_onikimm=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
      double varOdenecekPara_izoleduvar=Double.valueOf(mhtable.getCellValue(rownumber,14)).doubleValue();
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
            out.writeChars("Pencereler tek camli olursa TL cinsinden isi kaybini giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Iki cam arasi 6 mm olursa TL cinsinden isi kaybini giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Iki cam arasi 12 mm olursa TL cinsinden isi kaybini giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Duvarda izolasyon olursa TL cinsinden isi kaybini giriniz: ");
            useranswer[3]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn tek camli pencere icin");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn iki pencere arasi 6 mm icin cevabi");
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));mhtable.setCellNote(rownumber,18,"Kullanýcýnýn iki pencere arasi 12 mm icin cevabi");
          mhtable.setCellValue(rownumber,19,String.valueOf(useranswer[3]));mhtable.setCellNote(rownumber,19,"Kullanýcýnýn izolasyonli duvar icin cevabi");
          //
          double upperlimit[]=new double[4];
          double lowerlimit[]=new double[4];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varOdenecekPara_tekcam*(1-lowerRange);upperlimit[0]=varOdenecekPara_tekcam*(1+upperRange);
          lowerlimit[1]=varOdenecekPara_altimm*(1-lowerRange);upperlimit[1]=varOdenecekPara_altimm*(1+upperRange);
          lowerlimit[2]=varOdenecekPara_onikimm*(1-lowerRange);upperlimit[2]=varOdenecekPara_onikimm*(1+upperRange);
          lowerlimit[3]=varOdenecekPara_izoleduvar*(1-lowerRange);upperlimit[3]=varOdenecekPara_izoleduvar*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
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
          pw.println("Pencereler tek camli icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Iki pencere arasi 6 mm "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("Iki pencere arasi 12 mm "+mhtable.getCellValue(rownumber,18)+" girdiniz");
          pw.println("Izole duvar icin "+mhtable.getCellValue(rownumber,19)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
