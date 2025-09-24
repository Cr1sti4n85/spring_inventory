package inventory.system.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inventory.system.dto.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class CheckController {

    @GetMapping
    public ResponseEntity<Response> getStatus() {
        return ResponseEntity
                .ok(
                        Response.builder()
                                .status(200)
                                .message("Ok")
                                .build());
    }

}
