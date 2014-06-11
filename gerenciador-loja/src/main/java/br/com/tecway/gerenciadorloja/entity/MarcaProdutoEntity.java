package br.com.tecway.gerenciadorloja.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidade de cadastro da marca do produto
 * 
 * @author Ricardo Cafalchio
 * @since 13/08/2013
 */
@Entity
@Table(name = "tabela_marca")
@NamedQueries({ @NamedQuery(name = "MarcaProdutoEntity.findAll", query = "SELECT a FROM MarcaProdutoEntity a "
		+ " ORDER BY a.marca")

})
public class MarcaProdutoEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;
	private String marca;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ProdutoEntity> produtos;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public List<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return marca;
	}

}
