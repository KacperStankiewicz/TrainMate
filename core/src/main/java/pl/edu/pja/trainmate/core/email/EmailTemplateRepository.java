package pl.edu.pja.trainmate.core.email;

import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;

@Repository
public interface EmailTemplateRepository extends BaseRepository<EmailTemplateEntity> {

    EmailTemplateEntity getEmailTemplateEntityByType(TemplateType type);
}
