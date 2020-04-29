import java.io.*;
//
// 1. Odev 3. Soru----> MSAccess'teki Tablosu Odev1_3---->Manometrede mutlak bas�n� soruluyor
//
class Odev1_3 extends Questions{
    double Ro,h,Patm,Pmutlak;
    private final String TableName="Odev1_3";
    private final double SorununNotDegeri=20;
    public Odev1_3(){
      super();
      values=new double[3];
      results=new double[1];
    }
    public Questions getQuestion(){
      return this;
    }
    public void print(PrintWriter pw){
      
      String s="************************************************************************"+"\n";
      s+="************************************************************************"+"\n";
     
      s+="*****************************   SORU 1  ********************************"+"\n";
      s+="Kitabinizda Sekil 1-38'de gosterilen kaptaki basinc bir manometreyle"+"\n";
      s+="olculmektedir. Manometre sivisinin ozgul agirligi Ro olup, sutun"+"\n";
      s+="farki h (cm)'dir. Yerel atmosfer basinci Patm (kPa) olduguna gore"+"\n";
      s+="kap icindeki mutlak basinc (kPa) ne kadardir."+"\n";
      s+="************************************************************************");
      s+="";

      pw.print(s);
    }


    public void printRandomValues(PrintWriter pw)
    {
      boolean IsValuesGiven=dbc.IsValueTaken("IsValueTaken","Number",TableName,username);

      if(!IsValuesGiven)
      {
        do{
          Ro=Math.random()*1;
          h=(double)(int)(Math.random()*50+25);
          Patm=(double)(int)(Math.random()*10+95);
        }while(!((Ro>0.5)&&(Ro<0.95)));
        //
        pw.println("Ro= "+String.valueOf(Ro));
        values[0]=Ro;

        pw.println("h (cm)= "+String.valueOf(h));
        values[1]=h;

        pw.println("Patm (kPa)= "+String.valueOf(h));v
        alues[2]=Patm;
        //
        CalculateWriteResults();
        //
        for(int i=0;i<values.length;i++)
        {
          String ColumnToSet="Value"+String.valueOf(i+1);
          dbc.updateString("Number",username,ColumnToSet,TableName,String.valueOf(values[i]));
        }
        dbc.updateBoolean("NUMBER",username,"IsValueTaken",TableName,true);
      }
      else
      {
        pw.println("3. Soru Icin Degerlerinizi zaten almistiniz.");
        for(int i=0;i<values.length;i++)
        {
          String ColumnToSet="Value"+String.valueOf(i+1);
          String str=dbc.getSelectedCell(username,ColumnToSet,TableName);
          values[i]=Double.valueOf(str).doubleValue();
        }

        Ro=values[0];pw.println("Ro= "+String.valueOf(Ro));
        h=values[1];pw.println("h (cm)= "+String.valueOf(h));
        Patm=values[2];pw.println("Parm (kPa)= "+String.valueOf(Patm));
      }
    }


    protected void CalculateWriteResults(){
        //
        final double Ro_su=1000;
        double Rosivi=Ro*Ro_su;                       //S�v�n�n OzgulHacimi=S�v�n�n yo�unlu�u / Suyun yo�unlu�u
        Pmutlak=Patm+(Rosivi*9.807*(h/100))/1000;    //P=Pmutlak+Ro*g*h
       dbc.updateString("Number",username,"SystemAnswer1",TableName,String.valueOf(Pmutlak));
    }
    
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      double useranswer[]=new double[results.length];
      double totalpoint=0;
      double varPmutlak=Double.valueOf(dbc.getSelectedCell(username,"SystemAnswer1",TableName)).doubleValue ();
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
            out.writeChars("Mutlak basinci giriniz (kPa):");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            for(int i=0;i<useranswer.length;i++) pw.println(String.valueOf(i+1)+". deger icin "+String.valueOf(useranswer[i])+" girdiniz");
            out.writeChars("Bu de�erleri kabul ediyor musunuz [Y/N]:");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          intnumberofanswer++;
          dbc.updateString("Number",username,"NumberOfAnswer",TableName,String.valueOf(intnumberofanswer)); //
          dbc.updateString("Number",username,"UserAnswer1",TableName,String.valueOf(useranswer[0]));
          //
          double upperlimit[]=new double[results.length];
          double lowerlimit[]=new double[results.length];
          lowerlimit[0]=varPmutlak*(1-lowerRange);upperlimit[0]=varPmutlak*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
          }else{pw.println(String.valueOf(useranswer[0])+" cevabiniz yanlis.");}
          //
          dbc.updateString("Number",username,"NOTU",TableName,String.valueOf(totalpoint));
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if

        else{
          pw.println("********** Bu soruyu zaten cevaplamistiniz. **********");
          pw.println("Mutlak basinc "+dbc.getSelectedCell(username,"UserAnswer1",TableName)+" (kPa) girdiniz");
          double total=Double.valueOf(dbc.getSelectedCell(username,"NOTU",TableName)).doubleValue ();
          pw.println("Bu sorudan toplam "+String.valueOf(total)+" ald�n�z.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question 1");}
    }
}
