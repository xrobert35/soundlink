package soundlink.dao.repositories;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class IntegrationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Allow you to get next integration number
     *
     * @param name
     * @return
     */
    public Integer getNextIntegrationNumber() {
        Query q = entityManager.createNativeQuery("select nextval('integration_number_seq')");
        return ((BigInteger) q.getSingleResult()).intValue();
    }
}
