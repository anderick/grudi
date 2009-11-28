package net.sf.grudi.ui.editors.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.persistence.dao.AbstractDAO;
import net.sf.grudi.ui.actions.SearchReportAction;
import net.sf.grudi.ui.editors.report.ReportEditor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;


public abstract class ReportConfiguration<S extends SearchVO> extends
		EditorConfiguration implements ISearchableConfiguration<S> {
	
	private S searchVO;

	public abstract void createFilterArea(Composite parent, FormToolkit toolkit);

	public abstract Plugin getPlugin();
	
	public abstract String getReportFile();

	public abstract AbstractDAO<?, S> getDAO();

	public abstract void bind();

	public abstract S newSearchVO();
	
	public abstract boolean isProtected();

	public ReportConfiguration() {
		searchVO = newSearchVO();
		Assert.isNotNull(searchVO, "SearchVO cannot be null");
	}

	public void createFilterAreaButtons(ReportEditor<S> editor,
			IToolBarManager toolBarManager) {
		toolBarManager.add(new SearchReportAction<S>(editor, this));
	}
	
	public S getSearchVO() {
		return searchVO;
	}
	
	public List<? extends VO> getSearchResult() {
		return getDAO().list(getSearchVO());
	}

	/**
	 * This method build a map of parameters that can be used by the report.
	 * <br/>
	 * This method cannot return null.
	 * 
	 * @return map of parameters 
	 */
	public Map<String, Object> getParametros() {
		return new HashMap<String, Object>();
	}
	
	public InputStream getReportInputStream() throws IOException {
		Path file = new Path(getReportFile());
		return FileLocator.openStream(getPlugin().getBundle(), 
				file, false);
	}

	public boolean hasPermission() {
		return false;
	}

}
