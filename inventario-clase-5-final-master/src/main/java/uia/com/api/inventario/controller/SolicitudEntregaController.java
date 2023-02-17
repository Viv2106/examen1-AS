package uia.com.api.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uia.com.api.inventario.dto.SolicitudEntregaDTO;
import uia.com.api.inventario.model.SolicitudEntrega;
import uia.com.api.inventario.service.SolicitudEntregaService;

@RestController
@RequestMapping("SolicitudEntrega")

public class SolicitudEntregaController {
    private SolicitudEntregaService solicitudEntregaService;

    @Autowired
    public void SolicitudEntregaService(SolicitudEntregaService solicitudEntregaService) {
        this.solicitudEntregaService = solicitudEntregaService;
    }


    @PostMapping
    public ResponseEntity<SolicitudEntrega> save(@RequestBody SolicitudEntregaDTO solicitudEntregaDTO)
    {
        SolicitudEntrega response = solicitudEntregaService.save(solicitudEntregaDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
