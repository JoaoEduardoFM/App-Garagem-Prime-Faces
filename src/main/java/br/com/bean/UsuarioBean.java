package br.com.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.springframework.web.client.RestTemplate;

import br.com.model.entity.Usuario;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarios;
    private Usuario usuario = new Usuario();
    private String filtroNome;

    private final String API_BASE = "http://localhost:8080/api/usuarios";

    @PostConstruct
    public void init() {
        buscarTodos();
    }

    public void buscarTodos() {
        RestTemplate restTemplate = new RestTemplate();
        Usuario[] resposta = restTemplate.getForObject(API_BASE + "/listar", Usuario[].class);
        this.usuarios = List.of(resposta);
    }

    public void buscarPorNome() {
        RestTemplate restTemplate = new RestTemplate();
        Usuario[] resposta = restTemplate.getForObject(API_BASE + "/buscar-nome/" + filtroNome, Usuario[].class);
        this.usuarios = List.of(resposta);
    }

    public void salvar() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(API_BASE + "/salvar", usuario, Usuario.class);
        usuario = new Usuario();
        buscarTodos();
    }

    public void deletar(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(API_BASE + "/deletar/" + id);
        buscarTodos();
    }

    // Getters e setters
    public List<Usuario> getUsuarios() { return usuarios; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getFiltroNome() { return filtroNome; }
    public void setFiltroNome(String filtroNome) { this.filtroNome = filtroNome; }
}
