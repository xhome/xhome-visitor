package org.xhome.visitor;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project xhome-visitor
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 24, 20139:47:46 PM
 */
public interface Action extends Serializable {
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException;

}
