package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.CountryRepDaoImpl;
import watson.user.dao.RegionalRepDaoImpl;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.CountryRep;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

@Service("managerService")
public class ManagerServiceImpl implements ApproverService {

    @Autowired private RequestDaoImpl requestDao;
    @Autowired private CountryRepDaoImpl countryRepDao;
    @Autowired private RegionalRepDaoImpl regionalRepDao;
    @Autowired private NotificationServiceImpl notificationService;

    @Override
    @Transactional(readOnly = true)
    public Request reviewRequest(String requestId) throws Exception{
        Request request = requestDao.getRequestById(requestId);
        if (request == null)
            throw new Exception("there is no request been found!");
        if (request.getStatus().equalsIgnoreCase(RequestStatus.EXPIRED))
            throw new Exception("request has expired!");
        if (request.getStatus().equalsIgnoreCase(RequestStatus.DENIED))
            throw new Exception("request has been denied!");
        if (!request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL))
            throw new Exception("the request has been proceeded!");
        return request;
    }

    @Transactional
    public void proceedRequest(String requestId, String proceedAction, String comments) throws Exception{
        Request request = this.reviewRequest(requestId);
        CountryRep countryRep = countryRepDao.getCountryRep(request.getWatsonInstance(), request.getCountry());
        if (countryRep == null)
            throw new Exception("cannot find a country rep!");
        RegionalRep regionalRep = regionalRepDao.getRegionalRep(request.getWatsonInstance());
        if (regionalRep == null)
            throw new Exception("cannot find a region rep!");

        requestDao.updatedByManager(request, proceedAction, comments, countryRep, regionalRep, skipCountryRep(request));

        //TODO send notifications
//        String toCountryRepEmail = "yao.xiao@hp.com";
//        String ccEmail = "yao.xiao@hp.com";
//        String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
//        HashMap<String, String> model = new HashMap<String, String>();
//        model.put("approver", "Approver");
//        model.put("url", "http://localhost:8080/NUR_POC/countryRep/review/request/" + requestId);
//        notificationService.sendEmailWithTemplate(toCountryRepEmail, ccEmail, templateName, model);

    }

    private boolean skipCountryRep(Request request) {
        String requestWatsonInstance = request.getWatsonInstance();
        if (request.getDomainUserName().toUpperCase().startsWith("ASIAPACIFIC") && requestWatsonInstance.equalsIgnoreCase("apwatson"))
            return false;
        if (request.getDomainUserName().toUpperCase().startsWith("EMEA") && (requestWatsonInstance.equalsIgnoreCase("euwatson") || requestWatsonInstance.equalsIgnoreCase("eubwatson")))
            return false;
        if (request.getDomainUserName().toUpperCase().startsWith("AMERICAS") && (requestWatsonInstance.equalsIgnoreCase("nawatson") || requestWatsonInstance.equalsIgnoreCase("lawatson")))
            return false;
        return true;
    }

}
