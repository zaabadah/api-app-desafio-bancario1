package br.com.banco.controller;

import br.com.banco.model.Conta;
import br.com.banco.service.ContaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ContaService contaService;

    public AdminController(ContaService contaService) { this.contaService = contaService; }

    @GetMapping("/contas")
    public String listarContas(Model model) {
        model.addAttribute("contas", contaService.getTodasContas());
        return "admin/lista-contas";
    }

    @GetMapping("/contas/criar")
    public String criarContaForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "admin/criar-conta";
    }

    @PostMapping("/contas/criar")
    public String criarConta(Conta conta) {
        contaService.criarConta(conta);
        return "redirect:/admin/contas";
    }
}
