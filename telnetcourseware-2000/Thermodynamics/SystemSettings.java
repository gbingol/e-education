import java.io.*;
import java.util.*;
public class SystemSettings
{
  static String s[]=new String[10];
  static String SettingCommand[]=new String[10];
  static String SettingDefinition[]=new String[10];
  static int numberofcommands;

  static public void readFile(String Filename){
    int i=0;
    String str="";
    try
    {
      BufferedReader br=new BufferedReader(new FileReader(Filename));
      while((str=br.readLine())!=null){
        s[i]=str.trim ();
        StringTokenizer st=new StringTokenizer(s[i],"=");
        while(st.hasMoreTokens ()){
          SettingCommand[i]=st.nextToken ();
          SettingDefinition[i]=st.nextToken();
        }
        i++;
        numberofcommands=i;
      }
      br.close();
    }catch(Exception e){System.out.println(e);}
  }
  //
  //
  static public String getCommandParameter(String command){
    String s="";
    for(int i=0;i<SettingCommand.length;i++){
      if(SettingCommand[i].equals(command)){s=SettingDefinition[i];break;}
    }
    return s;
  }
}

