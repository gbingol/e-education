import java.io.*;
import java.util.Date;
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

  public void execute(String varusername){
    Students stu=null;
    Students[] students=ThreadManager.getConnectedStudents ();
    int varlength=students.length;
    for(int i=0;i<varlength;i++){
      if(varusername.equals(students[i].getUsername ().trim ())){
        stu=students[i];
        break;
      }
    }
    try{
      Date date=new Date();
      long timenow=date.getTime();
      long elapsedtime=(timenow-stu.giriszamani)/1000; //Convert to seconds
      if(elapsedtime>3600) {
        ThreadManager.removeStudent(stu);
      }else{
        out.println("Arkadasinizi askidan kurtarmak icin minimum 1 saat gecmesi gerekmektedir.");
      }
    }catch(Exception e){System.out.println("MHREMOVE ERROR:"+e);}
  }
}


