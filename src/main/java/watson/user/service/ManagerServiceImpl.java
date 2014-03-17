package watson.user.service;

import org.springframework.stereotype.Service;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService {

    private RequestDaoImpl requestDao;

    @Override
    public Request reviewRequest(String requestID) {
        return requestDao.getRequestByID(requestID);
    }

    @Override
    public void approveRequest(String requestID, String managerEmail, String comments) {
        Request request = reviewRequest(requestID);
        //if the request is still "IN" status, then proceed
        if (request.getManagerProceed().equalsIgnoreCase("IN")) {
            requestDao.proceedByManager(requestID, managerEmail, "AP", comments);
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
    public void denyRequest(String requestID, String managerEmail, String comments) {
        requestDao.proceedByManager(requestID, managerEmail, "DE", comments);
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
