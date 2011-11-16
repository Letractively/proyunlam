package ar.com.AmberSoft.iEvenTask.reports;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public class Report extends HttpServletReporte {

	public static String CONTENT_TYPE = "application/pdf";
	
	public Report(String title, HttpServletResponse response, String contentType) {
		super(title, response, contentType);
	}

	@Override
	public DocWriter getDocWritter() throws DocumentException {
		return PdfWriter.getInstance(document, output);
	}

}
