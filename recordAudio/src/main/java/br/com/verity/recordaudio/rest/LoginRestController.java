package br.com.verity.recordaudio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.recordaudio.business.LoginBusiness;

@RestController
public class LoginRestController {
	
	@Autowired
	private LoginBusiness loginBusiness;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/login/{user}/{senha}/")
	public ResponseEntity<Boolean> preencherLoja(@PathVariable String user, @PathVariable String senha) {
		return new ResponseEntity<Boolean>(loginBusiness.autenticar(user, senha), HttpStatus.OK);
	}
}
