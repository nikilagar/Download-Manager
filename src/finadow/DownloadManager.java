/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finadow;

import finadow.PacketDownloader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author nikhil
 */
public class DownloadManager {
       HttpURLConnection con;
    public long fsize = 0;
    int noofpackets=5;
    public void setpackets(int noofpackets){
        this.noofpackets=noofpackets;
    }
    PacketDownloader packdowobj=null;
    URL url;
    String target;
    public int fl = 0,secmodfi=0,status=-1;

    public long getdowntill() {
        return packdowobj.totaldowntillnow;
    }
    public long getspeed(){
        return packdowobj.speed;
    }

    public int getfinished() {
        if(packdowobj==null)
            return 0;
        return packdowobj.finished;
    }
    public void setspeed(int speed){
        packdowobj.set(speed);
    }

    /**
     * Creates new form dow
     */
    public DownloadManager(String url, String dest) throws MalformedURLException {
        this.url = new URL(url);
        target = dest;
        startDownload();
    }
    
    public void pause() {
        packdowobj.flagforpause = 1;
    }

    public void stop() {
        packdowobj.flagforpause = 1;
    }
public void startDownload(){
    try {
             noofpackets = 4;
            if(noofpackets==1)
                noofpackets--;
            long ps;
            int option=JOptionPane.showConfirmDialog(null,"Want proxy");
            if(option==JOptionPane.YES_OPTION)
            try{
final String authUser = "edcguest";
final String authPassword = "edcguest";
System.setProperty("http.proxyHost", "172.31.102.14");
System.setProperty("http.proxyPort", "3128");
System.setProperty("httk"
        + "p.proxyUser", authUser);
System.setProperty("http.proxyPassword", authPassword);

Authenticator.setDefault(
  new Authenticator() {
    public PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(authUser, authPassword.toCharArray());
    }
  }
);    
}
catch(Exception e){
    System.out.print("fuck");
}
            HttpURLConnection httpcon;
            
             httpcon= (HttpURLConnection) url.openConnection();
//System.out.print(fsize);
            if (httpcon.getResponseCode() / 100 ==2) {
                status=1;
                System.out.print("first");
                fsize = httpcon.getContentLength();
                if(noofpackets!=0)
                ps = fsize / (noofpackets);
                else ps=fsize;
                httpcon.disconnect();
                packdowobj = new PacketDownloader(noofpackets, ps, fsize, url, target);
                Thread packthread = new Thread(packdowobj);
                packthread.start();
            } else {
                fsize=0;
                    System.out.print("second");
               try{            
               httpcon.setRequestProperty("User-Agent", "Mozilla/5.0");
               
               }
               catch(Exception e){
                   JOptionPane.showMessageDialog(null,"File not exists");
                   status=0;
                   return;
               }
               InputStream is = null;
                try {
                    is = url.openStream();
                } catch (Exception e) {
                    System.out.print("Cannot do");
                }
                if (is != null) {
                    status=2;
                    FileOutputStream os = new FileOutputStream(target);
                    int c;
                    while ((c = is.read()) != -1) {
                        os.write(c);
                        
                    }
                    secmodfi=1;
                            
                    os.close();
                }
            }

// TODO add your handling code here:
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(finadow.DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
