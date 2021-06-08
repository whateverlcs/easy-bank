package cc.lr.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_TRANSACAO")
@NamedQuery(name = "Transacao.listarTodos", query = "SELECT t from Transacao t")
public class Transacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7634319100765482141L;
	
	@Id
	@SequenceGenerator(name = "SEQUENCIAL_TRANSACAO", sequenceName = "NUM_SEQ_TSC", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCIAL_TRANSACAO")
	@Column(name = "NUM_SEQ_TSC")
	private Long id;
	
	@NotEmpty
	private String tipoTransacao;
	
	@NotNull
	private Double valor;
	
	@NotEmpty
	private String credor;
	
	private Double saldoHist;
	
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataTransacao;
	
	@ManyToOne
	@JoinColumn(name = "cartaocredito_id")
	@NotNull
	private CartaoCredito cartaoCredito;
	
	public Transacao() {
		this.dataTransacao = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}

	public CartaoCredito getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public Double getSaldoHist() {
		return saldoHist;
	}

	public void setSaldoHist(Double saldoHist) {
		this.saldoHist = saldoHist;
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
		Transacao other = (Transacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
