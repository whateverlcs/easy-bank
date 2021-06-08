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
	 * Verifica se o n�mero e cvv existem no banco de dados e se os dois batem, tamb�m verifica, 
	 * caso for d�bito ele verifica se o valor da transa��o n�o ir� ultrapassar o saldo existente
	 */
	@Override
	protected void validarCadastramento(Transacao t) throws Exception {
		CartaoCredito cartao = buscarPorCartao(t.getCartaoCredito().getNumero(), t.getCartaoCredito().getCvv());
		
		if(cartao == null) {
			throw new Exception("Cart�o Inv�lido");
		}
		
		if(t.getTipoTransacao().equalsIgnoreCase("D�bito")) {
			if(cartao.getSaldo() < t.getValor()) {
				throw new Exception("N�o h� saldo suficiente");
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
	 * Busca o cart�o com base no numero e cvv digitados na pagina de transa��o para verifica��o
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
	 * M�todo usado para pesquisar transa��es de acordo com um numero de cart�o digitado
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
	 * M�todo usado para buscar todas as transa��es com o nome do cliente digitado (M�todo usado para gerar os gr�ficos)
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
	 * M�todo usado para buscar todas as transa��es com o nome do credor digitado (M�todo usado para gerar os gr�ficos)
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
