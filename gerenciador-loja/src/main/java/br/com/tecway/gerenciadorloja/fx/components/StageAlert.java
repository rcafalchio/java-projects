package br.com.tecway.gerenciadorloja.fx.components;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StageAlert extends Stage {

	/** Definicao de log */
	protected static final Logger LOGGER = LogManager.getLogger(StageAlert.class);

	// static properties
	private static final double MIN_STAGE_WIDTH = 200;
	private static final double MIN_STAGE_HEIGHT = 100;

	// properties
	private final IntegerProperty answer = new SimpleIntegerProperty(-1);

	// Alert properties
	private final StringProperty alertTitle = new SimpleStringProperty();
	private final StringProperty message = new SimpleStringProperty();
	private final ObjectProperty<Image> icon = new SimpleObjectProperty<Image>();
	private final ObjectProperty<Severity> severity = new SimpleObjectProperty<Severity>();
	private final BooleanProperty lightBoxMode = new SimpleBooleanProperty();
	private final BooleanProperty detailErrors = new SimpleBooleanProperty();
	private final ObjectProperty<Throwable> throwable = new SimpleObjectProperty<Throwable>();
	private final ObjectProperty<String[]> optionButtons = new SimpleObjectProperty<String[]>();

	private final Label errorLink = new Label("Detalhes");
	private final HBox errorHBox = new HBox();
	private final TextArea errorTextArea = new TextArea();
	private final StackPane errorStackPane = new StackPane();

	private AnchorPane root = null;
	private BorderPane borderPane = null;

	// mouse Properties
	private double initX;
	private double initY;

	/**
	 * Construtor
	 * 
	 * @param message
	 *            - Mensagem
	 */
	public StageAlert(final String message) {
		this("Mensagem", message); // TODO - Put in Properties
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 */
	public StageAlert(final String title, final String message) {
		this(title, message, "OK"); // TODO - Put in Properties
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param severity
	 *            - {@link Severity}
	 */
	public StageAlert(final String title, final String message, final Severity severity) {
		this(title, message, severity, "OK"); // TODO - Put in Properties
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param optionButtons
	 *            - Opções
	 */
	public StageAlert(final String title, final String message, final String... optionButtons) {
		this(title, message, Severity.INFO, optionButtons);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param severity
	 *            - {@link Severity}
	 * @param optionButtons
	 *            - Opções
	 */
	public StageAlert(final String title, final String message, final Severity severity, final String... optionButtons) {
		this(title, message, severity, Boolean.FALSE, optionButtons);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param severity
	 *            - {@link Severity}
	 * @param lightBoxMode
	 *            - Modo lightBox
	 * @param optionButtons
	 *            - Opções
	 */
	public StageAlert(final String title, final String message, final Severity severity, final Boolean lightBoxMode,
			final String... optionButtons) {
		this(title, message, severity, lightBoxMode, Boolean.FALSE, null, optionButtons);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param severity
	 *            - {@link Severity}
	 * @param lightBoxMode
	 *            - Modo lightBox
	 * @param detailErrors
	 *            - Mostrar link de Exceções
	 * @param throwable
	 *            - Exceções
	 * @param optionButtons
	 *            - Opções
	 */
	public StageAlert(final String title, final String message, final Severity severity, final Boolean lightBoxMode,
			final Boolean detailErrors, final Throwable throwable, final String... optionButtons) {
		this(title, message, severity, null, lightBoxMode, detailErrors, throwable, optionButtons);
	}

	/**
	 * Construtor
	 * 
	 * @param title
	 *            - Título
	 * @param message
	 *            - Mensagem
	 * @param severity
	 *            - {@link Severity}
	 * @param icon
	 *            - Ícone
	 * @param lightBoxMode
	 *            - Modo lightBox
	 * @param detailErrors
	 *            - Mostrar link de Exceções
	 * @param throwable
	 *            - Exceções
	 * @param optionButtons
	 *            - Opções
	 */
	public StageAlert(final String title, final String message, final Severity severity, final Image icon,
			final Boolean lightBoxMode, final Boolean detailErrors, final Throwable throwable,
			final String... optionButtons) {
		this.icon.set(icon);
		this.message.set(message);
		this.alertTitle.set(title);
		this.severity.set(severity);
		this.lightBoxMode.set(lightBoxMode);
		this.detailErrors.set(detailErrors);
		this.throwable.set(throwable);
		this.optionButtons.set(optionButtons);

		this.initComponents();
	}

	/**
	 * Método responsável por iniciar os componentes gráficos.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 04/01/2013 08:56:26
	 */
	private void initComponents() {
		this.initModality(Modality.APPLICATION_MODAL);
		this.initStyle(StageStyle.TRANSPARENT);

		this.errorLink.getStyleClass().add("stage-alert-error-link");

		this.errorHBox.setAlignment(Pos.CENTER_RIGHT);
		this.errorHBox.getChildren().add(this.errorLink);

		this.errorTextArea.setEditable(false);
		this.errorTextArea.getStyleClass().addAll("default-scroll-pane", "default-text-area",
				"stage-alert-error-text-area");

		this.errorStackPane.getChildren().add(this.errorTextArea);
		this.errorStackPane.getStyleClass().add("stage-alert-error-stack-pane");

		this.borderPane = this.loadBorderPane();

		this.borderPane.setMinWidth(MIN_STAGE_WIDTH);
		this.borderPane.setMinHeight(MIN_STAGE_HEIGHT);

		if (this.lightBoxMode.get()) {
			final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			this.root = new AnchorPane();
			this.root.setPrefWidth(visualBounds.getWidth());
			this.root.setPrefHeight(visualBounds.getHeight());
			this.root.setStyle("-fx-background-color: rgba(127, 127, 127, 0.5);");
			this.root.getChildren().add(this.borderPane);
			// Keeps the popup in its own center
			this.borderPane.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
						final Number newValue) {
					StageAlert.this.borderPane.setTranslateX((int) (visualBounds.getWidth() / 2 - StageAlert.this.borderPane
							.getWidth() / 2));
				}
			});

			this.borderPane.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
						final Number newValue) {
					StageAlert.this.borderPane.setTranslateY((int) (visualBounds.getHeight() / 2 - StageAlert.this.borderPane
							.getHeight() / 2));
				}
			});
		} else {
			this.root = new AnchorPane();
			// this.root.setStyle("-fx-padding: 8 12 0 0;");
			this.root.getChildren().add(this.borderPane);
			AnchorPane.setTopAnchor(this.borderPane, 0D);
			// Keeps the popup in its own center
			this.borderPane.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
						final Number newValue) {
					if (oldValue.doubleValue() > 0) {
						StageAlert.this.setX(StageAlert.this.getX() - (newValue.doubleValue() - oldValue.doubleValue())
								/ 2);
					}
				}
			});
			this.borderPane.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
						final Number newValue) {
					if (oldValue.doubleValue() > 0) {
						StageAlert.this.setY(StageAlert.this.getY() - (newValue.doubleValue() - oldValue.doubleValue())
								/ 2);
					}
				}
			});
		}

		final Scene scene = new Scene(this.root);
		scene.getStylesheets().add("/styles/style.css");
		scene.setFill(Color.TRANSPARENT);

		this.setScene(scene);
		this.setTitle(this.alertTitle.get());
		this.updateAlert();

		// External calls: Example= Alt+F4
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(final WindowEvent event) {
				event.consume();
			}
		});
	}

	/**
	 * Método responsável por 'preencher' os componentes de tela com os parâmetros de entrada da instância.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 04/01/2013 08:56:47
	 */
	private void updateAlert() {
		this.updateTitle(this.alertTitle.get());
		if (this.borderPane != null) {
			// Icon
			if (this.icon.get() != null) {
				((ImageView) ((Label) this.borderPane.lookup("#messageLabel")).getGraphic()).setImage(this.icon.get());
			} else if (this.severity.get() != null) {
				((ImageView) ((Label) this.borderPane.lookup("#messageLabel")).getGraphic()).setImage(this.severity
						.get().getIcon());
			}
			// Message
			((Label) this.borderPane.lookup("#messageLabel")).setText(this.message.get());
			// Controls
			((HBox) this.borderPane.lookup("#optionsButtons")).getChildren().clear();
			if (this.optionButtons.get() != null) {
				for (int i = 0; i < this.optionButtons.get().length; i++) {
					final int index = i;
					final Button button = new Button(this.optionButtons.get()[i]);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(final ActionEvent event) {
							StageAlert.this.answer.set(index);
							StageAlert.this.close();
						}
					});
					((HBox) this.borderPane.lookup("#optionsButtons")).getChildren().add(button);
				}
			}
			if (this.detailErrors.get()) {
				if (this.throwable.get() != null) {
					this.errorTextArea.setText(this.stackTraceToString(this.throwable.get()));
				}
				((BorderPane) this.borderPane.lookup("#errorBorderPane")).setTop(this.errorHBox);
				this.errorLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(final MouseEvent event) {
						if (((BorderPane) StageAlert.this.borderPane.lookup("#errorBorderPane")).getCenter() != null) {
							((BorderPane) StageAlert.this.borderPane.lookup("#errorBorderPane")).setCenter(null);
						} else {
							((BorderPane) StageAlert.this.borderPane.lookup("#errorBorderPane"))
									.setCenter(StageAlert.this.errorStackPane);
						}
						StageAlert.this.sizeToScene();
					}
				});
			}
		}
	}

	/**
	 * Método responsável por carregar o FXML do 'Alert'
	 * 
	 * @author Ricardo Cafalchio
	 * @since 04/01/2013 08:57:29
	 * @return {@link BorderPane}
	 */
	private BorderPane loadBorderPane() {
		BorderPane borderPane = null;
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/pages/stagealert.fxml"));
		try {
			borderPane = (BorderPane) loader.load();
			// Add events
			((Label) borderPane.lookup("#closeLabel")).setOnMouseClicked(this.closeStage);
			if (!this.lightBoxMode.get()) {
				borderPane.getTop().setCursor(Cursor.MOVE);
				borderPane.getTop().setOnMousePressed(this.moveStageStart);
				borderPane.getTop().setOnMouseDragged(this.moveStageEnd);
			}
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return borderPane;
	}

	/**
	 * Handler responsável por fechar o 'Alert'
	 */
	private final EventHandler<MouseEvent> closeStage = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StageAlert.this.close();
		}
	};

	/**
	 * Handler responsável por controlar o inicio da movimentação do 'Alert'
	 */
	private final EventHandler<MouseEvent> moveStageStart = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StageAlert.this.initX = event.getScreenX() - StageAlert.this.getX();
			StageAlert.this.initY = event.getScreenY() - StageAlert.this.getY();
		}
	};

	/**
	 * Handler responsável por controlar a movimentação do 'Alert'
	 */
	private final EventHandler<MouseEvent> moveStageEnd = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent event) {
			StageAlert.this.setX(event.getScreenX() - StageAlert.this.initX);
			StageAlert.this.setY(event.getScreenY() - StageAlert.this.initY);
		}
	};

	/**
	 * Método responsável por abrir o 'Alert' em uma posição específica.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 04/01/2013 08:59:32
	 * @param x
	 *            - X position
	 * @param y
	 *            - Y position
	 */
	public void show(final double x, final double y) {
		this.setX(x);
		this.setY(y);
		super.show();
	}

	/**
	 * Método responsável por abrir o 'Alert' em uma posição específica.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 05/02/2013 08:59:32
	 * @param x
	 *            - posição X
	 * @param y
	 *            - posição Y
	 */
	public void showAndWait(final double x, final double y) {
		this.setX(x);
		this.setY(y);
//		super.showAndWait();
		super.show();
	}

	/**
	 * Método responsável por abrir o 'Alert' em uma posição específica.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 05/02/2013 09:00:00
	 * @return - Resposta
	 */
	public int showAndWaitResponse() {
		super.showAndWait();
		return this.getAnswer();
	}

	/**
	 * Método responsável por abrir o 'Alert' em uma posição específica.
	 * 
	 * @author Ricardo Cafalchio
	 * @since 05/02/2013 09:00:00
	 * @param x
	 *            - X position
	 * @param y
	 *            - Y position
	 * @return - Resposta
	 */
	public int showAndWaitResponse(final double x, final double y) {
		this.setX(x);
		this.setY(y);
		// super.showAndWait();
		super.show();
		return this.getAnswer();
	}

	/**
	 * Método responsável por atualizar título do popup
	 * 
	 * @author Ricardo Cafalchio
	 * @since 02/01/2013 15:23:25
	 * @param popupTitle
	 *            - popupTitle
	 */
	public void updateTitle(final String popupTitle) {
		if (this.borderPane != null) {
			final Label titleLabel = (Label) this.borderPane.lookup("#titleLabel");
			if (titleLabel != null) {
				titleLabel.setText(popupTitle);
			}
		}
	}

	/**
	 * Método responsável por converter uma 'exceção' em 'String'
	 * 
	 * @author Ricardo Cafalchio
	 * @since 04/01/2013 08:59:18
	 * @param e
	 *            - {@link Throwable}
	 * @return - {@link String} com a pilha de exceção
	 */
	public String stackTraceToString(final Throwable e) {
		String exceptionAsString = null;
		if (e != null) {
			final StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
		}
		return exceptionAsString;
	}

	/**
	 * @return the answerProperty
	 */
	public IntegerProperty answerProperty() {
		return this.answer;
	}

	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return this.answer.get();
	}

	/**
	 * Enum para definir o status da Dialog.
	 * 
	 * @author jabes.felipe
	 */
	public enum Severity {

		ERROR("/images/error_icon.png"), INFO("/images/info_icon.png"), WARN("/images/warn_icon.png"), QUESTION(
				"/images/help_icon.png"), PLAIN(null);

		private Image icon;

		/**
		 * Construtor
		 * 
		 * @param icon
		 *            - Ícone
		 */
		private Severity(final String icon) {
			if (icon != null) {
				this.icon = new Image(StageAlert.class.getResourceAsStream(icon));
			}
		}

		/**
		 * @return the icon
		 */
		public Image getIcon() {
			return this.icon;
		}

		/**
		 * @param icon
		 *            the icon to set
		 */
		public void setIcon(final Image icon) {
			this.icon = icon;
		}

	}

}