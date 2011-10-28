package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogFactory;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
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

	
	@SuppressWarnings("rawtypes")
	public LoginPanel() {
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.APP_WINDOW_HEIGTH.toString());
		
		unmask();
		
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
		
//		textUsuario.addKeyListener(new KeyListener() {
//			public void componentKeyPress(ComponentEvent event) {
//				presionoEnter(event);
//			}
//		});
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
//		textPassword.addKeyListener(new KeyListener() {
//			public void componentKeyPress(ComponentEvent event) {
//				presionoEnter(event);
//			}
//		});
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
		
		Button btnIngresar = new Button("Ingresar");
		mainLoginPanel.add(btnIngresar, new FormData("100%"));
		btnIngresar.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce)
			{
				presionoEnter(ce);
			}});
		btnIngresar.setWidth(BUTTON_WIDTH);

		setLayout(new CenterLayout());
		this.add(mainLoginPanel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void presionoEnter(ComponentEvent event) {
		
		if (mainLoginPanel.isValid()) {
		
			Map params = new HashMap<String,String>();
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LOGIN);
			params.put(ParamsConst.USER, textUsuario.getValue());
			params.put(ParamsConst.PASSWORD, textPassword.getValue());
			maskAvaiable();
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {
	
				@Override
				public void onFailure(Throwable caught) {
					DialogFactory.error("Usuario o contrase\u00f1a incorrectos.");
					unmask();
				}
	
				@Override
				public void onSuccess(Object result) {
					unmask();
					Map map = (Map) result;
					Map user = (Map) map.get(ParamsConst.USER);
					
					DialogFactory.info("Bienvenido " + user.get(ParamsConst.NAME));
					
					Context.getInstance().setUsuario(user); 	
					Context.getInstance().setSesion(true); 	
					IEvenTask.iniciarSesion();
				}
			});
		} else {
			DialogFactory.error("Faltan completar campos obligatorios.");
		}
	}
	
	public void maskAvaiable(){
		this.mask("Aguarde un momento...");
	}
	
	public void maskDisable(){
		this.unmask();
	}
	
	
}
