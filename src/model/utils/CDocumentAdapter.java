package model.utils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class represent the helper to make filter (currently to help to find video game easier)
 */
public abstract class CDocumentAdapter implements DocumentListener {
    public abstract void update();

    @Override public void insertUpdate(DocumentEvent e) { update(); }
    @Override public void removeUpdate(DocumentEvent e) { update(); }
    @Override public void changedUpdate(DocumentEvent e) { update(); }
}