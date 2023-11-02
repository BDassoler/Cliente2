package br.com.senac.Cliente2.controller;

import br.com.senac.Cliente2.entitys.Cliente2;
import br.com.senac.Cliente2.entitys.Endereco;
import br.com.senac.Cliente2.model.EnderecoRequest;
import br.com.senac.Cliente2.model.EnderecoResponse;
import br.com.senac.Cliente2.repositorys.Cliente2Repository;
import br.com.senac.Cliente2.repositorys.EnderecoCacheRepository;
import br.com.senac.Cliente2.repositorys.EnderecoRepository;
import br.com.senac.Cliente2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    Cliente2Repository cliente2Repository;
    @Autowired
    private EnderecoCacheRepository enderecoCacheRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private EmailService emailService;
    @GetMapping(path = "/carregar")
    public ResponseEntity<List<EnderecoResponse>> carregarEndereco() {
        List<Endereco> enderecoList = enderecoCacheRepository.carregarTodosEnderecos();
        List<EnderecoResponse> out = new ArrayList<>();
        for (Endereco endereco : enderecoList) {
            EnderecoResponse enderecoResponse = this.enderecoResponseMapper(endereco);
            out.add(enderecoResponse);
        }
        return ResponseEntity.ok().body(out);
    }


    @PostMapping(path = "/criar")
    public ResponseEntity<EnderecoResponse> criarEndereco
            (@RequestBody EnderecoRequest enderecoRequest){
        Cliente2 cliente2 = cliente2Repository.findById(enderecoRequest.getCliente2Id()).get();
        Endereco endereco = this.enderecoMapper(enderecoRequest, cliente2);
        Endereco retorno = enderecoRepository.save(endereco);
        cacheManager.getCache("enderecos").clear();
        emailService.enviarEmailHtml("Endereco criado com sucesso, <strong> id:" + retorno.getId()+ "</strong>",
                "bruna.senac.back@gmail.com", "criação de endereco");

        return ResponseEntity.ok(this.enderecoResponseMapper(retorno));
    }
    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity<EnderecoResponse>
    atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoRequest enderecoRequest){
        Cliente2 cliente2 = cliente2Repository.findById(enderecoRequest.getCliente2Id()).get();
        Endereco endereco = enderecoRepository.findById(id).map(record ->{
            record.setRua(enderecoRequest.getRua());
            record.setBairro(enderecoRequest.getBairro());
            record.setCidade(enderecoRequest.getCidade());
            record.setEstado(enderecoRequest.getEstado());
            record.setNomeResponsavel(enderecoRequest.getNomeResponsavel());
            record.setCliente2(cliente2);
            return enderecoRepository.save(record);
        }).get();
        EnderecoResponse out = this.enderecoResponseMapper(endereco);
        return ResponseEntity.ok(out);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id){
        enderecoRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
    public EnderecoResponse enderecoResponseMapper(Endereco endereco){
        EnderecoResponse out = new EnderecoResponse();
        out.setId(endereco.getId());
        out.setNomeResponsavel(endereco.getNomeResponsavel());
        out.setRua(endereco.getRua());
        out.setBairro(endereco.getBairro());
        out.setCidade(endereco.getCidade());
        out.setEstado(endereco.getEstado());
        out.setCliente2Id(endereco.getCliente2().getId());
        return out;

    }
    public Endereco enderecoMapper
            (EnderecoRequest enderecoRequest, Cliente2 cliente2) {
        Endereco out = new Endereco();
        out.setId(enderecoRequest.getId());
        out.setRua(enderecoRequest.getRua());
        out.setBairro(enderecoRequest.getBairro());
        out.setCidade(enderecoRequest.getCidade());
        out.setEstado(enderecoRequest.getEstado());
        out.setNomeResponsavel(enderecoRequest.getNomeResponsavel());
        out.setCliente2(cliente2);
        return out;
    }
}
