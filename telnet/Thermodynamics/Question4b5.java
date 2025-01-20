import java.io.*;
//
//***************************10th QUESTION*******************************
//
class Question4b5 extends Questions{
    double Tevaporator,Kizdirma,Tyogunlastirici,AsiriSogutma;
    private final int rownumber=10;
    public Question4b5(){
      super();
      values=new double[4];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("R134a'nin is gören akiskan olarak kullanildigi bir sogutma cevriminde,");
      pw.println("buharlastirici (Tbuharlastirici) ve yogunlastirici (Tyogunlastirici) sicakliklari ile ");
      pw.println("kizginlik/asiri sogutma bilgileri verilmektedir.Verilen degerleri kullanarak,");
      pw.println("A) Genlesme vanasi cikisindaki kuruluk derecesini,");
      pw.println("B) Buharlastici kapasitesini (kJ/kg),");
      pw.println("C) Kompresore verilen isi (pozitif olarak) (kJ/kg),");
      pw.println("D) Yogunlastiricida atilmasi gereken isiyi (pozitif olarak) (kJ/kg),");
      pw.println("E) COP'yi hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
         try{
          Tevaporator=Double.valueOf(mhtable.getCellValue(10,1)).doubleValue ();
          Kizdirma=Double.valueOf(mhtable.getCellValue(10,2)).doubleValue ();
          Tyogunlastirici=Double.valueOf(mhtable.getCellValue(10,3)).doubleValue ();
          AsiriSogutma=Double.valueOf(mhtable.getCellValue(10,4)).doubleValue ();
          pw.println("Tevaporator°C= "+String.valueOf(Tevaporator));values[0]=Tevaporator;
          pw.println("Kizdirma (kJ/kg)= "+String.valueOf(Kizdirma));values[1]=Kizdirma;
          pw.println("Tyogunlastirici°C= "+String.valueOf(Tyogunlastirici));values[2]=Tyogunlastirici;
          pw.println("AsiriSogutma (kJ/kg)= "+String.valueOf(AsiriSogutma));values[3]=AsiriSogutma;
        }catch(Exception nfe){pw.println("Lutfen 6-10 arasindaki soru degerleri icin ilk 6 nolu sorunun degerini aliniz.");}
        CalculateWriteResults();
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
        Tevaporator=values[0];pw.println("Tevaporator°C= "+String.valueOf(Tevaporator));
        Kizdirma=values[1];pw.println("Kizdirma (kJ/kg)= "+String.valueOf(Kizdirma));
        Tyogunlastirici=values[2];pw.println("Tyogunlastirici°C= "+String.valueOf(Tyogunlastirici));
        AsiriSogutma=values[3];pw.println("AsiriSogutma (kJ/kg)= "+String.valueOf(AsiriSogutma));
      }
    }
    protected void CalculateWriteResults(){
      double h1,P3,T3,h3,hf3,T4,hf4,hg4,h4,x4,s1,sg1,s2,T2,P2,h2,Qyog,Qevap,Wkomp,COP;
      T3=Tyogunlastirici;
      hf3=doymus134a.Hf(T3);
      h3=hf3;
      T4=Tevaporator;
      hf4=doymus134a.Hf(T4);hg4=doymus134a.Hg(T4);
      h4=h3-AsiriSogutma;
      x4=(h4-hf4)/(hg4-hf4);
      h1=doymus134a.Hg(T4)+Kizdirma;
      s1=doymus134a.Sg(T4);
      s2=s1;
      P3=doymus134a.P(T3)/1000; //MPa
      P2=P3;              //MPa
      double temps,tempT=5;
      do{
        tempT=tempT+0.5;
        temps=kizgin134a.S(P2,tempT);
      }while(!(Math.abs(temps-s2)<0.01));
      double XX=tempT;double YY=tempT+5;
      double ort;
      do{
        ort=(XX+YY)/2;
        temps=kizgin134a.S(P2,ort);
        if((temps-s2)>0) YY=ort;
        if((temps-s2)<0) XX=ort;
      }while(!(Math.abs(temps-s2)<0.0001));
      T2=ort;
      h2=kizgin134a.h(P2,T2);
      Qyog=h2-h3;
      Qevap=doymus134a.Hg(T4)-h4;
      Wkomp=h2-h1;
      COP=Qevap/Wkomp;
      //Record them to MHTable
      mhtable.setCellValue(rownumber,5,String.valueOf(x4));mhtable.setCellNote(rownumber,5," Genlesme vanasi cikisindaki kuruluk derecesi");
      mhtable.setCellValue(rownumber,6,String.valueOf(Qevap));mhtable.setCellNote(rownumber,6," Buharlastirici kapasitesi");
      mhtable.setCellValue(rownumber,7,String.valueOf(Wkomp));mhtable.setCellNote(rownumber,7," Kompresore verilen is");
      mhtable.setCellValue(rownumber,8,String.valueOf(Qyog));mhtable.setCellNote(rownumber,8,"Yogunlastirici kapasitesi");
      mhtable.setCellValue(rownumber,9,String.valueOf(COP));mhtable.setCellNote(rownumber,9," COP degeri");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[5];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(rownumber,5)).doubleValue();
      double varQevap=Double.valueOf(mhtable.getCellValue(rownumber,6)).doubleValue();
      double varWkomp=Double.valueOf(mhtable.getCellValue(rownumber,7)).doubleValue();
      double varQyog=Double.valueOf(mhtable.getCellValue(rownumber,8)).doubleValue();
      double varCOP=Double.valueOf(mhtable.getCellValue(rownumber,9)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,rownumber);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,rownumber,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      double doublevalueforquestion=10-(intnumberofanswer-1)*10/5;
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
            out.writeChars("Genlesme vanasi cikisindaki kuruluk derecesini [0-1] giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Buharlastirici kapasitesini (kJ/kg) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Kompresore verilen isi (kJ/kg) (pozitif olarak) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Yogunlastiricida atilmasi gereken isiyi (kJ/kg) (pozitif olarak) giriniz: ");
            useranswer[3]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("COP'yi giriniz: ");
            useranswer[4]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,rownumber,1);
          mhtable.setCellValue(rownumber,10,String.valueOf(useranswer[0]));
          mhtable.setCellValue(rownumber,11,String.valueOf(useranswer[1]));
          mhtable.setCellValue(rownumber,12,String.valueOf(useranswer[2]));
          mhtable.setCellValue(rownumber,13,String.valueOf(useranswer[3]));
          mhtable.setCellValue(rownumber,14,String.valueOf(useranswer[4]));
          //
          double upperlimit[]=new double[5];
          double lowerlimit[]=new double[5];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          lowerlimit[1]=varQevap*(1-lowerRange);upperlimit[1]=varQevap*(1+upperRange);
          lowerlimit[2]=varWkomp*(1-lowerRange);upperlimit[2]=varWkomp*(1+upperRange);
          lowerlimit[3]=varQyog*(1-lowerRange);upperlimit[3]=varQyog*(1+upperRange);
          lowerlimit[4]=varCOP*(1-lowerRange);upperlimit[4]=varCOP*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,10,"Kullanicinin kuruluk derecesi icin cevabi");
          }else{mhtable.setCellValue(rownumber,15,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,11,"Kullanicinin Qevaporator icin cevabi");
          }else{mhtable.setCellValue(rownumber,15,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,12,"Kullanicinin Wkompresor icin cevabi");
          }else{mhtable.setCellValue(rownumber,15,"0.000");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,13,"Kullanicinin Qyogunlastirici icin cevabi");
          }else{mhtable.setCellValue(rownumber,15,"0.000");}
          //
          if(useranswer[4]<upperlimit[4]&&useranswer[4]>lowerlimit[4]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[4])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,14,"Kullanicinin COP icin cevabi");
          }else{mhtable.setCellValue(rownumber,15,"0.000");}
          //
          mhtable.setCellValue(rownumber,15,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,15,"Kullanýcýnýn bu soru için toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Kuruluk derecesi için "+mhtable.getCellValue(rownumber,4)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,5)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
