package jspclass.JFluids;

public abstract class MHFluidsSuperHeated extends MHFluids {
//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg

public abstract double v(double Pxp,double Txt);  // m3/kg
public abstract double h(double Pxp,double Txt);  //P=MPa h=kJ/kg
public abstract double s(double Pxp, double Txt); //P=MPa s=kJ/kg

}//End of class
