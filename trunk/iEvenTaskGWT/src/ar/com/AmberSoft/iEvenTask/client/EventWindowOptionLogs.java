package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

@SuppressWarnings({"rawtypes","unchecked"})
public class EventWindowOptionLogs extends EventWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.PATH, eventWindow.getFldPathLogs().getValue());
		params.put(ParamsConst.PATERN, eventWindow.getFldPatern().getValue());		
		if (eventWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_EVENT_LOGS);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_LOGS);
		}

	}
	
	@Override
	public void setVisiblePanel() {
		eventWindow.getvPanelLDAP().setVisible(Boolean.FALSE);
		eventWindow.getvPanelArchivos().setVisible(Boolean.FALSE);
		eventWindow.getvPanelServicios().setVisible(Boolean.FALSE);
		eventWindow.getvPanelPatron().show();
	}

	@Override
	public void beforeUpdate(Map actual) {
		eventWindow.getFldPathLogs().setValue((String)actual.get(ParamsConst.PATH));
		eventWindow.getFldPatern().setValue((String)actual.get(ParamsConst.PATERN));
		eventWindow.setCombo(eventWindow.getFldType(), (String)actual.get(ParamsConst.CLASS));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		Boolean valid1 = eventWindow.getFldPathLogs().isValid();
		Boolean valid2 = eventWindow.getFldPatern().isValid();
		return valid1 && valid2;
	}


}
