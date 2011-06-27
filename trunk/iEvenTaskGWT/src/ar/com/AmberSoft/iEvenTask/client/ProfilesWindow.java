package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.button.IconButton;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class ProfilesWindow extends Window {

	public ProfilesWindow() {
		setHeading("Gesti\u00F3n de Perfiles");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		ButtonBar buttonBar = new ButtonBar();
		
		Button btnAdd = new Button("Add");
		buttonBar.add(btnAdd);
		add(buttonBar);
		
		TabPanel tabPanel = new TabPanel();
		
		TabItem tbtmNewTabitem = new TabItem("Detalles");
		tbtmNewTabitem.setLayout(new RowLayout(Orientation.HORIZONTAL));
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		
		Text txtNombre = new Text("Nombre");
		verticalPanel_1.add(txtNombre);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("97px", "31px");
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		
		TextField textField = new TextField();
		verticalPanel_2.add(textField);
		horizontalPanel.add(verticalPanel_2);
		verticalPanel_2.setHeight("31px");
		tbtmNewTabitem.add(horizontalPanel, new RowData(800.0, 20.0, new Margins()));
		tabPanel.add(tbtmNewTabitem);
		tbtmNewTabitem.setSize("770px", "148px");
		add(tabPanel, new RowData(Style.DEFAULT, 200.0, new Margins()));
		
		TabPanel tabPanel_1 = new TabPanel();
		
		TabItem tbtmListadoDePerfiles = new TabItem("Listado de Perfiles");
		tabPanel_1.add(tbtmListadoDePerfiles);
		tbtmListadoDePerfiles.setSize("770px", "148px");
		add(tabPanel_1, new RowData(Style.DEFAULT, 230.0, new Margins()));
	}

}
