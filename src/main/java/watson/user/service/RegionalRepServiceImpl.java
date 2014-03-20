package watson.user.service;

import watson.user.dao.RequestDaoImpl;
import watson.user.model.Request;

import javax.annotation.Resource;

public class RegionalRepServiceImpl implements RegionalRepService {

    private RequestDaoImpl requestDao;

    @Resource
    public void setRequestDao(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public Request reviewRequest(String requestID) {
        Request request = requestDao.getRequestByID(requestID);
        if (request.getRegionalRepProceed().equalsIgnoreCase("IN") && request.getCountryRepProceed().equalsIgnoreCase("AP") && request.getManagerProceed().equalsIgnoreCase("AP")) {
            return request;
        }
        return null;
    }

    @Override
    public void approveRequest(String requestID, String regionalRepEmail, String comments) {

    }

    @Override
    public void denyRequest(String requestID, String regionalRepEmail, String comments) {

    }
}
