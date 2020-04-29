//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;

public class Psik12 extends java.applet.Applet implements ActionListener{
	Label lblTinlet0,lblTinlet1,lblPinlet0,lblPinlet1,lblRHinlet0,lblRHinlet1,lblRair0,lblRair1;
	Label lblTwater0,lblTwater1,lblRwater0,lblRwater1,lblTexit0,lblTexit1,lblPexit0,lblPexit1;
	Label lblRH0,lblRH,lblRH1,lblQ0,lblQ,lblQ1;
	TextField txtTinlet,txtPinlet,txtRHinlet,txtRair,txtTwater,txtRwater,txtTexit,txtPexit;
	Button hesapla;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
	PsikrometrikFonksiyonlar pf;
	SuOzellikleri su;

	public void init()
	{
		
		
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
		lblTinlet0=new Label("Inlet Temperature of Air:",Label.RIGHT);txtTinlet=new TextField(12);lblTinlet1=new Label("�C",Label.LEFT);
		lblPinlet0=new Label("Inlet Pressure of Air :",Label.RIGHT);txtPinlet=new TextField(12);lblPinlet1=new Label("kPa",Label.LEFT);
		lblRHinlet0=new Label("Inlet Relative Humidity of Air :",Label.RIGHT);txtRHinlet=new TextField(12);lblRHinlet1=new Label("%",Label.LEFT);
		lblRair0=new Label("Rate of Air :",Label.RIGHT);txtRair=new TextField(12);lblRair1=new Label("kg/s",Label.LEFT);
		lblTwater0=new Label("Inlet Temperature of Water :",Label.RIGHT);txtTwater=new TextField(12);lblTwater1=new Label("�C",Label.LEFT);
		lblRwater0=new Label("Rate of Water :",Label.RIGHT);txtRwater=new TextField(12);lblRwater1=new Label("kg/s",Label.LEFT);
		lblTexit0=new Label("Temperature of Leaving Air :",Label.RIGHT);txtTexit=new TextField(12);lblTexit1=new Label("�C",Label.LEFT);
		lblPexit0=new Label("Pressure of Leaving Air :",Label.RIGHT);txtPexit=new TextField(12);lblPexit1=new Label("kPa",Label.LEFT);		
		lblRH0=new Label("Relative Humidity of Leaving Air",Label.RIGHT);lblRH=new Label("                    ",Label.CENTER);lblRH1=new Label("%",Label.LEFT);
		lblQ0=new Label("Heat Transfer to the Heater",Label.RIGHT);lblQ=new Label("                    ",Label.CENTER);lblQ1=new Label("kW",Label.LEFT);
		hesapla=new Button("Calculate"); hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblTinlet0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTinlet,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTinlet1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblPinlet0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtPinlet,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblPinlet1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHinlet0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHinlet,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHinlet1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRair0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRair,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRair1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTwater0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTwater,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTwater1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRwater0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRwater,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRwater1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTexit0,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTexit,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTexit1,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblPexit0,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtPexit,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblPexit1,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH0,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRH,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH1,gbc,2,8,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblQ0,gbc,0,9,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblQ,gbc,1,9,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblQ1,gbc,2,9,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,10,1,1);
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
		double Pw1,RH1,Pws1,W1,mw3,mw1,mw2,W3,Pw3,Pws3,RH3,hw1,hw3,hws2,Q,RH;
		double Tinlet,Texit,Pinlet,RHinlet,Pexit,Rair,Rwater,Twater;
		pf=new PsikrometrikFonksiyonlar();
		su=new SuOzellikleri();
		Pinlet=Double.valueOf(txtPinlet.getText()).doubleValue();
		RHinlet=Double.valueOf(txtRHinlet.getText()).doubleValue()/100;
		Rair=Double.valueOf(txtRair.getText()).doubleValue();
		Tinlet=Double.valueOf(txtTinlet.getText()).doubleValue();
		Twater=Double.valueOf(txtTwater.getText()).doubleValue();
		Rwater=Double.valueOf(txtRwater.getText()).doubleValue();
		Texit=Double.valueOf(txtTexit.getText()).doubleValue();
		Pexit=Double.valueOf(txtPexit.getText()).doubleValue();
		Pws1=su.P(Tinlet);
		RH1=RHinlet;
		Pw1=RH1*Pws1;
		W1=0.622*Pw1/(Pinlet-Pw1);
		mw3=W1*Rair+Rwater;
		W3=mw3/Rair;
		double A=W3/0.622;
		Pw3=Pinlet*A/(1+A);
		Pws3=su.P(Texit);
		RH=Pw3/Pws3*100;
		hw1=su.Hg(Tinlet);
		hw3=su.Hg(Texit);
		hws2=su.Hf(Twater);
		Q=(1.003*(Texit-Tinlet)+W3*hw3-W1*hw1-(W3-W1)*hws2)*Rair;
		lblRH.setText(String.valueOf(df.format(RH)));
		lblQ.setText(String.valueOf(df.format(Q)));
	}
	
}
