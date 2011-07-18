package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SpinnerField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class TaskWindow extends Window {
	public TaskWindow() {
	}

	private VerticalPanel vp;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		vp = new VerticalPanel();
		vp.setSpacing(10);
		createForm1();
		add(vp);
		vp.setSize("500", "450");
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
	    
	    DateField fecha_com = new DateField();  
	    fecha_com.setFieldLabel("Fecha Comienzo");  
	    simple.add(fecha_com, new FormData("-20"));  
	  
	    DateField fecha_fin = new DateField();  
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
	    
	    TextArea description = new TextArea();  
	    description.setPreventScrollbars(true);  
	    description.setFieldLabel("Descripcion");  
	    simple.add(description, new FormData("-20"));  
	  
	    TextField<String> responsable = new TextField<String>();  
	    responsable.setFieldLabel("Responsable");  
	    responsable.setAllowBlank(false);  
	    responsable.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(responsable, new FormData("-20"));
	    
	    Button b = new Button("Guardar");  
	    simple.addButton(b);  
	    simple.addButton(new Button("Cancelar"));  
	  
	    simple.setButtonAlign(HorizontalAlignment.CENTER);  
	  
	    FormButtonBinding binding = new FormButtonBinding(simple);  
	    binding.addButton(b);  
	  
	    vp.add(simple);  
	    
	}
}
