import java.io.*;
//
//*********************
//
class Convection17 extends Questions{
    double Tgokyuzu,Tortam,h,Epsilon,Tsu_denge;
    private final int rownumber=3;
    private final double SorununNotDegeri=25;
    public Convection17(){
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
      pw.println("  Ince bir su tabakasý colde acik bir gokyuzune bakmaktadýr.Efektif gokyuzu ");
      pw.println("sýcakligi Tgokyuzu K olarak alýnabilmektedir. Ortam sýcaklýðý Tortam K’de ");
      pw.println("bulunmaktadýr. Taþýným ýsý transfer katsayýsý h (W/m2°C) olup ");
      pw.println("suyun uzun dalga boyu ýþýnýmý için yayýným ve yutma katsayýsý Epsilon=Alfa'dir.");
      pw.println("Suyun denge sicakligini hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double Temp1,Temp2,Talt,Tust,Tort=0,Denklem=0,DenklemAlt,DenklemUst;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            Tgokyuzu = (double)(int)(Math.random()* 50 + 220);
            Tortam= (double)(int)(Math.random()*30 + 20) + Tgokyuzu;
            h=(double)(int)(Math.random()*5 + 5);
            Epsilon=Math.random()*0.3 + 0.7;
            Temp1 = Epsilon* 5.67 * 1 / Math.pow(10,8);
            Temp2 = h* Tortam+ Temp1 *Math.pow(Tgokyuzu,4);
            Talt = Tgokyuzu; Tust = Tortam;
            //Kesen Yöntemiyle denklemin kökü bulunuyor
            //Bu yöntemde alt ve üst sýnýrlar denklemde yerine konur
            //altdenklem*üstdenklem in 0 dan büyük olup olmadýðý kontrol edilir
            do{
                DenklemAlt = Temp1 * Math.pow(Talt,4) + h* Talt- Temp2;
                DenklemUst = Temp1 * Math.pow(Tust,4) + h* Tust - Temp2;
                if (DenklemAlt * DenklemUst < 0){
                  Tort = (Talt + Tust) / 2;
                  Denklem = Temp1 * Math.pow(Tort,4)+h* Tort - Temp2;
                  if (Denklem < 0){
                    Talt = Tort;
                  }
                  else{
                    Tust = Tort;
                  }
                }
                else if (DenklemAlt * DenklemUst > 0){
                  Tort = (Talt + Tust) / 2;
                  Denklem = Temp1 * Math.pow(Tort,4) + h* Tort - Temp2;
                  if(Denklem > 0){
                    Talt = Tort;
                  }
                  else{
                        Tust = Tort;
                  }
                }
            }while(!(Math.abs(Denklem) < 0.001));
            Tsu_denge=Tort;
        }while(!(Epsilon < 0.95));
        //
        pw.println("Tgokyuzu (K)= "+String.valueOf(Tgokyuzu));values[0]=Tgokyuzu;
        pw.println("Tortam (K)= "+String.valueOf(Tortam));values[1]=Tortam;
        pw.println("h (W/m2°C)= "+String.valueOf(h));values[2]=h;
        pw.println("Epsilon= "+String.valueOf(Epsilon));values[3]=Epsilon;
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
        pw.println(String.valueOf(rownumber)+". Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(rownumber,i+1)).doubleValue();
        }
        Tgokyuzu=values[0];pw.println("Tgokyuzu (K)= "+String.valueOf(Tgokyuzu));
        Tortam=values[1];pw.println("Tortam (K)= "+String.valueOf(Tortam));
        h=values[2];pw.println("h (W/m2°C)= "+String.valueOf(h));
        Epsilon=values[3];pw.println("Epsilon= "+String.valueOf(Epsilon));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Tsu_denge));mhtable.setCellNote(rownumber,11,"Suyun Denge sicakligi icin sistem cevabi");
    }
    //
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varTsu_denge=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama sayýsýna göre soru deðerini hesapla
      //
      //double doublevalueforquestion=SorununNotDegeri-((intnumberofanswer-1)/5)*SorununNotDegeri;
      //yapýldýðýnda doublevalueforquestion deðeri hep 10 çýkmakta çünkü parantez içindeki deðer
      //integer sayýlýp bölüm 0.xx çýkýnca sýfýr yapýlmakta
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
            out.writeChars("Suyun denge sicakligini (K) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Suyun denge sicakligi icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varTsu_denge*(1-lowerRange);upperlimit[0]=varTsu_denge*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Suyun denge sicakligi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
