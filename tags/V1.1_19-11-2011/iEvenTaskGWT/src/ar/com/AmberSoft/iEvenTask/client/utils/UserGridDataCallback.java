package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.UserViewWindow;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;

public class UserGridDataCallback extends GridDataCallback {
	
	UserViewWindow window;

	@Override
	public void onSuccess(Object result) {
		super.onSuccess(result);
		List selection = new ArrayList();
		Iterator<String> it = window.getSelecteds().iterator();
		if (window.getSelecteds().size()<=0){
			Context.getInstance().addDetailExecution("UG - No hay seleccionados" );
		}
		while (it.hasNext()) {
			String actual = getActual(it.next());
			ModelData data = (ModelData)grid.searchModel(ParamsConst.ID, actual);
			if (data!=null){
				Context.getInstance().addDetailExecution("UG - Agregando " + data.get(ParamsConst.NAME));
				selection.add(data);

			} else {
				Context.getInstance().addDetailExecution("UG - No se encontro" + actual );
			}
		}
		grid.getSelectionModel().setSelection(selection);
	}

	public String getActual(Object object){
		if (object instanceof BaseModel) {
			BaseModel model = (BaseModel) object;
			return model.get(ParamsConst.ID);
		}
		return (String) object;
	}
	
	public UserGridDataCallback(UserViewWindow window, Grid grid) {
		super(grid);
		this.window = window;
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
