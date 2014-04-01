package watson.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import watson.user.service.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String userHome(HttpServletRequest req, ModelMap modelMap){
        modelMap.addAttribute("domainUserName", req.getSession().getAttribute("domainUserName"));
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest req, ModelMap modelMap) {
        String domainUserName = req.getParameter("domainUserName");
        req.getSession().setAttribute("domainUserName", domainUserName);
        modelMap.addAttribute("domainUserName", domainUserName);
        modelMap.addAttribute("openAccessRequests", userService.listOpenAccessRequests(domainUserName));
        modelMap.addAttribute("accessRequestsAwaitingApproval", userService.listAccessRequestsAwaitingApproval(domainUserName));
        return "home";
    }

    @RequestMapping(value = "/access/request", method = RequestMethod.GET)
    public String requestAccess() {
        return "requestAccess";
    }

    @RequestMapping(value = "/access/request", method = RequestMethod.POST)
    public String doRequestAccess(HttpServletRequest req, ModelMap modelMap) {
        String domainUserName = (String) req.getSession().getAttribute("domainUserName");
        String instance = req.getParameter("instance");
        String comments = req.getParameter("comments");

        try {
            userService.requestAccess(domainUserName, instance, comments);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:/home";
    }

    @Resource
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

}
