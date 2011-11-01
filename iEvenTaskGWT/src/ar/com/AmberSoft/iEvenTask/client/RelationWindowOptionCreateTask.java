package ar.com.AmberSoft.iEvenTask.client;

import java.util.Iterator;
import java.util.Map;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;
@SuppressWarnings({"rawtypes","unchecked"})
public class RelationWindowOptionCreateTask extends RelationWindowOption {

	@Override
	public void onSave(Map params) {
		params.put(ParamsConst.NAME, relationWindow.getFldName().getValue());
		params.put(ParamsConst.USER, ((ModelData)relationWindow.getFldUser().getValue()).get("key"));
		if (relationWindow.getWindowState().equals(State.UPDATE_STATE)) { 
			Context.getInstance().addDetailExecution("Invocando a UPDATE_RELATION_CREATE_TASK");
			setId(params);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_RELATION_CREATE_TASK);
		} else {
			Context.getInstance().addDetailExecution("Invocando a CREATE_RELATION_CREATE_TASK");
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_RELATION_CREATE_TASK);
		}

	}

	@Override
	public void beforeUpdate(Map actual) {
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - beforeUpdate - 1");
		relationWindow.getFldName().setValue(actual.get(ParamsConst.NAME));
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - beforeUpdate - 2");
		
		//relationWindow.getFldUser().setValue(actual.get(ParamsConst.USER));
		setCombo(relationWindow.getFldUser(), (String)actual.get(ParamsConst.USER));
		
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - beforeUpdate - 3");
		setVisiblePanel();
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - beforeUpdate - 4");
	}
	
	/**
	 * Setea el valor seleccionado de un combo
	 * @param comboBox
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	protected void setCombo(ComboBox comboBox, String key) {
		ListStore<ModelData> listStore = comboBox.getStore();
		ModelData modelData = null;
		Iterator it = listStore.getModels().iterator();
		while (it.hasNext()) {
			ModelData mdAux = (ModelData) it.next();
			//Context.getInstance().addDetailExecution("Comparando " + key + " con " + mdAux.get("key"));
			if (key.trim().equals(mdAux.get("key").toString().trim())){
				modelData = mdAux;
				break;
			}
		}
		comboBox.setValue(modelData);
	}

	
	@Override
	public void setVisiblePanel() {
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - setVisiblePanel - 1");
		relationWindow.getvPanelCreateTask().show();
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - setVisiblePanel - 2");
		relationWindow.getvPanelModifyState().setVisible(Boolean.FALSE);
		Context.getInstance().addDetailExecution("RelationWindowOptionCreateTask - setVisiblePanel - 3");
	}

	@Override
	public Boolean isValid() {
		Boolean boolean1 = relationWindow.getFldName().isValid();
		Boolean boolean2 = relationWindow.getFldUser().isValid();
		return boolean1 && boolean2;
	}

	@Override
	public Boolean clear() {
		relationWindow.getFldName().clear();
		relationWindow.getFldUser().clear();
		return null;
	}

}
