package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.ExecutionDetailWindow;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

public class MainStatusBar extends ToolBar  {
	
	Status status; 
	
	public MainStatusBar(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		status = new Status();
	    status.setText("Not writing");  
	    this.add(status);  
	    this.add(new FillToolItem());
	    
	    
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

}
