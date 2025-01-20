import java.io.*;
//
//*********************Uygulamada Taþýným 2. soru**********************
//
class Convection11 extends Questions{
    double D,L,V,t;
    private final int rownumber=2;
    private final double SorununNotDegeri=20;
    public Convection11(){
      super();
      values=new double[3];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println(" D (cm) çapýnda, L (cm) uzunluðunda bir silindirik metal eleman baþlangýçta  ");
      pw.println("100°C'de iken 50°C'de bulunan V (m/s) hýz ile akan suyun içine býrakýlmaktadýr.");
      pw.println("Metal eleman sýcaklýðýnýn 75°C'ye inmesi için geçen süreyi hesaplayýnýz.");
      pw.println("Eleman ile sývý arasýndaki taþýným ýsý transfer katsayýsýnýn hesaplanmasý ");
      pw.println("sýrasýnda aþaðýdaki ifade kullanýlacaktýr. Film özellikler için ortalama ");
      pw.println("82.22°C alýnabilmektedir.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    double h,Volume,A,Biot,Temp1,Temp2;
    final double Ro = 970.2,Viskozite=3.47*Math.pow(10,-4),k=0.673,Pr = 2.16;
    final double kAl = 206,CpAl=896,RoAl = 2707,T0=100,Tsonsuz=50,T = 75;
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
      double r,Re;
        do{
          do{
            D =(double)(int)(Math.random() * 5 + 1);   //Metal elemanýn çapý (cm)
            L =(double)(int)(Math.random() * 10 + 1);   //Metal elemanýn uzunluðu (cm)
            V =(double)(int)(Math.random() * 5 + 1);    //Akan suyun hýzý (m/s)
            r = (D/2) / 100;
            Re = (Ro*V*D/100)/Viskozite;
          }while(!((Re > 0.4) && (Re < 400000)));
          h = HeatTransferFunctions.H_CylinderandSphere(D, k, Re, Pr);
          Volume = Math.PI *Math.pow(r,2)*L/100;
          A = 2 * Math.PI * r * (r + L/100);
          Biot = (h * (Volume / A)) / kAl;
          Temp1 = h * A / (RoAl * Volume * CpAl);
          Temp2 = (T - Tsonsuz) / (T0 - Tsonsuz);
        }while(!(Biot<0.1));
        t=-Math.log(Temp2)/Temp1;
        //
        pw.println("D (cm)= "+String.valueOf(D));values[0]=D;
        pw.println("L (cm)= "+String.valueOf(L));values[1]=L;
        pw.println("V (m/s)= "+String.valueOf(V));values[2]=V;
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
        D=values[0];pw.println("D (cm)= "+String.valueOf(D));
        L=values[1];pw.println("L (cm)= "+String.valueOf(L));
        V=values[2];pw.println("V (m/s)= "+String.valueOf(V));
      }
    }
    protected void CalculateWriteResults(){
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(t));mhtable.setCellNote(rownumber,11,"Gecen sure (sn) icin sistem cevabi");
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
            out.writeChars("Gecmesi gereken sureyi (sn) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));mhtable.setCellNote(rownumber,16,"Gecmesi gerekn sure (sn) icin cevabi");
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
          pw.println("Gemesi gereken sure (sn) icin "+mhtable.getCellValue(rownumber,16)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
    //
}
