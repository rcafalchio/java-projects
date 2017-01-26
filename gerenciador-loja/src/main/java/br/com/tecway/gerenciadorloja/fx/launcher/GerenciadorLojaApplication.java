package br.com.tecway.gerenciadorloja.fx.launcher;

import java.io.InputStream;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.controller.AbstractController;
import br.com.tecway.gerenciadorloja.utils.AppConstants;

public class GerenciadorLojaApplication extends Application {

	private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("properties/gerenciadorloja");

	private static final Logger LOGGER = LogManager.getLogger(GerenciadorLojaApplication.class);

	// Main FX Components
	private static Scene scene;
	private static Stage stage;

	// Login pane
	private static AnchorPane loginPane = null;

	public static Scene getScene() {
		return scene;
	}

	public static Stage getStage() {
		return stage;
	}

	// Principal pane
	private static StackPane principalPane = null;

	// private static GerenciadorLojaMain instance;


	@Override
	public void start(Stage primaryStage) throws Exception {

		LOGGER.info("Iniciando a aplicação gerenciador-loja - " + new Date(System.currentTimeMillis()));

		// Dados do primary stage
		// instance = this;
		GerenciadorLojaApplication.stage = primaryStage;
		GerenciadorLojaApplication.stage.setTitle(RESOURCE.getString("title.sistema"));
		GerenciadorLojaApplication.stage.getIcons().add(new Image("/images/shortcut.png"));

		// Carrega todos os FXML
		this.carregaLoginFXML();
		this.carregaPrincipalFXML();

		// Carrega a tela cheia
		final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		GerenciadorLojaApplication.scene = new Scene(loginPane, screenBounds.getWidth(), screenBounds.getHeight());
		GerenciadorLojaApplication.stage.setX(screenBounds.getMinX());
		GerenciadorLojaApplication.stage.setY(screenBounds.getMinY());
		GerenciadorLojaApplication.stage.setWidth(screenBounds.getWidth());
		GerenciadorLojaApplication.stage.setHeight(screenBounds.getHeight());
		LOGGER.info("whidth: " + screenBounds.getWidth());
		LOGGER.info("Height: " + screenBounds.getHeight());
		// Carrega o CSS
		GerenciadorLojaApplication.scene.getStylesheets().add("/styles/style_tecway.css");
		GerenciadorLojaApplication.stage.setScene(GerenciadorLojaApplication.scene);
		GerenciadorLojaApplication.stage.setResizable(true);
		GerenciadorLojaApplication.stage.sizeToScene();
		// External calls: Example= Alt+F4
		GerenciadorLojaApplication.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(final WindowEvent event) {
				GerenciadorLojaApplication.this.requestCloseApp(event);
			}
		});

		GerenciadorLojaApplication.stage.show();

	}

	/**
	 * Método que carrega a tela de login
	 */
	private void carregaLoginFXML() {
		try {
			final FXMLLoader loginLoader = new FXMLLoader();
			final InputStream in = this.getClass().getResourceAsStream(AppConstants.PAGINA_LOGIN_USUARIO);
			loginLoader.setLocation(this.getClass().getResource(AppConstants.PAGINA_LOGIN_USUARIO));
			loginLoader.setBuilderFactory(new JavaFXBuilderFactory());
			GerenciadorLojaApplication.loginPane = (AnchorPane) loginLoader.load(in);
			// Inicializa os postConstructors dos controllers
			if (loginLoader.getNamespace().values() != null && loginLoader.getNamespace().values().size() > 0) {
				for (final Object node : loginLoader.getNamespace().values()) {
					if (node instanceof AbstractController) {
						final AbstractController controller = (AbstractController) node;
						controller.setApplication(this);
						controller.customInitialize();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de login.");
			LOGGER.error(e);
		}
	}


	/**
	 * Método que carrega a tela principal
	 */
	private void carregaPrincipalFXML() {

		try {

			final FXMLLoader appLoader = new FXMLLoader();
			final InputStream in = this.getClass().getResourceAsStream(AppConstants.PAGINA_MENU_PRINCIPAL);
			appLoader.setLocation(this.getClass().getResource(AppConstants.PAGINA_MENU_PRINCIPAL));
			appLoader.setBuilderFactory(new JavaFXBuilderFactory());
			GerenciadorLojaApplication.principalPane = (StackPane) appLoader.load(in);

			// Inicializa os postConstructors dos controllers
			if (appLoader.getNamespace().values() != null && appLoader.getNamespace().values().size() > 0) {
				for (final Object node : appLoader.getNamespace().values()) {
					if (node instanceof AbstractController) {
						final AbstractController controller = (AbstractController) node;
						controller.setApplication(this);
						controller.customInitialize();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de Principal.");
			LOGGER.error(e);
		}
	}

	/**
	 * Método que carrega a tela principal
	 */
	private void carregaCadastroNovoFXML() {

		try {

			final FXMLLoader appLoader = new FXMLLoader();
			final InputStream in = this.getClass().getResourceAsStream(AppConstants.PAGINA_NOVO_USUARIO);
			appLoader.setLocation(this.getClass().getResource(AppConstants.PAGINA_NOVO_USUARIO));
			appLoader.setBuilderFactory(new JavaFXBuilderFactory());
			GerenciadorLojaApplication.principalPane = (StackPane) appLoader.load(in);
			// Inicializa os postConstructors dos controllers
			if (appLoader.getNamespace().values() != null && appLoader.getNamespace().values().size() > 0) {
				for (final Object node : appLoader.getNamespace().values()) {
					if (node instanceof AbstractController) {
						final AbstractController controller = (AbstractController) node;
						controller.setApplication(this);
						controller.customInitialize();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de Principal.");
			LOGGER.error(e);
		}
	}


	/**
	 * Método que chama a tela principal
	 */
	public void chamarTelaPrincipal() {
		try {
			this.carregaPrincipalFXML();
			GerenciadorLojaApplication.stage.getScene().setRoot(principalPane);
		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de principal.");
			LOGGER.error(e);
		}
	}

	/**
	 * Método que chama a tela principal
	 */
	public void chamarLogin() {
		try {

			carregaLoginFXML();
			GerenciadorLojaApplication.stage.getScene().setRoot(loginPane);

		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de principal.");
			LOGGER.error(e);
		}
	}

	/**
	 * Método que chama a tela principal
	 */
	public void chamarTelaNovoUsuario() {
		try {
			carregaCadastroNovoFXML();
			chamarTelaPrincipal();
		} catch (Exception e) {
			LOGGER.error("Erro ao montar a tela de principal.");
			LOGGER.error(e);
		}
	}

	/**
	 * Método responsável por tratar as requisições de fechamento da aplicação.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 20/02/2013 07:48:48
	 * @param event
	 *            - {@link Event}
	 */
	public void requestCloseApp(final Event event) {
		final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja sair do sistema?", Severity.WARN,
				Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
		final int response = alert.showAndWaitResponse();
		if (response == 0) {
			Platform.exit();
			System.exit(0);
		} else {
			event.consume();
		}
	}


}
