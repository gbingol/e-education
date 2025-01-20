package jspclass.JFluids;

public class DoymusR32 extends MHFluidsSaturated{
	public double sg(double Txt){
		double Sgger,Sgk,Sgb,fark,ort;
		int k=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Sg1[]={2.757, 2.656, 2.571, 2.498, 2.434, 2.378, 2.327, 2.281, 2.238, 2.199, 2.161, 2.125, 2.089, 2.053, 2.014, 1.972, 1.92, 1.849};
		k = 0;
		try{
			do{
				k = k + 1;
			}while(!(Txt <= T[k]));
		}catch(ArrayIndexOutOfBoundsException e) {}
		Sgk = Sg1[k - 1]; Sgb = Sg1[k];
		fark = Txt - T[k - 1];
		ort = (Sgb - Sgk) / (T[k] - T[k - 1]);
		Sgger = Sgk + fark * ort;
		return Sgger;
	}//
	//
	//
	public double sf(double Txt){
		double Sfger,Sfk,Sfb,fark,ort;
		int k=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Sf1[]={0.195, 0.326, 0.433, 0.524, 0.604, 0.676, 0.743, 0.809, 0.873, 0.936, 1, 1.064, 1.127, 1.19, 1.253, 1.317, 1.386, 1.468};
		k = 0;
		do{
			k = k + 1;
		}while(!(Txt <= T[k]));
		Sfk = Sf1[k - 1]; Sfb = Sf1[k];
		fark = Txt - T[k - 1];
		ort = (Sfb - Sfk) / (T[k] - T[k - 1]);
		Sfger = Sfk + fark * ort;
		return Sfger;
	}//
	//
	//
	public double vg(double Txt){
		//vg m3/kg
		double fark,ort,Vgk,Vgb,Vgger;
		int i=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Vg1[]={7.345879, 3.267929, 1.620971, 0.87547, 0.506322, 0.309464, 0.197909, 0.131405, 0.0090016, 0.63288, 0.04546, 0.033221, 0.024594, 0.018359, 0.01374, 0.010223, 0.007452, 0.005131};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Vgk = Vg1[i - 1]; Vgb = Vg1[i];
		fark = Txt - T[i - 1];
		ort = (Vgb - Vgk) / (T[i] - T[i - 1]);
		Vgger = Vgk + fark * ort;
		return Vgger;
	}//
	//
	//
	public double vf(double Txt){
		//vf m3/kg
		double fark,ort,Vfk,Vfb,Vfger;
		int i=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Vf1[]={0.000754, 0.000767, 0.000781, 0.000796, 0.000813, 0.00083, 0.00085, 0.000871, 0.000894, 0.000919, 0.000948, 0.000981, 0.001019, 0.001064, 0.001119, 0.00119, 0.001291, 0.001465};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Vfk = Vf1[i - 1]; Vfb = Vf1[i];
		fark = Txt - T[i - 1];
		ort = (Vfb - Vfk) / (T[i] - T[i - 1]);
		Vfger = Vfk + fark * ort;
		return Vfger;
	}//
	//
	//
	public double hg(double Txt){
		//Hg kJ/kg
		double fark,ort,Hgk,Hgb,Hgger;
		int i=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Hg1[]={468.7, 475.2, 481.5, 487.6, 493.4, 498.8, 503.7, 508.1, 511.9, 514.9, 517.2, 518.5, 518.7, 517.4, 514.3, 508.6, 498.6, 480.3};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Hgk = Hg1[i - 1]; Hgb = Hg1[i];
		fark = Txt - T[i - 1];
		ort = (Hgb - Hgk) / (T[i] - T[i - 1]);
		Hgger = Hgk + fark * ort;
		return Hgger;
	}//
	//
	//
	public double hf(double Txt){
		//Hf kJ/kg
		double fark,ort,Hfk,Hfb,Hfger;
		int i=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double Hf1[]={25.1, 48.4, 68.7, 86.7, 103.3, 119, 134.5, 150.1, 166.1, 182.7, 200, 218, 236.6, 255.9, 275.9, 297.2, 320.6, 349.6};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Hfk = Hf1[i - 1]; Hfb = Hf1[i];
		fark = Txt - T[i - 1];
		ort = (Hfb - Hfk) / (T[i] - T[i - 1]);
		Hfger = Hfk + fark * ort;
		return Hfger;
	}//
	//
	//
	public double P(double Txt){
		double fark,ort,Pk,Pb,Pger;
		int i=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double P1[]={3.8, 8.9, 18.8, 36.4, 65.4, 110.6, 177.7, 273.7, 406, 583.1, 813.9, 1108.1, 1475.9, 1928.3, 2478.1, 3140.3, 3934, 4884.5};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Pk = P1[i - 1]; Pb = P1[i];
		fark = Txt - T[i - 1];
		ort = (Pb - Pk) / (T[i] - T[i - 1]);
		Pger = Pk + fark * ort;
		return Pger;
	}//
	//
	//
	public double T(double Pxp){
		double Tger,fark,ort,Tk,Tb,Pger;
		int i=0;
		double T1[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60, 70};
		double P1[]={3.8, 8.9, 18.8, 36.4, 65.4, 110.6, 177.7, 273.7, 406, 583.1, 813.9, 1108.1, 1475.9, 1928.3, 2478.1, 3140.3, 3934, 4884.5};
		i = 0;
		do{
			i = i + 1;
		}while(!(Pxp <= P1[i]));
		Tk = T1[i - 1]; Tb = T1[i];
		fark = Pxp - P1[i - 1];
		ort = (Tb - Tk) / (P1[i] - P1[i - 1]);
		Tger = Tk + fark * ort;
		return Tger;
	}
}//End of DoymusR32
