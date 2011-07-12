package ar.com.AmberSoft.iEvenTask.client.validaciones;

import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.Validator;

public class ValidaMultiField implements Validator {
	
	private int min = 1;
	
	public ValidaMultiField(){}
	
	public ValidaMultiField(int min){
		this.min = min;
	}

	@Override
	public String validate(Field<?> field, String value) {
		int select = 0;
		if (field instanceof MultiField) {
			MultiField multiField = (MultiField) field;
			List fields = multiField.getAll();
			Iterator it = fields.iterator();
			while (it.hasNext()) {
				Field actualField = (Field) it.next();
				if (actualField.getValue()==Boolean.TRUE){
					select++;
				}
			}
			
		}
		if (select<min){
			String opcion = "opcion";
			if (min>1){
				opcion += "es";
			}
			return "Debe seleccionar al menos " + Integer.toString(min) + " " + opcion + ". Restan " + Integer.toString(min-select) + " mas.";
		}
		return null;
	}

}
