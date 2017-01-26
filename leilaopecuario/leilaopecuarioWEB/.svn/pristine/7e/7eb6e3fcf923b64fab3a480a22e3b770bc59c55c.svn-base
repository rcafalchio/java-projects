package br.com.leilaopecuario.filters;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jboss.security.SecurityAssociation;

import com.leilaopecuario.entidades.Usuario;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.persistencia.ControladorPersistenciaUsuarioRemote;
import com.leilaopecuario.vo.UsuarioVO;

public class LoginFilter implements Filter {

	private final static Logger LOGGER = Logger.getLogger(LoginFilter.class);

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {

			final String userName = SecurityAssociation.getPrincipal().getName();

			if (userName != null) {
				InitialContext initialContext = new InitialContext();
				ControladorPersistenciaUsuarioRemote persistenciaUsuarioRemote = (ControladorPersistenciaUsuarioRemote) initialContext
						.lookup("leilaopecuarioEAR/ControladorPersistenciaUsuario/remote");
				final Usuario usuario = persistenciaUsuarioRemote.recuperaUsuarioPorLogin(userName);
				final UsuarioVO usuarioVO = new UsuarioVO();
				copiaDadosUsuario(usuario, usuarioVO);
				final HttpServletRequest request = (HttpServletRequest) servletRequest;
				final HttpSession session = request.getSession();
				session.setAttribute("usuario", usuarioVO);
			}
		} catch (NamingException e) {
			LOGGER.error("Problema ao efetuar o lookup ", e);
		} catch (PersistenciaException e) {
			LOGGER.error("Problema no método doFilter", e);
		}

		filterChain.doFilter(servletRequest, servletResponse);

	}

	/**
	 * 
	 * @param usuario
	 * @param usuarioVO
	 */
	private void copiaDadosUsuario(Usuario usuario, UsuarioVO usuarioVO) {
		if (usuario != null) {
			usuarioVO.setCodigo(usuario.getCodigo());
			usuarioVO.setLogin(usuario.getLogin());
			usuarioVO.setNome(usuario.getNome());
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
