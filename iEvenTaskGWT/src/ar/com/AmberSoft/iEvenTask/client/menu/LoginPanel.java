package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPanel extends LayoutContainer {
	
	public static final Integer PANEL_LOGIN_WIDTH = 400;
	public static final Integer PANEL_LOGIN_HEIGTH = PANEL_LOGIN_WIDTH/3;
	
	public static final Integer VERTICAL_PANEL_WIDTH = PANEL_LOGIN_WIDTH/2;
	
	public static final Integer HORIZONTAL_PANEL_HEIGTH = PANEL_LOGIN_HEIGTH/4;
	
	public static final Integer LABEL_WIDTH = VERTICAL_PANEL_WIDTH;
	public static final Integer TEXT_BOX_WIDTH = VERTICAL_PANEL_WIDTH - 40;
	public static final Integer BUTTON_WIDTH = LABEL_WIDTH/2;
	
	FormPanel mainLoginPanel = new FormPanel();
	@SuppressWarnings("rawtypes")
	TextField textUsuario = new TextField();
	@SuppressWarnings("rawtypes")
	TextField textPassword = new TextField();
	Timer timer;
	
	public LoginPanel() {
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.APP_WINDOW_HEIGTH.toString());
		
		
		mainLoginPanel.setHeading("iEvenTask - Autenticaci\u00F3n de Usuarios");
		mainLoginPanel.setCollapsible(false);
		mainLoginPanel.setWidth(PANEL_LOGIN_WIDTH);
		mainLoginPanel.setHeight(PANEL_LOGIN_HEIGTH);
		
		
		HorizontalPanel horizontalPanelUsuario = new HorizontalPanel();
		horizontalPanelUsuario.setHeight(HORIZONTAL_PANEL_HEIGTH);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		
		LabelField labelUsuario = new LabelField("Usuario");
		labelUsuario.setWidth(LABEL_WIDTH);
		TableData td_labelUsuario = new TableData();
		td_labelUsuario.setHorizontalAlign(HorizontalAlignment.CENTER);
		verticalPanel.add(labelUsuario, td_labelUsuario);
		verticalPanel.setWidth(VERTICAL_PANEL_WIDTH);
		horizontalPanelUsuario.add(verticalPanel);
		
		textUsuario.addKeyListener(new KeyListener() {
			public void componentKeyPress(ComponentEvent event) {
				presionoEnter(event);
			}
		});
		textUsuario.setAllowBlank(Boolean.FALSE);
		textUsuario.setWidth(TEXT_BOX_WIDTH);
		verticalPanel_1.add(textUsuario);
		verticalPanel_1.setWidth(VERTICAL_PANEL_WIDTH);
		TableData td_verticalPanel_1 = new TableData();
		td_verticalPanel_1.setHorizontalAlign(HorizontalAlignment.CENTER);
		horizontalPanelUsuario.add(verticalPanel_1, td_verticalPanel_1);
		mainLoginPanel.add(horizontalPanelUsuario);

		
		HorizontalPanel horizontalPanelPassword = new HorizontalPanel();
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		
		LabelField labelPassword = new LabelField("Password");
		labelPassword.setWidth(LABEL_WIDTH);
		TableData td_labelPassword = new TableData();
		td_labelPassword.setHorizontalAlign(HorizontalAlignment.CENTER);
		verticalPanel_2.add(labelPassword, td_labelPassword);
		verticalPanel_2.setWidth(VERTICAL_PANEL_WIDTH);
		horizontalPanelPassword.add(verticalPanel_2);
		
		VerticalPanel verticalPanel_3 = new VerticalPanel();
		
		textPassword = new TextField();
		textPassword.addKeyListener(new KeyListener() {
			public void componentKeyPress(ComponentEvent event) {
				presionoEnter(event);
			}
		});
		textPassword.setAllowBlank(Boolean.FALSE);
		textPassword.setPassword(true);

		textPassword.setPassword(true);
		textPassword.setWidth(TEXT_BOX_WIDTH);
		verticalPanel_3.add(textPassword);
		verticalPanel_3.setWidth(VERTICAL_PANEL_WIDTH);
		TableData td_verticalPanel_3 = new TableData();
		td_verticalPanel_3.setHorizontalAlign(HorizontalAlignment.CENTER);
		horizontalPanelPassword.add(verticalPanel_3, td_verticalPanel_3);
		horizontalPanelPassword.setHeight(HORIZONTAL_PANEL_HEIGTH);
		mainLoginPanel.add(horizontalPanelPassword);
		
		HorizontalPanel horizontalPanelBotones = new HorizontalPanel();
		
		VerticalPanel verticalPanel_4 = new VerticalPanel();
		
		Button btnCancelar = new Button("Cancelar");
		btnCancelar.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce)
			{
				accionCancelar();
			}});
		btnCancelar.setWidth(BUTTON_WIDTH);
		TableData td_btnCancelar = new TableData();
		td_btnCancelar.setHorizontalAlign(HorizontalAlignment.CENTER);
		verticalPanel_4.add(btnCancelar, td_btnCancelar);
		verticalPanel_4.setWidth(VERTICAL_PANEL_WIDTH);
		TableData td_verticalPanel_4 = new TableData();
		td_verticalPanel_4.setHorizontalAlign(HorizontalAlignment.CENTER);
		horizontalPanelBotones.add(verticalPanel_4, td_verticalPanel_4);
		
		VerticalPanel verticalPanel_5 = new VerticalPanel();
		
		Button btnIngresar = new Button("Ingresar");
		btnIngresar.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce)
			{
				accionIngresar(textUsuario.getValue().toString(),textPassword.getValue().toString());
			}});
		btnIngresar.setWidth(BUTTON_WIDTH);
		verticalPanel_5.add(btnIngresar);
		verticalPanel_5.setWidth(VERTICAL_PANEL_WIDTH);
		TableData td_verticalPanel_5 = new TableData();
		td_verticalPanel_5.setHorizontalAlign(HorizontalAlignment.CENTER);
		horizontalPanelBotones.add(verticalPanel_5, td_verticalPanel_5);
		horizontalPanelBotones.setHeight(HORIZONTAL_PANEL_HEIGTH);
		mainLoginPanel.add(horizontalPanelBotones);

		setLayout(new CenterLayout());
		this.add(mainLoginPanel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void presionoEnter(ComponentEvent event) {
		
		if (event.getKeyCode() == KeyCodes.KEY_ENTER){
			
			if (mainLoginPanel.isValid()) {
			
			Map params = new HashMap<String,String>();
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LOGIN);
			params.put(ParamsConst.USER, textUsuario.getValue());
			params.put(ParamsConst.PASSWORD, textPassword.getValue());
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

				@Override
				public void onFailure(Throwable caught) {
					Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Usuario o contrase\u00f1a incorrectos.");
					Info.display("Message: " + caught.getMessage(), "Cause" + caught.getCause());
					//TODO: Se colocan las siguientes dos lineas para cuando esta caido el servidor de dominios
					timer.cancel();
				}

				@Override
				public void onSuccess(Object result) {
					Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Bienvenido " + textUsuario.getValue());
					timer.cancel();
				}
			});
			} else {
				Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Faltan completar campos obligatorios.");
			}
		}
	}
	
	private void crearTimer(final int periodicidad) {
		timer = new Timer() {
			@Override
			public void run() {
				Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Debe colocar usuario y cotrase\u00f1a.\nLuego presione enter.");
				crearTimer(periodicidad);
			}
		};
		timer.schedule(periodicidad);
	}
	
	private void mostrarMensajeDemoraEnter() {
		int periodicidad = 10000;
		crearTimer(periodicidad);
	}
	
	protected void accionCancelar()
	{
		Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Accion boton cancelar");
	}
	protected void accionIngresar(String user, String pass){
		if (mainLoginPanel.isValid()){
			if(user.equals("amber") && pass.equals("amber")){
				IEvenTask.iniciarSesion(true);
			}else{
				Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Usuario o clave incorrecta, intente nuevamente.");	
			}
		}
	}
}
