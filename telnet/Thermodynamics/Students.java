import java.io.*;
import java.util.*;
public class Students{
    private String username,password;
    private Date d;
    public DBC dbc;
    public long giriszamani;
    public Students() {
    }
    public Students(String uname,String pword){
      username=uname;password=pword;
      d=new Date();
      dbc=new DBC();
      giriszamani=d.getTime ();
      dbc.connect ();
    }
    public void setUsername(String s){
      username=s;
    }
    public String getUsername(){
      return username;
    }
    public void setPassword(String s){
      password=s;
    }
    public String getPassword(){
      return password;
    }
    public DBC getDBC(){
      return dbc;
    }
    public void closeConnectionWithDatabase(){
      dbc.close ();
    }

}