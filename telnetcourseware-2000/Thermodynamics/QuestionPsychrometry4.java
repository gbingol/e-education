import java.io.*;
//
//*********************COOLING TOWER FOR A POWER PLANT**********************
//
class QuestionPsychrometry4 extends Questions{
    double Pwsgiren,Pwgiren,wgiren,Pwscikan,Pwcikan,wcikan,hf1,hf2,hf3,hvgiren,hvcikan,ma,mw,mhava;
		double Rwater,Tinitial,Tfinal,Tinletair,RHinletair,Tleavingair,RHleavingair,P,Tmakeup;
    private final int rownumber=4;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry4(){
      super();
      values=new double[8];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Bir güç tesisinde Rwater (kg/s) debisindeki suyun Tinitial°C'den Tfinal°C'ye");
      pw.println("soðutulmasý gerekmektedir. Kuleye giren hava Tinletair°C sýcaklýk ve      ");
      pw.println("%RHinletair baðýl neminde olup, kuleyi Tleavingair°C ve %RHleavingair baðýl");
      pw.println("nemde terk ettiði söylenebilir. Tamamlama suyunun sýcaklýðý Tmake-up°C'dir.");
      pw.println("debisiyle karýþýma püskürtülmektedir. Havanýn basýncýnýn P (kPa) olduðunu");
      pw.println("kabul ediniz.");
      pw.println("A) Tamamlama suyunun kütlesel debisini (kg/s)");
      pw.println("B) Havanýn kütlesel debisini (kg/s) bulunuz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          P=(double)(int)(Math.random()*10+95);
          Rwater=(double)(int)(Math.random()*20+40);
          Tinitial=(double)(int)(Math.random()*15+30);
          Tfinal=(double)(int)(Math.random()*10+15);
          Tinletair=(double)(int)(Math.random()*5+10);
          RHinletair=(double)(int)(Math.random()*10+35);
          Tleavingair=(double)(int)(Tfinal-Math.random()*5);
          RHleavingair=(double)(int)(Tinitial-Math.random()*5);
          Tmakeup=Tinitial;
          Pwsgiren=su.P(Tinletair);
          Pwgiren=Pwsgiren*RHinletair/100;
          wgiren=0.622*Pwgiren/(P-Pwgiren);
          Pwscikan=su.P(Tleavingair);
          Pwcikan=Pwscikan*RHleavingair/100;
          wcikan=0.622*Pwcikan/(P-Pwcikan);
          hf1=su.Hf(Tinitial);hf2=su.Hf(Tfinal);hf3=su.Hf(Tinitial);
          hvgiren=2501+1.863*Tinletair;
          hvcikan=2501+1.863*Tleavingair;
          ma=Rwater*(hf2-hf1)/(1.005*(Tinletair-Tleavingair)+wgiren*hvgiren-wcikan*hvcikan+(wcikan-wgiren)*hf3);
          mw=ma*(wcikan-wgiren);
          mhava=ma*(1+wgiren);
        }while(!((mhava>40)&&(mw>0.5)));
        //
        pw.println("P= "+String.valueOf(P));values[0]=P;
        pw.println("Rwater (kg/s)= "+String.valueOf(Rwater));values[1]=Rwater;
        pw.println("Tinitial°C= "+String.valueOf(Tinitial));values[2]=Tinitial;
        pw.println("Tfinal°C= "+String.valueOf(Tfinal));values[3]=Tfinal;
        pw.println("Tinletair°C= "+String.valueOf(Tinletair));values[4]=Tinletair;
        pw.println("%RHinletair= "+String.valueOf(RHinletair));values[5]=RHinletair;
        pw.println("Tleavingair°C= "+String.valueOf(Tleavingair));values[6]=Tleavingair;
        pw.println("%RHleavingair= "+String.valueOf(RHleavingair));values[7]=RHleavingair;
         pw.println("Tmakeup°C= "+String.valueOf(Tmakeup));values[7]=Tmakeup;
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
        Rwater=values[1];pw.println("Rwater (kg/s)= "+String.valueOf(Rwater));
        Tinitial=values[2];pw.println("Tinitial°C="  +String.valueOf(Tinitial));
        Tfinal=values[3];pw.println("Tfinal°C= "+String.valueOf(Tfinal));
        Tinletair=values[4];pw.println("Tinletair°C= "+String.valueOf(Tinletair));
        RHinletair=values[5];pw.println("%RHinletair= "+String.valueOf(RHinletair));
        Tleavingair=values[6];pw.println("Tleavingair°C= "+String.valueOf(Tleavingair));
        RHleavingair=values[7];pw.println("%RHleavingair= "+String.valueOf(RHleavingair));
        Tmakeup=values[7];pw.println("Tmakeup°= "+String.valueOf(Tmakeup));
      }
    }
    protected void CalculateWriteResults(){
          double varmw=0,varmhava=0;
          varmw=mw;
          varmhava=mhava;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(varmw));mhtable.setCellNote(rownumber,11,"Tamamlama suyunun kütlesel debisi");
      mhtable.setCellValue(rownumber,12,String.valueOf(varmhava));mhtable.setCellNote(rownumber,12,"Kuleye giren havanýn kütlesel debisi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varmw=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varmhava=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Tamamlama suyunun kütlesel debisini (kg/s) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Kuleye giren havanin kütlesel debisini (kg/s) giriniz: ");
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
          lowerlimit[0]=varmw*(1-lowerRange);upperlimit[0]=varmw*(1+upperRange);
          lowerlimit[1]=varmhava*(1-lowerRange);upperlimit[1]=varmhava*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,16,"Kullanicinin Baðýl Nem icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,17,"Kullanicinin Gerekli Isý Miktarý icin cevabi");
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
          pw.println("Tamamlama suyunun kütlesel debisi icin"+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Kuleye giren havanýn kütlesel debisi icin"+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
