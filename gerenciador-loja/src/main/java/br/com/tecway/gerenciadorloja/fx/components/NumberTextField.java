package br.com.tecway.gerenciadorloja.fx.components;

import java.math.BigDecimal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.utils.NumberUtils;

public class NumberTextField extends TextField {

	private String prefix;
	private String regexPattern;
	private String endsWith;
	private TypeNumberEnum typeNumber;

	/**
	 * Construtor
	 */
	public NumberTextField() {
		super();
		this.applyTextListener();
		this.applyFocusListener();
	}

	/**
	 * Construtor
	 * 
	 * @param typeNumber
	 *            - {@link TypeNumberEnum}
	 */
	public NumberTextField(final TypeNumberEnum typeNumber) {
		super();
		this.setTypeNumber(typeNumber);
		this.applyTextListener();
		this.applyFocusListener();
	}

	/**
	 * @return the typeNumber
	 */
	public TypeNumberEnum getTypeNumber() {
		return this.typeNumber;
	}

	/**
	 * @param typeNumber
	 *            the typeNumber to set
	 */
	public void setTypeNumber(final TypeNumberEnum typeNumber) {
		this.applyPattern(typeNumber);
	}

	/**
	 * Método responsável por aplicar o pattern
	 * 
	 * @param typeNumber
	 *            - Pattern
	 */
	private void applyPattern(final TypeNumberEnum typeNumber) {
		this.typeNumber = typeNumber;
		if (this.typeNumber != null) {
			if (this.typeNumber.equals(TypeNumberEnum.CURRENCY)) {
				this.prefix = "R$ ";
				this.endsWith = "";
				this.regexPattern = "((R\\$ )?[1-9]{1}[0-9]{0,}(\\,[0-9]{0,2})?|0(\\,[0-9]{0,2})?)?$";
			} else if (this.typeNumber.equals(TypeNumberEnum.DOUBLE)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "([1-9]{1}[0-9]{0,11}(\\.[0-9]{0,11})?|0(\\.[0-9]{0,11})?)?$";
			} else if (this.typeNumber.equals(TypeNumberEnum.HORA)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "([1-9]{1}[0-9]{0,2}(\\,[0-9]{0,2})?|0(\\,[0-9]{0,2})?)?$";
			} else if (this.typeNumber.equals(TypeNumberEnum.PERCENTAGE)) {
				this.prefix = "";
				this.endsWith = "%";
				this.regexPattern = "^(0*100{1,1}\\,?((?<=\\,)0*)?%?$)|(^0*\\d{0,2}\\,?((?<=\\,)\\d*)?%?)$";
			} else if (this.typeNumber.equals(TypeNumberEnum.INTEGER)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "^([1-9]{1}[0-9]{0,})$|^$";
			} else if (this.typeNumber.equals(TypeNumberEnum.CPF)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "^([0-9]{0,11})$";
			} else if (this.typeNumber.equals(TypeNumberEnum.DDD)) {
				this.prefix = "(";
				this.endsWith = ")";
				this.regexPattern = "^([1-9]{1}[0-9]{0,1})$|^$";
			} else if (this.typeNumber.equals(TypeNumberEnum.TEL)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "^([1-9]{1}[0-9]{0,7})$|^$";
			} else if (this.typeNumber.equals(TypeNumberEnum.CEL)) {
				this.prefix = "";
				this.endsWith = "";
				this.regexPattern = "^([1-9]{1}[0-9]{0,8})$|^$";
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
				if (NumberTextField.this.typeNumber != null) {
					if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.PERCENTAGE)) {
						if (newValue.replace(NumberTextField.this.endsWith, "").replace(".", ",")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue.replace(".", ","));
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.DOUBLE)) {
						if (newValue.replace(NumberTextField.this.endsWith, "").matches(
								NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.HORA)) {
						if (newValue.replace(NumberTextField.this.endsWith, "").replace(".", ",")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue.replace(".", ","));
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.CURRENCY)) {
						if (newValue.replace(NumberTextField.this.prefix, "").replace(".", "")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.INTEGER)) {
						if (newValue.replace(NumberTextField.this.prefix, "")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.CPF)) {
						if (newValue.replace(NumberTextField.this.prefix, "")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.DDD)) {
						if (newValue.replace(NumberTextField.this.prefix, "")
								.replace(NumberTextField.this.endsWith, "").matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.TEL)) {
						if (newValue.replace(NumberTextField.this.prefix, "")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
						}
					} else if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.CEL)) {
						if (newValue.replace(NumberTextField.this.prefix, "")
								.matches(NumberTextField.this.regexPattern)) {
							NumberTextField.this.setText(newValue);
						} else {
							NumberTextField.this.setText(oldValue);
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
				if (NumberTextField.this.typeNumber != null) {
					if (newValue) {
						if (NumberTextField.this.typeNumber.equals(TypeNumberEnum.PERCENTAGE)
								|| NumberTextField.this.typeNumber.equals(TypeNumberEnum.DOUBLE)
								|| NumberTextField.this.typeNumber.equals(TypeNumberEnum.HORA)) {
							NumberTextField.this.setText(NumberTextField.this.getText().replace(
									NumberTextField.this.endsWith, ""));
						} else {
							NumberTextField.this.setText(NumberTextField.this.getText()
									.replace(NumberTextField.this.prefix, "").replace(".", "")
									.replace(NumberTextField.this.endsWith, ""));
						}
					} else {
						if (!NumberTextField.this.getText().trim().equals("")) {
							String valorFormatado = "";
							valorFormatado = aplicarMascara(NumberTextField.this.typeNumber,
									NumberTextField.this.getText());
							NumberTextField.this.setText(valorFormatado);
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
	public static String aplicarMascara(TypeNumberEnum typeNumber, String valor) {

		String valorFormatado = "";

		NumberTextField numberTextField = new NumberTextField(typeNumber);
		numberTextField.setText(valor);

		if (typeNumber.equals(TypeNumberEnum.CURRENCY)) {
			valorFormatado = numberTextField.prefix + valor.replace(".", ",");
			if (!valorFormatado.matches("R\\$ [0-9]+\\,+[0-9]+")) {
				valorFormatado = valorFormatado + ",00";
			}
		} else if (typeNumber.equals(TypeNumberEnum.DOUBLE)) {
			valorFormatado = NumberUtils.formatBigDecimal(new BigDecimal(numberTextField.getText().replace(",", ".")));
		} else if (typeNumber.equals(TypeNumberEnum.HORA)) {
			valorFormatado = new BigDecimal(numberTextField.getText().replace(",", ".")).toString();
		} else if (typeNumber.equals(TypeNumberEnum.PERCENTAGE)) {
			valorFormatado = numberTextField.getText() + numberTextField.endsWith;
		} else if (typeNumber.equals(TypeNumberEnum.DDD)) {
			valorFormatado = numberTextField.prefix + numberTextField.getText() + numberTextField.endsWith;
		} else {
			valorFormatado = numberTextField.getText();
		}

		return valorFormatado;
	}

	/**
	 * Retorna um Double a partir de uma string formatada por Currency
	 * 
	 * @param valor
	 * @return Double
	 */
	public static Double converterCurrencyToDouble(String valor) {
		valor = valor.replace(new NumberTextField(TypeNumberEnum.CURRENCY).prefix, "").replace(",", ".");
		return NumberUtils.toDouble(valor);
	}

	/**
	 * Retorna um Double a partir de uma string formatada por Percentage
	 * 
	 * @param valor
	 * @return Double
	 */
	public static Double converterPercentageToDouble(String valor) {
		valor = valor.replace(new NumberTextField(TypeNumberEnum.PERCENTAGE).prefix, "").replace(",", ".");
		valor = valor.replace(new NumberTextField(TypeNumberEnum.PERCENTAGE).endsWith, "");
		return NumberUtils.toDouble(valor);
	}

}
