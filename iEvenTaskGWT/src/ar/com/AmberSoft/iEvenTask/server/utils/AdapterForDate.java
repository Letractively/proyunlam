package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.Date;

// FIXME: Este adaptador no es necesario, quitarlo y ver que logicas se ven afectadas
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
