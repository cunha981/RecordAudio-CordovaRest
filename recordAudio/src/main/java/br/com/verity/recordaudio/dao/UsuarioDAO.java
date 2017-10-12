package br.com.verity.recordaudio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.verity.recordaudio.entity.UsuarioEntity;

public interface UsuarioDAO extends JpaRepository<UsuarioEntity, Integer> {
	
	UsuarioEntity findByUsuario(String usuario);

}
