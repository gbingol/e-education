import java.io.*;
import java.util.*;
public class LS extends MHCommand{
  //
  public int time;
  private ThreadSocket ts;
  private Date date;
  private BufferedReader in;PrintWriter out;DataOutputStream dataout;
  //
  public LS(){};
  public LS(BufferedReader in1,PrintWriter out1,DataOutputStream dataout1,ThreadSocket ts1){
    in=in1;out=out1;dataout=dataout1;ts=ts1;
  }
  //

  public void execute(String stu){
    Students[] student=ThreadManager.getConnectedStudents ();
    date=new Date();
    int varlength=student.length;
    for(int i=0;i<varlength;i++){
        long temp=(date.getTime ()- student[i].giriszamani)/1000;
        if(temp<60) out.println(student[i].getUsername()+" kullanicisi "+String.valueOf(temp)+ " saniyedir sisteme baglidir" );
        if(temp>=60) out.println(student[i].getUsername()+" kullanicisi "+String.valueOf(temp/60)+ " dakikadir sisteme baglidir" );
    }
  }
}
