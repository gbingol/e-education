import java.io.*;
//
//***************************3rd QUESTION*******************************
//
class Question2a3 extends Questions{
    private double Ptop,Tin,RHin,Tex,Tsu,Msu,Q,Vhava;
    public Question2a3(){
      super();
      results=new double[2];
      values=new double[6];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Hava, pencere tipi bir klima cihazýna P (kPa) basýnç, Tgiriþ°C      ");
      pw.println("ve %RHgiriþ baðýl nemde, V m3/dakika debiyle girmekte,              ");
      pw.println("Tcikis°C sýcaklýkta doymuþ halde çýkmaktadýr.                       ");
      pw.println("Soðutma iþlemi sýrasýnda yoðuþan su Tatilma°C sýcaklýkta atýlmaktadýr.");
      pw.println("A) Havadan birim zamanda çekilen isiyi (kJ/dakika) ");
      pw.println("B) Yogusan su miktarini (kg/dakika) hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=super.mhtable.getCellValue(3,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        Ptop=Math.random()*10+95;pw.println("P (kPa)= "+String.valueOf(Ptop));values[0]=Ptop;
        Tin=Math.random()*10+25;pw.println("Tgiriþ°C= "+String.valueOf(Tin));values[1]=Tin;
        RHin=Math.random()*10+75; pw.println("%RHgiriþ= "+String.valueOf(RHin));values[2]=RHin;
        Vhava=Math.random()*10+5; pw.println("V (m3/dakika)= "+String.valueOf(Vhava));values[3]=Vhava;
        Tex=Math.random()*10+10;pw.println("Tcikis°C= "+String.valueOf(Tex));values[4]=Tex;
        Tsu=Tex;pw.println("Tatilma°C= "+String.valueOf(Tsu));values[5]=Tsu;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(3,i+1,String.valueOf(values[i]));
        }
        mhtable.setCellValue(3,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("3. Soru Icin degerlerinizi zaten almýþtýnz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(3,i+1)).doubleValue();
        }
        Ptop=values[0];pw.println("Ptop (kPa)= "+String.valueOf(Ptop));
        Tin=values[1];pw.println("Tgiris°C="+String.valueOf(Tin));
        RHin=values[2];pw.println("%RHgiris="+String.valueOf(RHin));
        Vhava=values[3];pw.println("V (m3/dakika)="+String.valueOf(Vhava));
        Tex=values[4];pw.println("Tcikis°C="+String.valueOf(Tex));
        Tsu=values[5];pw.println("Tatilma°C="+String.valueOf(Tsu));
      }
    }
    protected void CalculateWriteResults(){
			double h1,w1,v1,h3,w3,hsu,ma;
			double pws,pw,W,Ws,Td,Tyas,pwsyas,Wsyas,h;
			double pws2,pw2;
			pws=pf.Pws(Tin);
			pw=RHin/100*pws;
			W=pf.W2(Ptop*1000,pw);
			h=pf.H(Tin,W);
			h1=h;w1=W;
			v1=287.055*(Tin+273.15)*(1+1.6078*w1)/(Ptop*1000);
			pws2=pf.Pws(Tex);
			pw2=pws2;			//RH=%100
			w3=pf.W2(Ptop*1000,pw2);
			h3=pf.H(Tex,w3);
			hsu=su.Hf(Tsu);
			ma=Vhava/v1;
			Msu=ma*(w1-w3);
			Q=ma*(h3-h1)+Msu*hsu;
      mhtable.setCellValue(3,7,String.valueOf(Q));
      mhtable.setCellValue(3,8,String.valueOf(Msu));
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double Q=Double.valueOf(mhtable.getCellValue(3,7)).doubleValue();
      double Msu=Double.valueOf(mhtable.getCellValue(3,8)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(4,6);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(4,6,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      double doublevalueforquestion=20-(intnumberofanswer-1)*20/5;
      String strvalueforquestion=String.valueOf(doublevalueforquestion);
      //
      try{
        if(!isanswered){
          boolean isaccepted=false; //Whether user accepts his/her entering values
          do{
          //If user does not enter a value end just presses enter key
          //java throws java.lang.NumberFormatException: empty String
          //and hereby user have the chance not to enter any value and quit this session for
          //further chance of value entering
          //
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+String.valueOf(doublevalueforquestion*2));
            out.writeChars("Havadan birim zamanda cekilen isi (kJ/dakika):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Yogusan su miktari (kg/dakika) :");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz. [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(4,6,1);
          mhtable.setCellValue(3,9,String.valueOf(useranswer[0]));
          mhtable.setCellValue(3,10,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          if(Q<0) {Q=-Q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=Q*(1-lowerRange);upperlimit[0]=Q*(1+upperRange);
          lowerlimit[1]=Msu*(1-lowerRange);upperlimit[1]=Msu*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(4,7,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,7,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            mhtable.setCellValue(4,8,strvalueforquestion);totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,8,"0.000");}
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
        }//if
        else{
          pw.println("********* 3.soruyu zaten cevaplamistiniz. ********* ");
          pw.println("Havadan birim zamanda çekilen isi icin:"+mhtable.getCellValue(3,9)+" (kJ/dakika) girdiniz.");
          pw.println("Yogusan su miktari icin:"+mhtable.getCellValue(3,10)+" (kg/dakika) girdiniz.");
          double total=Double.valueOf(mhtable.getCellValue(4,7)).doubleValue ()+Double.valueOf(mhtable.getCellValue(4,8)).doubleValue ();
          pw.println("3. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
          pw.println("Þu ana kadar olan Toplam notunuz:"+mhtable.getCellValue(4,9));
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e);System.out.println(" for question 2");}
    }
}


