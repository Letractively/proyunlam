package ar.com.AmberSoft.iEvenTask.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseModel;

public abstract class RelationWindowOption {
	
	public static final String CREATE_TASK = "ar.com.AmberSoft.iEvenTask.backend.entities.RelationWithActionCreateTask";
	public static final String MODIFY_STATE = "ar.com.AmberSoft.iEvenTask.client.ReelationWindowOptionModifyState";

	protected RelationWindow relationWindow;

	public RelationWindow getRelationWindow() {
		return relationWindow;
	}

	public void setRelationWindow(RelationWindow relationWindow) {
		this.relationWindow = relationWindow;
	}
	
	public abstract void onSave(Map params);

	public abstract void setVisiblePanel();

	public abstract void beforeUpdate(Map actual);

	public abstract Boolean isValid();

	protected void setId(Map params) {
		List seleccionados = relationWindow.getGrid().getSelectionModel().getSelection();
		if (seleccionados.size() == 1) {
			Iterator it = seleccionados.iterator();
			if (it.hasNext()) {
				BaseModel baseModel = (BaseModel) it.next();
				params.put(ParamsConst.ID, baseModel.get(ParamsConst.ID));
			}
		}
	}
	
	
	
}
