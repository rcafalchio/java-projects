package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import br.com.tecway.gerenciadorloja.business.GerenciadorProduto;
import br.com.tecway.gerenciadorloja.business.IGerenciadorProduto;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.utils.ProdutoTelaHelper;

public class ConsultaProdutosController extends AbstractSubController<ConsultaVendasController> {

	@FXML
	public TableView<ProdutoVO> produtoTableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}

	@Override
	public void customInitialize() throws ApplicationException {
		this.buscarProdutos();
	}

	public void buscarProdutos() {
		try {
			this.produtoTableView.getItems().clear();
			final IGerenciadorProduto gerenciadorProduto = new GerenciadorProduto();
			final List<ProdutoVO> produtos = gerenciadorProduto
					.buscarProdutosVendidos(getParentController().vendasTableView.getSelectionModel().getSelectedItem()
							.getCodigo());
			ProdutoTelaHelper.montarTableViewProdutos(produtos, produtoTableView);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

}
