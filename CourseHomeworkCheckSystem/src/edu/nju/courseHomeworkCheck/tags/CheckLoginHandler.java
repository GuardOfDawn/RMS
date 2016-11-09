package edu.nju.courseHomeworkCheck.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CheckLoginHandler extends BodyTagSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int doEndTag() throws JspException{
		HttpSession session;
		return SKIP_PAGE;
	}

}
