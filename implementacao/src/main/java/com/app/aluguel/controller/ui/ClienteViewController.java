package com.app.aluguel.controller.ui;

import com.app.aluguel.model.Cliente;
import com.app.aluguel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteViewController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "clientes/list";
    }
    
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }
    
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente.get());
        return "clientes/view";
    }
    
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente.get());
        return "clientes/form";
    }
    
    @PostMapping
    public String salvar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            clienteService.salvar(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            cliente.setId(id);
            clienteService.salvar(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deletar(id);
            redirectAttributes.addFlashAttribute("success", "Cliente deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
}
