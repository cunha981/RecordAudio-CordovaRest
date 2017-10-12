package br.com.verity.recordaudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Loja")
public class LojaEntity {

	@Id
	@Column(name = "idLoja")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLoja;

	@Column(name = "nmLoja")
	private String nmLoja;

	public Integer getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Integer idLoja) {
		this.idLoja = idLoja;
	}

	public String getNmLoja() {
		return nmLoja;
	}

	public void setNmLoja(String nmLoja) {
		this.nmLoja = nmLoja;
	}

}