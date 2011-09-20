package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginWindow extends Window {

	private Timer timer;
	private TextField fldUser;
	private TextField fldPassword;
	private FormPanel frmLogin;
	
	public LoginWindow() {
		
		mostrarMensajeDemoraEnter();
		
		setModal(true);
		setHeading("iEvenTask - Autenticaci\u00F3n de Usuarios");
		setLayout(new FillLayout(Orientation.HORIZONTAL));
		
		frmLogin = new FormPanel();
		frmLogin.setHeaderVisible(false);
		frmLogin.setHeading("New FormPanel");
		frmLogin.setCollapsible(true);
		frmLogin.setLayout(new FlowLayout(5));
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		
		LabelField lblfldNewLabelfield = new LabelField("Usuario");
		verticalPanel.add(lblfldNewLabelfield);
		lblfldNewLabelfield.setWidth("75px");
		horizontalPanel.add(verticalPanel);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		
		fldUser = new TextField();
		fldUser.addKeyListener(new KeyListener() {
			public void componentKeyPress(ComponentEvent event) {
				presionoEnter(event);
			}
		});
		fldUser.setAllowBlank(Boolean.FALSE);
		
		verticalPanel_1.add(fldUser);
		horizontalPanel.add(verticalPanel_1);
		frmLogin.add(horizontalPanel);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		
		LabelField lblfldNewLabelfield_1 = new LabelField("Password");
		verticalPanel_2.add(lblfldNewLabelfield_1);
		lblfldNewLabelfield_1.setWidth("75px");
		horizontalPanel_1.add(verticalPanel_2);
		
		VerticalPanel verticalPanel_3 = new VerticalPanel();
		
		fldPassword = new TextField();
		fldPassword.addKeyListener(new KeyListener() {
			public void componentKeyPress(ComponentEvent event) {
				presionoEnter(event);
			}
		});
		fldPassword.setAllowBlank(Boolean.FALSE);
		fldPassword.setPassword(true);
		verticalPanel_3.add(fldPassword);
		horizontalPanel_1.add(verticalPanel_3);
		frmLogin.add(horizontalPanel_1);
		add(frmLogin);
	}

	private void mostrarMensajeDemoraEnter() {
		int periodicidad = 10000;
		crearTimer(periodicidad);
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
	
	private void presionoEnter(ComponentEvent event) {
		final LoginWindow loginWindow = this;
		if (event.getKeyCode() == KeyCodes.KEY_ENTER){
			
			if (frmLogin.isValid()) {
			
			Map params = new HashMap<String,String>();
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LOGIN);
			params.put(ParamsConst.USER, fldUser.getValue());
			params.put(ParamsConst.PASSWORD, fldPassword.getValue());
			DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

				@Override
				public void onFailure(Throwable caught) {
					Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Usuario o contrase\u00f1a incorrectos.");
					Info.display("Message: " + caught.getMessage(), "Cause" + caught.getCause());
					//TODO: Se colocan las siguientes dos lineas para cuando esta caido el servidor de dominios
					loginWindow.hide();
					timer.cancel();
				}

				@Override
				public void onSuccess(Object result) {
					Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Bienvenido " + fldUser.getValue());
					loginWindow.hide();
					timer.cancel();
				}
			});
			} else {
				Info.display("iEvenTask - Autenticaci\u00F3n de Usuarios", "Faltan completar campos obligatorios.");
			}
		}
	}
}
