import java.io.*;
//
//***************************4th QUESTION*******************************
//
class Question34 extends Questions{
    double Pa,W,ma,mw,a,b,c,T,P,BN;
    public Question34(){
      super();
      values=new double[6];
      results=new double[4];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("a(m)*b(m)*c(m) boyutlarýndaki bir odanýn içinde T°C, P(kPa) ve %BN");
      pw.println("koþullarýnda nemli hava bulunmaktadýr.");
      pw.println("A) Kuru havanýn kýsmi basýncýný bulunuz,");
      pw.println("B) Havanýn nem oranýný bulunuz,");
      pw.println("C) Odada bulunan kuru ve nem kütlelerini bulunuz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(4,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
      double h2=0,temph=0;
        a=(Math.random()*4+1);pw.println("a (m)= "+String.valueOf(a));values[0]=a;
        b=(Math.random()*4+1);pw.println("b (m) = "+String.valueOf(b));values[1]=b;
        c=(Math.random()*4+1); pw.println("c (c)= "+String.valueOf(c));values[2]=c;
        T=(Math.random()*10+15);pw.println("T°C= "+String.valueOf(T));values[3]=T;
        P=(Math.random()*20+90);pw.println("P (kPa)= "+String.valueOf(P));values[4]=P;
        BN=(Math.random()*20+45);pw.println("%BN= "+String.valueOf(BN));values[5]=BN;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(4,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(4,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(4,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("4. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(4,i+1)).doubleValue();
        }
        a=values[0];pw.println("a (m)= "+String.valueOf(a));
        b=values[1];pw.println("b (m)= "+String.valueOf(b));
        c=values[2];pw.println("c (m)= "+String.valueOf(c));
        T=values[3];pw.println("T°C= "+String.valueOf(T));
        P=values[4];pw.println("P (kPa)= "+String.valueOf(P));
        BN=values[5];pw.println("%BN= "+String.valueOf(BN));
      }
    }
    protected void CalculateWriteResults(){
        double Pw,Psu;
        Psu=su.P(T);
        Pw=BN/100*Psu;
        //Results to obtain by users
        Pa=P-Pw;
        W=0.622*Pw/(P-Pw);
        ma=Pa*(a*b*c)/(0.287*(T+273.15));
        mw=Pw*(a*b*c)/(0.4615*(T+273.15));
        //Record them to MHTable
        mhtable.setCellValue(4,7,String.valueOf(Pa));mhtable.setCellNote(4,7,"Pa icin dogru cevap");
        mhtable.setCellValue(4,8,String.valueOf(W)); mhtable.setCellNote(4,8,"W icin dogru cevap");
        mhtable.setCellValue(4,9,String.valueOf(ma));mhtable.setCellNote(4,9,"ma icin dogru cevap");
        mhtable.setCellValue(4,10,String.valueOf(mw));mhtable.setCellNote(4,10,"mw icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[4];
      double totalpoint=0;
      double varPa=Double.valueOf(mhtable.getCellValue(4,7)).doubleValue();
      double varW=Double.valueOf(mhtable.getCellValue(4,8)).doubleValue();
      double varma=Double.valueOf(mhtable.getCellValue(4,9)).doubleValue();
      double varmw=Double.valueOf(mhtable.getCellValue(4,10)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,4);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,4,1);}
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
            out.writeChars("Kuru havanin kýsmi basincini giriniz (kPa):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Havanin nem oranini giriniz (kg/kg kh):");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Odada bulunan kuru havanin kütlesini giriniz (kg):");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Odada bulunan nem kütlesini giriniz (kg):");
            useranswer[3]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,4,1);
          mhtable.setCellValue(3,8,String.valueOf(useranswer[0]));
          mhtable.setCellValue(3,9,String.valueOf(useranswer[1]));
          mhtable.setCellValue(3,10,String.valueOf(useranswer[2]));
          mhtable.setCellValue(3,11,String.valueOf(useranswer[3]));
          //
          double upperlimit[]=new double[4];
          double lowerlimit[]=new double[4];
          lowerlimit[0]=varPa*(1-lowerRange);upperlimit[0]=varPa*(1+upperRange);
          lowerlimit[1]=varW*(1-lowerRange);upperlimit[1]=varW*(1+upperRange);
          lowerlimit[2]=varma*(1-lowerRange);upperlimit[2]=varma*(1+upperRange);
          lowerlimit[3]=varmw*(1-lowerRange);upperlimit[3]=varmw*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/4;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,11,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/4;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,11,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/4;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,11,"0.000");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/4;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,11,"0.000");}
          mhtable.setCellValue(4,11,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 4.soruyu zaten cevaplamistiniz. **********");
          pw.println("Kuru havanin kismi basinci icin "+mhtable.getCellValue(3,8)+" girdiniz");
          pw.println("Havanin nem orani icin "+mhtable.getCellValue(3,9)+" girdiniz");
          pw.println("Kuru havanin kütlesi icin "+mhtable.getCellValue(3,10)+" girdiniz");
          pw.println("Nem kütlesi icin "+mhtable.getCellValue(3,11)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(4,11)).doubleValue ();
          pw.println("4. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 4");}
    }
}
