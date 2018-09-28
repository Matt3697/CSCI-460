import java.io.*;

public class Family_name_1 {
	public static void main(String[] args) throws IOException {
	      
		try {
	         byte bWrite [] = {11,21,3,40,5};
	         OutputStream os = new FileOutputStream("File_name-1.txt");
	         for(int x = 0; x < bWrite.length ; x++) {
	            os.write( bWrite[x] );   // writes the bytes
	         }
	         os.close();
		}
		catch (IOException e) {
	         System.out.print("Exception");
	      }	
	}
}
