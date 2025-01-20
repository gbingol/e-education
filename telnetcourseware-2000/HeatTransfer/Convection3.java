import java.io.*;
//
//*********************Uygulamada Taþýným 3. soru**********************
//
class Convection3 extends Questions{
    double T,P,V,x1,x2;
    double Ro,Re1,Re2,Sigma1,Sigma2,dm;
    private final int rownumber=3;
    private final double SorununNotDegeri=30;
    public Convection3(){
      super();
      values=new double[5];
      results=new double[3];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" T°C,P (kPa) koþullarýndaki hava bir levha uzerinden V(m/s) hýzla akmaktadýr. ");
      pw.println("A) Levhanýn baslangýcýndan x1 ve x2 (cm) uzakligindaki sinir tabaka kalinligini (cm),");
      pw.println("B) x1 ve x2 (cm) arasindan sinir tabakaya giren debiyi (gr/s) hesaplayiniz. ");
      pw.println("NOT: Akiskanin fiziksel ozelliklerini P=1 (atm) kabul ederek hesaplayabilirsiniz");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          T=(double)(int)(Math.random()*10+25);   //Hava sýcaklýgý °C
          P=(double)(int)(Math.random()*10+95); //Havanýn basýncý (kPa)
          V=(double)(int)(Math.random()*4+1);    //Havanýn hýzý  (m/s)
          x1=(double)(int)(Math.random()*10+10);        //Ilk a cm lik uzaklýk
          x2=x1+(double)(int)(Math.random()*10+10);  //Ilk b cm lik uzaklýk b=a+herhangi bir sayý
          //
          Ro=(P*1000)/(287.0*(T+273.15));
          double Viskozite=hava.Viskozite(T);
          Re1=Ro*V*(x1/100)/Viskozite;
          Re2=Ro*V*(x2/100)/Viskozite;
          Sigma1=(4.64*(x1/100)/Math.sqrt (Re1))*100;    //metre*100=cm
          Sigma2=(4.64*(x2/100)/Math.sqrt (Re2))*100;    //metre*100=cm
          dm=5.0/8.0*Ro*V*(Sigma2/100-Sigma1/100)*1000;        //g/s    kg/s çok küçük olduðundan aralýk zor tutturulur
          //
        }while(!(Re2<5*Math.pow(10,5)));    //Laminer kalma þartýna bak
        //
        pw.println("T°C= "+String.valueOf(T));values[0]=T;
        pw.println("P (kPa)= "+String.valueOf(P));values[1]=P;
        pw.println("V (m/s)= "+String.valueOf(V));values[2]=V;
        pw.println("x1 (cm)= "+String.valueOf(x1));values[3]=x1;
        pw.println("x2 (cm)= "+String.valueOf(x2));values[4]=x2;
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
        P=values[1];pw.println("P (kPa)= "+String.valueOf(P));
        V=values[2];pw.println("V (m/s)="  +String.valueOf(V));
        x1=values[3];pw.println("x1 (cm)= "+String.valueOf(x1));
        x2=values[4];pw.println("x2 (cm)= "+String.valueOf(x2));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Sigma1));mhtable.setCellNote(rownumber,11,"Ilk x1 cm'deki sinir tabaka kalinligi (cm)");
      mhtable.setCellValue(rownumber,12,String.valueOf(Sigma2));mhtable.setCellNote(rownumber,12,"x2 cm'deki sinir tabaka kalinligi (cm)");
      mhtable.setCellValue(rownumber,13,String.valueOf(dm));mhtable.setCellNote(rownumber,13,"x1 ve x2 arasýnda dm (gr/s)");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[3];
      double totalpoint=0;
      double varSigma1=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varSigma2=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double vardm=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
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
            out.writeChars("Ilk x1 (cm)'de sinir tabaka kalinligini (cm) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Ilk x2 (cm)'de sinir tabaka kalinligini (cm) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("x1 ve x2(cm) arasinda sinir tabakaya giren debiyi (gr/s) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn x1 cm sinir tabaka kalinligi (cm) icin cevabi");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn x2 cm sinir tabaka kalinligi (cm) icin cevabi");
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));mhtable.setCellNote(rownumber,18,"Kullanýcýnýn x1 ve x2 sinir tabakaya giren debi (kg/s) icin cevabi");
          //
          double upperlimit[]=new double[3];
          double lowerlimit[]=new double[3];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varSigma1*(1-lowerRange);upperlimit[0]=varSigma1*(1+upperRange);
          lowerlimit[1]=varSigma2*(1-lowerRange);upperlimit[1]=varSigma2*(1+upperRange);
          lowerlimit[2]=vardm*(1-lowerRange);upperlimit[2]=vardm*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[1])+" cevabiniz yanlis.");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[2])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("x1 cm'de sinir tabaka kalinlgi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("x2 cm'de sinir tabaka kalinlgi icin "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("x1 ve x2 cm de sinir tabakaya giren debi icin"+mhtable.getCellValue(rownumber,18)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
