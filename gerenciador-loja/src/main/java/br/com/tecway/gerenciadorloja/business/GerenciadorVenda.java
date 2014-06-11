package br.com.tecway.gerenciadorloja.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;

import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.common.FechamentoVendaVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVendaVO;
import br.com.tecway.gerenciadorloja.common.UsuarioVO;
import br.com.tecway.gerenciadorloja.common.VendaVO;
import br.com.tecway.gerenciadorloja.constants.TipoPagamentoEnum;
import br.com.tecway.gerenciadorloja.dao.CaixaDAO;
import br.com.tecway.gerenciadorloja.dao.CaixaDAOImpl;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAO;
import br.com.tecway.gerenciadorloja.dao.ProdutoDAOImpl;
import br.com.tecway.gerenciadorloja.dao.VendaDAO;
import br.com.tecway.gerenciadorloja.dao.VendaDAOImpl;
import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.entity.VendaEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.utils.ConverterUtils;
import br.com.tecway.gerenciadorloja.utils.DAOUtils;
import br.com.tecway.gerenciadorloja.utils.SegurancaUtils;

public class GerenciadorVenda implements IGerenciadorVenda {

	protected static final Logger LOGGER = LogManager.getLogger(GerenciadorVenda.class);

	@Override
	public void fecharVenda(final FechamentoVendaVO fechamentoVendaVO) throws BusinessException, DAOException {
		final EntityManager entityManager = DAOUtils.getEntityManager();
		try {
			// Atualiza os dados da venda
			fechamentoVendaVO.setValorLiquido(recuperarValorPago(fechamentoVendaVO));
			// Valida se o valor pago é maior ou igual ao valor que se deve pagar
			if (fechamentoVendaVO.getValorPago().compareTo(fechamentoVendaVO.getValorLiquido()) < 0) {
				throw new BusinessException("O valor do pagamento é menor do que o valor da venda!");
			}
			final List<ProdutoEntity> listaProdutosVenda = this.buscarProdutos(fechamentoVendaVO.getListaProdutos());
			this.registrarVenda(listaProdutosVenda, fechamentoVendaVO, entityManager);

			// Da baixa no estoque
			for (ProdutoVendaVO produtoVendaVO : fechamentoVendaVO.getListaProdutos()) {
				this.efetuarBaixaProduto(produtoVendaVO.getProdutoVO().getCodigo(), produtoVendaVO.getQuantidade(),
						entityManager);
			}
			// Atualiza o caixa
			this.atualizarCaixa(fechamentoVendaVO.getValorLiquido(), entityManager,
					fechamentoVendaVO.getTipoPagamentoEnum());
			// entityManager.getTransaction().commit();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Erro ao fechar a venda", e);
			entityManager.getTransaction().rollback();
		}
	}

	private List<ProdutoEntity> buscarProdutos(List<ProdutoVendaVO> listaProdutos) {
		final List<ProdutoEntity> listaProdutoEntity = new ArrayList<ProdutoEntity>();
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		ProdutoEntity produtoEntity = null;

		for (ProdutoVendaVO produtoVendaVO : listaProdutos) {
			for (int i = 0; i < produtoVendaVO.getQuantidade(); i++) {
				produtoEntity = produtoDAO.findByPk(produtoVendaVO.getProdutoVO().getCodigo());
				listaProdutoEntity.add(produtoEntity);
			}
		}
		return listaProdutoEntity;
	}

	private Double recuperarValorPago(FechamentoVendaVO ferchamentoVendaVO) {
		Double valorPago = null;
		if (ferchamentoVendaVO.getPercentualDesconto() != null) {
			final Double valorDesconto = ferchamentoVendaVO.getPercentualDesconto() * 0.01
					* ferchamentoVendaVO.getValorBruto();
			valorPago = ferchamentoVendaVO.getValorBruto() - valorDesconto;
		} else {
			valorPago = ferchamentoVendaVO.getValorBruto();
		}
		return valorPago;
	}

	/**
	 * 
	 * @param ferchamentoVendaVO
	 * @param entityManager
	 * @throws DAOException
	 */
	private void atualizarCaixa(final Double valorPago, final EntityManager entityManager,
			TipoPagamentoEnum pagamentoEnum) throws DAOException {
		final CaixaDAO caixaDAO = new CaixaDAOImpl(entityManager);
		CaixaEntity caixaEntity = caixaDAO.findByPk(new Date());
		// Soma o caixa do dia anterior com o caixa atual
		final CaixaEntity caixaAnteriorEntity = caixaDAO.buscarUltimoCaixa();
		// Se o caixa estiver nulo é o primeiro lançamento do dia
		if (caixaEntity == null) {
			caixaEntity = new CaixaEntity();
			caixaEntity.setData(new Date());
			if (pagamentoEnum.equals(TipoPagamentoEnum.DINHEIRO)) {
				caixaEntity.setValorDinheiro(valorPago);
				if (caixaAnteriorEntity != null && caixaAnteriorEntity.getValorDinheiro() != null) {
					caixaEntity.setValorDinheiro(caixaEntity.getValorDinheiro()
							+ caixaAnteriorEntity.getValorDinheiro());
				}
			} else {
				caixaEntity.setValorDinheiro(caixaAnteriorEntity.getValorDinheiro());
			}
			if (pagamentoEnum.equals(TipoPagamentoEnum.CARTAO_DEBITO)) {
				caixaEntity.setValorCartaoDebito(valorPago);
			}
			if (pagamentoEnum.equals(TipoPagamentoEnum.CARTAO_CREDITO)) {
				caixaEntity.setValorCartaoCredito(valorPago);
			}
		} else {
			if (pagamentoEnum.equals(TipoPagamentoEnum.DINHEIRO)) {
				if (caixaEntity.getValorDinheiro() == null) {
					caixaEntity.setValorDinheiro(valorPago);
				} else {
					caixaEntity.setValorDinheiro(caixaEntity.getValorDinheiro() + valorPago);
				}
			} else if (pagamentoEnum.equals(TipoPagamentoEnum.CARTAO_DEBITO)) {
				if (caixaEntity.getValorCartaoDebito() == null) {
					caixaEntity.setValorCartaoDebito(valorPago);
				} else {
					caixaEntity.setValorCartaoDebito(caixaEntity.getValorCartaoDebito() + valorPago);
				}

			} else if (pagamentoEnum.equals(TipoPagamentoEnum.CARTAO_CREDITO)) {
				if (caixaEntity.getValorCartaoCredito() == null) {
					caixaEntity.setValorCartaoCredito(valorPago);
				} else {
					caixaEntity.setValorCartaoCredito(caixaEntity.getValorCartaoCredito() + valorPago);
				}
			}
		}
		caixaDAO.merge(caixaEntity);
	}

	/**
	 * 
	 * @param listaProdutosVenda
	 * @param entityManager
	 * @param ferchamentoVendaVO
	 * @throws DAOException
	 */
	private void registrarVenda(final List<ProdutoEntity> listaProdutosVenda,
			final FechamentoVendaVO fechamentoVendaVO, final EntityManager entityManager) throws DAOException {
		final VendaDAO vendaDAO = new VendaDAOImpl(entityManager);
		final VendaEntity vendaEntity = new VendaEntity();
		vendaEntity.setDataVenda(new Date());
		vendaEntity.setValorBruto(fechamentoVendaVO.getValorBruto());
		if (fechamentoVendaVO.getPercentualDesconto() == null) {
			fechamentoVendaVO.setPercentualDesconto(0D);
		}
		vendaEntity.setPercentualDesconto(fechamentoVendaVO.getPercentualDesconto());
		vendaEntity.setVendedor(SegurancaUtils.getUsuarioEntity());
		vendaEntity.setValorLiquido(fechamentoVendaVO.getValorLiquido());
		vendaEntity.setTipoPagamento(fechamentoVendaVO.getTipoPagamentoEnum().getCodigo());
		vendaEntity.setProdutos(listaProdutosVenda);
		vendaDAO.save(vendaEntity);
	}

	/**
	 * Efetua a baixa do produto no estoque
	 * 
	 * @param codigoProduto
	 * @param quantidade
	 * @param entityManager
	 * @throws DAOException
	 */
	private void efetuarBaixaProduto(final Integer codigoProduto, final Integer quantidade,
			final EntityManager entityManager) throws DAOException {
		final ProdutoDAO produtoDAO = new ProdutoDAOImpl(DAOUtils.getEntityManager());
		final ProdutoEntity produtoEntity = produtoDAO.findByPk(codigoProduto);
		for (int i = 0; i < quantidade; i++) {
			produtoEntity.getEstoques().remove(0);
		}
		produtoDAO.merge(produtoEntity);
	}

	@Override
	public List<VendaVO> buscarVendas(final ObservableList<UsuarioVO> usuarios, final Date dataInicial,
			final Date dataFinal) throws ConverterException, DAOException, BusinessException {
		if (usuarios == null || usuarios.isEmpty()) {
			throw new BusinessException("É necessário selecionar os vendedores!");
		}
		if (dataInicial == null || dataFinal == null) {
			throw new BusinessException("É necessário informar o período para consulta!");
		}
		final VendaDAO vendaDAO = new VendaDAOImpl(DAOUtils.getEntityManager());
		final List<VendaEntity> vendasEntity = vendaDAO.buscarVendasPorUsuarios(
				ConverterUtils.deUsuarioVOParaUsuarioEntity(usuarios), dataInicial, dataFinal);
		return ConverterUtils.deVendaEntityParaVendaVO(vendasEntity);
	}
}
