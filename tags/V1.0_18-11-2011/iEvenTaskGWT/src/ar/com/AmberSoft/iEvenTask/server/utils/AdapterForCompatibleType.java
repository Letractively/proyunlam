package ar.com.AmberSoft.iEvenTask.server.utils;

public class AdapterForCompatibleType implements Compatibilizable {

	@Override
	public Object adapt(Object actual) {
		return actual;
	}

}
