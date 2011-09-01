package ar.com.AmberSoft.iEvenTask.client.validaciones;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;

public class IntegerValidator implements Validator {

	@Override
	public String validate(Field<?> field, String value) {
		
		try{
			new Integer((String)field.getValue());
		}catch (Exception e){
			return "Este campo admite solo valores enteros.";
		}
		
		return null;
	}

}
