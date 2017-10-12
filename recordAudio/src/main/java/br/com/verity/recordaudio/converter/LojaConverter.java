package br.com.verity.recordaudio.converter;

import org.springframework.stereotype.Component;

import br.com.verity.recordaudio.bean.LojaBean;
import br.com.verity.recordaudio.entity.LojaEntity;

@Component
public class LojaConverter implements Converter<LojaEntity, LojaBean>{

	@Override
	public LojaEntity convertBeanToEntity(LojaBean bean) {
		if (bean == null) {
			return null;
		}
		LojaEntity entity = new LojaEntity();
		
		entity.setIdLoja(bean.getId());
		entity.setNmLoja(bean.getNome());
		
		return entity;
	}

	@Override
	public LojaBean convertEntityToBean(LojaEntity entity) {
		if (entity == null) {
			return null;
		}
		LojaBean bean = new LojaBean();
		
		bean.setId(entity.getIdLoja());
		bean.setNome(entity.getNmLoja());
		
		return bean;
	}

}
