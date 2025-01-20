//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.applet.*;
import java.text.*;
public class Psik02 extends java.applet.Applet
{
	TextField txtTin,txtRHin,txtTex,txtRHex,txtTheating,txtPtop,txtVhava;
	Label lblTin,lblRHin,lblRHex,lblTex,lblTheating,lblPtop,lblA,lblAs,lblB,lblBs,lblVhava;
	Label lblTin1,lblRHin1,lblRHex1,lblTex1,lblTheating1,lblPtop1,lblA1,lblB1,lblVhava1;
	Button hesapla;
	Panel p,gp;
	Color c,co;
	Font f;
	DecimalFormat df=new DecimalFormat("0.####");


	public void init()
	{
		p=new Panel();
		p.setLayout(new BorderLayout());
		gp=new Panel();
		gp.setLayout(new GridLayout(9,3));
		GridBagLayout gbl=new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc=new GridBagConstraints();

		c=new Color(150,190,201);
		co=new Color(140,180,191);
		f=new Font("Arial",Font.PLAIN,11);
		setBackground(c);
		//
		lblTin=new Label("Enterance Temperature");
		txtTin=new TextField(14);
		lblTin1=new Label("�C");

		lblRHin=new Label("Enterance Relative Humidity");
		txtRHin=new TextField(14);
		lblRHin1=new Label("%");

		lblTex=new Label("Exit Temperature");
		txtTex=new TextField(14);
		lblTex1=new Label("�C");

		lblRHex=new Label("Exit Relative Humidity");
		txtRHex=new TextField(14);
		lblRHex1=new Label("%");

		lblTheating=new Label("Heating Temperature");
		txtTheating=new TextField(14);
		lblTheating1=new Label("�C");

		lblPtop=new Label("Total Pressure");
		txtPtop=new TextField(14);
		lblPtop1=new Label("kPa");

		lblA=new Label("A");
		lblAs=new Label("               ");
		lblA1=new Label("kJ/min");

		lblB=new Label("B");
		lblBs=new Label("               ");
		lblB1=new Label("kg/min");

		lblVhava=new Label("Volumetric Rate of Air");
		txtVhava=new TextField(14);
		lblVhava1=new Label("m3/min");

		hesapla=new Button("Calculate");
		hesapla.setFont(new Font("Arial",Font.ITALIC,14));
		
		
		lblTin.setFont(f);
		txtTin.setFont(f);
		lblTin1.setFont(f);
		lblTex.setFont(f);
		txtTex.setFont(f);
		lblTex1.setFont(f);
		lblRHin.setFont(f);
		txtRHin.setFont(f);
		lblRHin1.setFont(f);
		lblRHex.setFont(f);
		txtRHex.setFont(f);
		lblRHex1.setFont(f);
		lblTheating.setFont(f);
		txtTheating.setFont(f);
		lblTheating1.setFont(f);
		lblPtop.setFont(f);
		txtPtop.setFont(f);
		lblPtop1.setFont(f);
		lblVhava.setFont(f);
		txtVhava.setFont(f);
		lblVhava1.setFont(f);

		lblA.setFont(f);
		lblAs.setFont(f);
		lblA1.setFont(f);

		lblB.setFont(f);
		lblBs.setFont(f);
		lblB1.setFont(f);

		lblAs.setBackground(co);
		lblBs.setBackground(co);
		
		
		gbc.weightx=100;gbc.weighty=100;gbc.anchor=GridBagConstraints.EAST;add(lblTin,gbc,0,0,1,1);add(txtTin,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTin1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHin,gbc,0,1,1,1);add(txtRHin,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHin1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTex,gbc,0,2,1,1);add(txtTex,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTex1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHex,gbc,0,3,1,1);add(txtRHex,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHex1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTheating,gbc,0,4,1,1);add(txtTheating,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTheating1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblPtop,gbc,0,5,1,1);add(txtPtop,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblPtop1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblVhava,gbc,0,6,1,1);add(txtVhava,gbc,1,6,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblVhava1,gbc,2,6,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblA,gbc,0,7,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblAs,gbc,1,7,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblA1,gbc,2,7,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblB,gbc,0,8,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblBs,gbc,1,8,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblB1,gbc,2,8,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,9,1,1);
		
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
			double Pv1,RHin,Tin,Tex,RHex,Theat,Ptop,Pdoyma,Pa1,v1,ma,w1,h1,h2,Q,Vhava,w3,msu;
			Tin=Double.valueOf(txtTin.getText()).doubleValue();
			RHin=Double.valueOf(txtRHin.getText()).doubleValue();
			
			Tex=Double.valueOf(txtTex.getText()).doubleValue();
			RHex=Double.valueOf(txtRHex.getText()).doubleValue();
			
			Theat=Double.valueOf(txtTheating.getText()).doubleValue();
			Ptop=Double.valueOf(txtPtop.getText()).doubleValue();
			Vhava=Double.valueOf(txtVhava.getText()).doubleValue();
			
			Pdoyma=su.P(Tin);
			Pv1=RHin/100*Pdoyma;
			Pa1=Ptop-Pv1;
			v1=0.287*(Tin+273.15)/Pa1;
			ma=Vhava/v1;
			w1=0.622*Pv1/(Ptop-Pv1);
			h1=1.005*Tin+w1*su.Hg(Tin);
			
			h2=1.005*Theat+w1*su.Hg(Theat);
			
			Q=ma*(h2-h1);
			w3=0.622*RHex/100*su.P(Tex)/(Ptop-su.P(Tex));
			msu=ma*(w3-w1);
			
			lblAs.setText(String.valueOf(df.format(Q)));
			lblBs.setText(String.valueOf(df.format(msu)));
		}
		return true;
	}
}


