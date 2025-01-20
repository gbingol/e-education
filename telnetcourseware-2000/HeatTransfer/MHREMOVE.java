import java.io.*;
public class MHREMOVE extends MHCommand{
  //
  private ThreadSocket ts;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  //
  public MHREMOVE(){};
  public MHREMOVE(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String stu){
    Students[] student=ts.getStudentArray();
    int varlength=student.length;
    try{
      for(int i=0;i<varlength;i++){
          if(stu.equals(student[i].getUsername().toUpperCase())) {
            ThreadManager.removeStudent(student[i]);
            break;
          }
      }
    }catch(Exception e){System.out.println("MHREMOVE ERROR:"+e);}
  }
}


