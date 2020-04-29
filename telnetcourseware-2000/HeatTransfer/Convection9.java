import java.io.*;
//
//*********************Uygulamada Taþýným 11. soru**********************
//
class Convection9 extends Questions{
    double a,b,V,L,Tsu_ort,h,dP;
    private final int rownumber=5;
    private final double SorununNotDegeri=15;
    public Convection9(){
      super();
      values=new double[5];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" Demir sactan a (cm)*b(cm)'lik bir kanalda su V (m/s) ortalama hiz ile");
      pw.println("akmaktadir. Kanal L(m) uzunlugunda olup, suyun ortalama sicakligi ");
      pw.println("Tsu_ort°C'dir. Su kanali tamamen doldurmaktadir. Yuzey film katsayisini");
      pw.println("h(W/m2°C) ve boru boyunca basinc dusumunu kg/m2) hesaplayiniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    double T_bulk,Ro,Cp,Viskozite,k,Pr,Nu,DH,Re,f;
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
            a =(double)(int)(Math.random() * 5 + 5);  //cm
            b=(double)(int)(Math.random() * 5 + 5);   //cm
            V=(double)(int)(Math.random()*5+5); //m/s
            L =(double)(int)(Math.random() * 5 +5);         //Kanalýn uzunlugu (m)
            Tsu_ort=(double)(int)(Math.random()*10+20);            //Suyun ortalama sicakligi
            T_bulk=Tsu_ort;   //bulk sýcaklýk ortalama sýcaklýga esittir.
            Ro=su.Ro (T_bulk);
            Viskozite=su.Viskozite(T_bulk);
            k=su.k(T_bulk);
            Pr=su.Pr(T_bulk);
            //Hidrolik çap DH=4A/P  P=ýslak çevre  (metre)
            DH=(4*(a/100*b/100))/(2*(a/100+b/100));
            Re=Ro*V*DH/Viskozite;
            Nu=0.023*Math.pow(Re,0.8)*Math.pow(Pr,0.4);
            h=Nu*k/DH;    //Yuzey film katsayisi
            f=0.316/(Math.pow(Re,1.0/4.0));
            dP=f*L/DH*Ro*(Math.pow(V,2)/(2*9.81));
        }while(!(Re>2300+1000));  //1000 guvenlik payi
        //
        pw.println("a (cm)= "+String.valueOf(a));values[0]=a;
        pw.println("b (cm)= "+String.valueOf(b));values[1]=b;
        pw.println("L (m)= "+String.valueOf(L));values[2]=L;
        pw.println("V (m/s)= "+String.valueOf(V));values[3]=V;
        pw.println("Tsu_ort°C= "+String.valueOf(Tsu_ort));values[4]=Tsu_ort;
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
        a=values[0];pw.println("a (cm)= "+String.valueOf(a));
        b=values[1];pw.println("b (cm)= "+String.valueOf(b));
        L=values[2];pw.println("L (m)= "+String.valueOf(L));
        V=values[3];pw.println("V (m/s)= "+String.valueOf(V));
        Tsu_ort=values[4];pw.println("Tsu_ort°C= "+String.valueOf(Tsu_ort));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(h));mhtable.setCellNote(rownumber,11,"Yuzey film katsayisi");
      mhtable.setCellValue(rownumber,12,String.valueOf(dP));mhtable.setCellNote(rownumber,12,"Basinc dusumu");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varh=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double vardP=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
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
            out.writeChars("Yuzey film katsayisini h (W/m2°C) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Boru boyunca basinc dusumunu (kg/m2) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Kullanýcýnýn yuzey film katsayisi icin cevabi");
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));mhtable.setCellNote(rownumber,17,"Kullanýcýnýn basinc dusumu icin cevabi");
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          //
          //If the value to be evaluated <0 then take symetry to y-axis,this should also be applied to user answer
          //if(varT_duvardis<0) {varT_duvardis=-varT_duvardis;useranswer[2]=-useranswer[2];}
          //
          lowerlimit[0]=varh*(1-lowerRange);upperlimit[0]=varh*(1+upperRange);
          lowerlimit[1]=vardP*(1-lowerRange);upperlimit[1]=vardP*(1+upperRange);
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
          pw.println("Yuzey film katsayisi icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Basinc dusumu icin "+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
