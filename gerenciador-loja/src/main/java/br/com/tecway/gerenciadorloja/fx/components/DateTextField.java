package br.com.tecway.gerenciadorloja.fx.components;

import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DateTextField extends TextField {

	private static final Logger LOGGER = LogManager.getLogger(DateTextField.class);

	private String regexPattern;

	/**
	 * Construtor
	 */
	public DateTextField() {
		super();
		this.applyPattern();
		this.applyTextListener();
		this.applyFocusListener();
	}

	/**
	 * Método responsável por aplicar o pattern
	 * 
	 * @param typeNumber
	 *            - Pattern
	 */
	private void applyPattern() {
		this.regexPattern = "^([0-9]{0,2})(/{0,1})([0-9]{0,2})(/{0,1})([0-9]{0,4})|^$";
		// this.regexPattern = "[0-9]{0,2}/[0-9]{0,2}/[0-9]{0,4}";
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
				if (newValue.matches(DateTextField.this.regexPattern)) {
					DateTextField.this.setText(newValue);
				} else {
					DateTextField.this.setText(oldValue);
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
					if (!DateTextField.this.getText().trim().equals("")) {
						String valorFormatado = "";
						valorFormatado = aplicarMascara(DateTextField.this.getText());
						DateTextField.this.setText(valorFormatado);
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
	public static String aplicarMascara(String valor) {

		final StringBuffer valorFormatado = new StringBuffer("");

		try {
			if (valor.indexOf('/') > 0) {
				String[] partes = valor.split(Pattern.quote("/"));
				if (partes != null && partes.length == 2) {
					valorFormatado.append(partes[0]);
					valorFormatado.append("/");
					valorFormatado.append(partes[1]);
					valorFormatado.append("/");
				}else{
					valorFormatado.append(valor);
				}
			} else {
				valorFormatado.append(valor.substring(0, 2));
				valorFormatado.append("/");
				valorFormatado.append(valor.substring(2, 4));
				valorFormatado.append("/");
				valorFormatado.append(valor.substring(4, 8));
			}
		} catch (Exception e) {
			LOGGER.equals(e);
		}

		return valorFormatado.toString();
	}

}
