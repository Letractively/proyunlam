package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

public class RelationWindowOptionCreateTask extends RelationWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.NAME, relationWindow.getFldName().getValue());
		params.put(ParamsConst.USER, relationWindow.getFldName().getValue());
		if (relationWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_RELATION_CREATE_TASK);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_RELATION_CREATE_TASK);
		}

	}

	@Override
	public void setVisiblePanel() {
		relationWindow.getvPanelCreateTask().show();
		relationWindow.getvPanelModifyState().setVisible(Boolean.FALSE);
	}

	@Override
	public void beforeUpdate(Map actual) {
		relationWindow.getFldName().setValue(actual.get(ParamsConst.NAME));
		relationWindow.getFldUser().setValue(actual.get(ParamsConst.USER));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		Boolean boolean1 = relationWindow.getFldName().isValid();
		Boolean boolean2 = relationWindow.getFldUser().isValid();
		return boolean1 && boolean2;
	}

}
