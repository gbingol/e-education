import java.io.*;
//
//*********************Uygulamada
//
class Convection13 extends Questions{
    double Thava,Vhava,Dboru,Tboru,Lboru,h;
    private final int rownumber=4;
    private final double SorununNotDegeri=20;
    public Convection13(){
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
      pw.println(" 1 atm, Thava°C koþullarýnda bulunan hava Vhava (cm/s) ortalama hýz ile Dboru (mm)");
      pw.println("çapýndaki boru içinden geçirilmektedir. Boru yüzeyi Tboru°C’de sabit tutulmaktadýr.");
      pw.println("Eðer boru Lboru(m) uzunluðunda ise, ýsý transfer katsayýsýný h (W/m2°C) ");
      pw.println("hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double Tf,Rof,beta,Viskozitef,ViskoziteW,kf,Pr,Tb,ViskoziteB,Ref,Gr,Temp;
    double Temp2,Gz,Temp3,Temp4,Nu,P;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Thava=(double)(int)(Math.random()*10+20);   //Hava sýcaklýgý °C
          Vhava=(double)(int)(Math.random()*15+10);    //Havanýn hýzý  (cm/s)
          Dboru=(double)(int)(Math.random()*20+15);   //borunun çapý (mm)
          Tboru=(double)(int)(Math.random()*100+100);   //Borunun sýcaklýðý
          Lboru=(double)(int)(Math.random()*3+1);  //Borunun uzunluðu (m)
          Tf = (Tboru+Thava)/2;
          P=1*101325;   //Tablo okumada aksilik çýkmasýn diye herkese 1 atm veriliyor
          Rof = P/ (287 * Tf);
          beta = 1.0/Tf;
          Viskozitef = hava.Viskozite(Tf);
          ViskoziteW = hava.Viskozite(Tboru);
          kf = hava.k(Tf);
          Pr = hava.Pr(Tf);
          Tb = Thava;
          ViskoziteB = hava.Viskozite(Tb);
          Ref = (Rof * (Vhava/100)*(Dboru/1000)) /Viskozitef;
          Gr = (Math.pow(Rof,2) * 9.81 * beta * (Tboru - Thava) *Math.pow(Dboru/1000,3))/Math.pow(Viskozitef,2);
          Temp = Gr * Pr * (Dboru/ 1000) / Lboru;
        }while(!(Temp > 4000 && Temp<Math.pow(10,6) && Ref<Math.pow(10,3))); //Gr*Pr*D/L kýsýtýný grafikte müteakip formüle denk getirmek için
        Temp2 = Math.pow((ViskoziteB / ViskoziteW),0.14);
        Gz = Ref * Pr * (Dboru/1000)/Lboru;
        Temp3 = (Gz + 0.012 * Math.pow((Gz * Math.pow(Gr,0.333333333)),(4.0/3.0)));
        Temp4 = Math.pow(Temp3,(1.0/3.0));
        Nu = 1.75 * Temp2 * Temp4;
        h=kf/(Dboru/1000) * Nu;
        //
        pw.println("Thava°C= "+String.valueOf(Thava));values[0]=Thava;
        pw.println("Vhava (cm/s)= "+String.valueOf(Vhava));values[1]=Vhava;
        pw.println("Dboru (mm)= "+String.valueOf(Dboru));values[2]=Dboru;
        pw.println("Tboru°C= "+String.valueOf(Tboru));values[3]=Tboru;
        pw.println("Lboru (m)= "+String.valueOf(Lboru));values[4]=Lboru;
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
        Vhava=values[1];pw.println("Vhava (cm/s)= "+String.valueOf(Vhava));
        Dboru=values[2];pw.println("Dboru (mm)= "+String.valueOf(Dboru));
        Tboru=values[3];pw.println("Tboru°C= "+String.valueOf(Tboru));
        Lboru=values[4];pw.println("Lboru (m)= "+String.valueOf(Lboru));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(h));mhtable.setCellNote(rownumber,11,"Isi transfer katsayisi icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varh=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Isi transfer katsayisini (W/m2°C) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn isi transfer katsayisi icin cevabi");
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varh*(1-lowerRange);upperlimit[0]=varh*(1+upperRange);
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
          pw.println("Isi transfer katsayisi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
