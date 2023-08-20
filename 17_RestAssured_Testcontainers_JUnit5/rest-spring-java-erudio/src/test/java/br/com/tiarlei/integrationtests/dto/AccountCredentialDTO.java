package br.com.tiarlei.integrationtests.dto;

import java.io.Serializable;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountCredentialDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public AccountCredentialDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public AccountCredentialDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCredentialDTO other = (AccountCredentialDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
}
