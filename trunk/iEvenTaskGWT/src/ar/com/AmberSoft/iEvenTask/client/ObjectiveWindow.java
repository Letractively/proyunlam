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
import com.google.gwt.user.client.rpc.AsyncCallback;
  
public class ObjectiveWindow extends Window  {
	
	public static final Integer WINDOW_WIDTH = 400;
	public static final Integer WINDOW__HEIGTH = 365;
	public static final Integer OBJECTIVE_PANEL_WIDTH = WINDOW_WIDTH;
	
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
	
    /**
	 * @param guardar: boolean true para guardar / boolean false para modificar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ObjectiveWindow(boolean guardar) {
		super();
		setSize(WINDOW_WIDTH, WINDOW__HEIGTH);
		
		if(guardar){
			objPanel.setHeading("Nuevo Objetivo");
		}else{
			objPanel.setHeading("Modificar Objetivo");
		}
		
		objPanel.setFrame(true);
		objPanel.setWidth(OBJECTIVE_PANEL_WIDTH);
		
		objName.setFieldLabel("Nombre");  
		objName.setAllowBlank(false);  
		objName.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(objName);

		objType.setFieldLabel("Tipo");  
		objType.setAllowBlank(false);  
		objType.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(objType);
		
		objPond.setPropertyEditorType(Integer.class);
		objPond.setFieldLabel("Ponderacion");  
		objPond.setAllowBlank(false);  
		objPond.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(objPond);

		objScale.setFieldLabel("Escala de Medicion");  
		objScale.setAllowBlank(false);  
		objScale.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(objScale);
		
		fecha_finalizacion.setFieldLabel("Fecha Finalizacion");  
		objPanel.add(fecha_finalizacion);
		
		description.setPreventScrollbars(true);  
		description.setFieldLabel("Descripcion");  
		objPanel.add(description);
		
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
						String user = Context.getInstance().getUserName();
						if ((actual!=null) && (actual.get(ParamsConst.ID_USUARIO)!=null)){
							user = actual.get(ParamsConst.ID_USUARIO).toString();
						}
						setCombo(fldUser, user);
					}

				});
		fldUser.setEditable(Boolean.FALSE);
		fldUser.setTypeAhead(true);  
		fldUser.setTriggerAction(TriggerAction.ALL); 
		objPanel.add(fldUser);
		
		objPanel.add(btnView);
		btnView.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				UserViewWindow modal = new UserViewWindow(usersView);
				modal.show();
			}
		});
		
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
		
		btnCancelar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				cerrarVentana();}});
		objPanel.addButton(btnCancelar);
		
	    
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
				//FIXME: Invocar a la primer pagina
				//refreshGrid(grid);
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
		Iterator<ModelData> users = usersView.iterator();
		while (users.hasNext()) {
			ModelData modelData = (ModelData) users.next();
			toSend.add(modelData.get("id"));
		}
		params.put(ParamsConst.USERS_VIEW, toSend);
	}
	
	private void modificarObjetivo(){
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
			params.put(ParamsConst.ID, this.getId_obj());
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {
				
				@Override
				public void onFailure(Throwable caught) {
					maskDisable();
					DialogFactory.error("No pudo modificarse el objetivo. Aguarde un momento y vuelva a intentarlo.");
				}
				
				@Override
				public void onSuccess(Object result) {
					maskDisable();
					DialogFactory.info("Se modifico el objetivo con exito.");
					cerrarVentana();
//					MainTabTareas.reloadGrid();
					
				}
			});
		} else {
			maskDisable();
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setValuesToUpdate(Map<Object, Object> actual){
		this.setId_obj(Integer.valueOf(actual.get(ParamsConst.ID).toString()));
		objName.setValue(actual.get(ParamsConst.NOMBRE_OBJETIVO).toString());
		objType.setValue(actual.get(ParamsConst.TIPO_OBJETIVO).toString());
		objScale.setValue(actual.get(ParamsConst.ESCALA_MEDICION).toString());
		fecha_finalizacion.setValue((Date)actual.get(ParamsConst.FECHA_FINALIZACION));
		objPond.setValue((Number) actual.get(ParamsConst.PONDERACION));
		setCombo(fldUser, actual.get(ParamsConst.ID_USUARIO_ASIGNADO).toString());
		description.setValue(actual.get(ParamsConst.DESCRIPCION).toString());
		
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

}