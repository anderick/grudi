package net.sf.grudi.ui.editors.configuration;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ConfigurationInput implements IEditorInput {
	
	private EditorConfiguration editorConfiguration;
	
	public ConfigurationInput(EditorConfiguration editorConfiguration) {
		this.editorConfiguration = editorConfiguration;
	}
	
	public EditorConfiguration getEditorConfiguration() {
		return editorConfiguration;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Name";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Tooltip";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((editorConfiguration == null) ? 0 : editorConfiguration
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurationInput other = (ConfigurationInput) obj;
		if (editorConfiguration == null) {
			if (other.editorConfiguration != null)
				return false;
		} else if (!editorConfiguration.equals(other.editorConfiguration))
			return false;
		return true;
	}
	
}
