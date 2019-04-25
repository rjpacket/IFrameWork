package com.rjp.commonlib;

public class ServiceFactory {
    private static final ServiceFactory ourInstance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return ourInstance;
    }

    private ServiceFactory() {
    }

    private ILoginService loginService;

    public ILoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }
}
