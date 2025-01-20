import java.io.*;
//
// 1. Odev 2. Soru----> MSAccess'teki Tablosu Odev1_2----> Kürenin alaný ve hacmi hesaplattýrýlýyor
//
class Odev1_2 extends Questions{
    double r,A,V;
    private final String TableName="Odev1_2";
    private final double SorununNotDegeri=10;
    public Odev1_2(){
      super();
      values=new double[1];
      results=new double[2];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      pw.println("");
      pw.println("************************************************************************");
      pw.println("*****************************   SORU 1  ********************************");
      pw.println("Yaricapi r (cm) olan kurenin,");
      pw.println("A) Alanýný (m^2)");
      pw.println("B) Hacmini (m^3) hesaplayýnýz.");
      pw.println("************************************************************************");
      pw.println("");
    }
    public void printRandomValues(PrintWriter pw){
      boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);
      if(!IsValuesGiven){
        r=(double)(int)(Math.random()*50+50);pw.println("r (cm)= "+String.valueOf(r));values[0]=r;
        CalculateWriteResults();
        for(int i=0;i<values.length;i++){
          String ColumnToSet="Value"+String.valueOf(i+1);
          dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
        }
        dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
      }else{
        pw.println("1. Soru Icin Degerlerinizi zaten almýþtýnýz.");
        for(int i=0;i<values.length;i++){
          String ColumnToSet="Value"+String.valueOf(i+1);
          String str=dbc.getSelectedCell(username,ColumnToSet,TableName);
          values[i]=Double.valueOf(str).doubleValue();
        }
        r=values[0];pw.println("r (cm)= "+String.valueOf(r));
      }
    }
    protected void CalculateWriteResults(){
        //
        double varr=r/100;
        A=4*Math.PI*Math.pow(varr,2);
        V=(4.0/3.0)*Math.PI*Math.pow(varr,3);
       dbc.updateString("Number",username,"SystemAnswer1",TableName,String.valueOf(A));
       dbc.updateString("Number",username,"SystemAnswer2",TableName,String.valueOf(V));
    }
    
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      double useranswer[]=new double[results.length];
      double totalpoint=0;
      double varA=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
      double varV=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer2",TableName)).doubleValue ();
      boolean isanswered=false;
      String numberofanswer=dbc.getSelectedCell(username,"NumberOfAnswer",TableName);

      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {
        numberofanswer="1";
        dbc.updateString("Number",username,"NumberOfAnswer",TableName,String.valueOf(1));
      }
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
      
      //Calculate value of question regarding the number of answers given by student
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
            pw.println("Bu sizin "+numberofanswer+". denemeniz. Su an icin soru degeri "+strvalueforquestion);
            out.writeChars("Kurenin alanini giriniz (m^2):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Kurenin hacmini giriniz (m^3):");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu deðerleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          intnumberofanswer++;
          dbc.updateString("Number",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); //
          dbc.updateString("Number",username,"UserAnswer1",TableName,String.valueOf(useranswer[0]));
          dbc.updateString("Number",username,"UserAnswer2",TableName,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          lowerlimit[0]=varA*(1-lowerRange);upperlimit[0]=varA*(1+upperRange);
          lowerlimit[1]=varV*(1-lowerRange);upperlimit[1]=varV*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
          }else{pw.println(String.valueOf(useranswer[1])+" cevabiniz yanlis.");}
          //
          dbc.updateString("Number",username,"NOTU",TableName,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if

        else{
          pw.println("********** 1.soruyu zaten cevaplamistiniz. **********");
          pw.println("Alan için "+dbc.getSelectedCell(username,"UserAnswer1",TableName)+" (m^2) girdiniz");
          pw.println("Hacim için "+dbc.getSelectedCell(username,"UserAnswer2",TableName)+" (m^3) girdiniz");
          double total=Double.valueOf(dbc.getSelectedCell(username,"NOTU",TableName)).doubleValue ();
          pw.println("Bu sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 1");}
    }
}
