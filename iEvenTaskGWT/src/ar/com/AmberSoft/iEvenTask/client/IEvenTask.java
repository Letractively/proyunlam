package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.menu.LoginPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainMenu;
import ar.com.AmberSoft.iEvenTask.client.menu.MainStatusBar;
import ar.com.AmberSoft.iEvenTask.client.menu.MainTabPanel;
import ar.com.AmberSoft.iEvenTask.client.menu.MainToolBar;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IEvenTask implements EntryPoint {
	
	public static final Integer APP_WINDOW_WIDTH = 1024;
	public static final Integer APP_WINDOW_HEIGTH = 650;
	public static final Integer DEFAULT_MENU_HEIGTH = 24;
	public static final Integer MAIN_TAB_PANEL_HEIGTH = 400;
	
	public static final Integer DELAY = 1000;
	
	static RootPanel rootPanel;
	
	/*public static native void getUserLogon() /*-{
		var objNet = CreateObject("WScript.NetWork") 
		alert("Hola Mundo")
		var strInfo
		strInfo = "User Name is     " & objNet.UserName & vbCRLF & _
		          "Computer Name is " & objNet.ComputerName & vbCRLF & _
		          "Domain Name is   " & objNet.UserDomain
		alert( strInfo)
			
	}-*/
	//;

	public void onModuleLoad(){
		/*try {
			getUserLogon();
		} catch (Exception e){
			Context.getInstance().addDetailExecution(e.getMessage());
		}¨*/
	
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("body");
		rootPanel.setSize(APP_WINDOW_WIDTH.toString(), APP_WINDOW_HEIGTH.toString());
		
		Map params = new HashMap();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CHECK_USER);
		params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		
		DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Error", "Ocurrio un error al intentar verificar si el usuario esta activo");
			}

			@Override
			public void onSuccess(Object result) {
				Map map = (Map) result;
				if (map.get(ParamsConst.USER)!=null){
					iniciarSesion();
				} else {
					iniciarLogin();
				}
			}
		});
		
		//if(Context.getInstance().isSesion()){
		//	iniciarSesion();
		//}else{
			//iniciarLogin();
		//}
		
	}
	
	public static void iniciarSesion() {
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
		
		
		/*final ToolBar toolBar = new ToolBar();
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Map params = new HashMap();
				params.put(ServiceNameConst.SERVICIO, ServiceNameConst.BACKGROUND_PROCESS_CONSULTING);
				
				DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("Error", "Ocurrio un error al intentar consultar los procesos activos");
					}

					@Override
					public void onSuccess(Object result) {
						Map map = (Map) result;
						Map processes = (Map) map.get(ParamsConst.PROCESS);
						toolBar.setVisible(!processes.isEmpty());
						
						Iterator it = processes.keySet().iterator();
						while (it.hasNext()) {
							Integer key = (Integer) it.next();
							Map actual = (Map) processes.get(key);
							
						}
					}
				});
			}
		};
		timer.scheduleRepeating(DELAY);
		rootPanel.add(toolBar);
		rootPanel.setWidgetPosition(toolBar, 0, new Integer((2*DEFAULT_MENU_HEIGTH) + MAIN_TAB_PANEL_HEIGTH - 50));
		*/
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
				Info.display("Error", "Ocurrio un error al intentar quitar de session el usuario actual");
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
}
