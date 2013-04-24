package org.xhome.visitor;

/**
 * @project xhome-visitor
 * @author jhat
 * @email cpf624@126.com
 * @date Apr 24, 201310:47:37 PM
 */
public class FileContent {
	
	private String	name;
	private String	path;
	private String	type;
	
	/**
	 * @param name
	 *            request file name
	 * @param path
	 *            full path of the file
	 * @param type
	 *            response content type
	 */
	public FileContent(String name, String path, String type) {
		this.setName(name);
		this.setPath(path);
		this.setType(type);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
