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

public class ProfilesWindow extends Window {

	public ProfilesWindow() {
		setHeading("Gesti\u00F3n de Perfiles");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		ButtonBar buttonBar = new ButtonBar();
		
		IconButton iconButton = new IconButton("botonAdd");
		buttonBar.add(iconButton);
		
		Button btnAdd = new Button("Add");
		buttonBar.add(btnAdd);
		add(buttonBar);
		
		TabPanel tabPanel = new TabPanel();
		
		TabItem tbtmNewTabitem = new TabItem("Detalles");
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
