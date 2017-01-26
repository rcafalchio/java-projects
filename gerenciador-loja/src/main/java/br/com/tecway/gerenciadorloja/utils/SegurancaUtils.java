package br.com.tecway.gerenciadorloja.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
import br.com.tecway.gerenciadorloja.entity.UsuarioEntity;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 06/03/2013
 */
public class SegurancaUtils {

	private static UsuarioEntity usuarioEntity;

	/**
	 * Retorna a senha criptografada
	 * 
	 * @param senha
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String geraCriptografia(String senha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(senha.getBytes());

			final BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return senha;
		}
	}

	public static UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	public static void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		SegurancaUtils.usuarioEntity = usuarioEntity;
	}

	public static void limparUsuario() {
		SegurancaUtils.usuarioEntity = null;
	}

}
