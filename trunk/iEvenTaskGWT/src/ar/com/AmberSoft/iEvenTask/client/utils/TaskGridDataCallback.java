package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.TaskSelectWindow;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;

public class TaskGridDataCallback extends GridDataCallback {
	
	TaskSelectWindow window;
	
	public TaskGridDataCallback(TaskSelectWindow window, Grid grid) {
		super(grid);
		this.window = window;
	}


	

	@Override
	public void onSuccess(Object result) {
		super.onSuccess(result);
		List selection = new ArrayList();
		Iterator<String> it = window.getSelecteds().iterator();
		if (window.getSelecteds().size()<=0){
			Context.getInstance().addDetailExecution("TG - No hay seleccionados" );
		}
		while (it.hasNext()) {
			Integer actual = getActual(it.next());
			ModelData data = (ModelData)grid.searchModel(ParamsConst.ID, actual);
			if (data!=null){
				Context.getInstance().addDetailExecution("TG - Agregando " + data.get(ParamsConst.NAME));
				selection.add(data);

			} else {
				Context.getInstance().addDetailExecution("TG - No se encontro" + actual );
			}
		}
		grid.getSelectionModel().setSelection(selection);
	}

	public Integer getActual(Object object){
		if (object instanceof BaseModel) {
			BaseModel model = (BaseModel) object;
			return model.get(ParamsConst.ID);
		}
		return (Integer) object;
	}
	
	
	
	/**
	 * Retorna un Model Data Basico
	 * Usualmente utilizado en los combos
	 * @param key
	 * @param value
	 * @return
	 */
	protected BaseModel getModelData(String key, String value) {
		BaseModel baseModel = new BaseModel();
		baseModel.set("id", key);
		baseModel.set("name", value);
		return baseModel;
	}

}
