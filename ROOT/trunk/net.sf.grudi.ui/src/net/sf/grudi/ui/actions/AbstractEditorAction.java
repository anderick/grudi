package net.sf.grudi.ui.actions;

import net.sf.grudi.ui.editors.AbstractEditor;

import org.eclipse.jface.action.Action;


public class AbstractEditorAction<E extends AbstractEditor> extends Action {

	private E editor;
	
	public AbstractEditorAction(String text, E editor) {
		super(text);
		this.editor = editor;
	}

	public E getEditor() {
		return editor;
	}

	public void setEditor(E editor) {
		this.editor = editor;
	}
	
}
