package jspclass.JFluids;

public class Doymus143A extends MHFluidsSaturated{
	public double sg(double Txt){
		double Sgger,Sgk,Sgb,fark,ort;
		int k;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Sg1[]={1.756, 1.744, 1.733, 1.723, 1.714, 1.705, 1.697, 1.69, 1.684, 1.678, 1.673, 1.669, 1.665, 1.662, 1.66, 1.658, 1.657, 1.655, 1.654, 1.653, 1.651, 1.648, 1.642, 1.636, 1.633, 1.637};
		k = 0;
		do{
			k = k + 1;
		}while(!(Txt <= T[k]));
		Sgk = Sg1[k - 1]; Sgb = Sg1[k];
		fark = Txt - T[k - 1];
		ort = (Sgb - Sgk) / (T[k] - T[k - 1]);
		Sgger = Sgk + fark * ort;
		return Sgger;
	}
	//
	public double sf(double Txt){
		double Sfger,Sfk,Sfb,fark,ort;
		int k;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Sf1[]={0.683, 0.71, 0.738, 0.765, 0.792, 0.819, 0.846, 0.872, 0.898, 0.924, 0.949, 0.975, 1, 1.025, 1.051, 1.077, 1.103, 1.13, 1.158, 1.187, 1.216, 1.246, 1.273, 1.293, 1.304, 1.31};
		k = 0;
		do{
			k = k + 1;
		}while(!(Txt <= T[k]));
		Sfk = Sf1[k - 1]; Sfb = Sf1[k];
		fark = Txt - T[k - 1];
		ort = (Sfb - Sfk) / (T[k] - T[k - 1]);
		Sfger = Sfk + fark * ort;
		return Sfger;
	}
	//
	public double P(double Txt){
		//P MPa
		double fark,ort,Pk,Pb,Pger;
		int i;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double P1[]={53.7, 69.8, 89.5, 113.3, 141.9, 175.9, 215.9, 262.6, 316.6, 378.7, 449.6, 530.1, 620.9, 723, 837, 963.9, 1104.8, 1260.4, 1432.1, 1620.9, 1828.1, 2055.2, 2303.9, 2575.9, 2873.4, 3198.6};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Pk = P1[i - 1]; Pb = P1[i];
		fark = Txt - T[i - 1];
		ort = (Pb - Pk) / (T[i] - T[i - 1]);
		Pger = Pk + fark * ort;
		return Pger ;

	}
	//
	public double hf(double Txt){
		//Hf kJ/kg
		double fark,ort,Hfk,Hfb,Hfger;
		int i;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Hf1[]={122.4, 128.4, 134.5, 140.7, 147, 153.4, 159.8, 166.3, 172.9, 179.5, 186.2, 193.1, 200, 207.1, 214.4, 221.9, 229.7, 237.9, 246.4, 255.4, 264.8, 274.3, 283.4, 290.4, 294.4, 296.7};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Hfk = Hf1[i - 1]; Hfb = Hf1[i];
		fark = Txt - T[i - 1];
		ort = (Hfb - Hfk) / (T[i] - T[i - 1]);
		Hfger = Hfk + fark * ort;
		return Hfger;
	}
	//
	public double hg(double Txt){
		//Hg kJ/kg
		double fark,ort,Hgk,Hgb,Hgger;
		int i;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Hg1[]={351.2, 353.9, 356.6, 359.2, 361.8, 364.4, 366.9, 369.3, 371.8, 374.3, 376.7, 379.2, 381.7, 384.3, 386.8, 389.4, 392, 394.5, 396.9, 399.1, 401, 402.3, 402.8, 402.7, 403.8, 407.3};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Hgk = Hg1[i - 1]; Hgb = Hg1[i];
		fark = Txt - T[i - 1];
		ort = (Hgb - Hgk) / (T[i] - T[i - 1]);
		Hgger = Hgk + fark * ort;
		return Hgger;
	}
	//
	public double vf(double Txt){
		//vf m3/kg
		double fark,ort,Vfk,Vfb,Vfger;
		int i;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Vf1[]={0.000835, 0.000844, 0.000854, 0.000863, 0.000873, 0.000884, 0.000895, 0.000907, 0.00092, 0.000933, 0.000947, 0.000962, 0.000978, 0.000995, 0.001013, 0.001033, 0.001055, 0.001078, 0.001105, 0.001134, 0.001168, 0.001206, 0.001252, 0.001307, 0.001379, 0.00148};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));
		Vfk = Vf1[i - 1]; Vfb = Vf1[i];
		fark = Txt - T[i - 1];
		ort = (Vfb - Vfk) / (T[i] - T[i - 1]);
		Vfger = Vfk + fark * ort;
		return Vfger;
	}
	//
	public double vg(double Txt){
		//vg m3/kg
		double fark,ort,Vgk,Vgb,Vgger;
		int i;
		double T[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double Vg1[]={0.373107, 0.291493, 0.230399, 0.184086, 0.148565, 0.12102, 0.099439, 0.082367, 0.068738, 0.057761, 0.048846, 0.041547, 0.035523, 0.030513, 0.026314, 0.022765, 0.019743, 0.017146, 0.014895, 0.012928, 0.011197, 0.009671, 0.008355, 0.007323, 0.006664, 0.006289};
		i = 0;
		do{
			i = i + 1;
		}while(!(Txt <= T[i]));//hata genelde loop'larda meydana geliyor
		Vgk = Vg1[i - 1]; Vgb = Vg1[i];
		fark = Txt - T[i - 1];
		ort = (Vgb - Vgk) / (T[i] - T[i - 1]);
		Vgger = Vgk + fark * ort;
		return Vgger;
	}
	//
	public double T(double Pxp){
		double fark,ort,Tk,Tb,Tger;
		int i;
		double T1[]={-60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65};
		double P1[]={53.7, 69.8, 89.5, 113.3, 141.9, 175.9, 215.9, 262.6, 316.6, 378.7, 449.6, 530.1, 620.9, 723, 837, 963.9, 1104.8, 1260.4, 1432.1, 1620.9, 1828.1, 2055.2, 2303.9, 2575.9, 2873.4, 3198.6};
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

} //end of class R143A
