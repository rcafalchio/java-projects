package br.com.tecway.gerenciadorloja.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Entidade Contato
 * 
 * @author Ricardo Cafalchio
 * @since 24/06/2013
 */
@Entity
@Table(name="tabela_contato")
public class ContatoEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4863963689298404948L;

	private String email;
	@Column(name = "ddd_celular")
	private Short dddCelular;
	@Column(name = "telefone_celular")
	private Long telefoneCelular;
	@Column(name = "ddd_residencial")
	private Short dddResidencial;
	@Column(name = "telefone_residencial")
	private Long telefoneResidencial;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = { @Parameter(name = "property", value = "usuario") })
	@Column(name = "codigo_usuario")
	private Integer codigoUsuario;

	@PrimaryKeyJoinColumn
	@OneToOne
	private UsuarioEntity usuario;

	@Override
	public String toString() {
		return "br.com.tecway.gerenciadorloja.entity.Usuario[ codigo=" + this.usuario.getCodigo() + " ]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(Short dddCelular) {
		this.dddCelular = dddCelular;
	}

	public Long getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Long telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Short getDddResidencial() {
		return dddResidencial;
	}

	public void setDddResidencial(Short dddResidencial) {
		this.dddResidencial = dddResidencial;
	}

	public Long getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(Long telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

}
