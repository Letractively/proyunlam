package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;

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
	}

}
