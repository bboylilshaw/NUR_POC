package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import watson.user.commons.ExpiredLevel;
import watson.user.commons.RequestStatus;
import watson.user.model.Request;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository("requestDao")
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
        if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setFinalResult(RequestStatus.DENIED);
        }
        sessionFactory.getCurrentSession().update(request);
        return requestID;
    }

    @Override
    public String proceedByCountryRep(String requestID, String countryRepEmail, String proceedAction, String comments) {
        Request request = getRequestByID(requestID);
        request.setCountryRepEmail(countryRepEmail);
        request.setCountryRepProceed(proceedAction);
        request.setCountryRepProceedDate(new Date());
        request.setCountryRepComments(comments);
        if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setFinalResult(RequestStatus.DENIED);
        }
        sessionFactory.getCurrentSession().update(request);
        return requestID;
    }

    @Override
    public String proceedByRegionalRep(String requestID, String regionalRepEmail, String proceedAction, String comments) {
        Request request = getRequestByID(requestID);
        request.setRegionalRepEmail(regionalRepEmail);
        request.setRegionalRepProceed(proceedAction);
        request.setRegionalRepProceedDate(new Date());
        request.setRegionalRepComments(comments);
        if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setFinalResult(RequestStatus.DENIED);
        } else if (proceedAction.equalsIgnoreCase(RequestStatus.APPROVED)) {
            request.setFinalResult(RequestStatus.APPROVED);
        }
        sessionFactory.getCurrentSession().update(request);
        return requestID;
    }

    @Override
    public boolean allowToSubmit(String domainUserName, String instance) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.instance=:instance and (r.finalResult=:wip or r.finalResult=:ap)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Request> results = query.setString("domainUserName", domainUserName)
                                     .setString("instance", instance)
                                     .setString("wip", RequestStatus.WIP)
                                     .setString("ap", RequestStatus.APPROVED)
                                     .list();
        //if no requests being found, then allow to submit
        return results.size() <= 0;
    }

    @Override
    public void setRequestExpired(String requestID, String expiredLevel) {
        Request request = getRequestByID(requestID);
        if (expiredLevel.equalsIgnoreCase(ExpiredLevel.MANAGER)) {
            request.setManagerProceed(RequestStatus.EXPIRED);
            request.setFinalResult(RequestStatus.EXPIRED);
            sessionFactory.getCurrentSession().update(request);
        } else if (expiredLevel.equalsIgnoreCase(ExpiredLevel.COUNTRY_REP)) {
            request.setCountryRepProceed(RequestStatus.EXPIRED);
            request.setFinalResult(RequestStatus.EXPIRED);
            sessionFactory.getCurrentSession().update(request);
        } else if (expiredLevel.equalsIgnoreCase(ExpiredLevel.REGIONAL_REP)) {
            request.setRegionalRepProceed(RequestStatus.EXPIRED);
            request.setFinalResult(RequestStatus.EXPIRED);
            sessionFactory.getCurrentSession().update(request);
        } else {
            throw new RuntimeException("cannot define expired in which level");
        }
    }

    @Override
    public List<Request> listMyOpenRequests(String domainUserName) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.finalResult=:wip";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("wip", RequestStatus.WIP)
                    .list();
    }

    @Override
    public List<Request> listRequestsPendingMyApproval(String domainUserName) {
        String hql = "from Request as r where (r.managerDomainUserName=:domainUserName and r.managerProceedAction=:in)"
                                       + " or (r.countryRepDomainUserName=:domainUserName and r.countryRepProceedAction=:in)"
                                       + " or (r.regionalRepDomainUserName=:domainUserName and r.regionalRepProceedAction=:in)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("in", RequestStatus.INITIAL)
                    .list();
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
