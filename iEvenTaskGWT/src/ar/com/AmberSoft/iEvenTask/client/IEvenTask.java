package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.menu.LoginPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainMenu;
import ar.com.AmberSoft.iEvenTask.client.menu.MainStatusBar;
import ar.com.AmberSoft.iEvenTask.client.menu.MainTabPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainToolBar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IEvenTask implements EntryPoint {
	
	public static final Integer APP_WINDOW_WIDTH = 1024;
	public static final Integer APP_WINDOW_HEIGTH = 650;
	public static final Integer DEFAULT_MENU_HEIGTH = 24;
	public static final Integer MAIN_TAB_PANEL_HEIGTH = 400;
	
	static RootPanel rootPanel;

	public void onModuleLoad() {
		
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("body");
		rootPanel.setSize(APP_WINDOW_WIDTH.toString(), APP_WINDOW_HEIGTH.toString());
		
//		LoginPanel loginPanel = new LoginPanel();
//		loginPanel.setId("login_panel");
//		rootPanel.add(loginPanel);
//		rootPanel.setWidgetPosition(loginPanel, 0, 0);
		
		iniciarSesion(true);
//		LoginWindow loginWindow = new LoginWindow();
//		loginWindow.show();
	}
	
	public static void iniciarSesion(boolean flag) {
		if(flag){
//			RootPanel.get("login_panel").setVisible(false);
			//cargo el menu principal
			MainMenu mainMenu = new MainMenu();
			mainMenu.setId("main_menu");
			rootPanel.add(mainMenu);
			rootPanel.setWidgetPosition(mainMenu, 0, 0);
			
			//cargo la barra de botones
			MainToolBar mainToolBar = new MainToolBar();
			mainToolBar.setId("main_tool_bar");
			rootPanel.add(mainToolBar);
			rootPanel.setWidgetPosition(mainToolBar, 0, DEFAULT_MENU_HEIGTH);
			
			//cargo el panel tab
			MainTabPanel mainTabPanel = new MainTabPanel();
			mainTabPanel.setId("main_tab_panel");
			rootPanel.add(mainTabPanel);
			rootPanel.setWidgetPosition(mainTabPanel, 0, new Integer(2*DEFAULT_MENU_HEIGTH));
			
			//cargo la barra de estado
			MainStatusBar mainStatusBar = new MainStatusBar();
			mainStatusBar.setId("main_status_bar");
			rootPanel.add(mainStatusBar);
			rootPanel.setWidgetPosition(mainStatusBar, 0, new Integer((2*DEFAULT_MENU_HEIGTH) + MAIN_TAB_PANEL_HEIGTH));
		}
	}
	
	private static void cerrarSesion (boolean flag){
		RootPanel.get("main_menu").setVisible(false);
		RootPanel.get("main_tool_bar").setVisible(false);
		RootPanel.get("main_tab_panel").setVisible(false);
		RootPanel.get("main_status_bar").setVisible(false);
		RootPanel.get("login_panel").setVisible(true);
	}
}
