import java.io.*;
//
//***************************7th QUESTION*******************************
//
class Question37 extends Questions{
    double T1,V1,P2,V2;
    double h2,h1,hg,hfg,hf,x2;
    public Question37(){
      super();
      values=new double[4];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("T1°C kosullarindaki doymus buhar V1 (m/s) hiz ile cok iyi izole edilmiþ lüleye");
      pw.println("girmektedir.  Lüleyi P2 (kPa) basinc ve V2 (m/s) hiz ile terk etmektedir.  Son");
      pw.println("durumda eger buhar kizgin ise sicakligini, doymus ise kuruluk derecesini saptayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(7,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          T1=(Math.random()*100+100);
          V1=(Math.random()*25+25);
          P2=(Math.random()*100+100);
          V2=Math.random()*100+200; 
          h1=su.Hg(T1);
          h2=h1+V1*V1/(2*1000)-V2*V2/(2*1000);
          hg=su.HgP(P2);
          hf=su.HfP(P2);
          hfg=hg-hf;
          x2=(h2-hf)/hfg;
        }while(!((x2<0.98)&&(x2>0)));
        pw.println("T1°C= "+String.valueOf(T1));values[0]=T1;
        pw.println("V1 (m/s) = "+String.valueOf(V1));values[1]=V1;
        pw.println("P2 (kPa)= "+String.valueOf(P2));values[2]=P2;
        pw.println("V2 (m/s)= "+String.valueOf(V2));values[3]=V2;
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(7,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(7,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(7,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("7. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(7,i+1)).doubleValue();
        }
        T1=values[0];pw.println("T1°C= "+String.valueOf(T1));
        V1=values[1];pw.println("V1 (m/s)= "+String.valueOf(V1));
        P2=values[2];pw.println("P2 (kPa)= "+String.valueOf(P2));
        V2=values[3];pw.println("V2 (m/s)= "+String.valueOf(V2));
      }
    }
    protected void CalculateWriteResults(){
        h1=su.Hg(T1);
        h2=h1+V1*V1/(2*1000)-V2*V2/(2*1000);
        hg=su.HgP(P2);
        hf=su.HfP(P2);
        hfg=hg-hf;
        x2=(h2-hf)/hfg;
        //Record them to MHTable
        mhtable.setCellValue(7,5,String.valueOf(x2));mhtable.setCellNote(7,5,"xe icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varx2=Double.valueOf(mhtable.getCellValue(7,5)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,7);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,7,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      double doublevalueforquestion=10-(intnumberofanswer-1)*10/5;
      String strvalueforquestion=String.valueOf(doublevalueforquestion);
      //
      try{
        if(!isanswered){
          String answer="";
          boolean isaccepted=false;
          do{
          //If user does not enter a value end just presses enter key
          //java throws java.lang.NumberFormatException: empty String
          //and hereby user have the chance not to enter any value and quit this session for
          //further chance of value input
          //
            boolean isKizgin=false;
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+String.valueOf(doublevalueforquestion));
            do{
              //ask user whether her/his answer is superheated or not
              out.writeChars("Buhariniz kizgin mi? [Y/N]:");
              answer=in.readLine().trim().toUpperCase();
              if(answer.equals("Y")) isKizgin=true;
            }while(!(answer.equals("Y")||answer.equals("N")));
            if(isKizgin){
              out.writeChars("Sicakligini giriniz °C:");
              useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            }else{
              out.writeChars("Kuruluk derecesini giriniz:");
              useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            }
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,7,1);
          mhtable.setCellValue(7,6,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          lowerlimit[0]=varx2*(1-lowerRange);upperlimit[0]=varx2*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(7,7,"0.000");}
          //
          mhtable.setCellValue(7,7,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 7.soruyu zaten cevaplamistiniz. **********");
          pw.println("Bu soru icin "+mhtable.getCellValue(7,6)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(7,7)).doubleValue ();
          pw.println("7. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 7");}
    }
}
