import java.io.*;
//
//*********************MIXTURE OF AIRSTREAMS**********************
//
class QuestionPsychrometry2 extends Questions{
    double Tair,Xair,Y,Toutsideair,Xoutsideair,Rair,P;
    double ratio,Pws1,Pw1,w1,Pws2,Pw2,w2,w3,egim,T3,Pw3,Pws3,RH3;
    private final int rownumber=2;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry2(){
      super();
      values=new double[6];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Soðutma borularý üzerinden geçerek soðuyan hava ile az miktarda dýþ ortam");
      pw.println("havasýnýn karýþmasý genel olrak yapýlan bir uygulamadýr. Texas'taki bir");
      pw.println("uygulamada soðutma borusu üzerinden gelen hava Tair°C ve Xair baðýl neme");
      pw.println("sahiptir. %Y oranýndaki; Toutsideair°C, Xoutsideair baðýl nemine sahip");
      pw.println("dýþ ortam havasi ile karýþtýrýlacaktýr.");
      pw.println("A) Karisimin son sicakligini(°C): ");
      pw.println("B) Karisimin son bagil nemini hesaplayiniz [0-1]: ");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        //do{
          P=(double)(int)(Math.random()*10+95);
          Tair=(double)(int)(Math.random()*5+10);
          Xair=(double)(int)(Math.random()*10+40);
          Y=(double)(int)(Math.random()*10+30);
          Toutsideair=(double)(int)(Math.random()*5+20);
          Xoutsideair=(double)(int)(Math.random()*15+50);
          ratio=1/(Y/100);
          Pws1=su.P(Tair);
          Pw1=Pws1*Xair/100;
          w1=0.622*Pw1/(P-Pw1);
          Pws2=su.P(Toutsideair);
          Pw2=Pws2*Xoutsideair/100;
          w2=0.622*Pw2/(P-Pw2);
          w3=(ratio*w1+w2)/(1+ratio);
          egim=(w2-w1)/(Toutsideair-Tair);
          T3=(w3-w2)/egim+Toutsideair;
          Pw3=P*w3/(0.622+w3);
          Pws3=su.P(T3);
          RH3=Pw3/Pws3;
        //}while(!((RH>0.6)&&(RH<0.95)&&(Q>0)));
        //
        pw.println("P (kPa)= "+String.valueOf(P));values[0]=P;
        pw.println("Tair°C= "+String.valueOf(Tair));values[1]=Tair;
        pw.println("%Xair= "+String.valueOf(Xair));values[2]=Xair;
        pw.println("%Y= "+String.valueOf(Y));values[3]=Y;
        pw.println("Toutsideair°C= "+String.valueOf(Toutsideair));values[4]=Toutsideair;
        pw.println("%Xoutsideair= "+String.valueOf(Xoutsideair));values[5]=Xoutsideair;
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
        Tair=values[1];pw.println("Tair°C= "+String.valueOf(Tair));
        Xair=values[2];pw.println("%Xair="  +String.valueOf(Xair));
        Y=values[3];pw.println("%Y= "+String.valueOf(Y));
        Toutsideair=values[4];pw.println("Toutsideair°C= "+String.valueOf(Toutsideair));
        Xoutsideair=values[5];pw.println("%Xoutsideair= "+String.valueOf(Xoutsideair));
      }
    }
    protected void CalculateWriteResults(){
          ratio=1/(Y/100);
          Pws1=su.P(Tair);
          Pw1=Pws1*Xair/100;
          w1=0.622*Pw1/(P-Pw1);
          Pws2=su.P(Toutsideair);
          Pw2=Pws2*Xoutsideair/100;
          w2=0.622*Pw2/(P-Pw2);
          w3=(ratio*w1+w2)/(1+ratio);
          egim=(w2-w1)/(Toutsideair-Tair);
          T3=(w3-w2)/egim+Toutsideair;
          Pw3=P*w3/(0.622+w3);
          Pws3=su.P(T3);
          RH3=Pw3/Pws3;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(T3));mhtable.setCellNote(rownumber,11,"Çýkan havanýn sýcaklýðý");
      mhtable.setCellValue(rownumber,12,String.valueOf(RH3));mhtable.setCellNote(rownumber,12,"Çýkan havanýn baðýl nemi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varT=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varRH=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Çikan havanin bagil nemini giriniz [0-1]: ");
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
          lowerlimit[0]=varT*(1-lowerRange);upperlimit[0]=varT*(1+upperRange);
          lowerlimit[1]=varRH*(1-lowerRange);upperlimit[1]=varRH*(1+upperRange);
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
            mhtable.setCellNote(rownumber,17,"Kullanicinin Bagil nem icin cevabi");
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
          pw.println("Son sýcaklýk için "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Bagil nem için "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
