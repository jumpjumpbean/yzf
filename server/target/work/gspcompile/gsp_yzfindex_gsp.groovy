import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzfindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("content-language"),'content':("zh-CN")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("X-UA-Compatible"),'content':("IE=edge,chrome=1")],-1)
printHtmlPart(2)
expressionOut.print(resource(dir: 'images', file: 'favicon.ico'))
printHtmlPart(3)
out.print(request.contextPath)
printHtmlPart(4)
out.print(request.contextPath)
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',43,[:],1)
printHtmlPart(6)
createTagBody(1, {->
printHtmlPart(7)
out.print(request.contextPath)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
out.print(request.contextPath)
printHtmlPart(10)
})
invokeTag('ifAnyGranted','sec',52,['roles':("ROLE_ADMIN,ROLE_SUPER")],2)
printHtmlPart(11)
out.print(request.contextPath)
printHtmlPart(12)
out.print(request.contextPath)
printHtmlPart(13)
createTagBody(2, {->
printHtmlPart(14)
out.print(request.contextPath)
printHtmlPart(15)
out.print(request.contextPath)
printHtmlPart(16)
out.print(request.contextPath)
printHtmlPart(17)
out.print(request.contextPath)
printHtmlPart(18)
out.print(request.contextPath)
printHtmlPart(19)
out.print(request.contextPath)
printHtmlPart(20)
})
invokeTag('ifAnyGranted','sec',82,['roles':("ROLE_ADMIN,ROLE_SUPER")],2)
printHtmlPart(21)
})
invokeTag('captureBody','sitemesh',84,['style':("background: #eee9ed url('./img/index_bg.jpg') center top;padding-top: 70px;")],1)
printHtmlPart(22)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1404615017567L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
