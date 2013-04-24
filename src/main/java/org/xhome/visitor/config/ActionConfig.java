package org.xhome.visitor.config;

import java.util.Map;

import org.xhome.visitor.Action;

/**
 * @project xhome-visitor
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 24, 201310:34:05 PM
 */
public interface ActionConfig {
	
	/**
	 * config the actions
	 * key
	 * 	the name of the action
	 * value
	 * 	the full class name of the implements of {@link Action}
	 * 
	 * the request will be like xxx/name
	 * @return
	 */
	public Map<String, String> actions();

}
