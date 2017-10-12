/*package br.com.verity.recordaudio.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetails extends UsuarioBean implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(final UsuarioBean usuario) {
		super(usuario);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return super.getSenha();
	}

	@Override
	public String getUsername() {
		return super.getUsuario();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.getAtivo();
	}

}
*/