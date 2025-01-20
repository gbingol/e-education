package jspclass.JFluids;

public abstract class MHFluidsSaturated extends MHFluids{

	public abstract double sg(double Txt);    //kJ/kgK
	public abstract double sf(double Txt);    //kJ/kgK
	public abstract double P(double Txt);     //kPa
	public abstract double hf(double Txt);  	// kJ/kg
	public abstract double hg(double Txt);    //kJ/kg
	public abstract double vf(double Txt);    // m3/kg
	public abstract double vg(double Txt);    // m3/kg
	public abstract double T(double Pxp);     //°C

}