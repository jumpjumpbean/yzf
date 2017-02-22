/**
 * Created by root on 2014/7/17.
 */
import weixin.popular.api.MenuAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.Token;
public class WeixinMenu {
    private static long token_time = 0l;
    private static Token access_token = null;
    private static String appid = "wx81bef331e52398c6";
    private static String secret = "560de116eafa4a9c85de650a65c2b6d0";
    /**
     * 官方文档介绍：Token在7200秒后会过期，因此，
     * 我们在用前调用此函数先check一下最近取的token是否过期
     * @return
     */
    static void checkAppToken(){
        long curSec = System.currentTimeMillis() / 1000;
        if(curSec - token_time > 6000){
            TokenAPI tk = new TokenAPI();
            access_token = tk.token(appid, secret);
            token_time = curSec;
        }
    }


    /**
     * 创建MENU
     */
    static void createMenu(){
//MenuButtons mb = new MenuButtons();
//	 Button btn = new Button();
//        String menuJson="{\"button\":["
//                + "{\"type\":\"view\",\"name\":\"我的装修\",\"sub_button\":["
//                    + "{\"type\":\"click\",\"name\":\"我的客服\",\"key\":\"V101_TODAY\"},"
//                    + "{\"type\":\"click\",\"name\":\"装修进度\",\"key\":\"V104_PUS\"}"
//                + "]},"
//                + "{\"name\":\"装修课题\",\"sub_button\":["
//                    + "{\"type\":\"click\",\"name\":\"建材选择\",\"key\":\"V201_ACH\"},"
//                    + "{\"type\":\"click\",\"name\":\"近期活动\",\"key\":\"V202_S\"}]},"
//                + "{\"name\":\"更多\",\"sub_button\":["
//                    + "{\"type\":\"view\",\"name\":\"预约\",\"url\":\"http://115.28.222.209/weixin/bespeak\"},"
//                    + "{\"type\":\"view\",\"name\":\"关于我们\",\"url\":\"http://115.28.222.209/weixin/about\"}]}"
//                + "]}";
        try{
            String menuJson="{\"button\":["
                    + "{\"type\":\"view\",\"name\":\"我的装修\",\"sub_button\":["
                        + "{\"type\":\"view\",\"name\":\"我的客服\",\"url\":\"http://115.28.222.209/weixin/my_contact\"},"
                        + "{\"type\":\"view\",\"name\":\"问题反馈\",\"url\":\"http://115.28.222.209/weixin/my_feedback\"},"
                        + "{\"type\":\"view\",\"name\":\"装修进度\",\"url\":\"http://115.28.222.209/weixin/my_process\"}"
                    + "]},"
                    + "{\"name\":\"装修课题\",\"sub_button\":["
                        + "{\"type\":\"view\",\"name\":\"装修风格选择\",\"url\":\"http://115.28.222.209/weixin/article1\"},"
                        + "{\"type\":\"view\",\"name\":\"如何环保装修\",\"url\":\"http://115.28.222.209/weixin/article2\"}]},"
                    + "{\"name\":\"更多\",\"sub_button\":["
                        + "{\"type\":\"click\",\"name\":\"关于业之峰\",\"key\":\"V301_ABOUT\"}]}"
                    + "]}";
            MenuAPI ma = new MenuAPI();
            checkAppToken();
            BaseResult br = ma.menuCreate(access_token.getAccess_token(), menuJson);
            System.out.println(br.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
//ma.menuCreate(access_token.getAccess_token(), mb);
    }
    public static void main(String[] args) throws Exception{
        createMenu();
    }
}
