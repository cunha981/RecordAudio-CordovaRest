package br.com.verity.recordaudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class UsuarioEntity {
	@Id
	@Column(name = "idUsuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "ativo")
	private Boolean ativo;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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