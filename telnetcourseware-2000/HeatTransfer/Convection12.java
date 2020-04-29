import java.io.*;
//
//*********************
//
class Convection12 extends Questions{
    double Tboru,Dboru,Toda,q_L;
    private final int rownumber=3;
    private final double SorununNotDegeri=20;
    public Convection12(){
      super();
      values=new double[3];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Tboru°C sabit sýcaklýðýnda bulunan Dboru (m) çapýndaki yatay bir boru,");
      pw.println("Toda°C sýcaklýðýndaki bir odaya yerleþtirilmiþtir. Birim uzunluktan gerçekleþen");
      pw.println("doðal taþýným ýsý transferini hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double k,Tf,beta,KinematikViskozite,Ro,Viskozite,Ra,c,M,Pr,Gr,Nu,h;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Tboru=(double)(int)(Math.random()*150+100);   //Borunun sýcaklýgý °C
          Dboru=Math.random()+0.1;   // Borunun çapý (m)
          Toda=(double)(int)(Math.random()*10+15);      // Oda sýcaklýðý °C
          //
          Tf = (Tboru+ Toda) / 2;
          k = hava.k(Tf);
          beta = 1 / (Tf+273.15);
          Viskozite = hava.Viskozite(Tf);
          Ro=hava.Ro(Tf);
          KinematikViskozite = Viskozite / Ro;
          Pr = hava.Pr(Tf);
          Gr = (9.81 * beta * (Tboru- Toda)* Math.pow(Dboru,3)) /Math.pow(KinematikViskozite,2);
          Ra = Gr * Pr;
          c = HeatTransferFunctions.getC(Ra);
          M=HeatTransferFunctions.getM(Ra);
          Nu = c * Math.pow(Ra, M);
          h = k * Nu / Dboru;
          q_L=h * Math.PI * Dboru*(Tboru-Toda);
        }while(!(Ra<Math.pow(10,-5)||Ra>Math.pow(10,5))); //Rayleigh sayýsý tablodan okunabilsin
        //
        pw.println("Tboru°C= "+String.valueOf(Tboru));values[0]=Tboru;
        pw.println("Dboru (m)= "+String.valueOf(Dboru));values[1]=Dboru;
        pw.println("Toda°C= "+String.valueOf(Toda));values[2]=Toda;
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
        Tboru=values[0];pw.println("Tboru°C= "+String.valueOf(Tboru));
        Dboru=values[1];pw.println("Dboru (m)= "+String.valueOf(Dboru));
        Toda=values[2];pw.println("Toda°C= "+String.valueOf(Toda));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(q_L));mhtable.setCellNote(rownumber,11,"Birim uzunluktan isi kaybi icin sistem cevabi");
    }
    //
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varq_L=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Birim uzunluktan olan isi kaybini (W/m) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn x1 cm sinir tabaka kalinligi (cm) icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varq_L*(1-lowerRange);upperlimit[0]=varq_L*(1+upperRange);
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
          pw.println("Birim uzunluktan isi kaybi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
