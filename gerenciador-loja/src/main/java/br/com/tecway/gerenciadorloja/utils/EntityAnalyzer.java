package br.com.tecway.gerenciadorloja.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 01/03/2013
 */
public class EntityAnalyzer {

	private static final Logger LOGGER = LogManager.getLogger(EntityAnalyzer.class);

	/**
	 * Método responsável por retornar o hashCode por reflection de uma entidade ou pk de uma entidade
	 * 
	 * @param object
	 * @return
	 */
	public static int hashCodeAnalyzer(final Object object) {
		int hash = 0;
		if (object != null) {
			if (object.getClass().getAnnotation(Embeddable.class) != null) {
				// PK
				final List<Field> fields = getRecursiveDeclaredFields(object.getClass());
				for (final Field field : fields) {
					if (field.getAnnotation(Column.class) != null && field.getAnnotation(Transient.class) == null) {
						final Object value = getFieldValue(object, field);
						if (value != null) {
							hash += value.hashCode();
						}
					}
				}
			} else {
				// Entity
				final List<Field> fields = getRecursiveDeclaredFields(object.getClass());
				for (final Field field : fields) {
					if (field.getAnnotation(Transient.class) == null
							&& (field.getAnnotation(Column.class) != null && field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null)) {
						final Object value = getFieldValue(object, field);
						if (value != null) {
							hash += value.hashCode();
						}
						break;
					}
				}
			}
		}
		return hash;
	}

	/**
	 * Método responsável por comparar a equidade por reflection de uma entidade ou pk de uma entidade
	 * 
	 * @param object
	 * @param other
	 * @return
	 */
	public static boolean equalsAnalyzer(final Object object, final Object other) {
		boolean equals = true;
		if (object != null && other == null) {
			equals = false;
		} else if (object == null && other != null) {
			equals = false;
		} else if (object != null && other != null) {
			// Same memory reference
			if (object != other) {
				// Same instance class
				if (!object.getClass().isInstance(other)) {
					equals = false;
				} else {
					if (object.getClass().getAnnotation(Embeddable.class) != null) {
						// PK
						final List<Field> fields = getRecursiveDeclaredFields(object.getClass());
						for (final Field field : fields) {
							if (field.getAnnotation(Column.class) != null && field.getAnnotation(Transient.class) == null) {
								final Object value = getFieldValue(object, field);
								final Object otherValue = getFieldValue(other, field);
								if (value == null && otherValue != null) {
									equals = false;
									break;
								} else if (value != null && otherValue == null) {
									equals = false;
									break;
								} else if (value != null && otherValue != null) {
									if (!value.equals(otherValue)) {
										equals = false;
										break;
									}
								}
							}
						}
					} else {
						// Entity
						final List<Field> fields = getRecursiveDeclaredFields(object.getClass());
						for (final Field field : fields) {
							if (field.getAnnotation(Transient.class) == null
									&& (field.getAnnotation(Column.class) != null && field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null)) {
								final Object value = getFieldValue(object, field);
								final Object otherValue = getFieldValue(other, field);
								if (value == null && otherValue != null) {
									equals = false;
									break;
								} else if (value != null && otherValue == null) {
									equals = false;
									break;
								} else if (value != null && otherValue != null) {
									if (!value.equals(otherValue)) {
										equals = false;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return equals;
	}

	/**
	 * Método responsável por obter por reflection o método get de um field do objeto.
	 * 
	 * @param object
	 * @param field
	 * @return
	 */
	private static Method retrieveGetMethodFromField(final Object object, final Field field) {
		Method method = null;
		if (object != null && field != null) {
			final String fieldName = field.getName();
			try {
				method = object.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
			} catch (final NoSuchMethodException e) {
				LOGGER.warn("Método 'get' não declarado na classe " + object.getClass() + " para o campo " + field.getName(), e);
			} catch (final SecurityException e) {
				LOGGER.warn("Visibilidade do método 'get' restrita na classe " + object.getClass() + " para o campo " + field.getName(), e);
			}
		}
		return method;
	}

	/**
	 * Método responsável por obter por reflection o valor de um field de um objeto.
	 * 
	 * @param object
	 * @param field
	 * @return
	 */
	private static Object getFieldValue(final Object object, final Field field) {
		Object value = null;
		final Method getMethod = retrieveGetMethodFromField(object, field);
		if (getMethod != null) {
			try {
				value = getMethod.invoke(object);
			} catch (final IllegalAccessException e) {
				LOGGER.warn("Visibilidade do método 'get' restrita na classe " + object.getClass() + " para o campo " + field.getName(), e);
			} catch (final IllegalArgumentException e) {
				LOGGER.warn("Parâmetros inválidos no método 'get' da classe " + object.getClass() + " para o campo " + field.getName(), e);
			} catch (final InvocationTargetException e) {
				LOGGER.warn("Erro ao invicar o método 'get' da classe " + object.getClass() + " para o campo " + field.getName(), e);
			}
		}
		return value;
	}

	/**
	 * Obtém os 'fields' do Objeto e em suas superclasses.
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getRecursiveDeclaredFields(final Class<?> clazz) {
		final List<Field> fields = new ArrayList<Field>();
		if (clazz != null) {
			final Field[] arrayFields = clazz.getDeclaredFields();
			for (final Field field : arrayFields) {
				fields.add(field);
			}
			fields.addAll(getRecursiveDeclaredFields(clazz.getSuperclass()));
		}
		return fields;
	}

}