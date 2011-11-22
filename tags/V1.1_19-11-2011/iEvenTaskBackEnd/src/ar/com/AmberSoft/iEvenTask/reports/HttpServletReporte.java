package ar.com.AmberSoft.iEvenTask.reports;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;


public abstract class HttpServletReporte extends Reporte {
	
	private static Logger logger = Logger.getLogger(HttpServletReporte.class);
	
	private HttpServletResponse response;
	private String contentType;

	public HttpServletReporte(String title, HttpServletResponse response, String contentType) {
		super(title, null);
		try {
			this.response = response;
			this.stream = response.getOutputStream();
			this.contentType = contentType; 
			abrirDocumento();
		} catch (Exception e){
			logger.error(Tools.getStackTrace(e));
		}
	}
	
	public void flush() {
		response.resetBuffer();
		response.setContentType(contentType);
		try {
			stream = response.getOutputStream();
		} catch (IOException e) {
			logger.error(Tools.getStackTrace(e));
		}
		super.flush();
	}


	
}
