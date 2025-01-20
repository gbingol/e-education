package jspclass.JFluids;

//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
public class KizginR23 extends MHFluidsSuperHeated {
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
  double P[] ={0.01, 0.1, 1, 2, 3};
  k = 0;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k] == 0.01 || P[k - 1] == 0.01){
        double T[] ={-100, -50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={2.0429, 2.644, 3.2405, 3.8355, 4.4298, 5.024, 5.6181, 6.2121, 6.806, 7.3999, 7.9938, 8.5877, 9.1815};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.1 || P[k-1] == 0.1) {
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.259, 0.3211, 0.3817, 0.4417, 0.5015, 0.5611, 0.6207, 0.6803, 0.7398, 0.7993, 0.8588, 0.9183};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[1] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 1 || P[k-1] ==1) {
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0289, 0.0362, 0.0429, 0.0492, 0.0554, 0.0616, 0.0677, 0.0738, 0.0799, 0.0859, 0.092};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[2] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 2 || P[k-1] == 2) {
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] = {0.0122, 0.017, 0.0207, 0.0241, 0.0274, 0.0306, 0.0337, 0.0368, 0.0399, 0.043, 0.0461};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[3] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 3 || P[k-1] ==3) {
        double T[] ={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0105, 0.0133, 0.0157, 0.018, 0.0202, 0.0224, 0.0245, 0.0266, 0.0287, 0.0308};
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
  double P[]={0.01, 0.1, 1, 2, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-100, -50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={321.4, 351.1, 384.3, 421, 461.3, 505.1, 552, 601.7, 653.8, 707.3, 761.1, 812.9, 859.8};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }

  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={348.9, 383.1, 420.3, 460.9, 504.7, 551.7, 601.5, 653.6, 707.2, 760.9, 812.8, 859.7};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]==1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={370.8, 413.4, 456.2, 501.2, 548.9, 599.2, 651.6, 705.5, 759.4, 811.5, 858.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 2 || P[k-1]== 2 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={353.3, 404.8, 450.7, 497.2, 545.7, 596.5, 649.4, 703.6, 757.8, 810.1, 857.4};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={395.2, 444.9, 493.1, 542.5, 593.9, 647.2, 701.8, 756.3, 808.8, 856.3};
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
  double P[]={0.01, 0.1, 1, 2, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  //
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-100, -50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.012, 2.163, 2.297, 2.42, 2.536, 2.646, 2.751, 2.851, 2.946, 3.035, 3.118, 3.192, 3.255};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.882, 2.02, 2.145, 2.262, 2.372, 2.477, 2.577, 2.672, 2.761, 2.844, 2.919, 2.982};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]==1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.713, 1.856, 1.979, 2.092, 2.199, 2.3, 2.395, 2.486, 2.569, 2.644, 2.707};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 2 || P[k-1]== 2 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.58, 1.754, 1.886, 2.003, 2.111, 2.213, 2.31, 2.401, 2.484, 2.559, 2.623};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.683, 1.826, 1.948, 2.058, 2.161, 2.259, 2.35, 2.434, 2.509, 2.573};
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


} //Class KizginR23

