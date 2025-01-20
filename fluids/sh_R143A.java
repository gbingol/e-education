package jspclass.JFluids;

//Basýnçlar MPa, h=kJ/kg,v=m3/kg, u=kJ/kg
public class KizginR143A extends MHFluidsSuperHeated {
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
  double P[] ={0.01, 0.05, 0.1, 1, 2.5, 3};
  k = 0;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k] == 0.01 || P[k - 1] == 0.01){
        double T[] ={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={2.192, 2.6951, 3.1929, 3.6889, 4.1841, 4.679, 5.1738, 5.6686, 6.1633, 6.658, 7.1527, 7.6474};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.05 || P[k-1] == 0.05) {
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.4255, 0.5332, 6353, 0.7355, 0.835, 0.9342, 1.0333, 1.1323, 1.2313, 1.3303, 1.4293, 1.5282};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[1] = LR(T[i - 1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 0.1 || P[k-1] ==0.1) {
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.2629, 0.3155, 0.3663, 0.4163, 0.4661, 0.5157, 0.5653, 0.6148, 0.6643, 0.7138, 0.7633};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[2] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if(P[k] == 1 || P[k-1] ==1 ) {
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[] ={0.0274, 0.0338, 0.0394, 0.0448, 0.0499, 0.055, 0.0601, 0.0651, 0.0701, 0.0751};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[3] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 2.5 || P[k-1] ==2.5) {
        double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0114, 0.0142, 0.0167, 0.019, 0.0212, 0.0233, 0.0254, 0.0274, 0.0295};
        i = 0;
        do{
        i = i + 1;
        }while(!(Txt<=T[i]));
        aradeger[4] = LR(T[i-1], v1[i - 1], T[i], v1[i], Txt);
  }
  if (P[k] == 3 || P[k-1] ==3) {
        double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double v1[]={0.0091, 0.0115, 0.0136, 0.0156, 0.0175, 0.0193, 0.021, 0.0228, 0.0245};
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
  double P[]={0.01, 0.05, 0.1, 1, 2.5, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={362.9, 404.5, 451.4, 503.3, 559.9, 620.6, 685, 752.7, 823.3, 896.5, 972, 1049.7};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }

  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[] ={359.8, 403.1, 450.7, 503, 559.7, 620.5, 684.9, 752.6, 823.2, 896.4, 971.9, 1049.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]==0.1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={401.4, 449.9, 502.5, 559.4, 620.3, 684.7, 752.4, 823, 896.3, 971.8, 1049.5};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={433.9, 493.9, 554, 616.4, 681.7, 749.8, 820.7, 894.1, 969.8, 1047.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 2.5 || P[k-1]== 2.5 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={476.4, 543, 608.6, 675.6, 744.9, 816.6, 890.5, 966.6, 1044.6};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], h1[i - 1], T[i], h1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double  h1[]={471.4, 539.1, 605.7, 673.5, 743.2, 815.1, 889.3, 965.5, 1043.7};
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
  double P[]={0.01, 0.05, 0.1, 1, 2.5, 3};
  int k = 0,i;
  do{
    k = k + 1;
  }while(!(Pxp<=P[k]));
  //
  if(P[k]== 0.01 || P[k-1]== 0.01 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.972, 2.14, 2.298, 2.447, 2.589, 2.725, 2.854, 2.977, 3.095, 3.208, 3.317, 3.421};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[0] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.05 || P[k-1]== 0.05 ){
        double T[]={-50, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.802, 1.977, 2.137, 2.287, 2.429, 2.565, 2.695, 2.818, 2.936, 3.049, 3.157, 3.261};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[1] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 0.1 || P[k-1]==0.1 ){
        double T[]={0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.903, 2.066, 2.218, 2.361, 2.496, 2.626, 2.749, 2.867, 2.98, 3.089, 3.193};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[2] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 1 || P[k-1]== 1 ){
        double T[]={50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.801, 1.974, 2.125, 2.264, 2.395, 2.52, 2.638, 2.752, 2.86, 2.964};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[3] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 2.5 || P[k-1]== 2.5 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.85, 2.017, 2.163, 2.298, 2.425, 2.544, 2.658, 2.767, 2.872};
        i = 0;
        do{
        i++;
        }while(!(Txt<=T[i]));
       aradeger[4] = LR(T[i - 1], s1[i - 1], T[i], s1[i], Txt);
  }
  if(P[k]== 3 || P[k-1]== 3 ){
        double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500};
        double s1[]={1.821, 1.993, 2.142, 2.278, 2.405, 2.525, 2.64, 2.749, 2.853};
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

