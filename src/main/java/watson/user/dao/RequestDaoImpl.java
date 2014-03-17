package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import watson.user.model.Request;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class RequestDaoImpl implements RequestDao {

    private final Logger logger = Logger.getLogger(RequestDaoImpl.class);
    private SessionFactory sessionFactory;

    @Override
    public Request getRequestByID(String requestID) {
        return (Request) sessionFactory.getCurrentSession().get(Request.class, requestID);
    }

    @Override
    public String proceedByManager(String requestID, String managerEmail, String proceedAction, String comments) {
        Request request = getRequestByID(requestID);
        request.setManagerEmail(managerEmail);
        request.setManagerProceed(proceedAction);
        request.setManagerProceedDate(new Date());
        request.setManagerComments(comments);
        sessionFactory.getCurrentSession().update(request);
        return requestID;
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
