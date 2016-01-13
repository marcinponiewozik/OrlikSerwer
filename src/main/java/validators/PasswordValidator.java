/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Marcin
 */
@FacesValidator(value = "passwordValidator")
public class PasswordValidator implements Validator{

    
	@Override
	public void validate(FacesContext context, UIComponent component,
		Object value) throws ValidatorException {
 
	  String haslo = value.toString();
 
	  UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
		.get("haslo2");
	  String powtorzHaslo = uiInputConfirmPassword.getSubmittedValue()
		.toString();
 
	  // Let required="true" do its job.
	  if (haslo == null || haslo.isEmpty() || powtorzHaslo == null
		|| powtorzHaslo.isEmpty()) {
			return;
	  }
 
	  if (!haslo.equals(powtorzHaslo)) {
		uiInputConfirmPassword.setValid(false);
		throw new ValidatorException(new FacesMessage(
			"Hasła różnią się od siebie"));
	  }
 
	}
    
}
