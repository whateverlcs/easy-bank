package cc.lr.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import cc.lr.entidade.CartaoCredito;
import cc.lr.entidade.Transacao;
import cc.lr.servico.ServicoTransacao;

@Named
@ViewScoped
public class TransacaoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6859307506842089800L;
	
	@Inject
	private ServicoTransacao servicoTransacao;
	
	private List<Transacao> transacoes;
	
	private Transacao transacao;
	
	/**
	 * Variável usada para a pesquisa de transações por cartão de crédito
	 */
	private String numero;
	
	public TransacaoBean() {
	}
	
	@PostConstruct
	public void init() {
		this.transacao = new Transacao();
		this.transacao.setCartaoCredito(new CartaoCredito());
	}
	
	private void atualizarTransacoes() {
		this.transacoes = this.servicoTransacao.listar();
	}
	
	public void cadastrar() {
		try {
			this.servicoTransacao.cadastrar(this.transacao);
			this.transacao = new Transacao();
			this.atualizarTransacoes();
			this.transacao.setCartaoCredito(new CartaoCredito());
			Messages.addGlobalInfo("Transação realizada com sucesso!");
		}catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
		}
	}
	
	public void pesquisar() {
		this.transacoes = this.servicoTransacao.buscarPorTransacao(this.numero);
	}

	public ServicoTransacao getServicoTransacao() {
		return servicoTransacao;
	}

	public void setServicoTransacao(ServicoTransacao servicoTransacao) {
		this.servicoTransacao = servicoTransacao;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public Transacao getTransacao() {
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
