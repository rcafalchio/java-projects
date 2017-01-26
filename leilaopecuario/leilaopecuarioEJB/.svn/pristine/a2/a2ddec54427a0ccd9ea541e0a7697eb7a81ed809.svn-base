package com.leilaopecuario.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.leilaopecuario.entidades.Vacina;
import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.persistencia.ControladorPersistenciaLeilaoLocal;
import com.leilaopecuario.vo.VacinaVO;

@Stateless
public class GerenciadorVacina implements GerenciadorVacinaLocal {

	private final static Logger LOGGER = Logger.getLogger(GerenciadorVacina.class);

	@EJB
	private ControladorPersistenciaLeilaoLocal controladorPersistenciaLeilao;

	@Override
	public List<VacinaVO> obtemTodasVacinas() throws LeilaoException {

		List<VacinaVO> lista = null;

		try {
			List<Vacina> listaVacinas = controladorPersistenciaLeilao.obtemTodasVacinas();
			for (Vacina vacina : listaVacinas) {
				VacinaVO vacinaVO = new VacinaVO();
				PropertyUtils.copyProperties(vacinaVO, vacina);
				if (lista == null) {
					lista = new ArrayList<VacinaVO>();
				}
				lista.add(vacinaVO);
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new LeilaoException(e);
		}
		return lista;
	}

	@Override
	public List<String> obtemTodasNomesVacinas() throws LeilaoException {

		final List<String> nomeVacinas = new ArrayList<String>();

		try {
			final List<VacinaVO> listaVacinas = this.obtemTodasVacinas();
			for (VacinaVO vacinaVO : listaVacinas) {
				nomeVacinas.add(vacinaVO.getNomeVacina());
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new LeilaoException(e);
		}
		return nomeVacinas;
	}

	@Override
	public VacinaVO obtemVacinaPorNome(String nome) throws LeilaoException {
		VacinaVO vacinaVO = null;
		try {
			final Vacina vacina = controladorPersistenciaLeilao.obtemVacinaPorNome(nome);
			vacinaVO = new VacinaVO();
			PropertyUtils.copyProperties(vacinaVO, vacina);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new LeilaoException(e);
		}
		return vacinaVO;
	}

}
