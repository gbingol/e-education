// 26 Haziran 2001 16:50

public class PsikrometrikFonksiyonlar   {
	//
	//Pws T sýcaklýðý kullanýlarak elde edilir
	//
	public double Pws(double T){
		 double alfa,a,b,c ,D,Tk,Pws=0 ;
		Tk = T + 273.15;
		if((Tk>=213.15)&&(Tk<=273.15)){
			a = -0.7297593707 * Math.pow(10,-5); b = 0.5397420727 * Math.pow(10,-2); c = 0.2069880620 *Math.pow(10,2); D = -0.6042275128 * 10000;
			alfa = a * Tk*Tk + b * Tk + c + D *(1/Tk) ;
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=273.16)&&(Tk<=322.15)){
			a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; D = -0.6344011577 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=322.16)&&(Tk<=373.15)){
			a = 0.1246732157 *Math.pow(10,-4); b = -0.1915465806 * 0.1; c = 100 * 0.2702388315; D = -0.6340941639 * 10000;
			alfa = a * Tk*Tk+b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=373.16)&&(Tk<=423.15)){
			a = 0.1204507646 *Math.pow(10,-4); b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; D = -0.6316972063 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}else if((Tk>=423.16)&&(Tk<=473.15)){
			a = 0.1069730183 * Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; D = -0.622078123 * 10000;
			alfa = a * Tk*Tk+ b * Tk + c + D *(1/Tk);
			Pws = 1000 * Math.exp(alfa);
		}
		return Pws;		
	}
	//T Pws kullanýlarak elde ediliyor
	//
	public double T(double Pws){
		double beta,E, F, G, H, K,Tk,T=0;
		if((Pws>=1)&&(Pws<611)){
			E = 0.1004926534 *0.01; F = 0.1392917633 *0.01; G = 0.2815151574; H = 10 * 0.7311621119; K = 1000 * 0.2125893734;
            beta = Math.log(Pws);
            Tk = E * Math.pow(beta,4)+ F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=611)&&(Pws<12350)){
			E = 0.5031062503 * 0.01; F = -0.882677938 * 0.1; G = 0.1243688446 * 10; H = 0.3388534296 * 10; K = 0.2150077993 * 1000;
            beta =Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=12350)&&(Pws<101420)){
			E = 0.1209512517 *Math.pow(10,-1); F = -0.3545542105; G = 0.5020858479 * 10;H = -0.205030105 * 100; K = 1000 * 0.2718585432;
            beta = Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3)+ G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=101420)&&(Pws<476207)){
			E = 0.1 * 0.2467291016; F = -0.9367112883; G = 100 * 0.1514142334; H = -100 * 0.9882417501; K = 1000 * 0.4995092948;
            beta = Math.log(Pws);
            Tk = E *Math.pow(beta,4)+ F *Math.pow(beta,3)+ G *Math.pow(beta,2) + H * beta + K;
            T = Tk - 273.15;
		}else if((Pws>=476207)&&(Pws<1555099)){
			E = 0.2748402484 *Math.pow(10,-1); F=-10 * 0.1068661307; G = 100 * 0.1742964962; H = -0.1161208532 * 1000; K = 1000 * 0.547261812;
            beta =Math.log(Pws);
            Tk = E *Math.pow(beta,4) + F *Math.pow(beta,3) + G * beta*beta + H * beta + K;
            T = Tk - 273.15;
		}
		return T;
	}//T(Pws) metodunun sonu
	//
	//Pw(Td) fonksiyonu yazýlacak
	//
	public double Pw(double Td){
		double alfa2,a,b,c,D,Tdk,Pw=0;
		Tdk = Td + 273.15;   //Td derece iken Kelvine çevrilir
		if((Tdk>=213.15)&&(Tdk<=273.15)){
			a = -0.7297593707 *Math.pow(10,-5); b = 0.5397420727 *0.01; c = 0.206988062 * 100; D = -0.6042275128 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=273.16)&&(Tdk<=322.15)){
			a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; D = -0.6344011577 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=322.16)&&(Tdk<=373.15)){
			a = 0.1246732157 *Math.pow(10,-4); b= -0.1915465806 * 0.1;c =100 * 0.2702388315; D = -0.6340941639 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=373.16)&&(Tdk<=423.15)){
			a = 0.1204507646 *Math.pow(10,-4);b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; D = -0.6316972063 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}else if((Tdk>=423.16)&&(Tdk<=473.15)){
			a =0.1069730183 *Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; D = -0.622078123 * 10000;
            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}
		return Pw;
	}//Pw(Td) methodunun sonu
	//
	//Td(Pw) methodu yazýlacak
	//
	public double Td(double Pw){
		double beta2,E,F,G,H,K,Tdk2,Td=0;
		if((Pw>=1)&&(Pw<611)){
			E = 0.1004926534 *0.01; F = 0.1392917633 *0.01; G = 0.2815151574; H = 10 * 0.7311621119; K = 1000 * 0.2125893734;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15; //Yaþ termometre sýcaklýðý Tdk2 ile (K) olarak elde edildiðinden Td ile (°C) çevrilir
		}else if((Pw>=611)&&(Pw<12350)){//Hata çýkarsa 610.001'i 611 olarak deðiþtir ve diðerlerinede aynýsýný uygula
			E = 0.5031062503 * 0.01; F = -0.882677938 * 0.1; G = 0.1243688446 * 10; H = 0.3388534296 * 10; K = 0.2150077993 * 1000;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=12350)&&(Pw<101420)){
			E = 0.1209512517 *Math.pow(10,-1);F = -0.3545542105;G = 0.5020858479 * 10; H = -0.205030105 * 100; K = 1000 * 0.2718585432;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=101420)&&(Pw<476207)){
			E = 0.1 * 0.2467291016; F = -0.9367112883; G = 100 * 0.1514142334; H = -100 * 0.9882417501; K = 1000 * 0.4995092948;
            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}else if((Pw>=476207)&&(Pw<=1555099)){
			E = 0.2748402484 *Math.pow(10,-1); F = -10 * 0.1068661307; G = 100 * 0.1742964962; H = -0.1161208532 * 1000; K = 1000 * 0.547261812;
            beta2 =Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
            Td = Tdk2 - 273.15;
		}
		return Td;
	}
	//Td(Pw) methodunun sonu
	public double Pwsyas(double Tyas){
	// Yaþ termometre sýcaklýðýndaki Basýncý bulan metod
		double alfa3,a,b,c,d,TyasK,Pwsyas=0;
			TyasK = Tyas + 273.15;
			if((TyasK>=213.15)&&(TyasK<=273.15)){
				a = -0.7297593707 *Math.pow(10,-5); b = 0.5397420727 *0.01; c = 0.206988062 * 100; d = -0.6042275128 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=273.16)&&(TyasK<=322.15)){
				a = 0.1255001965 *Math.pow(10,-4); b = -0.1923595289 *0.1; c = 100 * 0.2705101899; d = -0.6344011577 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=322.16)&&(TyasK<=373.15)){
				a = 0.1246732157 *Math.pow(10,-4); b = -0.1915465806 * 0.1; c = 100 * 0.2702388315; d = -0.6340941639 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=373.16)&&(TyasK<=423.15)){
				a = 0.1204507646 *Math.pow(10,-4); b = -0.1866650553 * 0.1; c = 0.2683626903 * 100; d = -0.6316972063 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}else if((TyasK>=423.16)&&(TyasK<=473.15)){
				a = 0.1069730183 *Math.pow(10,-4); b = -0.1 * 0.1698965754; c = 100 * 0.2614073298; d = -0.622078123 * 10000;
				alfa3 = a * TyasK*TyasK + b * TyasK + c + d *(1/TyasK);
				Pwsyas = 1000 * Math.exp(alfa3);
			}//if þartýnýn sonu
			return Pwsyas;
	}//Pwsyas(Tyas) methodunun sonu
	//
	double H(double T,double W){	//h(kJ/kg)  W(kg/kg) T(°C)
		// Sýcaklýk ve nem oraný bilinirken entalpiyi hesaplar
		double h=0;
		h=T+W*(2501+1.805*T);
		return h;
	}//H(T,W) methodunun sonu
	//
	double T2(double h,double W){
		// Sýcaklýk °C olarak hesaplanýr h(kJ/kg)
		double T2=0;
		T2=(h-2501*W)/(1+1.805*W);
		return T2;
	}//T2(h,W) metodunun sonu
	//
	double W(double h,double T){
		// Nem (kg/kg) h(kJ/kg) T(°C)
		double W=0;
		W=(h-T)/(2501+1.805*T);
		return W;
	}// W(h,T) metodunun sonu
	//
	double W2(double P,double Pw){
		//W2(kg/kg) P,Pw(Pa)
		double W2=0;
		W2=0.62198*Pw/(P-Pw);
		return W2;
	}// W2(P,Pw) metodunun sonu
	//
	double P(double Pw,double W){
		// P,Pw(Pa),W(kg/kg)
		double P=0;
		P=(0.62198*Pw/W)+Pw;
		return P;
	}//P(Pw,W) metodunun sonu
	//
	double Pw2(double P,double W){
		//P,Pw2 (Pa)  W(kg/kg)
		double Pw2=0;
		Pw2=(P*W)/(W+0.62198);
		return Pw2;
	}// Pw2(P,W) metodunun sonu
	//
	double Ws(double P,double Pws){
		// Ws(kg/kg)   P,Pws(Pa)
		double Ws=0;
		Ws=0.62198*Pws/(P-Pws);
		return Ws;
	}// Ws(P,Pws) metodunun sonu
	//
	double P2(double Pws,double Ws){
		//P2,Pws (Pa)   Ws(kg/kg)
		double P2=0;
		P2=(0.62198*Pws/Ws)+Pws;
		return P2;
	}//P2(Pws,Ws) metodunun sonu
	//
	double Pws2(double P,double Ws){
		// P,Pws2 (Pa)   Ws(kg/kg)
		double Pws2=0;
		Pws2=0.62198*(P-Ws)/(Ws+0.62198);
		return Pws2;
	}//Pws2(P,Ws) metodunun sonu
	//
	double Wsyas(double P,double Pwsyas){
		//Wsyas (kg/kg)   P,Pwsyas(Pa)
		double Wsyas=0;
		Wsyas=0.62198*Pwsyas/(P-Pwsyas);
		return Wsyas;
	}// Wsyas(P,Pwsyas) metodunun sonu
	//
	double P3(double Pwsyas,double Wsyas){
		//P3,Pwsyas(Pa)   Wsyas(kg/kg)
		double P3=0;
		P3=0.62198*Pwsyas/Wsyas+Pwsyas;
		return P3;
	}//P3(Pwsyas,Wsyas) metodunun sonu
	//
	double Pwsyas2(double P,double Wsyas){
		double Pwsyas2=0;
		Pwsyas2=(P*Wsyas)/(Wsyas+0.62198);
		return Pwsyas2;
	}//Pwsyas2(P,Wsyas) metodunun sonu)
	//
	double Wsyas3(double H,double W,double Tyas){
		// Hsyas,H,Hw (kJ/kg)   Tyas (°C)   W (kg/kg)
		double Wsyas3=0;
		double Hsyas=0,Wsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;		
		Hw=4.186*Tyas;
		Wsyas3=(Hsyas-H)/Hw+W;
		return Wsyas3;
	}//Wsyas3(H,W,Tyas) metodunun sonu
	//
	double W3(double Wsyas,double H,double Tyas){
		double W3=0;
		double Hsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;	
		Hw=4.186*Tyas;
		W3=(H-Hsyas)/Hw+Wsyas;
		return W3;
	}//W3(Wsyas,H,Tyas) metodunun sonu
	//
	double W4(double Wsyas,double T,double Tyas){
		double W4=0;
		double a=0,b=0,c=0;
		a=(2501-2.381*Tyas)*Wsyas;
		b=T-Tyas;
		c=2501+1.805*T-4.186*Tyas;
		W4=(a-b)/c;
		return W4;
	}//W4(Wsyas,T,Tyas) metodunun sonu)
	//
	double Tyas(double Wsyas,double W,double T){
		double Tyas=0;
		double a=0,b=0,c=0;
		a=2501*(Wsyas-W);
		b=T*(1+1.805*W);
		c=2.381*Wsyas-4.186*W-1;
		Tyas=(a-b)/c;
		return Tyas;
	}// Tyas(Wsyas,W,T) metodunun sonu
	//
	double Wsyas2(double W,double T,double Tyas){
		double Wsyas2=0;
		double a=0,b=0,c=0;
		a=(2501+1.805*T-2.381*Tyas)*W;
		b=(T-Tyas);
		c=2501-2.381*Tyas;
		Wsyas2=(a+b)/c;
		return Wsyas2;
	}// Wsyas2(W,T,Tyas) metodunun sonu
	//
	double T3(double Wsyas,double W,double Tyas){
		double T3=0;
		double a=0,b=0;
		a=(2501-2.381*Tyas)*Wsyas-(2501-4.186*Tyas)*W+Tyas;
		b=1+1.805*W;
		T3=a/b;
		return T3;
	}// T3(Wsyas,W,Tyas) metodunun sonu)
	//
	double RH(double Pw,double Pws){
		double RH=0;
		RH=Pw/Pws;
		return RH;
	}// RH(Pw,Pws) metodunun sonu
	//
	double Tyas1521(double P,double Pwsyas,double W,double T){
		double a=0,b=0,c=0,d=0,Tyas1521=0;
		a=0.62198*Pwsyas/(P-Pwsyas);
		b=2501*(a-W)-T*(1+1.805*W);
		c=2.381*a-4.186*W-1;
		Tyas1521=b/c;
		return Tyas1521;
	}//Tyas1521(P,Pwsyas,W,T)
	//
	double Tyas1521(double varP,double varW,double varT){
		// 15 nolu kombinasyon ile 21 nolu kombinasyon birleþtiriliyor
		double varsubTyas, X,Y, AA,DD1,DD2;
		double DF1,DF2,a,b,varsubWsyas,varsubW1;
		double varsubPwsyas;
		double varsubRH,varsubPw,varsubPws;
		int T;
		double iter,fark;
		varsubTyas = varT ;
		iter=1;fark=1;
		varsubPw=Pw2(varP,varW);varsubPws=Pws(varT);varsubRH=varsubPw/varsubPws;
		do{
			varsubTyas = varsubTyas - iter;
			if((varsubRH > 0.991)&&(Math.abs(varsubTyas - varT) < 1)){
				iter = 1; fark = 1;
			}//if
			varsubPwsyas = Pwsyas(varsubTyas);
			varsubWsyas = Wsyas(varP, varsubPwsyas);
			a = (2501 - 2.381 * varsubTyas) * varsubWsyas - (varT - varsubTyas);
			b = 2501 + 1.805 * varT - 4.186 * varsubTyas;
			varsubW1 = a / b;
		if(Math.abs(varW-varsubW1)<=0.0000000001) return varsubTyas;
		}while(!((varsubW1<varW)));
		Y = varsubTyas+fark; X = varsubTyas;
		do{
			AA = (Y - X) / 3;
			DD1 = X + AA; 
			double PwsStar=Pwsyas(DD1);
			varsubWsyas=Wsyas(varP,PwsStar);
			a = (2501 - 2.381 * DD1) * varsubWsyas - (varT - DD1);
			b = 2501 + 1.805 * varT - 4.186 * DD1;
			varsubW1 = a / b;
			DF1 = varsubW1 - varW;
			DD2 = Y - AA;
			PwsStar=Pwsyas(DD2);
			varsubWsyas=Wsyas(varP,PwsStar);
			a = (2501 - 2.381 * DD2) * varsubWsyas - (varT - DD2);
			b = 2501 + 1.805 * varT - 4.186 * DD2;
			varsubW1 = a / b;
			DF2 = varsubW1 - varW;
			if(Math.abs(DF1)<0.0000000001) return DD1;
			if(Math.abs(DF2)<0.0000000001) return DD2;
			if(X==Y) return DD1;
			if (Math.abs(DF1) <Math.abs(DF2)){
				if (DF1<0){X = DD1; Y = DD1 + AA;}
				if (DF1>0){X = DD1 - AA; Y = DD1;}
				if(DF1==0){ return DD2;}
			}else{
				if(DF2<0){X = DD2; Y = DD2 + AA;}
				if(DF2>0){X = DD2 - AA; Y = DD2;}
				if(DF2==0){return DD1;}
			}
		}while(!((Math.abs(DD1 - DD2) < 0.0000000001)||(Math.abs(varsubW1 - varW) < 0.0000000001)));
		return DD1;
	}//Tyas1521
	//
	public double IkinciDerecedenDenklem(double a,double b,double c){
		double delta,x1=0;
		delta=b*b-4*a*c;
		if (delta==0){x1=-b/(2*a);}
		if (delta>0){x1=(-b+Math.pow(delta,0.5))/(2*a);}
		return x1;
	}
	//
	public double LR(double x1,double y1, double x2,double y2, double aranan){
		double m,n,lr;
		m=(y2-y1)/(x2-x1);
		n=y2-m*x2;
		lr=m*aranan+n;
		return lr;
	}//LR
	public double v(double T,double P,double W){
		//Havanýn hacmini m3/kg kuru hava olarak bulur.
		double x;
		x=287.055*(T+273.15)*(1+1.6075*W)/P;
		return x;
	}
		
}//PsikrometrikFonksiyonlar'ýn sonu



