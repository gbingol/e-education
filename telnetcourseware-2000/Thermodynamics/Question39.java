import java.io.*;
//
//***************************9th QUESTION*******************************
//
class Question39 extends Questions{
    double T1b,T2b,T1,T2,m,V;
    public Question39(){
      super();
      values=new double[6];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("devres.net'de sekli verilen sistemden yararlanilarak T1b°C kosullarinda isi");
      pw.println("degistiriciye giren doymus sivi amonyak T2b°C kosullarinda doymus buhar    ");
      pw.println("haline getirilecektir.  Bu islem icim T1°C'de sisteme girip T2°C'de ciktigi ");
      pw.println("kabul edilen baca gazlari kullanilacaktir.  Isi degistiricideki amonyak dolasimi");
      pw.println("dakikada m(kg)'dir.  Sistemi terk eden buhar hizinin v(m/s) olmasi istenmektedir.");
      pw.println("Baca gazinin ozgul isisi 1(kJ/kgK) olarak hesaplanmistir.");
      pw.println("A) Baca gazi debisi (kg/dak) ne olmalidir.");
      pw.println("B) Amonyak buhari cikis borusu capi (cm) ne olmalidir?");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(9,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          T1b=(Math.random()*10+10);
          T2b=(Math.random()*15+25);
          T1=(Math.random()*100+100);
          T2=T1-(Math.random()*50+50);
          m=Math.random()*15+15;
          V=Math.random()*10+5;
        }while(!((T2b/T1b>1.25)&&(T2>10)));
        pw.println("T1b°C= "+String.valueOf(T1b));values[0]=T1b;
        pw.println("T2b°C= "+String.valueOf(T2b));values[1]=T2b;
        pw.println("T1°C= "+String.valueOf(T1));values[2]=T1;
        pw.println("T2°C= "+String.valueOf(T2));values[3]=T2;
        pw.println("m (kg)= "+String.valueOf(m));values[4]=m;
        pw.println("V (m/s)= "+String.valueOf(V));values[5]=V;
        CalculateWriteResults();
        //Store the given values in MHTable
        for(int i=0;i<values.length;i++){
          mhtable.setCellValue(9,i+1,String.valueOf(values[i]));
          mhtable.setCellNote(9,i+1,"Sistemin verdigi degerler");
        }
        mhtable.setCellValue(9,0,"ValueIsGiven");
        IsValuesGiven=true;
      }else{
        pw.println("9. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          values[i]=Double.valueOf(mhtable.getCellValue(9,i+1)).doubleValue();
        }
        T1b=values[0];pw.println("T1b°C= "+String.valueOf(T1b));
        T2b=values[1];pw.println("T2b°C= "+String.valueOf(T2b));
        T1=values[2];pw.println("T1°C= "+String.valueOf(T1));
        T2=values[3];pw.println("T2°C= "+String.valueOf(T2));
        m=values[4];pw.println("m (kg)= "+String.valueOf(m));
        V=values[5];pw.println("V (m/s)= "+String.valueOf(V));
      }
    }
    protected void CalculateWriteResults(){
        double hfb,hgb,dh_bacagazi,mg,Acikis,dcikis;
        hfb=doymusAmonyak.Hf(T1b);
        hgb=doymusAmonyak.Hg(T2b);
        double v_amonyak=doymusAmonyak.Vg(T2b);
        dh_bacagazi=1*(T1-T2);
        double amonyakin_enerjisi=m*(hgb-hfb);
        mg=amonyakin_enerjisi/dh_bacagazi;   // kg/dak
        Acikis=m*v_amonyak/(V*60);
        dcikis=Math.sqrt(Acikis*4.0/Math.PI)*100;   //cm
        //Record them to MHTable
        mhtable.setCellValue(9,7,String.valueOf(mg));mhtable.setCellNote(9,7,"Baca gazi cikis debisi icin dogru cevap");
        mhtable.setCellValue(9,8,String.valueOf(dcikis));mhtable.setCellNote(9,8,"Cikis borusu cap'i icin dogru cevap");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varmg=Double.valueOf(mhtable.getCellValue(9,7)).doubleValue();
      double vardcikis=Double.valueOf(mhtable.getCellValue(9,8)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(11,9);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(11,9,1);}
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
            out.writeChars("Baca gazi debisini (kg/dak) giriniz:");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Amonyak buhari cikis borusu capini (cm) giriniz.");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(11,9,1);
          mhtable.setCellValue(9,9,String.valueOf(useranswer[0]));mhtable.setCellNote(9,9,"Kullanicinin baca gazi icin cevabi");
          mhtable.setCellValue(9,10,String.valueOf(useranswer[1]));mhtable.setCellNote(9,9,"Kullanicinin boru capi icin cevabi");
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          lowerlimit[0]=varmg*(1-lowerRange);upperlimit[0]=varmg*(1+upperRange);
          lowerlimit[1]=vardcikis*(1-lowerRange);upperlimit[1]=vardcikis*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/2;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(9,11,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/2;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{mhtable.setCellValue(9,11,"0.000");}
          //
          mhtable.setCellValue(9,11,String.valueOf(totalpoint));
          mhtable.setCellNote(9,11,"Kullanicinin bu sorudan aldigi not");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");
          //
        }//if
        else{
          pw.println("********** 9.soruyu zaten cevaplamistiniz. **********");
          pw.println("Baca gazi debisi icin "+mhtable.getCellValue(9,9)+" girdiniz");
          pw.println("Boru capi icin "+mhtable.getCellValue(9,10)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(9,11)).doubleValue ();
          pw.println("9. Sorudan toplam "+String.valueOf(total)+" aldiniz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 9");}
    }
}
