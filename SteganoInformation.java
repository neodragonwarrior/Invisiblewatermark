package watermarking;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;

class SteganoInformation
{
    private File file;
    private File DataFile=null;
    private byte features;
    private int dataLength,temp;
    private byte byteArray[],name[],byte1,byte2;
    private int inputMarker,i,j;
    public retrieve a;
    public File getFile()
    {
        return file;
    }
    public int getInputMarker()
    {
        return inputMarker;
    }
   
        
    public byte getFeatures()
    {
        return features;
    }
       
    public int getDataLength()
    {
        return dataLength;
    }

public File getdataFile() {
       System.out.println("MasterFile in retrieve  file:"+DataFile.getName());
      return DataFile;
    }

    void setdatafile(File datafile) {
       this.DataFile=datafile;
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
     public static char[] byteToCharArray(byte[] bytes)
    {
        int size=bytes.length,i;
        char []chars=new char[size];
        for(i=0;i<size;i++)
        {
            bytes[i]&=0x7F;
            chars[i]=(char)bytes[i];   
        }
        return chars;
    }

    
    private void readBytes(byte[] bytes)
    {
        int size=bytes.length;
          for(i=0;i<size;i++)
            
            try{  
            bytes[i]=byteArray[inputMarker];
            inputMarker++; 
            
    }catch(Exception e)
        {

        JOptionPane.showMessageDialog(null,"Ooops!!!!Neither file nor message found" +
                "\nPlease select an embeded file","ERROR",JOptionPane.ERROR_MESSAGE);
               
         a.setVisible(true); 
    
    }
        
 
  
    
    }
   
    public SteganoInformation(File file,retrieve a)
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
        System.out.println("io after retrieve"+inputMarker);
        dataLength=0;
        for(i=24,j=0;i>=0;i-=8,j++)
        {
            temp=name[j];
            temp&=0x000000FF;
            temp<<=i;
            dataLength|=temp;
        }
        inputMarker=dataLength;
  
        System.out.println("io after dl"+inputMarker);
      
        name=new byte[1];
        readBytes(name);
        features=name[0];
        System.out.println("features in steg "+features+" "+name);
   
       
       name=new byte[4];
        readBytes(name);
        dataLength=0;
       for(i=24,j=0;i>=0;i-=8,j++)
        {
            temp=name[j];
            temp&=0x000000FF;
            temp<<=i;
            dataLength|=temp;
       } 
   }
     
}







