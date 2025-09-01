package br.com.banco.controller;

import br.com.banco.dto.TransferenciaDTO;
import br.com.banco.model.Conta;
import br.com.banco.service.ContaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) { this.contaService = contaService; }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model) {
        Conta conta = contaService.obterPorId(id);
        model.addAttribute("conta", conta);
        return "conta/painel";
    }

    @PostMapping("/{id}/creditar")
    public String creditar(@PathVariable Long id, @RequestParam("valor") BigDecimal valor) {
        contaService.creditarPessimista(id, valor);
        return "redirect:/conta/" + id + "/painel";
    }

    @PostMapping("/{id}/debitar")
    public String debitar(@PathVariable Long id, @RequestParam("valor") BigDecimal valor) {
        contaService.debitarPessimista(id, valor);
        return "redirect:/conta/" + id + "/painel";
    }

    @PostMapping("/{id}/transferir")
    public String transferir(@PathVariable Long id, TransferenciaDTO dto) {
        contaService.transferirPessimista(id, dto.getIdDestino(), dto.getValor());
        return "redirect:/conta/" + id + "/painel";
    }
}
