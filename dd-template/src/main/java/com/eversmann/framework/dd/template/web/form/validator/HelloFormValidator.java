package com.eversmann.framework.dd.template.web.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eversmann.framework.dd.template.domain.Hello;

/**
 * @author dje
 *
 */
@Component("helloFormValidator")
public class HelloFormValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Hello.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.required", "Name is a required field");
	}

}
