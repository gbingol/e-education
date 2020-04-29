import java.io.*;
//
//*********************Uygulamada Taþýným 3. soru**********************
//
class Convection10 extends Questions{
    double D,Cp,Ro,k,Tbaslangic,Tortam,hyuzey,Tson,t;
    private final int rownumber=1;
    private final double SorununNotDegeri=20;
    public Convection10(){
      super();
      values=new double[8];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" D (cm) capindaki top (Cp  kJ/kg K;Yogunluk Ro(kg/m3); k  W/m°C) baþlangýçta");
      pw.println(" uniform olarak Tbaslangic°C’de iken aniden Tortam°C sýcaklýðýnda sabit ");
      pw.println("tutulan bir ortama býrakilmaktadir. Yuzey tasinim isi transfer katsayisi ");
      pw.println("hyuzey (W/m2°C)’tir. Topun sýcaklýðýnýn Tson°C’ye düþmesi için geçecek zamaný ");
      pw.println("hesaplayýnýz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
      double r,V,A,Biot,Temp1,Temp2;
        do{
          do{
            D =(double)(int)(Math.random() * 5 +2);
            Cp =Math.random() + 0.1;
            Ro =(double)(int)(Math.random() * 5000 + 500);
            k =(double)(int)(Math.random()*25+25);
            Tbaslangic=(double)(int)(Math.random() * 250 + 250);
            Tortam=(double)(int)(Math.random() * 100 + 100);
            hyuzey=(double)(int)(Math.random() * 25 +10);
            Tson=(double)(int)(Math.random() * 100 + Tortam);
            r = (D/ 2.0) / 100;
            V = 4.0 / 3.0 * Math.PI * Math.pow(r,3);
            A = 4 *Math.PI *Math.pow(r,2);
            Biot = (hyuzey * (V / A)) / k;
            Temp1 = hyuzey * A / (Ro * V * Cp * 1000);
            Temp2 = (Tson - Tortam) / (Tbaslangic - Tortam);
          }while(!((Biot < 0.1)&&(Tbaslangic / Tson > 1.25)&&(Tson /Tortam>1.25)&&(Tbaslangic/Tortam>1.25)));
            t=-Math.log(Temp2)/Temp1;
        }while(!((t>1000)&&(t<7500)));    //Zaman makul bir deger olsun
        //
        pw.println("D(cm)= "+String.valueOf(D));values[0]=D;
        pw.println("Cp (kJ/kgK)= "+String.valueOf(Cp));values[1]=Cp;
        pw.println("Ro (kg/m3)= "+String.valueOf(Ro));values[2]=Ro;
        pw.println("k (W/m°C)= "+String.valueOf(k));values[3]=k;
        pw.println("Tbaslangic°C= "+String.valueOf(Tbaslangic));values[4]=Tbaslangic;
        pw.println("Tortam°C= "+String.valueOf(Tortam));values[5]=Tortam;
        pw.println("hyuzey (W/m2°C)= "+String.valueOf(hyuzey));values[6]=hyuzey;
        pw.println("Tson°C= "+String.valueOf(Tson));values[7]=Tson;
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
        D=values[0];pw.println("D(cm)= "+String.valueOf(D));
        Cp=values[1];pw.println("Cp (kJ/kgK)= "+String.valueOf(Cp));
        Ro=values[2];pw.println("Ro (kg/m3)= "+String.valueOf(Ro));
        k=values[3];pw.println("k (W/m°C)= "+String.valueOf(k));
        Tbaslangic=values[4];pw.println("Tbaslangic°C= "+String.valueOf(Tbaslangic));
        Tortam=values[5];pw.println("Tortam°C= "+String.valueOf(Tortam));
        hyuzey=values[6];pw.println("hyuzey (W/m2°C)= "+String.valueOf(hyuzey));
        Tson=values[7];pw.println("Tson°C= "+String.valueOf(Tson));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(t));mhtable.setCellNote(rownumber,11,"t (sn) icin sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double vart=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Gecen sureyi (sn) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn t (sn) icin cevabi");
          //
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=vart*(1-lowerRange);upperlimit[0]=vart*(1+upperRange);
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
          pw.println("Gecen sure icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
