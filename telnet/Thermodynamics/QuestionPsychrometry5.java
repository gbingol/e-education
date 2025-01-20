import java.io.*;
//
//*********************ADIABATIC SATURATION PROCESS**********************
//
class QuestionPsychrometry5 extends Questions{
    double P,T,Tdoyma;
    double RH2,Pws2,Pw2,w2,hw1,hws2,hfg,w1,Pw1,Pws1,RH1;
    private final int rownumber=5;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry5(){
      super();
      values=new double[3];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Bir adyabatik doyurma iþleminde kuru hava + su buharý uzun ve izolasyonlu");
      pw.println("adyabatik doyurma kanalýna P (kPa) basýnç ve T°C'de girip Tdoyma°C doyma");
      pw.println("sýcaklýðýnda çýkmaktadýr. Karýþýmýn kanala giriþteki mutlak ve baðýl nemini");
      pw.println("hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          P=(double)(int)(Math.random()*10+95);
          T=(double)(int)(Math.random()*10+25);
          Tdoyma=(double)(int)(Math.random()*10+10);
          RH2=1;
          Pws2=su.P(Tdoyma);
          Pw2=Pws2;
          w2=0.622*Pw2/(P-Pw2);
          hw1=su.Hg(T);hws2=su.Hf(Tdoyma);hfg=su.Hg(Tdoyma)-su.Hf(Tdoyma);
          w1=(1.005*(Tdoyma-T)+w2*hfg)/(hw1-hws2);
          Pw1=P*w1/(w1+0.622);
          Pws1=su.P(T);
          RH1=Pw1/Pws1;
        }while(!((RH1>0.2)&&(RH1<0.6)));
        //
        pw.println("P (kPa)= "+String.valueOf(P));values[0]=P;
        pw.println("T°C= "+String.valueOf(T));values[1]=T;
        pw.println("Tdoyma°C= "+String.valueOf(Tdoyma));values[2]=Tdoyma;
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
        T=values[1];pw.println("T°C= "+String.valueOf(T));
        Tdoyma=values[2];pw.println("Tdoyma°C="  +String.valueOf(Tdoyma));
      }
    }
    protected void CalculateWriteResults(){
          double varw1=0,varRH1=0;
          varw1=w1;
          varRH1=RH1;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(varw1));mhtable.setCellNote(rownumber,11,"Giren havanýn mutlak nemi");
      mhtable.setCellValue(rownumber,12,String.valueOf(varRH1));mhtable.setCellNote(rownumber,12,"Giren havanýn bagil nemi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varw1=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varRH1=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Giren havanin mutlak nemini giriniz (kg/kg kh): ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Giren havanýn bagil nemini [0-1] giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varw1*(1-lowerRange);upperlimit[0]=varw1*(1+upperRange);
          lowerlimit[1]=varRH1*(1-lowerRange);upperlimit[1]=varRH1*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,16,"Kullanicinin Son Sicaklik icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,17,"Kullanicinin Son Sicaklik icin cevabi");
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
          pw.println("Giris mutlak nemi için "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Giris bagil nemi için "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
