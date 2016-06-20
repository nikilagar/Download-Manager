/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikhil
 */
public class Merger {
    int noofpackets;
    String target;
    public Merger(int noofpackets,String target){
        this.noofpackets=noofpackets;
        this.target=target;
    }
    
    int timer=0,flagMerfinished=0;
class tim extends Thread{
public void run(){
while(flagMerfinished==0){
    try {
        sleep(1000);
    } catch (InterruptedException ex) {
        Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    timer++;
}
System.out.print("Merge time:"+timer);
}
    }
    public void exec() {
        new tim().start();
        System.out.print("yes");
        File out=new File(target);
        FileOutputStream fileos;
        try {
            fileos = new FileOutputStream(out,true);
        
    for(int i=1;i<=noofpackets;i++)
    {File dir=new File(target+i);
    int c;
    byte buff[]=new byte[50*1024*1024];
    if(!dir.exists())
        continue;
    InputStream in=new FileInputStream(dir);
    
    int end= (int) (dir.length()-1); 
    System.out.print(end+"siz");
          while((c=in.read(buff))!=-1)
          fileos.write(buff,0,c);   
           dir.delete();
    }
    fileos.close();
    flagMerfinished=1;
    
    } catch (FileNotFoundException ex) {
            Logger.getLogger(Merger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Merger.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
