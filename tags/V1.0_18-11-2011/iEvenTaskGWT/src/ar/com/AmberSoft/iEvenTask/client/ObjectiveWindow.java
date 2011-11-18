package ar.com.AmberSoft.iEvenTask.client;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.user.client.rpc.AsyncCallback;
  
public class ObjectiveWindow extends Window implements Seleccionable {
	
	public static final Integer WINDOW_WIDTH = 500;
	public static final Integer WINDOW_HEIGTH = 550;
	public static final Integer OBJECTIVE_PANEL_WIDTH = 500;
	
	private final Button btnView = new Button("Opciones de visibilidad");
	private Collection usersView = new ArrayList();
	
	FormPanel objPanel = new FormPanel();
	@SuppressWarnings("rawtypes")
	List<Field> toValidate = new ArrayList<Field>();
	@SuppressWarnings("unused")
	private Boolean editing = Boolean.FALSE;
    TextField<String> objName = new TextField<String>();
    TextField<String> objType = new TextField<String>();
    NumberField objPond = new NumberField();
    
    NumberField cumplimiento = new NumberField();
    
    TextField<String> objScale = new TextField<String>();
    DateField fecha_finalizacion = new DateField();  
    TextArea description = new TextArea();  
    
    @SuppressWarnings("rawtypes")
	private final ComboBox fldUser = new ComboBox();
    
//    TextField<String> usuario_asignado = new TextField<String>();
    Button btnGuardar = new Button("Guardar");
    Button btnModificar = new Button("Modificar");
    Button btnCancelar = new Button("Cancelar");  
    Integer id_obj;
    Map<Object, Object> actual;
    
    private Grid grid = null;
    
    private HorizontalPanel panel = new HorizontalPanel();
	
    /**
	 * @param guardar: boolean true para guardar / boolean false para modificar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ObjectiveWindow(boolean guardar) {
		super();
		Context.getInstance().addDetailExecution("ObjectiveWindow 1");
		setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
		setResizable(false);
		Context.getInstance().addDetailExecution("ObjectiveWindow 2");
		if(guardar){
			objPanel.setHeading("Nuevo Objetivo");
		}else{
			objPanel.setHeading("Modificar Objetivo");
		}
		Context.getInstance().addDetailExecution("ObjectiveWindow 3");
		objPanel.setFrame(true);
		objPanel.setWidth(OBJECTIVE_PANEL_WIDTH);
		Context.getInstance().addDetailExecution("ObjectiveWindow 4");
		objName.setFieldLabel("Nombre");  
		objName.setAllowBlank(false);  
		objName.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());
		objName.setMaxLength(30);
		objPanel.add(objName);
		Context.getInstance().addDetailExecution("ObjectiveWindow 5");
		objType.setFieldLabel("Tipo");  
		objType.setAllowBlank(false);  
		objType.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());
		objType.setMaxLength(20);
		objPanel.add(objType);
		Context.getInstance().addDetailExecution("ObjectiveWindow 6");
		objPond.setPropertyEditorType(Integer.class);
		objPond.setFieldLabel("Ponderacion");  
		objPond.setAllowBlank(false);  
		objPond.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPond.setMinValue(0);
		objPond.setMaxValue(100);
		objPanel.add(objPond);
		Context.getInstance().addDetailExecution("ObjectiveWindow 7");
		objScale.setFieldLabel("Escala de Medicion");
		objScale.setMaxLength(255);
		objScale.setAllowBlank(false);
		
		objScale.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(objScale);
		Context.getInstance().addDetailExecution("ObjectiveWindow 8");
		fecha_finalizacion.setFieldLabel("Fecha Finalizacion");  
		fecha_finalizacion.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				if (((DateField)field).getValue().before(new Date())){
					return "La fecha de finalizacion debe ser posterior a la fecha actual.";
				}
				return null;
			}
		});
		objPanel.add(fecha_finalizacion);
		Context.getInstance().addDetailExecution("ObjectiveWindow 9");
		description.setPreventScrollbars(true);  
		description.setFieldLabel("Descripcion");  
		description.setMaxLength(255);
		objPanel.add(description);
		Context.getInstance().addDetailExecution("ObjectiveWindow 10");
		fldUser.setStore(new ListStore<ModelData>());
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_USERS);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Context.getInstance().addDetailExecution("ObjectiveWindow 11");
						Info.display(
								"iEvenTask",
								"No se han podido consultar los usuarios LDAP.");
					}

					@Override
					public void onSuccess(Object result) {
						Context.getInstance().addDetailExecution("ObjectiveWindow 12");
						Map map = (Map) result;
						Collection users = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = users.iterator();
						while (it.hasNext()) {
							Map actualUser = (Map) it.next();
							listStore.add(getModelData((String)actualUser.get(ParamsConst.ID), (String)actualUser.get(ParamsConst.NAME)));
						}
						Context.getInstance().addDetailExecution("ObjectiveWindow 13");
						fldUser.setStore(listStore);
						
						String user = Context.getInstance().getUserID();
						Context.getInstance().addDetailExecution("ObjectiveWindow - user=" + user);
						if ((actual!=null) && (actual.get(ParamsConst.ID_USUARIO_ASIGNADO)!=null)){
							user = actual.get(ParamsConst.ID_USUARIO_ASIGNADO).toString();
							Context.getInstance().addDetailExecution("ObjectiveWindow - actual.get(ParamsConst.ID_USUARIO_ASIGNADO)=" + user);
						}
						setCombo(fldUser, user);
						Context.getInstance().addDetailExecution("ObjectiveWindow 14");
					}

				});
		Context.getInstance().addDetailExecution("ObjectiveWindow 15");
		fldUser.setFieldLabel("Responsable");
		fldUser.setEditable(Boolean.FALSE);
		fldUser.setTypeAhead(true);  
		fldUser.setTriggerAction(TriggerAction.ALL); 
		objPanel.add(fldUser);
		Context.getInstance().addDetailExecution("ObjectiveWindow 16");
		objPanel.add(btnView);
		btnView.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UserViewWindow modal = new UserViewWindow(usersView);
				modal.show();
			}
		});
		
		this.cumplimiento.setVisible(Boolean.FALSE);
		this.cumplimiento.setEnabled(Boolean.FALSE);
		this.cumplimiento.setFieldLabel("Porcentaje de cumplimiento:");
		
		panel.setSize(WINDOW_WIDTH, 100);
		objPanel.add(panel);
		Context.getInstance().addDetailExecution("ObjectiveWindow 17");
		
		FormButtonBinding binding = new FormButtonBinding(objPanel);  
		if(guardar){
			btnGuardar.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					guardarObjetivo();}});
			objPanel.addButton(btnGuardar);
			binding.addButton(btnGuardar);  
		}else{
			btnModificar.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					modificarObjetivo();}});
			objPanel.addButton(btnModificar);
			binding.addButton(btnModificar);  
		}
		Context.getInstance().addDetailExecution("ObjectiveWindow 18");
		btnCancelar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cerrarVentana();}});
		objPanel.addButton(btnCancelar);
		
		
		Context.getInstance().addDetailExecution("ObjectiveWindow 19");
		
	    
	    this.add(objPanel);
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
	
	private void guardarObjetivo(){
		maskAvaiable();
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_OBJETIVO, objName.getValue());
			params.put(ParamsConst.TIPO_OBJETIVO, objType.getValue());
			params.put(ParamsConst.ESCALA_MEDICION, objScale.getValue());
			params.put(ParamsConst.FECHA_FINALIZACION, fecha_finalizacion.getValue());
			params.put(ParamsConst.PONDERACION, objPond.getValue());
			params.put(ParamsConst.ID_USUARIO_ASIGNADO, fldUser.getValue().get("key"));
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			
			setUsersVisibles(params);
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {

			@Override
			public void onFailure(Throwable caught) {
				maskDisable();
				DialogFactory.error("No pudo guardarse el objetivo. Aguarde un momento y vuelva a intentarlo.");
			}

			@Override
			public void onSuccess(Object result) {
				maskDisable();
				DialogFactory.info("Se guardo el objetivo con exito.");
				Context.getInstance().getObjectiveGrid().getStore().getLoader().load();
				cerrarVentana();
			}
			});
		} else {
			maskDisable();
		}
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
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
	
	private void modificarObjetivo(){
		maskAvaiable();
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			Context.getInstance().addDetailExecution("modificarObjetivo 1");
			params.put(ParamsConst.NOMBRE_OBJETIVO, objName.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 2");
			params.put(ParamsConst.TIPO_OBJETIVO, objType.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 3");
			params.put(ParamsConst.ESCALA_MEDICION, objScale.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 4");
			params.put(ParamsConst.FECHA_FINALIZACION, fecha_finalizacion.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 5");
			params.put(ParamsConst.PONDERACION, objPond.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 6");
			params.put(ParamsConst.ID_USUARIO_ASIGNADO, fldUser.getValue().get("key"));
			Context.getInstance().addDetailExecution("modificarObjetivo 7");
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			Context.getInstance().addDetailExecution("modificarObjetivo 8");
			params.put(ParamsConst.ID, this.getId_obj());
			Context.getInstance().addDetailExecution("modificarObjetivo 9");
			setUsersVisibles(params);
			Context.getInstance().addDetailExecution("modificarObjetivo 10");
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {
				
				@Override
				public void onFailure(Throwable caught) {
					Context.getInstance().addDetailExecution("modificarObjetivo 11");
					maskDisable();
					DialogFactory.error("No pudo modificarse el objetivo. Aguarde un momento y vuelva a intentarlo.");
				}
				
				@Override
				public void onSuccess(Object result) {
					Context.getInstance().addDetailExecution("modificarObjetivo 12");
					maskDisable();
					DialogFactory.info("Se modifico el objetivo con exito.");
					Context.getInstance().getObjectiveGrid().getStore().getLoader().load();
					cerrarVentana();
				}
			});
		} else {
			Context.getInstance().addDetailExecution("modificarObjetivo 13");
			maskDisable();
		}
		Context.getInstance().addDetailExecution("modificarObjetivo 14");
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setValuesToUpdate(Map<Object, Object> actual){
		this.actual=actual;
		Context.getInstance().addDetailExecution("ObjectiveWindow 20");
		this.setId_obj(Integer.valueOf(actual.get(ParamsConst.ID).toString()));
		objName.setValue((String) actual.get(ParamsConst.NOMBRE_OBJETIVO));
		objType.setValue((String) actual.get(ParamsConst.TIPO_OBJETIVO));
		objScale.setValue((String) actual.get(ParamsConst.ESCALA_MEDICION));
		fecha_finalizacion.setValue((Date)actual.get(ParamsConst.FECHA_FINALIZACION));
		objPond.setValue((Number) actual.get(ParamsConst.PONDERACION));
		Context.getInstance().addDetailExecution("ObjectiveWindow 21");
		setCombo(fldUser, (String) actual.get(ParamsConst.ID_USUARIO_ASIGNADO));
		description.setValue((String) actual.get(ParamsConst.DESCRIPCION));
		Context.getInstance().addDetailExecution("ObjectiveWindow 22");
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
		Context.getInstance().addDetailExecution("ObjectiveWindow 23");
		Map paramsGrid = new HashMap();
		paramsGrid.put(ParamsConst.ID_OBJETIVO, actual.get(ParamsConst.ID));
		grid = new Grid(this, ServiceNameConst.LIST_TASK_BY_OBJECTIVE, getGridConfig(), 10, paramsGrid);
		grid.setSize(WINDOW_WIDTH, 150);
		this.cumplimiento.setVisible(Boolean.TRUE);
		this.cumplimiento.setValue((Number) actual.get(ParamsConst.CUMPLIMIENTO));
		panel.add(grid);
		Context.getInstance().addDetailExecution("ObjectiveWindow 24");
	}
	
	@SuppressWarnings("deprecation")
	private void cerrarVentana(){
		this.close();
	}

	public Integer getId_obj() {
		return id_obj;
	}

	public void setId_obj(Integer id_obj) {
		this.id_obj = id_obj;
	}
	
	public void maskAvaiable(){
		this.mask("Aguarde un momento...");
	}
	
	public void maskDisable(){
		this.unmask();
	}

	/**
	 * Retorna la configuracion de la grilla
	 */
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// Se agrega esta columna para mantener el identificador de los perfiles
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, ParamsConst.ID, 1);
		clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfg1 = new ColumnConfig(ParamsConst.NOMBRE_TAREA, "Nombre", 150);
		configs.add(clmncnfg1);

		ColumnConfig clmncnfg2 = new ColumnConfig(ParamsConst.PESO, "Peso", 110);
		configs.add(clmncnfg2);

		ColumnConfig clmncnfg3 = new ColumnConfig(ParamsConst.CUMPLIMIENTO, "Cumplimiento", 110);
		configs.add(clmncnfg3);

		return configs;
	}
	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSelect(List selected) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onModify() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDividir() {
	}

}