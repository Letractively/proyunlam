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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SpinnerField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TaskWindow extends Window {
	public TaskWindow() {
		setInitialWidth(400);
	}

	private VerticalPanel vp;
	List<Field> toValidate = new ArrayList<Field>();
	private Boolean editing = Boolean.FALSE;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		vp = new VerticalPanel();
		vp.setSpacing(10);
		createForm1();
		add(vp);
		vp.setSize("360", "330");
	}
	
	private void createForm1() {
	    FormPanel simple = new FormPanel();  
	    simple.setHeading("Nueva Tarea");  
	    simple.setFrame(true);  
	    simple.setWidth(350);
	    
	    TextField<String> taskName = new TextField<String>();  
	    taskName.setFieldLabel("Nombre");  
	    taskName.setAllowBlank(false);  
	    taskName.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(taskName, new FormData("-20"));
	    
	    final DateField fecha_com = new DateField();  
	    fecha_com.setFieldLabel("Fecha Comienzo");  
	    simple.add(fecha_com, new FormData("-20"));  
	  
	    final DateField fecha_fin = new DateField();  
	    fecha_fin.setFieldLabel("Fecha Fin");  
	    simple.add(fecha_fin, new FormData("-20"));  
	    
	    SpinnerField spinnerField = new SpinnerField();  
	    spinnerField.setIncrement(.1d);  
	    spinnerField.getPropertyEditor().setType(Double.class);  
	    spinnerField.getPropertyEditor().setFormat(NumberFormat.getFormat("00.0"));  
	    spinnerField.setFieldLabel("Duracion (hs)");  
	    spinnerField.setMinValue(-10d);  
	    spinnerField.setMaxValue(10d);  
	    simple.add(spinnerField, new FormData("-20"));  
	    
	    final TextArea description = new TextArea();  
	    description.setPreventScrollbars(true);  
	    description.setFieldLabel("Descripcion");  
	    simple.add(description, new FormData("-20"));  
	  
	    final TextField<String> responsable = new TextField<String>();  
	    responsable.setFieldLabel("Responsable");  
	    responsable.setAllowBlank(false);  
	    responsable.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(responsable, new FormData("-20"));
	    
	    
	    Button btnGuardar = new Button("Guardar");  
		btnGuardar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if (isValid()){
						Map params = new HashMap<String,String>();

//						params.put(ParamsConst.FECHACREACION, fecha_com.getValue());
//						params.put(ParamsConst.FECHAFIN, fecha_fin.getValue());
						System.out.println("ID_USUARIO: " + responsable.getValue());
						params.put(ParamsConst.ID_USUARIO, responsable.getValue());
//						params.put(ParamsConst.FECHAMODIFICACION, fecha_fin.getValue());
//						params.put(ParamsConst.ESTADO, fldAdmin.getValue());
//						params.put(ParamsConst.CUMPLIMIENTO, fldAdmin.getValue());
//						params.put(ParamsConst.TIPO_TAREA, fldAdmin.getValue());
						/*
						if (editing.booleanValue()){
							List seleccionados = grid.getSelectionModel().getSelection();
							if (seleccionados.size()==1){
								Iterator it = seleccionados.iterator();
								if (it.hasNext()){
									BaseModel baseModel = (BaseModel) it.next();
									params.put(ParamsConst.ID, baseModel.get(ParamsConst.ID));
								}
							}
							params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_PROFILE);
						} else {
							params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_PROFILE);
						}*/
						params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_TASK);
						DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {
	
							@Override
							public void onFailure(Throwable caught) {
								Info.display("iEvenTask", "No pudo guardarse la tarea. Aguarde un momento y vuelva a intentarlo.");
							}
	
							@Override
							public void onSuccess(Object result) {
								Info.display("iEvenTask", "Se guardo la tarea con exito.");
								//resetFields(fldName, fldConection, fldGroup, fldObjective, fldAdmin);
								//FIXME: Invocar a la primer pagina
								//refreshGrid(grid);
							}


						});
					
	
				}
				
			}
		});
	    simple.addButton(btnGuardar);  
	    simple.addButton(new Button("Cancelar"));  
	  
	    simple.setButtonAlign(HorizontalAlignment.CENTER);  
	  
	    FormButtonBinding binding = new FormButtonBinding(simple);  
	    binding.addButton(btnGuardar);  
	  
	    vp.add(simple);  
	    
	}
	
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
}
