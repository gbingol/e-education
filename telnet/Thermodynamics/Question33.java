import java.io.*;
//
//***************************THIRD QUESTION*******************************
//
class Question33 extends Questions{
    double P,T,Q,ds;
    public Question33(){
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
      pw.println("P (kPa), T°C kosullarýndaki su sabit basýnçta ýsýtýlmaktadýr. Isý transferi");
      pw.println("miktarý Q (MJ/kg) ise akýþkan baþýna düþen entropi deðiþimini hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(3,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
      double h2=0,temph=0;
        do{
          P=Math.random()*100+150;pw.println("P (kPa)= "+String.valueOf(P));values[0]=P;
          T=Math.random()*25+25;pw.println("T°C = "+String.valueOf(T));values[1]=T;
          Q=Math.random()*1+1; pw.println("Q (MJ/kg)= "+String.valueOf(Q));values[2]=Q;
          h2=su.Hf(T)+Q*1000;
          temph=su.HgP(P);
        }while(!(h2<temph));
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(3,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(3,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(3,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("3. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(3,i+1)).doubleValue();
        }
        P=values[0];pw.println("P (kPa)= "+String.valueOf(P));
        T=values[1];pw.println("T°C= "+String.valueOf(T));
        Q=values[2];pw.println("Q (MJ/kg)= "+String.valueOf(Q));
      }
    }
    protected void CalculateWriteResults(){
        double s1,h1,q,h2,p2,hf,hg,x2,s2,sf,sfg;
        double Tx=su.T(P);
        s1=su.Sf(T);h1=su.Hf(T);
        h2=h1+Q*1000;
        x2=(h2-su.HfP(P))/(su.HgP(P)-su.HfP(P));
        s2=su.Sf(Tx)+x2*(su.Sg(Tx)-su.Sf(Tx));
        ds=s2-s1;
        mhtable.setCellValue(3,4,String.valueOf(ds));
        mhtable.setCellNote(3,4,"ds icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(3,4)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,3);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,3,1);}
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
          updateCell(11,3,1);
          mhtable.setCellValue(3,5,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(3,6,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(3,6,"0.000");}
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("********** 3.soruyu zaten cevaplamistiniz. **********");
          pw.println("Entropi degisimi icin "+mhtable.getCellValue(3,5)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(3,6)).doubleValue ();
          pw.println("3. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 3");}
    }
}
