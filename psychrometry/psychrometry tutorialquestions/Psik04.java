//Developed in 1999 by Gokhan Bingol

import java.awt.*;
import java.applet.*;
import java.text.*;
public class Psik04 extends java.applet.Applet{
	TextField txtP,txtTin,txtRHin,txtRHex,txtTex,txtTmin;
	Label lblP,lblTin,lblRHin,lblRHex,lblTex,lblTmin;
	Label lblP1,lblTin1,lblRHin1,lblRHex1,lblTex1,lblTmin1;
	Label lblTex0,lblTmin0;
	Button hesapla;
	Panel p,gp;
	Color c,co;
	Font f,fo;
	DecimalFormat df=new DecimalFormat("0.####");


	public void init(){
		f=new Font("Arial",Font.PLAIN,11);
		fo=new Font("Arial",Font.ITALIC,13);
		c=new Color(151,185,200);
		co=new Color(155,190,205);
		setBackground(c);
		lblP=new Label("Pressure");txtP=new TextField(14);lblP1=new Label("kPa");lblP1.setAlignment(Label.LEFT); 
		lblTin=new Label("Enterance Temperature"); txtTin=new TextField(14);lblTin1=new Label("�C");lblTin1.setAlignment(Label.LEFT);
		lblRHin=new Label("Enterance Relative Humidity");txtRHin=new TextField(14);lblRHin1=new Label("%");lblRHin1.setAlignment(Label.LEFT);
		lblRHex=new Label("Exit Relative Humidity");txtRHex=new TextField(14);lblRHex1=new Label("%");lblRHex1.setAlignment(Label.LEFT);
		lblTex0=new Label("Exit Temperature");lblTex=new Label("             ");lblTex1=new Label("�C");lblTex1.setAlignment(Label.LEFT);
		lblTmin0=new Label("Minimum Temperature"); lblTmin=new Label("             ");lblTmin1=new Label("�C");lblTmin1.setAlignment(Label.LEFT);
		hesapla=new Button("Calculate");hesapla.setFont(fo);
		//
		lblP.setFont(f);txtP.setFont(f);lblP1.setFont(f);
		lblTin.setFont(f);txtTin.setFont(f);lblTin1.setFont(f);
		lblRHin.setFont(f);txtRHin.setFont(f);lblRHin1.setFont(f);
		lblRHex.setFont(f);txtRHex.setFont(f);lblRHex1.setFont(f);
		lblTex0.setFont(f);lblTex.setFont(f);lblTex.setFont(f);
		lblTmin0.setFont(f);lblTmin.setFont(f);lblTmin1.setFont(f);
		//
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbl);
		//
		gbc.weightx=100;gbc.weighty=100;
		gbc.anchor=GridBagConstraints.EAST;add(lblP,gbc,0,0,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtP,gbc,1,0,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblP1,gbc,2,0,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTin,gbc,0,1,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtTin,gbc,1,1,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTin1,gbc,2,1,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHin,gbc,0,2,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHin,gbc,1,2,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHin1,gbc,2,2,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblRHex,gbc,0,3,1,1);gbc.anchor=GridBagConstraints.CENTER;add(txtRHex,gbc,1,3,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblRHex1,gbc,2,3,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTex0,gbc,0,4,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblTex,gbc,1,4,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTex1,gbc,2,4,1,1);
		gbc.anchor=GridBagConstraints.EAST;add(lblTmin0,gbc,0,5,1,1);gbc.anchor=GridBagConstraints.CENTER;add(lblTmin,gbc,1,5,1,1);gbc.anchor=GridBagConstraints.WEST;add(lblTmin1,gbc,2,5,1,1);
		gbc.anchor=GridBagConstraints.CENTER;add(hesapla,gbc,1,6,1,1);
		//
	}


	public void add(Component c,GridBagConstraints gb,int x,int y,int w,int h){
		gb.gridx=x;
		gb.gridy=y;
		gb.gridwidth=w;
		gb.gridheight=h;
		add(c,gb);
	}


	public boolean action(Event evt,Object obj){
		double Tin,RHin,RHex,Tex,Tmin,Tyt,P;
		double Pws,Pw,W,Pwsyas,Wsyas;
		double AA,Y,X,DD1,DD2,varsubTyas,varSubT,DF1,DF2;
		PsikrometrikFonksiyonlar pf=new PsikrometrikFonksiyonlar();
		if(evt.target.equals(hesapla)){
			P=Double.valueOf(txtP.getText()).doubleValue();		//kPa olarak okunuyor.
			Tin=Double.valueOf(txtTin.getText()).doubleValue();
			RHin=Double.valueOf(txtRHin.getText()).doubleValue();
			RHex=Double.valueOf(txtRHex.getText()).doubleValue();
			Pws=pf.Pws(Tin);
			Pw=RHin/100*Pws;
			W=pf.W2(P*1000,Pw);
			Tyt=pf.Tyas1521(P*1000,W,Tin);
			Pwsyas=pf.Pwsyas(Tyt);
			Wsyas=pf.Wsyas(P*1000,Pwsyas);
			varSubT = Tyt;
			do{
				varSubT = varSubT + 0.1;
				Pws = pf.Pws(varSubT);
				W = pf.W2(P*1000, RHex/100 * Pws);
				varsubTyas = pf.Tyas(Wsyas, W, varSubT);
			}while(!(Math.abs(varsubTyas - Tyt) <1));

			X = varSubT - 0.5; Y = varSubT + 0.5;

			do{
				AA = (Y - X) / 3;
				DD1 = X + AA; DD2 = Y - AA;
				Pws = pf.Pws(DD1);
				W = pf.W2(P*1000, (RHex/100 * Pws));
				varsubTyas = pf.Tyas(Wsyas, W, DD1);
				DF1 = varsubTyas - Tyt;
				
				Pws = pf.Pws(DD2);
				W = pf.W2(P*1000, (RHex/100 * Pws));
				varsubTyas = pf.Tyas(Wsyas, W, DD2);
				DF2 = varsubTyas - Tyt;

				if(Math.abs(DF1) < Math.abs(DF2))
				{
					if(DF1<0){X = DD1; Y = DD1 + AA;}
					if(DF1>0){X = DD1 - AA; Y = DD1;}
					if(DF1==0){varsubTyas = DD2;}
				}
				else
				{
					if(DF2<0){X = DD2; Y = DD2 + AA;}
					if(DF2>0){X = DD2 - AA; Y = DD2;}
					if(DF2==0){varsubTyas = DD1;}
				}
			}while(!((Math.abs(DD1 - DD2) < 0.0000000001)||(Math.abs(varsubTyas - Tyt) < 0.0000000001)));
			
			Tex = DD1;
			
			lblTex.setText(String.valueOf(df.format(Tex)));
			lblTmin.setText(String.valueOf(df.format(Tyt)));
		}
		return true;
	}
}
			