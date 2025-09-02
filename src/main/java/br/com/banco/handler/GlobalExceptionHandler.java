package br.com.banco.handler;

import br.com.banco.exception.SaldoInsuficienteException;
import br.com.banco.exception.ContaNaoEncontradaException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public String handleSaldoInsuficiente(SaldoInsuficienteException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro"; // nome da página HTML que exibirá a mensagem
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public String handleContaNaoEncontrada(ContaNaoEncontradaException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }
}
