package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class TrocaController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(TrocaController.class);

	@FXML
	protected TextField codigoBarrasText;

	@FXML
	protected Label totalLabel;

	@FXML
	protected TextField quantidadeText;

	@FXML
	protected TableView<EstoqueTableVO> trocaTableView;

	protected StagePopup popupPesquisarProduto = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void adicionarProduto() {

		try {
			this.popupPesquisarProduto = new StagePopup("Pesquisar Produto", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_PESQUISAR_PRODUTO, this), Modality.APPLICATION_MODAL, 800.0, 310.0,
					Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			this.popupPesquisarProduto.show();
		} catch (Exception e) {

			ControllerException.registrarErro(LOGGER, e);

		}

	}

	public void retirar() {

	}

}
