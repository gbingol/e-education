import java.io.*;
//
class Question2a2 extends Questions{
    private double Vsogutma,Tgiris,Tsoguk,P,Tsogutma,RHsogutma,Tdoymus,Vhava,Vsu;
    public Question2a2(){
      super();
      results=new double[2];
      values=new double[7];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Bir güç santralinin yoguþturucusunda dolaþan Vsoðutma (kg/s) debisindeki");
      pw.println("soðutma suyu, ýslak sogutma kulesine Tgiriþ°C sýcaklýkta girmekte ");
      pw.println("ve Tsoðuk°C sýcaklýða sogutulmaktadýr.  Soðutma, kuleye P (kPa) basýnç,");
      pw.println("Tsoðutma°C sýcaklýk, %RHsogutma nemde giren ve Tdoymuþ°C sýcaklýkta doymuþ ");
      pw.println("olarak çýkan hava tarafýndan yapýlmaktadýr.  Faný çalýþtýrmak için ");
      pw.println("gerekli gücü ihmal ederek,");
      pw.println("A) Soðutma kulesine giren havanýn hacimsel debisini (m3/s), ");
      pw.println("B) Tamamlama suyunun debisini (kg/s) hesaplayýn");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=super.mhtable.getCellValue(2,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        Tgiris=Math.random()*10+30;pw.println("Tgiriþ°C= "+String.valueOf(Tgiris));values[0]=Tgiris;
        Vsogutma=Math.random()*10+25; pw.println("Vsoðutma (kg/s)= "+String.valueOf(Vsogutma));values[1]=Vsogutma;
        Tsoguk=Tgiris-Math.random()*10-10; pw.println("Tsoðuk°C= "+String.valueOf(Tsoguk));values[2]=Tsoguk;
        P=Math.random()*10+95;pw.println("P (kPa)= "+String.valueOf(P));values[3]=P;
        Tsogutma=Tsoguk+Math.random()*10+5;pw.println("Tsogutma°C= "+String.valueOf(Tsogutma));values[4]=Tsogutma;
        RHsogutma=Math.random()*20+50;pw.println("%RHsoðutma°C= "+String.valueOf(RHsogutma));values[5]=RHsogutma;
        Tdoymus=Tsogutma+Math.random()*10;pw.println("Tdoymus= "+String.valueOf(Tdoymus));values[6]=Tdoymus;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(2,i+1,String.valueOf(values[i]));
        }
        mhtable.setCellValue(2,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("2. Soru Icin degerlerinizi zaten almýþtýnz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(2,i+1)).doubleValue();
        }
        Tgiris=values[0];pw.println("Tgiriþ°C= "+String.valueOf(Tgiris));
        Vsogutma=values[1];pw.println("Vsoðutma (kg/s)="+String.valueOf(Vsogutma));
        Tsoguk=values[2];pw.println("Tsoðuk°C="+String.valueOf(Tsoguk));
        P=values[3];pw.println("P (kPa)="+String.valueOf(P));
        Tsogutma=values[4];pw.println("Tsogutma°C="+String.valueOf(Tsogutma));
        RHsogutma=values[5];pw.println("%RHsoðutma="+String.valueOf(RHsogutma));
        Tdoymus=values[6];pw.println("Tdoymus="+String.valueOf(Tdoymus));
      }
    }
    protected void CalculateWriteResults(){
      double d[]=new double[2];
      double h1,w1,v1,h2,w2,h3,h4,ma,V1;
			double V,Tsuin,Tsusog,varP,Tin,RHin,Tex,Vhava,Vsu;
			double Pws,Pw;
			Tsuin=Tgiris;
			V=Vsogutma;
			Tsusog=Tsoguk;
			varP=P;
			Tin=Tsogutma;
			RHin=RHsogutma;
			Tex=Tdoymus;
			h3=su.Hf(Tsuin);
			h4=su.Hf(Tsusog);
			Pws=pf.Pws(Tin);
			Pw=Pws*RHin/100;
			w1=pf.W2(varP*1000,Pw);
			h1=pf.H(Tin,w1);
			v1=pf.v(Tin,varP*1000,w1);
			Pws=pf.Pws(Tex);
			Pw=Pws;
			w2=pf.W2(varP*1000,Pw);
			h2=pf.H(Tex,w2);
			ma=V*(h3-h4)/((h2-h1)-(w2-w1)*h4);
			Vhava=ma*v1;d[0]=Vhava;
			Vsu=ma*(w2-w1);d[1]=Vsu;
      mhtable.setCellValue(2,8,String.valueOf(d[0]));
      mhtable.setCellValue(2,9,String.valueOf(d[1]));
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double Vhava=Double.valueOf(mhtable.getCellValue(2,8)).doubleValue();
      double Vsu=Double.valueOf(mhtable.getCellValue(2,9)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(4,3);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(4,3,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      double doublevalueforquestion=15-(intnumberofanswer-1)*15/5;
      String strvalueforquestion=String.valueOf(doublevalueforquestion);
      //
      try{
        if(!isanswered){
          boolean isaccepted=false;
          do{
          //If user does not enter a value end just presses enter key
          //java throws java.lang.NumberFormatException: empty String
          //and hereby user have the chance not to enter any value and quit this session for
          //further chance of value entering
          //
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+String.valueOf(doublevalueforquestion*2));
            out.writeChars("Sogutma kulesine giren havanin hacimsel debisini (m3/s):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Tamamlama suyunun debisi (kg/s):");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz. [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(4,3,1);
          mhtable.setCellValue(2,10,String.valueOf(useranswer[0]));
          mhtable.setCellValue(2,11,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          lowerlimit[0]=Vhava*(1-lowerRange);upperlimit[0]=Vhava*(1+upperRange);
          lowerlimit[1]=Vsu*(1-lowerRange);upperlimit[1]=Vsu*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(4,4,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,4,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            mhtable.setCellValue(4,5,strvalueforquestion);totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,5,"0.000");}
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
        }//if
        else{
          pw.println("********* 2.soruyu zaten cevaplamistiniz. ********* ");
          pw.println("Havanýn hacimsel debisi için:"+mhtable.getCellValue(2,10)+" (m3/s) girdiniz.");
          pw.println("Tamamlama suyu debisi için:"+mhtable.getCellValue(2,11)+" (kg/s) girdiniz.");
          double total=Double.valueOf(mhtable.getCellValue(4,4)).doubleValue ()+Double.valueOf(mhtable.getCellValue(4,5)).doubleValue ();
          pw.println("2. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
          pw.println("Þu ana kadar olan Toplam notunuz:"+mhtable.getCellValue(4,9));
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e);System.out.println(" for question 2");}
    }
}
