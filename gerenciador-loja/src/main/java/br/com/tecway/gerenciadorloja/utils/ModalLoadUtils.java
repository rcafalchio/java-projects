package br.com.tecway.gerenciadorloja.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class ModalLoadUtils {

	private static ModalLoadUtils instance;
	private Stage stage = null;
	private Scene scene = null;
	private ProgressIndicator progressIndicator = null;
	private StackPane stackPane = null;

	/**
	 * Construtor privado
	 */
	private ModalLoadUtils() {
	}

	/**
	 * Método responsável por retornar o Singleton do ModalLoadUtils.
	 * 
	 * @return ModalLoadUtils
	 */
	public synchronized static ModalLoadUtils getInstance() {
		if (instance == null) {
			instance = new ModalLoadUtils();
			final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			instance.progressIndicator = new ProgressIndicator();
			instance.progressIndicator.setMaxWidth(100);
			instance.progressIndicator.setMaxHeight(100);
			instance.stackPane = new StackPane();
			instance.stackPane.setStyle("-fx-background-color: gray;");
			instance.stackPane.setOpacity(0.5);
			instance.stackPane.setPrefWidth(bounds.getWidth());
			instance.stackPane.setPrefHeight(bounds.getHeight());
			instance.stackPane.getChildren().add(instance.progressIndicator);
			instance.scene = new Scene(instance.stackPane);
			instance.scene.getStylesheets().add("/styles/style.css");
			instance.scene.setFill(Color.TRANSPARENT);
			instance.stage = new Stage();
			instance.stage.initModality(Modality.APPLICATION_MODAL);
			instance.stage.initStyle(StageStyle.TRANSPARENT);
			instance.stage.getIcons().add(new Image("/images/loading.png"));
			instance.stage.setScene(instance.scene);
			instance.stage.setTitle("Carregando...");
		}
		return instance;
	}

	/**
	 * Método responsável por exibir o modal de carregamento.
	 * 
	 */
	public synchronized void showModalLoad() {
		instance.stage.show();
	}

	/**
	 * Método responsável por esconder o modal de carregamento.
	 * 
	 */
	public synchronized void hideModalLoad() {
		instance.stage.close();
	}

}
