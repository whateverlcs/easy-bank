package cc.lr.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_CARTAOCREDITO")
@NamedQuery(name = "CartaoCredito.listarTodos", query = "SELECT c from CartaoCredito c")
public class CartaoCredito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8915754954167469035L;
	
	@Id
	@SequenceGenerator(name = "SEQUENCIAL_CARTAO", sequenceName = "NUM_SEQ_CC", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCIAL_CARTAO")
	@Column(name = "NUM_SEQ_CC")
	private Long id;
	
	@NotEmpty
	private String numero;
	
	@NotEmpty
	private String cvv;
	
	@NotNull
	private Double saldo;
	
	@NotNull
	private Date dataCriacao;
	
	@NotNull
	private Date dataValidade;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@NotNull
	private Cliente cliente;
	
	@OneToMany(mappedBy = "cartaoCredito")
	private List<Transacao> transacoes;
	
	public CartaoCredito() {
		this.dataCriacao = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaoCredito other = (CartaoCredito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
