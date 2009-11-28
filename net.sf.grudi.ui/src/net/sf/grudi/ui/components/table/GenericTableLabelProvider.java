package net.sf.grudi.ui.components.table;

import java.util.List;

import net.sf.grudi.ui.UIPlugin;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


public class GenericTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	private List<GenericTableColumn> tableColumnList;

	public GenericTableLabelProvider(List<GenericTableColumn> tableColumnList) {
		Assert.isNotNull(tableColumnList);
		this.tableColumnList = tableColumnList;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		GenericTableColumn tableCol = tableColumnList.get(columnIndex);
		try {
			Object value = PropertyUtils.getProperty(element, tableCol.getFieldName());
			if (value == null) {
				return "";
			}
			if (tableCol.getFormatter() != null) {
				value = tableCol.getFormatter().format(tableCol.getType().cast(value));
			}
			return String.valueOf(value);
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, UIPlugin.PLUGIN_ID,
					"Error loading property to the table column", e);
			UIPlugin.getDefault().getLog().log(status);
			return "";
		}
	}

}
