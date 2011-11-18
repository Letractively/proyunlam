package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.Window;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

public class MenuItem extends com.extjs.gxt.ui.client.widget.menu.MenuItem {
	
	public MenuItem(String text, final Window window){
		super(text);
		addSelectionListener(new SelectionListener<MenuEvent>() {
			/**
			 * Registra la ventana cuando se da click en la opcion
			 * Si la ventana ya se encuentra registrada, toma esta para mostrar
			 */
			public void componentSelected(MenuEvent ce) {
				 Context.getInstance().windowShow(window);
			}
		});
	}
	
}
