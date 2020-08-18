package com.github.raynigon.unit_api_starter.jackson.helpers

import org.springframework.stereotype.Service

@Service
class BasicService {

    private Map<String, BasicEntity> data = new HashMap<>()

    def create(BasicEntity entity){
        data.put(entity.id, entity)
    }

    def getEntity(String id) {
        return data.get(id)
    }
}
