/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package playbackbuttons;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

/**
 *
 * @author Alexandra
 */

public class CheckMenuItem extends RadialMenuItem {

    protected boolean selected = false;

    protected Paint selectedColor;

    protected Paint selectedMouseOnColor;

    public CheckMenuItem(final double menuSize, final Node graphic) {
	super(menuSize, graphic);
    }

    public CheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected) {
	this(menuSize, graphic);
	this.selected = selected;
    }

    public CheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected, final Paint selectedColor) {
	this(menuSize, graphic, selected);
	this.selectedColor = selectedColor;
    }

    public CheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected, final Paint selectedColor,
	    final Paint selectedMouseOnColor) {
	this(menuSize, graphic, selected);
	this.selectedColor = selectedColor;
	this.selectedMouseOnColor = selectedMouseOnColor;
    }

    @Override
    protected void redraw() {
	super.redraw();

	Paint color = null;
	if (this.backgroundVisible.get()) {
	    if (this.selected && this.selectedColor != null) {
		if (this.mouseOn && this.selectedMouseOnColor != null) {
		    color = this.selectedMouseOnColor;
		} else {
		    color = this.selectedColor;
		}
	    } else {
		if (this.mouseOn && this.backgroundMouseOnColor != null) {
		    color = this.backgroundMouseOnColor.get();
		} else {
		    color = this.backgroundColor.get();
		}
	    }
	}

	this.path.setFill(color);
    }

    @Override
    void setSelected(final boolean selected) {
	this.selected = selected;
	this.redraw();
    }

    @Override
    boolean isSelected() {
	return this.selected;
    }

}
