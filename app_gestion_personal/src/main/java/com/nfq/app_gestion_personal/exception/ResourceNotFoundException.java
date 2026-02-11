package com.nfq.app_gestion_personal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada.
 * La lanzaremos cuando busquemos algo en BBDD y no exista.
 * @ResponseStatus(HttpStatus.NOT_FOUND): Indica el código HTTP será 404.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}