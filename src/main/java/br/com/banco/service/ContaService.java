package br.com.banco.service;

import br.com.banco.exception.ContaNaoEncontradaException;
import br.com.banco.exception.SaldoInsuficienteException;
import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Transactional
    public Conta criarConta(Conta conta) {
        conta.setSaldo(conta.getSaldo() == null ? BigDecimal.ZERO : conta.getSaldo());
        return contaRepository.save(conta);
    }

    @Transactional
    public List<Conta> getTodasContas() {
        return contaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Conta obterPorId(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + id));
    }

    @Transactional
    public Conta creditarPessimista(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.buscarComLockPessimista(idConta).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + idConta));
        conta.setSaldo(conta.getSaldo().add(valor));
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta debitarPessimista(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.buscarComLockPessimista(idConta).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + idConta));
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para débito");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    @Transactional
    public void transferirPessimista(Long idOrigem, Long idDestino, BigDecimal valor) {
        if (idOrigem.equals(idDestino)) throw new IllegalArgumentException("Conta de origem e destino iguais");

        Long primeiro = Math.min(idOrigem, idDestino);
        Long segundo = Math.max(idOrigem, idDestino);

        Conta primeiraConta = contaRepository.buscarComLockPessimista(primeiro).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + primeiro));
        Conta segundaConta = contaRepository.buscarComLockPessimista(segundo).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + segundo));

        Conta origem = primeiraConta.getId().equals(idOrigem) ? primeiraConta : segundaConta;
        Conta destino = origem == primeiraConta ? segundaConta : primeiraConta;

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência");
        }

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    @Transactional
    public Conta debitarOtimista(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + idConta));
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para débito");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }
}
