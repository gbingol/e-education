import java.util.*;
public class ThreadManager  {
// Threads register themselves here thus can not make simultaneous multiple connections
  public static ThreadSocket thread;
  public static boolean studentconnected=false;
  public static Students students;
  public static Hashtable connectedStudents=new Hashtable();
  public ThreadManager(ThreadSocket th) {
    thread=th;
  }
  //
  //
  public static void addStudent(ThreadSocket th, Students s){
    students=s;   thread=th;
    if((Thread)connectedStudents.get(students.getUsername())==null){
      connectedStudents.put(students.getUsername(),thread);
      studentconnected=false;
    }
    else studentconnected=true;
  }
  //
  public static void removeStudent(Students s){
    students=s;
    thread=(ThreadSocket)connectedStudents.remove(students.getUsername());
    studentconnected=false;
  }
  //
  public static int getNumberofConnections(){
    return connectedStudents.size ();
  }
  //
  public static Students[] getConnectedStudents(){
  // ls komutunda kullanılıyor
    Enumeration enum;
    Students[] st=new Students[200];
    int arraylength=0;
    int i=0;
    enum=connectedStudents.elements ();
    while(enum.hasMoreElements ()){
      ThreadSocket temp=(ThreadSocket)enum.nextElement ();
      st[i]=(Students)temp.getStudent ();
      i++;
    }
    for(int k=0;i<st.length;k++){
      if(st[k]==null) {arraylength=k;break;}
    }
    Students stu[]=new Students[arraylength];
    System.arraycopy(st,0,stu,0,arraylength);
    return stu;
  }
  //
}

