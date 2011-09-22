package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import com.extjs.gxt.ui.client.data.ModelData;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

public class EventWindowOptionFiles extends EventWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.CONTROL_TYPE, ((ModelData)eventWindow.getFldControlType().getValue()).get("key"));
		params.put(ParamsConst.PATH, eventWindow.getFldPath().getValue());
		if (eventWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_EVENT_FILES);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_FILES);
		}
	}
	
	@Override
	public void setVisiblePanel() {
		eventWindow.getvPanelLDAP().setVisible(Boolean.FALSE);
		eventWindow.getvPanelPatron().setVisible(Boolean.FALSE);
		eventWindow.getvPanelArchivos().show();
		eventWindow.getvPanelServicios().setVisible(Boolean.FALSE);
	}

	@Override
	public void beforeUpdate(Map actual) {
		eventWindow.setCombo(eventWindow.getFldControlType(), actual.get(ParamsConst.CONTROL_TYPE).toString());
		eventWindow.getFldPath().setValue((String) actual.get(ParamsConst.PATH));
		eventWindow.setCombo(eventWindow.getFldType(), (String)actual.get(ParamsConst.CLASS));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		Boolean valid1 = eventWindow.getFldControlType().isValid();
		Boolean valid2 = eventWindow.getFldPath().isValid();
		return valid1 && valid2;
	}


}
