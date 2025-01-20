//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import PsikrometrikFonksiyonlar;
public class Psik07 extends java.applet.Applet implements ActionListener{
	TextField txtTcoil,txtXcoil,txtToutair,txtXoutair,txtYpercent,txtP;
	Label lblTcoil0,lblTcoil1,lblXcoil0,lblXcoil1,lblToutair0,lblToutair1,lblP0,lblP1;
	Label lblXoutair0,lblXoutair1,lblYpercent0,lblYpercent1;
	Label lblT30,lblT3,lblT31,lblRH30,lblRH3,lblRH31,lblW31,lblW3,lblW30;
	Button hesapla,close;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
	PsikrometrikFonksiyonlar pf;
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
		lblP0=new Label("Pressure of the Envorinment :",Label.RIGHT);txtP=new TextField(14);lblP1=new Label("kPa",Label.RIGHT);
		lblTcoil0=new Label("Temperature of Cooling Coil Air :",Label.RIGHT);txtTcoil=new TextField(14);lblTcoil1=new Label("�C",Label.LEFT);
		lblXcoil0=new Label("Humidity Percentage of Cooling Coil Air :",Label.RIGHT);txtXcoil=new TextField(14);lblXcoil1=new Label("%",Label.LEFT);
		lblToutair0=new Label("Temperature of Outside Air :",Label.RIGHT);txtToutair=new TextField(14);lblToutair1=new Label("�C",Label.LEFT);
		lblXoutair0=new Label("Humidity Percentage of Outside Air : :",Label.RIGHT);txtXoutair=new TextField(14);lblXoutair1=new Label("%",Label.LEFT);
		lblYpercent0=new Label("Percentage of Outside Air :",Label.RIGHT);txtYpercent=new TextField(14);lblYpercent1=new Label("%",Label.LEFT);
		lblW30=new Label("Final Humidity of Air :",Label.RIGHT);lblW3=new Label("                            ",Label.CENTER);lblW31=new Label("kg/kg dry air",Label.LEFT);                  
		lblRH30=new Label("Final Relative Humidity of Air :",Label.RIGHT);lblRH3=new Label("                            ",Label.CENTER);lblRH31=new Label("%",Label.LEFT);
		lblT30=new Label("Final Temperature of Air :",Label.RIGHT);lblT3=new Label("                            ",Label.CENTER);lblT31=new Label("�C",Label.LEFT);
		hesapla=new Button("Calculate"); hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblTcoil0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTcoil,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTcoil1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblXcoil0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtXcoil,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblXcoil1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblToutair0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtToutair,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblToutair1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblXoutair0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtXoutair,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblXoutair1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblYpercent0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtYpercent,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblYpercent1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblW30,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblW3,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblW31,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblT30,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblT3,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblT31,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH30,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRH3,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH31,gbc,2,8,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,9,1,1);
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
		double T1,RH1,T2,RH2,ma2,ratio,P,T3,RH3;
		double w1=0,w2=0,w3=0,Pws=0,Pw=0;
		pf=new PsikrometrikFonksiyonlar();
		P=Double.valueOf(txtP.getText()).doubleValue()*1000;
		T1=Double.valueOf(txtTcoil.getText()).doubleValue();
		RH1=Double.valueOf(txtXcoil.getText()).doubleValue()/100;
		T2=Double.valueOf(txtToutair.getText()).doubleValue();
		RH2=Double.valueOf(txtXoutair.getText()).doubleValue()/100;
		ma2=Double.valueOf(txtYpercent.getText()).doubleValue()/100;
		ratio=1/ma2;
		Pws=pf.Pws(T1);
		Pw=Pws*RH1;	//Pws (Pa)
		w1=pf.W2(P,Pw);
		Pws=pf.Pws(T2);
		Pw=Pws*RH2;
		w2=pf.W2(P,Pw);
		w3=(ratio*w1+w2)/(1+ratio);		//w3,w2,w2 kg/kg dry air
		double slope=(w2-w1)/(T2-T1);
		T3=(w3-w2)/slope+T2;			//We now draw a line between (T1,w1) and (T2,w2) and find T3 from w3
		Pw=pf.Pw2(P,w3);
		Pws=pf.Pws(T3);
		RH3=Pw/Pws*100;
		lblRH3.setText(String.valueOf(df.format(RH3)));
		lblT3.setText(String.valueOf(df.format(T3)));
		lblW3.setText(String.valueOf(df.format(w3)));
	}
}
//
//
