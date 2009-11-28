package net.sf.grudi.ui.actions;

import net.sf.grudi.ui.UIPlugin;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;
import net.sf.grudi.ui.editors.configuration.EditorConfiguration;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class OpenEditorAction extends Action {

	private String editorId;
	private EditorConfiguration editorConfig;
	
	public OpenEditorAction(String text, String editorId, EditorConfiguration editorConfig) {
		super(text);
		Assert.isNotNull(editorId, "Editor ID cannot be null.");
		Assert.isNotNull(editorConfig, "Editor Config cannot be null.");
		this.editorId = editorId;
		this.editorConfig = editorConfig;
	}
	
	@Override
	public void run() {
		try {
			editorConfig.init();
			IWorkbenchWindow wWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			wWindow.getActivePage().openEditor(new ConfigurationInput(editorConfig), editorId);
		} catch (PartInitException e) {
			UIPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error durin open editor", e));
		}
	}
	
}
