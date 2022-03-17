/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package watermarking;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
class MessageDisplay extends JFrame{

    public MessageDisplay(String message) {
        
             new select().setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            getContentPane().add(new JScrollPane(new JTextArea(message,14,50)));
            Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
            int width=(int)(0.75*d.width);
            int height=(int)(0.75*d.height);
            setSize(width,height);
            setLocation((d.width-width)/2,(d.height-height)/2);
            setVisible(true);
           
        }
    }


