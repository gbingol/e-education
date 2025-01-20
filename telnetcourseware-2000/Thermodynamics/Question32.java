import java.io.*;
//
//***************************SECOND QUESTION*******************************
//
class Question32 extends Questions{
    double P1,P2,x,ds;
    public Question32(){
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
      pw.println("P1(kP) ve x kuruluk derecesine sahip su buharý adyabatik olarak P2 (kPa)'a");
      pw.println("kýsýlmaktadýr.  Kinetik ve potansiyel enerjiler ihmal edilecektir. Buna");
      pw.println("göre entropi degisimini (kJ/kg.K) hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(2,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        P1=Math.random()*1000+2000;pw.println("P1 (kPa)= "+String.valueOf(P1));values[0]=P1;
        x=Math.random()*0.04+0.95;pw.println("x = "+String.valueOf(x));values[1]=x;
        P2=Math.random()*50+100; pw.println("P2°C= "+String.valueOf(P2));values[2]=P2;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(2,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(2,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(2,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("2. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(2,i+1)).doubleValue();
        }
        P1=values[0];pw.println("P1 (kPa)= "+String.valueOf(P1));
        x=values[1];pw.println("x= "+String.valueOf(x));
        P2=values[2];pw.println("P2 (kPa)= "+String.valueOf(P2));
      }
    }
    protected void CalculateWriteResults(){
        double hi,he,si,se,Ti,Te;
        Ti=su.T(P1);
        hi=su.Hf(Ti)+x*(su.Hg(Ti)-su.Hf(Ti));
        si=su.Sf(Ti)+x*(su.Sg(Ti)-su.Sf(Ti));
        he=hi;
        double tempT=50, temph=0;
        double XX,YY;
        do{
          tempT=tempT+10;
          temph=kizginSuBuhari.h(P2/1000,tempT);
        }while(!((hi-temph)<20));
        XX=tempT-10;YY=tempT+10;
        do{
          tempT=(XX+YY)/2;
          temph=kizginSuBuhari.h(P2/1000,tempT);
          if((temph-hi)>0) YY=tempT;
          if((temph-hi)<0) XX=tempT;
        }while(!(Math.abs(temph-hi)<0.0001));
        Te=tempT;
        se=kizginSuBuhari.S(P2/1000,Te);
        ds=se-si;
        mhtable.setCellValue(2,4,String.valueOf(ds));
        mhtable.setCellNote(2,4,"ds icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(2,4)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,2);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,2,1);}
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
            out.writeChars("Entropi degisim miktarýný giriniz (kJ/kg.K):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,2,1);
          mhtable.setCellValue(2,5,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(2,6,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(2,6,"0.000");}
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("********** 2.soruyu zaten cevaplamistiniz. **********");
          pw.println("Entropi degisimi icin "+mhtable.getCellValue(2,5)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(2,6)).doubleValue ();
          pw.println("2. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 2");}
    }
}
