package ar.com.AmberSoft.iEvenTask.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;
import ar.com.AmberSoft.iEvenTask.services.ListService;
import ar.com.AmberSoft.iEvenTask.services.Service;
import ar.com.AmberSoft.iEvenTask.shared.exceptions.ServiceClassNotFoundException;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

import com.lowagie.text.Element;

public class ReportDispatcher extends HttpServlet {

	public static final String SERVICIO = "Servicio2";
	public static String FILTERS = "filters";
	
	private static Logger logger = Logger.getLogger(ReportDispatcher.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			elaborarReporte(req, resp);
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			elaborarReporte(req, resp);
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
	}
	
	public Object getValue(Object objeto, String key){
		try {
			return PropertyUtils.getProperty(objeto, key);
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
		return null;
	}

	private static Class getType(Map params) {
		String serviceName = (String) params.get(SERVICIO);
		Class type;
		try {
			type = Class.forName(serviceName);
		} catch (ClassNotFoundException e) {
			logger.error(Tools.getStackTrace(e));
			throw new ServiceClassNotFoundException();
		}
		return type;
	}
	
	protected void elaborarReporte(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException, InstantiationException, IllegalAccessException {
		
		Map params = (Map) req.getSession().getAttribute(ParamsConst.PARAMS);
		
		Service service = (Service) getType(params).newInstance();
		
		Collection columnsModels = (Collection) params.get(ParamsConst.COLUMN_MODEL);
		Report report = new Report((String)params.get(ParamsConst.TITLE), response, Report.CONTENT_TYPE);
		
		TablaPDF tabla = new TablaPDF(report.getDocument());
		List<Float> tamanios = new ArrayList();
		Integer columnas = 0;
		if (columnsModels!=null){
			Iterator itDefiniciones = columnsModels.iterator();
			while (itDefiniciones.hasNext()) {
				Object define = (Object) itDefiniciones.next();
				Boolean hidden = (Boolean) getValue(define, "hidden");
				if (!hidden){
					CeldaPDF celda = new CeldaPDF();
					celda.addTexto((String) getValue(define, "name"));
					celda.applyHeaderColor();
					celda.setAlineacionHorizontal(Element.ALIGN_CENTER);
					celda.setAlineacionVertical(Element.ALIGN_MIDDLE);
					tabla.addCelda(celda);
					tamanios.add(new Float((Integer)getValue(define, "size") * 30) );
					columnas++;
				}
			}
		}
		
		tabla.setTamanioColumnas(tamanios);
		tabla.setColumnas(columnas);
		
		
		try {
			params.put(ParamsConst.REQUEST, req);
			params.put(ListService.LIMIT, null);
			params.put(ListService.OFFSET, null);
			Map result = service.execute(params);
			Collection datas = (Collection) result.get(ParamsConst.DATA);
			
			if (datas!=null){
				Iterator itDatas = datas.iterator();
				int i=0;
				while (itDatas.hasNext()) {
					i++;
					if (i>=25){
						tabla.flush();
						tabla = new TablaPDF(report.getDocument());
						tabla.setTamanioColumnas(tamanios);
						tabla.setColumnas(columnas);
						
						report.getDocument().newPage();
						i=0;
						if (columnsModels!=null){
							Iterator itDefiniciones = columnsModels.iterator();
							while (itDefiniciones.hasNext()) {
								Object define = (Object) itDefiniciones.next();
								Boolean hidden = (Boolean) getValue(define, "hidden");
								if (!hidden){
									CeldaPDF celda = new CeldaPDF();
									celda.addTexto((String) getValue(define, "name"));
									celda.applyHeaderColor();
									celda.setAlineacionHorizontal(Element.ALIGN_CENTER);
									celda.setAlineacionVertical(Element.ALIGN_MIDDLE);
									tabla.addCelda(celda);
								}
							}
						}
					}
					Object element = itDatas.next();
					if (columnsModels!=null){
						Iterator itDefiniciones = columnsModels.iterator();
						while (itDefiniciones.hasNext()) {
							Object define = (Object) itDefiniciones.next();
							Boolean hidden = (Boolean) getValue(define, "hidden");
							if (!hidden){
								Object value = PropertyUtils.getProperty(element, (String)getValue(define, "id"));
								
								CeldaPDF celda = new CeldaPDF();
								if (value!=null){
									celda.addTexto(value.toString());
								}
								Integer alignment = (Integer)getValue(define, "horizontalAlignment");
								if (alignment!=null){
									celda.setAlineacionHorizontal(alignment);
								} else {
									celda.setAlineacionHorizontal(Element.ALIGN_LEFT);
								}
								celda.setAlineacionVertical(Element.ALIGN_MIDDLE);
	
								tabla.addCelda(celda);
							}
						}
					}
					
					
				}
			}
			
			tabla.flush();
			
		} catch (Exception e1) {
			logger.error(Tools.getStackTrace(e1));
		}
		
		report.flush();
	}
	
	
	
}
