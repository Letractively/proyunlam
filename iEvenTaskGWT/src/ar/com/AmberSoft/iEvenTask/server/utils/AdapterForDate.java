package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.Date;

public class AdapterForDate implements Compatibilizable {

	@Override
	public Object adapt(Object actual) {
		
		if (actual instanceof Date) {
			Date date = (Date) actual;
			return date.getTime();
		}
		
		return null;
	}

}
