package net.sf.grudi.ui.components.binding;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;


public class BindingUtils {

	public static DataBindingContext createContext(Control c) {
	    final DataBindingContext dbc = new DataBindingContext();
	    c.addDisposeListener(new DisposeListener() {
	    	@Override
	    	public void widgetDisposed(DisposeEvent e) {
	    		dbc.dispose();
	    	}
	    });
	    return dbc;
	}
	
	public static Binding addTextBinding(DataBindingContext context, Control control, Object o, String property) {
		return addTextBinding(context, control, o, property, null);
	}
	
	public static Binding addTextBinding(DataBindingContext context, Control control, Object o, String property, IValidator validator) {
		IObservableValue oTarget = SWTObservables.observeText(control, SWT.Modify);
		IObservableValue oModel = PojoObservables.observeValue(o, property);
		UpdateValueStrategy targetToModel = prepareValidator(control, validator, null);
		return context.bindValue(oTarget, oModel, targetToModel, null);
	}
	
	public static Binding addImageBinding(DataBindingContext context, Label label, Object o, String property) {
		IObservableValue oTarget = new ImageObservableValue(label);
		IObservableValue oModel = PojoObservables.observeValue(o, property);
		return context.bindValue(oTarget, oModel, null, null);
	}

	public static Binding addDateTimeBinding(DataBindingContext context, DateTime dateTime, Object o, String property) {
		return addDateTimeBinding(context, dateTime, o, property, null);
	}
	
	public static Binding addDateTimeBinding(DataBindingContext context, DateTime dateTime, Object o, String property, IValidator validator) {
		IObservableValue oTarget = new DateTimeObservableValue(dateTime);
		IObservableValue oModel = PojoObservables.observeValue(o, property);
		UpdateValueStrategy targetToModel = prepareValidator(dateTime, validator, null);
		return context.bindValue(oTarget, oModel, targetToModel, null);
	}

	public static Binding addComboBinding(DataBindingContext context, final Combo combo, Object o, String property) {
		return addComboBinding(context, combo, o, property, null);
	}
	
	public static Binding addComboBinding(DataBindingContext context, final Combo combo, Object o, String property, IValidator validator) {
		IObservableValue oTarget = SWTObservables.observeText(combo);
		IObservableValue oModel = PojoObservables.observeValue(o, property);
		UpdateValueStrategy targetToModel = new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return combo.getData(String.valueOf(value));
			}
		};
		prepareValidator(combo, validator, targetToModel);
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null) {
					combo.deselectAll();
				}
				for (String item : combo.getItems()) {
					if (combo.getData(item).equals(value)) {
						combo.select(combo.indexOf(item));
						break;
					}
				}
				return value;
			}
		};
		return context.bindValue(oTarget, oModel, targetToModel, modelToTarget);
	}
	
	public static Binding addCheckBinding(DataBindingContext context,
			Button button, Object o, String property) {
		IObservableValue oTarget = SWTObservables.observeSelection(button);
		IObservableValue oModel = PojoObservables.observeValue(o, property);
		return context.bindValue(oTarget, oModel, null, null);
	}
	
	// Internal utilities

	private static ControlDecoration createDecorator(Control control) {
		ControlDecoration controlDecoration = new ControlDecoration(control,
				SWT.LEFT | SWT.TOP);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}
	
	private static UpdateValueStrategy prepareValidator(Control control, IValidator validator, UpdateValueStrategy uvs) {
		UpdateValueStrategy targetToModel = uvs;
		if (validator != null) {
			WrapperValidator wv = new WrapperValidator(validator, createDecorator(control));
			if (targetToModel == null) {
				targetToModel = new UpdateValueStrategy();
			}
			targetToModel.setAfterConvertValidator(wv);
		}
		return targetToModel;
	}

}
