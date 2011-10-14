package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CommentWindows extends Window {

	public static final Integer WINDOW_WIDTH = 510;
	public static final Integer WINDOW_HEIGTH = 390;
	
	public static final Integer FIELD_WIDTH = WINDOW_WIDTH - 10;
	public static final Integer LABEL_WIDTH = 400;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	// Campos
	private final HtmlEditor htmlEditor = new HtmlEditor();
	
	private Integer id;
	
	final Button save = new SaveButton(this);
	final Button cancel = new CancelButton(this);
	
	public CommentWindows(Integer id, String name) {
		super();

		this.id = id;
		
		initialize(name);

		addToolBar();
		
		TabPanel tabPanel = new TabPanel();
		TabItem tbtmDetalles = new TabItem("Detalles");
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		tabPanel.add(tbtmDetalles);
		tbtmDetalles.add(horizontalPanel);
		tbtmDetalles.setHeight(DETAILS_HEIGTH.toString());

		horizontalPanel.add(getPanelFields());

		add(tabPanel, new RowData(WINDOW_WIDTH, Style.DEFAULT, new Margins()));
		
	}

	/**
	 * Inicializa la ventana actual
	 */
	private void initialize(String name) {
		setInitialWidth(WINDOW_WIDTH);
		setHeight(WINDOW_HEIGTH);
		setMaximizable(true);
		setTitleCollapse(true);
		setHeading("Comentario para la tarea " + name);
		setLayout(new RowLayout(Orientation.VERTICAL));
	}

	/**
	 * Agrega la bara de botones
	 * @return
	 */
	private void addToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.add(save);
		toolBar.add(cancel);
		add(toolBar);
	}

	/**
	 * Agrega los campos 
	 * @param verticalPanel
	 */
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();

		//Se agrega editor HTML
		HorizontalPanel fieldHorizontalLine = new HorizontalPanel();
		fieldHorizontalLine.setWidth(new Integer(WINDOW_WIDTH));
		fieldHorizontalLine.add(htmlEditor);
		// Realiza la validacion del campo cuando pierde el foco
		htmlEditor.setAutoValidate(Boolean.TRUE);
		htmlEditor.setValidateOnBlur(Boolean.TRUE);
		htmlEditor.setWidth(FIELD_WIDTH);
		//htmlEditor.setHeight(DETAILS_HEIGTH);
		verticalPanel.add(fieldHorizontalLine);
		//htmlEditor.setAllowBlank(Boolean.FALSE);
		registerField(htmlEditor);
		
		return verticalPanel;
	}

	@Override
	public void onDelete() {
	}

	@Override
	public void onSave() {
		componentsDisabled();
		if (isValid()) {
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.ID, id);
			params.put(ParamsConst.COMMENT, htmlEditor.getValue());
			params.put(ServiceNameConst.SERVICIO,
						ServiceNameConst.ADD_COMMENT);
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							DialogError dialogError = new DialogError("No fue posible guardar el comentario.");
							componentsEnabled();
						}

						@Override
						public void onSuccess(Object result) {
							//FIXME: Tambien se deberia guardar el nuevo comentario en la tarea que esta almacenada en la grilla
							// de lo contrario luego de guardar un comentario al ir y volver entre tareas da la impresion de que el comentario se pierde
							// aunque lo almacena correctamente
							Context.getInstance().addHtml(htmlEditor.getValue());
							Context.getInstance().getWindowInstance(CommentWindows.class).setVisible(Boolean.FALSE);
							clear();
							componentsEnabled();
						}

					});

		} else {
			DialogError dialogError = new DialogError("El comentario no puede estar vacio.");
			componentsEnabled();
		}
	}
	
	protected Boolean isValid() {
		return (htmlEditor.getValue()!=null) && !"".equals(htmlEditor.getValue().trim());
	}

	@Override
	public void componentsEnabled() {
		save.setEnabled(Boolean.TRUE);
		cancel.setEnabled(Boolean.TRUE);
		
	}

	@Override
	public void componentsDisabled() {
		save.setEnabled(Boolean.FALSE);
		cancel.setEnabled(Boolean.FALSE);
		
	}

	@Override
	public void onModify() {
	}

	/**
	 * Se invoca cuando se presiona el boton cancelar
	 */
	public void onCancel(){
		htmlEditor.setValue("");
		this.setVisible(Boolean.FALSE);
	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
	}

}
