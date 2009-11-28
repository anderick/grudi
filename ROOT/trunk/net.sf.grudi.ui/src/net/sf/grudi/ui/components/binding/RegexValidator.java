package net.sf.grudi.ui.components.binding;

import java.util.regex.Pattern;

import net.sf.grudi.ui.UIPlugin;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;


/**
 * 
 * @see Pattern
 */
public class RegexValidator implements IValidator {

	private String message;
	private String regex;
	
	public RegexValidator(String regex, String message) {
		this.message = message;
		this.regex = regex;
	}
	
	@Override
	public IStatus validate(Object value) {
		IStatus status = new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, message);
		String s = String.valueOf(value);
		if (Pattern.matches(regex, s)) {
			return Status.OK_STATUS;
		}
		return status;
	}

}
