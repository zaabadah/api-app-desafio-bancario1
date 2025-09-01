package br.com.banco;

import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import br.com.banco.service.ContaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    public void deveCreditarValor() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(new BigDecimal("100.00"));

        when(contaRepository.buscarComLockPessimista(1L)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Conta resultado = contaService.creditarPessimista(1L, new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), resultado.getSaldo());
    }
}
