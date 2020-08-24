package com.raynigon.unit_api.jpa.helpers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/basic-entity")
class BasicRestController {

    private BasicService service

    BasicRestController(BasicService service){
        this.service = service
    }

    @PostMapping
    def create(@RequestBody BasicEntity entity){
        service.create(entity)
    }
}
