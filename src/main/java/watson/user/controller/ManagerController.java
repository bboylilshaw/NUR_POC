package watson.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import watson.user.model.Request;
import watson.user.service.ManagerServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ManagerController {

    private ManagerServiceImpl managerService;

    @RequestMapping(value = "/manager/review/request/{requestId}", method = RequestMethod.GET)
    public String reviewRequest(@PathVariable String requestId, ModelMap modelMap) {
        Request request = managerService.reviewRequest(requestId);
        if (request == null) {
            return "error";
        }
        modelMap.addAttribute("requestId", requestId);
        modelMap.addAttribute("domainUserName", request.getDomainUserName());
        modelMap.addAttribute("email", request.getEmployeeEmail());
        modelMap.addAttribute("instance", request.getInstance());
        modelMap.addAttribute("comments", request.getComments());
        modelMap.addAttribute("date", request.getRequestDate());
        return "reviewRequest";
    }

    @RequestMapping(value = "/manager/proceed/{requestId}", method = RequestMethod.POST)
    public @ResponseBody String proceedRequest(@PathVariable String requestId, HttpServletRequest req){
        String managerEmail = "xiaoyao8823@gmail.com";
        String comments = req.getParameter("comments");
        if (req.getParameter("proceedAction").equalsIgnoreCase("AP")) {
            managerService.approveRequest(requestId, managerEmail, comments);
        } else if (req.getParameter("proceedAction").equalsIgnoreCase("DE")){
            managerService.denyRequest(requestId, managerEmail, comments);
        }
        return "done";
    }

    @Resource
    public void setManagerService(ManagerServiceImpl managerService) {
        this.managerService = managerService;
    }
}
