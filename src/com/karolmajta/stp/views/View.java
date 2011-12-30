package com.karolmajta.stp.views;

import com.karolmajta.stp.exception.UnboundViewException;

import processing.core.PApplet;

/**
 * Abstract class for creating specialized views for concrete models.
 * Makes given Model instance accessible via protected model field.
 * 
 * @author Karol
 *
 * @param <Model> Model this View will draw
 */
public abstract class View<Model> implements IDrawable<Model> {
	private boolean visible;
	
	protected Model model;
	
	public View() {
		this.visible = true;
	}
	
	@Override
	public final void bindModel(Model model) {
		this.model = model;
	}

	@Override
	public final void draw(PApplet p) throws UnboundViewException {
		if(model == null) {
			throw new UnboundViewException();
		}
		if(visible){
			onDraw(p);
		}
	}
	
	@Override
	public final void setVisible(boolean flag) {
		visible = flag;
	}
	
	@Override
	public final boolean isVisible() {
		return visible;
	}
	
	/**
	 * Protected field model referencing object bound with
	 * {@link View#bindModel(Object)} can be used inside this method.
	 * @param p
	 */
	protected abstract void onDraw(PApplet p);
}