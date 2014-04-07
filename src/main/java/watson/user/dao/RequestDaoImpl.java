package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import watson.user.commons.ExpiredLevel;
import watson.user.commons.RequestStatus;
import watson.user.model.CountryRep;
import watson.user.model.HPEmployee;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.Date;
import java.util.List;

@Repository("requestDao")
public class RequestDaoImpl implements RequestDao {

    private final Logger logger = Logger.getLogger(RequestDaoImpl.class);
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Request getRequestById(String requestId) {
        return (Request) sessionFactory.getCurrentSession().get(Request.class, requestId);
    }

    @Override
    public String submitRequest(HPEmployee hpEmployee, String watsonInstance, String comments) {
        Request request = new Request();
        request.setWatsonInstance(watsonInstance);
        request.setDomainUserName(hpEmployee.getDomainUserName());
        request.setEmployeeId(hpEmployee.getEmployeeId());
        request.setEmail(hpEmployee.getEmail());
        request.setComments(comments);
        request.setRequestDate(new Date());
        request.setManagerDomainUserName(hpEmployee.getManagerDomainUserName());
        request.setManagerEmployeeId(hpEmployee.getManagerEmployeeId());
        request.setManagerEmail(hpEmployee.getManagerEmail());
        request.setManagerProceed(RequestStatus.INITIAL);
        sessionFactory.getCurrentSession().save(request);
        return request.getRequestId();
    }

    @Override
    public String approvedByManager(String requestId, String comments) {
        return this.approvedByManager(requestId, comments, false);
    }

    @Override
    public String approvedByManager(String requestId, String comments, boolean skipCountryRep) {
        Request request = this.getRequestById(requestId);
        request.setManagerProceed(RequestStatus.APPROVED);
        request.setManagerProceedDate(new Date());
        request.setManagerComments(comments);
        request.setCountryRepProceed(RequestStatus.INITIAL);
        request.setFinalResult(RequestStatus.WIP);
        if (skipCountryRep) {
            request.setCountryRepProceed(RequestStatus.SKIP);
            request.setCountryRepProceedDate(new Date());
            request.setRegionalRepProceed(RequestStatus.INITIAL);
        }
        sessionFactory.getCurrentSession().update(request);
        return requestId;
    }

    @Override
    public String deniedByManager(String requestId, String comments) {
        Request request = this.getRequestById(requestId);
        request.setManagerProceed(RequestStatus.DENIED);
        request.setManagerProceedDate(new Date());
        request.setManagerComments(comments);
        request.setFinalResult(RequestStatus.DENIED);
        sessionFactory.getCurrentSession().update(request);
        return requestId;
    }

    @Override
    public String approvedByCountryRep(String requestId, CountryRep countryRep, String comments) {
        Request request = this.getRequestById(requestId);
        request.setCountryRepDomainUserName(countryRep.getDomainUserName());
        request.setCountryRepEmployeeId(countryRep.getEmployeeId());
        request.setCountryRepEmail(countryRep.getEmail());
        request.setCountryRepProceed(RequestStatus.APPROVED);
        request.setCountryRepProceedDate(new Date());
        request.setCountryRepComments(comments);
        request.setRegionalRepProceed(RequestStatus.INITIAL);
        request.setFinalResult(RequestStatus.WIP);
        sessionFactory.getCurrentSession().update(request);
        return requestId;
    }

    @Override
    public String deniedByCountryRep(String requestId, CountryRep countryRep, String comments) {
        Request request = this.getRequestById(requestId);
        request.setCountryRepDomainUserName(countryRep.getDomainUserName());
        request.setCountryRepEmployeeId(countryRep.getEmployeeId());
        request.setCountryRepEmail(countryRep.getEmail());
        request.setCountryRepProceed(RequestStatus.DENIED);
        request.setCountryRepProceedDate(new Date());
        request.setCountryRepComments(comments);
        request.setFinalResult(RequestStatus.DENIED);
        sessionFactory.getCurrentSession().update(request);
        return requestId;
    }

    @Override
    public String approvedByRegionalRep(String requestId, RegionalRep regionalRep, String comments) {
        return null;
    }

    @Override
    public String deniedByRegionalRep(String requestId, RegionalRep regionalRep, String comments) {
        return null;
    }

    @Override
    public boolean allowToSubmit(String domainUserName, String watsonInstance) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.watsonInstance=:watsonInstance and r.finalResult in (:init, :wip, :ap)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Request> results = query.setString("domainUserName", domainUserName)
                                     .setString("watsonInstance", watsonInstance)
                                     .setString("init", RequestStatus.INITIAL)
                                     .setString("wip", RequestStatus.WIP)
                                     .setString("ap", RequestStatus.APPROVED)
                                     .list();
        //if no requests being found, then allow to submit
        return results.size() <= 0;
    }

    @Override
    public void setRequestExpired(String requestID, String expiredLevel) {
        Request request = getRequestById(requestID);
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
    public List<Request> listOpenAccessRequests(String domainUserName) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.finalResult in (:init, :wip)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("init", RequestStatus.INITIAL)
                    .setString("wip", RequestStatus.WIP)
                    .list();
    }

    @Override
    public List<Request> listAccessRequestsAwaitingApproval(String domainUserName) {
        String hql = "from Request as r where (r.managerDomainUserName=:domainUserName and r.managerProceed=:in)"
                                       + " or (r.countryRepDomainUserName=:domainUserName and r.countryRepProceed=:in)"
                                       + " or (r.regionalRepDomainUserName=:domainUserName and r.regionalRepProceed=:in)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("in", RequestStatus.INITIAL)
                    .list();
    }

}
