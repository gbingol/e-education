import java.io.*;
//
//***************************6th QUESTION*******************************
//
class Question36 extends Questions{
    double z,P1,P2,Q;
    public Question36(){
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
      pw.println("Doymus buhar ile isitilacak olan z(m) yüksekligindeki binanin zemin katinda");
      pw.println("bulunan kalorifer tesisatinda buhar P1(kPa)'da doymus halde girmektedir. Binanin");
      pw.println("Binanin ust katinda ise basinc P2 (kPa) olarak olculmus ve yapilan hesaplamalar");
      pw.println("sonucunda, en alt kattan ust kata toplam boru boyunca Q(kJ/kg) isi kaybi ortaya");
      pw.println("ciktigi saptanmistir. Binanin en ust katindaki buharin kalitesi ne olacaktir.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(6,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          z=(Math.random()*25+10);
          P1=(Math.random()*1000+100);
          P2=P1-(Math.random()*500+50);
          Q=Math.random()*50+50;
        }while(!(P1/P2>1.25));
        pw.println("z (m)= "+String.valueOf(z));values[0]=z;
        pw.println("P1 (kPa) = "+String.valueOf(P1));values[1]=P1;
        pw.println("P2 (kPa)= "+String.valueOf(P2));values[2]=P2;
        pw.println("Q (kJ/kg)= "+String.valueOf(Q));values[3]=Q;
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(6,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(6,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(6,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("6. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(6,i+1)).doubleValue();
        }
        z=values[0];pw.println("z (m)= "+String.valueOf(z));
        P1=values[1];pw.println("P1 (kPa)= "+String.valueOf(P1));
        P2=values[2];pw.println("P2 (kPa)= "+String.valueOf(P2));
        Q=values[3];pw.println("Q (kJ/kg)= "+String.valueOf(Q));
      }
    }
    protected void CalculateWriteResults(){
        double hi,PEi,PEe,xe,he;
        hi=su.HgP(P1);
        he=hi+(0-z)*9.807/1000.0-Q;
        double hf=su.HfP(P2);
        double hg=su.HgP(P2);
        double hfg=hg-hf;
        xe=(he-hf)/hfg;
        //Record them to MHTable
        mhtable.setCellValue(6,5,String.valueOf(xe));mhtable.setCellNote(6,5,"xe icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(6,5)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,6);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,6,1);}
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
            out.writeChars("Buharin kalitesini giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,6,1);
          mhtable.setCellValue(6,6,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(6,7,"0.000");}
          //
          mhtable.setCellValue(6,7,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 6.soruyu zaten cevaplamistiniz. **********");
          pw.println("Karisimin kuruluk derecesi icin "+mhtable.getCellValue(6,6)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(6,7)).doubleValue ();
          pw.println("6. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 6");}
    }
}
