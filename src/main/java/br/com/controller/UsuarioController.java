package br.com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.model.entity.Usuario;
import br.com.model.response.ResponseRest;
import br.com.repository.UsuarioRepository;
import br.com.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("usuario")
@Api(tags = { "Usuario" }, description = "Serviços relacionados aos usuários.")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService service;

    @PostMapping("cadastraUsuario")
    @ApiOperation(value = "Cadastra usuário.", notes = "Cadastra um usuário.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRest salvaRegistro(@RequestBody @Valid Usuario usuario) {
        ResponseRest response = new ResponseRest();
        try {       
            service.salvaRegistro(usuario, response);
            response.setMessage("Usuário cadastrado com sucesso.");
        } catch (Exception e) {
            response.setMessage("Erro ao cadastrar usuário: " + e.getMessage());
        }
        return response;
    }

    @PutMapping("atualizaUsuario/{id}")
    @ApiOperation(value = "Atualiza usuário.", notes = "Atualiza um usuário.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRest atualizaRegistro(@RequestBody @Valid Usuario usuario) {
        ResponseRest response = new ResponseRest();
        try {
            service.salvaRegistro(usuario, response);
            response.setMessage("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            response.setMessage("Erro ao atualizar usuário: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("deletaUsuario/{id}")
    @ApiOperation(value = "Deleta usuário.", notes = "Deleta um usuário.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRest deleta(@PathVariable Long id) {
        ResponseRest response = new ResponseRest();
        try {
            service.deleta(id, response);
            response.setMessage("Usuário deletado com sucesso.");
        } catch (Exception e) {
            response.setMessage("Erro ao deletar usuário: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("buscaPorID/{id}")
    @ApiOperation(value = "Busca por ID.", notes = "Busca um usuário pelo ID.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> buscaPorID(@PathVariable Long id) {
        return service.buscaPorID(id, null);
    }

    @GetMapping("buscaPorNome/{nome}")
    @ApiOperation(value = "Busca por Nome.", notes = "Busca um usuário pelo nome.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> buscaPorNome(@PathVariable String nome) {
        return service.buscaPorNome(nome, null);
    }

    @GetMapping("buscaPorLogin/{login}")
    @ApiOperation(value = "Busca por Login.", notes = "Busca um usuário pelo login.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> buscaPorLogin(@PathVariable String login) {
        return service.buscaPorLogin(login, null);
    }

    @GetMapping("buscaUsuarios")
    @ApiOperation(value = "Busca todos os usuários.", notes = "Lista todos os usuários cadastrados.")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> listaTodos() {
        return service.buscaUsuarios();
    }

    @GetMapping(value = "buscaUsuarios/page/{pagina}", produces = "application/json")
    @ApiOperation(value = "Busca usuários com paginação.", notes = "Lista usuários com paginação.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Usuario>> listaTodosPaginado(@PathVariable("pagina") int pagina) {
        return service.buscaUsuariosPage(1);
    }

    @GetMapping("buscaPorNome/page")
    @ApiOperation(value = "Busca por Nome com paginação.", notes = "Busca usuários pelo nome com paginação.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Usuario>> buscaPorNomePage(
            @RequestParam("nome") String nome, 
            @RequestParam("pagina") int pagina) {
        return service.buscaNomePage(nome, pagina);
    }

    @GetMapping("findByLoginAndSenha/{login}/{senha}")
    @ApiOperation(value = "Busca por login e senha.", notes = "Busca um usuário pelo login e senha.")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> buscaPorLoginAndSenha(@PathVariable String login, @PathVariable String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }
}
