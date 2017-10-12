package br.com.verity.recordaudio.bean;

import org.springframework.stereotype.Component;

@Component
public class UsuarioBean {
	private Integer id;
	private String usuario;
	private String senha;
	private Boolean ativo;
	
	public UsuarioBean() {
	}

	public UsuarioBean(UsuarioBean usuario) {
		super();
		this.id = usuario.getId();
		this.usuario = usuario.getUsuario();
		this.senha = usuario.getSenha();
		this.ativo = usuario.getAtivo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}