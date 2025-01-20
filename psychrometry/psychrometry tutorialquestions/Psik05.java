//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;

public class Psik05 extends java.applet.Applet{
	TextField txtT1,txtV1,txtT2,txtRH2,txtV2,txtP;
	Label lblT10,lblT11,lblV10,lblV11,lblT20,lblT21,lblRH20,lblRH21,lblV20,lblV21,lblP0,lblP1;
	Label lblV30,lblV3,lblV31,lblW30,lblW31,lblW3,lblRH30,lblRH3,lblRH31,lblT30,lblT3,lblT31;
	Button hesapla;
	Panel p,gp;
	Color c,co;
	Font f,fo;
	DecimalFormat df;


	public void init(){
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbl);
		c=new Color(150,185,205);
		co=new Color(140,175,195);
		f=new Font("Arial",Font.PLAIN,11);
		fo=new Font("Arial",Font.ITALIC,14);
		setBackground(c);
		
		
		lblP0=new Label("Total Pressure");txtP=new TextField(14);lblP1=new Label("kPa");
		lblT10=new Label("Temperature of Saturated Air");txtT1=new TextField(14);lblT11=new Label("�C");
		lblV10=new Label("Rate of Saturated Air");txtV1=new TextField(14);lblV11=new Label("m3/min");
		lblT20=new Label("Temperature of Outside Air");txtT2=new TextField(14);lblT21=new Label("�C");
		lblRH20=new Label("Relative Humidity of Outside Air");txtRH2=new TextField(14);lblRH21=new Label("%RH");
		lblV20=new Label("Rate of Outside Air");txtV2=new TextField(14);lblV21=new Label("m3/min");
		lblV30=new Label("Rate of Mixture");lblV3=new Label("               ");lblV31=new Label("m3/min");
		lblW30=new Label("Spesific Humidity of Mixture");lblW3=new Label("               ");lblW31=new Label("kg H2O/kg kuru hava");
		lblRH30=new Label("Relative Humidity of Mixture");lblRH3=new Label("               ");lblRH31=new Label("%");
		lblT30=new Label("Temperature of Mixture");lblT3=new Label("               ");lblT31=new Label("�C");
		hesapla=new Button("Calculate");
		
		
		lblP0.setFont(f);txtP.setFont(f);lblP1.setFont(f);
		lblT10.setFont(f);txtT1.setFont(f);;lblT11.setFont(f);
		lblV10.setFont(f);txtV1.setFont(f);lblV11.setFont(f);
		lblT20.setFont(f);txtT2.setFont(f);lblT21.setFont(f);
		lblRH20.setFont(f);txtRH2.setFont(f);lblRH21.setFont(f);
		lblV20.setFont(f);txtV2.setFont(f);lblV21.setFont(f);
		lblV30.setFont(f);lblV3.setFont(f);lblV31.setFont(f);
		lblW30.setFont(f);lblW3.setFont(f);lblW31.setFont(f);
		lblRH30.setFont(f);lblRH3.setFont(f);lblRH31.setFont(f);
		lblT30.setFont(f);lblT3.setFont(f);lblT31.setFont(f);
		hesapla.setFont(fo);
		
		
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblT10,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtT1,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblT11,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblV10,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtV1,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblV11,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblT20,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtT2,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblT21,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH20,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRH2,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH21,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblV20,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtV2,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblV21,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblV30,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblV3,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblV31,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblW30,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblW3,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblW31,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH30,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRH3,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH31,gbc,2,8,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblT30,gbc,0,9,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblT3,gbc,1,9,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblT31,gbc,2,9,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,10,1,1);
		
		
		lblV30.setBackground(co);lblV3.setBackground(co);lblV31.setBackground(co);
		lblW30.setBackground(co);lblW3.setBackground(co);lblW31.setBackground(co);
		lblRH30.setBackground(co);lblRH3.setBackground(co);lblRH31.setBackground(co);
		lblT30.setBackground(co);lblT3.setBackground(co);lblT31.setBackground(co);
	}


	private void add(Component comp,GridBagConstraints gb,int x,int y,int w,int h){
		gb.gridx=x;
		gb.gridy=y;
		gb.gridwidth=w;
		gb.gridheight=h;
		add(comp,gb);
	}


	public boolean action(Event evt,Object obj)
	{
		if(evt.target.equals(hesapla)){
			PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
			double h1,w1,v1,V1,T1,ma1,h2,w2,v2,T2,V2,RH2,ma2;
			double ma3,w3,h3,T3,RH3,v3,V3,P;
			double Pws,Pw;
			df=new DecimalFormat("0.####");
			T1=Double.valueOf(txtT1.getText()).doubleValue();
			V1=Double.valueOf(txtV1.getText()).doubleValue();
			
			T2=Double.valueOf(txtT2.getText()).doubleValue();
			RH2=Double.valueOf(txtRH2.getText()).doubleValue();
			V2=Double.valueOf(txtV2.getText()).doubleValue();
			
			P=Double.valueOf(txtP.getText()).doubleValue();		//kPa olarak okunuyor
			Pws=pf.Pws(T1);
			Pw=Pws;		//Doymu� hava
			
			w1=pf.W2(P*1000,Pw);
			h1=pf.H(T1,w1);
			v1=287.055*(T1+273.15)*(1+1.6078*w1)/(P*1000);
			
			Pws=pf.Pws(T2);
			Pw=Pws*RH2/100;
			
			w2=pf.W2(P*1000,Pw);
			h2=pf.H(T2,w2);
			v2=287.055*(T2+273.15)*(1+1.6078*w2)/(P*1000);
			
			ma1=V1/v1;
			
			ma2=V2/v2;
			
			ma3=ma1+ma2;
			
			w3=(w2*ma2+w1*ma1)/(ma1+ma2);
			h3=(h2*ma2+h1*ma1)/(ma1+ma2);
			T3=pf.T2(h3,w3);
			
			Pws=pf.Pws(T3);
			Pw=pf.Pw2(P*1000,w3);
			RH3=Pw/Pws*100;
			
			v3=287.055*(T3+273.15)*(1+1.6078*w3)/(P*1000);
			V3=v3*ma3;
			
			lblV3.setText(String.valueOf(df.format(V3)));
			lblT3.setText(String.valueOf(df.format(T3)));
			lblRH3.setText(String.valueOf(df.format(RH3)));
			lblW3.setText(String.valueOf(df.format(w3)));
		}
		return true;
	}
}