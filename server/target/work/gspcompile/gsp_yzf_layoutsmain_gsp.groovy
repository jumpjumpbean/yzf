import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_layoutsmain_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/main.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()

String contextPath = request.contextPath;

printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',7,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',8,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("content-language"),'content':("zh-CN")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',9,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("X-UA-Compatible"),'content':("IE=edge,chrome=1")],-1)
printHtmlPart(3)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('layoutTitle','g',14,['default':("业之峰")],-1)
})
invokeTag('captureTitle','sitemesh',14,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',14,[:],2)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',15,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("viewport"),'content':("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")],-1)
printHtmlPart(4)
expressionOut.print(resource(dir: 'images', file: 'favicon.ico'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'images', file: 'apple-touch-icon.png'))
printHtmlPart(6)
expressionOut.print(resource(dir: 'images', file: 'apple-touch-icon-retina.png'))
printHtmlPart(7)
expressionOut.print(contextPath)
printHtmlPart(8)
expressionOut.print(resource(dir: 'css', file: 'yzf.css'))
printHtmlPart(9)
expressionOut.print(resource(dir: 'css', file: 'mobile.css'))
printHtmlPart(10)
expressionOut.print(contextPath)
printHtmlPart(11)
invokeTag('layoutHead','g',23,[:],-1)
printHtmlPart(1)
invokeTag('layoutResources','r',24,[:],-1)
printHtmlPart(12)
})
invokeTag('captureHead','sitemesh',25,[:],1)
printHtmlPart(12)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',27,['template':("/templates/hdmemu")],-1)
printHtmlPart(2)
invokeTag('layoutResources','r',28,[:],-1)
printHtmlPart(1)
invokeTag('layoutBody','g',29,[:],-1)
printHtmlPart(13)
expressionOut.print(contextPath)
printHtmlPart(14)
expressionOut.print(contextPath)
printHtmlPart(15)
expressionOut.print(contextPath)
printHtmlPart(16)
expressionOut.print(contextPath)
printHtmlPart(17)
})
invokeTag('captureBody','sitemesh',39,[:],1)
printHtmlPart(18)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402816527466L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
