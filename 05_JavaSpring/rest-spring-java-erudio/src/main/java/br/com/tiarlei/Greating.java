package br.com.tiarlei;

public class Greating {
	
	private final long id;
	private final String content;
	
	public Greating(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
}
