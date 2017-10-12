package br.com.verity.recordaudio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.verity.recordaudio.entity.LojaEntity;

public interface LojaDAO extends JpaRepository<LojaEntity, Integer>{

}
