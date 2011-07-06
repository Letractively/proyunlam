package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.validaciones.ValidaObligatorio;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;

public class ProfilesWindow extends Window {

	public ProfilesWindow() {
		setHeading("Gesti\u00F3n de Perfiles");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		TabPanel tabPanel = new TabPanel();
		
		TabItem tbtmNewTabitem = new TabItem("Detalles");
		tbtmNewTabitem.setLayout(new FlowLayout(5));
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		final FormPanel frmDetalles = new FormPanel();
		frmDetalles.setHeaderVisible(false);
		frmDetalles.setHeading("New FormPanel");
		frmDetalles.setCollapsible(true);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		frmDetalles.add(verticalPanel, new FormData("100%"));
		verticalPanel.setBorders(true);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		
		LabelField lblfldNombre = new LabelField("Nombre");
		horizontalPanel_1.add(lblfldNombre);
		lblfldNombre.setWidth("75px");
		
		final TextField campoNombre = new TextField();
		horizontalPanel_1.add(campoNombre);
		campoNombre.setFieldLabel("New TextField");
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("268px");
		campoNombre.setValidator(new ValidaObligatorio());
		campoNombre.setAutoValidate(Boolean.TRUE);
		campoNombre.setAllowBlank(Boolean.FALSE);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		
		LabelField lblfldConexion = new LabelField("Conexion");
		horizontalPanel_2.add(lblfldConexion);
		lblfldConexion.setWidth("75px");
		
		TextField campoConexion = new TextField();
		campoConexion.setFieldLabel("New TextField");
		horizontalPanel_2.add(campoConexion);
		verticalPanel.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("268px");
		campoConexion.setAllowBlank(Boolean.FALSE);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		
		LabelField lblfldGrupoLdap = new LabelField("Grupo LDAP");
		horizontalPanel_3.add(lblfldGrupoLdap);
		lblfldGrupoLdap.setWidth("75px");
		
		TextField campoGrupo = new TextField();
		campoGrupo.setFieldLabel("New TextField");
		horizontalPanel_3.add(campoGrupo);
		verticalPanel.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("268px");
		campoGrupo.setAllowBlank(Boolean.FALSE);
		
		Button btnAgregar = new Button("Agregar");
		btnAgregar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				Info.display("Boton Agregar", "Validando campos");
				if (!frmDetalles.isValid()){
					Info.display("Formulario", "Existen campos invalidos");
				} else {
					Info.display("Formulario", "Validaciones correctas");
				}
			}
		});
		verticalPanel.add(btnAgregar);
		
		Button btnCancelar = new Button("Cancelar");
		verticalPanel.add(btnCancelar);
		verticalPanel.setSize("270px", "124px");
		horizontalPanel.add(frmDetalles);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setBorders(true);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("536px", "225px");
		tbtmNewTabitem.add(horizontalPanel);
		tabPanel.add(tbtmNewTabitem);
		tbtmNewTabitem.setWidth("813px");
		add(tabPanel, new RowData(Style.DEFAULT, 265.0, new Margins()));
	}

}
