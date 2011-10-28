package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
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
	public static final Integer WINDOW_HEIGTH = 400;
	public static final Integer TASK_PANEL_WIDTH = WINDOW_WIDTH;
	
	
	private final Button btnView = new Button("Opciones de visibilidad");
	private Collection usersView = new ArrayList();
	
	FormPanel taskPanel = new FormPanel();
	@SuppressWarnings("rawtypes")
	List<Field> toValidate = new ArrayList<Field>();
	@SuppressWarnings("unused")
	private Boolean editing = Boolean.FALSE;
    TextField<String> taskName = new TextField<String>();
    DateField fecha_com = new DateField();  
    DateField fecha_fin = new DateField();  
    SpinnerField completed = new SpinnerField();  
    TextArea description = new TextArea();  
    //TextField<String> responsable = new TextField<String>();
    @SuppressWarnings("rawtypes")
	private final ComboBox fldUser = new ComboBox();
    Button btnGuardar = new Button("Guardar");
    Button btnModificar = new Button("Modificar");
    Button btnCancelar = new Button("Cancelar");  
    Integer id_tarea;
    Map<Object, Object> actual;
	
    private final ComboBox fldObjective = new ComboBox();
    
    
    
    
    
    /**
	 * @param guardar: boolean true para guardar / boolean false para modificar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskWindow(final boolean guardar) {
		super();
		setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
		
		Context.getInstance().addDetailExecution("TaskWindows 1");
		if(guardar){
			taskPanel.setHeading("Nueva Tarea");
		}else{
			taskPanel.setHeading("Modificar Tarea");
		}
		Context.getInstance().addDetailExecution("TaskWindows 2");
		taskPanel.setFrame(true);
		taskPanel.setWidth(TASK_PANEL_WIDTH);
		Context.getInstance().addDetailExecution("TaskWindows 3");
		taskName.setFieldLabel("Nombre");  
		taskName.setAllowBlank(false);  
		taskName.getFocusSupport().setPreviousId(taskPanel.getButtonBar().getId());  
		taskPanel.add(taskName);
		Context.getInstance().addDetailExecution("TaskWindows 4");
		fecha_com.setFieldLabel("Fecha Comienzo");  
		taskPanel.add(fecha_com);  
		fecha_fin.setFieldLabel("Fecha Fin");  
		taskPanel.add(fecha_fin);  
		Context.getInstance().addDetailExecution("TaskWindows 5");
		completed.setIncrement(1d);  
		completed.getPropertyEditor().setType(Integer.class);  
		completed.getPropertyEditor().setFormat(NumberFormat.getFormat("00"));  
		completed.setFieldLabel("Completado (%)");
		completed.setMinValue(0);  
		completed.setMaxValue(100);  
		taskPanel.add(completed);  
		Context.getInstance().addDetailExecution("TaskWindows 6");
		description.setPreventScrollbars(true);  
		description.setFieldLabel("Descripcion");  
		taskPanel.add(description);
		Context.getInstance().addDetailExecution("TaskWindows 7");
		addResponsable(guardar);
		Context.getInstance().addDetailExecution("TaskWindows 8");
		addObjectives(guardar);
		Context.getInstance().addDetailExecution("TaskWindows 9");
		taskPanel.add(btnView);
		btnView.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UserViewWindow modal = new UserViewWindow(usersView);
				modal.show();
			}
		});
		Context.getInstance().addDetailExecution("TaskWindows 10");
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
		Context.getInstance().addDetailExecution("TaskWindows 11");
		btnCancelar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cerrarVentana();}});
		taskPanel.addButton(btnCancelar);  
		Context.getInstance().addDetailExecution("TaskWindows 12");
	    this.add(taskPanel);
	    Context.getInstance().addDetailExecution("TaskWindows 13");
	}

	public void addResponsable(final boolean guardar) {
		fldUser.setFieldLabel("Responsable");
		fldUser.setEnabled(Boolean.FALSE);
		fldUser.setStore(new ListStore<ModelData>());
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_USERS);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Context.getInstance().validateUserExpired(caught);
						DialogFactory.error("No se han podido consultar los usuarios LDAP.");
					}

					@Override
					public void onSuccess(Object result) {
						if (!(((guardar) && (!Context.getInstance().isAvaiable(PermissionsConst.ASIGNAR_TAREAS))) ||
								((!guardar) && (!Context.getInstance().isAvaiable(PermissionsConst.REASIGNAR_TAREAS))))){
							fldUser.setEnabled(Boolean.TRUE);
						}
						Map map = (Map) result;
						Collection users = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = users.iterator();
						while (it.hasNext()) {
							Map actual = (Map) it.next();
							listStore.add(getModelData((String)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NAME)));
						}
						
						fldUser.setStore(listStore);
						String user = Context.getInstance().getUserID();
						if ((actual!=null) && (actual.get(ParamsConst.ID_USUARIO)!=null)){
							user = actual.get(ParamsConst.ID_USUARIO).toString();
						}
						setCombo(fldUser, user);
					}

				});
		fldUser.setEditable(Boolean.FALSE);
		fldUser.setTypeAhead(true);  
		fldUser.setTriggerAction(TriggerAction.ALL); 
		
		if (((guardar) && (!Context.getInstance().isAvaiable(PermissionsConst.ASIGNAR_TAREAS))) ||
			((!guardar) && (!Context.getInstance().isAvaiable(PermissionsConst.REASIGNAR_TAREAS)))){
			fldUser.setEnabled(Boolean.FALSE);
		}
		taskPanel.add(fldUser);
	}

	public void addObjectives(final boolean guardar) {
		fldObjective.setFieldLabel("Objetivo relacionado");
		fldObjective.setEnabled(Boolean.FALSE);
		fldObjective.setStore(new ListStore<ModelData>());
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_OBJECTIVE_WITH_VISIBLE_FILTER);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Context.getInstance().validateUserExpired(caught);
						DialogFactory.error("No se han podido consultar los objetivos relacionados.");
					}

					@Override
					public void onSuccess(Object result) {
						if (Context.getInstance().isAvaiable(PermissionsConst.RELACIONAR_CON_OBJETIVO)){
							fldObjective.setEnabled(Boolean.TRUE);
						}
						Map map = (Map) result;
						Collection objectives = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = objectives.iterator();
						while (it.hasNext()) {
							Map actual = (Map) it.next();
							Context.getInstance().addDetailExecution("Agregando " + actual.get(ParamsConst.ID).toString()+ "-" + actual.get(ParamsConst.NOMBRE_OBJETIVO));
							listStore.add(getModelData(actual.get(ParamsConst.ID).toString(), (String)actual.get(ParamsConst.NOMBRE_OBJETIVO)));
						}
						
						fldObjective.setStore(listStore);
						
						if (actual != null){
							Map objetivo = (Map) actual.get(ParamsConst.OBJETIVO);
							Context.getInstance().addDetailExecution("TaskWindow seteando objetivo");
							if (objetivo!=null){
								Context.getInstance().addDetailExecution("TaskWindow seteando el objetivo " + objetivo.get(ParamsConst.ID).toString());
								setCombo(fldObjective, objetivo.get(ParamsConst.ID).toString());
							} else {
								Context.getInstance().addDetailExecution("TaskWindow el objetivo es nulo");
							}
						}

					}

				});
		fldObjective.setEditable(Boolean.FALSE);
		fldObjective.setTypeAhead(true);  
		fldObjective.setTriggerAction(TriggerAction.ALL); 
		
		if (!(Context.getInstance().isAvaiable(PermissionsConst.RELACIONAR_CON_OBJETIVO))){			
			fldObjective.setEnabled(Boolean.FALSE);
		}
		taskPanel.add(fldObjective);
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
	 * @param valu
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
		maskAvaiable();
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_TAREA, taskName.getValue());
			params.put(ParamsConst.FECHA_COMIENZO, fecha_com.getValue());
			params.put(ParamsConst.FECHA_FIN, fecha_fin.getValue());
			params.put(ParamsConst.CUMPLIMIENTO, completed.getValue());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			if (fldUser.getValue()!=null){
				params.put(ParamsConst.ID_USUARIO, fldUser.getValue().get("key"));
			}
			if (fldObjective.getValue()!=null){
				params.put(ParamsConst.ID_OBJETIVO, fldObjective.getValue().get("key"));
			}
			setUsersVisibles(params);
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_TASK);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {

			@Override
			public void onFailure(Throwable caught) {
				maskDisable();
				DialogFactory.error("No pudo guardarse la tarea. Aguarde un momento y vuelva a intentarlo.");
			}

			@Override
			public void onSuccess(Object result) {
				maskDisable();
				DialogFactory.info("Se guardo la tarea con exito.");
				Context.getInstance().getTaskGrid().getStore().getLoader().load();
				cerrarVentana();
			}
			});
		} else {
			maskDisable();
		}
	}

	public void setUsersVisibles(Map<Object, Object> params) {
		Collection toSend = new ArrayList<String>();
		if (usersView!=null){
			Iterator<ModelData> users = usersView.iterator();
			while (users.hasNext()) {
				String id = "";
				Object actualUser = users.next();
				if (actualUser instanceof ModelData) {
					ModelData modelData = (ModelData) actualUser;
					id = modelData.get("id");
				} else {
					id = (String) actualUser;
				}
				toSend.add(id);
			}
			params.put(ParamsConst.USERS_VIEW, toSend);
		}
	}
	private void modificarTarea(){
		maskAvaiable();
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_TAREA, taskName.getValue());
			params.put(ParamsConst.FECHA_COMIENZO, fecha_com.getValue());
			params.put(ParamsConst.FECHA_FIN, fecha_fin.getValue());
			params.put(ParamsConst.CUMPLIMIENTO, completed.getValue());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			if (fldUser.getValue()!=null){
				params.put(ParamsConst.ID_USUARIO, fldUser.getValue().get("key"));
			}
			if (fldObjective.getValue()!=null){
				params.put(ParamsConst.ID_OBJETIVO, fldObjective.getValue().get("key"));
			}
			params.put(ParamsConst.ID, this.getId_tarea());
			
			setUsersVisibles(params);
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_TASK);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {
				
				@Override
				public void onFailure(Throwable caught) {
					maskDisable();
					DialogFactory.error("No pudo modificarse la tarea. Aguarde un momento y vuelva a intentarlo.");
				}
				
				@Override
				public void onSuccess(Object result) {
					maskDisable();
					DialogFactory.info("Se modifico la tarea con exito.");
					Context.getInstance().getTaskGrid().getStore().getLoader().load();
					cerrarVentana();
				}
			});
		} else {
			maskDisable();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void cerrarVentana(){
		this.close();
	}
	
	public void setValuesToUpdate(Map<Object, Object> actual){
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate inicio");
		this.actual = actual;
		this.setId_tarea(Integer.valueOf(actual.get(ParamsConst.ID).toString()));
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.1");
		taskName.setValue((String) actual.get(ParamsConst.NOMBRE_TAREA));
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.2");
		description.setValue((String) actual.get(ParamsConst.DESCRIPCION));
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.3");
		setCombo(fldUser, actual.get(ParamsConst.ID_USUARIO).toString());
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.4");
		fecha_com.setValue((Date) actual.get(ParamsConst.FECHA_COMIENZO));
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.5");
		fecha_fin.setValue((Date) actual.get(ParamsConst.FECHA_FIN));
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 1.6");
		if (actual.get(ParamsConst.CUMPLIMIENTO)!=null){
			try {
				completed.setValue(Long.valueOf(actual.get(ParamsConst.CUMPLIMIENTO).toString()));
			} catch (Exception e){
				Context.getInstance().addDetailExecution("TaskWindow cumplimiento debe ser Long (" + actual.get(ParamsConst.DURACION).toString() + ")");
			}
		}
		
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 3");
		Collection visibles = (Collection) actual.get(ParamsConst.VISIBLES);
		if (visibles!=null){
			Iterator<Map> itVisibles = visibles.iterator();
			while (itVisibles.hasNext()) {
				Map map = (Map) itVisibles.next();
				Context.getInstance().addDetailExecution("Agregando a usersView:"+map.get(ParamsConst.USUARIO));
				usersView.add(map.get(ParamsConst.USUARIO));
			}
		} else {
			Context.getInstance().addDetailExecution("Visibles es nulo");
		}
		Context.getInstance().addDetailExecution("TaskWindow setValuesToUpdate 4");
	}

	public Integer getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Integer id_tarea) {
		this.id_tarea = id_tarea;
	}
	
	public void maskAvaiable(){
		this.mask("Aguarde un momento...");
	}
	
	public void maskDisable(){
		this.unmask();
	}
	
}
