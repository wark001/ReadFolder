package com.readfolder;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Pare {

	String nomArxiu[] = new String[9999];
	String contingutArxiu[] = new String[9999];
	int numerArxiu=0;
	
	public Pare(){
		
	}
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(folder);   
	        } else {
	        	nomArxiu[numerArxiu]=fileEntry.getName();	
	        	llegeix(fileEntry);
	        }
	    }
	}
	
	public void llegeix(final File folder){
		try{
			//final File fileEntry : folder.listFiles()
			 // Abrimos el archivo
		    FileInputStream fstream = new FileInputStream(folder);
		    // Creamos el objeto de entrada
		    DataInputStream entrada = new DataInputStream(fstream);
		    // Creamos el Buffer de Lectura
		    BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
		    String strLinea;
		    

		    strLinea = buffer.readLine();
		    strLinea = buffer.readLine();
		    strLinea = buffer.readLine();
		    strLinea = buffer.readLine();
		   
		    //contingutNecessary(strLinea);
		    System.out.println(contingutNecessary(strLinea));
		    System.out.println(strLinea);
		    contingutArxiu[numerArxiu]=contingutNecessary(strLinea);
        	numerArxiu++;   
		}catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
	}


	public void escritura(){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("resum.txt");
            pw = new PrintWriter(fichero);
 
            for (int i = 0; nomArxiu[i]!=null; i++)
                pw.println(nomArxiu[i]+" - "+contingutArxiu[i]);
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public String contingutNecessary(String coment){
		int inici=coment.indexOf("\"")+1;
		int acabament=coment.lastIndexOf("\"");
		String resultatFinal=coment.substring(inici, acabament);
		
		return resultatFinal;
	}
	
	public static void main(String[] args) {
	    System.out.println("Hello World!"); //Display the string.
	    final File folder = new File("C:/Users/wark/Desktop/PE_TXT");
	    Pare papa=new Pare();
	    papa.listFilesForFolder(folder);
	    papa.escritura();
	}


}

