package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.util.ParamsConst;

public class UnLockService extends Service {

	@Override
	public Map onExecute(Map params) {
		EntitiesManager.unlock(params.get(ParamsConst.ID));
		return null;
	}

	@Override
	public Map onEmulate(Map params) {
		return onExecute(params);
	}

}
