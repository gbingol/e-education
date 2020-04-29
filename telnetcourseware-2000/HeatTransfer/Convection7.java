import java.io.*;
//
//*********************Uygulamada Taþýným 8. soru**********************
//
class Convection7 extends Questions{
    double L,x,Dic,V,Tsu,hdis,Tdis,k_boru,q;
    private final int rownumber=3;
    private final double SorununNotDegeri=15;
    public Convection7(){
      super();
      values=new double[8];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" L (m) uzunlugunda, x (cm) et kalinliginda, Dic (cm) ic capindaki bir boru");
      pw.println("icinden V (m/s) hiz ile ortalama Tsu°C sicakliginda su gecmektedir. Dis");
      pw.println("yuzey isi tasinim katsayisi hdis (W/m2K), dis sicaklik Tdis°C, borunun");
      pw.println("isi iletim katsayisi k_boru (W/mK) olduguna gore isi kaybini (W) hesaplayiniz");
      pw.println("Boru ic yuzeyi surtunmesiz alinacaktir.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    double T_bulk,k,Ro,Viskozite,Pr,Nu,Red,h,Aic,Adis,Ddis;
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            L =(double)(int)(Math.random() * 10 + 5);
            x =Math.random() *1 +1 ;
            Dic =(double)(int)(Math.random() * 10 + 10);
            V=(double)(int)(Math.random()*5+10);
            Tsu =(double)(int)(Math.random() * 40 + 40);
            hdis=(double)(int)(Math.random()*20+20);
            Tdis=(double)(int)(Math.random()*5+5);
            k_boru=(double)(int)(Math.random()*20+40);
            T_bulk=Tsu;   //Ortalama sicaklik olduðundan bulk sicaklik alinabilir
            k=su.k(T_bulk);
            Ro=su.Ro(T_bulk);
            Viskozite=su.Viskozite(T_bulk);
            Pr=su.Pr(T_bulk);
            Red=V*(Dic/100)*Ro/Viskozite;
            Nu=0.023*Math.pow(Red,0.8)*Math.pow(Pr,0.4);
            h=k*Nu/(Dic/100);
            double ric=Dic/2;
            double rdis=Dic/2+x;
            Aic=2*Math.PI*(ric/100)*L;
            Adis=2*Math.PI*(rdis/100)*L;
            double dT=Tsu-Tdis;
            double direnc=1/(h*Aic)+Math.log(rdis/ric)/(2*Math.PI*k_boru*L)+1/(hdis*Adis);
            q=dT/direnc;
        }while(!(Red>2300+1000));  //1000 guvenlik payi
        //
        pw.println("L (m)= "+String.valueOf(L));values[0]=L;
        pw.println("x (cm)= "+String.valueOf(x));values[1]=x;
        pw.println("Dic (cm)= "+String.valueOf(Dic));values[2]=Dic;
        pw.println("V (m/s)= "+String.valueOf(V));values[3]=V;
        pw.println("Tsu°C= "+String.valueOf(Tsu));values[4]=Tsu;
        pw.println("hdis (W/m2K)= "+String.valueOf(hdis));values[5]=hdis;
        pw.println("Tdis°C= "+String.valueOf(Tdis));values[6]=Tdis;
        pw.println("k_boru (W/mK)= "+String.valueOf(k_boru));values[7]=k_boru;
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
        L=values[0];pw.println("L (m)= "+String.valueOf(L));
        x=values[1];pw.println("x (cm)= "+String.valueOf(x));
        Dic=values[2];pw.println("Dic (cm)= "+String.valueOf(Dic));
        V=values[3];pw.println("V (m/s)= "+String.valueOf(V));
        Tsu=values[4];pw.println("Tsu°C= "+String.valueOf(Tsu));
        hdis=values[5];pw.println("hdis (W/m2K)= "+String.valueOf(hdis));
        Tdis=values[6];pw.println("Tdis°C= "+String.valueOf(Tdis));
        k_boru=values[7];pw.println("k_boru (W/mK)= "+String.valueOf(k_boru));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(q));mhtable.setCellNote(rownumber,11,"Isi kaybi (W) icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varq=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Isi kaybi miktarini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn isi kaybi (W) icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varq*(1-lowerRange);upperlimit[0]=varq*(1+upperRange);
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
          pw.println("Isi kaybi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
