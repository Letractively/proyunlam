package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.extjs.gxt.ui.client.widget.ContentPanel;

public class CommentWindows extends Window {

	public static final Integer WINDOW_WIDTH = 515;
	public static final Integer WINDOW_HEIGTH = 380;
	
	public static final Integer FIELD_WIDTH = WINDOW_WIDTH - 10;
	public static final Integer LABEL_WIDTH = 400;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	private Integer id;
	
	final Button save = new SaveButton(this);
	final Button cancel = new CancelButton(this);
	HtmlEditor htmlEditor = new HtmlEditor();
	
	public CommentWindows(Integer id, String name) {
		super();

		this.id = id;
		
		initialize(name);

		addToolBar();
		
		ContentPanel cntntpnlNewContentpanel = new ContentPanel();
		cntntpnlNewContentpanel.setHeaderVisible(false);
		
		cntntpnlNewContentpanel.add(htmlEditor);
		add(cntntpnlNewContentpanel, new RowData(Style.DEFAULT, 300.0, new Margins()));
		
	}

	/**
	 * Inicializa la ventana actual
	 */
	private void initialize(String name) {
		setInitialWidth(WINDOW_WIDTH);
		setHeight(362);
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

	@Override
	public void onDelete() {
	}

	@Override
	public void onSave() {
		maskAvaiable();
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
							maskDisable();
						}

						@Override
						public void onSuccess(Object result) {
							//FIXME: Tambien se deberia guardar el nuevo comentario en la tarea que esta almacenada en la grilla
							// de lo contrario luego de guardar un comentario al ir y volver entre tareas da la impresion de que el comentario se pierde
							// aunque lo almacena correctamente
							Context.getInstance().addComment(
									id,
									htmlEditor.getValue(),
									new Date(), 
									Context.getInstance().getUserName());
							
							clear();
							maskDisable();
							close();
						}

					});

		} else {
			DialogError dialogError = new DialogError("El comentario no puede estar vacio.");
			maskDisable();
		}
	}
	
	
	protected Boolean isValid() {
		return (htmlEditor.getValue()!=null) && !"".equals(htmlEditor.getValue().trim());
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
