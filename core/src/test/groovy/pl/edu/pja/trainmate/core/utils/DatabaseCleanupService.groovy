package pl.edu.pja.trainmate.core.utils

import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.Table

@Service
@Transactional
class DatabaseCleanupService {

    def tables = []

    @Autowired
    EntityManager entityManager

    @EventListener(ContextRefreshedEvent)
    def fetchTablesNames() {
        def session = entityManager.unwrap(Session)
        def metamodel = session.getSessionFactory().getMetamodel()
        metamodel.entities.forEach {
            def table = it.getJavaType().getAnnotation(Table)
            if (table) {
                tables.add(table.name())
            }
        }
    }

    def cleanupDatabase() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tables.forEach { entityManager.createNativeQuery("DELETE FROM " + it).executeUpdate() }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
