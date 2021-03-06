package com.goldze.base.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterActivityPath {
    /**
     * 主业务组件
     */
    public static class Main {
        private static final String MAIN = "/main";
        /*主业务界面*/
        public static final String PAGER_MAIN = MAIN + "/Main";
    }

    /**
     * 身份验证组件
     */
    public static class Sign {
        private static final String SIGN = "/sign";
        /*登录界面*/
        public static final String PAGER_LOGIN = SIGN + "/Login";
    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*用户详情*/
        public static final String PAGER_USERDETAIL = USER + "/UserDetail";
        public static final String PAGER_USERDISTURB = USER + "/UserDisturb";
    }

    /**
     * 用户组件
     */
    public static class Home {
        private static final String HOME = "/home";
        //物业服务
        public static final String PAGER_WUYE = HOME + "/WuYe";
        //社区服务
        public static final String PAGER_SHEQU = HOME + "/SheQu";
        //物业缴费
        public static final String PAGER_WUYE_PAY = HOME + "/WuYePay";

        public static final String PAGER_DEVICE_LIST = HOME + "/DeviceList";

        public static final String PAGER_KEY_SHARE = HOME + "/KeyShare";

        public static final String SUGGEST_ADD = HOME + "/suggest_add";
    }


    public static class Rtc {
        private static final String RTC = "/rtc";
        /*用户搜索*/
        public static final String PAGER_RTC = RTC + "/sample";
    }

}
