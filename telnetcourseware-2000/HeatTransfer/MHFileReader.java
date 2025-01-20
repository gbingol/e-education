import java.io.*;
import java.util.*;
public class MHFileReader{
  static String s[]=new String[100];
  static String username[]=new String[100];
  static String password[]=new String[100];
  static int numberofstudents;
  static public void readFile(String Filename){
    int i=0;
    String str="";
    try{
      BufferedReader br=new BufferedReader(new FileReader(Filename));
      while((str=br.readLine())!=null){
        s[i]=str.trim ();
        StringTokenizer st=new StringTokenizer(s[i],",");
        while(st.hasMoreTokens ()){
          username[i]=st.nextToken ();
          password[i]=st.nextToken();
        }
        i++;
      }
      numberofstudents=i;
      br.close();
    }catch(Exception e){System.out.println(e);}
  }
  //
  static public int getNumberOfStudents(){
    return numberofstudents;
  }
  static public String[] getUsername(){
    String s[]=new String[numberofstudents];
    System.arraycopy(username,0,s,0,numberofstudents);
    return s;
  }
  //
  static public String[] getPassword(){
    String p[]=new String[numberofstudents];
    System.arraycopy(password,0,p,0,numberofstudents);
    return p;
  }
  //
  /*public static void main(String arg[]){
    MHFileReader mhf=new MHFileReader();
    mhf.readFile("Pass.txt");
  }*/
  //
}

