package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.RegionalRepDaoImpl;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

@Service("countryRepService")
public class CountryRepServiceImpl implements ApproverService {

    @Autowired private RequestDaoImpl requestDao;
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
        if (!request.getCountryRepProceed().equalsIgnoreCase(RequestStatus.INITIAL))
            throw new Exception("the request has been proceeded!");
        return request;
    }

    @Transactional
    public void proceedRequest(String requestId, String proceedAction, String comments) throws Exception{
        Request request = this.reviewRequest(requestId);
        RegionalRep regionalRep = regionalRepDao.getRegionalRep(request.getWatsonInstance());
        if (regionalRep == null)
            throw new Exception("connot find a region rep!");
        requestDao.proceededByCountryRep(request, proceedAction, comments, regionalRep);

        //TODO send notifications
//        String toCountryRepEmail = "yao.xiao@hp.com";
//        String ccEmail = "yao.xiao@hp.com";
//        String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
//        HashMap<String, String> model = new HashMap<String, String>();
//        model.put("approver", "Approver");
//        model.put("url", "http://localhost:8080/NUR_POC/countryRep/review/request/" + requestId);
//        notificationService.sendEmailWithTemplate(toCountryRepEmail, ccEmail, templateName, model);

    }

}
