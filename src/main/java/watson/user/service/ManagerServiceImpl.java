package watson.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService {

    private RequestDaoImpl requestDao;

    @Override
    @Transactional(readOnly = true)
    public Request reviewRequest(String requestID) {
        Request request = requestDao.getRequestByID(requestID);
        if (request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            return request;
        }
        return null;
    }

    @Override
    @Transactional
    public void approveRequest(String requestID, String managerEmail, String comments) {
        Request request = reviewRequest(requestID);
        //if the request is still "IN" status, then proceed
        if (request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            requestDao.proceedByManager(requestID, managerEmail, RequestStatus.APPROVED, comments);
            // if requester is in same domain, then pass to country rep, if not, skip country rep and pass to regional rep
            if (request.getDomainUserName().startsWith("AISAPACIFIC") && request.getInstance().equalsIgnoreCase("apwatson")
                    || request.getDomainUserName().startsWith("EMEA") && request.getInstance().equalsIgnoreCase("eubwatson")
                    || request.getDomainUserName().startsWith("EMEA") && request.getInstance().equalsIgnoreCase("euwatson")
                    || request.getDomainUserName().startsWith("AMERICAS") && request.getInstance().equalsIgnoreCase("nawatson")
                    || request.getDomainUserName().startsWith("AMERICAS") && request.getInstance().equalsIgnoreCase("lawatson")) {
                //TODO pass to country rep
                //passToCountryRep();
                //send notifications
            } else {//skip country rep, direct pass to regional rep
                //TODO pass to regional rep
                //passToRegionalRep();
                //send notifications
            }
        } else {// already proceed the request before, throw an error
            throw new RuntimeException("already proceed the request");
        }
    }

    @Override
    @Transactional
    public void denyRequest(String requestID, String managerEmail, String comments) {
        Request request = reviewRequest(requestID);
        //if the request is still "IN" status, then proceed
        if (request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            requestDao.proceedByManager(requestID, managerEmail, RequestStatus.DENIED, comments);
        }
    }

    @Override
    public void passToCountryRep(String requestID) {

    }

    @Override
    public void passToRegionalRep(String requestID) {

    }

    @Resource
    public void setRequestDao(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

}
