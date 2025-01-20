import java.io.*;
//
//*********************AIR CONDITIONING**********************
//
class QuestionPsychrometry3 extends Questions{
    double P,Tdb,RH,Rhava,Pfan;
    double Pws,Pw,Pa,ma,mw,Tcikis;
    private final int rownumber=3;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry3(){
      super();
      values=new double[5];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Havanýn Tdb°C kuru termometre sýcaklýðý ve %RH baðýl nemde bir odaya ");
      pw.println("verilmesi gerekmektedir. Hesaplamalar odaya verilen havanýn kütlesel");
      pw.println("debisi Rhava (kg/s) baz alýnarak yapýlacaktýr ve bu iþ için kullanýlan ");
      pw.println("fanýn gücü Pfan (kW)'dýr. Havanýn ýsýtýcýdan çýkýþ sýcaklýðýný hesaplayýnýz. ");
      pw.println("Ýþletme boyunca hava basýncýnýn P (kPa)'da sabit kaldýðýný kabul ediniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          P=(double)(int)(Math.random()*10+95);
          Tdb=(double)(int)(Math.random()*10+15);
          RH=(double)(int)(Math.random()*20+40);
          Rhava=(double)(int)(Math.random()*5+1);
          Pfan=(double)(int)(Math.random()*3+1);
          Pws=su.P(Tdb);
          Pw=Pws*RH/100;
          Pa=P-Pw;
          ma=(Pa*Rhava)/(0.287*(Tdb+273.15));
          mw=(Pw*Rhava)/(0.4618*(Tdb+273.15));
          Tcikis=Tdb-Pfan/(ma*1.005+1.86*mw);
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("P (kPa)= "+String.valueOf(P));values[0]=P;
        pw.println("Tdb°C= "+String.valueOf(Tdb));values[1]=Tdb;
        pw.println("%RH= "+String.valueOf(RH));values[2]=RH;
        pw.println("Rhava (kg/s)= "+String.valueOf(Rhava));values[3]=Rhava;
        pw.println("Pfan (kW)= "+String.valueOf(Pfan));values[4]=Pfan;
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
        P=values[0];pw.println("P (kPa)= "+String.valueOf(P));
        Tdb=values[1];pw.println("Tdb°C= "+String.valueOf(Tdb));
        RH=values[2];pw.println("%RH="  +String.valueOf(RH));
        Rhava=values[3];pw.println("Rhava (kg/s)= "+String.valueOf(Rhava));
        Pfan=values[4];pw.println("Pfan (kW)= "+String.valueOf(Pfan));
      }
    }
    protected void CalculateWriteResults(){
          Pws=su.P(Tdb);
          Pw=Pws*RH/100;
          Pa=P-Pw;
          ma=(Pa*Rhava)/(0.287*(Tdb+273.15));
          mw=(Pw*Rhava)/(0.4618*(Tdb+273.15));
          Tcikis=Tdb-Pfan/(ma*1.005+1.86*mw);
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Tcikis));mhtable.setCellNote(rownumber,11,"Çýkan havanýn sýcaklýðý");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varT=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
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
            out.writeChars("Çýkan havanýn sicakligini giriniz (°C): ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degeri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varT*(1-lowerRange);upperlimit[0]=varT*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,16,"Kullanicinin Son Sicaklik icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Çýkýþ sýcaklýðý için "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
