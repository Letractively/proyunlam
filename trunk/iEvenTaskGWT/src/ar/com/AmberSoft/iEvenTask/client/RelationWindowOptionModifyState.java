package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

public class RelationWindowOptionModifyState extends RelationWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.FROM_STATE, relationWindow.getFldFromState().getValue());
		params.put(ParamsConst.TO_STATE, relationWindow.getFldToState().getValue());
		if (relationWindow.getWindowState().equals(State.UPDATE_STATE)) {
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_EVENT_LDAP);
		} else {
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_LDAP);
		}
	}

	@Override
	public void setVisiblePanel() {
		relationWindow.getvPanelCreateTask().setVisible(Boolean.FALSE);
		relationWindow.getvPanelModifyState().show();
	}

	@Override
	public void beforeUpdate(Map actual) {
		relationWindow.setCombo(relationWindow.getFldFromState(),(String) actual.get(ParamsConst.FROM_STATE));
		relationWindow.setCombo(relationWindow.getFldToState(),(String) actual.get(ParamsConst.TO_STATE));
		setVisiblePanel();
	}

	@Override
	public Boolean isValid() {
		Boolean boolean1 = relationWindow.getFldFromState().isValid();
		Boolean boolean2 = relationWindow.getFldToState().isValid();
		return boolean1 && boolean2;
	}


}
