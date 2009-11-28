package net.sf.grudi.ui.components.binding;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;

public class ImageObservableValue extends AbstractObservableValue {

	private final Label label;

	protected Image oldValue;

	PaintListener listener = new PaintListener() {

		@Override
		public void paintControl(PaintEvent e) {
			Image newValue = label.getImage();

			if (newValue != null && !newValue.equals(ImageObservableValue.this.oldValue)) {
				fireValueChange(Diffs.createValueDiff(
						ImageObservableValue.this.oldValue, newValue));
				ImageObservableValue.this.oldValue = newValue;

			}
		}

	};

	public ImageObservableValue(final Label label) {
		this.label = label;
		this.label.addPaintListener(this.listener);
	}

	@Override
	protected Object doGetValue() {
		return label.getImage();
	}

	@Override
	protected void doSetValue(final Object value) {
		if (value instanceof Image) {
			Image i = (Image) value;
			inputStreamToLabel(i);
		}
	}

	@Override
	public Object getValueType() {
		return Image.class;
	}

	private void inputStreamToLabel(final Image i) {
		if (!this.label.isDisposed()) {
			label.setImage(i);
		}
		
	}

	@Override
	public synchronized void dispose() {
		this.label.removePaintListener(this.listener);
		super.dispose();
	}

}
