package com.leilaopecuario.negocio;

import javax.ejb.Local;

import com.leilaopecuario.exception.ValidacaoException;
import com.leilaopecuario.vo.LeilaoVO;

@Local
public interface ValidadorLeilaoLocal {

	/**
	 * Efetua a validação da data de finalização do leilão.
	 * @param leilaoVO 
	 * 
	 * @return
	 * @throws ValidacaoException
	 */
	public String validaDataCadastroLeilao(LeilaoVO leilaoVO) throws ValidacaoException;
	
}
