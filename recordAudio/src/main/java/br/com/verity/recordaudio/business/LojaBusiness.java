package br.com.verity.recordaudio.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.verity.recordaudio.bean.LojaBean;
import br.com.verity.recordaudio.converter.LojaConverter;
import br.com.verity.recordaudio.dao.LojaDAO;

@Service
public class LojaBusiness {

	@Autowired
	private LojaDAO lojaDAO;
	
	@Autowired
	private LojaConverter lojaConverter;
	
	@Transactional
	public List<LojaBean> getAll(){
		return lojaConverter.convertEntityToBean(lojaDAO.findAll());
	}
}
