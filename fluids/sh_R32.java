package jspclass.JFluids;

//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
public class KizginR32 extends MHFluidsSuperHeated {
  private double aranan;
  private double LR(double x1,double y1,double x2,double y2,double x3){
    double m,n;
    m = (y2 - y1) / (x2 - x1);
    n = y2 - m * x2;
    return m * x3 + n;
  }

public double v(double Pxp,double Txt){
  //Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
  int k,i;
  double aradeger[]=new double[5];
  double sondeger[]=new double[2];
  double P[] ={0.01, 0.1, 1, 3, 5};
  k = 0;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k] == 0.01 || P[k - 1] == 0.01){
        double T[] ={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={3.5537, 4.3582, 5.16, 5.9605, 6.7605, 7.5603, 8.3599, 9.1594, 9.9588, 10.7581, 11.5574, 12.357};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.1 || P[k-1] == 0.1) {
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.3436, 0.4292, 0.5118, 0.5932, 0.674, 0.7546, 0.835, 0.9153, 0.9995, 1.0757, 1.1558, 1.236};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[1] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 1 || P[k-1] ==1) {
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0467, 0.0564, 0.0654, 0.074, 0.0825, 0.0909, 0.0992, 0.1075, 0.1157, 0.1239};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[2] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 3 || P[k-1] == 3) {
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] = {0.0112, 0.0164, 0.0202, 0.0236, 0.0268, 0.0298, 0.0328, 0.0358, 0.0386, 0.0415};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[3] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 5 || P[k-1] ==5) {
        double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0082, 0.0112, 0.0136, 0.0157, 0.0177, 0.0196, 0.0214, 0.0232, 0.025};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[4] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  int sd = 0;
  for (int X =0;X<aradeger.length;X++)
  {
    if(aradeger[X]!= 0)
    {
      sondeger[sd] = aradeger[X];
      sd = sd + 1;
    }
  }
  aranan = LR(P[k-1], sondeger[0], P[k], sondeger[1], Pxp);
  return aranan;

}//v (m3/kg)

public double h(double Pxp,double Txt){
  //P=MPa h=kJ/kg
  double  aradeger[]=new double[5];
  double sondeger[]=new double[2];
  double P[]={0.01, 0.1, 1, 3, 5};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={503.8, 541.9, 583.3, 628.4, 677.4, 730.3, 786.9, 846.9, 910, 976, 1044.6, 1115.7};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }

  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={499.3, 539.5, 581.7, 627.3, 676.6, 729.7, 786.3, 846.4, 909.6, 975.7, 1044.4, 1115.4};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]==1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={565.5, 616.5, 668.6, 723.4, 781.2, 842.1, 905.9, 972.5, 1041.6, 1113.1};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={514.1, 589.3, 650, 709.2, 769.8, 832.6, 897.9, 965.7, 1035.7, 1108};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 5 || P[k-1]== 5 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={555.3, 629.8, 694.7, 758.4, 823.3, 890.1, 959, 1030, 1103.1};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  int sd = 0;
  for(int X =0;X<aradeger.length;X++){
    if(aradeger[X]!=0 ){
        sondeger[sd] = aradeger[X];
        sd = sd + 1;
    }
  }
  aranan = LR(P[k - 1], sondeger[0], P[k], sondeger[1], Pxp);
  return aranan;

}//h (kJ/kg)



public double s(double Pxp,double Txt){
  //P=MPa h=kJ/kg
  double  aradeger[]=new double[5];
  double sondeger[]=new double[2];
  double P[]={0.01, 0.1, 1, 3, 5};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  //
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.778, 2.932, 3.071, 3.201, 3.324, 3.442, 3.556, 3.665, 3.771, 3.873, 3.971, 4.066};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.395, 2.558, 2.7, 2.831, 2.955, 3.073, 3.187, 3.296, 3.402, 3.504, 3.603, 3.698};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]==1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.295, 2.442, 2.573, 2.695, 2.811, 2.922, 3.029, 3.132, 3.231, 3.326};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.994, 2.211, 2.364, 2.496, 2.618, 2.732, 2.842, 2.946, 3.047, 3.143};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 5 || P[k-1]== 5 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.057, 2.245, 2.39, 2.518, 2.637, 2.749, 2.855, 2.957, 3.054};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }

  int sd = 0;
  for(int X =0;X<aradeger.length;X++){
    if(aradeger[X]!=0 ){
        sondeger[sd] = aradeger[X];
        sd = sd + 1;
    }
  }
  aranan = LR(P[k - 1], sondeger[0], P[k], sondeger[1], Pxp);
  return aranan;
  }


} //Class KizginR32

