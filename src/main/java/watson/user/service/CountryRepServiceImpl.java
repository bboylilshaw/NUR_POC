package watson.user.service;

import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

import javax.annotation.Resource;

public class CountryRepServiceImpl implements CountryRepService {

    private RequestDaoImpl requestDao;

    @Resource
    public void setRequestDao(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public Request reviewRequest(String requestID) {
        Request request = requestDao.getRequestByID(requestID);
        if (request.getCountryRepProceed().equalsIgnoreCase("IN") && request.getManagerProceed().equalsIgnoreCase("AP")) {
            return request;
        }
        return null;
    }

    @Override
    public void approveRequest(String requestID, String countryRepEmail, String comments) {
        Request request = reviewRequest(requestID);
        //if the request is still "IN" status and already approved by manager, then proceed
        if (request.getCountryRepProceed().equalsIgnoreCase("IN") && request.getManagerProceed().equalsIgnoreCase("AP")) {
            requestDao.proceedByCountryRep(requestID, countryRepEmail, "AP", comments);
            //TODO pass to reginalRep and send notifications
            //passToRegionalRep()
        }

    }

    @Override
    public void denyRequest(String requestID, String countryRepEmail, String comments) {
        Request request = reviewRequest(requestID);
        if (request.getCountryRepProceed().equalsIgnoreCase("IN") && request.getManagerProceed().equalsIgnoreCase("AP")) {
            requestDao.proceedByCountryRep(requestID, countryRepEmail, "DE", comments);
        }

    }

    @Override
    public void passToRegionalRep(String requestID) {

    }

}
