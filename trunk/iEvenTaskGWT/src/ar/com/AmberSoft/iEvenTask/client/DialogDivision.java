package ar.com.AmberSoft.iEvenTask.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.menu.MainTabTareas;
import ar.com.AmberSoft.iEvenTask.client.resources.Resources;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SpinnerField;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DialogDivision extends Dialog {

	private MainTabTareas mainTabTareas;
	private SpinnerField cantidad;
	
	public DialogDivision(String text, MainTabTareas mainTabTareas) {
		super();
		this.mainTabTareas = mainTabTareas;
		 setHeading("iEvenTask - Division de tareas");
		 addText("Se dividira la tarea " + text);
		 
		 cantidad = new SpinnerField();  
		 cantidad.getPropertyEditor().setType(Integer.class);  
		 cantidad.getPropertyEditor().setFormat(NumberFormat.getFormat("00"));  
		 cantidad.setFieldLabel("Cantidad");
		 cantidad.setMinValue(2);  
		 cantidad.setMaxValue(100);  
		 
		 add(cantidad);
			
		 setBodyStyle("fontWeight:bold;padding:13px;");
		 setSize(350, 150);
		 setHideOnButtonClick(true);
		 setButtons(Dialog.OK);
		 setIcon(Resources.ICONS.info());
		 show();
	}
	
	  /**
	   * Called after a button in the button bar is selected. If
	   * {@link #setHideOnButtonClick(boolean)} is true, hides the dialog
	   * when any button is pressed.
	   * 
	   * @param button the button
	   */
	  protected void onButtonPressed(Button button) {
		  super.onButtonPressed(button);
		  
		  List seleccionados = mainTabTareas.grid.getSelectionModel().getSelectedItems();
		  if (seleccionados != null){
			  ModelData actual = (ModelData) seleccionados.iterator().next();
			  Map params = new HashMap();
			  params.put(ParamsConst.CANTIDAD, cantidad.getValue());
			  params.put(ParamsConst.ID, actual.get(ParamsConst.ID));
			  params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DIVIDE_TASK);
				DispatcherUtil.getDispatcher().execute(params,
						new AsyncCallback() {

							@Override
							public void onFailure(Throwable caught) {
								DialogFactory.error("No se ha dividir la tarea.");
							}

							@Override
							public void onSuccess(Object result) {
								mainTabTareas.grid.getStore().getLoader().load();
							}

						});
			  
		  }
		  
		  
	  }
	
}
