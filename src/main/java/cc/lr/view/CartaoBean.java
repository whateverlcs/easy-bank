package cc.lr.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import cc.lr.entidade.CartaoCredito;
import cc.lr.servico.ServicoCartao;

@Named
@ViewScoped
public class CartaoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5585386509448503950L;
	
	@Inject
	private ServicoCartao servicoCartao;
	
	private List<CartaoCredito> cartoes;
	
	private CartaoCredito cartaoCredito;
	
	private CartaoCredito cartaoShow;
	
	public CartaoBean() {
	}
	
	@PostConstruct
	public void init() {
		this.cartaoCredito = new CartaoCredito();
		this.atualizarCartoes();
	}
	
	private void atualizarCartoes() {
		this.cartoes = this.servicoCartao.listar();
	}
	
	public void cadastrar() {
		try {
			this.servicoCartao.cadastrar(this.cartaoCredito);
			this.cartaoShow = this.cartaoCredito;
			this.cartaoCredito = new CartaoCredito();
			this.atualizarCartoes();
			Messages.addGlobalInfo("Cartão criado com sucesso!");
		}catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
		}
	}

	public CartaoCredito getCartaoShow() {
		return cartaoShow;
	}

	public void setCartaoShow(CartaoCredito cartaoShow) {
		this.cartaoShow = cartaoShow;
	}

	public ServicoCartao getServicoCartao() {
		return servicoCartao;
	}

	public void setServicoCartao(ServicoCartao servicoCartao) {
		this.servicoCartao = servicoCartao;
	}

	public List<CartaoCredito> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<CartaoCredito> cartoes) {
		this.cartoes = cartoes;
	}

	public CartaoCredito getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}
	
}
