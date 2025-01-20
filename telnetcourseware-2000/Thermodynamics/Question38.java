import java.io.*;
//
//***************************8th QUESTION*******************************
//
class Question38 extends Questions{
    double V,P,T1,RH1,T2;
    double W1,Pw1,Pa,ma,Ra,mw1,W3,RH3,Pws3,mw3,dm_su;
    public Question38(){
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
      pw.println("V (m3) hacmindeki hava+su buhari karisimi P (kPa) basinc ve T1°C'de olup");
      pw.println("bagil nemi %RH1'dir.  Bu karisim sabit basincta T2°C'ye sogutulursa,    ");
      pw.println("yogusan su buhari miktari (kg) olarak ne kadardir.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(8,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          V=(Math.random()*25+25);
          P=(Math.random()*10+95);
          T1=(Math.random()*10+25);
          RH1=Math.random()*25+40;
          T2=T1-Math.random()*10-5;
          Pw1=su.P(T1)*RH1/100;
          W1=0.622*Pw1/(P-Pw1);
          Pa=P-Pw1;
          Ra=0.287;
          ma=(Pa*V)/(Ra*(T1+273.15));
          mw1=W1*ma;
          RH3=1;
          Pws3=su.P(T2);
          double Pw3=Pws3;
          W3=0.622*Pw3/(P-Pw3);
          mw3=ma*W3;
         dm_su=mw1-mw3;
        }while(!(dm_su>0.1));
        pw.println("V (m3)= "+String.valueOf(V));values[0]=V;
        pw.println("P (kPa) = "+String.valueOf(P));values[1]=P;
        pw.println("T1°C= "+String.valueOf(T1));values[2]=T1;
        pw.println("RH1%= "+String.valueOf(RH1));values[3]=RH1;
        pw.println("T2°C= "+String.valueOf(T2));values[4]=T2;
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(8,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(8,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(8,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("8. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(8,i+1)).doubleValue();
        }
        V=values[0];pw.println("V (m3)= "+String.valueOf(V));
        P=values[1];pw.println("P (kPa)= "+String.valueOf(P));
        T1=values[2];pw.println("T1°C= "+String.valueOf(T1));
        RH1=values[3];pw.println("RH1%= "+String.valueOf(RH1));
        T2=values[4];pw.println("T2°C= "+String.valueOf(T2));
      }
    }
    protected void CalculateWriteResults(){
        double W1,Pw1,Pa,ma,Ra,mw1,W3,RH3,Pws3,mw3,dm_su;
        Pw1=su.P(T1)*RH1/100;
        W1=0.622*Pw1/(P-Pw1);
        Pa=P-Pw1;
        Ra=0.287;
        ma=(Pa*V)/(Ra*(T1+273.15));
        mw1=W1*ma;
        RH3=1;
        Pws3=su.P(T2);
        double Pw3=Pws3;
        W3=0.622*Pw3/(P-Pw3);
        mw3=ma*W3;
        dm_su=mw1-mw3;
        //Record them to MHTable
        mhtable.setCellValue(8,6,String.valueOf(dm_su));mhtable.setCellNote(8,8,"Yogunlasma icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double vardm_su=Double.valueOf(mhtable.getCellValue(8,6)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,8);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,8,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      double doublevalueforquestion=10-(intnumberofanswer-1)*10/5;
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
            out.writeChars("Yogusan su buhari miktarini giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,8,1);
          mhtable.setCellValue(8,7,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          if(vardm_su<0) {vardm_su=-vardm_su;if(useranswer[0]<0) useranswer[0]=-useranswer[0];}
          lowerlimit[0]=vardm_su*(1-lowerRange);upperlimit[0]=vardm_su*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(8,8,"0.000");}
          //
          mhtable.setCellValue(8,8,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 8.soruyu zaten cevaplamistiniz. **********");
          pw.println("Yogusan su buhari miktari icin "+mhtable.getCellValue(8,7)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(8,8)).doubleValue ();
          pw.println("8. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 8");}
    }
}
