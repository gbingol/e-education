import java.io.*;
//
//*********************ÝLETÝM UYGULAMA 2. SORU **********************
//
class Conduction2 extends Questions{
    double T1,x,a,b,T2,qtasinim,qradyasyon,Tic;
    private final int rownumber=2;
    private final double SorununNotDegeri=10;
    public Conduction2(){
      super();
      values=new double[7];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" T1°C'de bulunan hava x (cm) kalinligindaki, a*b (cm) boyutlarindaki T2°C");
      pw.println("sýcaklýgýnda sabit tutulan %1'lik C celiginden yapýlmýþ levha üzerinden   ");
      pw.println("akmaktadýr. Levha boyunca tasýným ile transfer edilen ýsý miktarý ");
      pw.println("qtasinim kW'dýr. qradyasyon W'lýk bir ýsýnýn da radyasyon yoku ile ");
      pw.println("kaybolduðunu kabul ederek,");
      pw.println("levhanýn iç sýcaklýðýný hesaplayýnýz.");
      pw.println("Levhanýn ýsýl iletkenlik katsayýsý klevha=43 (W/m°C)");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          T1=(double)(int)(Math.random()*10+20);
          x=(double)(int)(Math.random()*3+2);
          a=(double)(int)(Math.random()*45+30);
          b=(double)(int)(Math.random()*45+30);
          T2=(double)(int)(Math.random()*100+150);
          qtasinim=(Math.random()*3+1);
          qradyasyon=(double)(int)(Math.random()*100+200);
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("T1°C= "+String.valueOf(T1));values[0]=T1;
        pw.println("x (cm)= "+String.valueOf(x));values[1]=x;
        pw.println("a (cm)= "+String.valueOf(a));values[2]=a;
        pw.println("b (cm)= "+String.valueOf(b));values[3]=b;
        pw.println("T2°C= "+String.valueOf(T2));values[4]=T2;
        pw.println("qtasinim (kW)= "+String.valueOf(qtasinim));values[5]=qtasinim;
        pw.println("qradyasyon (W)= "+String.valueOf(qradyasyon));values[6]=qradyasyon;
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
        x=values[1];pw.println("x (cm) = "+String.valueOf(x));
        a=values[2];pw.println("a (cm)="  +String.valueOf(a));
        b=values[3];pw.println("b (cm)= "+String.valueOf(b));
        T2=values[4];pw.println("T2°C= "+String.valueOf(T2));
        qtasinim=values[5];pw.println("qtasinim (kW)= "+String.valueOf(qtasinim));
        qradyasyon=values[6];pw.println("qradyasyon (W)= "+String.valueOf(qradyasyon));
      }
    }
    protected void CalculateWriteResults(){
      double qiletim,dT,Area;
      Area=(a/100)*(b/100);
      qiletim=qtasinim*1000+qradyasyon;
      dT=(qiletim*(x/100))/(Area*43);
      Tic=T2+dT;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(Tic));mhtable.setCellNote(rownumber,11,"Levhanýn iç sýcaklýðý");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varTic=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama sayýsýna göre soru deðerini hesapla
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
            out.writeChars("Levhanin ic sicakligini (Tic°C) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn girdiði deðer");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varTic*(1-lowerRange);upperlimit[0]=varTic*(1+upperRange);
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
          pw.println("Levhanin ic sicakligi için "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
