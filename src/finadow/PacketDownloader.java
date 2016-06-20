/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finadow;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikhil
 */
public class PacketDownloader implements Runnable{
long packetsize,filesize,totaldowntillnow=0,tot=0,speed=0;
int noofpackets,finished=0,noofpackdownloaded=0,timer=0,flagforpause=0,speedlimit=52428800;
URL url;

String target;
public void set(int speedlimit){
    if(noofpackets==0)
        this.speedlimit=speedlimit;
    else this.speedlimit=speedlimit/(noofpackets);
    
}
class tim extends Thread{
public void run(){
while(noofpackdownloaded<=noofpackets){
    try {
        sleep(500);
        speed=(totaldowntillnow-tot)/1000;
        tot=totaldowntillnow;
        sleep(500);
    } catch (InterruptedException ex) {
        Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    timer++;
    
     speed=(totaldowntillnow-tot)/1000;
     tot=totaldowntillnow;
}

}

}
class cr extends Thread{
int packname;
public cr(int y){
packname=y;
}    
public void run(){
    File f;
    if(packname==0)
     f=new File(target);
    else
     f=new File(target+packname);   
    try {
            HttpURLConnection ur=(HttpURLConnection)url.openConnection();
         // System.out.print(ur.getContentLength());
            int w=0;
            long already=f.length();
            totaldowntillnow+=already;
	 if(packetsize*(packname+1)>filesize)
         {  
             ur.setRequestProperty("Range", "bytes="+ (packname*packetsize+already) + "-"+(filesize-1));
             if((packname*packetsize+already)>=filesize)
             {
                 noofpackdownloaded++;
                 return;
             }
         }
         else
         {   ur.setRequestProperty("Range", "bytes="+ (packname*packetsize+already) + "-"+(packetsize*(packname+1)-1));
            if((packname*packetsize+already)>=(packetsize*(packname+1)))
             {
                 noofpackdownloaded++;
                 return;
             }
         }
            InputStream b=ur.getInputStream();
            FileOutputStream os=new FileOutputStream(f,true);
           int c=0;
           byte buff[]=new byte[50*1024*1024];
           if(filesize==0){
               noofpackdownloaded++;
               os.close();return;
           }
           
           int pretime=timer;
           int g=0;
           long init=System.currentTimeMillis()%100000;;
           while((c=b.read(buff,0,speedlimit))!=-1)  {
            g+=c;
            if(g>=speedlimit){
                if(System.currentTimeMillis()%100000-init<1000)
                    sleep(1000-(System.currentTimeMillis()%1000-init%1000));
                    init=System.currentTimeMillis()%100000;
                g=0;
            }
            os.write(buff,0,c);
            totaldowntillnow+=c;
            if(flagforpause==1)
                break;
            
          }
           
            os.close();noofpackdownloaded++;
    } catch (MalformedURLException ex) {
        Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
        Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    }
    public PacketDownloader(int nop,long sop,long filesize,URL u,String target){
        noofpackets=nop;
        this.target=target;
        this.filesize=filesize;
        this.url=u;
        packetsize=sop;
    }
public void run(){
        new tim().start();
        int i=0;
        for(i=0;i<=noofpackets;i++)
        new cr(i).start();
          while(noofpackdownloaded<=noofpackets){
       try {
           sleep(200);
       } catch (InterruptedException ex) {
           Logger.getLogger(PacketDownloader.class.getName()).log(Level.SEVERE, null, ex);
       }
       if(flagforpause==1)
           break;
    
}



System.out.print("  "+timer+"   ");
Merger m=new Merger((int)noofpackets,target);
if(flagforpause==0)
{
finished=1;
    System.out.println("Merged");
   m.exec();
finished=2;
}

}


}
