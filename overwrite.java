/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package watermarking;



import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;


/**
 *
 * @author valakannikal
 */
 class overwrite {
    
    private File file;
    private File DataFile=null;
    private byte features;
    private int dataLength,temp;
    private byte byteArray[],name[],byte1,byte2;
    private int inputMarker,i,j;
   
  
        
    public byte getFeatures()
    {
        return features;
    }
       
  
    
    private void retrieveBytes(byte[] bytes,byte[] array,int marker)
    {
        byteArray=array;
        inputMarker=marker;
        int size=bytes.length;
        for(i=0;i<size;i++)
        {
            byte1=0;
            for(j=6;j>=0;j-=2)
            {
                byte2=byteArray[inputMarker];
                inputMarker++;
                byte2&=0x03;
                byte2<<=j;
                byte1|=byte2;
            }
            bytes[i]=byte1;
        }
        
   }   
 
    
  private void readBytes(byte[] bytes)
    {
        int size = bytes.length;
        for (i = 0; i < size; i++) {
         try{  bytes[i] = byteArray[inputMarker];
         }catch(Exception e)
         {
         bytes[0] =-1;
         }
        }
        inputMarker++; 
  
       
    }
   
    public overwrite(File file)
    {
       
        this.file=file;
       
        byteArray=new byte[(int)file.length()];
        try
        {
            DataInputStream in=new DataInputStream(new FileInputStream(file));
            in.read(byteArray,0,(int)file.length());
            in.close();   
        }
        catch(Exception e){} 
        name=new byte[4];
        String filename=file.getName();
   
        if (filename.toLowerCase().endsWith(".jpg")) {
            inputMarker = watermark.OFFSET_JPG;
        } else if (filename.toLowerCase().endsWith(".png")) {
            inputMarker = watermark.OFFSET_PNG;
        } else 
            inputMarker = watermark.OFFSET_GIF_BMP_TIF;
        
      
        retrieveBytes(name,byteArray,inputMarker);
        dataLength=0;
        for(i=24,j=0;i>=0;i-=8,j++)
        {
            temp=name[j];
            temp&=0x000000FF;
            temp<<=i;
            dataLength|=temp;
        }
        inputMarker=dataLength;
  
   
      
        name=new byte[1];
      
        readBytes(name);
        features=name[0];
        }
}