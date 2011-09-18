package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;

import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;

public class MainMenu extends MenuBar {

	MenuBarItem mnuFile; 
	
	public MainMenu(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		mnuFile = new MenuBarItem("Archivo", new FileMenu());
		this.add(mnuFile);
	}
	
}
