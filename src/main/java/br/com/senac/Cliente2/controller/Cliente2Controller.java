package br.com.senac.Cliente2.controller;

import br.com.senac.Cliente2.entitys.Cliente2;
import br.com.senac.Cliente2.entitys.Endereco;
import br.com.senac.Cliente2.mapper.Cliente2Mapper;
import br.com.senac.Cliente2.model.*;
import br.com.senac.Cliente2.repositorys.Cliente2CacheRepository;
import br.com.senac.Cliente2.repositorys.Cliente2Repository;
import br.com.senac.Cliente2.repositorys.EnderecoRepository;
import br.com.senac.Cliente2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cliente2")

public class Cliente2Controller {
    @Autowired
    private Cliente2Repository cliente2Repository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private Cliente2CacheRepository cliente2CacheRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private EmailService emailService;

    @GetMapping(path = "/carregar")
    public ResponseEntity<List<Cliente2Response>> carregarCliente2() {
        List<Cliente2> cliente2List = cliente2CacheRepository.carregarTodosClientes();
        List<Cliente2Response> out = new ArrayList<>();
        for (Cliente2 cliente2 : cliente2List) {
            Cliente2Response cliente2Response = this.cliente2ResponseMapper(cliente2);
            out.add(cliente2Response);
        }
        return ResponseEntity.ok().body(out);

    }


    @GetMapping(path = "/carregar/{id}")
    public ResponseEntity<Cliente2Response> carregarById(@PathVariable Long id) {
        Cliente2 cliente2 = cliente2Repository.findById(id).get();

        List<Endereco> enderecoList = enderecoRepository.carregarEnderecosByCliente2Id(id);
        List<EnderecoResponse> enderecoResponseList = new ArrayList<>();
        for (Endereco endereco : enderecoList) {
            enderecoResponseList.add(this.enderecoResponseMapper(endereco));

        }

        Cliente2Response out = this.cliente2ResponseMapper(cliente2);
        {
            out.setEnderecos(enderecoResponseList);

            return ResponseEntity.ok(out);
        }
    }

    @GetMapping(path = "/carregar_paginado")
    public ResponseEntity<DataResponse> carregarPedidos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Cliente2> cliente2List = cliente2Repository.findAll(paging);
        List<Cliente2Response> cliente2ResponseList = cliente2List.getContent().stream().map(Cliente2Mapper :: cliente2ResponseMapper).collect(Collectors.toList());

        InfoRow infoRow = new InfoRow();
        //numero da pagina atual
        infoRow.setPage(cliente2List.getNumber() + 1);
        //total de paginas a serem listadas
        infoRow.setPageCount(cliente2List.getTotalPages());
        //total de elementos
        infoRow.setTotalElements(cliente2List.getTotalElements());

        DataResponse out = new DataResponse();
        out.setRows(cliente2ResponseList);
        out.setInfoRows(infoRow);

        return ResponseEntity.ok(out);
    }

    @PostMapping(path = "/criar")
    public ResponseEntity<Cliente2Response> criarCliente2
            (@RequestBody Cliente2Request cliente2Request) {
        Cliente2 cliente2 = new Cliente2();
        cliente2.setId(cliente2Request.getId());
        cliente2.setNome(cliente2Request.getNome());
        cliente2.setSobreNome(cliente2Request.getSobreNome());
        cliente2.setDocumento(cliente2Request.getDocumento());
        cliente2.setEmail(cliente2Request.getEmail());
        cliente2.setTelefone(cliente2Request.getTelefone());
        cliente2.setDataNascimento(cliente2Request.getDataNascimento());

        Cliente2 result = cliente2Repository.save(cliente2);
        cacheManager.getCache("clientes").clear();

        emailService.criarFilaEmail("Cliente criado com sucesso, <strong> id:"+ result.getId()+ "</strong>",
        "bruna.senac.back@gmail.com", "criação de cliente");

        return ResponseEntity.ok().body(this.cliente2ResponseMapper(result));
    }

    @PutMapping(path = "/atualizar/{id}")
    public ResponseEntity<Cliente2Response>
    atualizarCliente2(@PathVariable Long id, @RequestBody Cliente2Request cliente2Request){
        Cliente2 cliente2 = cliente2Repository.findById(id).map(record -> {

            record.setNome(cliente2Request.getNome());
            record.setSobreNome(cliente2Request.getSobreNome());
            record.setDataNascimento(cliente2Request.getDataNascimento());
            record.setDocumento(cliente2Request.getDocumento());
            record.setEmail(cliente2Request.getEmail());
            record.setTelefone(cliente2Request.getTelefone());

            return cliente2Repository.save(record);

        }).get();

        Cliente2Response out = this.cliente2ResponseMapper(cliente2);
        return ResponseEntity.ok().body(out);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<Void> deletarCliente2(@PathVariable Long id){
        cliente2Repository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

        private Cliente2Response cliente2ResponseMapper(Cliente2 cliente2) {
        Cliente2Response out = new Cliente2Response();
        out.setId(cliente2.getId());
        out.setNome(cliente2.getNome());
        out.setSobreNome(cliente2.getSobreNome());
        out.setDocumento(cliente2.getDocumento());
        out.setEmail(cliente2.getEmail());
        out.setTelefone(cliente2.getTelefone());
        out.setDataNascimento(cliente2.getDataNascimento());

        return out;
    }

    public EnderecoResponse enderecoResponseMapper(Endereco endereco) {
        EnderecoResponse out = new EnderecoResponse();
        out.setId(endereco.getId());
        out.setRua(endereco.getRua());
        out.setBairro(endereco.getBairro());
        out.setCidade(endereco.getCidade());
        out.setEstado(endereco.getEstado());
        out.setNomeResponsavel(endereco.getNomeResponsavel());

        return out;

    }
    }
