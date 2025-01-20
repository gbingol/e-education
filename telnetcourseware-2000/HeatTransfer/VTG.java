import java.io.*;
public class VTG extends MHCommand{
  //
  private ThreadSocket ts;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  //
  public VTG(){};
  public VTG(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String s){
    students=ts.getStudent ();
    mhtable=students.getMHTable ();
    final int  DEFAULT_GRADE_CELL=22;
    double totalgrade=0;
    if(!mhtable.getCellValue(1,DEFAULT_GRADE_CELL).equals("")) totalgrade=totalgrade+Double.valueOf(mhtable.getCellValue(1,DEFAULT_GRADE_CELL)).doubleValue ();
    if(!mhtable.getCellValue(2,DEFAULT_GRADE_CELL).equals("")) totalgrade=totalgrade+Double.valueOf(mhtable.getCellValue(2,DEFAULT_GRADE_CELL)).doubleValue ();
    if(!mhtable.getCellValue(3,DEFAULT_GRADE_CELL).equals("")) totalgrade=totalgrade+Double.valueOf(mhtable.getCellValue(3,DEFAULT_GRADE_CELL)).doubleValue ();
    if(!mhtable.getCellValue(4,DEFAULT_GRADE_CELL).equals("")) totalgrade=totalgrade+Double.valueOf(mhtable.getCellValue(4,DEFAULT_GRADE_CELL)).doubleValue ();
    if(!mhtable.getCellValue(5,DEFAULT_GRADE_CELL).equals("")) totalgrade=totalgrade+Double.valueOf(mhtable.getCellValue(5,DEFAULT_GRADE_CELL)).doubleValue ();
    try{
      out.println("Toplam notunuz:"+String.valueOf(totalgrade));
      out.println("1.sorudan  "+ mhtable.getCellValue(1,DEFAULT_GRADE_CELL)+"  puan aldiniz");
      out.println("2.sorudan  "+ mhtable.getCellValue(2,DEFAULT_GRADE_CELL)+"  puan aldiniz");
      out.println("3.sorudan  "+ mhtable.getCellValue(3,DEFAULT_GRADE_CELL)+"  puan aldiniz");
      out.println("4.sorudan  "+ mhtable.getCellValue(4,DEFAULT_GRADE_CELL)+"  puan aldiniz");
      out.println("5.sorudan  "+ mhtable.getCellValue(5,DEFAULT_GRADE_CELL)+"  puan aldiniz");
      mhtable.setCellValue(0,6,String.valueOf(totalgrade));
      mhtable.setCellValue(0,6,"Kullanicinin toplam notu");
    }catch(Exception e){}
  }
}
