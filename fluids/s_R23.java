package jspclass.JFluids;

public class DoymusR23 extends MHFluidsSaturated{
	public double sg(double Txt){
		double Sgger,Sgk,Sgb,fark,ort;
		int k=0;
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Sg1[]={1.869, 1.816, 1.769, 1.728, 1.692, 1.659, 1.629, 1.601, 1.574, 1.546, 1.517, 1.48, 1.424};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Sf1[]={0.409, 0.477, 0.543, 0.606, 0.666, 0.724, 0.78, 0.834, 0.888, 0.942, 1, 1.065, 1.151};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Vg1[]={0.632936, 0.334835, 0.19071, 0.11538, 0.073334, 0.048516, 0.033143, 0.023207, 0.016536, 0.011894, 0.008543, 0.00601, 0.003901};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Vf1[]={0.000669, 0.000683, 0.000698, 0.000716, 0.000736, 0.000759, 0.000786, 0.000818, 0.000857, 0.000906, 0.000971, 0.001066, 0.00124};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Hg1[]={320.1, 324.5, 328.6, 332.3, 335.5, 338.3, 340.6, 342.2, 343.1, 342.9, 341.1, 336.4, 324.6};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double Hf1[]={67.3, 79.4, 91.8, 104.3, 117, 129.7, 142.6, 155.8, 169.4, 183.9, 200, 219, 244.6};
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
		double T[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double P1[]={31.8, 62.8, 114.4, 194.8, 313.5, 481, 709, 1010.3, 1399, 1891.1, 2505.4, 3263.7, 4193.2};
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
		double T1[]={-100, -90, -80, -70, -60, -50, -40, -30, -20, -10, 0, 10, 20};
		double P1[]={31.8, 62.8, 114.4, 194.8, 313.5, 481, 709, 1010.3, 1399, 1891.1, 2505.4, 3263.7, 4193.2};
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
}//End of DoymusR23
