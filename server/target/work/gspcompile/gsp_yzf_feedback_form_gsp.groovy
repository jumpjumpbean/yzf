import co.yzf.Feedback
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_feedback_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/feedback/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
invokeTag('textField','g',7,['name':("title"),'class':("form-control"),'maxlength':("100"),'value':(feedbackInstance?.title)],-1)
printHtmlPart(1)
invokeTag('textArea','g',14,['id':("fcontent"),'name':("content"),'cols':("40"),'rows':("5"),'class':("form-control"),'maxlength':("1023"),'value':(feedbackInstance?.content)],-1)
printHtmlPart(2)
expressionOut.print(feedbackInstance?.summary)
printHtmlPart(3)
out.print(request.getContextPath())
printHtmlPart(4)
out.print(request.getContextPath())
printHtmlPart(5)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402198107537L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
