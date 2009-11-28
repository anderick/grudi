package net.sf.grudi.ui.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public abstract class AbstractEditor extends FormEditor implements PropertyChangeListener {
	
	private boolean dirty;

	@Override
	public void doSaveAs() {}

	@Override
	public boolean isSaveAsAllowed() { return false; }
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setDirty(true);
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		firePropertyChange(PROP_DIRTY);
	}
	
	public void showMessage(final FormPage page, final String message, final int type) {
		page.getManagedForm().getForm().setMessage(message, type);
	}
	
}
