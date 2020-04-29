import java.io.*;
//
//*********************ÝLETÝM UYGULAMA 5. SORU **********************
//
class Conduction3 extends Questions{
    double x,kduvar,Aduvar,Tic,Tdis,hic,hdis,Q,T_duvaric,T_duvardis;
    private final int rownumber=3;
    private final double SorununNotDegeri=30;
    public Conduction3(){
      super();
      values=new double[7];
      results=new double[3];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" x (cm) kalinligindaki tugla duvar ortalama isil iletkenlik katsayisi ");
      pw.println("kduvar (W/mK) olan malzemeden insa edilecektir. Duvar alani           ");
      pw.println("Aduvar (m2)'dir. Duvarýn iç tarafindaki ortam sicakliginin Tic°C, dýþ ");
      pw.println("tarafýndaki ortam sýcaklýðýnýn Tdis°C oldugunu ve ic yüzeydeki isi transfer ");
      pw.println("katsayisinin hic W/m2K, dýþ yüzeyindekinin hdis W/m2K oldugunu kabul ederek;");
      pw.println("a) Duvar boyunca gerceklesen isi transferi miktarini (W)");
      pw.println("b) Duvarin ic ve dis yuzey sicakliklarini hesaplayiniz");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          x=(double)(int)(Math.random()*10+15);
          kduvar=Math.random()*0.4+0.25;
          Aduvar=(double)(int)(Math.random()*5+5);
          Tic=(double)(int)(Math.random()*10+10);
          Tdis=-(double)(int)(Math.random()*10+10);
          hic=(double)(int)(Math.random()*5+5);
          hdis=(double)(int)(Math.random()*10+15);
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("x (cm)= "+String.valueOf(x));values[0]=x;
        pw.println("kduvar (W/mK)= "+String.valueOf(kduvar));values[1]=kduvar;
        pw.println("Aduvar (m2)= "+String.valueOf(Aduvar));values[2]=Aduvar;
        pw.println("Tic°C= "+String.valueOf(Tic));values[3]=Tic;
        pw.println("Tdis°C= "+String.valueOf(Tdis));values[4]=Tdis;
        pw.println("hic (W/m2K)= "+String.valueOf(hic));values[5]=hic;
        pw.println("hdis (W/m2K)= "+String.valueOf(hdis));values[6]=hdis;
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
        x=values[0];pw.println("x (cm)= "+String.valueOf(x));
        kduvar=values[1];pw.println("kduvar (W/mK)= "+String.valueOf(kduvar));
        Aduvar=values[2];pw.println("Aduvar (m2)="  +String.valueOf(Aduvar));
        Tic=values[3];pw.println("Tic°C= "+String.valueOf(Tic));
        Tdis=values[4];pw.println("Tdis°C= "+String.valueOf(Tdis));
        hic=values[5];pw.println("hic (W/m2K)= "+String.valueOf(hic));
        hdis=values[6];pw.println("hdis (W/m2K)= "+String.valueOf(hdis));
      }
    }
    protected void CalculateWriteResults(){
      double payda,pay;
      payda=1/hic+(x/100)/kduvar+1/hdis;
      pay=Aduvar*(Tic-Tdis);
      Q=pay/payda;      //W
      T_duvaric=Tic-(Q/Aduvar)/hic;
      T_duvardis=Tdis+(Q/Aduvar)/hdis;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Q));mhtable.setCellNote(rownumber,11,"Isý transferi miktarý");
      mhtable.setCellValue(rownumber,12,String.valueOf(T_duvaric));mhtable.setCellNote(rownumber,12,"Duvarýn iç sýcaklýgý");
      mhtable.setCellValue(rownumber,13,String.valueOf(T_duvardis));mhtable.setCellNote(rownumber,13,"Duvarýn dýs sýcaklýgý");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[3];
      double totalpoint=0;
      double varQ=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varT_duvaric=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double varT_duvardis=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama sayýsýna göre soru deðerini hesapla
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
            out.writeChars("Duvar boyunca gerceklesen isi transferi miktarini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Duvarin ic sicakligini (°C) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Duvarin dis sicakligini (°C) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn girdiði deðer");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn Tic°C icin girdiði deðer");
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));mhtable.setCellNote(rownumber,18,"Kullanýcýnýn Tdis°C icin girdiði deðer");
          //
          double upperlimit[]=new double[3];
          double lowerlimit[]=new double[3];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varQ*(1-lowerRange);upperlimit[0]=varQ*(1+upperRange);
          lowerlimit[1]=varT_duvaric*(1-lowerRange);upperlimit[1]=varT_duvaric*(1+upperRange);
          lowerlimit[2]=varT_duvardis*(1-lowerRange);upperlimit[2]=varT_duvardis*(1+upperRange);
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
          pw.println("Transfer edilen ýsý miktarý için "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Levhanin ic sicakligi için "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("Levhanin dis sicakligi için "+mhtable.getCellValue(rownumber,18)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
