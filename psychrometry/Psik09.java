//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;

public class Psik09 extends java.applet.Applet implements ActionListener{
	Label lblTdbair0,lblTdbair1,lblRHair0,lblRHair1,lblRair0,lblRair1,lblFan0,lblFan1,lblP0,lblP1;
	Label lblRH0,lblRH,lblRH1,lblT0,lblT,lblT1;
	TextField txtTdbair,txtRHair,txtRair,txtFan,txtP;
	Button hesapla;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
	PsikrometrikFonksiyonlar pf;
	SuOzellikleri su;
	public void init(){
		/*addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});*/
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
		lblP0=new Label("Total Pressure :",Label.RIGHT);txtP=new TextField(12);lblP1=new Label("kPa",Label.LEFT);
		lblTdbair0=new Label("Dry-bulb Temperature of Air :",Label.RIGHT);txtTdbair=new TextField(12);lblTdbair1=new Label("�C",Label.LEFT);
		lblRHair0=new Label("Relative Humidity of Air :",Label.RIGHT);txtRHair=new TextField(12);lblRHair1=new Label("%",Label.LEFT);
		lblRair0=new Label("Rate of Air Flow :",Label.RIGHT);txtRair=new TextField(12);lblRair1=new Label("m3/s",Label.LEFT);
		lblFan0=new Label("Fan Input :",Label.RIGHT);txtFan=new TextField(12);lblFan1=new Label("kW",Label.LEFT);
		lblRH0=new Label("Relative Humidity of Air Leaving the Heater :",Label.RIGHT);lblRH=new Label("                    ",Label.CENTER);lblRH1=new Label("%",Label.LEFT);
		lblT0=new Label("Temperature of Air Leaving the Heater :",Label.RIGHT);lblT=new Label("                    ",Label.CENTER);lblT1=new Label("�C",Label.LEFT);
		hesapla=new Button("Calculate"); hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTdbair0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTdbair,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTdbair1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHair0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHair,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHair1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRair0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRair,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRair1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblFan0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtFan,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblFan1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRH,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblT0,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblT,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblT1,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,7,1,1);
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
		double P,Ps,Pa,Pg,ma,ms3,T3,RH3,Tdb,Pfan,RHair,Rair;
		pf=new PsikrometrikFonksiyonlar();
		su=new SuOzellikleri();
		P=Double.valueOf(txtP.getText()).doubleValue()/100;	//kPa Bar'a �evriliyor
		Tdb=Double.valueOf(txtTdbair.getText()).doubleValue();		
		RHair=Double.valueOf(txtRHair.getText()).doubleValue()/100;
		Pfan=Double.valueOf(txtFan.getText()).doubleValue();
		Rair=Double.valueOf(txtRair.getText()).doubleValue();
		Pg=su.P(Tdb)/100;
		Ps=RHair*Pg;
		Pa=P-Ps;
		ma=(Pa*100000*Rair)/(1000*0.287*(Tdb+273.15));
		ms3=(Ps*100000*Rair)/(1000*0.4618*(Tdb+273.15));
		T3=Tdb-Pfan/(ma*1.005+1.86*ms3);
		Pg=su.P(T3)/100;
		RH3=Ps/Pg*100;
		lblRH.setText(String.valueOf(df.format(RH3)));
		lblT.setText(String.valueOf(df.format(T3)));

	}
	/*public static void main(String arg[]){
		Frame f=new Psik08();
		f.show();
	}*/
}
