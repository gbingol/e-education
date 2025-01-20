//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;
public class Psik11 extends java.applet.Applet implements ActionListener{
	Label lblP0,lblP1,lblTinlet0,lblTinlet1,lblTsaturation0,lblTsaturation1;
	Label lblRH0,lblRH1,lblRH,lblW0,lblW,lblW1;
	TextField txtP,txtTinlet,txtTsaturation;
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
		lblTinlet0=new Label("Enterance Temperature of Mixture :",Label.RIGHT);txtTinlet=new TextField(12);lblTinlet1=new Label("�C",Label.LEFT);		
		lblTsaturation0=new Label("Saturation Temperature :",Label.RIGHT);txtTsaturation=new TextField(12);lblTsaturation1=new Label("�C",Label.LEFT);
		lblRH0=new Label("Relative Humidity of Mixture :",Label.RIGHT);lblRH=new Label("                    ",Label.CENTER);lblRH1=new Label("%",Label.LEFT);
		lblW0=new Label("Absolute Humidity of Mixture :",Label.RIGHT);lblW=new Label("                    ",Label.CENTER);lblW1=new Label("kg/kg dry air",Label.LEFT);
		hesapla=new Button("Calculate"); hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTinlet0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTinlet,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTinlet1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTsaturation0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTsaturation,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTsaturation1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRH0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRH,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRH1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblW0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblW,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblW1,gbc,2,4,1,1);
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
		double P,RHf,Wf,RH2,W2,h,hw2,hws2,hw1,W1,Pw1,Pws1,Pw2,RH1=0,Tinlet,Tsaturation;
		pf=new PsikrometrikFonksiyonlar();
		su=new SuOzellikleri();
		P=Double.valueOf(txtP.getText()).doubleValue();	
		Tinlet=Double.valueOf(txtTinlet.getText()).doubleValue();
		Tsaturation=Double.valueOf(txtTsaturation.getText()).doubleValue();
		RH2=1;
		Pw2=su.P(Tsaturation);
		W2=0.622*RH2*Pw2/(P-RH2*Pw2);
		hw1=su.Hg(Tinlet);
		hws2=su.Hf(Tsaturation);
		h=su.Hg(Tsaturation)-su.Hf(Tsaturation);
		W1=(1.003*(Tsaturation-Tinlet)+W2*h)/(hw1-hws2);
		double A=W1/0.622;
		Pw1=(A*P)/(1+A);
		Pws1=su.P(Tinlet);
		RH1=Pw1/Pws1*100;
		lblW.setText(String.valueOf(df.format(W1)));
		lblRH.setText(String.valueOf(df.format(RH1)));
	}
	/*public static void main(String arg[]){
		Frame f=new Psik11();
		f.show();
	} */
}
