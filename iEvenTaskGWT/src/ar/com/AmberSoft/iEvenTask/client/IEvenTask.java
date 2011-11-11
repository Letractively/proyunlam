package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.menu.InitializePanel;
import ar.com.AmberSoft.iEvenTask.client.menu.LoginPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainMenu;
import ar.com.AmberSoft.iEvenTask.client.menu.MainStatusBar;
import ar.com.AmberSoft.iEvenTask.client.menu.MainTabPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainToolBar;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class IEvenTask implements EntryPoint {
	
	public static final Integer APP_WINDOW_WIDTH = 1275;
	public static final Integer APP_WINDOW_HEIGTH = 600;
	public static final Integer DEFAULT_MENU_HEIGTH = 30;
	public static final Integer MAIN_TAB_PANEL_HEIGTH = 480;
	
	public static final Integer DELAY = 1000;
	
	static RootPanel rootPanel;
	
	private InitializePanel initializePanel;
	
	public void onModuleLoad(){
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("body");
		rootPanel.setSize(APP_WINDOW_WIDTH.toString(), APP_WINDOW_HEIGTH.toString());

		Map params = new HashMap();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CHECK_USER);
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		
		mask();
		DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				DialogFactory.error("Ocurrio un error al intentar verificar el usuario.");
			}

			@Override
			public void onSuccess(Object result) {
				unmask();
				Map map = (Map) result;
				if (map.get(ParamsConst.USER)!=null){
					Context.getInstance().setUsuario((Map) map.get(ParamsConst.USER));
					iniciarSesion();
				} else {
					iniciarLogin();
				}
			}
		});
	}
	
	public static void iniciarSesion() {
		//FIXME: Atrapamos el evento de cierre del explorador
		Window.addWindowCloseListener(new WindowCloseListener() {
			
			@Override
			public String onWindowClosing() {
				cerrarSesion();
				return null;
			}
			
			@Override
			public void onWindowClosed() {
				
			}
		});
		
		//elimino del panel de logins
		if (RootPanel.get("login_panel")!=null){
			rootPanel.remove(RootPanel.get("login_panel"));
		}
		
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
	
	public static void cerrarSesion(){
		Map params = new HashMap();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.EXIT);
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		
		DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				DialogFactory.error("Ocurrio un error al intentar quitar de session el usuario actual");
			}

			@Override
			public void onSuccess(Object result) {
			}
		});
        java.util.Iterator<Widget> itr = rootPanel.iterator();
        while(itr.hasNext()) {
                itr.next();
                itr.remove();
        }
        iniciarLogin();
	}
	
	public static void iniciarLogin(){
		LoginPanel loginPanel = new LoginPanel();
		loginPanel.setId("login_panel");
		rootPanel.add(loginPanel);
		rootPanel.setWidgetPosition(loginPanel, 0, 0);
	}

	public void mask(){
		initializePanel = new InitializePanel();
		initializePanel.setId("login_panel");
		rootPanel.add(initializePanel);
		rootPanel.setWidgetPosition(initializePanel, 0, 0);
	}
	
	public void unmask(){
		initializePanel.removeFromParent();
	}


	
	
}
