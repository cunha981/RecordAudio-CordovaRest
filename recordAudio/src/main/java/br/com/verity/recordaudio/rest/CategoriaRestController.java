package br.com.verity.recordaudio.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.recordaudio.bean.LojaBean;
import br.com.verity.recordaudio.business.LojaBusiness;

@RestController
public class CategoriaRestController {

	@Autowired
	private LojaBusiness lojaBusiness;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/preencher-loja")
	public ResponseEntity<List<LojaBean>> preencherLoja(Model model) {
		return new ResponseEntity<List<LojaBean>>(lojaBusiness.getAll(), HttpStatus.OK);
	}
}
