package jspclass.JFluids;

public abstract class MHFluids{

  public int getState(double Temperature, double Pressure,MHFluidsSaturated sfluid)
  {
    try{

      if(sfluid.P(Temperature)<Pressure) return -1;    //fluid is compressed
      if(sfluid.P(Temperature)==Pressure) return 0;   //fluid is saturated
      if(sfluid.P(Temperature)>Pressure) return 1;    //fluid is superheated

    //the error occurs when temperature is very high and exceeds saturated temperature fluid range
    }catch(Exception e){return 1;}

    return -10;   //returning an error value
  }


}
