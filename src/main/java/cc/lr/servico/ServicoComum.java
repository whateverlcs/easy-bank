package cc.lr.servico;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Operações comuns dos serviços
 * @author lukin
 *
 */
public abstract class ServicoComum<T> {
	
	@PersistenceContext
	protected EntityManager em;
	
	protected Class<T> tipoGenericoT;
	
	public ServicoComum() {
		
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ParameterizedType tiposGenericos = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] tipos = tiposGenericos.getActualTypeArguments();
		this.tipoGenericoT = (Class<T>) tipos[0];
	}
	
	public T cadastrar(T t) throws Exception{
		validarCadastramento(t);
		em.persist(t);
		return t;
	}
	
	public void excluir(T t) throws Exception {
		validarExclusao(t);
		em.remove(em.merge(t));
	}
	
	public T editar(T t) throws Exception {
		validarEdicao(t);
		return em.merge(t);
	}
	
	public List<T> listar(){
		String selecionaTudo = "SELECT t FROM {0} t";
		String JPAQueryString = MessageFormat.format(selecionaTudo, tipoGenericoT.getSimpleName());
		return em.createQuery(JPAQueryString, tipoGenericoT).getResultList();
	}
	
	protected abstract void validarCadastramento(T t) throws Exception;
	protected abstract void validarEdicao(T t) throws Exception;
	protected abstract void validarExclusao(T t) throws Exception;
	
}
