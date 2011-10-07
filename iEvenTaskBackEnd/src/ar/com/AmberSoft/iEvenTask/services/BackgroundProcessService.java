package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.events.BackgroundEventController;
import ar.com.AmberSoft.iEvenTask.events.BackgroundEventDetectFilesProcess;
import ar.com.AmberSoft.iEvenTask.events.BackgroundEventDetectProcess;
import ar.com.AmberSoft.util.ParamsConst;

public class BackgroundProcessService extends Service {

	@Override
	public Map onExecute(Map params) {
		
		BackgroundEventController controller = BackgroundEventController.getInstance();

		Map map = new HashMap();
		map.put(ParamsConst.PROCESS, controller.getActiveProcesses());
		
		return map;
	}

	@Override
	public Map onEmulate(Map params) {
		Map<Integer, BackgroundEventDetectProcess> activeProcesses = new HashMap<Integer, BackgroundEventDetectProcess>();
		activeProcesses.put(1, new BackgroundEventDetectFilesProcess(null));

		Map map = new HashMap();
		map.put(ParamsConst.PROCESS, activeProcesses);
		
		return map;

	}

}
