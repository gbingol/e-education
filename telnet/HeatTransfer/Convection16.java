import java.io.*;
//
//*********************Uygulamada Ta��n�m 2. soru**********************
//
class Convection16 extends Questions{
    double e1,e2,e3,Azalma;
    private final int rownumber=2;
    private final double SorununNotDegeri=20;
    public Convection16(){
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
      pw.println(" �ki paralel levha g�z �n�ne alal�m. Birinci levhan�n s�cakl��� T1 (K) ");
      pw.println("ve yay�n�m� e1  ; ikincinin s�cakl��� T2 (K) ve yay�n�m� e2 olmaktad�r. ");
      pw.println("Yay�n�m� e3 olan bir al�minyum ���n�m kalkan� levhalar aras�na yerle�tirilmi�tir.");
      pw.println("Bu durumda ger�ekle�en �s� transferi miktar�ndaki azalmay� hesaplay�n�z.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double R1,Q0,R2,q1;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            e1 =Math.random() * 1 + 0.1;
            e2 =Math.random() * 1 + 0.1;
            e3 =Math.random() * 0.5 + 0.01;
            R1 = (1/e1 + 1/e2 - 1);
            Q0 = 1 / R1;
            R1 = (1/e1 + 1/e3 - 1);
            R2 = (1/e2 + 1/e3 - 1);
            q1 = 1/(R1 + R2);
            Azalma = (Q0 - q1) / Q0*100;
        }while(!(e1<1 && e1>0.6 && e2<1 && e2> 0.6 && e3<0.1));
        //
        pw.println("e1= "+String.valueOf(e1));values[0]=e1;
        pw.println("e2= "+String.valueOf(e2));values[1]=e2;
        pw.println("e3= "+String.valueOf(e3));values[2]=e3;
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
        pw.println(String.valueOf(rownumber)+". Soru Icin Degerlerinizi zaten alm��t�n�z.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(rownumber,i+1)).doubleValue();
        }
        e1=values[0];pw.println("e1= "+String.valueOf(e1));
        e2=values[1];pw.println("e2= "+String.valueOf(e2));
        e3=values[2];pw.println("e3= "+String.valueOf(e3));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Azalma));mhtable.setCellNote(rownumber,11,"Azalma icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varAzalma=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama say�s�na g�re soru de�erini hesapla
      //
      //double doublevalueforquestion=SorununNotDegeri-((intnumberofanswer-1)/5)*SorununNotDegeri;
      //yap�ld���nda doublevalueforquestion de�eri hep 10 ��kmakta ��nk� parantez i�indeki de�er
      //integer say�l�p b�l�m 0.xx ��k�nca s�f�r yap�lmakta
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
            out.writeChars("Isi transferi miktar�ndaki % azalmayi giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"%Azalma icin kullanici cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varAzalma*(1-lowerRange);upperlimit[0]=varAzalma*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullan�c�n�n bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("%Azalma icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" ald�n�z.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
    //
}
