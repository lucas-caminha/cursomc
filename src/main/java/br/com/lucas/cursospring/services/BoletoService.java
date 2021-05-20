package br.com.lucas.cursospring.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamento(PagamentoComBoleto pagto, Date instate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instate);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
