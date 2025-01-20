import java.io.*;
//
//***************************5th QUESTION*******************************
//
class Question35 extends Questions{
    double V,P1,P2;
    public Question35(){
      super();
      values=new double[3];
      results=new double[3];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("V(m3) hacime sahip bir tank P1(kPa)'da doymus su buhari icermektedir.");
      pw.println("Sistemden olasi isi transferi nedeniyle eger basinc P2 (kPa)'a düserse,");
      pw.println("A) Karisimin kuruluk derecesini,");
      pw.println("B) En son durumdaki buhar ve sivinin kütlesini hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(5,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          V=(Math.random()*4+1);
          P1=(Math.random()*1000+10);
          P2=P1-(Math.random()*500+50);
        }while(!(P1/P2>1.25));
        pw.println("V (m3)= "+String.valueOf(V));values[0]=V;
        pw.println("P1 (kPa) = "+String.valueOf(P1));values[1]=P1;
        pw.println("P2 (kPa)= "+String.valueOf(P2));values[2]=P2;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(5,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(5,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(5,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("5. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(5,i+1)).doubleValue();
        }
        V=values[0];pw.println("V (m3)= "+String.valueOf(V));
        P1=values[1];pw.println("P1 (kPa)= "+String.valueOf(P1));
        P2=values[2];pw.println("P2 (kPa)= "+String.valueOf(P2));
      }
    }
    protected void CalculateWriteResults(){
        double vf1,vg1,vf2,vg2,m,v1,v2,x2,mv2,mf2;
        vf1=su.VfP(P1);vg1=su.VgP(P1);
        vf2=su.VfP(P2);vg2=su.VgP(P2);
        m=V/vg1;
        v2=v1=vg1;
        x2=(v2-vf2)/(vg2-vf2);
        mv2=x2*m;
        mf2=m-mv2;
        //Record them to MHTable
        mhtable.setCellValue(5,4,String.valueOf(x2));mhtable.setCellNote(5,4,"x icin dogru cevap");
        mhtable.setCellValue(5,5,String.valueOf(mv2));mhtable.setCellNote(5,5,"Buhar kutlesi icin dogru cevap");
        mhtable.setCellValue(5,6,String.valueOf(mf2));mhtable.setCellNote(5,6,"Sivi kutlesi icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[3];
      double totalpoint=0;
      double x=Double.valueOf(mhtable.getCellValue(5,4)).doubleValue();
      double mv=Double.valueOf(mhtable.getCellValue(5,5)).doubleValue();
      double mf=Double.valueOf(mhtable.getCellValue(5,6)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,5);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,5,1);}
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
            out.writeChars("Karisimin kuruluk derecesini giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Son durumdaki buhar kütlesini giriniz (kg):");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Son durumdaki buhar kütlesini giriniz (kg):");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,5,1);
          mhtable.setCellValue(5,7,String.valueOf(useranswer[0]));
          mhtable.setCellValue(5,8,String.valueOf(useranswer[1]));
          mhtable.setCellValue(5,9,String.valueOf(useranswer[2]));
          //
          double upperlimit[]=new double[3];
          double lowerlimit[]=new double[3];
          lowerlimit[0]=x*(1-lowerRange);upperlimit[0]=x*(1+upperRange);
          lowerlimit[1]=mv*(1-lowerRange);upperlimit[1]=mv*(1+upperRange);
          lowerlimit[2]=mf*(1-lowerRange);upperlimit[2]=mf*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/3;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(5,10,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/3;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(5,10,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/3;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(5,10,"0.000");}
          //
          mhtable.setCellValue(5,10,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 5.soruyu zaten cevaplamistiniz. **********");
          pw.println("Karisimin kuruluk derecesi icin "+mhtable.getCellValue(5,7)+" girdiniz");
          pw.println("Son durumdaki buhar kütlesi icin "+mhtable.getCellValue(5,8)+" girdiniz");
          pw.println("Son durumdaki sivi kütlesi icin "+mhtable.getCellValue(5,9)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(5,10)).doubleValue ();
          pw.println("5. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 5");}
    }
}
