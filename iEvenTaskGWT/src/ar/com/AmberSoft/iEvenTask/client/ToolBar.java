package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

public class ToolBar extends com.extjs.gxt.ui.client.widget.toolbar.ToolBar {

	private static ToolBar instance;
	
	private Status status; 
	
	private ToolBar() {
		super();
		status = new Status();
		status.setText("Mostrar estado");  
	    
	    add(status);  
	    add(new FillToolItem());
	    
	    Button executionDetailButton = new Button();
	    executionDetailButton.setIcon(Resources.ICONS.text());
	    executionDetailButton.setToolTip("Detalles de ejecucion");
	    
	    executionDetailButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Context.getInstance().windowShow(new ExecutionDetailWindow());
			}
		});
	    
	    add(executionDetailButton);
	}
	
	public static ToolBar getInstance(){
		if (instance==null){
			instance = new ToolBar();
		}
		return instance;
	}

}
