package br.com.tecway.gerenciadorloja.fx.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import br.com.tecway.gerenciadorloja.constants.TypeCustomTextEnum;

public class CustomTextField extends TextField {

	private String regexPattern;
	private TypeCustomTextEnum type;

	/**
	 * Construtor
	 */
	public CustomTextField() {
		super();
		this.applyTextListener();
		this.applyFocusListener();
	}

	/**
	 * Construtor
	 * 
	 * @param type
	 *            - {@link TypeCustomTextEnum}
	 */
	public CustomTextField(final TypeCustomTextEnum type) {
		super();
		this.setType(type);
		this.applyTextListener();
		this.applyFocusListener();
	}

	/**
	 * Método responsável por aplicar o pattern
	 * 
	 * @param typeNumber
	 *            - Pattern
	 */
	private void applyPattern(final TypeCustomTextEnum type) {

		this.type = type;

		if (this.type != null) {
			if (this.type.equals(TypeCustomTextEnum.MAIUSCULA)) {
				this.regexPattern = "[a-zA-Z0-9\\s]*";
			}
		}
	}

	/**
	 * Método responsável por aplicar o Listerner de text do pattern
	 * 
	 */
	private void applyTextListener() {
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observableValue, final String oldValue,
					final String newValue) {
				if (CustomTextField.this.type != null && !oldValue.isEmpty()) {
					if (CustomTextField.this.type.equals(TypeCustomTextEnum.MAIUSCULA)) {
						if (newValue.matches(CustomTextField.this.regexPattern)) {
							CustomTextField.this.setText(newValue);
						} else {
							CustomTextField.this.setText(oldValue);
						}
					}
				}
			}
		});
	}

	/**
	 * Método responsável por aplicar o Listerner de focus do pattern
	 * 
	 */
	private void applyFocusListener() {
		this.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue,
					final Boolean newValue) {
				if (CustomTextField.this.type != null) {
					if (newValue) {
						// if (CustomTextField.this.typeNumber.equals(TypeNumberEnum.PERCENTAGE)
						// || CustomTextField.this.typeNumber.equals(TypeNumberEnum.DOUBLE)
						// || CustomTextField.this.typeNumber.equals(TypeNumberEnum.HORA)) {
						// CustomTextField.this.setText(CustomTextField.this.getText().replace(
						// CustomTextField.this.endsWith, ""));
						// } else {
						// CustomTextField.this.setText(CustomTextField.this.getText()
						// .replace(CustomTextField.this.prefix, "").replace(".", ""));
						// }
					} else {
						if (!CustomTextField.this.getText().trim().equals("")) {
							String valorFormatado = "";
							valorFormatado = aplicarMascara(CustomTextField.this.type, CustomTextField.this.getText());
							CustomTextField.this.setText(valorFormatado);
						}
					}
				}
			}
		});
	}

	/**
	 * 
	 * @param typeNumber
	 * @return
	 */
	public static String aplicarMascara(TypeCustomTextEnum type, String valor) {

		String valorFormatado = "";

		CustomTextField numberTextField = new CustomTextField(type);
		numberTextField.setText(valor);

		if (type.equals(TypeCustomTextEnum.MAIUSCULA)) {
			valorFormatado = numberTextField.getText().toUpperCase();
		}

		return valorFormatado;
	}

	public TypeCustomTextEnum getType() {
		return type;
	}

	public void setType(TypeCustomTextEnum type) {
		this.applyPattern(type);
	}


}
