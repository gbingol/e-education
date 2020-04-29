import java.io.*;
//
//*********************Uygulamada Taþýným 6 + 7 soru**********************
//
class Convection6 extends Questions{
    double T,V,L,Tlevha,q,Sigma1,Sigma2;
    private final int rownumber=2;
    private final double SorununNotDegeri=30;
    public Convection6(){
      super();
      values=new double[4];
      results=new double[3];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" T°C, 1 (atm) kosullarindaki hava, bir duz levha uzerinden V (m/s) hizla");
      pw.println("akmaktadir. Levha L (cm) uzunlugunda olup Tlevha°C'de sabit tutulmaktadir.");
      pw.println("z yonunde birim uzunluk kabul edip, ");
      pw.println("A) Levhadan havaya olan isi transferini (W) hesaplayiniz.");
      pw.println("B) Akimin levhanin baslangicinda gelistigini (cm),");
      pw.println("C) Reort=5*10^5'den baslayarak gelistigini kabul ederek,");
      pw.println("   turbulant sinir tabaka kalinligini (cm) hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    double Tf,Ro,Viskozite,Pr,k,Cp,ReL,NuL,h;
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            T =(double)(int)(Math.random() * 15 + 15);
            V =(double)(int)(Math.random() * 30 + 40);
            L =(double)(int)(Math.random() * 40 + 50);
            Tlevha =(double)(int)(Math.random() * 30 + 50);
            Tf=(T+Tlevha)/2;    //°C
            Ro=101325/(287*(Tf+273.15));
            Viskozite=hava.Viskozite (Tf);
            Pr=hava.Pr(Tf);
            k=hava.k(Tf);
            Cp=hava.Cp(Tf);
            ReL=(Ro*V*(L/100))/Viskozite;
            NuL=Math.pow(Pr,1.0/3.0)*(0.037*Math.pow(ReL,0.8)-871);
            h=NuL*k/(L/100);
            double Area=(L/100)*1;   //1=birim uzunluk
            q=h*Area*(Tlevha-T);
            double Rex=ReL;
            //Akým Levhanýn baþlangýcýnda geliþtiðinde sýnýr tabaka kalýnlýðý
            Sigma1=(L/100)*0.381*Math.pow(Rex,(-1.0/5.0));
            Sigma1=Sigma1*100;    //birim cm oluyor
            //Akým Reort=5*10^5'den baþlayarak geliþtiðinde sýnýr tabaka kalýnlýðý
            Sigma2=(L/100)*(0.381*Math.pow(Rex,(-1.0/5.0))-10256*Math.pow(Rex,-1));
            Sigma2=Sigma2*100;    //birim cm oluyor
        //Turbulent deðer verilmesi saðlaniyor
        //25000 degeri öðrencilerin 5*10^5'i geçtiklerinden emin olmak için
        }while(!(ReL>(5*Math.pow(10,5)+25000)));
        //
        pw.println("T°C= "+String.valueOf(T));values[0]=T;
        pw.println("V (m/s)= "+String.valueOf(V));values[1]=V;
        pw.println("L (cm)= "+String.valueOf(L));values[2]=L;
        pw.println("Tlevha°C= "+String.valueOf(Tlevha));values[3]=Tlevha;
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
        T=values[0];pw.println("T°C= "+String.valueOf(T));
        V=values[1];pw.println("V (m/s)= "+String.valueOf(V));
        L=values[2];pw.println("L (cm)= "+String.valueOf(L));
        Tlevha=values[3];pw.println("Tlevha°C= "+String.valueOf(Tlevha));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(q));mhtable.setCellNote(rownumber,11,"Isi tranferi (W) icin sistem cevabi");
      mhtable.setCellValue(rownumber,12,String.valueOf(Sigma1));mhtable.setCellNote(rownumber,12,"Levhanin baslangicinda gelisirse");
      mhtable.setCellValue(rownumber,13,String.valueOf(Sigma2));mhtable.setCellNote(rownumber,13,"Re=5*10^5'den itibaren gelisirse");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[3];
      double totalpoint=0;
      double varq=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varSigma1=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double varSigma2=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
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
            out.writeChars("Isi transferi miktarini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Levhanin basindan gelistigi varsayilan sinir tabak kalinligi (cm): ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Reort=5*10^5 den itibaren gelistigi varsayilan sinir tabak kalinligi (cm): ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn isi transferi (W) icin cevabi");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanicinin Sigma1 (cm) cevabi");
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));mhtable.setCellNote(rownumber,18,"Kullanicinin Sigma2 (cm) cevabi");
          //
          double upperlimit[]=new double[3];
          double lowerlimit[]=new double[3];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varq*(1-lowerRange);upperlimit[0]=varq*(1+upperRange);
          lowerlimit[1]=varSigma1*(1-lowerRange);upperlimit[1]=varSigma1*(1+upperRange);
          lowerlimit[2]=varSigma2*(1-lowerRange);upperlimit[2]=varSigma2*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[1])+" cevabiniz yanlis.");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
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
          pw.println("Isi transferi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Sigma1 icin "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("Sigma2 icin "+mhtable.getCellValue(rownumber,18)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
