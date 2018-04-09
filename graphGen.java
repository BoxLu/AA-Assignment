import java.io.*;
import java.util.*;

public class graphGen
{
	
	public static void main(String args[]) {
    	generateAV();
		generateAE();
	}
	public static void generateAV() {
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("DataSetAV2.txt"));
			String[] token;
			String str;
			while ((str = in.readLine()) != null) {
				token = str.split(" ");
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				out.write("AV "+token[0]+"\n");
				out.write("AV "+token[1]+"\n");
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	public static void generateAE() {
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("DataSetAE1.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				out.write("AE "+str+"\n");
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
}