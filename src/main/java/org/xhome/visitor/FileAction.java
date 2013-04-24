package org.xhome.visitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhome.file.FileUtils;
import org.xhome.util.ClassUtils;
import org.xhome.visitor.config.FileConfig;

/**
 * @project cuirass
 * @author jhat
 * @email cpf624@126.com
 * @date May 24, 20127:21:50 PM
 */
public class FileAction implements Action {
	
	private static final long							serialVersionUID	= -2347168052613551137L;
	private final Map<String, Map<String, FileContent>>	fileContents;
	private final Logger								logger;
	private final String								PARAM_CONTENT		= "c";
	private final String								PARAM_FILE			= "f";
	
	public FileAction() {
		fileContents = new HashMap<String, Map<String, FileContent>>();
		logger = LoggerFactory.getLogger(FileAction.class);
		
		try {
			Set<Class<?>> clazzs = ClassUtils.findClasses(FileConfig.class);
			if (clazzs != null) {
				for (Class<?> clazz : clazzs) {
					FileConfig fileConfig = (FileConfig) clazz.newInstance();
					String content = fileConfig.content();
					
					if (logger.isDebugEnabled()) {
						logger.debug("load file content of " + content);
					}
					
					Map<String, FileContent> contents = fileContents.get(content);
					if (contents == null) {
						contents = new HashMap<String, FileContent>();
						fileContents.put(content, contents);
					}
					List<FileContent> list = fileConfig.fileContents();
					if (list != null) {
						for (FileContent fc : list) {
							String name = fc.getName();
							contents.put(name, fc);
							
							if (logger.isDebugEnabled()) {
								logger.debug("load file content of " + content + " with file " + name);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String content = request.getParameter(PARAM_CONTENT);
		String file = request.getParameter(PARAM_FILE);
		if (content != null && file != null) {
			Map<String, FileContent> contents = fileContents.get(content);
			if (contents != null) {
				FileContent fc = contents.get(file);
				
				if (fc != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("load file " + file + " from content of " + content);
					}
					
					response.setContentType(fc.getType());
					InputStream in = ClassUtils.getResourceAsStream(fc.getPath(), FileAction.class);
					if (in != null) {
						OutputStream out = response.getOutputStream();
						FileUtils.copy(in, out);
						out.flush();
						in.close();
						return;
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("file " + file + " not exists in the content of " + content);
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("the content of " + content + " is not exists");
				}
			}
		} else {
			logger.warn("missing content or file param");
		}
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
}
