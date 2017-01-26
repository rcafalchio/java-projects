package com.leilaopecuario.negocio;

import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.security.auth.spi.Util;

import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.persistencia.ControladorPersistenciaUsuarioLocal;
import com.leilaopecuario.util.CriptoUtil;
import com.leilaopecuario.vo.UsuarioVO;

@Stateless
public class GerenciadorUsuario implements GerenciadorUsuarioLocal {

	@EJB
	private ControladorPersistenciaUsuarioLocal controladorPersistenciaUsuario;

	@EJB
	private ValidadorUsuarioLocal validadorUsuario;

	private final static Logger LOGGER = Logger
			.getLogger(GerenciadorUsuario.class);

	@Override
	public String gravaUsuario(final UsuarioVO usuarioVO)
			throws LeilaoException {

		String fraseErro = null;
		try {
			// Validação de usuário
			fraseErro = validadorUsuario.validaUsuario(usuarioVO);
			if (fraseErro == null) {
				// Gera senha do usuário
				efetuarCriptografiaDeSenha(usuarioVO);
				// Efetua a persistencia do usuário
				controladorPersistenciaUsuario.inserirUsuario(usuarioVO);
			}
		} catch (Exception e) {
			LOGGER.error("Ocorreu um problema no método gravaUsuario", e);
			fraseErro = "Problema ao gravar";
		}
		return fraseErro;

	}

	private void efetuarCriptografiaDeSenha(UsuarioVO usuarioVO)
			throws NoSuchAlgorithmException {

		byte[] b = CriptoUtil.digest(usuarioVO.getSenha().getBytes(), "md5");
//		String senhaCriptografada = CriptoUtil.byteArrayToHexString(b);
		final String senhaCriptografada = Util.encodeBase64(b);
		usuarioVO.setSenha(senhaCriptografada);
	}

}
