public abstract class MHFluids{
	//
	public abstract double P(double Txt);

  protected boolean isAkiskanKizgin(double Basinc,double Sicaklik,MHFluids Akiskan){
    double doymaBasinci=0;
    doymaBasinci=Akiskan.P(Sicaklik);
    if(Basinc<doymaBasinci) return true;
    return false;
  }

  protected boolean isAkiskanSikistirilmis(double Basinc,double Sicaklik,MHFluids Akiskan){
    double doymaBasinci=0;
    doymaBasinci=Akiskan.P(Sicaklik);
    if(Basinc>doymaBasinci) return true;
    return false;
  }

}//
