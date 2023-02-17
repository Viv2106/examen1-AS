package uia.com.api.inventario.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uia.com.api.inventario.dto.LoteDTO;
import uia.com.api.inventario.model.Lote;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-16T19:33:56-0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ApiMapperLoteImpl implements ApiMapperLote {

    @Override
    public LoteDTO entityToDTO(Lote lote) {
        if ( lote == null ) {
            return null;
        }

        LoteDTO loteDTO = new LoteDTO();

        loteDTO.setId( lote.getId() );
        loteDTO.setName( lote.getName() );
        loteDTO.setEstatus( lote.getEstatus() );
        loteDTO.setClase( lote.getClase() );
        loteDTO.setFechaIngreso( lote.getFechaIngreso() );
        loteDTO.setNameEmbalaje( lote.getNameEmbalaje() );

        return loteDTO;
    }

    @Override
    public Lote DTOToEntity(LoteDTO loteDTO) {
        if ( loteDTO == null ) {
            return null;
        }

        Lote lote = new Lote();

        lote.setId( loteDTO.getId() );
        lote.setName( loteDTO.getName() );
        lote.setEstatus( loteDTO.getEstatus() );
        lote.setClase( loteDTO.getClase() );
        lote.setFechaIngreso( loteDTO.getFechaIngreso() );
        lote.setNameEmbalaje( loteDTO.getNameEmbalaje() );

        return lote;
    }

    @Override
    public List<Lote> entityToDTO(Iterable<LoteDTO> lote) {
        if ( lote == null ) {
            return null;
        }

        List<Lote> list = new ArrayList<Lote>();
        for ( LoteDTO loteDTO : lote ) {
            list.add( DTOToEntity( loteDTO ) );
        }

        return list;
    }

    @Override
    public List<Lote> DTOToEntities(List<LoteDTO> lotes) {
        if ( lotes == null ) {
            return null;
        }

        List<Lote> list = new ArrayList<Lote>( lotes.size() );
        for ( LoteDTO loteDTO : lotes ) {
            list.add( DTOToEntity( loteDTO ) );
        }

        return list;
    }
}
