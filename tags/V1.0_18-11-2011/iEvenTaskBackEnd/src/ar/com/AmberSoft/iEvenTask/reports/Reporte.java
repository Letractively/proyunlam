package ar.com.AmberSoft.iEvenTask.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;

import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;

public abstract class Reporte {
	
	private static Logger logger = Logger.getLogger(Reporte.class);

	protected DocWriter writter;
	protected OutputStream stream;
	protected Document document;
	protected ByteArrayOutputStream output;
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Reporte(String title, OutputStream stream){
		this.title = title;
		Float margin = new Float(30);
		document = new Document(PageSize.A4.rotate(), margin, margin, margin, margin);
		//document = new Document(PageSize.A4.rotate());
		output = new ByteArrayOutputStream();
		try {
			this.writter = getDocWritter();
		} catch (DocumentException e) {
			logger.error(Tools.getStackTrace(e));
		}
	}

	public void abrirDocumento() {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
		
		  // headers and footers must be added before the document is opened
        HeaderFooter footer = new HeaderFooter(
                    new Phrase("Pagina número: ", new Font(bf)), true);
        footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);

        HeaderFooter header = new HeaderFooter(
                    new Phrase(title, new Font(bf)), false);
        header.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header);
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
