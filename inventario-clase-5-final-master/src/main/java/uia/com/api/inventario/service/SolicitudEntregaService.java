package uia.com.api.inventario.service;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uia.com.api.inventario.dto.CategoriaDTO;
import uia.com.api.inventario.dto.SolicitudEntregaDTO;
import uia.com.api.inventario.dto.SolicitudMaterialDTO;
import uia.com.api.inventario.model.*;
import uia.com.api.inventario.repository.*;

import java.util.ArrayList;
@Service
public class SolicitudEntregaService {

    SolicitudEntregaRepository repositorySolicitudEntrega;
    ItemRepository repositoryItem;
    SubpartidaRepository repositorySubpartida;
    PartidaRepository repositoryPartida;
    CategoriaRepository repositoryCategoria;
    ControlId idControl;
    ControlId idControlDTO;

    Validator validator;

    @Autowired
    public SolicitudEntregaService(SolicitudEntregaRepository repositorySolicitudEntrega,
                                    SubpartidaRepository repositorySubpartida,
                                    PartidaRepository repositoryPartida,
                                    ItemRepository repositoryItem,
                                    CategoriaRepository repositoryCategoria,
                                    Validator validator) {
        this.repositorySolicitudEntrega = repositorySolicitudEntrega;
        this.repositoryItem = repositoryItem;
        this.repositoryCategoria = repositoryCategoria;
        this.repositorySubpartida = repositorySubpartida;
        this.repositoryPartida = repositoryPartida;

        this.validator = validator;
    }

    public SolicitudEntrega save(SolicitudEntregaDTO solicitud) {
        return saveInformation(solicitud);
    }

    public SolicitudEntrega update(SolicitudEntregaDTO solicitud) {
        return saveInformation(solicitud);
    }

    private SolicitudEntrega saveInformation(SolicitudEntregaDTO solicitudInDTO) {
        SolicitudEntregaDTO solicitudes_salvados = new SolicitudEntregaDTO();
        // creacion de nueva solicitud de material
        ArrayList<Item> itemsApartados = new ArrayList<Item>();
        SolicitudEntrega solicitudEntrega = new SolicitudEntrega();
        int itemDisponibles = 0;
        int categoriasApartados = 0;


        if (solicitudInDTO.getCategorias().size() > 0) {
            for (int i = 0; i < solicitudInDTO.getCategorias().size(); i++) {
                CategoriaDTO categoriaDTO = solicitudInDTO.getCategorias().get(i);
                String idCategoria = categoriaDTO.getIdCategoria();
                String idPartida = String.valueOf((Integer.parseInt(idCategoria) / 100) * 100);
                String idSubpartida = String.valueOf(Integer.parseInt(idPartida) + ((Integer.parseInt(idCategoria) % 100) / 10) * 10);
                itemDisponibles = 0;

                if (this.repositoryPartida.existsByIdPartida(idPartida) &&
                        this.repositorySubpartida.existsByIdSubpartida(idSubpartida) &&
                        this.repositoryCategoria.existsByIdCategoria(idCategoria)
                ) {

                    //-- Se toma el 0 como el unico que debe haber
                    Categoria entityCategoriaBD = this.repositoryCategoria.findByIdCategoria(idCategoria).get(0);

                    for (int k = 0; k < entityCategoriaBD.getItems().size(); k++) {
                        if (entityCategoriaBD.getItems().get(k).getEstatus().contentEquals("Disponible"))
                            ++itemDisponibles;
                    }

                    if (itemDisponibles >= categoriaDTO.getCantidad()) {
                        for (int k = 0; k < entityCategoriaBD.getItems().size(); k++) {
                            if (entityCategoriaBD.getItems().get(k).getEstatus().contentEquals("Disponible")) {
                                entityCategoriaBD.getItems().get(k).setEstatus("Apartado");
                                itemsApartados.add(entityCategoriaBD.getItems().get(k));
                            }
                        }
                        ++categoriasApartados;
                    }
                }
            }

            if (solicitudInDTO.getCategorias().size() == categoriasApartados) {
                for (int j = 0; j < itemsApartados.size(); j++) {
                    this.repositoryItem.save(itemsApartados.get(j));
                }
                solicitudEntrega.setItems(itemsApartados);
                solicitudEntrega.setId(solicitudInDTO.getId());
                solicitudEntrega.setName(solicitudInDTO.getName());
                solicitudEntrega.setEstatus("Apartada");
                solicitudEntrega.setClase("SSM");
                solicitudEntrega.setCantidad(String.valueOf(itemsApartados.size()));
                repositorySolicitudEntrega.save(solicitudEntrega);

            }
        }

        return solicitudEntrega;
    }


}
