package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * Representa una ventan estandar para la aplicacion
 * @author Leo
 *
 */

@SuppressWarnings("rawtypes")
public abstract class Window extends com.extjs.gxt.ui.client.widget.Window implements Seleccionable{

	public static final Integer EXTRA_WIDTH = 20;
	
	protected State windowState;
	protected Collection<Field> fields = new ArrayList<Field>();
	
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
	

	@SuppressWarnings("unused")
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
	 * @param fieldWith
	 * @param labelWidth
	 * @return
	 */
	protected HorizontalPanel getFieldHorizontalLine(Field field, String labelText, Integer fieldWith, Integer labelWidth) {
		HorizontalPanel fieldHorizontalLine = new HorizontalPanel();
		fieldHorizontalLine.setWidth(new Integer(fieldWith + labelWidth + EXTRA_WIDTH));
		LabelField labelField = new LabelField(labelText);
		fieldHorizontalLine.add(labelField);
		labelField.setWidth(labelWidth);
		fieldHorizontalLine.add(field);
		// Realiza la validacion del campo cuando pierde el foco
		field.setAutoValidate(Boolean.TRUE);
		field.setValidateOnBlur(Boolean.TRUE);
		field.setWidth(fieldWith);
		return fieldHorizontalLine;
	}
	 
	/**
	 * Retorna un Model Data Basico
	 * Usualmente utilizado en los combos
	 * @param key
	 * @param value
	 * @return
	 */
	protected BaseModel getModelData(String key, String value) {
		BaseModel baseModel = new BaseModel();
		baseModel.set("key", key);
		baseModel.set("text", value);
		return baseModel;
	}
	
	/**
	 * Setea el valor seleccionado de un combo
	 * @param comboBox
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	protected void setCombo(ComboBox comboBox, String key) {
		ListStore<ModelData> listStore = comboBox.getStore();
		ModelData modelData = null;
		Iterator it = listStore.getModels().iterator();
		while (it.hasNext()) {
			ModelData mdAux = (ModelData) it.next();
			//Context.getInstance().addDetailExecution("Comparando " + key + " con " + mdAux.get("key"));
			if (key.trim().equals(mdAux.get("key").toString().trim())){
				modelData = mdAux;
				break;
			}
		}
		comboBox.setValue(modelData);
	}
	
	public void maskAvaiable(){
		this.mask("Aguarde un momento...");
	}
	
	public void maskDisable(){
		this.unmask();
	}
	
}
