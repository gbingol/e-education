//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
import PsikrometrikFonksiyonlar;
public class Psik10 extends java.applet.Applet implements ActionListener{
	Label lblRair0,lblRair1,lblFan0,lblFan1,lblP0,lblP1,lblTwater0,lblTwater1,lblTinletair0,lblTinletair1;
	Label lblRHinletair0,lblRHinletair1,lblTleavingair0,lblTleavingair1,lblRwater0,lblRwater1;
	Label lblTwaterfinal0,lblTwaterfinal,lblTwaterfinal1,lblRmakeupwater0,lblRmakeupwater,lblRmakeupwater1;
	TextField txtRwater,txtRair,txtTinletair,txtRHinletair,txtTleavingair,txtFan,txtP,txtTwater;
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
		lblRwater0=new Label("Rate of Water :",Label.RIGHT);txtRwater=new TextField(12);lblRwater1=new Label("lt/s",Label.LEFT);
		lblTwater0=new Label("Temperature of Water :",Label.RIGHT);txtTwater=new TextField(12);lblTwater1=new Label("�C",Label.LEFT);
		lblRair0=new Label("Rate of Air Flow :",Label.RIGHT);txtRair=new TextField(12);lblRair1=new Label("m3/s",Label.LEFT);
		lblTinletair0=new Label("Temperature of Air :",Label.RIGHT);txtTinletair=new TextField(12);lblTinletair1=new Label("�C",Label.LEFT);
		lblRHinletair0=new Label("Relative Humidity of Air :",Label.RIGHT);txtRHinletair=new TextField(12);lblRHinletair1=new Label("%",Label.LEFT);
		lblTleavingair0=new Label("Temperature of Leaving Air :",Label.RIGHT);txtTleavingair=new TextField(12);lblTleavingair1=new Label("�C",Label.LEFT);		
		lblFan0=new Label("Fan Input :",Label.RIGHT);txtFan=new TextField(12);lblFan1=new Label("kW",Label.LEFT);
		lblTwaterfinal0=new Label("Final Temperature of Water :",Label.RIGHT);lblTwaterfinal=new Label("                    ",Label.CENTER);lblTwaterfinal1=new Label("�C",Label.LEFT);
		lblRmakeupwater0=new Label("Amount of Cooling Make-up Water :",Label.RIGHT);lblRmakeupwater=new Label("                    ",Label.CENTER);lblRmakeupwater1=new Label("kg/s",Label.LEFT);
		hesapla=new Button("Calculate"); hesapla.addActionListener(this);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRwater0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRwater,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRwater1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTwater0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTwater,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTwater1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRair0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRair,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRair1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblFan0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtFan,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblFan1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTinletair0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTinletair,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTinletair1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHinletair0,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHinletair,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHinletair1,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTleavingair0,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTleavingair,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTleavingair1,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTwaterfinal0,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblTwaterfinal,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTwaterfinal1,gbc,2,8,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRmakeupwater0,gbc,0,9,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblRmakeupwater,gbc,1,9,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRmakeupwater1,gbc,2,9,1,1);
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
		double RH,Ps,Pg,Ps1,Pa1,ma,ms1,Ps2,w2,ms2,mw1,mw2,W,hw1,ha1,hs1,hs2,ha2,hf,hw2,Rmakeupwater,Tfinal;
		double Tinletair,RHinletair,Tleavingair,Rwater,Twater,Rair,Wfan,P;
		pf=new PsikrometrikFonksiyonlar();
		su=new SuOzellikleri();
		P=Double.valueOf(txtP.getText()).doubleValue()/100;	//kPa Bar'a �evriliyor
		Tinletair=Double.valueOf(txtTinletair.getText()).doubleValue();
		RHinletair=Double.valueOf(txtRHinletair.getText()).doubleValue()/100;
		Tleavingair=Double.valueOf(txtTleavingair.getText()).doubleValue();
		Rwater=Double.valueOf(txtRwater.getText()).doubleValue();
		Rair=Double.valueOf(txtRair.getText()).doubleValue();
		Twater=Double.valueOf(txtTwater.getText()).doubleValue();
		Wfan=Double.valueOf(txtFan.getText()).doubleValue();
		Pg=su.P(Tinletair)/100;	//kPa Bar'a �evriliyor
		Ps1=RHinletair*Pg;
		Pa1=P-Ps1;
		ma=(100000*Pa1*Rair)/(1000*0.287*(Tinletair+273.15));
		ms1=(100000*Ps1*Rair)/(1000*0.4618*(Tinletair+273.15));
		Pg=su.P(Tleavingair)/100;	//kPa Bar'a �evriliyor
		Ps2=Pg;
		w2=0.622*Ps2/(P-Ps2);
		ms2=ma*w2;
		Rmakeupwater=ms2-ms1;
		mw1=Rwater;
		mw2=mw1-Rmakeupwater;
		hw1=su.Hf(Twater);
		ha1=1.005*Tinletair;
		hs1=2519.4+1.86*Tinletair;
		hs2=su.Hg(Tleavingair);
		ha2=1.005*Tleavingair;
		hf=(Wfan+mw1*hw1+ma*ha1+ms1*hs1-ma*ha2-ms2*hs2)/mw2;
		double XX=-5,YY=0,hxx=0,hyy=0,slope;
		do{
			XX=XX+5;
			hxx=su.Hf(XX);
		}while(!((hxx-hf)>0));
		YY=XX-5;
		hyy=su.Hf(YY);
		slope=(hxx-hyy)/(XX-YY);
		Tfinal=(hxx-hf)/slope+XX;
		lblTwaterfinal.setText(String.valueOf(df.format(Tfinal)));
		lblRmakeupwater.setText(String.valueOf(df.format(Rmakeupwater)));
		}
	}
