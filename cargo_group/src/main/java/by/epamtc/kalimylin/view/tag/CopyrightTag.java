package by.epamtc.kalimylin.view.tag;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag.
 */
public class CopyrightTag extends TagSupport {
	
	private static final long serialVersionUID = 6698558147458810463L;
	private static final Logger logger = LogManager.getLogger();
	private static final String MESSAGE = "<p class=\"page-title__text\">2021 "
			+ "&copy; Copyright <a href=\"index.jsp\">Cargo Group</a>.</p>";

	@Override
	public int doStartTag() throws JspTagException {
		try {
			pageContext.getOut().write(MESSAGE);
		} catch (IOException e) {
			logger.log(Level.ERROR, "IO exception in CopyrightTag");
		}
		return SKIP_BODY;
	}
}
