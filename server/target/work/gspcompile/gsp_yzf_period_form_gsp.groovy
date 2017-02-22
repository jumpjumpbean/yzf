import co.yzf.Period
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_period_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/period/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(hasErrors(bean: periodInstance, field: 'orderNo', 'error'))
printHtmlPart(1)
invokeTag('textField','g',7,['name':("orderNo"),'maxlength':("5"),'class':("form-control"),'value':(periodInstance?.orderNo)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: periodInstance, field: 'name', 'error'))
printHtmlPart(3)
invokeTag('textField','g',13,['name':("name"),'maxlength':("32"),'class':("form-control"),'value':(periodInstance?.name)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: periodInstance, field: 'name', 'error'))
printHtmlPart(4)
invokeTag('select','g',19,['name':("hasMaterial"),'from':([0:'无',1:'是']),'optionKey':("key"),'optionValue':("value"),'value':(periodInstance?.hasMaterial),'class':("form-control")],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: periodInstance, field: 'memo', 'error'))
printHtmlPart(5)
invokeTag('textField','g',25,['name':("memo"),'maxlength':("127"),'class':("form-control"),'value':(periodInstance?.memo)],-1)
printHtmlPart(6)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403705806688L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
