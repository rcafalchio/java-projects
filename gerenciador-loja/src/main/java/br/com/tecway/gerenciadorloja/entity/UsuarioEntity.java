package br.com.tecway.gerenciadorloja.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tabela_usuario")
@NamedQueries({
		@NamedQuery(name = "UsuarioEntity.buscarUsuarioPorLogin", query = "SELECT a FROM UsuarioEntity a "
				+ " WHERE a.login = :login"),
		@NamedQuery(name = "UsuarioEntity.findAll", query = "SELECT a FROM UsuarioEntity a " + " order by a.nome") })
public class UsuarioEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6556684340752337587L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_usuario")
	private Integer codigo;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
	private ContatoEntity contato;

	@OneToMany(mappedBy = "vendedor")
	private List<VendaEntity> vendas;

	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String login;
	@Column(nullable = false)
	private String senha;
	@Column(nullable = false)
	private Long cpf;
	@Column(name = "flag_administrador", nullable = false)
	private boolean flagAdministrador;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "br.com.tecway.gerenciadorloja.entity.Usuario[ codigo=" + this.codigo + " ]";
	}

	public boolean isFlagAdministrador() {
		return flagAdministrador;
	}

	public void setFlagAdministrador(boolean flagAdministrador) {
		this.flagAdministrador = flagAdministrador;
	}

	public ContatoEntity getContato() {
		return contato;
	}

	public void setContato(ContatoEntity contato) {
		this.contato = contato;
	}

	public List<VendaEntity> getVendas() {
		return vendas;
	}

	public void setVendas(List<VendaEntity> vendas) {
		this.vendas = vendas;
	}

}
