import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_templates_changeqc_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/templates/_changeqc.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(processId)
printHtmlPart(1)
if(true && (curQc != null)) {
expressionOut.print(curQc.uname)
printHtmlPart(2)
expressionOut.print(curQc.telephone)
printHtmlPart(3)
}
else {
printHtmlPart(4)
}
printHtmlPart(5)
invokeTag('select','g',17,['id':("newqc"),'name':("user.id"),'from':(co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_SUPERVISOR'",[])),'optionKey':("id"),'value':(curQc?.id),'class':("form-control")],-1)
printHtmlPart(6)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1404057813605L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
