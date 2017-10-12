package br.com.verity.recordaudio.converter;

import org.springframework.stereotype.Component;

import br.com.verity.recordaudio.bean.UsuarioBean;
import br.com.verity.recordaudio.entity.UsuarioEntity;


@Component
public class UsuarioConverter implements Converter<UsuarioEntity, UsuarioBean>{

	@Override
	public UsuarioEntity convertBeanToEntity(UsuarioBean bean) {
		if (bean == null) {
			return null;
		}
		UsuarioEntity entity = new UsuarioEntity();
		
		entity.setIdUsuario(bean.getId());
		entity.setUsuario(bean.getUsuario());
		entity.setSenha(bean.getSenha());
		entity.setAtivo(bean.getAtivo());
		
		return entity;
	}

	@Override
	public UsuarioBean convertEntityToBean(UsuarioEntity entity) {
		if (entity == null) {
			return null;
		}
		UsuarioBean bean = new UsuarioBean();
		
		bean.setId(entity.getIdUsuario());
		bean.setUsuario(entity.getUsuario());
		bean.setAtivo(entity.getAtivo());
		bean.setSenha(entity.getSenha());
		
		return bean;
	}

}
