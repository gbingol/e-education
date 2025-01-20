import java.io.*;
//
//*********************Uygulamada Taþýným 4 ve 5. soru**********************
//
class Convection4 extends Questions{
    double Thava,Tlevha,V,x1,x2;
    double Re1,Re2,Pr,k,Cp,Ro,Viskozite,Tf;
    private final int rownumber=4;
    private final double SorununNotDegeri=40;
    public Convection4(){
      super();
      values=new double[5];
      results=new double[3];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Thava°C sicakliginda bulunan hava V(m/s) duz bir levha uzerinden hýzla");
      pw.println("akmaktadýr .Levha tum uzunlugu boyunca Tlevha°C'ye isitilmistir. Buna gore,");
      pw.println("A) Ilk x1 ve x2 (cm)'lik bolgelerde transfer edilen isi miktarini (W)");
      pw.println("B) Ilk x2 (cm)'de COLBURN ANALOJISI kullanarak, ortaya çýkan surtunme");
      pw.println("   direncini (N) hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Thava=(double)(int)(Math.random()*5+25);   //Hava sýcaklýgý °C
          V=(double)(int)(Math.random()*4+1);    //Havanýn hýzý  (m/s)
          Tlevha=(double)(int)(Math.random()*20+40);   //Levha sýcaklýgý °C
          x1=(double)(int)(Math.random()*10+10);        //Ilk a cm lik uzaklýk
          x2=x1+(double)(int)(Math.random()*10+10);  //Ilk b cm lik uzaklýk b=a+herhangi bir sayý
          //
          Tf=(Tlevha+Thava)/2;
          Viskozite=hava.Viskozite(Tf);
          Ro=hava.Ro(Tf);
          k=hava.k(Tf);
          Pr=hava.Pr(Tf);
          Cp=hava.Cp(Tf);
          Re2=V*Ro*(x2/100)/Viskozite;
          Re1=V*Ro*(x1/100)/Viskozite;
          //
        }while(!(Re2<5*Math.pow(10,5)));    //Laminer kalma þartýna bak
        //
        pw.println("Thava°C= "+String.valueOf(Thava));values[0]=Thava;
        pw.println("V (m/s)= "+String.valueOf(V));values[1]=V;
        pw.println("Tlevha°C= "+String.valueOf(Tlevha));values[2]=Tlevha;
        pw.println("x1 (cm)= "+String.valueOf(x1));values[3]=x1;
        pw.println("x2 (cm)= "+String.valueOf(x2));values[4]=x2;
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
        Thava=values[0];pw.println("Thava°C= "+String.valueOf(Thava));
        V=values[1];pw.println("V (m/s)="  +String.valueOf(V));
        Tlevha=values[2];pw.println("Tlevha°C= "+String.valueOf(Tlevha));
        x1=values[3];pw.println("x1 (cm)= "+String.valueOf(x1));
        x2=values[4];pw.println("x2 (cm)= "+String.valueOf(x2));
      }
    }
    protected void CalculateWriteResults(){
      double Nu,hx,hort,A,q1,q2;
      //
      //Ýlk x1 cm için
      //
      Nu=0.332*Math.pow(Re1,1.0/2.0)*Math.pow(Pr,1.0/3.0);
      hx=Nu*k/(x1/100);
      hort=2*hx;
      A=(x1/100)*1;      //z yönünde birim derinlik alýnmýþtýr
      q1=hort*A*(Tlevha-Thava);
      //
      //Ýlk x2 cm için
      //
      Nu=0.332*Math.pow(Re2,1.0/2.0)*Math.pow(Pr,1.0/3.0);
      hx=Nu*k/(x2/100);
      hort=2*hx;
      A=(x2/100)*1;      //z yönünde birim derinlik alýnmýþtýr
      q2=hort*A*(Tlevha-Thava);
      //
      //Ilk x2 cm de COLBURN ANALOJISI
      //
      double St_ort=hort/(Ro*(Cp*1000)*V);
      double Cf_ort=2*St_ort*Math.pow(Pr,2.0/3.0); //Surtunme katsayisi
      double Tow_ort=Cf_ort*Ro*V*V/2;     //Yuzeydeki kayma direnci (N/m2)
      double SurtunmeDirenci=Tow_ort*(x2/100);      //SurtunmeDirenci=Kayma Direnci * Alan
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(q1));mhtable.setCellNote(rownumber,11,"Ilk x1 cm'lik bolgede transfer edilen isi (W)");
      mhtable.setCellValue(rownumber,12,String.valueOf(q2));mhtable.setCellNote(rownumber,12,"Ilk x2 cm'lik bolgede transfer edilen isi (W)");
      mhtable.setCellValue(rownumber,13,String.valueOf(SurtunmeDirenci));mhtable.setCellNote(rownumber,13,"x2 cm'lik bolgede surtunme direnci (N/m)");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[3];
      double totalpoint=0;
      double varq1=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varq2=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      double varSurtunmeDirenci=Double.valueOf(mhtable.getCellValue(rownumber,13)).doubleValue();
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
            out.writeChars("Ilk x1 (cm)'lik bolgede transfer edilen isi miktarini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("x2 (cm)'lik bolgede transfer edilen isi miktarini (W) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("x2 (cm)'lik bolgedeki Surtunme Direncini (N) giriniz: ");
            useranswer[2]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn x1 cm transfer edilen isi miktari (W)");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn x2 cm transfer edilen isi miktari (W)");
          mhtable.setCellValue(rownumber,18,String.valueOf(useranswer[2]));mhtable.setCellNote(rownumber,18,"Kullanýcýnýn x2cm lik bolge icin Surtunme Direnci (N)");
          //
          double upperlimit[]=new double[3];
          double lowerlimit[]=new double[3];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varq1*(1-lowerRange);upperlimit[0]=varq1*(1+upperRange);
          lowerlimit[1]=varq2*(1-lowerRange);upperlimit[1]=varq2*(1+upperRange);
          lowerlimit[2]=varSurtunmeDirenci*(1-lowerRange);upperlimit[2]=varSurtunmeDirenci*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[1])+" cevabiniz yanlis.");}
          //
          if(useranswer[2]<upperlimit[2]&&useranswer[2]>lowerlimit[2]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[2])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(rownumber,22,"0.000");pw.println(String.valueOf(useranswer[2])+" cevabiniz yanlis.");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("x1 cm'de isi miktari icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("x2 cm'de isi miktari icin "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          pw.println("x2 cm de surtunme direnci icin"+mhtable.getCellValue(rownumber,18)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
