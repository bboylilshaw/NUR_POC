package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.commons.RequestStatus;
import watson.user.dao.RegionalRepDaoImpl;
import watson.user.dao.RequestDaoImpl;
import watson.user.model.CountryRep;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.HashMap;

@Service("countryRepService")
public class CountryRepServiceImpl implements CountryRepService {

    @Autowired private RequestDaoImpl requestDao;
    @Autowired private RegionalRepDaoImpl regionalRepDao;
    @Autowired private NotificationServiceImpl notificationService;

    @Override
    @Transactional(readOnly = true)
    public Request reviewRequest(String requestId) {
        Request request = requestDao.getRequestById(requestId);
        if (request != null && request.getCountryRepProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            return request;
        }
        return null;
    }

    @Override
    @Transactional
    public void proceedRequest(String requestId, CountryRep countryRep, String proceedAction, String comments) throws Exception {
        Request request = this.reviewRequest(requestId);
        // this countryRepProceed is not "IN", which means this request has been proceeded
        if (!request.getManagerProceed().equalsIgnoreCase(RequestStatus.INITIAL)) {
            throw new Exception("This request has been proceeded!");
        }

        if (proceedAction.equalsIgnoreCase(RequestStatus.APPROVED)) { //CountryRep approved the request
            //pass to regional rep by setting regionalRepProceed as "IN"
            requestDao.approvedByCountryRep(requestId, countryRep, comments);
            RegionalRep regionalRep = this.getRegionalRep(request.getWatsonInstance());

            //TODO send notifications
            String toRegionalRepEmail = "yao.xiao@hp.com";
            String ccEmail = "yao.xiao@hp.com";
            String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
            HashMap<String, String> model = new HashMap<String, String>();
            model.put("approver", "Approver");
            model.put("url", "http://localhost:8080/NUR_POC/regionalRep/review/request/" + requestId);
            notificationService.sendEmailWithTemplate(toRegionalRepEmail, ccEmail, templateName, model);
        } else if (proceedAction.equalsIgnoreCase(RequestStatus.DENIED)) { //Manager denied the request
            requestDao.deniedByCountryRep(requestId, countryRep, comments);
        }
    }

    @Override
    public RegionalRep getRegionalRep(String watsonInstance) {
        return regionalRepDao.getRegionalRep(watsonInstance);
    }

}
