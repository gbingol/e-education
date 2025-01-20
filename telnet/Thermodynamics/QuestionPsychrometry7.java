import java.io.*;
//
//*********************ADIABATIC MIXING OF AIRSTREAMS**********************
//
class QuestionPsychrometry7 extends Questions{
    double h1,w1,v1,V1,T1,ma1,h2,w2,v2,T2,V2,RH2,ma2;
    double ma3,w3,h3,T3,RH3,v3,V3,P;
    double Pws,Pw;
    private final int rownumber=7;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry7(){
      super();
      values=new double[6];
      results=new double[4];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("V1 (m3/dak) debisindeki ve T1°C sýcaklýktaki doymuþ hava ile V2 (m3/min)");
      pw.println("T2°C sýcaklýk ve %RH2 baðýl nemdeki hava ile karýþtýrýlmaktadýr. Prosesin");
      pw.println("P (kPa) basýnçta olduðunu kabul ederek, karýþýmýn mutlak nemini, baðýl nemini,");
      pw.println("kuru termometre sýcaklýðýný ve hacimsel debisini hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
    			T1=(double)(int)(Math.random()*5+10);
    			V1=(double)(int)(Math.random()*20+25);
    			T2=(double)(int)(Math.random()*10+25);
    			RH2=(double)(int)(Math.random()*25+45);
    			V2=(double)(int)(Math.random()*10+15);
    			P=(double)(int)(Math.random()*10+95);		//kPa olarak okunuyor
    			Pws=pf.Pws(T1);
    			Pw=Pws;		//Doymuþ hava
    			w1=pf.W2(P*1000,Pw);
    			h1=pf.H(T1,w1);
    			v1=287.055*(T1+273.15)*(1+1.6078*w1)/(P*1000);
    			Pws=pf.Pws(T2);
    			Pw=Pws*RH2/100;
    			w2=pf.W2(P*1000,Pw);
    			h2=pf.H(T2,w2);
    			v2=287.055*(T2+273.15)*(1+1.6078*w2)/(P*1000);
    			ma1=V1/v1;
    			ma2=V2/v2;
    			ma3=ma1+ma2;
    			w3=(w2*ma2+w1*ma1)/(ma1+ma2);
    			h3=(h2*ma2+h1*ma1)/(ma1+ma2);
    			T3=pf.T2(h3,w3);
    			Pws=pf.Pws(T3);
    			Pw=pf.Pw2(P*1000,w3);
    			RH3=Pw/Pws;
    			v3=287.055*(T3+273.15)*(1+1.6078*w3)/(P*1000);
    			V3=v3*ma3;
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("T1°C= "+String.valueOf(T1));values[0]=T1;
        pw.println("V1 (m3/dak)= "+String.valueOf(V1));values[1]=V1;
        pw.println("T2°C= "+String.valueOf(T2));values[2]=T2;
        pw.println("%RH2= "+String.valueOf(RH2));values[3]=RH2;
        pw.println("V2 (m3/dak)= "+String.valueOf(V2));values[4]=V2;
        pw.println("P (kPa)= "+String.valueOf(P));values[5]=P;
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
        T1=values[0];pw.println("T1°C= "+String.valueOf(T1));
        V1=values[1];pw.println("V1 (m3/dak)= "+String.valueOf(V1));
        T2=values[2];pw.println("T2°C="  +String.valueOf(T2));
        RH2=values[3];pw.println("%RH2= "+String.valueOf(RH2));
        V2=values[4];pw.println("V2 (m3/dak)= "+String.valueOf(V2));
        P=values[5];pw.println("P (kPa)= "+String.valueOf(P));
      }
    }
    protected void CalculateWriteResults(){
      double varT3=0,varw3=0,varRH3=0,varV3=0;
      varT3=T3;varw3=w3;varRH3=RH3;varV3=V3;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(varw3));mhtable.setCellNote(rownumber,11," Çýkan havanýn mutlak nemi");
      mhtable.setCellValue(rownumber,12,String.valueOf(varRH3));mhtable.setCellNote(rownumber,12,"Çýkan havanýn bagil nemi");
      mhtable.setCellValue(rownumber,13,String.valueOf(varT3));mhtable.setCellNote(rownumber,13,"Çýkan havanýn sýcaklýgý");
      mhtable.setCellValue(rownumber,14,String.valueOf(varV3));mhtable.setCellNote(rownumber,14,"Çýkan havanýn hacimsel debisi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[4];
      double totalpoint=0;
      double varw3=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varRH3=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double varT3=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
      double varV3=Double.valueOf(mhtable.getCellValue(rownumber,14)).doubleValue();
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
            out.writeChars("Çýkan havanin mutlak nemini (kg/kg kh) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Çikan havanin bagil nemini [0-1] giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Çikan havanin sicakligini (°C) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Çikan havanin hacimsel debisini (m3/dakika) giriniz");
            useranswer[3]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));
          mhtable.setCellValue(rownumber,19,String.valueOf(useranswer[3]));
          //
          double upperlimit[]=new double[4];
          double lowerlimit[]=new double[4];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varw3*(1-lowerRange);upperlimit[0]=varw3*(1+upperRange);
          lowerlimit[1]=varRH3*(1-lowerRange);upperlimit[1]=varRH3*(1+upperRange);
          lowerlimit[2]=varT3*(1-lowerRange);upperlimit[2]=varT3*(1+upperRange);
          lowerlimit[3]=varV3*(1-lowerRange);upperlimit[3]=varV3*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,16,"Kullanicinin Mutlak Nem icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,17,"Kullanicinin Mutlak Nem icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,18,"Kullanicinin Sicaklik icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,19,"Kullanicinin Hacimsel Debi icin cevabi");
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
          pw.println("Havanin mutlak nemi icin"+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Havanin mutlak nemi icin"+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("Havanin sicakligi icin"+mhtable.getCellValue(rownumber,18)+" girdiniz");
          pw.println("Havanin hacimsel debisi icin"+mhtable.getCellValue(rownumber,19)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
