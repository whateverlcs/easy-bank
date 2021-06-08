package cc.lr.servico;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import cc.lr.entidade.CartaoCredito;
import cc.lr.entidade.Cliente;

@Stateless
public class ServicoCliente extends ServicoComum<Cliente> {
	
	/**
	 * Verifica se existe o cpf digitado no banco de dados.
	 */
	@Override
	protected void validarCadastramento(Cliente t) throws Exception {
		Cliente clienteBanco = buscarPorCpf(t.getCpf());
		if(clienteBanco != null) {
			throw new Exception("Já existe esse cpf!");
		}
	}

	@Override
	protected void validarEdicao(Cliente t) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Verifica se existe clientes com cartões cadastrados, caso haja ele não deixa excluir
	 */
	@Override
	protected void validarExclusao(Cliente t) throws Exception {
		List<CartaoCredito> cartao = buscarPorCartao(t.getId());
		if(!cartao.isEmpty()) {
			throw new Exception("Não se pode excluir um cliente com cartão de crédito cadastrado!");
		}
	}
	
	/**
	 * Busca no banco um CPF cadastrado.
	 * @param cpf
	 * @return
	 */
	public Cliente buscarPorCpf(String cpf) {
		Query query = em.createQuery("FROM Cliente c WHERE c.cpf = :p1").setParameter("p1", cpf);
		try {
			return (Cliente) query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Busca o id do cliente na tabela cartão de crédito para comparar com o cliente cadastrado
	 * @param Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CartaoCredito> buscarPorCartao(Long Id) {
		Query query = em.createQuery("FROM CartaoCredito c WHERE c.cliente.id = :p1").setParameter("p1", Id);
		try {
			return (List<CartaoCredito>) query.getResultList();
		}catch (NoResultException e) {
			return null;
		}
	}

}
