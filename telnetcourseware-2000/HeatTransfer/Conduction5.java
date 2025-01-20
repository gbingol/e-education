import java.io.*;
//
//*********************�LET�M UYGULAMA 11. SORU **********************
//
class Conduction5 extends Questions{
    double R,L,x1,x2,Tdis,Tyuzey,Q,T_yalitkan_curuf;
    private final int rownumber=5;
    private final double SorununNotDegeri=40;
    public Conduction5(){
      super();
      values=new double[6];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Bir k�zg�n buhar borusu R (mm) �ap�nda ve L(m) uzunlugunda olup, iki");
      pw.println("tabaka halinde yal�t�lm��t�r. Birinci tabaka x1(mm) kal�nl���nda yal�tkan");
      pw.println("tuglas�yla (k=0.14 W/m�C), ikinci tabaka x2 (mm) kal�nl�g�nda c�ruf elyafla");
      pw.println("(k=0.06 W/m�C) yal�t�lm��t�r. Boru d�� y�zey metal s�cakl��� Tdis�C, yal�tkan");
      pw.println("y�zey s�cakl�g� Tyuzey olduguna g�re,");
      pw.println("A) Borudan �s� kayb�n� (W)");
      pw.println("B) Yal�tkan tugla ile c�ruf aras�ndaki s�cakl�g� hesaplay�n�z.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          R=(double)(int)(Math.random()*50+75);
          L=(double)(int)(Math.random()*50+50);
          x1=(double)(int)(Math.random()*10+15);  //Yal�tkan tu�la
          x2=(double)(int)(Math.random()*10+35);  //C�ruf elyaf
          Tdis=(double)(int)(Math.random()*200+400);  //Boru d�� y�zey metal s�cakl���
          Tyuzey=(double)(int)(Math.random()*10+25);  //Yal�tkan y�zey s�cakl���
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("R (mm)= "+String.valueOf(R));values[0]=R;
        pw.println("L (m)= "+String.valueOf(L));values[1]=L;
        pw.println("x1 (mm)= "+String.valueOf(x1));values[2]=x1;
        pw.println("x2 (mm)= "+String.valueOf(x2));values[3]=x2;
        pw.println("Tdis�C= "+String.valueOf(Tdis));values[4]=Tdis;
        pw.println("Tyuzey�C= "+String.valueOf(Tyuzey));values[5]=Tyuzey;
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
        R=values[0];pw.println("R (mm)= "+String.valueOf(R));
        L=values[1];pw.println("L (m)= "+String.valueOf(L));
        x1=values[2];pw.println("x1 (mm)="  +String.valueOf(x1));
        x2=values[3];pw.println("x2 (mm)= "+String.valueOf(x2));
        Tdis=values[4];pw.println("Tdis�C= "+String.valueOf(Tdis));
        Tyuzey=values[5];pw.println("Tyuzey�C= "+String.valueOf(Tyuzey));
      }
    }
    protected void CalculateWriteResults(){
      double r1,r2,r3,pay,payda;
      final double k1=0.14,k2=0.06; //k1 (yal�tkan tu�la), k2( c�ruf elyaf)
      r1=R/2;
      r2=r1+x1;
      r3=r2+x2;
      pay=2*Math.PI*L*(Tdis-Tyuzey);
      payda=Math.log(r2/r1)/k1+Math.log(r3/r2)/k2;
      Q=pay/payda;
      //
      T_yalitkan_curuf=Tdis-(Q*Math.log(r2/r1))/(k1*2*Math.PI*L);
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Q));mhtable.setCellNote(rownumber,11,"Borudan olan �s� kayb�");
      mhtable.setCellValue(rownumber,12,String.valueOf(T_yalitkan_curuf));mhtable.setCellNote(rownumber,12,"Yal�tkan tu�lan ile curuf aras�ndaki s�cakl�k");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varQ=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varT_yalitkan_curuf=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Boruda olan isi kaybini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Yal�tkan tugla ile curuf arasindaki sicakligi (�C) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullan�c�n�n toplam isi transferi icin cevabi");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullan�c�n�n yalitkan ile curuf arasindaki sicaklik icin cevabi");
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varQ*(1-lowerRange);upperlimit[0]=varQ*(1+upperRange);
          lowerlimit[1]=varT_yalitkan_curuf*(1-lowerRange);upperlimit[1]=varT_yalitkan_curuf*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[1])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullan�c�n�n bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Isi kaybi i�in "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Yalitkan tugla ile curuf arasindaki sicaklik icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" ald�n�z.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
