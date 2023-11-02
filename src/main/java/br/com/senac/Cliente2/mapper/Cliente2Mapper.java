package br.com.senac.Cliente2.mapper;

import br.com.senac.Cliente2.entitys.Cliente2;
import br.com.senac.Cliente2.model.Cliente2Response;

public class Cliente2Mapper {
    public static Cliente2Response cliente2ResponseMapper(Cliente2 cliente2) {
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
}
