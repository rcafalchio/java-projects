package com.leilaopecuario.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.leilaopecuario.constantes.ConstantesMensagens;
import com.leilaopecuario.entidades.Lance;
import com.leilaopecuario.entidades.Leilao;
import com.leilaopecuario.exception.LeilaoException;
import com.leilaopecuario.persistencia.ControladorPersistenciaLanceLocal;
import com.leilaopecuario.persistencia.ControladorPersistenciaLeilaoLocal;
import com.leilaopecuario.util.LeilaoHelper;
import com.leilaopecuario.vo.DadosLanceUsuarioVO;
import com.leilaopecuario.vo.LanceVO;
import com.leilaopecuario.vo.UsuarioVO;

@Stateless
public class GerenciadorLance implements GerenciadorLanceLocal {

	private final static Logger LOGGER = Logger.getLogger(GerenciadorLance.class);

	@EJB
	private ControladorPersistenciaLanceLocal controladorPersistenciaLance;

	@EJB
	private ControladorPersistenciaLeilaoLocal controladorPersistenciaLeilao;

	@Override
	public String efetuarLance(LanceVO lanceVO) throws LeilaoException {

		String fraseErro = null;

		try {
			Double lanceAtual = controladorPersistenciaLance.recuperaLanceAtual(lanceVO.getLeilaoVO().getCodigo());

			// Se não encontrar o lance atual seta como lance minimo o lance
			// inicial
			if (lanceAtual == null) {
				lanceAtual = lanceVO.getLeilaoVO().getLanceInicial();
			}
			// Caso não exista lance
			if (lanceAtual.compareTo(lanceVO.getLance()) > -1) {
				fraseErro = ConstantesMensagens.ERRO_LANCE;
			} else {
				controladorPersistenciaLance.atualizarLance(lanceVO);
			}
		} catch (Exception e) {
			fraseErro = ConstantesMensagens.FALHA_GENERICA;
			LOGGER.error(e);
			throw new LeilaoException(e);
		}
		return fraseErro;
	}

	@Override
	public List<DadosLanceUsuarioVO> recuperaDadosLancesPorUsuario(UsuarioVO usuarioVO) throws LeilaoException {

		final List<DadosLanceUsuarioVO> listaDadosLanceUsuarioVO = new ArrayList<DadosLanceUsuarioVO>();

		try {
			List<Lance> listaLance = controladorPersistenciaLance.recuperaLancesPorUsuario(usuarioVO.getCodigo());
			for (Lance lance : listaLance) {
				final DadosLanceUsuarioVO dadosLanceUsuarioVO = new DadosLanceUsuarioVO();
				dadosLanceUsuarioVO.setLanceEfetuado(lance.getLance());
				final Leilao leilao = controladorPersistenciaLeilao.recuperaLeilaoPorCodigo(lance.getChaveLance()
						.getCodigoLeilao());
				dadosLanceUsuarioVO.setLeilaoVO(LeilaoHelper.copiaLeilaoParaLeilaoVO(leilao));
				dadosLanceUsuarioVO.setUsuarioVO(usuarioVO);
				if (lance.getLance().equals(leilao.getInformacoesLanceLeilao().getLanceAtual())) {
					dadosLanceUsuarioVO.setLanceGanhador(true);
				} else {
					dadosLanceUsuarioVO.setLanceGanhador(false);
				}
				listaDadosLanceUsuarioVO.add(dadosLanceUsuarioVO);
			}

		} catch (Exception e) {
			LOGGER.error(e);
			throw new LeilaoException(e);
		}
		return listaDadosLanceUsuarioVO;
	}
}
