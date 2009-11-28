package net.sf.grudi.ui.editors.report;

import java.util.List;
import java.util.Map;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.ui.UIPlugin;
import net.sf.grudi.ui.editors.AbstractEditor;
import net.sf.grudi.ui.editors.configuration.ConfigurationInput;
import net.sf.grudi.ui.editors.configuration.ReportConfiguration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public class ReportEditor<S extends SearchVO> extends AbstractEditor {

	public static final String EDITOR_ID = "net.sf.grudi.ui.reportEditor";
	
	private ReportPage<S> reportPage;
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(getEditorConfiguration().getName());
	}
	
	@Override
	protected void addPages() {
		try {
			reportPage = new ReportPage<S>(this);
			addPage(reportPage);
		} catch (PartInitException e) {
			UIPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID, "Error to add pages to Report Editor", e));
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@SuppressWarnings("unchecked")
	public ReportConfiguration<S> getEditorConfiguration() {
		return (ReportConfiguration<S>) ((ConfigurationInput)getEditorInput()).getEditorConfiguration();
	}
	
	public void refreshReport(List<VO> list, Map<String, Object> parametros) {
		reportPage.refreshReport(list, parametros);
	}
}
