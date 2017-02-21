package soundlink.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soundlink.dao.repositories.IntegrationRepository;
import soundlink.service.manager.IIntegrationManager;

@Service
public class IntegrationManager implements IIntegrationManager {

    @Autowired
    private IntegrationRepository integrationRepository;

    @Override
    public Integer getNextIntegrationNumber() {
        return integrationRepository.getNextIntegrationNumber();
    }
}
