package com.rahul.zeppelinonlinebank.filters;

import java.beans.PropertyEditorSupport;
import org.springframework.web.util.HtmlUtils;

public class HtmlEscapeStringEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String out = "";
		if (text != null)
			out = HtmlUtils.htmlEscape(text.trim());

		setValue(out);
	}

	@Override
	public String getAsText() {
		String out = (String) getValue();
		if (out == null)
			out = "";
		return out;
	}
}