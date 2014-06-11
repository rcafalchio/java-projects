package br.com.tecway.gerenciadorloja.business;

import java.util.Date;
import java.util.List;

import br.com.tecway.gerenciadorloja.common.CaixaVO;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.dao.CaixaDAO;
import br.com.tecway.gerenciadorloja.dao.CaixaDAOImpl;
import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;

public class GerenciadorCaixa implements IGerenciadorCaixa {

	@Override
	public CaixaVO recuperarCaixa(Date data) throws DAOException {
		this.atualizarCaixaDiario();
		final CaixaVO caixaVO = new CaixaVO();
		final CaixaDAO caixaDAO = new CaixaDAOImpl(DAOUtils.getEntityManager());
		final CaixaEntity caixaEntity = caixaDAO.findByPk(data);
		final CaixaEntity caixaAnteriorEntity = caixaDAO.buscarUltimoCaixa();
		
		if (caixaEntity == null && caixaAnteriorEntity == null) {
			caixaVO.setValorDinheiro(0.0);
			caixaVO.setValorCartaoCredito(0.0);
			caixaVO.setValorCartaoDebito(0.0);
		} else {
			if (caixaEntity == null) {
				if (caixaAnteriorEntity.getValorDinheiro() == null) {
					caixaVO.setValorDinheiro(0.0);
				} else {
					caixaVO.setValorDinheiro(caixaAnteriorEntity.getValorDinheiro());
				}
				caixaVO.setValorCartaoCredito(0.0);
				caixaVO.setValorCartaoDebito(0.0);
			} else {
				if (caixaEntity.getValorDinheiro() == null) {
					caixaVO.setValorDinheiro(0.0);
				} else {
					caixaVO.setValorDinheiro(caixaEntity.getValorDinheiro());
				}
				if (caixaEntity.getValorCartaoCredito() == null) {
					caixaVO.setValorCartaoCredito(0.0);
				} else {
					caixaVO.setValorCartaoCredito(caixaEntity.getValorCartaoCredito());
				}
				if (caixaEntity.getValorCartaoDebito() == null) {
					caixaVO.setValorCartaoDebito(0.0);
				} else {
					caixaVO.setValorCartaoDebito(caixaEntity.getValorCartaoDebito());
				}
			}
		}
		return caixaVO;
	}

	@Override
	public List<CaixaEntity> pesquisarCaixas(Date dataInicial, Date dataFinal) throws BusinessException, DAOException {
		if (dataFinal.before(dataInicial)) {
			throw new BusinessException("A data final deve ser maior que a data inicial!");
		}

		final CaixaDAO caixaDAO = new CaixaDAOImpl(DAOUtils.getEntityManager());
		final List<CaixaEntity> listaCaixas = caixaDAO.buscarCaixas(dataInicial, dataFinal);
		if (listaCaixas == null || listaCaixas.isEmpty()) {
			throw new BusinessException("Não existem registros nesse período!");
		}
		return listaCaixas;
	}

	@Override
	public void realizarRetirada(Double valorRetirada) throws BusinessException, DAOException {
		final CaixaDAO caixaDAO = new CaixaDAOImpl(DAOUtils.getEntityManager());
		final CaixaEntity caixaEntity = this.atualizarCaixaDiario();

		if (caixaEntity.getValorDinheiro() != null && caixaEntity.getValorDinheiro().compareTo(Double.valueOf(0.0)) > 0) {
			if (valorRetirada.compareTo(caixaEntity.getValorDinheiro()) > 0) {
				throw new BusinessException("Não temos o dinheiro em caixa, o total no caixa é de "
						+ NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaEntity.getValorDinheiro()
								.toString()));
			} else {
				caixaEntity.setValorDinheiro(caixaEntity.getValorDinheiro() - valorRetirada);
				caixaDAO.merge(caixaEntity);
			}
		} else {
			throw new BusinessException("Não existe dinheiro no caixa.");
		}
	}

	private CaixaEntity atualizarCaixaDiario() throws DAOException {
		final CaixaDAO caixaDAO = new CaixaDAOImpl(DAOUtils.getEntityManager());
		CaixaEntity caixaEntity = caixaDAO.findByPk(new Date());
		// Atualiza o caixa diário
		if (caixaEntity == null) {
			caixaEntity = new CaixaEntity();
			caixaEntity.setData(new Date());
			final CaixaEntity caixaAnteriorEntity = caixaDAO.buscarUltimoCaixa();
			if(caixaAnteriorEntity!=null){
				caixaEntity.setValorDinheiro(caixaAnteriorEntity.getValorDinheiro());
			}
			caixaDAO.merge(caixaEntity);
		}
		return caixaEntity;
	}

	@Override
	public void ajustarCaixa(Double novoValorDinheiro) throws DAOException {
		final CaixaDAO caixaDAO = new CaixaDAOImpl(DAOUtils.getEntityManager());
		final CaixaEntity caixaEntity = this.atualizarCaixaDiario();
		caixaEntity.setValorDinheiro(novoValorDinheiro);
		caixaDAO.merge(caixaEntity);
	}
}
