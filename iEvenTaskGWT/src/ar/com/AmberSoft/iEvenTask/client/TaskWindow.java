package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.menu.MainTabTareas;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SpinnerField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TaskWindow extends Window {
	
	public static final Integer WINDOW_WIDTH = 400;
	public static final Integer WINDOW__HEIGTH = 340;
	public static final Integer TASK_PANEL_WIDTH = WINDOW_WIDTH;
	
	FormPanel taskPanel = new FormPanel();
	@SuppressWarnings("rawtypes")
	List<Field> toValidate = new ArrayList<Field>();
	@SuppressWarnings("unused")
	private Boolean editing = Boolean.FALSE;
    TextField<String> taskName = new TextField<String>();
    DateField fecha_com = new DateField();  
    DateField fecha_fin = new DateField();  
    SpinnerField duration = new SpinnerField();  
    TextArea description = new TextArea();  
    //TextField<String> responsable = new TextField<String>();
    @SuppressWarnings("rawtypes")
	private final ComboBox fldUser = new ComboBox();
    Button btnGuardar = new Button("Guardar");
    Button btnModificar = new Button("Modificar");
    Button btnCancelar = new Button("Cancelar");  
    Integer id_tarea;
    Map<Object, Object> actual;
	
    /**
	 * @param guardar: boolean true para guardar / boolean false para modificar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskWindow(boolean guardar) {
		super();
		setSize(WINDOW_WIDTH, WINDOW__HEIGTH);
		
		if(guardar){
			taskPanel.setHeading("Nueva Tarea");
		}else{
			taskPanel.setHeading("Modificar Tarea");
		}
		taskPanel.setFrame(true);
		taskPanel.setWidth(TASK_PANEL_WIDTH);
		
		taskName.setFieldLabel("Nombre");  
		taskName.setAllowBlank(false);  
		taskName.getFocusSupport().setPreviousId(taskPanel.getButtonBar().getId());  
		taskPanel.add(taskName);

		fecha_com.setFieldLabel("Fecha Comienzo");  
		taskPanel.add(fecha_com);  
		fecha_fin.setFieldLabel("Fecha Fin");  
		taskPanel.add(fecha_fin);  

		duration.setIncrement(1d);  
		duration.getPropertyEditor().setType(Double.class);  
		duration.getPropertyEditor().setFormat(NumberFormat.getFormat("00"));  
		duration.setFieldLabel("Duracion (hs)");
		duration.setMinValue(-100d);  
		duration.setMaxValue(100d);  
		taskPanel.add(duration);  

		description.setPreventScrollbars(true);  
		description.setFieldLabel("Descripcion");  
		taskPanel.add(description);
		
		fldUser.setStore(new ListStore<ModelData>());
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_USERS);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display(
								"iEvenTask",
								"No se han podido consultar los usuarios LDAP.");
					}

					@Override
					public void onSuccess(Object result) {
						Map map = (Map) result;
						Collection users = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = users.iterator();
						while (it.hasNext()) {
							Map actual = (Map) it.next();
							listStore.add(getModelData((String)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NAME)));
						}
						
						fldUser.setStore(listStore);
						String user = Context.getInstance().getUsuario();
						if ((actual!=null) && (actual.get(ParamsConst.ID_USUARIO)!=null)){
							user = actual.get(ParamsConst.ID_USUARIO).toString();
						}
						setCombo(fldUser, user);
					}

				});
		fldUser.setEditable(Boolean.FALSE);
		fldUser.setTypeAhead(true);  
		fldUser.setTriggerAction(TriggerAction.ALL); 
		taskPanel.add(fldUser);
		/*responsable.setFieldLabel("Responsable");  
		responsable.setAllowBlank(false);  
		responsable.setValue(Context.getInstance().getUsuario());
		responsable.getFocusSupport().setPreviousId(taskPanel.getButtonBar().getId());  
		taskPanel.add(responsable);*/
		
		FormButtonBinding binding = new FormButtonBinding(taskPanel);  
		if(guardar){
			btnGuardar.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					guardarTarea();}});
			taskPanel.addButton(btnGuardar);
			binding.addButton(btnGuardar);  
		}else{
			btnModificar.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					modificarTarea();}});
			taskPanel.addButton(btnModificar);
			binding.addButton(btnModificar);  
		}
		
		btnCancelar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cerrarVentana();}});
		taskPanel.addButton(btnCancelar);  
	    
	    this.add(taskPanel);
	}
	
	/**
	 * Setea el valor seleccionado de un combo
	 * @param comboBox
	 * @param key
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setCombo(ComboBox comboBox, String key) {
		ListStore<ModelData> listStore = comboBox.getStore();
		ModelData modelData = null;
		if (listStore!=null){
			Iterator it = listStore.getModels().iterator();
			while (it.hasNext()) {
				ModelData mdAux = (ModelData) it.next();
				Context.getInstance().addDetailExecution("Comparando " + key + " con " + mdAux.get("key"));
				if (key.equals(mdAux.get("key"))){
					modelData = mdAux;
				}
			}
		}
		comboBox.setValue(modelData);
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
		baseModel.set("key", key);
		baseModel.set("text", value);
		return baseModel;
	}

	@SuppressWarnings("rawtypes")
	public Boolean isValid(){
		Boolean valid = Boolean.TRUE;
		Iterator it = toValidate.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			if (!field.isValid()){
				valid = Boolean.FALSE;
			}
		}
		return valid;
	}
	
	private void guardarTarea(){
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_TAREA, taskName.getValue());
			params.put(ParamsConst.FECHA_COMIENZO, fecha_com.getValue());
			params.put(ParamsConst.FECHA_FIN, fecha_fin.getValue());
			params.put(ParamsConst.DURACION, duration.getValue().toString());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			params.put(ParamsConst.ID_USUARIO, fldUser.getValue().get("key"));
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_TASK);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("iEvenTask", "No pudo guardarse la tarea. Aguarde un momento y vuelva a intentarlo.");
			}

			@Override
			public void onSuccess(Object result) {
				Info.display("iEvenTask", "Se guardo la tarea con exito.");
				//FIXME: Invocar a la primer pagina
				//refreshGrid(grid);
				cerrarVentana();
			}
			});
		}
	}
	private void modificarTarea(){
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_TAREA, taskName.getValue());
			params.put(ParamsConst.FECHA_COMIENZO, fecha_com.getValue());
			params.put(ParamsConst.FECHA_FIN, fecha_fin.getValue());
			params.put(ParamsConst.DURACION, duration.getValue().toString());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			params.put(ParamsConst.ID_USUARIO, fldUser.getValue().get("key"));
			params.put(ParamsConst.ID, this.getId_tarea());
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_TASK);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {
				
				@Override
				public void onFailure(Throwable caught) {
					Info.display("iEvenTask", "No pudo modificarse la tarea. Aguarde un momento y vuelva a intentarlo.");
				}
				
				@Override
				public void onSuccess(Object result) {
					Info.display("iEvenTask", "Se modifico la tarea con exito.");
					cerrarVentana();
					//MainTabTareas.reloadGrid();
					
				}
			});
		}
	}
	
	@SuppressWarnings("deprecation")
	private void cerrarVentana(){
		this.close();
	}
	
	public void setValuesToUpdate(Map<Object, Object> actual){
		this.actual = actual;
		this.setId_tarea(Integer.valueOf(actual.get(ParamsConst.ID).toString()));
		taskName.setValue(actual.get(ParamsConst.NOMBRE_TAREA).toString());
		description.setValue(actual.get(ParamsConst.DESCRIPCION).toString());
		setCombo(fldUser, actual.get(ParamsConst.ID_USUARIO).toString());
		fecha_com.setValue((Date) actual.get(ParamsConst.FECHA_COMIENZO));
		fecha_fin.setValue((Date) actual.get(ParamsConst.FECHA_FIN));
		duration.setValue(Long.valueOf(actual.get(ParamsConst.DURACION).toString()));
	}

	public Integer getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Integer id_tarea) {
		this.id_tarea = id_tarea;
	}
	
}
