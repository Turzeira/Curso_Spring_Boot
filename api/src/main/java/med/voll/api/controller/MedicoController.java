package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));

    }
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
        // esta convertendo os dadosMedico para o dadosListagem e colocando em uma lista!
        // return repository.findAll().map(DadosListagemMedico::new).toList();

        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        // parametro no Insonima ?size = 1, mostra so 1 registro.
        // page = 1 qual pagina eu quero carregar
        // sort =  parametro, para organizar de forma crescente
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    //EXCLUSAO LOGICA
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

    }



}