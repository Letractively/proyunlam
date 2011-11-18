package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

public class ComentarioComparetor implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		Map comentario0 = (Map) arg0;
		Map comentario1 = (Map) arg1;
		Date fecha0 = (Date) comentario0.get(ParamsConst.FECHA);
		Date fecha1 = (Date) comentario0.get(ParamsConst.FECHA);
		return fecha0.compareTo(fecha1);
	}

}
