package com.leilaopecuario.negocio;

import java.util.Date;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.leilaopecuario.constantes.ConstantesMensagens;
import com.leilaopecuario.exception.ValidacaoException;
import com.leilaopecuario.vo.LeilaoVO;

@Stateless
public class ValidadorLeilao implements ValidadorLeilaoLocal {

	private final static Logger LOGGER = Logger.getLogger(ValidadorLeilao.class);

	@Override
	public String validaDataCadastroLeilao(LeilaoVO leilaoVO) throws ValidacaoException {

		String frase = null;

		try {
			if (new Date().after(leilaoVO.getDataEHoraFinal())) {
				frase = ConstantesMensagens.DATA_FINAL_INVALIDA;
			}
		} catch (Exception e) {
			LOGGER.error("Erro no método validaDataCadastroLeilao",e);
			throw new ValidacaoException(e);
		}
		return frase;
	}

}
