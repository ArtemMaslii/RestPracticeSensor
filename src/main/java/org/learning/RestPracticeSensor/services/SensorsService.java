package org.learning.RestPracticeSensor.services;

import org.learning.RestPracticeSensor.models.Sensor;
import org.learning.RestPracticeSensor.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void register(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> getByName(String name) {
        return sensorsRepository.findByName(name);
    }
}
