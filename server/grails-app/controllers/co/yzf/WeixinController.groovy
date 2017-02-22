package co.yzf
import grails.converters.JSON
import org.springframework.util.StreamUtils
import weixin.popular.bean.EventMessage
import weixin.popular.bean.xmlmessage.XMLTextMessage
import weixin.popular.bean.xmlmessage.XMLNewsMessage
import weixin.popular.bean.xmlmessage.XMLNewsMessage.Article
import weixin.popular.util.SignatureUtil
import weixin.popular.util.XMLConverUtil

import javax.servlet.ServletInputStream
import javax.servlet.ServletOutputStream
import java.nio.charset.Charset

class WeixinController {
    def passwordEncoder
    def springSecurityService
    private String token = "yzfwx";
    private String imgPath = "http://115.28.222.209/img/";
    def index(){
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //首次请求申请验证,返回echostr
        if(echostr != null){
            if(signature.equals(SignatureUtil.generateEventMessageSignature(token,timestamp,nonce))){
//				System.out.println("Yeah, it's passed");
                outputStreamWrite(outputStream,echostr);
                return null;
            }else{
//				System.out.println("Looks like something wrong at somewhere!");
                outputStreamWrite(outputStream,"");
                return null;
            }
        }else {
            String xml = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
            xml = CommonUtil.trimStr(xml)
            if(xml == null){
                outputStreamWrite(outputStream,"nothing received!");
                return null;
            }
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,xml);
            //创建回复
            String msgType = CommonUtil.trimStr(eventMessage.getMsgType());
            if(msgType == null){
                XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),"暂时还不能理解你的请求！");
                xmlTextMessage.outputStreamWrite(outputStream);
            }else {
                if ("text".equals(msgType)) {
                    String content = CommonUtil.trimStr(eventMessage.getContent());
                    if("yzf".equals(content)){
                        XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),"Hi，欢迎加入德州业之峰");
                        xmlTextMessage.outputStreamWrite(outputStream);
                    }
                }else if("image".equals(msgType)){
                }else if("voice".equals(msgType)){
                }else if("video".equals(msgType)){
                }else if("location".equals(msgType)){
                }else if("link".equals(msgType)){
                }else if("event".equals(msgType)){
                    String event = CommonUtil.trimStr(eventMessage.getEvent());
                    if (event != null){
                        if ("subscribe".equals(event)){
                            XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),"欢迎加入德州业之峰");
                            xmlTextMessage.outputStreamWrite(outputStream);
                        }else if("unsubscribe".equals(event)){
                        }else if("SCAN".equals(event)){
                        }else if("LOCATION".equals(event)){
                        }else if("CLICK".equals(event)){
                            String eventKey = CommonUtil.trimStr(eventMessage.getEventKey());
                            if ("V301_ABOUT".equals(eventKey)){
                                List<Article> article = new ArrayList<Article>();
                                Article a1 = new Article();
                                a1.setPicurl(imgPath + "corp3.png");
                                a1.setTitle("德州业之峰装饰");
                                article.add(a1);
                                Article a2 = new Article();
                                a2.setTitle("预约咨询电话：0534-2609222 15066598535");
                                article.add(a2);
                                Article a3 = new Article();
                                a3.setTitle("QQ:2936386065");
                                article.add(a3);
                                Article a4 = new Article();
                                a4.setTitle("微信公众平台:德州业之峰");
                                article.add(a4);
                                Article a5 = new Article();
                                a5.setTitle("欢迎新老客户来电咨询装修方案、预约设计师查看成功案例及装修样板房");
                                article.add(a5);
                                XMLNewsMessage xmlNewsMessage = new XMLNewsMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),article);
                                xmlNewsMessage.outputStreamWrite(outputStream);
                                response.flushBuffer();
                                return null;
                            }else{
                                XMLTextMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),"抱歉，本功能还未上线，请稍候");
                                xmlTextMessage.outputStreamWrite(outputStream);
                            }
                        }else if("VIEW".equals(event)){
                        }
                    }
                }
            }
            response.flushBuffer();
            return null;
        }
    }


    private boolean outputStreamWrite(OutputStream outputStream,String text){
        try {
            outputStream.write(text.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /************************************Action************************************/
    def wxlogin(){
        if(params.cb != null){
            session["s_redirect"] = "my_"+ params.cb
        }
    }
    def dologin(){
        if(params.account == null || params.password == null){
            flash.message = "用户名或密码为空"
            render(view:"wxlogin")
            return
        }
        def user= User.findByAccount(params.account)
        if(user == null){
            flash.message = "用户不存在"
            render(view:"wxlogin")
            return
        }
        if(user.password == passwordEncoder.encodePassword(params.password,params.account)){
            def token = session.id
            session["s_login_user"] = user
            if(session["s_redirect"] != null){
                redirect uri:"/weixin/"+session["s_redirect"]
                session["s_redirect"] = null
                return
            }else{
                flash.message="登陆成功，请返回微信主页，使用相关功能"
                render(view:"wxlogin")
                return
            }
        }
    }
    def my_contact(){
        def user = session["s_login_user"]
        if(user == null){
            redirect uri: "/weixin/wxlogin?cb=contact"
            return
        }
        def project = co.yzf.Project.findByUser(user)
        if(project == null){
            flash.message = "暂时没有查询到你的相关装修信息"
        }else{
            if(project.status == 1){
                flash.message = "你的项目还没有开始"
            }else if(project.status == 2){
                def process = co.yzf.Process.findByProjectAndStatus(project,2)
                if(process != null){
                    render (view: "my_contact",model: [qc:project.qc,supervisor:process.charger])
                }
            }else{
                flash.message = "你的项目已经结束"
            }
        }
    }

    def my_feedback(){
        def user = session["s_login_user"]
        if(user == null){
            redirect uri: "/weixin/wxlogin?cb=feedback"
            return
        }
    }

    def my_process(){
        def user = session["s_login_user"]
        if(user == null){
            redirect uri: "/weixin/wxlogin?cb=process"
            return
        }
        def project = co.yzf.Project.findByUser(user)
        if(project == null){
            flash.message = "暂时没有查询到你的相关装修信息"
        }else{
            if(project.status == 1){
                flash.message = "你的项目还没有开始"
            }else if(project.status == 2){
                def process = co.yzf.Process.findByProjectAndStatus(project,2)
                if(process != null){
                    render (view: "my_process",model: [project:project,process:process,ps:co.yzf.ProcessStep.findByProcessAndStatus(process,2),qc:project.qc,supervisor:process.charger])
                }
            }else{
                flash.message = "你的项目已经结束"
            }
        }
    }

    def my_wxsave(){
        def user = session["s_login_user"]
        if(user == null){
            render "error"
            return
        }
        co.yzf.Feedback feedbackInstance = new Feedback()
        feedbackInstance.title = params.title
        def content = params.content
        def sum = content
        if(sum != null && sum.length()>100){
            sum = sum.substring(0,100);
        }
        feedbackInstance.content = content
        feedbackInstance.summary = sum
        feedbackInstance.user = user
        def project = Project.findByUser(user)
        if(project != null){
            feedbackInstance.project = project
        }
        if(!feedbackInstance.save(flush: true)){
            render "error"
            return
        }
        render "ok"
    }

    def article1(){
    }
    def article2(){
    }
}
