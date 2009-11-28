package net.sf.grudi.ui.editors.configuration;

import java.util.ArrayList;
import java.util.List;

import net.sf.grudi.model.search.SearchVO;
import net.sf.grudi.model.vo.VO;
import net.sf.grudi.persistence.dao.AbstractDAO;
import net.sf.grudi.ui.actions.DeleteAction;
import net.sf.grudi.ui.actions.EditAction;
import net.sf.grudi.ui.actions.NewAction;
import net.sf.grudi.ui.actions.SaveAction;
import net.sf.grudi.ui.actions.SearchAction;
import net.sf.grudi.ui.components.table.GenericTableColumn;
import net.sf.grudi.ui.editors.crud.CRUDEditor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;



public abstract class CRUDConfiguration<T extends VO, S extends SearchVO> extends EditorConfiguration
		implements ISearchableConfiguration<S> {
	
	private T current;
	
	private S searchVO;
	
	private List<T> searchResult = new ArrayList<T>();
	
	public abstract void createSearchArea(Composite parent, FormToolkit toolkit);
	
	public abstract List<GenericTableColumn> createColumns();

	public abstract void createEditArea(Composite parent, FormToolkit toolkit);

	public abstract T newVO();
	
	public abstract S newSearchVO();
	
	public abstract void bind();

	public abstract AbstractDAO<T, S> getDAO();
	
	public abstract boolean canSave();
	
	public CRUDConfiguration() {
		searchVO = newSearchVO();
		Assert.isNotNull(searchVO, "SearchVO cannot be null");
	}

	public T getCurrent() {
		return current;
	}

	@SuppressWarnings("unchecked")
	public void setCurrent(VO current) {
		this.current = (T) current;
	}

	public List<T> getSearchResulst() {
		return searchResult;
	}

	public boolean add(T vo) {
		return searchResult.add(vo);
	}

	public boolean remove(VO vo) {
		return searchResult.remove(vo);
	}

	public void createEditAreaButtons(CRUDEditor<? extends VO, ? extends SearchVO> editor, IToolBarManager toolBarManager) {
		toolBarManager.add(new SaveAction(editor));
	}
	
	public void createSearchAreaButtons(CRUDEditor<T, S> editor, IToolBarManager toolBarManager) {
		toolBarManager.add(new SearchAction<T,S>(editor, this));
		toolBarManager.add(new Separator());
		toolBarManager.add(new NewAction(editor));
		toolBarManager.add(new EditAction(editor));
		toolBarManager.add(new DeleteAction(editor));
	}

	public void refreshPage() {
		// do nothing
	}

	public S getSearchVO() {
		return searchVO;
	}
	
	public List<T> getSearchResult() {
		return getDAO().list(getSearchVO());
	}
	
}
