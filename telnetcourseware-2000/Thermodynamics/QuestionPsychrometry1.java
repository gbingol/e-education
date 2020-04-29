import java.io.*;
//
//*********************HEATING WTIH HUMIDIFICATION (II)**********************
//
class QuestionPsychrometry1 extends Questions{
    double Pw1,RH1,Pws1,W1,mw3,mw1,mw2,W3,Pw3,Pws3,RH3,hw1,hw3,hws2,Q,RH;
		double Tinlet,Texit,Pinlet,RHinlet,Pexit,Rair,Rwater,Twater;
    private final int rownumber=1;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry1(){
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
      pw.println("Kuru hava ve su buharý karýþýmý ýsýtýcý-nemlendirici bir cihaza Tinlet°C,");
      pw.println("Pinlet (kPa) basýnç ve %RHinlet baðýl nemde girmekte ve Texit°C ve Pexit");
      pw.println("(kPa)'da çýkmaktadýr. Kuru havanýn kütlesel debisi Rdryair (kg/s)'dir. ");
      pw.println("Nemlendirme iþlemi için Twater°C'deki su Rwater (kg/s) kütlesel        ");
      pw.println("debisiyle karýþýma püskürtülmektedir.");
      pw.println("A) Çikan havanin bagil nemini [0-1],");
      pw.println("B) Isiticiya verilmesi gerekli isi miktarini bulunuz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Pinlet=(double)(int)(Math.random()*10+95);
          RHinlet=(double)(int)(Math.random()*10+40);
          Rair=(double)(int)(Math.random()*5+10);
          Tinlet=(double)(int)(Math.random()*5+10);
          Texit=(double)(int)(Math.random()*10+20);
          Pexit=Pinlet;
          Twater=Texit;
          Rwater=1/(Math.random()*3+3);
          Pws1=su.P(Tinlet);
          RH1=RHinlet/100;    
		      Pw1=RH1*Pws1;
		      W1=0.622*Pw1/(Pinlet-Pw1);
      		mw3=W1*Rair+Rwater;
      		W3=mw3/Rair;
      		double A=W3/0.622;
      		Pw3=Pinlet*A/(1+A);
      		Pws3=su.P(Texit);
      		RH=Pw3/Pws3;
      		hw1=su.Hg(Tinlet);
      		hw3=su.Hg(Texit);
      		hws2=su.Hf(Twater);
      		Q=(1.005*(Texit-Tinlet)+W3*hw3-W1*hw1-(W3-W1)*hws2)*Rair;
        }while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("Pinlet (kPa)= "+String.valueOf(Pinlet));values[0]=Pinlet;
        pw.println("%RHinlet= "+String.valueOf(RHinlet));values[1]=RHinlet;
        pw.println("Rair (kg/s)= "+String.valueOf(Rair));values[2]=Rair;
        pw.println("Tinlet°C= "+String.valueOf(Tinlet));values[3]=Tinlet;
        pw.println("Twater°C= "+String.valueOf(Twater));values[4]=Twater;
        pw.println("Rwater (kg/s)= "+String.valueOf(Rwater));values[5]=Rwater;
        pw.println("Texit°C= "+String.valueOf(Texit));values[6]=Texit;
        pw.println("Pexit (kPa)= "+String.valueOf(Pexit));values[7]=Pexit;
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
        Pinlet=values[0];pw.println("Pinlet (kPa)= "+String.valueOf(Pinlet));
        RHinlet=values[1];pw.println("%RHinlet= "+String.valueOf(RHinlet));
        Rair=values[2];pw.println("Rair (kg/s)="  +String.valueOf(Rair));
        Tinlet=values[3];pw.println("Tinlet°C= "+String.valueOf(Tinlet));
        Twater=values[4];pw.println("Twater°C= "+String.valueOf(Twater));
        Rwater=values[5];pw.println("Rwater (kg/s)= "+String.valueOf(Rwater));
        Texit=values[6];pw.println("Texit°C= "+String.valueOf(Texit));
        Pexit=values[7];pw.println("Pexit (kPa)= "+String.valueOf(Pexit));
      }
    }
    protected void CalculateWriteResults(){
          Pws1=su.P(Tinlet);
          RH1=RHinlet/100;
		      Pw1=RH1*Pws1;
		      W1=0.622*Pw1/(Pinlet-Pw1);
      		mw3=W1*Rair+Rwater;
      		W3=mw3/Rair;
      		double A=W3/0.622;
      		Pw3=Pinlet*A/(1+A);
      		Pws3=su.P(Texit);
      		RH=Pw3/Pws3;
      		hw1=su.Hg(Tinlet);
      		hw3=su.Hg(Texit);
      		hws2=su.Hf(Twater);
      		Q=(1.005*(Texit-Tinlet)+W3*hw3-W1*hw1-(W3-W1)*hws2)*Rair;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(RH));mhtable.setCellNote(rownumber,11," Çýkan havanýn baðýl nemi");
      mhtable.setCellValue(rownumber,12,String.valueOf(Q));mhtable.setCellNote(rownumber,12," Isýtýcýya verilmesi gerekli ýsý miktarý");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varRH=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varQ=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Çýkan havanýn baðýl nemini giriniz [0-1]: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Isýtýcýya verilmesi gerekli ýsý miktarýný giriniz (kW): ");
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
          lowerlimit[0]=varRH*(1-lowerRange);upperlimit[0]=varRH*(1+upperRange);
          lowerlimit[1]=varQ*(1-lowerRange);upperlimit[1]=varQ*(1+upperRange);
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
          pw.println("Havanin bagil nemi icin"+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Isiticiya verilmesi gerekli isi miktari icin"+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
