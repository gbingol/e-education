import java.io.*;
//
//***************************FIRST QUESTION*******************************
//
class Question31 extends Questions{
    double T1,T2,x;
    public Question31(){
      super();
      values=new double[2];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Freon-12 T1°C'de doymuþ sývý durumunda iken genleþme vanasý yardýmýyla");
      pw.println("T2°C'ye kýsýlmaktadýr. Kýsýlma sonrasý Freon-12'nin kuruluk derecesini");
      pw.println("hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(1,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        T1=Math.random()*20+30;pw.println("T1°C= "+String.valueOf(T1));values[0]=T1;
        T2=Math.random()*10+15; pw.println("T2°C= "+String.valueOf(T2));values[1]=T2;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(1,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(1,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(1,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("1. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(1,i+1)).doubleValue();
        }
        T1=values[0];pw.println("T1°C= "+String.valueOf(T1));
        T2=values[1];pw.println("T2°C= "+String.valueOf(T2));
      }
    }
    protected void CalculateWriteResults(){
        double h1,h2,hfg;
        h1=doymusFreon.Hf(T1);
        h2=doymusFreon.Hf(T2);hfg=doymusFreon.Hg(T2)-h2;
        x=(h1-h2)/hfg;
        mhtable.setCellValue(1,3,String.valueOf(x));
        mhtable.setCellNote(1,3,"x icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      String strx=mhtable.getCellValue(1,3);
      double varx=Double.valueOf(mhtable.getCellValue(1,3)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,1);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,1,1);}
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
            out.writeChars("Kuruluk derecesini giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,1,1);
          mhtable.setCellValue(1,4,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(1,5,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(1,5,"0.000");}
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("********** 1.soruyu zaten cevaplamistiniz. **********");
          pw.println("Kuruluk derecesi için "+mhtable.getCellValue(1,4)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(1,5)).doubleValue ();
          pw.println("1. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 1");}
    }
}
