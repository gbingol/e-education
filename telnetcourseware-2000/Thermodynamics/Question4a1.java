import java.io.*;
//
//***************************1st QUESTION*******************************
//
class Question4a1 extends Questions{
    double Tyog,Tturbin,Pturbin,farkTyog,farkTturbin,farkPturbin;
    public Question4a1(){
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
    String str=mhtable.getCellValue(1,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
      double P1,h1,v1,P2,s2,s1,Wpompa,h2,P3,T3,h3,h4,s3,P4,T4,s4,x4,Wturbin,Wnet,Qkazan,Verim;
        do{
          Tyog=(double)(int)(Math.random()*40+60);farkTyog=(double)(int)(Math.random()*20+20);
          Tturbin=(double)(int)(Math.random()*300+200);farkTturbin=(double)(int)(100+Math.random ()*100);
          Pturbin=(double)(int)(Math.random()*3+2);farkPturbin=(double)(int)(2+Math.random()*3);
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
        }while(!((x4>0.75)&&(x4<0.95)&&(Wpompa>2)&&(Wturbin>500)&&(Verim>20)&&(Verim<30)));
        pw.println("Tyogunlastirici°C= "+String.valueOf(Tyog));values[0]=Tyog;
        pw.println("Tturbin°C= "+String.valueOf(Tturbin));values[1]=Tturbin;
        pw.println("Pturbin (MPa)= "+String.valueOf(Pturbin));values[2]=Pturbin;
        CalculateWriteResults();
        for(int j=1;j<=5;j++){
          if(j==2) values[0]=values[0]-farkTyog;
          if(j==3) values[1]=values[1]+farkTturbin;
          if(j==4) values[2]=values[2]+farkPturbin;
          for(int i=0;i<values.length;i++){
            mhtable.setCellValue(j,i+1,String.valueOf(values[i]));
            mhtable.setCellNote(j,i+1,"Sistemin verdigi degerler");
          }
          if(j==4) values[0]=values[0]+farkTyog;
        }
        mhtable.setCellValue(1,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("1. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(1,i+1)).doubleValue();
        }
        Tyog=values[0];pw.println("Tyogunlastirici°C= "+String.valueOf(Tyog));
        Tturbin=values[1];pw.println("Tturbin°C= "+String.valueOf(Tturbin));
        Pturbin=values[2];pw.println("Pturbin (MPa)= "+String.valueOf(Pturbin));
      }
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
        mhtable.setCellValue(1,4,String.valueOf(x4));mhtable.setCellNote(1,4,"Turbin cikisindaki kuruluk derecesi");
        mhtable.setCellValue(1,5,String.valueOf(Wturbin));mhtable.setCellNote(1,5,"Turbinden elde edilen is");
        mhtable.setCellValue(1,6,String.valueOf(Wpompa));mhtable.setCellNote(1,6,"Pompaya verilen is");
        mhtable.setCellValue(1,7,String.valueOf(Qkazan));mhtable.setCellNote(1,7,"Kazana verilmesi gerekli isi miktari");
        mhtable.setCellValue(1,8,String.valueOf(Verim));mhtable.setCellNote(1,8,"Cevrimin % de isil verimi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[5];
      double totalpoint=0;
      double varx=Double.valueOf(mhtable.getCellValue(1,4)).doubleValue();
      double varWturbin=Double.valueOf(mhtable.getCellValue(1,5)).doubleValue();
      double varWpompa=Double.valueOf(mhtable.getCellValue(1,6)).doubleValue();
      double varQkazan=Double.valueOf(mhtable.getCellValue(1,7)).doubleValue();
      double varVerim=Double.valueOf(mhtable.getCellValue(1,8)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,1);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,1,1);}
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
          updateCell(11,1,1);
          mhtable.setCellValue(1,9,String.valueOf(useranswer[0]));
          mhtable.setCellValue(1,10,String.valueOf(useranswer[1]));
          mhtable.setCellValue(1,11,String.valueOf(useranswer[2]));
          mhtable.setCellValue(1,12,String.valueOf(useranswer[3]));
          mhtable.setCellValue(1,13,String.valueOf(useranswer[4]));
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
            mhtable.setCellNote(1,9,"Kullanicinin kuruluk derecesi icin cevabi");
          }else{mhtable.setCellValue(1,14,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(1,10,"Kullanicinin Wturbin icin cevabi");
          }else{mhtable.setCellValue(1,14,"0.000");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
            mhtable.setCellNote(1,11,"Kullanicinin Wpompa icin cevabi");
          }else{mhtable.setCellValue(1,14,"0.000");}
          //
          if(useranswer[3]<upperlimit[3]&&useranswer[3]>lowerlimit[3]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[3])+" cevabiniz dogru.");
            mhtable.setCellNote(1,12,"Kullanicinin Qkazan icin cevabi");
          }else{mhtable.setCellValue(1,14,"0.000");}
          //
          if(useranswer[4]<upperlimit[4]&&useranswer[4]>lowerlimit[4]){
            totalpoint=totalpoint+doublevalueforquestion/5;
            pw.println(String.valueOf(useranswer[4])+" cevabiniz dogru.");
            mhtable.setCellNote(1,13,"Kullanicinin %Verim icin cevabi");
          }else{mhtable.setCellValue(1,14,"0.000");}
          //
          mhtable.setCellValue(1,14,String.valueOf(totalpoint));
          mhtable.setCellNote(1,14,"Kullanicinin bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("********** 1.soruyu zaten cevaplamistiniz. **********");
          pw.println("Kuruluk derecesi için "+mhtable.getCellValue(1,9)+" girdiniz");
          pw.println("Wturbin için "+mhtable.getCellValue(1,10)+" girdiniz");
          pw.println("Wpompa için "+mhtable.getCellValue(1,11)+" girdiniz");
          pw.println("Qkazan için "+mhtable.getCellValue(1,12)+" girdiniz");
          pw.println("%Verim için "+mhtable.getCellValue(1,13)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(1,14)).doubleValue ();
          pw.println("1. Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 1");}
    }
}
