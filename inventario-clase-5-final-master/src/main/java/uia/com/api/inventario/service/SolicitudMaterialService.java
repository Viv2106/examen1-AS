package uia.com.api.inventario.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uia.com.api.inventario.dto.CategoriaDTO;
import uia.com.api.inventario.dto.SolicitudMaterialDTO;
import uia.com.api.inventario.model.*;
import uia.com.api.inventario.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class SolicitudMaterialService {
    SolicitudMaterialRepository repositorySolicitudMaterial;
    ItemRepository repositoryItem;
    SubpartidaRepository repositorySubpartida;
    PartidaRepository repositoryPartida;
    CategoriaRepository repositoryCategoria;
    ControlId idControl;
    ControlId idControlDTO;

    Validator validator;

    @Autowired
    public SolicitudMaterialService(SolicitudMaterialRepository repositorySolicitudMaterial,
                                    SubpartidaRepository repositorySubpartida,
                                    PartidaRepository repositoryPartida,
                                    ItemRepository repositoryItem,
                                    CategoriaRepository repositoryCategoria,
                                    Validator validator) {
        this.repositorySolicitudMaterial = repositorySolicitudMaterial;
        this.repositoryItem = repositoryItem;
        this.repositoryCategoria = repositoryCategoria;
        this.repositorySubpartida = repositorySubpartida;
        this.repositoryPartida = repositoryPartida;

        this.validator = validator;
    }

    public SolicitudMaterial save(SolicitudMaterialDTO solicitud) {
        return saveInformation(solicitud);
    }

    public SolicitudMaterial update(SolicitudMaterialDTO solicitud) {
        return saveInformation(solicitud);
    }

    private SolicitudMaterial saveInformation(SolicitudMaterialDTO solicitudInDTO) {
        SolicitudMaterialDTO solicitudes_salvados = new SolicitudMaterialDTO();
        // creacion de nueva solicitud de material
        ArrayList<Item> itemsApartados = new ArrayList<Item>();
        SolicitudMaterial solicitudMaterial = new SolicitudMaterial();
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
                solicitudMaterial.setItems(itemsApartados);
                solicitudMaterial.setId(solicitudInDTO.getId());
                solicitudMaterial.setName(solicitudInDTO.getName());
                solicitudMaterial.setEstatus("Apartada");
                solicitudMaterial.setClase("SSM");
                solicitudMaterial.setCantidad(String.valueOf(itemsApartados.size()));
                repositorySolicitudMaterial.save(solicitudMaterial);

            }
        }

        return solicitudMaterial;
    }


}
