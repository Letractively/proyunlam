package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.Window;
import ar.com.AmberSoft.iEvenTask.client.exceptions.WindowPreviouslyRegisteredException;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

public class MenuItem extends com.extjs.gxt.ui.client.widget.menu.MenuItem {
	
	protected Context context = Context.getInstance();

	public MenuItem(String text, final Window window){
		super(text);
		addSelectionListener(new SelectionListener<MenuEvent>() {
			/**
			 * Registra la ventana cuando se da click en la opcion
			 * Si la ventana ya se encuentra registrada, toma esta para mostrar
			 */
			public void componentSelected(MenuEvent ce) {
				Window windowContextRegistered = window;
				try {
					context.registerWindow(window);
				} catch (WindowPreviouslyRegisteredException e){
					windowContextRegistered = Context.getInstance().getWindowInstance(window.getClass());
				}
				windowContextRegistered.show();
			}
		});
	}
	
}
