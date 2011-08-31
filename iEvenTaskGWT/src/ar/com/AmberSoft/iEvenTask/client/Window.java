package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * Representa una ventan estandar para la aplicacion
 * @author Leo
 *
 */
public abstract class Window extends com.extjs.gxt.ui.client.widget.Window {

	protected State windowState;
	protected Context context = Context.getInstance();
	protected Collection<Field> fields = new ArrayList<Field>();
	
	
	private List<Field> toValidate = new ArrayList<Field>();
	
	public Window(){
		windowState =  State.UNKNOW_STATE;
		setIcon(Resources.ICONS.table());
		setLayout(new FitLayout());  
	}
	
	/**
	 * Retorna el estado de la ventana actual en la aplicacion para el cliente actual
	 * @return
	 */
	public State getWindowState(){
		return windowState;
	}
	
	/**
	 * Establece el estado de la ventana actual
	 * @param state
	 */
	public void setWindowState(State state){
		windowState = state;
	}
	
	/**
	 * Antes de ser visualizada por primera vez se intenta registrar la ventana
	 */
	@Override
	protected void afterShow() {
		context.registerWindow(this);
		super.afterShow();
	}


	/**
	 * Valida que todos los campos a validar sean validos
	 * @return
	 */
	protected Boolean isValid() {
		Boolean valid = Boolean.TRUE;
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			if (!field.isValid()) {
				valid = Boolean.FALSE;
			}
		}
		return valid;
	}
	
	protected void clear() {
		Boolean valid = Boolean.TRUE;
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			field.clear();
		}
	}	

	/**
	 * Registra el campo 
	 * @param field
	 */
	protected void registerField(Field field){
		if (field != null) {
			fields.add(field);
		}
	}
	
	/**
	 * Se invoca cuando se presiona el boton cancelar
	 */
	public void onCancel(){
		clear();
		windowState = State.UNKNOW_STATE;
	}

	/**
	 * Se invoca cuando se realiza una accion de borrado
	 */
	public void onDelete(){
		clear();
		setWindowState(State.UNKNOW_STATE);
	}
	
	/**
	 * Se invoca cuando se realiza una accion de seleccion
	 */
	public void onSelect(List selected){
		if (selected.size() == 1) {
			Iterator it = selected.iterator();
			if (it.hasNext()) {
				beforeUpdate((BaseModel) it.next());
				windowState = State.UPDATE_STATE;
			}
		} else {
			clear();
			windowState = State.UNKNOW_STATE;
		}
	}
	
	/**
	 * Se invoca antes de actualizar 
	 * Por lo general utilizado para cargar los datos de la grilla a los campos correspondientes
	 * @param baseModel
	 */
	public abstract void beforeUpdate(BaseModel baseModel);
	
	/**
	 * Se invoca cuando se presiona el boton save
	 */
	public abstract void onSave();
	
	/**
	 * Genera un panel horizontal con una etiqueta y el campo correspondiente
	 * @param field
	 * @param labelText
	 * @param lineWith
	 * @param labelWidth
	 * @return
	 */
	protected HorizontalPanel getFieldHorizontalLine(Field field, String labelText, String lineWith, String labelWidth) {
		HorizontalPanel fieldHorizontalLine = new HorizontalPanel();
		fieldHorizontalLine.setWidth(lineWith);
		LabelField labelField = new LabelField(labelText);
		fieldHorizontalLine.add(labelField);
		labelField.setWidth(labelWidth);
		fieldHorizontalLine.add(field);
		field.setAutoValidate(Boolean.TRUE);
		return fieldHorizontalLine;
	}
	
}