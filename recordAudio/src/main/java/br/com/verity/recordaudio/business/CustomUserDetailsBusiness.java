/*package br.com.verity.recordaudio.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.verity.recordaudio.bean.CustomUserDetails;
import br.com.verity.recordaudio.bean.UsuarioBean;
import br.com.verity.recordaudio.converter.UsuarioConverter;
import br.com.verity.recordaudio.dao.UsuarioDAO;
import br.com.verity.recordaudio.entity.UsuarioEntity;

@Service
public class CustomUserDetailsBusiness implements UserDetailsService {
	
	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Optional<UsuarioEntity> optionalUsuarios = usuarioDAO.findByUsuario(usuario);

		optionalUsuarios.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		UsuarioBean usuarioBean = new UsuarioBean();

		usuarioBean = usuarioConverter.convertEntityToBean(optionalUsuarios.get());

		return null;
				//new CustomUserDetails(usuarioBean);
	}
}
*/