package com.lanyine.manifold.web.editor;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateEditorSupport extends PropertyEditorSupport {

	private static final String DATEFORMAT = "yyyy-MM-dd";
	private static final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormat dateFormat;
    private boolean allowEmpty = true;

    public DateEditorSupport() {
    }

    public DateEditorSupport(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateEditorSupport(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            try {
                if(this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
				else {
					if (text.contains(":"))
						setValue(new SimpleDateFormat(TIMEFORMAT).parse(text));
					else
						setValue(new SimpleDateFormat(DATEFORMAT).parse(text));
				}
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
	public String getAsText() {
		Date value = (Date) getValue();
		DateFormat dateFormat = this.dateFormat;
		if (dateFormat == null)
			dateFormat = new SimpleDateFormat(TIMEFORMAT);
		return (value != null ? dateFormat.format(value) : "");
	}
}
