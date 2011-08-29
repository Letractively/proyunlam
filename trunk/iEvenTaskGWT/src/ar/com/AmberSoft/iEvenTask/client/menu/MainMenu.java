package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;

import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;

public class MainMenu extends MenuBar {

	MenuBarItem mnuFile; 
	
	public MainMenu(){
		super();
		setSize(IEvenTask.appWindowWidth.toString(), "24px");
		
		mnuFile = new MenuBarItem("Archivo", new FileMenu());
		this.add(mnuFile);
	}
	
}
