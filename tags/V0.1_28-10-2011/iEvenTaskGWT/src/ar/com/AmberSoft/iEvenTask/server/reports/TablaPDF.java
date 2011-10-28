package ar.com.AmberSoft.iEvenTask.server.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

public class TablaPDF {
	
	private static Logger logger = Logger.getLogger(TablaPDF.class);

	private Collection celdas = new ArrayList();
	private int columnas = 1;
	private Document documento;
	int tamanioColumnas[];
	private Boolean agregarADocumento = Boolean.TRUE;
	
	
	public Boolean getAgregarADocumento() {
		return agregarADocumento;
	}

	public void setAgregarADocumento(Boolean agregarADocumento) {
		this.agregarADocumento = agregarADocumento;
	}

	public int[] getTamanioColumnas() {
		return tamanioColumnas;
	}

	public void setTamanioColumnas(int[] tamanioColumnas) {
		this.tamanioColumnas = tamanioColumnas;
	}

	public TablaPDF(Document document){
		this.documento = document;
	}
	
	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public void addCelda(CeldaPDF celda){
		celdas.add(celda);
	}
	
	public void addMensajeEnCelda(String key){
		CeldaPDF celda = new CeldaPDF();
		celda.addMensaje(key, null);
		addCelda(celda);
	}
	
	/**
	 * Arma la tabla y la escribe en el documento
	 */
	public void flush(){
		getMe();
	}

	public PdfPTable getMe() {
		PdfPTable tabla = new PdfPTable(columnas);
		
		Iterator it = celdas.iterator();
		while (it.hasNext()) {
			CeldaPDF celda = (CeldaPDF) it.next();
			tabla.addCell(celda.getMe());
		}
		try {
			if (tamanioColumnas!=null){
				tabla.setWidths(tamanioColumnas);
			}
			if (agregarADocumento.booleanValue()){
				documento.add(tabla);
			}
		} catch (DocumentException e) {
			logger.error(Tools.getStackTrace(e));
		}
		return tabla;
	}
	
}
