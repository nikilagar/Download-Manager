/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idmgui;

import finadow.DownloadManager;
import static idmgui.UrlFrame.tabbedpane;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author anshal
 */
public class jpanel extends javax.swing.JPanel {

    /**
     * Creates new form jpanle
     */
    public jpanel() {
        initComponents();
    }
/////////////////////////////////////////////////////////////////////////////////////////////

    DownloadManager dowmanager = null;
    /**
     * Creates new form ProgressSimulation
     */
    String finalfilelocation;
    int i = 0, stop = 0, can = 0;
    NewThread nt;
    int extension_indicator;
    String File_name;
    String unmurl;

    public jpanel(String url, int ext, String name) {
        this.extension_indicator = ext;
        this.File_name = name;
        this.unmurl = url;
        initComponents();

        nt = new NewThread();
        nt.start();
    }

    public class NewThread extends Thread {

        public void run() {
            String homeDir = System.getProperty("user.home");

            //System.out.println("yesStarted");
            File f = null;
            boolean bool = false;
            String Fil_path = null;
            System.out.println(extension_indicator);
            if (extension_indicator == 0) {
                Fil_path = homeDir + "/Downloads/Idm_Downloads/Others/" + File_name;
            }
            if (extension_indicator == 1) {
                Fil_path = homeDir + "/Downloads/Idm_Downloads/Documents/" + File_name;
            }
            if (extension_indicator == 2) {
                Fil_path = homeDir + "/Downloads/Idm_Downloads/Music/" + File_name;
            }
            if (extension_indicator == 3) {
                Fil_path = homeDir + "/Downloads/Idm_Downloads/Videos/" + File_name;
            }
            if (extension_indicator == 4) {
                Fil_path = homeDir + "/Downloads/Idm_Downloads/Pictures/" + File_name;
            }
            // System.out.println("a"+ufr.File_name+"ad");
            //String Fil_path="/home/anshal/Idm_Downloads/Others/asd.txt";
            System.out.println(Fil_path);

            try {
                System.out.println(unmurl);
                File test = new File(Fil_path);
                finalfilelocation = Fil_path;

                if (test.exists() && !(new File(Fil_path + "1")).exists()) {
                    cancelButton.setText("close");
                    pauseButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "File exists");
                    return;

                }

                dowmanager = new DownloadManager(unmurl, Fil_path);

                long fsiz = dowmanager.fsize;
                while (fsiz == -1) {
                    sleep(1000);
                }
                fsiz = dowmanager.fsize;
                System.out.println("FIle size" + fsiz);
                double tillnow = 0;
                while (dowmanager.status == -1) {
                    sleep(500);
                }
                if (dowmanager.status == 0) {
                    pauseButton.setEnabled(false);
                    cancelButton.setText("close");
                    return;
                }
                if (dowmanager.status == 1) {
                    for (i = 1; i <= 100; i++) {
                        if (stop == 1) {
                            break;
                        }
                        System.out.println("progress " + i + " tillnow " + tillnow);
                        try {
                            while (i > tillnow && stop == 0) {
                                sleep(100);
                                tillnow = dowmanager.getdowntill() * 100.0 / fsiz;
                                if (dowmanager.getfinished() > 0) {
                                    pauseButton.setEnabled(false);
                                    tillnow = 101;
                                }

                            }
                        } catch (Exception e) {
                        }
                        pbar.setValue(i);

                    }
                    System.out.print("SEEEE MEEE" + dowmanager.getdowntill());
                    if (dowmanager.getfinished() > 0 || i == 101) {
                        mergingdialog dia = new mergingdialog();
                        dia.setVisible(true);
                        cancelButton.setEnabled(false);
                        pauseButton.setEnabled(false);
                        while (dowmanager.getfinished() == 1) {
                            sleep(1000);
                        }
                        dia.dispose();
                        JOptionPane.showMessageDialog(null, "Download Complete :(");
                        cancelButton.setText("close");
                        cancelButton.setEnabled(true);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "cannot download in packets \n downloading serially :)");
                    pauseButton.setEnabled(false);
                    while (dowmanager.secmodfi != 1) {
                        pbar.setValue(50);
                        sleep(1000);
                        if (can == 1) {
                            return;
                        }

                    }
                    pbar.setValue(100);
                    JOptionPane.showMessageDialog(null, "Download complete :)");
                    cancelButton.setText("close");
                    dowmanager = null;
                }

                /*
                
                 File file = new File(Fil_path);
                 try {
                 file.createNewFile();
                 } catch (Exception e) {
                 }*/
            } catch (MalformedURLException ex) {
                Logger.getLogger(jpanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(jpanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pbar = new javax.swing.JProgressBar();
        pauseButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pauseButton1 = new javax.swing.JButton();

        pbar.setBackground(new java.awt.Color(210, 210, 210));
        pbar.setFont(new java.awt.Font("Ubuntu Condensed,", 1, 14)); // NOI18N
        pbar.setForeground(java.awt.Color.blue);
        pbar.setStringPainted(true);

        pauseButton.setBackground(new java.awt.Color(144, 176, 235));
        pauseButton.setForeground(new java.awt.Color(255, 255, 255));
        pauseButton.setText("pause");
        pauseButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, java.awt.Color.white));

        cancelButton.setBackground(new java.awt.Color(144, 176, 235));
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("cancel and del");
        cancelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, java.awt.Color.white));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Download Progress:-");

        jButton1.setBackground(new java.awt.Color(220, 220, 200));
        jButton1.setForeground(java.awt.Color.red);
        jButton1.setText("x");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pauseButton1.setBackground(new java.awt.Color(144, 176, 235));
        pauseButton1.setForeground(new java.awt.Color(255, 255, 255));
        pauseButton1.setText("cancel");
        pauseButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, java.awt.Color.white));
        pauseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbar, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(pauseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pbar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pauseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonActionPerformed

/*
     private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
     // TODO add your handling code here:
     }                                            
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       pauseButton1.doClick();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pauseButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButton1ActionPerformed
can = 1;
pauseButton.doClick();
        Component selected = tabbedpane.getSelectedComponent();
        tabbedpane.remove(selected);
        // TODO add your handling code here:
    }//GEN-LAST:event_pauseButton1ActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (stop == 0) {
            dowmanager.pause();
            stop = 1;
            pauseButton.setText("Resume");

        } else {
            pauseButton.setText("Pause");
            stop = 0;
            new jpanel.NewThread().start();
        }
        //  dowmanager.stop();
// TODO add your handling code here:

    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // System.out.println("hohohohoh");
        can = 1;
        int i = 0;
        if (dowmanager != null && dowmanager.getfinished() == 0) {
            for (; i < 5; i++) {
                File tm;
                if (i > 0) {
                    tm = new File(finalfilelocation + "" + i);
                } else {
                    tm = new File(finalfilelocation);
                }
                if (tm.exists()) {
                    tm.delete();
                } else {
                    break;
                }
            }
        }

      //  this.remove(this);
        //  int h=UrlFrame.tabbedpane.getTabCount();
        //System.out.println(h);
        //   UrlFrame.tabbedpane.removeTabAt(h);
        //  this.dispose();
        Component selected = tabbedpane.getSelectedComponent();
        tabbedpane.remove(selected);

    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton pauseButton1;
    private javax.swing.JProgressBar pbar;
    // End of variables declaration//GEN-END:variables
}
