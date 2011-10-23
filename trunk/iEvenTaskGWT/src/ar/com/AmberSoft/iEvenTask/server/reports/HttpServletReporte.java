package ar.com.AmberSoft.iEvenTask.server.reports;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.utils.Tools;


public abstract class HttpServletReporte extends Reporte {
	
	private static Logger logger = Logger.getLogger(HttpServletReporte.class);
	
	private HttpServletResponse response;
	private String contentType;

	public HttpServletReporte(HttpServletResponse response, String contentType) {
		super(null);
		try {
			this.response = response;
			this.stream = response.getOutputStream();
			this.contentType =contentType; 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.flush();
	}


	
}
