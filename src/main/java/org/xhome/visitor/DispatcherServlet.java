package org.xhome.visitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhome.util.ClassUtils;
import org.xhome.visitor.config.ActionConfig;

/**
 * @project xhome-visitor
 * @author jhat
 * @email cpf624@126.com
 * @date May 24, 20121:32:42 PM
 */
public class DispatcherServlet extends HttpServlet {
	
	private static final long	serialVersionUID	= 7493179885484314543L;
	private Map<String, String>	actions;
	private Map<String, Action>	actions_cache;
	private Logger				logger;
	
	@Override
	public void init() throws ServletException {
		actions = new HashMap<String, String>();
		actions_cache = new HashMap<String, Action>();
		logger = LoggerFactory.getLogger(DispatcherServlet.class);
		
		// add fileAction
		actions.put("file", FileAction.class.getName());
		
		try {
			Set<Class<?>> clazzs = ClassUtils.findClasses(ActionConfig.class);
			if (clazzs != null) {
				for(Class<?> clazz : clazzs) {
					ActionConfig ac = (ActionConfig) clazz.newInstance();
					Map<String, String> acs = ac.actions();
					logger.debug("load action config {}", acs);
					actions.putAll(acs);
				}
			}
		} catch (Exception e) {
			logger.error("fail to load actions", e);
			throw new ServletException(e.getMessage(), e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] uris = request.getRequestURI().split("/");
		String at = uris[uris.length - 1];
		logger.debug("begin process action of {}", at);
		Action action = actions_cache.get(at);
		if (action == null) {
			try {
				synchronized (this) {
					if (action == null) {
						logger.debug("try to load action of {}", at);
						action = (Action) ClassUtils.loadClass(actions.get(at),
								DispatcherServlet.class).newInstance();
						logger.debug("success to load action of {}", at);
					}
				}
			} catch (Exception e) {
				logger.error("fail to load action of {}", at);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}
		action.execute(request, response);
		logger.debug("end process action of {}", at);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
}
