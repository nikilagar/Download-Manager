/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finadow;

import idmgui.DownloadOption;
import idmgui.ScheduleFrame;
import idmgui.UrlFrame;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nikhil
 */
public class MainClass {
public static int download_count=1;

 public static class NeThread extends Thread{
     int t=1;
        public void run(){
            
            try {
                while(t!=0){
                    System.out.println("anshal");
                    DateFormat df=new SimpleDateFormat("HHmmss");
                    DateFormat df2=new SimpleDateFormat("yyyyMMdd");
                    Date date=new Date();
                     Date date2=new Date();
                    String dt=df.format(date);
                    String dt2=df2.format(date);
                    int dti=Integer.parseInt(dt);
                    int dt2i=Integer.parseInt(dt2);
                  
                       //ScheduleFrame sfr=new ScheduleFrame();
                    String homeDir = System.getProperty("user.home");
        File file=new File(homeDir+"/Downloads/Idm_Downloads/record.txt");
        File file2=new File(homeDir+"/Downloads/Idm_Downloads/tmprecord.txt");
        FileInputStream fis=new FileInputStream(file);
        if(!file.exists())
        {
            file.createNewFile();
        }
                    BufferedReader nuf=new BufferedReader(new InputStreamReader(fis));
                    String tmp,tmp2,tmp3;
                  while((tmp=nuf.readLine())!=null)
                    {
                          
                   
                        
                        String Timer=tmp.substring(0,6);
                         
                        String datinfo=tmp.substring(6,14);
                      //  System.out.println("knajmd");
                       String url=tmp.substring(14);
                        int timer=Integer.parseInt(Timer);
                        int dtinfo=Integer.parseInt(datinfo);
                      //  System.out.println(Timer);
                        //System.out.println(datinfo);
                        System.out.println(timer+"and"+dti);
                        System.out.println(dtinfo+"and"+dt2i);
                        
                        if((timer<=dti&&dtinfo<=dt2i)||dt2i>dtinfo)
                        {
                            FileOutputStream fos2=new FileOutputStream(file2,true);
        FileInputStream fis2=new FileInputStream(file);
                         BufferedWriter bwf2=new BufferedWriter(new OutputStreamWriter(fos2));
                    BufferedReader nuf2=new BufferedReader(new InputStreamReader(fis2));
                            
                            file2.createNewFile();
                            while((tmp2=nuf2.readLine())!=null)
                            {
                                //System.out.println("maa ki chut bhosdi ke");
                                if(!(tmp2.equals(tmp)))
                                {
                             
                                    bwf2.write(tmp2+"\n");
                                    System.out.println("pay attentio File updated");
                                }
                            }
                         
                            bwf2.close();
                           
                              file.delete();  
                            System.out.println("pay attentio File deleted");
                            file2.renameTo(file);
                            System.out.println("pay attentio File renamed");
                         
                           
                   startdown sd=new startdown(url);
                   sd.start();
                          
                            
                            
                        }
                         
                        
                    }
               
                   
               /* if(sfr.Schedule_time.equals("")){
                  //  System.out.println("no schedule");
                    }
                else
                {
                    System.out.println(sfr.Schedule_time+" "+sfr.Schedule_date);
                    int t2=Integer.parseInt(sfr.Schedule_time);
                      int t1=Integer.parseInt(dt);
                     int d2=Integer.parseInt(sfr.Schedule_date);
                      int d1=Integer.parseInt(dt2);
                      
                    if((t1>=t2&&d1>=d2)||(d1>d2))
                    {
                         System.out.println("Download Start");
                    sfr.Schedule_time="";
                    }
                      System.out.println();
                   
                }*/
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                
            } 
                }
             catch (Exception e) {
            }
            
        }
    }
   
    /**
     * @param args the command line arguments
     */
 public static class startdown extends Thread {
     String url;
     public startdown(String url){
         this.url=url;
     }
     public void run(){
         UrlFrame obj=new UrlFrame();
         obj.UrlField.setText(url);
         obj.setVisible(true);
        obj.getContentPane().setBackground(Color.DARK_GRAY);
         obj.startButton.doClick();
     }
 }
    public static void main(String[] args) {
         NeThread no=new NeThread();
       no.start();
      
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new DownloadOption().setVisible(true);

            
            }
        });
       
       
       
       
      
        // TODO code application logic here
    }
    
}
