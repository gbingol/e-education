import java.io.*;
//
//***************************10th QUESTION*******************************
//
class Question310 extends Questions{
    double a,b,c,T,P;
    public Question310(){
      super();
      values=new double[5];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Boyutlari a(m)*b(m)*c(m) olan T°C ve P(kPa) kosullarindaki odada bulunan ");
      pw.println("havanin yogunlugu (kg/m3) ve ozgul hacmi (m3/kg) nedir?");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(10,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          a=Math.random()*4+1;
          b=Math.random()*4+1;
          c=Math.random()*4+1;
          T=Math.random()*10+20;
          P=Math.random()*10+95;
        //}while(!((T2b/T1b>1.25)&&(T2>10)));
        pw.println("a (m)= "+String.valueOf(a));values[0]=a;
        pw.println("b (m)= "+String.valueOf(b));values[1]=b;
        pw.println("c (m)= "+String.valueOf(c));values[2]=c;
        pw.println("T°C= "+String.valueOf(T));values[3]=T;
        pw.println("P (kPa)= "+String.valueOf(P));values[4]=P;
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(10,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(10,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(10,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("10. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(10,i+1)).doubleValue();
        }
        a=values[0];pw.println("a (m)= "+String.valueOf(a));
        b=values[1];pw.println("b (m)= "+String.valueOf(b));
        c=values[2];pw.println("c (m)= "+String.valueOf(c));
        T=values[3];pw.println("T°C= "+String.valueOf(T));
        P=values[4];pw.println("P (kPa)= "+String.valueOf(P));
      }
    }
    protected void CalculateWriteResults(){
        double V,m,R;
        V=a*b*c;
        R=0.287; // kPa*m3/kg.K
        m=(P*V)/(R*(T+273.15));
        double Ro=m/V;
        double ozgulhacim=1/Ro;
        //Record them to MHTable
        mhtable.setCellValue(10,6,String.valueOf(Ro));mhtable.setCellNote(10,6,"Havanin yogunlugu icin dogru cevap");
        mhtable.setCellValue(10,7,String.valueOf(ozgulhacim));mhtable.setCellNote(10,7,"Havanin ozgul hacmi icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varRo=Double.valueOf(mhtable.getCellValue(10,6)).doubleValue();
      double varozgulhacim=Double.valueOf(mhtable.getCellValue(10,7)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,10);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,10,1);}
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
            out.writeChars("Havanin yogunlugunu (kg/m3) giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Havanin ozgul hacmini (m3/kg) giriniz.");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,9,1);
          mhtable.setCellValue(10,8,String.valueOf(useranswer[0]));mhtable.setCellNote(10,8,"Kullanicinin havanin yogunlugu icin cevabi");
          mhtable.setCellValue(10,9,String.valueOf(useranswer[1]));mhtable.setCellNote(10,9,"Kullanicinin havanin ozgul hacmi icin cevabi");
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          lowerlimit[0]=varRo*(1-lowerRange);upperlimit[0]=varRo*(1+upperRange);
          lowerlimit[1]=varozgulhacim*(1-lowerRange);upperlimit[1]=varozgulhacim*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/2;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(10,10,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/2;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(10,10,"0.000");}
          //
          mhtable.setCellValue(10,10,String.valueOf(totalpoint));
          mhtable.setCellNote(10,10,"Kullanicinin bu sorudan aldigi not");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 10.soruyu zaten cevaplamistiniz. **********");
          pw.println("Havanin yogunlugu icin "+mhtable.getCellValue(10,8)+" girdiniz");
          pw.println("Havanin ozgul hacmi icin "+mhtable.getCellValue(10,9)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(10,10)).doubleValue ();
          pw.println("10. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 10");}
    }
}
