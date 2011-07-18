package ar.com.AmberSoft.iEvenTask.client;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;
  
public class ObjectiveWindow extends Window {
	public ObjectiveWindow() {
	}  
  
	private VerticalPanel vp;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		vp = new VerticalPanel();
		vp.setSpacing(10);
		createForm1();
		add(vp);
		vp.setSize("370", "340");
	}
	
	private void createForm1() {
	    FormPanel simple = new FormPanel();  
	    simple.setHeading("Nuevo Objetivo");  
	    simple.setFrame(true);  
	    simple.setWidth(350);
	    
	    TextField<String> objectiveType = new TextField<String>();  
	    objectiveType.setFieldLabel("Tipo");  
	    objectiveType.setAllowBlank(false);  
	    objectiveType.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(objectiveType, new FormData("-20"));
	    
	    TextField<String> objectiveName = new TextField<String>();  
	    objectiveName.setFieldLabel("Nombre");  
	    objectiveName.setAllowBlank(false);  
	    objectiveName.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(objectiveName, new FormData("-20"));
	    
	    DateField fecha_fin = new DateField();  
	    fecha_fin.setFieldLabel("Fecha Fin");  
	    simple.add(fecha_fin, new FormData("-20"));  
	    
	    TextField<String> scale = new TextField<String>();  
	    scale.setFieldLabel("Escala de Medicion");  
	    scale.setAllowBlank(false);  
	    scale.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(scale, new FormData("-20"));
	    
	    TextField<String> weighing = new TextField<String>();  
	    weighing.setFieldLabel("Ponderacion");  
	    weighing.setAllowBlank(false);  
	    weighing.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(weighing, new FormData("-20"));
	    
	    TextArea description = new TextArea();  
	    description.setPreventScrollbars(true);  
	    description.setFieldLabel("Descripcion");  
	    simple.add(description, new FormData("-20"));  
	  
	    Button b = new Button("Guardar");  
	    simple.addButton(b);  
	    simple.addButton(new Button("Cancelar"));  
	  
	    simple.setButtonAlign(HorizontalAlignment.CENTER);  
	  
	    FormButtonBinding binding = new FormButtonBinding(simple);  
	    binding.addButton(b);  
	  
	    vp.add(simple);  
	    
	}
}