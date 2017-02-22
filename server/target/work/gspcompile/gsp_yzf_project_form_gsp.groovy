import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_project_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (projectInstance?.id != null)) {
printHtmlPart(1)
expressionOut.print(hasErrors(bean: projectInstance, field: 'user', 'error'))
printHtmlPart(2)
invokeTag('select','g',9,['id':("user"),'name':("user.id"),'from':(co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_CUSTOMER'")),'optionKey':("id"),'value':(projectInstance?.user?.id),'class':("form-control"),'noSelection':(['null': ''])],-1)
printHtmlPart(3)
out.print(request.contextPath)
printHtmlPart(4)
}
printHtmlPart(1)
expressionOut.print(hasErrors(bean: projectInstance, field: 'customer', 'error'))
printHtmlPart(5)
invokeTag('textField','g',20,['name':("customer"),'class':("form-control"),'maxlength':("63"),'value':(projectInstance?.customer)],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'contractNo', 'error'))
printHtmlPart(7)
invokeTag('textField','g',26,['name':("contractNo"),'class':("form-control"),'maxlength':("32"),'value':(projectInstance?.contractNo)],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'address', 'error'))
printHtmlPart(8)
invokeTag('textField','g',32,['name':("address"),'class':("form-control"),'maxlength':("127"),'value':(projectInstance?.address)],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'planStartDate', 'error'))
printHtmlPart(9)
invokeTag('formatDate','g',38,['date':(projectInstance?.planStartDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(10)
expressionOut.print(hasErrors(bean: projectInstance, field: 'planEndDate', 'error'))
printHtmlPart(11)
invokeTag('formatDate','g',44,['date':(projectInstance?.planEndDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(10)
expressionOut.print(hasErrors(bean: projectInstance, field: 'responser', 'error'))
printHtmlPart(12)
invokeTag('select','g',50,['id':("responser"),'name':("responser.id"),'from':(co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_SUPERVISOR'",[])),'optionKey':("id"),'value':(projectInstance?.responser?.id),'class':("form-control"),'noSelection':(['null': ''])],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'qc', 'error'))
printHtmlPart(13)
invokeTag('select','g',56,['id':("qc"),'name':("qc.id"),'from':(co.yzf.User.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_QC'",[])),'optionKey':("id"),'value':(projectInstance?.qc?.id),'class':("form-control"),'noSelection':(['null': ''])],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'totalCost', 'error'))
printHtmlPart(14)
invokeTag('field','g',63,['name':("totalCost"),'type':("number"),'class':("form-control"),'value':(projectInstance.totalCost),'required':("")],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'imprest', 'error'))
printHtmlPart(15)
invokeTag('field','g',70,['name':("imprest"),'type':("number"),'class':("form-control"),'value':(projectInstance.imprest),'required':("")],-1)
printHtmlPart(6)
expressionOut.print(hasErrors(bean: projectInstance, field: 'payment', 'error'))
printHtmlPart(16)
invokeTag('field','g',77,['name':("payment"),'type':("number"),'class':("form-control"),'value':(projectInstance.payment),'required':("")],-1)
printHtmlPart(17)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1404628122516L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
