package ar.com.AmberSoft.iEvenTask.reports;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public class ReportePDF extends HttpServletReporte {
	
	public static String CONTENT_TYPE = "application/pdf";

	public ReportePDF(HttpServletResponse response) {
		super("", response, CONTENT_TYPE);
	}

	public DocWriter getDocWritter() throws DocumentException {
		return PdfWriter.getInstance(document, output);
	}

	
	
}
