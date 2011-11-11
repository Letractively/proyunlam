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

public class InitializePanel extends LayoutContainer {
	public static final Integer PANEL_LOGIN_WIDTH = 400;
	public static final Integer PANEL_LOGIN_HEIGTH = PANEL_LOGIN_WIDTH/3;
	
	public static final Integer VERTICAL_PANEL_WIDTH = PANEL_LOGIN_WIDTH/2;
	
	public static final Integer HORIZONTAL_PANEL_HEIGTH = PANEL_LOGIN_HEIGTH/4;
	
	public static final Integer LABEL_WIDTH = VERTICAL_PANEL_WIDTH;
	public static final Integer TEXT_BOX_WIDTH = VERTICAL_PANEL_WIDTH - 40;
	public static final Integer BUTTON_WIDTH = LABEL_WIDTH/2;
	
	FormPanel main = new FormPanel();

	
	public InitializePanel() {
		super();
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.APP_WINDOW_HEIGTH.toString());
		
		maskAvaiable();
		
		main.setHeading("iEvenTask - Bienvenido");
		main.setCollapsible(false);
		main.setWidth(PANEL_LOGIN_WIDTH);
		main.setHeight(PANEL_LOGIN_HEIGTH);

		setLayout(new CenterLayout());
		this.add(main);
	}
	
	public void maskAvaiable(){
		this.mask("Inicializando ...");
	}
	
	public void maskDisable(){
		this.unmask();
	}

}
