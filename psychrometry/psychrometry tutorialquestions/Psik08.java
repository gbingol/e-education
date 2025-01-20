//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;


public class Psik08 extends java.applet.Applet implements ActionListener
{
	Label lblRwater0,lblRwater1,lblTwaterf0,lblTwaterf1,lblTwatert0,lblTwatert1,lblTinletair0,lblTinletair1;
	Label lblRHinletair0,lblRHinletair1,lblRHexitair0,lblRHexitair1,lblTexitair0,lblTexitair1,lblTmakeupwater0,lblTmakeupwater1;
	Label lblRair0,lblRair,lblRair1,lblRw0,lblRw,lblRw1,lblP0,lblP1;
	TextField txtRwater,txtTwaterf,txtTwatert,txtTinletair,txtRHinletair,txtTexitair,txtRHexitair,txtTmakeupwater,txtP;
	Button hesapla;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
	PsikrometrikFonksiyonlar pf;
	SuOzellikleri su;


	public void init(){
		
		f=new Font("Arial",Font.PLAIN,11);
		fo=new Font("Arial",Font.BOLD,13);
		c=new Color(120,150,180);
		co=new Color(110,140,170);					
		setBackground(c);
		setFont(f);
		//
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbl);
		//Nesnelerin olu�turulmas�

		lblRwater0=new Label("Rate of Water :",Label.RIGHT);
		txtRwater=new TextField(12);
		lblRwater1=new Label("kg/s",Label.LEFT);


		lblTwaterf0=new Label("Initial Temperature of Water :",Label.RIGHT);
		txtTwaterf=new TextField(12);
		lblTwaterf1=new Label("�C",Label.LEFT);

		lblTwatert0=new Label("Final Temperature of Water :",Label.RIGHT);
		txtTwatert=new TextField(12);
		lblTwatert1=new Label("�C",Label.LEFT);

		lblTinletair0=new Label("Temperature of Inlet Air :",Label.RIGHT);
		txtTinletair=new TextField(12);
		lblTinletair1=new Label("�C",Label.LEFT);

		lblRHinletair0=new Label("Relative Humidity of Inlet Air :",Label.RIGHT);
		txtRHinletair=new TextField(12);
		lblRHinletair1=new Label("%",Label.LEFT);

		lblTexitair0=new Label("Temperature of Leaving Air :",Label.RIGHT);
		txtTexitair=new TextField(12);
		lblTexitair1=new Label("�C",Label.LEFT);

		lblRHexitair0=new Label("Relative Humidity of Leaving Air :",Label.RIGHT);
		txtRHexitair=new TextField(12);
		lblRHexitair1=new Label("%",Label.LEFT);

		lblTmakeupwater0=new Label("Temperature of Make-Up Water :",Label.RIGHT);
		txtTmakeupwater=new TextField(12);
		lblTmakeupwater1=new Label("�C",Label.LEFT);

		lblRw0=new Label("Mass Flow Rate of Make-Up Water :",Label.RIGHT);
		lblRw=new Label("                    ",Label.CENTER);
		lblRw1=new Label("kg/s",Label.LEFT);

		lblRair0=new Label("Mass Flow Rate of Air :",Label.RIGHT);
		lblRair=new Label("                    ",Label.CENTER);
		lblRair1=new Label("kg/s",Label.LEFT);

		lblP0=new Label("Total Pressure of Air :",Label.RIGHT);
		txtP=new TextField(12);
		lblP1=new Label("kPa",Label.LEFT);


		hesapla=new Button("Calculate"); 
		hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;
		add(lblRwater0,gbc,0,0,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtRwater,gbc,1,0,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblRwater1,gbc,2,0,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblTwaterf0,gbc,0,1,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtTwaterf,gbc,1,1,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblTwaterf1,gbc,2,1,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblTwatert0,gbc,0,2,1,1);

		gbc.anchor=GridBagConstraints.CENTER;
		add(txtTwatert,gbc,1,2,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblTwatert1,gbc,2,2,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblTinletair0,gbc,0,3,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtTinletair,gbc,1,3,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblTinletair1,gbc,2,3,1,1);
		
		gbc.anchor=GridBagConstraints.EAST;
		add(lblRHinletair0,gbc,0,4,1,1);

		gbc.anchor=GridBagConstraints.CENTER;
		add(txtRHinletair,gbc,1,4,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblRHinletair1,gbc,2,4,1,1);
		
		gbc.anchor=GridBagConstraints.EAST;
		add(lblTexitair0,gbc,0,5,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtTexitair,gbc,1,5,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblTexitair1,gbc,2,5,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblRHexitair0,gbc,0,6,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtRHexitair,gbc,1,6,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblRHexitair1,gbc,2,6,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblTmakeupwater0,gbc,0,7,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtTmakeupwater,gbc,1,7,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblTmakeupwater1,gbc,2,7,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblP0,gbc,0,8,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(txtP,gbc,1,8,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblP1,gbc,2,8,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblRw0,gbc,0,9,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(lblRw,gbc,1,9,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblRw1,gbc,2,9,1,1);

		gbc.anchor=GridBagConstraints.EAST;
		add(lblRair0,gbc,0,10,1,1);
		
		gbc.anchor=GridBagConstraints.CENTER;
		add(lblRair,gbc,1,10,1,1);
		
		gbc.anchor=GridBagConstraints.WEST;
		add(lblRair1,gbc,2,10,1,1);

		gbc.anchor=GridBagConstraints.CENTER;
		add(hesapla,gbc,1,11,1,1);
		//
	}


	private void add(Component comp,GridBagConstraints gb,int x,int y,int w,int h){
		gb.gridx=x;
		gb.gridy=y;
		gb.gridwidth=w;
		gb.gridheight=h;
		add(comp,gb);
	}


	public void actionPerformed(ActionEvent evt){
		String arg=evt.getActionCommand();
		if(arg.equals("Calculate")) hesapla_clicked();
	}


	private void hesapla_clicked(){
		double P,Ta1,Ta2,Pg1,Pg2,Pv1,Pv2,RH1,RH2,hf1,hf2,hf3,hv1,hv2,ma,mw3,mtot,w1,w2,Rw;
		double Twf,Twt,Tmuw;
		
		pf=new PsikrometrikFonksiyonlar();
		
		su=new SuOzellikleri();
		
		P=Double.valueOf(txtP.getText()).doubleValue();
		Rw=Double.valueOf(txtRwater.getText()).doubleValue();
		
		Twf=Double.valueOf(txtTwaterf.getText()).doubleValue();
		Twt=Double.valueOf(txtTwatert.getText()).doubleValue();
		Tmuw=Double.valueOf(txtTmakeupwater.getText()).doubleValue();
		
		Ta1=Double.valueOf(txtTinletair.getText()).doubleValue();
		Ta2=Double.valueOf(txtTexitair.getText()).doubleValue();
		
		RH1=Double.valueOf(txtRHinletair.getText()).doubleValue()/100;
		RH2=Double.valueOf(txtRHexitair.getText()).doubleValue()/100;
		
		Pg1=pf.Pws(Ta1)/1000;
		Pg2=pf.Pws(Ta2)/1000;
		Pv1=RH1*Pg1;
		Pv2=RH2*Pg2;
		
		w1=0.622*Pv1/(P-Pv1);
		w2=0.622*Pv2/(P-Pv2);
		
		hf1=su.Hf(Twf);
		hf2=su.Hf(Twt);
		hf3=su.Hf(Tmuw);
		
		hv1=2501+1.863*Ta1;
		hv2=2501+1.863*Ta2;
		
		ma=Rw*(hf2-hf1)/((1.005*(Ta1-Ta2)+w1*hv1-w2*hv2+(w2-w1)*hf3));
		mw3=ma*(w2-w1);
		mtot=ma*(1+w1);
		
		lblRw.setText(String.valueOf(df.format(mw3)));
		lblRair.setText(String.valueOf(df.format(mtot)));
	}
	
}