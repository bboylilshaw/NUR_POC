package watson.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import watson.user.model.HPEmployee;
import watson.user.service.LDAPServiceImpl;
import watson.user.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired private UserServiceImpl userService;
    @Autowired private LDAPServiceImpl ldapService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest req) {
        if (req.getSession().getAttribute("hpEmployee") != null) {
            return "forward:/home";
        }
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String userHome(HttpServletRequest req, ModelMap modelMap){
        HPEmployee hpEmployee = (HPEmployee) req.getSession().getAttribute("hpEmployee");
        if (hpEmployee == null) {
            return "redirect:/";
        }
        String domainUserName = hpEmployee.getDomainUserName();
        modelMap.addAttribute("domainUserName", domainUserName);
        modelMap.addAttribute("openRequests", userService.listOpenRequests(domainUserName));
        modelMap.addAttribute("requestsAwaitingApproval", userService.listRequestsAwaitingApproval(domainUserName));
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (ldapService.authenticateUser(email, password)) {
            HPEmployee hpEmployee = ldapService.getEmployeeDataByEmail(email);
            req.getSession().setAttribute("hpEmployee", hpEmployee);
            return "redirect:/home";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(HttpServletRequest req) {
        req.getSession().setAttribute("hpEmployee", null);
        return "redirect:/";
    }

    @RequestMapping(value = "/access/request", method = RequestMethod.GET)
    public String requestAccess() {
        return "requestAccess";
    }

    @RequestMapping(value = "/access/request", method = RequestMethod.POST)
    public String doRequestAccess(HttpServletRequest req, ModelMap modelMap) {
        HPEmployee hpEmployee = (HPEmployee) req.getSession().getAttribute("hpEmployee");
        String watsonInstance = req.getParameter("watsonInstance");
        String comments = req.getParameter("comments");

        try {
            userService.submitRequest(hpEmployee, watsonInstance, comments);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.addAttribute("errMsg", e.getMessage());
            return "error";
        }
        return "redirect:/home";
    }

}
