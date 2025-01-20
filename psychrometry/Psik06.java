//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.applet.*;
import java.text.*;
import Fluids.SuOzellikleri;
public class Psik06 extends java.applet.Applet{
	TextField txtV,txtTsuin,txtTsusog,txtP,txtTin,txtRHin,txtTex;
	Label lblV0,lblV1,lblTsuin0,lblTsuin1,lblTsusog0,lblTsusog1,lblP0,lblP1,lblTin0,lblTin1,lblRHin0,lblRHin1,lblTex0,lblTex1;
	Label lblVhava,lblVhava0,lblVhava1,lblVsu,lblVsu0,lblVsu1;
	Button hesapla;
	Panel p,gp;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");
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
		lblV0=new Label("Rate of Cooling Water",Label.RIGHT);txtV=new TextField(12);lblV1=new Label("kg/s",Label.LEFT);
		lblTsuin0=new Label("Temperature of Cooling Water",Label.RIGHT);txtTsuin=new TextField(12);lblTsuin1=new Label("�C",Label.LEFT);
		lblTsusog0=new Label("Cooling Temperature",Label.RIGHT);txtTsusog=new TextField(12);lblTsusog1=new Label("�C",Label.LEFT);
		lblP0=new Label("Pressure of Cooling Air",Label.RIGHT);txtP=new TextField(12);lblP1=new Label("kPa",Label.LEFT);
		lblTin0=new Label("Temperature of Cooling Air",Label.RIGHT);txtTin=new TextField(12);lblTin1=new Label("�C",Label.LEFT);
		lblRHin0=new Label("Relative Humidity of Cooling Air",Label.RIGHT);txtRHin=new TextField(12);lblRHin1=new Label("%",Label.LEFT);
		lblTex0=new Label("Exit Temperature of Cooling Air",Label.RIGHT);txtTex=new TextField(12);lblTex1=new Label("�C",Label.LEFT);
		lblVhava0=new Label("Rate of Air Entering Cooling Tower",Label.RIGHT);lblVhava=new Label("              ");lblVhava1=new Label("m3/s",Label.LEFT);
		lblVsu0=new Label("Rate of Cool Water",Label.RIGHT);lblVsu=new Label("              ");lblVsu1=new Label("kg/s",Label.LEFT);
		hesapla=new Button("Calculate");hesapla.setFont(fo);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblV0,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtV,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblV1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTsuin0,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTsuin,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTsuin1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTsusog0,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTsusog,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTsusog1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblP0,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTin0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTin,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTin1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHin0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHin,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHin1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTex0,gbc,0,6,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTex,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTex1,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblVhava0,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblVhava,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblVhava1,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblVsu0,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblVsu,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblVsu1,gbc,2,8,1,1);
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
	public boolean action(Event evt,Object obj){
		if(evt.target.equals(hesapla)){
			SuOzellikleri su=new SuOzellikleri();
			PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
			double h1,w1,v1,h2,w2,h3,h4,ma,V1;
			double V,Tsuin,Tsusog,P,Tin,RHin,Tex,Vhava,Vsu;
			double Pws,Pw;
			Tsuin=Double.valueOf(txtTsuin.getText()).doubleValue();
			V=Double.valueOf(txtV.getText()).doubleValue();
			Tsusog=Double.valueOf(txtTsusog.getText()).doubleValue();
			P=Double.valueOf(txtP.getText()).doubleValue();
			Tin=Double.valueOf(txtTin.getText()).doubleValue();
			RHin=Double.valueOf(txtRHin.getText()).doubleValue();
			Tex=Double.valueOf(txtTex.getText()).doubleValue();
			h3=su.Hf(Tsuin);
			h4=su.Hf(Tsusog);
			Pws=pf.Pws(Tin);
			Pw=Pws*RHin/100;
			w1=pf.W2(P*1000,Pw);
			h1=pf.H(Tin,w1);
			v1=pf.v(Tin,P*1000,w1);
			Pws=pf.Pws(Tex);
			Pw=Pws;
			w2=pf.W2(P*1000,Pw);
			h2=pf.H(Tex,w2);
			ma=V*(h3-h4)/((h2-h1)-(w2-w1)*h4);
			Vhava=ma*v1;
			Vsu=ma*(w2-w1);
			lblVhava.setText(String.valueOf(df.format(Vhava)));
			lblVsu.setText(String.valueOf(df.format(Vsu)));
		}
		return true;
	}
}
