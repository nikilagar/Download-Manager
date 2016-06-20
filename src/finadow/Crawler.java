/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finadow;
import idmgui.UrlFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.swing.text.Document;

/**
 *
 * @author nikhil
 */
public class Crawler {
    static String url;
    static int depth=0;
    public static int index(String t,String s){
    if(t.contains(s))
        return t.indexOf(s);
    return -1;
    }
    public Crawler(int dep,String url){
        this.url=url;
        this.depth=dep;
    }
    static int testToCrawlOrNot(String tem,String pre) throws MalformedURLException, IOException{
        if(tem.length()>=4)
            if(tem.length()>4){
                if(tem.substring(tem.length()-5).equals(".html")||tem.substring(tem.length()-5).equals(".html"))
                    return 1;
                    }
            else if(tem.length()>3)
                if(tem.substring(tem.length()-4).equals(".xml")||tem.substring(tem.length()-4).equals(".asp")||tem.substring(tem.length()-4).equals(".jsp")||tem.substring(tem.length()-4).equals(".htm")||tem.substring(tem.length()-4).equals(".php"))
                return 1;
            if(tem.contains("."))
                    return 0;
            URL u=new URL(pre+tem);
            BufferedReader buff= new BufferedReader(new InputStreamReader(u.openStream()));
            if(buff.readLine().contains("html"))
                return 2;
            else if(buff.readLine().contains("html"))
                return 2;
            else return 0;
    }
    static String urlbeforeslash(String tem){
        int i,l=tem.length()-1;
        for(i=l;i>=0;i--)
            if(tem.charAt(i)=='/')
                return tem.substring(0,i+1);
        return "";
    }
    static int startFrom(int hr,String tem){
        for(;hr<tem.length();hr++)
                    if((tem.charAt(hr)>='A'&&tem.charAt(hr)<='Z')||(tem.charAt(hr)>='a'&&tem.charAt(hr)<='z')||(tem.charAt(hr)>='0'&&tem.charAt(hr)<='9'))
                    break;
        return hr;
    }
    
    static int endat(int i,String tem){
        for( ;i<tem.length();i++)
                if(tem.charAt(i)==' '||tem.charAt(i)=='>'||tem.charAt(i)=='\n'||tem.charAt(i)=='\"'||tem.charAt(i)=='\'')
                    break;
        return i;
    }
    
    /**
     * @param args the command line arguments
     */
    
    public static Map giveurls() {
        Map map=new HashMap();
        curinfo obj=new curinfo(url,urlbeforeslash(url),1);
        Queue<curinfo> result=new LinkedList<curinfo>();
        result.add(obj);
        while(!result.isEmpty()){
            try {
                obj=result.peek();
                URL u=new URL(obj.current);
                result.remove();
                URLConnection con= u.openConnection();
                BufferedReader buff=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String tem;
                
                
                while((tem=buff.readLine())!=null)
                {System.out.println(tem);
                int hr=index(tem,"href");
                if(hr!=-1)   
                    while(hr!=-1&&hr<tem.length()){
                        int i;hr+=4;
                        
                        hr=startFrom(hr,tem);
                        i=hr;
                        i=endat(i,tem);
                        if(tem.length()-7<hr||(hr<tem.length()-6&&!tem.substring(hr,hr+7).equals("http://")))
                        {
                            if((hr<tem.length()-7&&!tem.substring(hr,hr+8).equals("https://"))&&!tem.contains("//"))
                            {
                                if(map.get(obj.tillnow+tem.substring(hr,i))==null)
                                {int tes=testToCrawlOrNot(tem.substring(hr,i),obj.tillnow);
                                if(tes==0)
                                    map.put(obj.tillnow+tem.substring(hr,i), 1);
                                else if(tes==1)
                                {
                                    if(depth>=obj.depth)
                                    result.add(new curinfo(obj.tillnow+tem.substring(hr,i),obj.tillnow+urlbeforeslash(tem.substring(hr,i)),obj.depth+1));
                                    map.put(obj.tillnow+tem.substring(hr,i), 1);
                                    
                                }
                                else {
                                    result.add(new curinfo(obj.tillnow+tem.substring(hr,i),obj.tillnow+tem.substring(hr,i)+"/",obj.depth+1));
                                    map.put(obj.tillnow+tem.substring(hr,i), 1);
                                    
                                }
                                }
                            }
                            //UrlFram down=new UrlFram("localhost/"+tem.substring(hr,i));
                            //down.start();
                        }
                        tem=tem.substring(i);
                        hr=index(tem,"href");
                        
                    }
                hr=index(tem,"src");
                if(hr!=-1)
                    while(hr!=-1&&hr<tem.length()){
                        int i;hr+=4;
                        hr=startFrom(hr,tem);
                        i=hr;
                        i=endat(i,tem);
                        if(tem.length()-7<hr||(hr<tem.length()-6&&!tem.substring(hr,hr+7).equals("http://")))
                        {
                            if((hr<tem.length()-7&&!tem.substring(hr,hr+8).equals("https://")))
                 
                            {   if(map.get(obj.tillnow+tem.substring(hr,i))==null)
                                map.put(obj.tillnow+tem.substring(hr,i), 1);
                            }
//UrlFram down=new UrlFram("localhost/"+tem.substring(hr,i));
                            // down.start();
                        }
                        
                        tem=tem.substring(i);
                        hr=index(tem,"src");
                        
                    }
                
                
                }   } catch (MalformedURLException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Iterator it=map.keySet().iterator();
            while(it.hasNext())
            System.out.println(it.next());
        
        
     return map;   // TODO code application logic here
    }
    }