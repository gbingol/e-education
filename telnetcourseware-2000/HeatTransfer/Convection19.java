import java.io.*;
//
//*********************
//
class Convection19 extends Questions{
    double Tort,Vhava,Dic,Lboru,dP,h;
    private final int rownumber=5;
    private final double SorununNotDegeri=15;
    public Convection19(){
      super();
      values=new double[4];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Ortalama Tort°C sicakligindaki atmosferik hava Vhava (m/s) hiz ile ic capi Dic (cm)");
      pw.println("olan bir boru icinden akmaktadir. L (m) uzunlugu boyunca, ");
      pw.println(" A) Ortaya cikan basinc dusumunu (kg/m2),  f=(0.316/Re^0.25)");
      pw.println(" B) Isi transfer katsayisini (W/m2K),      St*Pr^(2/3)=(f/8)");
      pw.println("NOT: Yukarida verilen esitlikleri kullaniniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double Re,Ro,Viskozite,KinematikViskozite,Pr,k,f,St,Nu;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Tort=(double)(int)(Math.random()*10+20);   //Havanýn ortalama sýcaklýðý °C
          Vhava=(double)(int)(Math.random()*10+10);    //Havanýn hýzý (m/s)
          Dic=(double)(int)(Math.random()*4+2);   //Camýn sýcaklýðý °C
          Lboru=(double)(int)(Math.random()*50+50);   //Borunun uzunluðu
          //
          Ro=hava.Ro(Tort);Viskozite=hava.Viskozite(Tort);KinematikViskozite=Viskozite/Ro;
          Pr=hava.Pr(Tort);k=hava.k(Tort);
          //
          Re=(Ro*Vhava*(Dic/100))/Viskozite;
          f=0.316/(Math.pow(Re,0.25));
          dP=f*(Lboru/(Dic/100))*Ro*Math.pow(Vhava,2)/(2*9.81);
          Nu=(f/8.0)*(Re*Pr)/Math.pow(Pr,2.0/3.0);
          h=Nu*k/(Dic/100);
        }while(!(Re>2300+500));
        //
        pw.println("Tort°C= "+String.valueOf(Tort));values[0]=Tort;
        pw.println("Vhava (m/s)= "+String.valueOf(Vhava));values[1]=Vhava;
        pw.println("Dic (cm)= "+String.valueOf(Dic));values[2]=Dic;
        pw.println("Lboru (m)= "+String.valueOf(Lboru));values[3]=Lboru;
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
        Tort=values[0];pw.println("Tort°C= "+String.valueOf(Tort));
        Vhava=values[1];pw.println("Vhava (m/s)= "+String.valueOf(Vhava));
        Dic=values[2];pw.println("Dic (cm)= "+String.valueOf(Dic));
        Lboru=values[3];pw.println("Lboru (m)= "+String.valueOf(Lboru));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(dP));mhtable.setCellNote(rownumber,11,"dP (kg/m2) sistem cevabi");
      mhtable.setCellValue(rownumber,12,String.valueOf(h));mhtable.setCellNote(rownumber,12,"h (W/m2K) icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double vardP=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varh=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Basinc dusumunu (kg/m2) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Isi transfer katsayisini (W/m2K) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn dP (kg/m2) icin cevabi");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn h (W/m2K) icin cevabi");
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=vardP*(1-lowerRange);upperlimit[0]=vardP*(1+upperRange);
          lowerlimit[1]=varh*(1-lowerRange);upperlimit[1]=varh*(1+upperRange);
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
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Basinc dusumu icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Isi transfer katsayisi icin "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
