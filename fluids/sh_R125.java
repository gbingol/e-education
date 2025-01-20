package jspclass.JFluids;

//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
public class KizginR125 extends MHFluidsSuperHeated {
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
  double P[] ={0.01, 0.05, 0.1, 1, 2, 3};
  k = 0;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k] == 0.01 || P[k - 1] == 0.01){
        double T[] ={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={1.5406, 1.8887, 2.2361, 2.5833, 2.9302, 3.277, 3.6237, 3.9703, 4.3169, 4.6634, 5.0099, 5.3564};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.05 || P[k-1] == 0.05) {
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.3038, 0.3748, 0.4452, 0.5153, 0.5851, 0.6548, 0.7244, 0.7939, 0.8634, 0.9238, 1.0022, 1.0716};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[1] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.1 || P[k-1] ==0.1) {
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.1856, 0.2214, 0.2568, 0.292, 0.327, 0.362, 0.3969, 0.4317, 0.4665, 0.5012, 0.536};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[2] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 1 || P[k-1] ==1 ) {
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.0197, 0.0241, 0.0281, 0.032, 0.0358, 0.0395, 0.0432, 0.0468, 0.0504, 0.0539};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[3] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 2 || P[k-1] ==2) {
        double T[] ={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0081, 0.0111, 0.0135, 0.0157, 0.0177, 0.0197, 0.0216, 0.0235, 0.0253, 0.0272};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[4] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 3 || P[k-1] ==3) {
        double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0067, 0.0086, 0.0102, 0.0117, 0.0131, 0.0144, 0.0157, 0.017, 0.0182};
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
  double P[]={0.01, 0.05, 0.1, 1, 2, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={306.5, 342.2, 382.2, 426.2, 473.6, 524, 577, 632.3, 689.5, 748.4, 808.9, 870.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }

  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={305.9, 341.7, 381.9, 425.9, 473.4, 523.8, 576.9, 632.2, 689.4, 748.4, 808.8, 870.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]==0.1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={341.2, 381.4, 425.6, 473.1, 523.6, 576.7, 632, 689.3, 748.3, 808.8, 870.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={373, 419.3, 468.3, 519.8, 573.7, 629.6, 687.4, 746.8, 807.7, 869.8};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 2 || P[k-1]== 2 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={360.4, 411.6, 462.7, 515.6, 570.4, 627, 685.4, 745.2, 806.5, 868.9};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={402.7, 456.9, 511.3, 567.1, 624.5, 683.4, 743.7, 805.3, 868.1};
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
  double P[]={0.01, 0.05, 0.1, 1, 2, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  //
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.659, 1.803, 1.937, 2.064, 2.83, 2.295, 2.402, 2.503, 2.599, 2.69, 2.776, 2.859};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.546, 1.69, 1.825, 1.952, 2.071, 2.184, 2.29, 2.391, 2.487, 2.578, 2.665, 2.747};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]==0.1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.641, 1.776, 1.903, 2.023, 2.135, 2.242, 2.343, 2.439, 2.53, 2.616, 2.699};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.598, 1.731, 1.854, 1.969, 2.077, 2.18, 2.276, 2.368, 2.455, 2.538};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 2 || P[k-1]== 2 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.52, 1.667, 1.796, 1.914, 2.024, 2.127, 2.225, 2.317, 2.405, 2.488};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.62, 1.757, 1.878, 1.99, 2.095, 2.194, 2.287, 2.375, 2.459};
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


} //Class KizginR125

