package ar.com.AmberSoft.iEvenTask.client.menu;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.VerticalPanel;

public class MainMenu extends LayoutContainer {
	
	public static final Integer BUTTON_WIDTH = 100;
	public static final Integer VERTICAL_PANEL_MENU_WIDTH = IEvenTask.APP_WINDOW_WIDTH-BUTTON_WIDTH;
	
	MenuBar mnuBar;
	MenuBarItem mnuFile;
	MenuBarItem mnuReport; 
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private VerticalPanel verticalPanel_1;
	
	public MainMenu(){
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		horizontalPanel = new HorizontalPanel();
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setWidth(VERTICAL_PANEL_MENU_WIDTH);
		mnuBar = new MenuBar();
		verticalPanel.add(mnuBar);
		
		FileMenu fileMenu = new FileMenu();
		mnuFile = new MenuBarItem("Preferencias", fileMenu);
		mnuBar.add(mnuFile);
		
		ReportMenu reportMenu = new ReportMenu();
		mnuReport = new MenuBarItem("Reportes", reportMenu);
		mnuBar.add(mnuReport);
		
		horizontalPanel.add(verticalPanel);
		
		verticalPanel_1 = new VerticalPanel();
		
		Button btnSalir = new Button("Salir");
		btnSalir.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce)
			{
				cerrarSesion();
			}});
		btnSalir.setWidth(BUTTON_WIDTH);
		verticalPanel_1.add(btnSalir);
		horizontalPanel.add(verticalPanel_1);
		add(horizontalPanel);
	}
	
	protected void cerrarSesion() {
		IEvenTask.cerrarSesion();
	}
	
}
