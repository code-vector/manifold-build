package com.lanyine.manifold.web.editor;

import java.beans.PropertyEditorSupport;

/**
 * 与spring mvc的@InitBinder结合 用于防止XSS攻击
 */
public class XssEscapeEditorSupport extends PropertyEditorSupport {

    public XssEscapeEditorSupport() {
        super();
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            if (text.indexOf("<") > 0 || text.indexOf(">") > 0) {// 文本中有<>就进行xssfilter
//				text = XssFilter.cleanRichText(text);
            }
            setValue(text);
        }
    }


}