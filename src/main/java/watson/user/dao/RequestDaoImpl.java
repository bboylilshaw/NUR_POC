package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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
        request.setCountry(hpEmployee.getCountry());
        request.setEmployeeId(hpEmployee.getEmployeeId());
        request.setEmail(hpEmployee.getEmail());
        request.setComments(comments);
        request.setRequestDate(new Date());
        request.setManagerDomainUserName(hpEmployee.getManagerDomainUserName());
        request.setManagerEmployeeId(hpEmployee.getManagerEmployeeId());
        request.setManagerEmail(hpEmployee.getManagerEmail());
        request.setManagerProceed(RequestStatus.INITIAL);
        request.setStatus(RequestStatus.PENDING_MANAGER); //set request status as pending manager
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
        request.setStatus(RequestStatus.PENDING_COUNTRY_REP);// default set status as pending country rep's approval

        if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setStatus(RequestStatus.DENIED);
        } else {
            request.setCountryRepDomainUserName(countryRep.getDomainUserName());
            request.setCountryRepEmployeeId(countryRep.getEmployeeId());
            request.setCountryRepEmail(countryRep.getEmail());
            request.setCountryRepProceed(RequestStatus.INITIAL);

            if (skipCountryRep) {
                request.setCountryRepProceed(RequestStatus.SKIP_COUNTRY_REP);
                request.setCountryRepProceedDate(new Date());
                //initial Regional Rep notification
                request.setRegionalRepDomainUserName(regionalRep.getDomainUserName());
                request.setRegionalRepEmployeeId(regionalRep.getEmployeeId());
                request.setRegionalRepEmail(regionalRep.getEmail());
                request.setRegionalRepProceed(RequestStatus.INITIAL);
                request.setStatus(RequestStatus.PENDING_REGIONAL_REP);
            }
        }

        sessionFactory.getCurrentSession().update(request);
        return request;
    }

    @Override
    public Request proceededByCountryRep(Request request, String proceedAction, String comments, RegionalRep regionalRep) {

        request.setCountryRepProceed(proceedAction);
        request.setCountryRepProceedDate(new Date());
        request.setCountryRepComments(comments);
        request.setStatus(RequestStatus.PENDING_REGIONAL_REP);

        if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) {
            request.setStatus(RequestStatus.DENIED);
        } else {
            request.setRegionalRepDomainUserName(regionalRep.getDomainUserName());
            request.setRegionalRepEmployeeId(regionalRep.getEmployeeId());
            request.setRegionalRepEmail(regionalRep.getEmail());
            request.setRegionalRepProceed(RequestStatus.INITIAL);
        }

        sessionFactory.getCurrentSession().update(request);
        return request;
    }

    @Override
    public Request proceededByRegionalRep(Request request, String proceedAction, String comments) {

        request.setRegionalRepProceed(proceedAction);
        request.setRegionalRepProceedDate(new Date());
        request.setStatus(proceedAction);

        sessionFactory.getCurrentSession().update(request);
        return request;
    }

    @Override
    public boolean allowToSubmit(String domainUserName, String watsonInstance) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.watsonInstance=:watsonInstance and r.status not in (:denied, :exired)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Request> results = query.setString("domainUserName", domainUserName)
                                     .setString("watsonInstance", watsonInstance)
                                     .setString("denied", RequestStatus.DENIED)
                                     .setString("exired", RequestStatus.EXPIRED)
                                     .list();
        //if no requests being found, then allow to submit
        return results.size() <= 0;
    }

    @Override
    public List<Request> listOpenRequests(String domainUserName) {
        String hql = "from Request as r where r.domainUserName=:domainUserName and r.status in (:pm, :pcr, :prr)";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("pm", RequestStatus.PENDING_MANAGER)
                    .setString("pcr", RequestStatus.PENDING_COUNTRY_REP)
                    .setString("prr", RequestStatus.PENDING_REGIONAL_REP)
                    .list();
    }

    @Override
    public List<Request> listRequestsAwaitingApproval(String domainUserName) {
        String hql = "from Request as r where r.status in (:pm, :pcr, :prr)"
                    + "and ((r.managerDomainUserName=:domainUserName and r.managerProceed=:init)"
                    + "or (r.countryRepDomainUserName=:domainUserName and r.countryRepProceed=:init)"
                    + "or (r.regionalRepDomainUserName=:domainUserName and r.regionalRepProceed=:init))";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setString("domainUserName", domainUserName)
                    .setString("pm", RequestStatus.PENDING_MANAGER)
                    .setString("pcr", RequestStatus.PENDING_COUNTRY_REP)
                    .setString("prr", RequestStatus.PENDING_REGIONAL_REP)
                    .setString("init", RequestStatus.INITIAL)
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

    @Override
    public int getCurrentApprovalLevel(String requestId) {
        Request request = (Request) sessionFactory.getCurrentSession().get(Request.class, requestId);
        if (request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL))
            return 1;
        if (request.getCountryRepProceed().equalsIgnoreCase(RequestStatus.INITIAL))
            return 2;
        if (request.getRegionalRepProceed().equalsIgnoreCase(RequestStatus.INITIAL))
            return 3;
        return 0;
    }

}
