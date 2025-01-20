package jspclass.JFluids;

//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
public class KizginR22 extends MHFluidsSuperHeated {
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
  double aradeger[]=new double[6];
  double sondeger[]=new double[2];
  double P[] ={0.01, 0.05, 0.1, 1, 2.5, 4};
  k = 0;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k] == 0.01 || P[k - 1] == 0.01){
        double T[] ={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={2.2342, 2.6213, 3.0074, 3.393, 3.7783, 4.1633, 4.5482, 5.0293, 5.5103, 5.9913, 6.4723, 6.9532, 7.434};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.05 || P[k-1] == 0.05) {
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.4406, 0.5201, 0.5985, 0.6764, 0.7539, 0.8313, 0.9086, 1.005, 1.1014, 1.1978, 1.2941, 1.3903, 1.4866};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[1] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.1 || P[k-1] == 0.1) {
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.2163, 0.2574, 0.2974, 0.3368, 0.3759, 0.4148, 0.4536, 0.502, 0.5503, 0.5986, 0.6468, 0.695, 0.7432};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[2] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 1 || P[k-1] == 1) {
        double T[]={25, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] = {0.0239, 0.0273, 0.0334, 0.0389, 0.0441, 0.0493, 0.0543, 0.0593, 0.0643, 0.0692, 0.0741};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[3] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 2.5 || P[k-1] == 2.5) {
        double T[] ={75, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0099, 0.0116, 0.0144, 0.0168, 0.0191, 0.0213, 0.0234, 0.0255, 0.0275, 0.0296};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[4] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 4 || P[k-1] == 4) {
        double T[] = {100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0058, 0.0082, 0.01, 0.0116, 0.013, 0.0144, 0.0158, 0.0171, 0.0184};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[5] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
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
  double  aradeger[]=new double[6];
  double sondeger[]=new double[2];
  double P[]={0.01, 0.05, 0.1, 1, 2.5, 4};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={391.1, 415, 440.8, 468.4, 497.8, 528.8, 561.3, 603.7, 648.1, 694, 741.4, 790.1, 839.9};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }

  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={390.1, 414.3, 440.3, 468.1, 497.5, 528.6, 561.1, 603.6, 647.9, 693.9, 741.3, 790, 839.9};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={388.7, 413.4, 439.7, 467.6, 497.2, 528.3, 560.8, 603.4, 647.8, 693.8, 741.2, 789.9, 839.8};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={25, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={414.3, 434.6, 474.4, 514.7, 556.5, 599.9, 644.9, 691.3, 739.1, 788.1, 838.1};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 2.5 || P[k-1]== 2.5 ){
        double T[]={75, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={433.8, 458.8, 504.4, 548.9, 594, 640, 687.2, 736.6, 785, 835.5};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 4 || P[k-1]== 4 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={436.9, 492.7, 540.8, 587.8, 635.1, 683.1, 732.1, 782, 832.9};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[5] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
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
  double  aradeger[]=new double[6];
  double sondeger[]=new double[2];
  double P[]={0.01, 0.05, 0.1, 1, 2.5, 4};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  //
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={2.059, 2.153, 2.241, 2.324, 2.403, 2.478, 2.55, 2.635, 2.716, 2.793, 2.866, 2.936, 3.002};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.901, 1.997, 2.085, 2.169, 2.248, 2.323, 2.395, 2.48, 2.561, 2.638, 2.711, 2.781, 2.847};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]== 0.1 ){
        double T[]={-40, 0, 40, 80, 120, 160, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.83, 1.928, 2.017, 2.101, 2.18, 2.256, 2.328, 2.413, 2.494, 2.571, 2.644, 2.714, 2.781};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={25, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.725, 1.791, 1.905, 2.006, 2.1, 2.187, 2.269, 2.347, 2.42, 2.491, 2.557};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 2.5 || P[k-1]== 2.5 ){
        double T[]={75, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.716, 1.786, 1.9, 2, 2.09, 2.174, 2.253, 2.328, 2.399, 2.466};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 4.0 || P[k-1]== 4.0 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.694, 1.834, 1.942, 2.036, 2.123, 2.203, 2.279, 2.35, 2.418};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[5] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
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


} //Class KizginR22

