package net.sf.grudi.ui.components.binding;

import net.sf.grudi.ui.UIPlugin;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;


public class RequiredValidator implements IValidator {

	@Override
	public IStatus validate(Object value) {
		IStatus status = new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "O preenchimento deste campo é obrigatório");
		if (value != null && !String.valueOf(value).isEmpty()) {
			return Status.OK_STATUS;
		}
		return status;
	}

}
