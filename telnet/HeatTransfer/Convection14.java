import java.io.*;
//
//*********************
//
class Convection14 extends Questions{
    double Camyukseklik,Camgenislik,Tcam,Toda,q;
    private final int rownumber=5;
    private final double SorununNotDegeri=20;
    public Convection14(){
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
      pw.println(" Bir þömine önüne kývýlcým sýçramasýný ve odaya duman kacmasini önlemek üzere");
      pw.println("Camyukseklik (m) yüksekliðinde, Camgenislik (m) genisliginde cam yerleþtirilmiþtir.");
      pw.println("Camin yüzey sýcaklýðý bu sekilde Tcam°C’ye yükselmektedir. Eðer oda sicakligi");
      pw.println("Toda°C ise odaya ýsý kaybý (W) ne kadardýr? Isinimin etkisi ihmal edilecektir.");
      pw.println("Çözüm sýrasýnda Churchill ve Chu eþitliklerinden yararlanýlacaktýr.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double A,Tf,Prf,beta,KinematikViskozite,kf,Gr,Ra;
    double Temp,Temp2,Temp3,Nu,h,Height,WidthCam;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          Camyukseklik=Math.random()*1+0.5;   //Camýn yuksekliði (m)
          Camgenislik=Math.random()*1+0.5;    //Camin genisliði (m)
          Tcam=(double)(int)(Math.random()*100+200);   //Camýn sýcaklýðý °C
          Toda=(double)(int)(Math.random()*10+15);   //Oda sýcaklýðý
          //
          Height=Camyukseklik;
          WidthCam=Camgenislik;
          A = Height* WidthCam;           //Alan
          Tf = (Tcam+Toda)/2;
          beta = 1 / (Tf+273.5);
          Prf = hava.Pr(Tf);
          KinematikViskozite = hava.Viskozite(Tf) / hava.Ro(Tf);
          kf = hava.k(Tf);
          Gr = (9.81 * beta * (Tcam-Toda) *Math.pow(Height,3)) /Math.pow(KinematikViskozite,2);  //Cam dikey durumda olduðundan karakteristik uzunluk Height dýr
          Ra = Gr * Prf;
        }while(!(Ra > 0.1&& Ra<Math.pow(10,12))); //Gr*Pr*D/L kýsýtýný grafikte müteakip formüle denk getirmek için
        Temp = 0.387 * Math.pow(Ra,(1.0/6.0));
        Temp2 = Math.pow((0.492/Prf),(9.0/16.0));
        Temp3 = Math.pow((1 + Temp2),(8.0/27.0));
        Nu = Math.pow((0.825 + Temp / Temp3),2);
        h = Nu * kf / Height;
        q=h * A * (Tcam-Toda);
        //
        pw.println("Camyukseklik (m)= "+String.valueOf(Camyukseklik));values[0]=Camyukseklik;
        pw.println("Camgenislik (m)= "+String.valueOf(Camgenislik));values[1]=Camgenislik;
        pw.println("Tcam°C= "+String.valueOf(Tcam));values[2]=Tcam;
        pw.println("Toda°C= "+String.valueOf(Toda));values[3]=Toda;
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
        Camyukseklik=values[0]; pw.println("Camyukseklik (m)= "+String.valueOf(Camyukseklik));
        Camgenislik=values[1];pw.println("Camgenislik (m)= "+String.valueOf(Camgenislik));
        Tcam=values[2];pw.println("Tcam°C= "+String.valueOf(Tcam));
        Toda=values[3];pw.println("Toda°C= "+String.valueOf(Toda));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(q));mhtable.setCellNote(rownumber,11,"Isý kaybi (W) sistem cevabi");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[1];
      double totalpoint=0;
      double varq=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
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
            out.writeChars("Odaya olan isi kaybini (W) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn isi kaybi (W) icin cevabi");
          double upperlimit[]=new double[1];
          double lowerlimit[]=new double[1];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varq*(1-lowerRange);upperlimit[0]=varq*(1+upperRange);
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
          pw.println("Isi kaybi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
