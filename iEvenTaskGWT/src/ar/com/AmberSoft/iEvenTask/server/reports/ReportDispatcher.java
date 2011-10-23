package ar.com.AmberSoft.iEvenTask.server.reports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;

import com.lowagie.text.DocWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

public class ReportDispatcher extends HttpServlet {

	private static Logger logger = Logger.getLogger(ReportDispatcher.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		elaborarReporte(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		elaborarReporte(req, resp);
	}

	protected void elaborarReporte(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		Report report = new Report(response, Report.CONTENT_TYPE);
		Phrase phrase = new Phrase("Generacion de PDFs para reportes");
		HeaderFooter headerFooter = new HeaderFooter(phrase, Boolean.TRUE);
		report.getDocument().setHeader(headerFooter);
				
		try {
			report.getDocument().add(new Paragraph("Prueba de escritura"));
		} catch (DocumentException e) {
			logger.error(Tools.getStackTrace(e));
		}
				
		//report.getDocument().setPageCount(1);
		
		report.flush();
	}
	
	
	
}
