package watson.user.service;

import java.util.Map;

public interface NotificationService {
    public void sendEmailWithTemplate(String to, String cc, String templateName, Map model) throws Exception;
}
