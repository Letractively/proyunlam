package ar.com.AmberSoft.iEvenTask.server.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;

import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;

public abstract class Reporte {
	
	private static Logger logger = Logger.getLogger(Reporte.class);

	protected DocWriter writter;
	protected OutputStream stream;
	protected Document document;
	protected ByteArrayOutputStream output;
	
	public Reporte(OutputStream stream){
		document = new Document();
		output = new ByteArrayOutputStream();
		try {
			this.writter = getDocWritter();
		} catch (DocumentException e) {
			logger.error(Tools.getStackTrace(e));
		}
	}

	public void abrirDocumento() {
		document.open();
	}
	
	/**
	 * Escribe el reporte en el flujo de salida
	 */
	public void flush(){
		try {
			document.close();
			output.writeTo(stream);
			stream.flush();
		} catch (Exception e){
			logger.error(Tools.getStackTrace(e));
		} 
	}
	
	/**
	 * Retorna el Writter actual
	 * @return
	 */
	public DocWriter getWritter(){
		return writter;
	}
	
	/**
	 * Retorna un nuevo DocWritter
	 * @return
	 */
	public abstract DocWriter getDocWritter() throws DocumentException ;
	
	/**
	 * Salto de linea
	 */
	public void saltoDeLinea(){
		try {
			
			document.add(new Phrase(ComunesConst.SALTO_LINEA));
		} catch (DocumentException e) {
			Tools.getStackTrace(e);
		}
	}
	
	public Document getDocument() {
		return document;
	}	
	
}
