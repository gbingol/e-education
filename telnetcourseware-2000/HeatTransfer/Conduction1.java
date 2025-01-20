import java.io.*;
//
//********************* �LET�M UYGULAMA 1. SORU **********************
//
class Conduction1 extends Questions{
    double x,T1,T2,q,k;
    private final int rownumber=1;
    private final double SorununNotDegeri=10;
    public Conduction1(){
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
      pw.println(" x (cm) kalinligindaki bakir levhanin bir y�z� T1�C'de, di�er y�z� T2�C'de");
      pw.println("sabit tutulmaktadir. Levha boyunca transfer edilen isiyi (MW/m2) hesaplayaniz.");
      pw.println("Levhanin isi iletim katsay�s� k (W/m�C)'dir.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          x=(double)(int)(Math.random()*5+2);
          T1=(double)(int)(Math.random()*250+250);
          T2=T1-50-(double)(int)(Math.random()*100);
          k=(double)(int)(Math.random()*20+360);   //Bak�r i�in se�ilmi�tir.
          q=k*(T1-T2)/(x*0.01);  //W
          q=q/1000000;  //MW
        }while(!(q>1));
        //
        pw.println("x (cm)= "+String.valueOf(x));values[0]=x;
        pw.println("T1C= "+String.valueOf(T1));values[1]=T1;
        pw.println("T2C= "+String.valueOf(T2));values[2]=T2;
        pw.println("k (W/mC)= "+String.valueOf(k));values[3]=k;
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
        x=values[0];pw.println("x (cm) = "+String.valueOf(x));
        T1=values[1];pw.println("T1�C= "+String.valueOf(T1));
        T2=values[2];pw.println("T2�C="  +String.valueOf(T2));
        k=values[3];pw.println("k (W/m�C)= "+String.valueOf(k));
      }
    }
    protected void CalculateWriteResults(){
      double varq;
      varq=q;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(varq));mhtable.setCellNote(rownumber,11,"Levha boyunca transfer edilen �s�");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varq=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama say�s�na g�re soru de�erini hesapla
      double doublevalueforquestion=SorununNotDegeri-(intnumberofanswer-1)*SorununNotDegeri/5;
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
            out.writeChars("Levha boyunca transfer edilen isi miktar�n� giriniz (MW/m2): ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullan�c�n�n girdi�i de�er");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varq*(1-lowerRange);upperlimit[0]=varq*(1+upperRange);
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
          pw.println("Transfer edilen �s� miktar� i�in "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" ald�n�z.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
