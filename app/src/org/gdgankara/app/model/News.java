package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String photo; //Haberin resmi.Duruma g�re bu drawable id(int) olabilir bunun t�r�.
	private String description; //Haberin �zeti
	private List<String> linkList; //Haber i�erisinde bir veya birden fazla link ba�lant� adresi kullanacaksak
	private boolean isSession; // E�er haber bir oturumsa true olacak
	private Long session_id; // E�er haber bir oturumsa o oturumun id'si buna e�lenecek
	
	public News() {
		super();
	}

	

	public News(long id, String photo, String description,
			List<String> linkList, boolean isSession) {
		super();
		this.id = id;
		this.photo = photo;
		this.description = description;
		this.linkList = linkList;
		this.isSession = isSession;
	}
	

	public News(long id, String photo, String description,
			List<String> linkList, boolean isSession, Long session_id) {
		super();
		this.id = id;
		this.photo = photo;
		this.description = description;
		this.linkList = linkList;
		this.isSession = isSession;
		this.session_id = session_id;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}



	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}


	public Long getSession_id() {
		return session_id;
	}


	public void setSession_id(Long session_id) {
		this.session_id = session_id;
	}


}
