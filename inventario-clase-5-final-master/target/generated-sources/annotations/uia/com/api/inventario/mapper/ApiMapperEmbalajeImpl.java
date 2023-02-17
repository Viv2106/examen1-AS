package uia.com.api.inventario.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uia.com.api.inventario.dto.EmbalajeDTO;
import uia.com.api.inventario.model.Embalaje;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-16T19:33:56-0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ApiMapperEmbalajeImpl implements ApiMapperEmbalaje {

    @Override
    public EmbalajeDTO entityToDTO(Embalaje embalaje) {
        if ( embalaje == null ) {
            return null;
        }

        EmbalajeDTO embalajeDTO = new EmbalajeDTO();

        embalajeDTO.setId( embalaje.getId() );
        embalajeDTO.setName( embalaje.getName() );
        embalajeDTO.setClase( embalaje.getClase() );
        embalajeDTO.setNamePartida( embalaje.getNamePartida() );
        embalajeDTO.setNameSubpartida( embalaje.getNameSubpartida() );
        embalajeDTO.setNameCategoria( embalaje.getNameCategoria() );
        embalajeDTO.setIdPartida( embalaje.getIdPartida() );
        embalajeDTO.setIdSubpartida( embalaje.getIdSubpartida() );
        embalajeDTO.setIdCategoria( embalaje.getIdCategoria() );
        embalajeDTO.setEstatus( embalaje.getEstatus() );

        return embalajeDTO;
    }

    @Override
    public Embalaje DTOToEntity(EmbalajeDTO embalaje) {
        if ( embalaje == null ) {
            return null;
        }

        Embalaje embalaje1 = new Embalaje();

        embalaje1.setId( embalaje.getId() );
        embalaje1.setName( embalaje.getName() );
        embalaje1.setNamePartida( embalaje.getNamePartida() );
        embalaje1.setNameSubpartida( embalaje.getNameSubpartida() );
        embalaje1.setNameCategoria( embalaje.getNameCategoria() );
        embalaje1.setEstatus( embalaje.getEstatus() );
        embalaje1.setIdPartida( embalaje.getIdPartida() );
        embalaje1.setIdSubpartida( embalaje.getIdSubpartida() );
        embalaje1.setIdCategoria( embalaje.getIdCategoria() );
        embalaje1.setClase( embalaje.getClase() );

        return embalaje1;
    }

    @Override
    public List<Embalaje> DTOToEntities(List<EmbalajeDTO> embalajes) {
        if ( embalajes == null ) {
            return null;
        }

        List<Embalaje> list = new ArrayList<Embalaje>( embalajes.size() );
        for ( EmbalajeDTO embalajeDTO : embalajes ) {
            list.add( DTOToEntity( embalajeDTO ) );
        }

        return list;
    }
}
