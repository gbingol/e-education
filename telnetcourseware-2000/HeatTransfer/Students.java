import java.io.*;
import java.util.*;
public class Students{
    private String username,password;
    private Date d;
    public long giriszamani;
    public MHTable mhtable=new MHTable(6,24);
    public Students() {
    }
    public Students(String uname,String pword){
      username=uname;password=pword;
      d=new Date();
      giriszamani=d.getTime ();
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
    public MHTable getMHTable(){
      return this.mhtable;
    }
    //
}