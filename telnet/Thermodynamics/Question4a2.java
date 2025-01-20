import java.io.*;
//
//***************************2nd QUESTION*******************************
//
class Question4a2 extends Questions{
    double Tyog,Tturbin,Pturbin;
    public Question4a2(){
      super();
      values=new double[3];
      results=new double[5];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("Suyun iþgören akiþkan olarak kullanildigi bir Rankine cevriminde, yogunlaþtirici");
      pw.println("sicakliklari (Tyogunlastirici°C) ve türbin girisindeki sicaklik(Tturbin°C) ve   ");
      pw.println("basinclar (Pturbin) verilmistir. Verilen degerleri kullanarak,");
      pw.println("A) Turbin cikisindaki kuruluk derecesini [0-1],");
      pw.println("B) Turbinden elde edilen iþi (kJ/kg),");
      pw.println("C) Pompaya verilen iþi (kJ/kg),");
      pw.println("D) Kazanda verilmesi gereken isiyi (kJ/kg),");
      pw.println("E) % Verimi hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(2,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      try{
        if(!IsValuesGiven){
          Tyog=Double.valueOf(mhtable.getCellValue(2,1)).doubleValue ();
          Tturbin=Double.valueOf(mhtable.getCellValue(2,2)).doubleValue ();
          Pturbin=Double.valueOf(mhtable.getCellValue(2,3)).doubleValue ();
          pw.println("Tyogunlastirici°C= "+String.valueOf(Tyog));values[0]=Tyog;
          pw.println("Tturbin°C= "+String.valueOf(Tturbin));values[1]=Tturbin;
          pw.println("Pturbin (MPa)= "+String.valueOf(Pturbin));values[2]=Pturbin;
          CalculateWriteResults();
          for(int i=0;i<values.length;i++){
           mhtable.setCellValue(2,i+1,String.valueOf(values[i]));
           mhtable.setCellNote(2,i+1,"Sistemin verdigi degerler");
          }
          mhtable.setCellValue(2,0,"ValueIsGiven");
          IsValuesGiven=true;
        }else{
          pw.println("2. Soru Icin Degerlerinizi zaten almýþtýnýz.");
          for(int i=0;i<values.length;i++){
            values[i]=Double.valueOf(mhtable.getCellValue(2,i+1)).doubleValue();
          }
          Tyog=values[0];pw.println("Tyogunlastirici°C= "+String.valueOf(Tyog));
          Tturbin=values[1];pw.println("Tturbin°C= "+String.valueOf(Tturbin));
          Pturbin=values[2];pw.println("Pturbin (MPa)= "+String.valueOf(Pturbin));
        }
      }catch(Exception e){pw.println("1-5 arasindaki sorular icin lutfen ilk once 1 no'lu sorunun degerini aliniz.");}
    }
    protected void CalculateWriteResults(){
        double P1,h1,v1,P2,s2,s1,Wpompa,h2,P3,T3,h3,h4,s3,P4,T4,s4,x4,Wturbin,Wnet,Qkazan,Verim;
        T4=Tyog;
        P1=su.P(T4);  //kPa
        h1=su.HfP(P1);v1=su.VfP(P1);
        P3=Pturbin;     //MPa
        T3=Tturbin;
        P2=P3*1000;   //kPa
        Wpompa=v1*(P2-P1);
        h2=h1+Wpompa;
        h3=kizginSuBuhari.h(P3,T3);s3=kizginSuBuhari.S(P3,T3);
        P4=P1;    //kPa
        s4=s3;
        T4=su.T(P4);
        double sf4=su.Sf(T4);
        double sg4=su.Sg(T4);
        double sfg4=sg4-sf4;
        x4=(s4-sf4)/sfg4;
        h4=su.HfP(P4)+x4*(su.HgP(P4)-su.HfP(P4));
        Wturbin=h3-h4;
        Wnet=Wturbin-Wpompa;
        Qkazan=h3-h2;
        Verim=Wnet/Qkazan*100;
        //Record them to MHTable
        mhtable.setCellValue(2,4,String.valueOf(x4));mhtable.setCellNote(2,4,"Turbin cikisindaki kuruluk derecesi");
        mhtable.setCellValue(2,5,String.valueOf(Wturbin));mhtable.setCellNote(2,5,"Turbinden elde edilen is");
        mhtable.setCellValue(2,6,String.valueOf(Wpompa));mhtable.setCellNote(2,6,"Pompaya verilen is");
        mhtable.setCellValue(2,7,String.valueOf(Qkazan));mhtable.setCellNote(2,7,"Kazana verilmesi gerekli isi miktari");
        mhtable.setCellValue(2,8,String.valueOf(Verim));mhtable.setCellNote(2,8,"Cevrimin % de isil verimi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[5];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(2,4)).doubleValue();
      double varWturbin=Double.valueOf(mhtable.getCellValue(2,5)).doubleValue();
      double varWpompa=Double.valueOf(mhtable.getCellValue(2,6)).doubleValue();
      double varQkazan=Double.valueOf(mhtable.getCellValue(2,7)).doubleValue();
      double varVerim=Double.valueOf(mhtable.getCellValue(2,8)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,2);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,2,1);}
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
            out.writeChars("Turbin cikisindaki kuruluk derecesini [0-1] giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Turbinden elde edilen isi (kJ/kg) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Pompaya verilen isi (kJ/kg) (pozitif olarak) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Kazanda verilmesi gereken isiyi (kJ/kg) giriniz: ");
            useranswer[3]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Verimi % giriniz: ");
            useranswer[4]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,2,1);
          mhtable.setCellValue(2,9,String.valueOf(useranswer[0]));
          mhtable.setCellValue(2,10,String.valueOf(useranswer[1]));
          mhtable.setCellValue(2,11,String.valueOf(useranswer[2]));
          mhtable.setCellValue(2,12,String.valueOf(useranswer[3]));
          mhtable.setCellValue(2,13,String.valueOf(useranswer[4]));
          //
          double upperlimit[]=new double[5];
          double lowerlimit[]=new double[5];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varx*(1-lowerRange);upperlimit[0]=varx*(1+upperRange);
          lowerlimit[1]=varWturbin*(1-lowerRange);upperlimit[1]=varWturbin*(1+upperRange);
          lowerlimit[2]=varWpompa*(1-lowerRange);upperlimit[2]=varWpompa*(1+upperRange);
          lowerlimit[3]=varQkazan*(1-lowerRange);upperlimit[3]=varQkazan*(1+upperRange);
          lowerlimit[4]=varVerim*(1-lowerRange);upperlimit[4]=varVerim*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(2,9,"Kullanicinin kuruluk derecesi icin cevabi");
          }else{mhtable.setCellValue(2,14,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(2,10,"Kullanicinin Wturbin icin cevabi");
          }else{mhtable.setCellValue(2,14,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
            mhtable.setCellNote(2,11,"Kullanicinin Wpompa icin cevabi");
          }else{mhtable.setCellValue(2,14,"0.000");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
            mhtable.setCellNote(2,12,"Kullanicinin Qkazan icin cevabi");
          }else{mhtable.setCellValue(2,14,"0.000");}
          //
          if(useranswer[4]<upperlimit[4]&&useranswer[4]>lowerlimit[4]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[4])+" cevabiniz dogru.");
            mhtable.setCellNote(2,13,"Kullanicinin %Verim icin cevabi");
          }else{mhtable.setCellValue(2,14,"0.000");}
          //
          mhtable.setCellValue(2,14,String.valueOf(totalpoint));
          mhtable.setCellNote(2,14,"Kullanicinin bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("********** 2.soruyu zaten cevaplamistiniz. **********");
          pw.println("Kuruluk derecesi için "+mhtable.getCellValue(2,9)+" girdiniz");
          pw.println("Wturbin için "+mhtable.getCellValue(2,10)+" girdiniz");
          pw.println("Wpompa için "+mhtable.getCellValue(2,11)+" girdiniz");
          pw.println("Qkazan için "+mhtable.getCellValue(2,12)+" girdiniz");
          pw.println("%Verim için "+mhtable.getCellValue(2,13)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(2,14)).doubleValue ();
          pw.println("2. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 2");}
    }
}
