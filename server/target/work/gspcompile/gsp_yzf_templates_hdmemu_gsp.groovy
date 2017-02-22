import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_templates_hdmemu_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/templates/_hdmemu.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
out.print(request.getContextPath())
printHtmlPart(1)
out.print(request.getContextPath())
printHtmlPart(2)
createTagBody(1, {->
printHtmlPart(3)
out.print(request.getContextPath())
printHtmlPart(4)
})
invokeTag('ifAnyGranted','sec',19,['roles':("ROLE_ADMIN,ROLE_SUPER")],1)
printHtmlPart(5)
out.print(request.getContextPath())
printHtmlPart(6)
out.print(request.getContextPath())
printHtmlPart(7)
createTagBody(1, {->
printHtmlPart(8)
out.print(request.getContextPath())
printHtmlPart(9)
out.print(request.getContextPath())
printHtmlPart(10)
out.print(request.getContextPath())
printHtmlPart(11)
out.print(request.getContextPath())
printHtmlPart(12)
out.print(request.getContextPath())
printHtmlPart(13)
out.print(request.getContextPath())
printHtmlPart(14)
})
invokeTag('ifAnyGranted','sec',47,['roles':("ROLE_ADMIN,ROLE_SUPER")],1)
printHtmlPart(15)
out.print(request.getContextPath())
printHtmlPart(16)
invokeTag('loggedInUserInfo','sec',63,['field':("username")],-1)
printHtmlPart(17)
out.print(request.getContextPath())
printHtmlPart(18)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405263325301L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
