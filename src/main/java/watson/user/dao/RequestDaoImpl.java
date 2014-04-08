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
    public Request proceededByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep) {
        // default does not skip country rep
        return this.proceededByManager(request, proceedAction, comments, countryRep, regionalRep, false);
    }

    @Override
    public Request proceededByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep, boolean skipCountryRep) {

        request.setManagerProceed(proceedAction);
        request.setManagerProceedDate(new Date());
        request.setManagerComments(comments);
        request.setFinalResult(RequestStatus.WIP);

        if (proceedAction.equalsIgnoreCase(RequestStatus.APPROVED)) {

            request.setCountryRepDomainUserName(countryRep.getDomainUserName());
            request.setCountryRepEmployeeId(countryRep.getEmployeeId());
            request.setCountryRepEmail(countryRep.getEmail());
            request.setCountryRepProceed(RequestStatus.INITIAL);

            if (skipCountryRep) {
                request.setCountryRepProceed(RequestStatus.SKIP);
                request.setCountryRepProceedDate(new Date());
                //initial Regional Rep notification
                request.setRegionalRepDomainUserName(regionalRep.getDomainUserName());
                request.setRegionalRepEmployeeId(regionalRep.getEmployeeId());
                request.setRegionalRepEmail(regionalRep.getEmail());
                request.setRegionalRepProceed(RequestStatus.INITIAL);
            }

        } else if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setFinalResult(RequestStatus.DENIED);
        }

        sessionFactory.getCurrentSession().update(request);
        return request;
    }

    @Override
    public Request proceededByCountryRep(Request request, String proceedAction, String comments, RegionalRep regionalRep) {

        request.setCountryRepProceed(proceedAction);
        request.setCountryRepProceedDate(new Date());
        request.setCountryRepComments(comments);
        request.setFinalResult(RequestStatus.WIP);

        if (proceedAction.equalsIgnoreCase(RequestStatus.APPROVED)) {
            request.setRegionalRepDomainUserName(regionalRep.getDomainUserName());
            request.setRegionalRepEmployeeId(regionalRep.getEmployeeId());
            request.setRegionalRepEmail(regionalRep.getEmail());
            request.setRegionalRepProceed(RequestStatus.INITIAL);

        } else if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setFinalResult(RequestStatus.DENIED);
        }

        sessionFactory.getCurrentSession().update(request);
        return request;
    }

    @Override
    public Request proceededByRegionalRep(Request request, String proceedAction, String comments) {

        request.setRegionalRepProceed(proceedAction);
        request.setRegionalRepProceedDate(new Date());
        request.setFinalResult(proceedAction);

        sessionFactory.getCurrentSession().update(request);
        return request;
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
    public List<Request> listOpenRequests(String domainUserName) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.finalResult in (:init, :wip)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("init", RequestStatus.INITIAL)
                    .setString("wip", RequestStatus.WIP)
                    .list();
    }

    @Override
    public List<Request> listRequestsAwaitingApproval(String domainUserName) {
        String sql = "SELECT * FROM REQUEST r WHERE r.FinalResult IN (:init, :wip)"
                    + "AND ((r.ManagerDomainUserName=:domainUserName AND r.ManagerProceed=:init)"
                    + "OR (r.CountryRepDomainUserName=:domainUserName AND r.CountryRepProceed=:init)"
                    + "OR (r.RegionalRepDomainUserName=:domainUserName AND r.RegionalRepProceed=:init))";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return query.setString("domainUserName", domainUserName)
                    .setString("init", RequestStatus.INITIAL)
                    .setString("wip", RequestStatus.WIP)
                    .list();
    }

    @Override
    public List<Request> listRequestsAwaitingManagerApproval(String managerDomainUserName) {
        String hql = "from Request as r where r.managerDomainUserName=:managerDomainUserName and r.managerProceed=:init";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("managerDomainUserName", managerDomainUserName)
                    .setString("init", RequestStatus.INITIAL)
                    .list();
    }

    @Override
    public List<Request> listRequestsAwaitingCountryRepApproval(String watsonInstance, String countryCode) {
        String sql = "SELECT * FROM REQUEST r WHERE r.CountryRepDomainUserName in"
                   + " (SELECT cr.DomainUserName FROM COUNTRY_REP cr WHERE cr.WatsonInstance=:watsonInstance AND cr.CountryCode=:countryCode AND cr.EffectiveStatus=:active) ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return query.setString("watsonInstance", watsonInstance)
                    .setString("countryCode", countryCode)
                    .setString("active", "A")
                    .list();
    }

    @Override
    public List<Request> listRequestsAwaitingRegionalRepApproval(String watsonInstance) {
        String sql = "SELECT * FROM REQUEST r WHERE r.RegionalRepDomainUserName in"
                + " (SELECT rr.DomainUserName FROM REGIONAL_REP rr WHERE rr.WatsonInstance=:watsonInstance AND rr.EffectiveStatus=:active) ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return query.setString("watsonInstance", watsonInstance)
                    .setString("active", "A")
                    .list();
    }

}
