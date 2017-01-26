package com.leilaopecuario.negocio;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.leilaopecuario.constantes.ConstantesMensagens;
import com.leilaopecuario.exception.PersistenciaException;
import com.leilaopecuario.exception.ValidacaoException;
import com.leilaopecuario.persistencia.ControladorPersistenciaUsuarioLocal;
import com.leilaopecuario.vo.UsuarioVO;

@Stateless
public class ValidadorUsuario implements ValidadorUsuarioLocal {
	
	private final static Logger LOGGER = Logger.getLogger(ValidadorUsuario.class);

	@EJB
	private ControladorPersistenciaUsuarioLocal controladorPersistenciaUsuario;

	@Override
	public String validaUsuario(UsuarioVO usuarioVO) throws ValidacaoException {
		String fraseErro = null;
		try {
			if (usuarioVO != null && usuarioVO.getLogin() != null) {
				// Se não existe deixa inserir
				if (controladorPersistenciaUsuario
						.verificaExistenciaLogin(usuarioVO.getLogin())) {
					fraseErro = ConstantesMensagens.ERRO_LOGIN;
				}
			}
		} catch (PersistenciaException e) {
			LOGGER.error("Ocorreu um erro no método validaUsuario");
			fraseErro = ConstantesMensagens.FALHA_GENERICA;
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro no método validaUsuario");
			fraseErro = ConstantesMensagens.FALHA_GENERICA;
		}

		return fraseErro;
	}

}
