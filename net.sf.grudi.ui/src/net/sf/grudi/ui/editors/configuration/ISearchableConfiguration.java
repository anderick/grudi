package net.sf.grudi.ui.editors.configuration;

import net.sf.grudi.model.search.SearchVO;

public interface ISearchableConfiguration<S extends SearchVO> {

	public abstract S getSearchVO();

}