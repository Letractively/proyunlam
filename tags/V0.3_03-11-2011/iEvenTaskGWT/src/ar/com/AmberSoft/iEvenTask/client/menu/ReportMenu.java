package ar.com.AmberSoft.iEvenTask.client.menu;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.user.client.Window;

public class ReportMenu extends Menu {

	
	com.extjs.gxt.ui.client.widget.menu.MenuItem reporte;
	
	public ReportMenu() {
		super();

		
		reporte = new com.extjs.gxt.ui.client.widget.menu.MenuItem("Reporte");
		reporte.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				 Window.open("/iEvenTask/report", "Reporte", "");
			}
		});
		add(reporte);
	
	
	}

}
