package watson.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import watson.user.model.Request;
import watson.user.service.ManagerServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ManagerController {

    @Autowired private ManagerServiceImpl managerService;

    @RequestMapping(value = "/manager/review/request/{requestId}", method = RequestMethod.GET)
    public String reviewRequest(@PathVariable String requestId, ModelMap modelMap) {
        Request request = managerService.reviewRequest(requestId);
        if (request == null) {
            return "error";
        }
        modelMap.addAttribute("requestId", requestId);
        modelMap.addAttribute("domainUserName", request.getDomainUserName());
        modelMap.addAttribute("email", request.getEmail());
        modelMap.addAttribute("instance", request.getWatsonInstance());
        modelMap.addAttribute("comments", request.getComments());
        modelMap.addAttribute("date", request.getRequestDate());
        return "reviewRequest";
    }

    @RequestMapping(value = "/manager/proceed/{requestId}", method = RequestMethod.POST)
    public @ResponseBody String proceedRequest(@PathVariable String requestId, HttpServletRequest req){
        String comments = req.getParameter("comments");
        String proceedAction = req.getParameter("proceedAction");
        try {
            managerService.proceedRequest(requestId, proceedAction, comments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "done";
    }

}
