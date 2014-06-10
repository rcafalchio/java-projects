package com.leilaopecuario.negocio;

import javax.ejb.Local;

import com.leilaopecuario.exception.ValidacaoException;
import com.leilaopecuario.vo.UsuarioVO;

@Local
public interface ValidadorUsuarioLocal {

	public String validaUsuario(UsuarioVO usuarioVO) throws ValidacaoException;

}
