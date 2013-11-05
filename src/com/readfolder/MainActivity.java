package com.readfolder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText direccio,nomArxiu;
	Button button1;
	String NOMBRE_ARCHIVO="arxiu.txt";
	String DIRECTORIO_TODO="/ReadFolder";
	TextView result;
	
	private boolean mAlmacenamientoExternoDisponible = false;
	private boolean mAlmacenamientoExternoEscritura = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);
		
		direccio=(EditText) findViewById(R.id.direccio);
		nomArxiu=(EditText) findViewById(R.id.nomArxiu);
		button1=(Button) findViewById(R.id.button1);
		result=(TextView) findViewById(R.id.resultat);
				
	}

	public void guardar(View view) {
		//busca i crea el directori on hi han el arxius a llegir
		validaNom();
		String path=Environment.getExternalStorageDirectory().getAbsolutePath()+DIRECTORIO_TODO;
	    File llocArxiu = new File(path);
	    llocArxiu.mkdirs();
		
		//crea una instacia de l'objecte
		Pare pare=new Pare();
		pare.listFilesForFolder(llocArxiu);
		guardarEnAlmacenExterno(pare.getContingutArxiu(),pare.getNomArxiu());
		
		}

	//funcions almacenament extern
	private void guardarEnAlmacenExterno(String[] contingutArxius,String[] nomArxius) {

		//if (contingutArxius.length>1){
			//Toast.makeText(this, getString(R.string.rellena_campos), Toast.LENGTH_LONG).show();
		//}else {
			checkEstadoAlmacenExterno();
			if (mAlmacenamientoExternoDisponible && mAlmacenamientoExternoEscritura) {
				
				try {
					
					// Se crea la ruta para almacenar el archivo en la raíz del
					// almacenamiento externo (desde donde la apilcación tiene permisos)
					String path=Environment.getExternalStorageDirectory().getAbsolutePath()+
							DIRECTORIO_TODO+"/Resultats/";
					
					File llocArxiu = new File(path);
					llocArxiu.mkdirs();
					File file = new File(llocArxiu, NOMBRE_ARCHIVO);
					
					FileWriter fichero = new FileWriter(file);
			        PrintWriter pw = new PrintWriter(fichero);
		            for (int i = 0; nomArxius[i]!=null; i++){
		                pw.println(nomArxius[i]+" - "+contingutArxius[i]);
		            }
		            fichero.close();
	
					Toast.makeText(this, getString(R.string.archivo_guardado), Toast.LENGTH_LONG).show();
					//result.setText("Resultat a: "+path+ Html.fromHtml("<br />"));
					
				} catch (FileNotFoundException e) {
					Toast.makeText(this, getString(R.string.error_fnf), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(this, getString(R.string.error_io), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
			
		//}
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
	
	public void validaNom(){
		if (direccio.getText().length()!=0){
			DIRECTORIO_TODO = "/"+direccio.getText().toString();
		}
		if (nomArxiu.getText().length()!=0){
			NOMBRE_ARCHIVO = nomArxiu.getText().toString();
		}
	}
}
