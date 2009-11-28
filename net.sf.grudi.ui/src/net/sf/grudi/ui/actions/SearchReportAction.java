package net.sf.grudi.ui.actions;

import java.util.Collections;
import java.util.Map;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.ui.editors.configuration.ReportConfiguration;
import net.sf.grudi.ui.editors.report.ReportEditor;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;


public class SearchReportAction<S extends SearchVO> extends Action {

	private ReportConfiguration<S> configuration;
	
	private ReportEditor<S> editor;
	
	public SearchReportAction(ReportEditor<S> editor, 
			ReportConfiguration<S> configuration) {
		super("Buscar");
		this.configuration = configuration;
		this.editor = editor;
	}
	
	@Override
	public void run() {
		if (configuration.isProtected()) {
			if (!configuration.hasPermission()) {
				MessageDialog.openError(null, "Protegido", "Seu usuário não possui acesso a este relatório");
				return;
			}
		}
		Map<String, Object> parametros = configuration.getParametros();
		parametros.put("_logo", FileLocator.find(configuration.getPlugin().getBundle(), 
					new Path("relatorio/logo.jpg"), null));
		editor.refreshReport(Collections.unmodifiableList(configuration.getSearchResult()), parametros);
	}
}
