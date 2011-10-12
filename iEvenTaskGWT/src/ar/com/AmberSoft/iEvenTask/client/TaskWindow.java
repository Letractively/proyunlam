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
import com.extjs.gxt.ui.client.widget.form.SpinnerField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.i18n.client.DateTimeFormat;
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
    TextField<String> responsable = new TextField<String>();
    Button btnGuardar = new Button("Guardar");
    Button btnCancelar = new Button("Cancelar");  
	
	public TaskWindow(String heading) {
		super();
		setSize(WINDOW_WIDTH, WINDOW__HEIGTH);
		
		taskPanel.setHeading(heading);
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
		
		responsable.setFieldLabel("Responsable");  
		responsable.setAllowBlank(false);  
		responsable.setValue(Context.getInstance().getUsuario());
		responsable.getFocusSupport().setPreviousId(taskPanel.getButtonBar().getId());  
		taskPanel.add(responsable);
		
		btnGuardar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				guardarTarea();}});
		taskPanel.addButton(btnGuardar);
		 
		taskPanel.addButton(btnCancelar);  
	  
	    FormButtonBinding binding = new FormButtonBinding(taskPanel);  
	    binding.addButton(btnGuardar);  
	    
	    this.add(taskPanel);
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
			Map<String,String> params = new HashMap<String,String>();
			
			params.put(ParamsConst.NOMBRE_TAREA, taskName.getValue());
			params.put(ParamsConst.FECHA_COMIENZO, dateToString(fecha_com.getValue()));
			params.put(ParamsConst.FECHA_FIN, dateToString(fecha_fin.getValue()));
			params.put(ParamsConst.DURACION, duration.getValue().toString());
			params.put(ParamsConst.DESCRIPCION, description.getValue());
			params.put(ParamsConst.ID_USUARIO, responsable.getValue());
			
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_TASK);
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback<Object>() {

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
	/**
	 * 
	 * @param fecha tipo Date
	 * @return String formateado, el formato es el mismo con el que en el BackEnd se crea el Date.
	 */
	private String dateToString (Date fecha){
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		return fmt.format(fecha);
	}
	
	/**
	 * Setters para realizar la modificacion de una tarea
	 */
	public void setTaskName(String taskName) {
		this.taskName.setValue(taskName);
	}

	public void setFecha_com(DateField fecha_com) {
		this.fecha_com = fecha_com;
	}

	public void setFecha_fin(DateField fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public void setDuration(SpinnerField duration) {
		this.duration = duration;
	}

	public void setDescription(String description) {
		this.description.setValue(description);
	}

	public void setResponsable(String responsable) {
		this.responsable.setValue(responsable);
	}
	

	public String getTaskName() {
		return taskName.getValue();
	}

	public DateField getFecha_com() {
		return fecha_com;
	}

	public DateField getFecha_fin() {
		return fecha_fin;
	}

	public SpinnerField getDuration() {
		return duration;
	}

	public String getDescription() {
		return description.getValue();
	}

	public String getResponsable() {
		return responsable.getValue();
	}
}
