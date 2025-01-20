class HavaOzellikleri {
  public double alfa( double Txt){
    double Alfager,AlfaB,AlfaK,Tk,fark,ort;
    int i;
    double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double Alfa1[]={0.000002501, 0.000005745, 0.000010165, 0.000015675, 0.00002216, 0.00002983, 0.0000376, 0.00004222, 0.00005564, 0.00006532, 0.00007512, 0.00008578, 0.00009672, 0.00010774, 0.00011951, 0.00013097, 0.00014271, 0.0001551, 0.00016779, 0.0001969, 0.0002251, 0.0002583, 0.000292, 0.0003262, 0.0003609, 0.0003977, 0.0004379, 0.0004811, 0.000526, 0.0005715, 0.000612, 0.000654, 0.000702, 0.0007441};
    Tk = Txt + 273.15;
    if((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    AlfaB = Alfa1[i]; AlfaK = Alfa1[i-1];
    fark = Tk - T[i-1];
    ort = (AlfaB - AlfaK) / (T[i] - T[i-1]);
    Alfager = AlfaK + fark * ort;
    return Alfager;
  }
  //
  public double Pr(double Txt){
    double Prger,Prb,Prk,Tk,fark,ort;
    int i;
    double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double Pr1[]={0.77, 0.753, 0.739, 0.722, 0.708, 0.697, 0.689, 0.683, 0.68, 0.68, 0.68, 0.682, 0.684, 0.686, 0.689, 0.692, 0.696, 0.699, 0.702, 0.704, 0.707, 0.705, 0.705, 0.705, 0.705, 0.705, 0.704, 0.704, 0.702, 0.7, 0.707, 0.71, 0.718, 0.73};
    Tk = Txt + 273.15;
    if ((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    Prb = Pr1[i]; Prk = Pr1[i-1];
    fark = Tk - T[i-1];
    ort = (Prb - Prk) / (T[i] - T[i-1]);
    Prger = Prk + fark * ort;
    return Prger;
  }
  //
  public double k(double Txt){
    double kGer,kB,kK,Tk,fark,ort;
    int i;
    double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double K1[]={0.009246, 0.013735, 0.01809, 0.02227, 0.02624, 0.03003, 0.03365, 0.03707, 0.04038, 0.0436, 0.04659, 0.04953, 0.0523, 0.05509, 0.05779, 0.06028, 0.06279, 0.06525, 0.06752, 0.0732, 0.0782, 0.0837, 0.0891, 0.0946, 0.1, 0.105, 0.111, 0.117, 0.124, 0.131, 0.139, 0.149, 0.161, 0.175};
    Tk = Txt + 273.15;
    if((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    kB = K1[i]; kK = K1[i-1];
    fark = Tk - T[i-1];
    ort = (kB - kK) / (T[i] - T[i-1]);
    kGer = kK + fark * ort;
    return kGer;
  }
  public double Viskozite(double Txt){
    double Visger,Visb,Visk,Tk,fark,ort;
    int i;
    double T[]={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double Vis1[] ={0.000006924, 0.000010283, 0.000013289, 0.00001599, 0.000018462, 0.00002075, 0.00002286, 0.00002484, 0.00002671, 0.00002848, 0.00003018, 0.00003177, 0.00003332, 0.00003481, 0.00003625, 0.00003765, 0.00003899, 0.00004023, 0.00004152, 0.0000444, 0.0000469, 0.0000493, 0.0000517, 0.000054, 0.0000563, 0.0000585, 0.0000607, 0.0000629, 0.000065, 0.0000672, 0.0000693, 0.0000714, 0.0000735, 0.0000757};
    Tk = Txt + 273.15;
    if ((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    Visb = Vis1[i]; Visk = Vis1[i-1];
    fark = Tk - T[i-1];
    ort = (Visb - Visk) / (T[i] - T[i-1]);
    Visger = Visk + fark * ort;
    return Visger;
  }
  //
  public double Cp(double Txt){
    double Cpger,Cpk,Cpb,fark,ort,Tk;
    int i;
    double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double Cp1[]={1.0266, 1.0099, 1.0061, 1.0053, 1.0057, 1.009, 1.014, 1.0207, 1.0295, 1.0392, 1.0551, 1.0635, 1.0752, 1.0856, 1.0978, 1.1095, 1.1212, 1.1312, 1.1417, 1.16, 1.179, 1.197, 1.214, 1.23, 1.248, 1.267, 1.287, 1.309, 1.338, 1.372, 1.419, 1.482, 1.574, 1.688};
    Tk = Txt + 273.15;
    if ((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    Cpb = Cp1[i]; Cpk = Cp1[i-1];
    fark = Tk - T[i-1];
    ort = (Cpb - Cpk) / (T[i] - T[i-1]);
    Cpger = Cpk + fark * ort;
    return Cpger;
  }
  //
  public double Ro(double Txt){
    double Roger,Rob,Rok,ort,fark,Tk;
    int i=0;
    double T[] ={100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500};
    double Ro1[]={3.601, 2.3675, 1.7684, 1.4128, 1.1774, 0.998, 0.8826, 0.7833, 0.7048, 0.6423, 0.5879, 0.543, 0.503, 0.4709, 0.4405, 0.4149, 0.3925, 0.3716, 0.3524, 0.3204, 0.2947, 0.2707, 0.2515, 0.2355, 0.2211, 0.2082, 0.197, 0.1858, 0.1762, 0.1682, 0.1602, 0.1538, 0.1458, 0.1394};
    Tk = Txt + 273.15;
    if ((Tk < 99.99999999999)||(Tk > 2500)) return 0;
    i = 0;
    do{
      i = i + 1;
    }while(!(Tk <= T[i]));
    Rob = Ro1[i]; Rok = Ro1[i-1];
    fark = Tk - T[i-1];
    ort = (Rob - Rok) / (T[i] - T[i-1]);
    Roger = Rok + fark * ort;
    return Roger;
  }
} 