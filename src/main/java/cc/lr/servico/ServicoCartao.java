package cc.lr.servico;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;

import cc.lr.entidade.CartaoCredito;

@Stateless
public class ServicoCartao extends ServicoComum<CartaoCredito> {

	@Override
	protected void validarCadastramento(CartaoCredito t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validarEdicao(CartaoCredito t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validarExclusao(CartaoCredito t) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public CartaoCredito cadastrar(CartaoCredito t) throws Exception {
		String gerarNumero = geradorCartao();
		String gerarCVV = gerarCVV();
		Date dataValidade = gerarDataVencimento();
		t.setNumero(gerarNumero);
		t.setCvv(gerarCVV);
		t.setDataValidade(dataValidade);
		return super.cadastrar(t);
	}
	
	/**
	 * M�todo que gera os n�meros de cart�o
	 * @return
	 */
	public String geradorCartao() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append(gerarNumero());
			if(i < 3) {
				sb.append("-");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Gera a sequ�ncia aleat�ria do n�mero de cart�o (de acordo com a regra)
	 * @return
	 */
	public String gerarNumero() {
		return new DecimalFormat("0000").format((int) (Math.random() * 9999));
	}
	
	/**
	 * Gera a sequ�ncia aleat�rio do cvv
	 * @return
	 */
	public String gerarCVV() {
		return new DecimalFormat("000").format((int) (Math.random() * 999));
	}
	
	/**
	 * Gera a data de validade do cart�o a partir da data de cadastro
	 * @return
	 */
	public Date gerarDataVencimento() {
		GregorianCalendar vencimento = new GregorianCalendar();
		vencimento.add(Calendar.YEAR, 5);
		return vencimento.getTime();
	}
	

}
