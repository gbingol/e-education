//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.lang.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;

public class Psik03 extends java.applet.Applet
{
	TextField txtTin,txtRHin,txtTex,txtTsu,txtP,txtVhava;
	Label lblTin,lblRHin,lblTex,lblTsu,lblP,lblVhava;
	Label lblTin1,lblRHin1,lblTex1,lblTsu1,lblP1,lblVhava1;
	Label lblQ0,lblMsu0,lblMsu1,lblQ1,lblMsu2,lblQ2;
	Panel p,gp;
	Button hesapla;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
	//
        public void init() throws NullPointerException {
		try
		{
			c=new Color(150,190,201);
			co=new Color(140,180,191);
			setBackground(c);
			f=new Font("Arial",Font.PLAIN,12);
			//
			GridBagLayout gbl=new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc=new GridBagConstraints();

			//gp=new Panel();
			//gp.setLayout(new GridLayout(8,3));
			//
			lblP=new Label("Pressure");txtP=new TextField(14);lblP1=new Label("kPa");
			lblVhava=new Label("Volumetric Flow Rate of Air");txtVhava=new TextField(14);lblVhava1=new Label("m3/min");
			lblTin=new Label("Inlet Temperature");txtTin=new TextField(14);lblTin1=new Label("�C");
			lblRHin=new Label("Inlet Relative Humidity");txtRHin=new TextField(14);lblRHin1=new Label("%");
			lblTex=new Label("Exit Temperature");txtTex=new TextField(14);lblTex1=new Label("�C");
			lblTsu=new Label("Condensation Temperature of Water");txtTsu=new TextField(14);lblTsu1=new Label("�C");
			lblTsu.setSize(40,40);
			lblQ0=new Label("Heat Removal from Air");lblQ1=new Label("                   ");lblQ2=new Label("kJ/min");
			lblMsu0=new Label("Moisture Removal from Air");lblMsu1=new Label("                  ");lblMsu2=new Label("kg/min");
			hesapla=new Button("Calculate");; fo=new Font("Arial",Font.ITALIC,14);hesapla.setFont(fo);
			
			
			lblP.setFont(f);txtP.setFont(f);lblP1.setFont(f);
			lblVhava.setFont(f);txtVhava.setFont(f);lblVhava1.setFont(f);
			lblTin.setFont(f);txtTin.setFont(f);lblTin1.setFont(f);
			lblRHin.setFont(f);txtRHin.setFont(f);lblRHin1.setFont(f);
			lblTex.setFont(f);txtTex.setFont(f);lblTex1.setFont(f);
			lblTsu.setFont(f);txtTsu.setFont(f);lblTsu1.setFont(f);
			lblQ0.setFont(f);lblQ1.setFont(f);lblQ2.setFont(f);
			lblQ0.setBackground(co);lblQ1.setBackground(co);lblQ2.setBackground(co);
			lblMsu0.setBackground(co);lblMsu1.setBackground(co);lblMsu2.setBackground(co);
			lblMsu0.setFont(f);lblMsu1.setFont(f);lblMsu2.setFont(f);
			
			
			gbc.weightx=5;gbc.weighty=5;gbc.anchor=GridBagConstraints.EAST;
			add(lblP,gbc,0,0,1,1);add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblVhava,gbc,0,1,1,1);add(txtVhava,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblVhava1,gbc,2,1,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblTin,gbc,0,2,1,1);add(txtTin,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTin1,gbc,2,2,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblRHin,gbc,0,3,1,1);add(txtRHin,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHin1,gbc,2,3,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblTex,gbc,0,4,1,1);add(txtTex,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTex1,gbc,2,4,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblTsu,gbc,0,5,1,1);add(txtTsu,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTsu1,gbc,2,5,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblQ0,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblQ1,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblQ2,gbc,2,6,1,1);
			gbc.anchor=GridBagConstraints.EAST;add(lblMsu0,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblMsu1,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblMsu2,gbc,2,7,1,1);
			gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,9,1,1);
		
		}catch(NullPointerException x){}
	}


	public void add(Component c, GridBagConstraints gbc, int x, int y, int w, int h){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=w;
		gbc.gridheight=h;
		add(c,gbc);
	}


	public boolean action(Event e,Object o){
		if(e.target.equals(hesapla)){
			SuOzellikleri su=new SuOzellikleri();
			PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
			double Ptop,Tin,RHin,Tex,Tsu,Msu,Q,Vhava;
			double h1,w1,v1,h3,w3,hsu,ma;
			double pws,pw,W,Ws,Td,Tyas,pwsyas,Wsyas,h;
			double pws2,pw2;
			
			Ptop=Double.valueOf(txtP.getText()).doubleValue();
			Vhava=Double.valueOf(txtVhava.getText()).doubleValue();
			Tin=Double.valueOf(txtTin.getText()).doubleValue();
			RHin=Double.valueOf(txtRHin.getText()).doubleValue();
			Tex=Double.valueOf(txtTex.getText()).doubleValue();
			Tsu=Double.valueOf(txtTsu.getText()).doubleValue();
			
			pws=pf.Pws(Tin);
			pw=RHin/100*pws;
			
			W=pf.W2(Ptop*1000,pw);
			h=pf.H(Tin,W);
			
			h1=h;
			w1=W;
			v1=287.055*(Tin+273.15)*(1+1.6078*w1)/(Ptop*1000);

			pws2=pf.Pws(Tex);
			pw2=pws2;			//RH=%100

			w3=pf.W2(Ptop*1000,pw2);
			h3=pf.H(Tex,w3);

			hsu=su.Hf(Tsu);
			ma=Vhava/v1;
			Msu=ma*(w1-w3);

			lblMsu1.setText(String.valueOf(df.format(Msu)));
			
			Q=ma*(h3-h1)+Msu*hsu;
			lblQ1.setText(String.valueOf(df.format(Q)));
		}
		return true;
	}
}
