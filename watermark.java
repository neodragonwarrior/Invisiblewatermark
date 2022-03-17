

package watermarking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
class watermark {
    private static File basefile;
    private static String message;
    private static Cipher cipher;
    private static SecretKeySpec spec;
    private static ByteArrayOutputStream byteout;
    private static int inputfilesize,msgsize,iomarker,i,j,tempInt,filesize;
    public static final int OFFSET_JPG = 3;
    public static final int OFFSET_PNG = 42;
    public static final int OFFSET_GIF_BMP_TIF = 32;
    public static final byte UUM = 0;
    public static final byte UUF = 1;
    public static final byte UEM = 2;
    public static final byte UEF = 3;
    public static final byte CUM = 4;
    public static final byte CUF = 5;
    public static final byte CEM = 6;
    public static final byte CEF = 7;
    private static byte features,  byte1,  byte2,  byte3,  byteArrayIn[];
    public static boolean embeddingmsg(File basefile, File copyfile, String msg, String pass, int comp) {
        System.out.println("pass+comp  "+pass+" "+comp);
         if(comp!=0)
          {
           if(pass==null)
               features=CUM;
           else
               features=CEM;
         }
         else
         {
           if(pass==null)
               features=UUM;
           else
               features=UEM;
           
         }
    
    try{System.out.println("inside try"+features);
            byteout = new ByteArrayOutputStream();
            byte[] msgarray = msg.getBytes();
            msgsize = msgarray.length;
            inputfilesize = (int) basefile.length();
            byteArrayIn = new byte[inputfilesize];
            FileInputStream in = new FileInputStream(basefile);
            in.read(byteArrayIn, 0, inputfilesize);
            in.close();

            String filename = basefile.getName();
            System.out.println("file"+filename);
            if (filename.toLowerCase().endsWith(".jpg")) {
                byteout.write(byteArrayIn, 0, OFFSET_JPG);
                iomarker = OFFSET_JPG;
            } else if (filename.toLowerCase().endsWith(".png")) {
                byteout.write(byteArrayIn, 0, OFFSET_PNG);
                iomarker = OFFSET_PNG;
            } else  {
                byteout.write(byteArrayIn, 0, OFFSET_GIF_BMP_TIF);
                iomarker = OFFSET_GIF_BMP_TIF;
            }
            
            byte tempbyte[] = new byte[4];
            
            for (i = 24, j = 0; i >= 0; i -= 8, j++) 
            {
            tempInt = inputfilesize;
            tempInt >>= i;
            tempInt &= 0x000000FF;
            tempbyte[j] = (byte) tempInt;
            }
            embedbytes(tempbyte);
            System.out.println("after embedbytes");
            byteout.write(byteArrayIn,iomarker,inputfilesize-iomarker);
            System.out.println("size in embedmsg" +inputfilesize);
            iomarker=inputfilesize;
            writeBytes(new byte[]{features});
    
         
                ByteArrayOutputStream arrayoutputstream=new ByteArrayOutputStream();
                ZipOutputStream zout=new ZipOutputStream(arrayoutputstream);
                ZipEntry entry=new ZipEntry(msg);
                zout.setLevel(comp);
                zout.putNextEntry(entry);
                zout.write(msgarray,0,msgsize);
                zout.closeEntry();
                zout.finish();
                zout.close();
                msgarray=arrayoutputstream.toByteArray();
                msgsize=msgarray.length;
    
            System.out.println("after compression");
            if(features==UEM||features==CEM){
                 cipher=Cipher.getInstance("DES");
                 spec=new SecretKeySpec(pass.substring(0,8).getBytes(),"DES");
                 cipher.init(Cipher.ENCRYPT_MODE,spec);
                 msgarray=cipher.doFinal(msgarray);
                 msgsize=msgarray.length;
             }
            System.out.println("after encryption");
             tempbyte = new byte[4];
            for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                tempInt = msgsize;
                tempInt >>= i;
                tempInt &= 0x000000FF;
                tempbyte[j] = (byte) tempInt;
            }
             System.out.println("before writebytes");
            writeBytes(tempbyte);
            writeBytes(msgarray);
            DataOutputStream out = new DataOutputStream(new FileOutputStream(copyfile));
            byteout.writeTo(out);
            out.close();
            System.out.println("gggshdfh");

        } 
           catch (Exception e) {
            msg = "Error" + e.toString();
            e.printStackTrace();
            return false;
        }
        System.out.println("features in embed "+features);
        return true;
    }

     public static boolean embeddingfile(File basefile, File copyfile, File datafile, String pass, int comp) {
         filesize = (int) datafile.length();
         if (comp != 0) {
             if (pass == null) {
                 features = CUF;
             } else {
                 features = CEF;
             }
         } else {
             if (pass == null) {
                 features = UUF;
             } else {
                 features = UEF;
             }

         }
         inputfilesize = (int) basefile.length();
         try {
             byteout = new ByteArrayOutputStream();
             byteArrayIn = new byte[inputfilesize];
             DataInputStream in = new DataInputStream(new FileInputStream(basefile));
             in.read(byteArrayIn, 0, inputfilesize);
             in.close();
             String filename = basefile.getName();
             if (filename.toLowerCase().endsWith(".jpg")) {
                 byteout.write(byteArrayIn, 0, OFFSET_JPG);
                 iomarker = OFFSET_JPG;
             } else if (filename.toLowerCase().endsWith(".png")) {
                 byteout.write(byteArrayIn, 0, OFFSET_PNG);
                 iomarker = OFFSET_PNG;
             } else {
                 byteout.write(byteArrayIn, 0, OFFSET_GIF_BMP_TIF);
                 iomarker = OFFSET_GIF_BMP_TIF;
             }
             byte tempByte[] = new byte[4];
             for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                 tempInt = inputfilesize;
                 tempInt >>= i;
                 tempInt &= 0x000000FF;
                 tempByte[j] = (byte) tempInt;
             }
             embedbytes(tempByte);
             byteout.write(byteArrayIn, iomarker, inputfilesize - iomarker);
             System.out.println("size in embed" +inputfilesize);
             iomarker = inputfilesize;
             writeBytes(new byte[]{features});
             byte[] fileArray = new byte[filesize];
             in = new DataInputStream(new FileInputStream(datafile));
             in.read(fileArray, 0, filesize);
             in.close();
        
                 ByteArrayOutputStream arrayoutputstream = new ByteArrayOutputStream();
                 ZipOutputStream zout = new ZipOutputStream(arrayoutputstream);
                 ZipEntry entry = new ZipEntry(datafile.getName());
                 zout.setLevel(comp);
                 zout.putNextEntry(entry);
                 zout.write(fileArray, 0, filesize);
                 zout.closeEntry();
                  zout.finish();
                 zout.close();
                 fileArray = arrayoutputstream.toByteArray();
                 filesize=fileArray.length;
    
             if (features == UEF || features == CEF) {
                 cipher = Cipher.getInstance("DES");
                 spec = new SecretKeySpec(pass.substring(0, 8).getBytes(), "DES");
                 cipher.init(Cipher.ENCRYPT_MODE, spec);
                 fileArray = cipher.doFinal(fileArray);
                 filesize = fileArray.length;
             }
             tempByte = new byte[4];
             for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                 tempInt = filesize;
                 tempInt >>= i;
                 tempInt &= 0x000000FF;
                 tempByte[j] = (byte) tempInt;
             }
             writeBytes(tempByte);
             writeBytes(fileArray);
              FileOutputStream fout = new FileOutputStream(copyfile);
             DataOutputStream out = new DataOutputStream(fout);
             System.out.println("DataFile in embed file:" + datafile.getName());
             byteout.writeTo(out);
             byteout.close();
             out.close();
             fout.close();
         }
         catch (Exception e) {
             message = "Error.... :" + e.toString();
            
      JOptionPane.showMessageDialog(null, "unexpected size of input file", 
              "error", JOptionPane.ERROR_MESSAGE);
             return false;
             
         }
         System.out.println("features in e file"+features);
         return true;
    }

    static String getMessage() {
        return message;
    }

    static boolean retrieveFile(SteganoInformation details, String password) {
     File DataFile = null;
        features = details.getFeatures();
        System.out.println("features in r file"+features);
        try {
          basefile = details.getFile();
            byteArrayIn = new byte[(int) basefile.length()];
            DataInputStream in = new DataInputStream(new FileInputStream(basefile));
            in.read(byteArrayIn, 0, (int) basefile.length());
            in.close();
         msgsize =details.getDataLength();
            byte[] fileArray = new byte[msgsize];
            iomarker = details.getInputMarker();
            readBytes(fileArray);
            if (msgsize <= 0) {
                message = "Unexpected size of embedded file: 0.";
                return false;
            }
            if (features == CEF || features == UEF) {
                password = password.substring(0, 8);
                byte passwordBytes[] = password.getBytes();
                cipher = Cipher.getInstance("DES");
                spec = new SecretKeySpec(passwordBytes, "DES");
                cipher.init(Cipher.DECRYPT_MODE, spec);
                try {
                    fileArray = cipher.doFinal(fileArray);
                } catch (Exception bp) {
                    message = "Incorrect Password";
                    bp.printStackTrace();
                    return false;
                }
                msgsize = fileArray.length;
            }

      
                ByteArrayOutputStream by = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(by);
                ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(fileArray));
                ZipEntry entry = zipIn.getNextEntry();
              
                DataFile = new File(entry.getName());
                byteArrayIn = new byte[1024];
                while ((tempInt = zipIn.read(byteArrayIn, 0, 1024)) != -1) {
                    out.write(byteArrayIn, 0, tempInt);
                }
                zipIn.close();
                 out.close();
                fileArray = by.toByteArray();
                msgsize = fileArray.length;
     
            details.setdatafile(DataFile);
          DataOutputStream data = new DataOutputStream(new FileOutputStream(DataFile));
            data.write(fileArray, 0, fileArray.length);
            System.out.println("DataFile in retireve file:" + DataFile.getName());
            data.close();
        } catch (Exception e) {
            message = "Oops!!\n Error: " + e;
            e.printStackTrace();
           
            return false;
        }
     
        return true;
    }

    static String retrieveMessage(SteganoInformation details, String password) {
        String msg = null;
        features = details.getFeatures();
        try {
            basefile = details.getFile();
            byteArrayIn = new byte[(int) basefile.length()];
            DataInputStream in = new DataInputStream(new FileInputStream(basefile));
            in.read(byteArrayIn, 0, (int) basefile.length());
            in.close();
            msgsize = details.getDataLength();
            if (msgsize <= 0) {
                message = "Unexpected size of message:0.";
                return ("#FAILED#");

            }
            byte[] messageArray = new byte[msgsize];
            iomarker = details.getInputMarker();
            readBytes(messageArray);
            if (features == CEM || features == UEM) {
                password = password.substring(0, 8);
                byte passwordBytes[] = password.getBytes();
                cipher = Cipher.getInstance("DES");
                spec = new SecretKeySpec(passwordBytes, "DES");
                cipher.init(Cipher.DECRYPT_MODE, spec);
                try {
                    messageArray = cipher.doFinal(messageArray);
                } catch (Exception bp) {
                    message = "Incorrect Password";
                    bp.printStackTrace();
                    return "#FAILED#";
                }
            }
      
                ByteArrayOutputStream by = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(by);
                ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(messageArray));
                zipIn.getNextEntry();
                byteArrayIn = new byte[1024];
                while ((tempInt = zipIn.read(byteArrayIn, 0, 1024)) != -1) {
                    out.write(byteArrayIn, 0, tempInt);
                }
                zipIn.close();
                out.close();
                messageArray = by.toByteArray();
                msgsize = messageArray.length;
    
            msg = new String(SteganoInformation.byteToCharArray(messageArray));
        } 
        catch (Exception e) {
            message = "Oops||\n Error:" + e;
            e.printStackTrace();
            return ("#FAILED#");
        }
        message = "Message size: " + msgsize + " B";
        return msg;
            

   
    }
       
    

    private static void embedbytes(byte[] bytes) {
        int size = bytes.length;
        for (i = 0; i < size; i++) {
            byte1 = bytes[i];
            for (j = 6; j >= 0; j -= 2) {
                byte2 = byte1;
                byte2 >>= j;
                byte2 &= 0x03;
                byte3 = byteArrayIn[iomarker];
                byte3 &= 0xFC;
                byte3 |= byte2;
                byteout.write(byte3);
                iomarker++;
            }
        }
    }

    private static void readBytes(byte[] bytes) {
       int size = bytes.length;
        for (i = 0; i < size; i++) {
            bytes[i] = byteArrayIn[iomarker];
         iomarker++;
        }
    }
    

    private static void writeBytes(byte[] bytes) {
        int size = bytes.length;
        for (i = 0; i < size; i++) {
            byteout.write(bytes[i]);
          
            iomarker++;
        }

    }
    }
      

