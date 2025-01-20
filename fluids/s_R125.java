package jspclass.JFluids;

public class DoymusR125 extends MHFluidsSaturated{
	public double sg(double Txt){
		double Sgger,Sgk,Sgb,fark,ort;
		int k=0;
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Sg1[]={1.502, 1.496, 1.492, 1.49, 1.49, 1.49, 1.49, 1.49, 1.488, 1.485, 1.476, 1.455};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Sf1[]={0.735, 0.794, 0.85, 0.902, 0.952, 1, 1.046, 1.091, 1.135, 1.18, 1.228, 1.285};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Vg1[]={0.162991, 0.130163, 0.068301, 0.046893, 0.033141, 0.023955, 0.017605, 0.013077, 0.009754, 0.007243, 0.005274, 0.003606};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Vf1[]={0.00066, 0.000678, 0.000696, 0.000716, 0.000737, 0.000761, 0.000788, 0.000821, 0.000864, 0.00092, 0.001002, 0.001146};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Hg1[]={305.3, 311.3, 317.2, 323, 328.5, 333.8, 338.8, 343.1, 346.8, 349.2, 349.7, 345.8};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double Hf1[]={134.1, 147.6, 160.9, 174.1, 187.1, 200, 212.9, 226.1, 239.6, 253.9, 269.8, 288.9};
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
		double T[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double P1[]={91.8, 149.2, 230.5, 341.3, 487.4, 675.3, 912.4, 1206.7, 1567.5, 2005.8, 2534.6, 3169.4};
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
		double T1[]={-50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60};
		double P1[]={91.8, 149.2, 230.5, 341.3, 487.4, 675.3, 912.4, 1206.7, 1567.5, 2005.8, 2534.6, 3169.4};
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
}//End of DoymusR125
