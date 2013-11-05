package com.readfolder;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText direccio,nomArxiu;
	Button button1;
	String NOMBRE_ARCHIVO="arxiu.txt";
	
	private boolean mAlmacenamientoExternoDisponible = false;
	private boolean mAlmacenamientoExternoEscritura = false;
	
	//Ini------------------------------------------------------------------//
	private static final String TAG = MainActivity.class.getName();
    private static final String FILENAME = "./storage/emulated/0/myFile.txt";
   //End------------------------------------------------------------------//
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);
		
		direccio=(EditText) findViewById(R.id.direccio);
		nomArxiu=(EditText) findViewById(R.id.nomArxiu);
		button1=(Button) findViewById(R.id.button1);
		
		//Ini-------------------------------------------------------------------//
		//String textToSaveString = "Hello Android";
		//writeToFile(textToSaveString);
		
		//End------------------------------------------------------------------//
		
	}
	//Ini------------------------------------------------------------------//
	private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
         
    }
	//End------------------------------------------------------------------//
	
	public void guardar(View view) {
		guardarEnAlmacenExterno("prova de concepte");
		//creaCarpeta();
	/*	final File folder = new File("C:/Users/wark/Desktop/PE_TXT");
	    Pare papa=new Pare();
	    papa.listFilesForFolder(folder);
	    papa.escritura();
	*/}
	
	public void creaCarpeta(){
		File folder = new File("/marcPlanas/gat.txt");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//funcions almacenament extern
	private void guardarEnAlmacenExterno(String texto) {
		
		boolean archivoGuardado = false;
		String textoArchivo = "donat";
		

		if (textoArchivo.equals("")){
			//Toast.makeText(this, getString(R.string.rellena_campos), Toast.LENGTH_LONG).show();
		}else {
			
			// Se comprueba el estado del almacenamiento externo antes de intentar crear un archivo
			checkEstadoAlmacenExterno();
			
			if (mAlmacenamientoExternoDisponible && mAlmacenamientoExternoEscritura) {
			
				try {
					
					// Se crea la ruta para almacenar el archivo en la raíz del
					// almacenamiento externo (desde donde la apilcación tiene permisos)
				    File file = new File(getExternalFilesDir(null), NOMBRE_ARCHIVO);
					
					// Se escribe el archivo
				    OutputStream os = new FileOutputStream(file);
				    os.write(textoArchivo.getBytes());
				    os.close();
					
					Toast.makeText(this, getString(R.string.archivo_guardado), Toast.LENGTH_LONG).show();
					//((EditText) findViewById(R.id.etTextoArchivo)).setText("");
					cargarArchivoExterno();			
					
				} catch (FileNotFoundException e) {
					Toast.makeText(this, getString(R.string.error_fnf), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(this, getString(R.string.error_io), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		}
		if (archivoGuardado) {
			//((TextView) findViewById(R.id.txtArchivosGuardados)).append(textoArchivo + "\n");
			//limpiarCampos();
			Toast.makeText(this, getString(R.string.archivo_guardado), Toast.LENGTH_SHORT).show();
		}
	}

	private void checkEstadoAlmacenExterno() {
		
		String estadoAlmacenExterno = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(estadoAlmacenExterno)) {
		    // Se puede leer y escribir
		    mAlmacenamientoExternoDisponible = mAlmacenamientoExternoEscritura = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(estadoAlmacenExterno)) {
		    // Sólo se puede escribir
		    mAlmacenamientoExternoDisponible = true;
		    mAlmacenamientoExternoEscritura = false;
		} else {
		    // No se puede leer ni escribir. 
			// La variable "estadoAlmacenExterno" dará la información exacata. 
		    mAlmacenamientoExternoDisponible = mAlmacenamientoExternoEscritura = false;
		}
	}
	
	private boolean cargarArchivoExterno() {
		
		boolean archivoCargado = false;
		
		try {
			
			String textoArchivoExterno = "arxiboexterno";
			String linea = "";
			
			// Se obtiene el archivo
			File file = new File(getExternalFilesDir(null), NOMBRE_ARCHIVO);			
			FileInputStream fis = new FileInputStream(file);
			
			// Esta es una forma usual en Java de leer un archivo línea por línea al final.
			// Si se usara directamente fis.read(), habría que limitar el número de bytes que se extraen del archivo.
			InputStreamReader in = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(in);
			while ((linea = br.readLine()) != null) {
				textoArchivoExterno += linea + "\n";
			}
				
			//((TextView) findViewById(R.id.txtArchivosGuardados)).append(textoArchivoExterno);
			//listRaw();
			archivoCargado = true;
			br.close();
			
		} catch (FileNotFoundException e) {
			Toast.makeText(this, getString(R.string.error_fnf), Toast.LENGTH_LONG).show();
			e.printStackTrace();
			//  Cerrar buffer en caso de que haya sido abierto
		} catch (IOException e) {
			Toast.makeText(this, getString(R.string.error_io), Toast.LENGTH_LONG).show();
			e.printStackTrace();
			//  Cerrar buffer en caso de que haya sido abierto
		}

		return archivoCargado;
	}
	
}
