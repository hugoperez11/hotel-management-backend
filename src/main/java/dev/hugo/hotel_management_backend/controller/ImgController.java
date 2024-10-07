package dev.hugo.hotel_management_backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImgController {

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        // Cargar la imagen desde la carpeta estática
        Resource resource = new ClassPathResource("static/img/" + imageName);
        
        // Verificar si el recurso existe
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Devolver la imagen con el tipo de contenido adecuado
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Cambia esto si necesitas otro tipo de imagen
                .body(resource);
    }
}


