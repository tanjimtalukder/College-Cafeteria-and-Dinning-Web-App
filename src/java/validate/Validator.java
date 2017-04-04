/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package validate;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author TanjimTalukder
 */
public class Validator {

    // ensures that quantity input is number between 0 and 99
    // applies to quantity fields in cart page
    public boolean validateQuantity (String productId, String quantity) {

        boolean errorFlag = false;

        if (!productId.isEmpty() && !quantity.isEmpty()) {

            int i = -1;

            try {

                i = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {

                System.out.println("User did not enter a number in the quantity field");
            }

            if (i < 0 || i > 99) {

                errorFlag = true;
            }
        }

        return errorFlag;
    }


    // performs simple validation on checkout form
    public boolean validateForm(String name,
                                String email,
                                String phone,
                                String address, 
                                String ccNumber,
                                HttpServletRequest request) {

        boolean errorFlag = false;
        boolean nameError;
        boolean emailError;
        boolean phoneError;
        boolean addressError; 
        boolean ccNumberError;

        if (name == null
                || name.equals("")
                || name.length() > 45) {
            errorFlag = true;
            nameError = true;
            request.setAttribute("nameError", nameError);
        }
        if (email == null
                || email.equals("")
                || !email.contains("@")
              //  || !Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email))
                || !Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.(?:[a-z]{2}|com|org|net|edu|gov|mil|\n" +
"biz|info|mobi|name|aero|asia|jobs|museum)$", email))
             {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9) {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }
        if (address == null
                || address.equals("")
                || address.length() > 45) {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        } 
        if (ccNumber == null
                || ccNumber.equals("")
                || ccNumber.length()  < 16) {
            errorFlag = true;
            ccNumberError = true;
            request.setAttribute("ccNumberError", ccNumberError);
        }

        return errorFlag;
    }

}