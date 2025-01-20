import java.io.*;
//
class Question2a1 extends Questions{
    double Tgiris,RHgiris,V,Tcikis,RHcikis,Tisitma,Ptoplam;
    public Question2a1(){
      super();
      values=new double[7];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Dýþ hava sürekli bir iklimlendirme sistemine Tgiriþ°C sýcaklýk");
      pw.println("ve %RH baðýl nemde V m3/dakika debiyle girmekte, sistemden Tçýkýþ°C");
      pw.println("ve %RHçýkýþ baðýl nemde çýkmaktadýr. Dýþ hava önce ýsýtma bölümünde");
      pw.println("Týsýtma°C sýcaklýða ýsýtýlmakta, daha sonra nemlendirme bölümünde ");
      pw.println("içine sýcak buhar püskürtülerek istenen koþullara getirilmektedir.");
      pw.println("Tüm iþlem boyunca toplam basýncýn Ptoplam olduðunu kabul ederek;");
      pw.println("A) Isýtma bölümünde havaya birim zamanda verilen ýsýyý (kJ/dak),");
      pw.println("B) Nemlendirme için gerekli buhar debisini (kg/dak) hesaplayýnýz");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(1,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        Tgiris=Math.random()*5+10;pw.println("Tgiriþ°C= "+String.valueOf(Tgiris));values[0]=Tgiris;
        RHgiris=Math.random()*10+25; pw.println("%RHgiriþ= "+String.valueOf(RHgiris));values[1]=RHgiris;
        V=Math.random()*20+30; pw.println("V m3/dakika= "+String.valueOf(V));values[2]=V;
        Tcikis=Tgiris+Math.random()*10+15;pw.println("Tçýkýþ°C= "+String.valueOf(Tcikis));values[3]=Tcikis;
        RHcikis=Math.random()*30+40;pw.println("%RHçýkýþ= "+String.valueOf(RHcikis));values[4]=RHcikis;
        Tisitma=Tgiris+Math.random()*10;pw.println("Týsýtma°C= "+String.valueOf(Tisitma));values[5]=Tisitma;
        Ptoplam=95+Math.random()*10;pw.println("Ptoplam kPa= "+String.valueOf(Ptoplam));values[6]=Ptoplam;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(1,i+1,String.valueOf(values[i]));
        }
        mhtable.setCellValue(1,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("1. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(1,i+1)).doubleValue();
        }
        Tgiris=values[0];pw.println("Tgiriþ°C= "+String.valueOf(Tgiris));
        RHgiris=values[1];pw.println("%RHgiriþ= "+String.valueOf(RHgiris));
        V=values[2];pw.println("V m3/dakika= "+String.valueOf(V));
        Tcikis=values[3];pw.println("Tçýkýþ°C= "+String.valueOf(Tcikis));
        RHcikis=values[4];pw.println("%RHçýkýþ= "+String.valueOf(RHcikis));
        Tisitma=values[5];pw.println("Týsýtma°C= "+String.valueOf(Tisitma));
        Ptoplam=values[6];pw.println("Ptoplam kPa= "+String.valueOf(Ptoplam));
      }
    }
    protected void CalculateWriteResults(){
        double Pdoyma=0,Pv1=0,Pa1=0,v1=0,ma=0,w1=0,h1=0,h2=0,Q=0,w3=0,msu=0;
        double d[]=new double[2];
        Pdoyma=su.P(Tgiris);Pv1=RHgiris/100*Pdoyma;Pa1=Ptoplam-Pv1;
        v1=0.287*(Tgiris+273.15)/Pa1;
        ma=V/v1;
        w1=0.622*Pv1/(Ptoplam-Pv1);
        h1=1.005*Tgiris+w1*su.Hg(Tgiris);
        h2=1.005*Tisitma+w1*su.Hg(Tisitma);
        Q=ma*(h2-h1);
        w3=0.622*RHcikis/100*su.P(Tcikis)/(Ptoplam-(RHcikis/100*su.P(Tcikis)));
        msu=ma*(w3-w1);
        d[0]=Q;d[1]=msu;
        mhtable.setCellValue(1,8,String.valueOf(d[0]));
        mhtable.setCellValue(1,9,String.valueOf(d[1]));
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double q=Double.valueOf(mhtable.getCellValue(1,8)).doubleValue();
      double msu=Double.valueOf(mhtable.getCellValue(1,9)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(4,0);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(4,0,1);}
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
          //further chance of value input
          //
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+String.valueOf(doublevalueforquestion*2));
            out.writeChars("Isýtma bölümünde havaya birim zamanda verilen isi (kJ/dak):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Nemlendirme için gerekli buhar debisi (kg/dak):");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(4,0,1);
          mhtable.setCellValue(1,10,String.valueOf(useranswer[0]));
          mhtable.setCellValue(1,11,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=q*(1-lowerRange);upperlimit[0]=q*(1+upperRange);
          lowerlimit[1]=msu*(1-lowerRange);upperlimit[1]=msu*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            mhtable.setCellValue(4,1,strvalueforquestion); totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,1,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            mhtable.setCellValue(4,2,strvalueforquestion);totalpoint=totalpoint+doublevalueforquestion;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(4,2,"0.000");}
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
          /*if(!(mhtable.getCellValue(4,9).equals(""))){
            double realtotal=Double.valueOf(mhtable.getCellValue(4,9)).doubleValue();
            realtotal=realtotal+totalpoint;
            mhtable.setCellValue(4,9,String.valueOf(realtotal));
          }//if(!mhtable.getCellValue ()
          else{mhtable.setCellValue(4,9,String.valueOf(totalpoint));}*/
        }//if
        else{
          pw.println("********** 1.soruyu zaten cevaplamistiniz. **********");
          pw.println("Havaya birim zamanda verilen ýsý için "+mhtable.getCellValue(1,10)+" (kJ/dak) girdiniz");
          pw.println("Buhar debisi için "+mhtable.getCellValue(1,11)+" (kg/dak) girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(4,1)).doubleValue ()+Double.valueOf(mhtable.getCellValue(4,2)).doubleValue ();
          pw.println("1. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
          pw.println("Þu ana kadar olan Toplam notunuz:"+mhtable.getCellValue(4,9));
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 1");}
    }
}
