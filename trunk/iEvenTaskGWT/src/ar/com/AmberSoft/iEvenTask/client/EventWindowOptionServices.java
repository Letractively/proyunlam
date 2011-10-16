package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

@SuppressWarnings({"rawtypes","unchecked"})
public class EventWindowOptionServices extends EventWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.HOST, eventWindow.getFldHost().getValue());
		params.put(ParamsConst.PORT, eventWindow.getFldPort().getValue());
		if (eventWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_EVENT_SERVICES);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_SERVICES);
		}

	}
	
	@Override
	public void setVisiblePanel() {
		eventWindow.getvPanelLDAP().setVisible(Boolean.FALSE);
		eventWindow.getvPanelPatron().setVisible(Boolean.FALSE);
		eventWindow.getvPanelArchivos().setVisible(Boolean.FALSE);
		eventWindow.getvPanelServicios().show();
	}

	@Override
	public void beforeUpdate(Map actual) {
		eventWindow.getFldHost().setValue((String) actual.get(ParamsConst.HOST));
		eventWindow.getFldPort().setValue(actual.get(ParamsConst.PORT).toString());
		eventWindow.setCombo(eventWindow.getFldType(), (String)actual.get(ParamsConst.CLASS));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		Boolean valid1 = eventWindow.getFldHost().isValid();
		Boolean valid2 = eventWindow.getFldPort().isValid();
		return valid1 && valid2;
	}


}
