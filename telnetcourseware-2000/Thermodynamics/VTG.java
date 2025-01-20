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
    String varNot="";
    students=ts.getStudent ();
    DBC dbc=new DBC();
    dbc.connect ();
    Questions questions[]=ts.getQuestions();
    String QuestionTableName="";
    int questionlength=ts.ques.length;
    double totalgrade=0;
    for(int i=0;i<questionlength;i++){
      Class cl=questions[i].getClass ();
      QuestionTableName=cl.getName ();
      varNot=dbc.getSelectedCell(students.getUsername(),"NOTU",QuestionTableName);
      if(!varNot.equals("")) totalgrade=totalgrade+Double.valueOf(varNot).doubleValue ();
    }
    try{
      out.println("Toplam notunuz:"+String.valueOf(totalgrade));
      for(int i=0;i<questionlength;i++){
        Class cl=questions[i].getClass ();
        QuestionTableName=cl.getName ();
        String not=dbc.getSelectedCell(students.getUsername (),"NOTU",QuestionTableName);
        out.println(String.valueOf(i+1)+".sorudan  "+not+"  puan aldiniz");
      }
    }catch(Exception e){}
    dbc=null;
    System.gc (); //Call garbage collector
  }
}
