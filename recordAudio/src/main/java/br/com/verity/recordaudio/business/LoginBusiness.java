package br.com.verity.recordaudio.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.recordaudio.bean.UsuarioBean;
import br.com.verity.recordaudio.converter.UsuarioConverter;
import br.com.verity.recordaudio.dao.UsuarioDAO;
import br.com.verity.recordaudio.util.EncriptaDecriptaApacheCodec;

@Service
public class LoginBusiness {
	
	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	public Boolean autenticar(String user, String senha) {
		UsuarioBean autenticado = usuarioConverter.convertEntityToBean(usuarioDAO.findByUsuario(user));
		String senhaCrip = null;
		
		if(autenticado != null){
			senhaCrip = EncriptaDecriptaApacheCodec.codificaBase64Encoder(senha);
			return (senhaCrip.equals(autenticado.getSenha())?true:false);
		}
		return false;
	}
}