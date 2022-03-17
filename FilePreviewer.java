/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package watermarking;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class FilePreviewer extends JComponent implements PropertyChangeListener{
ImageIcon thumbnail=null;
public FilePreviewer(JFileChooser fc){
    setPreferredSize(new Dimension(100,100));
    fc.addPropertyChangeListener(this);
    }
    public void loadimage(File f){
    if(f==null)
        thumbnail=null;
    else{
        ImageIcon temp=new ImageIcon(f.getPath());
        if(temp.getIconWidth()>90)
             thumbnail = new ImageIcon(temp.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
        else
            thumbnail=temp;
            
    }
        
}
public void propertyChange(PropertyChangeEvent evt) {
String prop=evt.getPropertyName();
if(prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)){
    if(isShowing()){
        loadimage((File) evt.getNewValue());
        repaint();   
}
    }
}
   
public void paint(Graphics gr){
    if(thumbnail!=null){
       int xpos=(getWidth() - thumbnail.getIconWidth()) / 2;
       int ypos=(getHeight() - thumbnail.getIconHeight()) / 2;
       gr.drawImage(thumbnail.getImage(), xpos, ypos,null);
    }
}
}
