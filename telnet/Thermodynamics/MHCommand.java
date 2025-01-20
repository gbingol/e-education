import java.util.*;
import java.io.*;
//
public abstract class MHCommand {
  protected Students students;
  //
  public MHCommand(){}

  //
  //
  public static String[] parseCommand(String varcommand){
    //
    String[] commands=new String[20];
    int commandlength=varcommand.length();   //Komut satýrýnýn uzunluðu
    char charString[]=new char[commandlength];
    charString=varcommand.toCharArray ();
    int command_no=0,begin=0,end=0,i=0;
    int j=0;
    for(int k=0;k<commandlength;k++){
      if(Character.isSpace (varcommand.charAt(k))){
        end=k;
        commands[j]=varcommand.substring(begin,end);
        j++;
        begin=k;
      }
      if(k==commandlength-1)commands[j]=varcommand.substring(begin,commandlength);
    }
    j=0;
    String realcommands[]=new String[10];
    for(int k=0;k<commands.length;k++){
      boolean flag2=commands[k]==null;
      if(flag2) break;
      boolean flag1=(commands[k].equals(" "));
      boolean flag=flag1||flag2;
      if(!(flag)){
        realcommands[j]=commands[k].trim ();
        j++;
      }
    }
    String cmd[]=new String[j];
    System.arraycopy(realcommands,0,cmd,0,j);
    return cmd;
  }
  //
  //
  public abstract void execute(String str);
}