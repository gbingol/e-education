package jspclass.JFluids;

public class DoymusR22 extends MHFluidsSaturated{
	public double sg(double Txt){
		double Sgger,Sgk,Sgb,fark,ort;
		int k=0;
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Sg1[]={1.825, 1.814, 1.803, 1.793, 1.784, 1.775, 1.767, 1.759, 1.752, 1.745, 1.738, 1.731, 1.725, 1.719, 1.712, 1.706, 1.7, 1.694, 1.687, 1.68, 1.673, 1.665, 1.656, 1.646, 1.634, 1.618, 1.596};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Sf1[]={0.825, 0.847, 0.869, 0.892, 0.914, 0.935, 0.957, 0.979, 1, 1.021, 1.042, 1.063, 0.084, 0.105, 1.126, 1.146, 1.167, 1.188, 1.209, 1.23, 1.251, 1.273, 1.295, 1.319, 1.343, 1.371, 1.403};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Vg1[]={0.250842, 0.166474, 0.135901, 0.111904, 0.092879, 0.077654, 0.065363, 0.055358, 0.04715, 0.040368, 0.034724, 0.029996, 0.02601, 0.02263, 0.019747, 0.017273, 0.015139, 0.013287, 0.011672, 0.010254, 0.009002, 0.007889, 0.00689, 0.005984, 0.005149, 0.004359, 0.003564};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Vf1[]={0.000709, 0.000717, 0.000725, 0.000733, 0.000741, 0.00075, 0.000759, 0.000768, 0.000778, 0.000789, 0.0008, 0.000812, 0.000824, 0.000838, 0.000852, 0.000867, 0.000884, 0.000902, 0.000922, 0.000944, 0.000969, 0.000997, 0.00103, 0.001069, 0.001118, 0.001183, 0.001282};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Hg1[]={388.5, 390.8, 393.1, 395.3, 397.4, 399.5, 401.6, 403.5, 405.4, 407.2, 408.9, 410.5, 412, 413.4, 414.6, 415.8, 416.7, 417.5, 418, 418.3, 418.3, 418, 417.1, 415.6, 413.2, 409.5, 403};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double Hf1[]={155.3, 160.7, 166.1, 171.5, 177.1, 182.7, 188.4, 194.2, 200, 205.9, 211.9, 218, 224.1, 230.4, 236.8, 243.2, 249.8, 256.5, 263.4, 270.5, 277.8, 285.4, 293.3, 301.7, 310.8, 320.9, 333};
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
		double T[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double P1[]={104.9, 131.6, 163.4, 200.9, 244.7, 295.6, 354.2, 421.2, 497.5, 583.6, 680.5, 789, 909.7, 1043.7, 1191.7, 1354.6, 1533.3, 1728.8, 1942, 2174.2, 2426.3, 2699.6, 2995.6, 3315.7, 3661.9, 4036.4, 4442.1};
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
		double T1[]={-40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90};
		double P1[]={104.9, 131.6, 163.4, 200.9, 244.7, 295.6, 354.2, 421.2, 497.5, 583.6, 680.5, 789, 909.7, 1043.7, 1191.7, 1354.6, 1533.3, 1728.8, 1942, 2174.2, 2426.3, 2699.6, 2995.6, 3315.7, 3661.9, 4036.4, 4442.1};
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
}//End of DoymusR22
