package net.sf.grudi.ui.components.binding;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class WrapperValidator implements IValidator {

	private ControlDecoration controlDecoration;
	private IValidator validator;
	
	public WrapperValidator(IValidator validator, ControlDecoration controlDecoration) {
		this.controlDecoration = controlDecoration;
		this.validator = validator;
	}
	
	@Override
	public IStatus validate(Object value) {
		IStatus status = validator.validate(value);
		if (status.isOK()) {
			controlDecoration.hide();
			controlDecoration.setDescriptionText("");
		} else {
			controlDecoration.show();
			controlDecoration.setDescriptionText(status.getMessage());
		}
		return status;
	}

}
