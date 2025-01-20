// 26 June 2001 16:50

public class PsikrometrikFonksiyonlar   {
	//
	public PsikrometrikFonksiyonlar(){}
	
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
	}
	

	public double Pw(double Td){
		double alfa2,a,b,c,D,Tdk,Pw=0;
		Tdk = Td + 273.15;   

		if((Tdk>=213.15)&&(Tdk<=273.15)){
			a = -0.7297593707 *Math.pow(10,-5); 
			b = 0.5397420727 *0.01; 
			c = 0.206988062 * 100; 
			D = -0.6042275128 * 10000;

            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);

		}else if((Tdk>=273.16)&&(Tdk<=322.15)){
			a = 0.1255001965 *Math.pow(10,-4); 
			b = -0.1923595289 *0.1; 
			c = 100 * 0.2705101899; 
			D = -0.6344011577 * 10000;

            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);

		}else if((Tdk>=322.16)&&(Tdk<=373.15)){
			a = 0.1246732157 *Math.pow(10,-4); 
			b= -0.1915465806 * 0.1;
			c =100 * 0.2702388315; 
			D = -0.6340941639 * 10000;

            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);

		}else if((Tdk>=373.16)&&(Tdk<=423.15)){
			a = 0.1204507646 *Math.pow(10,-4);
			b = -0.1866650553 * 0.1; 
			c = 0.2683626903 * 100; 
			D = -0.6316972063 * 10000;

            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);

		}else if((Tdk>=423.16)&&(Tdk<=473.15)){
			a =0.1069730183 *Math.pow(10,-4); 
			b = -0.1 * 0.1698965754; 
			c = 100 * 0.2614073298; 
			D = -0.622078123 * 10000;

            alfa2 = a * Tdk*Tdk+ b * Tdk + c + D *(1/Tdk);
			Pw = 1000 * Math.exp(alfa2);
		}


		return Pw;
	}
	

	public double Td(double Pw)
	{
		double beta2,E,F,G,H,K,Tdk2,Td=0;

		if((Pw>=1)&&(Pw<611)){
			E = 0.1004926534 *0.01; 
			F = 0.1392917633 *0.01; 
			G = 0.2815151574; 
			H = 10 * 0.7311621119; 
			K = 1000 * 0.2125893734;

            beta2 = Math.log(Pw);
            Tdk2 = E *Math.pow(beta2,4)+ F *Math.pow(beta2,3) + G *Math.pow(beta2,2)+ H * beta2 + K;
			Td = Tdk2 - 273.15; 
			
		}else if((Pw>=611)&&(Pw<12350)){
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
	
	

	public double Pwsyas(double Tyas)
	{
	
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
			}


			return Pwsyas;
	}
	

	double H(double T,double W){	//h(kJ/kg)  W(kg/kg) T(�C)
		
		return T+W*(2501+1.805*T);
	}
	

	double T2(double h,double W){
				
		return (h-2501*W)/(1+1.805*W);
	}
	
	double W(double h,double T){
		// Nem (kg/kg) h(kJ/kg) T(�C)
		
		return (h-T)/(2501+1.805*T);
	}
	
	double W2(double P,double Pw){
		//W2(kg/kg) P,Pw(Pa)
		
		return 0.62198*Pw/(P-Pw);
	}
	
	double P(double Pw,double W){
		// P,Pw(Pa),W(kg/kg)
		
		return (0.62198*Pw/W)+Pw;
	}
	
	double Pw2(double P,double W){
		//P,Pw2 (Pa)  W(kg/kg)
		
		return (P*W)/(W+0.62198);
	}
	
	double Ws(double P,double Pws){
		// Ws(kg/kg)   P,Pws(Pa)
		
		return 0.62198*Pws/(P-Pws);
	}
	
	double P2(double Pws,double Ws){
		//P2,Pws (Pa)   Ws(kg/kg)
		
		return (0.62198*Pws/Ws)+Pws;
	}
	
	double Pws2(double P,double Ws){
		// P,Pws2 (Pa)   Ws(kg/kg)
		
		return 0.62198*(P-Ws)/(Ws+0.62198);
	}
	
	double Wsyas(double P,double Pwsyas){
		//Wsyas (kg/kg)   P,Pwsyas(Pa)
		
		return 0.62198*Pwsyas/(P-Pwsyas);
	}


	double P3(double Pwsyas,double Wsyas){
		//P3,Pwsyas(Pa)   Wsyas(kg/kg)
		
		return 0.62198*Pwsyas/Wsyas+Pwsyas;
	}
	

	double Pwsyas2(double P,double Wsyas){
		
		return (P*Wsyas)/(Wsyas+0.62198);
	}
	
	double Wsyas3(double H,double W,double Tyas){
		// Hsyas,H,Hw (kJ/
		
		double Hsyas=0,Wsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;		
		Hw=4.186*Tyas;
		
		return (Hsyas-H)/Hw+W;
	}
	

	double W3(double Wsyas,double H,double Tyas){
		
		double Hsyas=0;
		double Hw=0;
		Hsyas=Tyas+(2501+1.805*Tyas)*Wsyas;	
		Hw=4.186*Tyas;
		
		return (H-Hsyas)/Hw+Wsyas;
	}
	

	double W4(double Wsyas,double T,double Tyas){
		
		double a=0,b=0,c=0;
		a=(2501-2.381*Tyas)*Wsyas;
		b=T-Tyas;
		c=2501+1.805*T-4.186*Tyas;
		
		return (a-b)/c;
	}
	

	double Tyas(double Wsyas,double W,double T){
		double a=0,b=0,c=0;
		a=2501*(Wsyas-W);
		b=T*(1+1.805*W);
		c=2.381*Wsyas-4.186*W-1;
		
		return (a-b)/c;
	}
	

	double Wsyas2(double W,double T,double Tyas){
		double a=0,b=0,c=0;
		a=(2501+1.805*T-2.381*Tyas)*W;
		b=(T-Tyas);
		c=2501-2.381*Tyas;

		return (a+b)/c;
	}
	

	double T3(double Wsyas,double W,double Tyas){
		double a=0,b=0;
		a=(2501-2.381*Tyas)*Wsyas-(2501-4.186*Tyas)*W+Tyas;
		b=1+1.805*W;
		
		return a/b;
	}
	

	double RH(double Pw,double Pws){
		
		return Pw/Pws;
	}
	

	double Tyas1521(double P,double Pwsyas,double W,double T){
		double a=0,b=0,c=0,d=0;
		a=0.62198*Pwsyas/(P-Pwsyas);
		b=2501*(a-W)-T*(1+1.805*W);
		c=2.381*a-4.186*W-1;
		
		return b/c;
	}
	

	double Tyas1521(double varP,double varW,double varT){
		// 15 nolu kombinasyon ile 21 nolu kombinasyon birle�tiriliyor
		double varsubTyas, X,Y, AA,DD1,DD2;
		double DF1,DF2,a,b,varsubWsyas,varsubW1;
		double varsubPwsyas;
		double varsubRH,varsubPw,varsubPws;
		int T;
		double iter,fark;
		varsubTyas = varT ;
		
		iter=1;
		fark=1;


		varsubPw=Pw2(varP,varW);
		varsubPws=Pws(varT);
		varsubRH=varsubPw/varsubPws;


		do
		{
			varsubTyas = varsubTyas - iter;
			if((varsubRH > 0.991)&&(Math.abs(varsubTyas - varT) < 1)){
				iter = 1; 
				fark = 1;
			}

			varsubPwsyas = Pwsyas(varsubTyas);
			varsubWsyas = Wsyas(varP, varsubPwsyas);
			a = (2501 - 2.381 * varsubTyas) * varsubWsyas - (varT - varsubTyas);
			b = 2501 + 1.805 * varT - 4.186 * varsubTyas;
			varsubW1 = a / b;
			if(Math.abs(varW-varsubW1)<=0.0000000001) 
				return varsubTyas;

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
			if(Math.abs(DF1)<0.0000000001) 
				return DD1;

			if(Math.abs(DF2)<0.0000000001) 
				return DD2;

			if(X==Y) 
				return DD1;

			if (Math.abs(DF1) <Math.abs(DF2))
			{
				if (DF1<0)
				{
					X = DD1; 
					Y = DD1 + AA;
				}

				if (DF1>0)
				{
					X = DD1 - AA; 
					Y = DD1;
				}

				if(DF1==0)
					return DD2;
				
			}
			else
			{
				if(DF2<0){X = DD2; Y = DD2 + AA;}

				if(DF2>0){X = DD2 - AA; Y = DD2;}

				if(DF2==0){return DD1;}
			}
		}while(!((Math.abs(DD1 - DD2) < 0.0000000001)||(Math.abs(varsubW1 - varW) < 0.0000000001)));


		return DD1;
	}
	


	public double IkinciDerecedenDenklem(double a,double b,double c){
		double delta,x1=0;
		delta=b*b-4*a*c;

		if (delta==0){x1=-b/(2*a);}

		if (delta>0){x1=(-b+Math.pow(delta,0.5))/(2*a);}

		return x1;
	}
	
	
	public double LR(double x1,double y1, double x2,double y2, double aranan){
		double m,n,lr;
		m=(y2-y1)/(x2-x1);
		n=y2-m*x2;
		lr=m*aranan+n;
		return lr;
	}


	public double v(double T,double P,double W){
		//Havan�n hacmini m3/kg kuru hava olarak bulur.
		
		return 287.055*(T+273.15)*(1+1.6075*W)/P;
	}
		
}



