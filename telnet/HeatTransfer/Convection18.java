import java.io.*;
//
//*********************Uygulamada
//
class Convection18 extends Questions{
    double Epsilon,Toda,Tyuzey,h,Thava_gercek;
    private final int rownumber=4;
    private final double SorununNotDegeri=10;
    public Convection18(){
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
      pw.println(" Cival� termometrenin yay�n�m katsay�s� Epsilon olup, metal bir odada ");
      pw.println("sicakligi Toda�C olarak g�stermektedir. Odan�n duvarlar� zay�f ");
      pw.println("izolasyonlu olup y�zey s�cakl��� Tyuzey �C�dir. Oda havas�yla  ");
      pw.println("termometre aras�ndaki h (W/m2�C) (do�al ta��n�m) olarak al�nabilmektedir. ");
      pw.println("Ger�ek hava s�cakl���n� hesaplay�n�z. ");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double Tt,Ts,Temp1,Temp2,fark;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Epsilon=Math.random() * 0.25 + 0.7;
          Toda= (double)(int)(Math.random()* 7 + 18);
          Tyuzey=(double)(int)(Math.random()* 5 + 5);
          h=(double)(int)(Math.random()*5+5);
          Tt = Toda+ 273.15;
          Ts = Tyuzey+ 273.15;
          Temp1 = (5.669 *Math.pow(10,-8)*Epsilon*(Math.pow(Tt,4) - Math.pow(Ts,4)));
          Temp2 = Temp1 / h;
          Thava_gercek= Tt + Temp2;
          fark= Thava_gercek- 273.15-Toda;
        }while(!(fark>5));
        //
        pw.println("Epsilon= "+String.valueOf(Epsilon));values[0]=Epsilon;
        pw.println("Toda�C= "+String.valueOf(Toda));values[1]=Toda;
        pw.println("Tyuzey�C= "+String.valueOf(Tyuzey));values[2]=Tyuzey;
        pw.println("h (W/m2�C)= "+String.valueOf(h));values[3]=h;
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
        Epsilon=values[0];pw.println("Epsilon= "+String.valueOf(Epsilon));
        Toda=values[1];pw.println("Toda�C= "+String.valueOf(Toda));
        Tyuzey=values[2];pw.println("Tyuzey�C= "+String.valueOf(Tyuzey));
        h=values[3];pw.println("h (W/m2�C)= "+String.valueOf(h));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Thava_gercek));mhtable.setCellNote(rownumber,11,"Havanin gercek sicakligi icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varThava_gercek=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Havanin gercek sicakligini (K) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullan�c�n�n havanin gercek sicakligi icin cevabi");
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varThava_gercek*(1-lowerRange);upperlimit[0]=varThava_gercek*(1+upperRange);
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
          pw.println("Havanin gercek sicakligi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" ald�n�z.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
