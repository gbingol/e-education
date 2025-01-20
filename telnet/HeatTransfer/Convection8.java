import java.io.*;
//
//*********************Uygulamada Taþýným 9. soru**********************
//
class Convection8 extends Questions{
    double Dic,Tboru_ic,m,Ti,Ts,L;
    private final int rownumber=4;
    private final double SorununNotDegeri=15;
    public Convection8(){
      super();
      values=new double[5];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Dic (mm) ic capindaki borunun ic yuzeyi sabit Tboru_ic°C sicaklikta olup,");
      pw.println("icinden 1 saatte gecen m (kg) havanin Ti°C'den Tf°C'ye cikarilmasi");
      pw.println("isteniyor. Gerekli boru boyunu (cm) bulunuz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    double T_bulk,Ro,Cp,Viskozite,k,Pr,V,Red,Nud,h;
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            Dic =(double)(int)(Math.random() * 10 + 10);
            Tboru_ic=(double)(int)(Math.random() * 50 + 100);
            m =(double)(int)(Math.random() * 25 +40);         //Havanýn debisi
            Ti=(double)(int)(Math.random()*10+15);            //Havanýn ilk sicakligi
            Ts=Tboru_ic-(double)(int)(Math.random()*20+20);  //Havanýn son sicakligi
            T_bulk=(Ti+Ts)/2;   //Bulk sýcaklýk °C
            Ro=hava.Ro(T_bulk);
            Cp=hava.Cp(T_bulk);
            Viskozite=hava.Viskozite(T_bulk);
            k=hava.k(T_bulk);
            Pr=hava.Pr(T_bulk);
            V=(m/3600) / (Ro*Math.PI*Math.pow(Dic/1000,2)/4);
            Red=Ro*V*(Dic/1000)/Viskozite;
            Nud=0.023*Math.pow(Red,0.8)*Math.pow(Pr,0.4);
            h=Nud*k/(Dic/1000);
            double q1=(m/3600)*(Cp*1000)*(Ts-Ti);
            double temp=h*Math.PI*(Dic/1000)*(Tboru_ic-T_bulk);
            L=q1/temp;      //metre
            L=L*100;        //cm
        }while(!(Red>2300+1000));  //1000 guvenlik payi
        //
        pw.println("Dic (mm)= "+String.valueOf(Dic));values[0]=Dic;
        pw.println("Tboru_ic°C= "+String.valueOf(Tboru_ic));values[1]=Tboru_ic;
        pw.println("m (kg)= "+String.valueOf(m));values[2]=m;
        pw.println("Ti°C= "+String.valueOf(Ti));values[3]=Ti;
        pw.println("Ts°C= "+String.valueOf(Ts));values[4]=Ts;
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
        Dic=values[0];pw.println("Dic (mm)= "+String.valueOf(Dic));
        Tboru_ic=values[1];pw.println("Tboru_ic°C= "+String.valueOf(Tboru_ic));
        m=values[2];pw.println("m (kg)= "+String.valueOf(m));
        Ti=values[3];pw.println("Ti°C= "+String.valueOf(Ti));
        Ts=values[4];pw.println("Ts°C= "+String.valueOf(Ts));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(L));mhtable.setCellNote(rownumber,11,"Gerekli boru boyu (cm)");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varL=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      boolean isanswered=false;
      //Give 5 chances to student
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      //Cevaplama sayýsýna göre soru deðerini hesapla
      //
      //double doublevalueforquestion=SorununNotDegeri-((intnumberofanswer-1)/5)*SorununNotDegeri;
      //yapýldýðýnda doublevalueforquestion deðeri hep 10 çýkmakta çünkü parantez içindeki deðer
      //integer sayýlýp bölüm 0.xx çýkýnca sýfýr yapýlmakta
      //
      double doublevalueforquestion=SorununNotDegeri*NotDusumAraligi[intnumberofanswer-1];
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
            out.writeChars("Gerekli boru boyunu (cm) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn boru boyu (cm) icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varL*(1-lowerRange);upperlimit[0]=varL*(1+upperRange);
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
          pw.println("Gerekli boru boyu icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
