package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.constants.RelatorioEnum;
import br.com.tecway.gerenciadorloja.constants.TipoEstoqueEnum;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.EstoqueTelaHelper;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class ConsultaEstoqueController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(ConsultaEstoqueController.class);

	@FXML
	private ComboBox<TipoEstoqueEnum> estoqueComboBox;

	@FXML
	private VBox estoqueVBox;

	protected StagePopup popup = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		estoqueComboBox.getItems().clear();
		for (TipoEstoqueEnum estoqueEnum : TipoEstoqueEnum.values()) {
			estoqueComboBox.getItems().add(estoqueEnum);
		}
		estoqueComboBox.getSelectionModel().select(0);
	}

	@Override
	public void customInitialize() throws ApplicationException {
		this.pesquisar();
		super.setObjectsReport(RelatorioEnum.ESTOQUE);
	}

	public void pesquisar() {
		try {
			final TableView<EstoqueTableVO> estoqueTableView = new TableView<EstoqueTableVO>();
			EstoqueTelaHelper.criarTableView(estoqueComboBox.getSelectionModel().getSelectedItem(), estoqueTableView);
			estoqueTableView.setPrefHeight(500);
			estoqueTableView.setPrefWidth(900);
			super.dadosTableView = estoqueTableView;
			// Botão exportar
			final Button exportarButton = new Button("Exportar");
			final Insets insets = new Insets(10.0);
			VBox.setMargin(exportarButton, insets);
			exportarButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					exportar();
				}
			});
			estoqueVBox.getChildren().clear();
			estoqueVBox.getChildren().add(estoqueTableView);
			estoqueVBox.getChildren().add(exportarButton);

		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	private void exportar() {
		try {
			this.popup = new StagePopup("Exportar Dados", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_GERAR_ARQUIVO, this), Modality.APPLICATION_MODAL, 800.0, 310.0,
					Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			super.popup = this.popup;
			this.popup.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

}