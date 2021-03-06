package watson.user.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    @Autowired private JavaMailSenderImpl javaMailSender;
    @Autowired private SimpleMailMessage simpleMailMessage;
    @Autowired
    @Qualifier("velocityEngine")
    private VelocityEngine velocityEngine;

    @Override
    @Async
    public void sendEmailWithTemplate(String to, String cc, String templateName, Map model) throws Exception{

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", model);
        helper.setFrom(simpleMailMessage.getFrom());
        helper.setSubject(simpleMailMessage.getSubject());
        helper.setTo(to);
        helper.setCc(cc);
        helper.setText(text);
        javaMailSender.send(mimeMessage);

    }

    @Resource
    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Resource
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    @Resource
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
