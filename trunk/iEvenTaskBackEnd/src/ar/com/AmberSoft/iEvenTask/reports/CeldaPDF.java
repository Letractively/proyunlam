package ar.com.AmberSoft.iEvenTask.reports;

import java.awt.Color;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;


/**
 * PdfPCell Wrapper
 * @author leonel.g.larreta
 *
 */
public class CeldaPDF {
	
	private StringBuffer buffer = new StringBuffer();
	private int alineacionHorizontal = Element.ALIGN_CENTER;
	private int alineacionVertical = Element.ALIGN_TOP;
	private Integer border;
	private Color color = Color.WHITE;
		

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getAlineacionVertical() {
		return alineacionVertical;
	}

	public void setAlineacionVertical(int alineacionVertical) {
		this.alineacionVertical = alineacionVertical;
	}

	private int colspan = 1;
	private Element element;
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getAlineacionHorizontal() {
		return alineacionHorizontal;
	}

	public void setAlineacionHorizontal(int alineacionHorizontal) {
		this.alineacionHorizontal = alineacionHorizontal;
	}

	public void addMensaje(String key, Object[] params){
		//buffer.append(AdministradorAplicacion.extraerMensaje(key, params));
	}

	public void addMensajeEspaciado(String key, Object[] params){
		buffer.append(ComunesConst.ESPACIO);
		addMensaje(key, params);
	}

	public void addMensajeEnSiguienteLinea(String key, Object[] params){
		buffer.append(ComunesConst.SALTO_LINEA);
		addMensaje(key, params);
	}
	
	public void addTexto(String texto){
		buffer.append(texto);
	}

	public void addTextoEspaciado(String texto){
		buffer.append(ComunesConst.ESPACIO);
		addTexto(texto);
	}

	public void addTextoEnSiguienteLinea(String texto){
		buffer.append(ComunesConst.SALTO_LINEA);
		addTexto(texto);
	}	
	
	
	public void applyHeaderColor(){
		color = Color.CYAN;
	}
	
	/**
	 * Obtenerme
	 * Obtiene una instancia de la Celda con los datos que se fueron agregando
	 */
	public PdfPCell getMe(){
		PdfPCell cell = getCell();
		
		cell.setBackgroundColor(color);
		cell.setHorizontalAlignment(alineacionHorizontal);
		cell.setVerticalAlignment(alineacionVertical);
		cell.setColspan(colspan);
		//cell.setNoWrap(Boolean.TRUE);
		if (border!=null){
			cell.setBorder(border);
		}
		return cell;
	}

	private PdfPCell getCell() {
		Phrase phrase = new Phrase(buffer.toString());
		phrase.getFont().setSize(7);
		PdfPCell cell = null;
		if (element==null){
			cell = new PdfPCell(phrase);
		} else {
			if (element instanceof Image) {
				cell = new PdfPCell((Image)element);
			}
			if (element instanceof PdfPTable) {
				cell = new PdfPCell((PdfPTable)element);
			}
		}
		return cell;
	}	

}
