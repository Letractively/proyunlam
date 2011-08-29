package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowPreviouslyRegisteredException;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;


public class MenuItem extends com.extjs.gxt.ui.client.widget.menu.MenuItem {

	public MenuItem(String text, final Window window){
		super(text);
		addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				try {
					window.show();
				} catch (WindowPreviouslyRegisteredException e){
					// FIXME: Mostrar por interfaz grafica un mensaje de error
				}
			}
		});
	}
	
}
