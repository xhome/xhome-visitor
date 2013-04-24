0. config org.xhome.visitor.DispatcherServlet as servlet in web.xml

1. implements org.xhome.visitor.Action to process request

2. implements org.xhome.visitor.config.ActionConfig to config the actions
    and the class must be under the package of org.xhome.visitor.config
    so that the DispatcherServlet can load the action.
    
    the result should be map of name and class full name, and the name must be unique of all.

3. implements org.xhome.visitor.config.FileConfig to config the files
    and the class must be under the package of org.xhome.visitor.config
    so that the FileAction can load the file configs.

    the request will be like xxx/file?c=content&f=file_name

    param of c is to difference jars and should be unique of all
    param of f is the file name to request in content

    FileContent
        name    the request param f's value
        type    the response content type
        path    the full path of the file

4. action example
package org.xhome.visitor.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xhome.visitor.Action;

/**
 * @project xhome-test
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 25, 201312:05:39 AM
 */
public class TestAction implements Action {

	private static final long	serialVersionUID	= 8324240929490726813L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getOutputStream().write("Test Action".getBytes());
	}

}


5. action config example
package org.xhome.visitor.config;

import java.util.HashMap;
import java.util.Map;

import org.xhome.visitor.action.TestAction;

/**
 * @project xhome-test
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 25, 201312:05:25 AM
 */
public class TestActionConfig implements ActionConfig {

	@Override
	public Map<String, String> actions() {
		Map<String, String> maps = new HashMap<String, String>();
		// xxx/test
		maps.put("test", TestAction.class.getName());
		return maps;
	}

}

6. file config
package org.xhome.visitor.config;

import java.util.ArrayList;
import java.util.List;

import org.xhome.visitor.FileContent;

/**
 * @project xhome-test
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 25, 201312:07:18 AM
 */
public class TestFileConfig implements FileConfig {

	@Override
	public String content() {
		return "test";
	}

	@Override
	public List<FileContent> fileContents() {
		List<FileContent> contents = new ArrayList<FileContent>();
		// xxx/file?c=test&f=cuirass.png
		FileContent c = new FileContent("cuirass.png", "cuirass.png", "image/png");
		contents.add(c);
		return contents;
	}
	
}
