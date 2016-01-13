/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import beansDB.UzytkownikBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Marcin
 */
@FacesValidator("loginValidator")
public class LoginValidator implements Validator{
    
    private static final String LOGIN_PATTERN = "^[_A-Za-z0-9-]{5,20}$";

    private Pattern pattern;
    private Matcher matcher;

    public LoginValidator() {
        pattern = Pattern.compile(LOGIN_PATTERN);
    }

    @EJB
    private UzytkownikBean request;
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        

        String login = value.toString();
        matcher = pattern.matcher(login);
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("Niepoprawna nazwa uzytkownika.",
                            "Niepoprawna nazwa uzytkownika( Dzowolone znaki: A-Z, a-z, 0-9 min 5 max 20)"
                            + ".");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if(request.checkSingleUser(login)){
            FacesMessage msg
                    = new FacesMessage("Niepoprawna nazwa uzytkownika.",
                            "Uzytkownik o takiej nazwie już istnieje w bazie danych"
                            + ".");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
}
