package cc.lr.servico;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import cc.lr.entidade.CartaoCredito;
import cc.lr.entidade.Transacao;

@Stateless
public class ServicoTransacao extends ServicoComum<Transacao> {
	
	/**
	 * Verifica se o número e cvv existem no banco de dados e se os dois batem, também verifica, 
	 * caso for débito ele verifica se o valor da transação não irá ultrapassar o saldo existente
	 */
	@Override
	protected void validarCadastramento(Transacao t) throws Exception {
		CartaoCredito cartao = buscarPorCartao(t.getCartaoCredito().getNumero(), t.getCartaoCredito().getCvv());
		
		if(cartao == null) {
			throw new Exception("Cartão Inválido");
		}
		
		if(t.getTipoTransacao().equalsIgnoreCase("Débito")) {
			if(cartao.getSaldo() < t.getValor()) {
				throw new Exception("Não há saldo suficiente");
			}else {
				t.setSaldoHist(cartao.getSaldo() - t.getValor());
				Double novoSaldo = cartao.getSaldo() - t.getValor();
				cartao.setSaldo(novoSaldo);
			}
		}else {
			t.setSaldoHist(cartao.getSaldo() + t.getValor());
			Double novoSaldo2 = cartao.getSaldo() + t.getValor();
			cartao.setSaldo(novoSaldo2);
		}
		
		t.setCartaoCredito(cartao);
		
	}

	@Override
	protected void validarEdicao(Transacao t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validarExclusao(Transacao t) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Busca o cartão com base no numero e cvv digitados na pagina de transação para verificação
	 * @param numero
	 * @param cvv
	 * @return
	 */
	public CartaoCredito buscarPorCartao(String numero, String cvv) {
		Query query = em.createQuery("FROM CartaoCredito c WHERE c.numero = :p1 AND c.cvv = :p2", CartaoCredito.class);
		query.setParameter("p1", numero);
		query.setParameter("p2", cvv);
		try {
			return (CartaoCredito) query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Método usado para pesquisar transações de acordo com um numero de cartão digitado
	 * @param numero
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Transacao> buscarPorTransacao(String numero) {
		Query query = em.createQuery("FROM Transacao c WHERE c.cartaoCredito.numero = :p1", Transacao.class);
		query.setParameter("p1", numero);
		try {
			return (List<Transacao>) query.getResultList();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Método usado para buscar todas as transações com o nome do cliente digitado (Método usado para gerar os gráficos)
	 * @param nome
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Transacao> buscarCartaoPorCliente(String nome) {
		Query query = em.createQuery("FROM Transacao c WHERE c.cartaoCredito.cliente.nome = :p1", Transacao.class);
		query.setParameter("p1", nome);
		try {
			return (List<Transacao>) query.getResultList();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Método usado para buscar todas as transações com o nome do credor digitado (Método usado para gerar os gráficos)
	 * @param credor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Transacao> buscarCartaoPorCredor(String credor) {
		Query query = em.createQuery("FROM Transacao c WHERE c.credor = :p1", Transacao.class);
		query.setParameter("p1", credor);
		try {
			return (List<Transacao>) query.getResultList();
		}catch (NoResultException e) {
			return null;
		}
	}

}
