package net.sf.grudi.ui.editors.plan;

import net.sf.grudi.ui.UIPlugin;
import net.sf.grudi.ui.editors.AbstractEditor;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;
import net.sf.grudi.ui.editors.configuration.PlanConfiguration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public class PlanEditor extends AbstractEditor {
	
	public static final String EDITOR_ID = "net.sf.grudi.ui.planEditor";

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(getEditorConfiguration().getName());
	}
	
	@Override
	protected void addPages() {
		try {
			addPage(new PlanPage(this));
		} catch (PartInitException e) {
			UIPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error to add pages to Plan Editor", e));
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}
	
	private PlanConfiguration<?, ?> getEditorConfiguration() {
		return (PlanConfiguration<?,?>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}

}
