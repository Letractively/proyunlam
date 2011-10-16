package ar.com.AmberSoft.iEvenTask.client;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
  
public class ObjectiveWindow extends Window  {
	
	public static final Integer WINDOW_WIDTH = 400;
	public static final Integer WINDOW__HEIGTH = 365;
	public static final Integer OBJECTIVE_PANEL_WIDTH = WINDOW_WIDTH;
	
	FormPanel objPanel = new FormPanel();
	@SuppressWarnings("rawtypes")
	List<Field> toValidate = new ArrayList<Field>();
	@SuppressWarnings("unused")
	private Boolean editing = Boolean.FALSE;
    TextField<String> objName = new TextField<String>();
    TextField<String> objType = new TextField<String>();
    TextField<Integer> objPond = new TextField<Integer>();
    TextField<String> objScale = new TextField<String>();
    DateField fecha_finalizacion = new DateField();  
    TextArea description = new TextArea();  
    TextField<String> usuario_asignado = new TextField<String>();
    Button btnGuardar = new Button("Guardar");
    Button btnModificar = new Button("Modificar");
    Button btnCancelar = new Button("Cancelar");  
    Integer id_obj;
	
    /**
	 * @param guardar: boolean true para guardar / boolean false para modificar
	 */
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
		
		usuario_asignado.setFieldLabel("Asignado a");  
		usuario_asignado.setAllowBlank(false);  
		usuario_asignado.setValue(Context.getInstance().getUsuario());
		usuario_asignado.getFocusSupport().setPreviousId(objPanel.getButtonBar().getId());  
		objPanel.add(usuario_asignado);
		
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
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_OBJETIVO, objName.getValue());
			params.put(ParamsConst.TIPO_OBJETIVO, objType.getValue());
			params.put(ParamsConst.ESCALA_MEDICION, objScale.getValue());
			params.put(ParamsConst.FECHA_FINALIZACION, fecha_finalizacion.getValue().toString());
			params.put(ParamsConst.PONDERACION, objPond.getValue());
			params.put(ParamsConst.ID_USUARIO_ASIGNADO, usuario_asignado.getValue());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("iEvenTask", "No pudo guardarse el objetivo. Aguarde un momento y vuelva a intentarlo.");
			}

			@Override
			public void onSuccess(Object result) {
				Info.display("iEvenTask", "Se guardo el objetivo con exito.");
				//FIXME: Invocar a la primer pagina
				//refreshGrid(grid);
				cerrarVentana();
			}
			});
		}
	}
	
	private void modificarObjetivo(){
		if (isValid()){
			Map<Object,Object> params = new HashMap<Object,Object>();
			
			params.put(ParamsConst.NOMBRE_OBJETIVO, objName.getValue());
			params.put(ParamsConst.TIPO_OBJETIVO, objType.getValue());
			params.put(ParamsConst.ESCALA_MEDICION, objScale.getValue());
			params.put(ParamsConst.FECHA_FINALIZACION, fecha_finalizacion.getValue().toString());
			params.put(ParamsConst.PONDERACION, objPond.getValue());
			params.put(ParamsConst.ID_USUARIO_ASIGNADO, usuario_asignado.getValue());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			params.put(ParamsConst.ID, this.getId_obj());
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {
				
				@Override
				public void onFailure(Throwable caught) {
					Info.display("iEvenTask", "No pudo modificarse el objetivo. Aguarde un momento y vuelva a intentarlo.");
				}
				
				@Override
				public void onSuccess(Object result) {
					Info.display("iEvenTask", "Se modifico el objetivo con exito.");
					cerrarVentana();
//					MainTabTareas.reloadGrid();
					
				}
			});
		}
	}
	
	public void setValuesToUpdate(Map<Object, Object> actual){
		this.setId_obj(Integer.valueOf(actual.get(ParamsConst.ID).toString()));
		objName.setValue(actual.get(ParamsConst.NOMBRE_OBJETIVO).toString());
		objType.setValue(actual.get(ParamsConst.TIPO_OBJETIVO).toString());
		objScale.setValue(actual.get(ParamsConst.ESCALA_MEDICION).toString());
		fecha_finalizacion.setValue(new Date(Long.valueOf(actual.get(ParamsConst.FECHA_FINALIZACION).toString())));
		objPond.setValue(Integer.valueOf(actual.get(ParamsConst.PONDERACION).toString()));
		usuario_asignado.setValue(actual.get(ParamsConst.ID_USUARIO_ASIGNADO).toString());
		description.setValue(actual.get(ParamsConst.DESCRIPCION).toString());
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
	
}