

package watermarking;

import java.io.File;
import javax.swing.filechooser.FileFilter;


public class imgfilter extends FileFilter{


   public boolean accept(File f) {
        return  f.getName().toLowerCase().endsWith(".jpg")||
                f.getName().toLowerCase().endsWith(".gif")||
                f.getName().toLowerCase().endsWith(".bmp")||
                f.getName().toLowerCase().endsWith(".png");
               
                
    }

   
   public String getDescription() {
        return "image files";
    }

}
