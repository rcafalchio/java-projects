package br.com.tecway.gerenciadorloja.fx.controller;

import java.util.List;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.launcher.GerenciadorLojaApplication;
import br.com.tecway.gerenciadorloja.utils.AppConstants;

public final class CadastroHelper {

	/**
	 * Gera as mensagens de erro na tela
	 * 
	 * @param listaMensagens
	 */
	public static void gerarMensagensCadastro(List<String> listaMensagens) {

		final StageAlert alert = new StageAlert(AppConstants.AVISO,
				ConstantesMensagens.MENSAGEM_GENERICA_ERRO_PREENCHIMENTO, Severity.ERROR, Boolean.TRUE,
				AppConstants.BOTAO_OK);
		alert.show();
		// Recupera o BorderPanePrincipal
		final Node nodeBorderPane = ((StackPane) GerenciadorLojaApplication.getScene().getRoot()).getChildren().get(0);
		final BorderPane borderPane = ((BorderPane) nodeBorderPane);
		// Recupera o borderPane de cadastro
		final BorderPane borderPaneCadastro = (BorderPane) borderPane.getCenter();
		final AnchorPane anchorPane = (AnchorPane) borderPaneCadastro.getBottom();
		// Monta um grid na tela para apresentar os erros no cadastro
		// final GridPane gridPaneErros = montarGridErros(listaMensagens);
		final VBox vBox = montarGridErros(listaMensagens, Pos.TOP_LEFT);
		// Recupera o tamanho da tela
		final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		// posiciona o grid um pouco a esquerda do meio da tela
		vBox.setLayoutX((screenBounds.getWidth() / 2.0) - screenBounds.getWidth() * 0.10);
		vBox.setLayoutY(0.0);
		anchorPane.getChildren().clear();
		anchorPane.getChildren().add(vBox);

	}

	/**
	 * Monta o VBox de erros
	 * 
	 * @param listaMensagens
	 * @return VBox
	 */
	public static VBox montarGridErros(List<String> listaMensagens, Pos pos) {

		final VBox vBox = new VBox();
		vBox.setAlignment(pos);
		Label label = null;

		for (String mensagem : listaMensagens) {

			label = new Label("* " + mensagem);
			label.setId("login-error-label");
			label.setContentDisplay(ContentDisplay.LEFT);
			vBox.getChildren().add(label);

		}

		return vBox;

	}

}
