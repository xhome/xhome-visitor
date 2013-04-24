package org.xhome.visitor.config;

import java.util.List;

import org.xhome.visitor.FileContent;

/**
 * @project xhome-visitor
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 24, 201311:10:45 PM
 */
public interface FileConfig {
	
	/**
	 * to difference jars and must unique of all
	 * @return
	 */
	public String content();
	
	/**
	 * file matchs
	 * {@link FileContent}
	 * the request will be like xxx/file?c=content&f=name
	 * @return
	 */
	public List<FileContent> fileContents();

}
