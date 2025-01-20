import java.io.*;
//
//*********************EVAPORATIVE COOLING**********************
//
class QuestionPsychrometry6 extends Questions{
    double Tin,RHin,RHex,Tex,Tmin,Tyt,P;
		double Pws,Pw,W,Pwsyas,Wsyas;
		double AA,Y,X,DD1,DD2,varsubTyas,varSubT,DF1,DF2;
    private final int rownumber=6;
    private final double SorununNotDegeri=10;
    public QuestionPsychrometry6(){
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
      pw.println("Hava buharlaþmalý bir soðutucuya P (kPa) basýnç,Tgiris°C ve %RHgiris");
      pw.println("bagýl nemde girmekte, ve %RHcikis baðýl nemde çýkmaktadýr. ");
      pw.println("A) Havanýn çýkýþ sýcaklýðýný (°C),");
      pw.println("B) Havanýn soðutulabileceði minimum sýcaklýðý hesaplayýnýz.");
      pw.println("IPUCU: Yaþ termometre sýcaklýðý ve entalpiyi sabit kabul ediniz.");
      pw.println("************************************************************************");
    }
    public void printRandomValues(PrintWriter pw){
    String str=mhtable.getCellValue(rownumber,0);
    if(str.equals("ValueIsGiven")) IsValuesGiven=true;
      if(!IsValuesGiven){
        do{
          P=(double)(int)(Math.random()*10+95);		//kPa olarak okunuyor.
			    Tin=(double)(int)(Math.random()*10+25);
    			RHin=(double)(int)(Math.random()*10+15);
		    	RHex=(double)(int)(Math.random()*25+60);
    			Pws=pf.Pws(Tin);
    			Pw=RHin/100*Pws;
		    	W=pf.W2(P*1000,Pw);
    			Tyt=pf.Tyas1521(P*1000,W,Tin);
		    	Pwsyas=pf.Pwsyas(Tyt);
    			Wsyas=pf.Wsyas(P*1000,Pwsyas);
    			varSubT = Tyt;
		    	do{
				    varSubT = varSubT + 0.1;
    				Pws = pf.Pws(varSubT);
		    		W = pf.W2(P*1000, RHex/100 * Pws);
				    varsubTyas = pf.Tyas(Wsyas, W, varSubT);
    			}while(!(Math.abs(varsubTyas - Tyt) <1));
		    	X = varSubT - 0.5; Y = varSubT + 0.5;
    			do{
		    		AA = (Y - X) / 3;
    				DD1 = X + AA; DD2 = Y - AA;
		    		Pws = pf.Pws(DD1);
    				W = pf.W2(P*1000, (RHex/100 * Pws));
		    		varsubTyas = pf.Tyas(Wsyas, W, DD1);
    				DF1 = varsubTyas - Tyt;
		    		Pws = pf.Pws(DD2);
    				W = pf.W2(P*1000, (RHex/100 * Pws));
		    		varsubTyas = pf.Tyas(Wsyas, W, DD2);
				    DF2 = varsubTyas - Tyt;
    				if(Math.abs(DF1) < Math.abs(DF2)){
		  	  		if(DF1<0){X = DD1; Y = DD1 + AA;}
			  	  	if(DF1>0){X = DD1 - AA; Y = DD1;}
				  	  if(DF1==0){varsubTyas = DD2;}
				    }else{
					    if(DF2<0){X = DD2; Y = DD2 + AA;}
  					  if(DF2>0){X = DD2 - AA; Y = DD2;}
	  				  if(DF2==0){varsubTyas = DD1;}
		  		  }
			    }while(!((Math.abs(DD1 - DD2) < 0.0000000001)||(Math.abs(varsubTyas - Tyt) < 0.0000000001)));
			    Tex = DD1;  // çýkýþ sýcaklýðý
        }while(!((Tyt<(Tex-2))));
        //
        pw.println("P (kPa)= "+String.valueOf(P));values[0]=P;
        pw.println("Tin°C= "+String.valueOf(Tin));values[1]=Tin;
        pw.println("%RHin= "+String.valueOf(RHin));values[2]=RHin;
        pw.println("%RHex= "+String.valueOf(RHex));values[3]=RHex;
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
        P=values[0];pw.println("P (kPa)= "+String.valueOf(P));
        Tin=values[1];pw.println("Tin°C= "+String.valueOf(Tin));
        RHin=values[2];pw.println("%RHin="  +String.valueOf(RHin));
        RHex=values[3];pw.println("%RHex= "+String.valueOf(RHex));
      }
    }
    protected void CalculateWriteResults(){
        double varTex=0,varTmin=0;
        varTex=Tex;   //Havanýn çýkýþ sýcaklýðý
        varTmin=Tyt;  //Havanýn soðutulabileceði minimum sýcaklýk
      //Record them to MHTable
      mhtable.setCellValue(rownumber,11,String.valueOf(varTex));mhtable.setCellNote(rownumber,11,"Havanýn çýkýþ sýcaklýðý");
      mhtable.setCellValue(rownumber,12,String.valueOf(varTmin));mhtable.setCellNote(rownumber,12,"Havanýn soðutulabileceði minimum sýcaklýk");
    }
    public void GradeIt(DataOutputStream out,BufferedReader in,PrintWriter pw){
      //Correct answers for the question is read from MHTable cause when random values are given
      //answers are written to MHTable,however values to be evaluated are taken from student
      //answer of the students should be in the range of lowerlimit<answer<upperlimit
      double useranswer[]=new double[2];
      double totalpoint=0;
      double varTex=Double.valueOf(mhtable.getCellValue(rownumber,11)).doubleValue();
      double varTmin=Double.valueOf(mhtable.getCellValue(rownumber,12)).doubleValue();
      boolean isanswered=false;
      String numberofanswer=mhtable.getCellValue(rownumber,21);
      //Give 5 chances to student
      if(numberofanswer.equals("6")) isanswered=true;
      if(numberofanswer.equals(""))  {numberofanswer="1";updateCell(rownumber,21,1);}
      //Calculate value of question regarding the number of answers given by student
      int intnumberofanswer=Integer.valueOf(numberofanswer).intValue();
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
            out.writeChars("Havanin cikis sicakligini (°C) giriniz: ");
            useranswer[0]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Havanin sogutulabilecegi minimum sicakligi (°C) giriniz: ");
            useranswer[1]=Double.valueOf(in.readLine().trim()).doubleValue();
            out.writeChars("Bu degerleri kabul ediyor musunuz [Y/N]: ");
            String yesorno=in.readLine().trim().toUpperCase();
            if(yesorno.equals("Y")) isaccepted=true;
          }while(!isaccepted);
          updateCell(rownumber,21,1);
          mhtable.setCellValue(rownumber,16,String.valueOf(useranswer[0]));
          mhtable.setCellValue(rownumber,17,String.valueOf(useranswer[1]));
          //
          double upperlimit[]=new double[2];
          double lowerlimit[]=new double[2];
          // if(q<0) {q=-q;useranswer[0]=-useranswer[0];}//If Q<0 then take symetry to y-axis,this should also be applied to user answer
          lowerlimit[0]=varTex*(1-lowerRange);upperlimit[0]=varTex*(1+upperRange);
          lowerlimit[1]=varTmin*(1-lowerRange);upperlimit[1]=varTmin*(1+upperRange);
          //
          if(useranswer[0]<upperlimit[0]&&useranswer[0]>lowerlimit[0]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[0])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,16,"Kullanicinin Baðýl Nem icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          if(useranswer[1]<upperlimit[1]&&useranswer[1]>lowerlimit[1]){
            totalpoint=totalpoint+doublevalueforquestion/results.length;
            pw.println(String.valueOf(useranswer[1])+" cevabiniz dogru.");
            mhtable.setCellNote(rownumber,17,"Kullanicinin Gerekli Isý Miktarý icin cevabi");
          }else{mhtable.setCellValue(rownumber,22,"0.000");}
          //
          mhtable.setCellValue(rownumber,22,String.valueOf(totalpoint));
          mhtable.setCellNote(rownumber,22,"Kullanýcýnýn bu soru icin toplam notu");
          //
          pw.println("Bu sorudan "+String.valueOf(totalpoint)+" puan aldiniz.");out.flush();
          //
        }//if
        else{
          pw.println("**********"+String.valueOf(rownumber)+ ".soruyu zaten cevaplamistiniz. **********");
          pw.println("Havanin cikis sicakligi icin"+mhtable.getCellValue(rownumber,16)+" girdiniz");
          pw.println("Havanin sogutulabilecegi min sicaklik icin"+mhtable.getCellValue(rownumber,17)+" girdiniz");
          double total=Double.valueOf(mhtable.getCellValue(rownumber,22)).doubleValue ();
          pw.println(String.valueOf(rownumber)+". Sorudan toplam "+String.valueOf(total)+" aldýnýz.");
        }//else
      }catch(Exception e){System.out.print(username+" throwed "); System.out.print(e); System.out.println(" for Question "+String.valueOf(rownumber));}
    }
}
