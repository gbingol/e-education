import java.io.*;
//
//*********************
//
class Convection15 extends Questions{
    double Tgiris,Dboru,Vsu,Lboru,Tboru,Tcikis;
    private final int rownumber=1;
    private final double SorununNotDegeri=30;
    public Convection15(){
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
      pw.println(" Tgiriþ°C sýcaklýðýndaki su Dboru (cm) çapýndaki boruya Vsu (cm/s) ortalama ");
      pw.println(" hýz ile girmektedir. Eger boru Lboru (m) uzunluðunda ve  yüzey sýcaklýðý ");
      pw.println("sabit Tw°C ise cikis sicakligini hesaplayýnýz ");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double Tbort=0,Tb1=0,Tb2,TempTb2,Ro,Cp,Viskozite,k,Pr,Re,Sart,ViskoziteW;
    double Temp1,Temp2,Temp3,Nu=0,h,Debi;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        TempTb2=0;Tb2=0;
        int iter=1;
        end_it:
        do{
          if (iter > 50) iter = 1;    //iterasyon herhangi bir sebeple kaçmýþ ise
          if (iter == 1){
            Tgiris=(double)(int)(Math.random()*70+30);
            Dboru=(Math.random()* 3 + 2)/100.0;
            Vsu=(double)(int)(Math.random()* 8 + 2) /100+30; // cm/s doðal taþýnýmýndan kurtulmak için V>30
            Lboru=(double)(int)(Math.random()*5 + 1);
            Tboru=(double)(int)(Math.random()*50+50+Tgiris); //Boru sýcaklýðý her zaman suyun giris sýcaklýðýndan yüksek olmalý +20 güvenlik nedeniyle eklenmiþtir
            Tbort = Tgiris;
            Tb1 = Tgiris;
        }
        TempTb2 = Tb2;
        Ro = su.Ro(Tbort);
        Cp = su.Cp(Tbort);
        Viskozite=su.Viskozite(Tbort);
        k = su.k(Tbort);
        Pr = su.Pr(Tbort);
        Re = (Ro * Vsu* Dboru)/Viskozite;
        Sart = Re * Pr * Dboru/Lboru;
        ViskoziteW = su.Viskozite(Tboru);
        if (Re < 2300){     //Eðer laminer bölgedeyse
            if (Sart < 10) break end_it;
            Temp1 = Math.pow((Re * Pr),(1.0 /3.0));
            Temp2 = Math.pow((Dboru/Lboru),(1.0 /3.0));
            Temp3 = Math.pow((Viskozite / ViskoziteW),(0.14));
            Nu = 1.86 * Temp1 * Temp2 * Temp3;
        }
        else if (Re > 2300){
            Nu = 0.023 * Math.pow(Re,0.8) * Math.pow(Pr,0.4);
        }
        h = k * Nu / Dboru;
        Debi = Ro * Math.PI *Math.pow(Dboru,2)*Vsu/4.0;      //kg/s
        Temp1 = h * Math.PI * Dboru * Lboru;
        Temp2 = Debi * Cp * 1000;
        Temp3 = Temp1 * Tboru- Temp1*Tb1/2 + Temp2 * Tb1;
        Tb2 = Temp3/(Temp2 + Temp1 / 2);
        Tbort = (Tb1 + Tb2) / 2;
        iter = iter + 1;
        }while(!( (Math.abs(TempTb2 - Tb2)<0.001) && (Tboru/Tb2 > 1.25) ));
        Tcikis=Tb2;
        //
        pw.println("Tgiris°C= "+String.valueOf(Tgiris));values[0]=Tgiris;
        pw.println("Dboru (cm)= "+String.valueOf(Dboru*100));values[1]=Dboru;
        pw.println("Vsu (cm/s)= "+String.valueOf(Vsu*100));values[2]=Vsu;
        pw.println("Lboru (m)= "+String.valueOf(Lboru));values[3]=Lboru;
        pw.println("Tboru°C= "+String.valueOf(Tboru));values[4]=Tboru;
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
        Tgiris=values[0];pw.println("Tgiris°C= "+String.valueOf(Tgiris));
        Dboru=values[1];pw.println("Dboru (cm)= "+String.valueOf(Dboru*100));
        Vsu=values[2];pw.println("Vsu (cm/s)= "+String.valueOf(Vsu*100));
        Lboru=values[3];pw.println("Lboru (m)= "+String.valueOf(Lboru));
        Tboru=values[4];pw.println("Tboru°C= "+String.valueOf(Tboru));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Tcikis));mhtable.setCellNote(rownumber,11,"Cikis sicakligi icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varTcikis=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
      double doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
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
            out.writeChars("Cikis sicakligini °C giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Cikis sicakligi °C icin kullanici cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varTcikis*(1-lowerRange);upperlimit[0]=varTcikis*(1+upperRange);
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
          pw.println("Cikis sicakligi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
