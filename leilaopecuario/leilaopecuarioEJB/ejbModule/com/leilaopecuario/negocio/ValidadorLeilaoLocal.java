package com.leilaopecuario.negocio;

import javax.ejb.Local;

import com.leilaopecuario.exception.ValidacaoException;
import com.leilaopecuario.vo.LeilaoVO;

@Local
public interface ValidadorLeilaoLocal {

	/**
	 * Efetua a valida��o da data de finaliza��o do leil�o.
	 * @param leilaoVO 
	 * 
	 * @return
	 * @throws ValidacaoException
	 */
	public String validaDataCadastroLeilao(LeilaoVO leilaoVO) throws ValidacaoException;
	
}
