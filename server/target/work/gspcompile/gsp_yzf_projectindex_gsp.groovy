import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
if(true && (flash.message)) {
printHtmlPart(5)
expressionOut.print(flash.message)
printHtmlPart(6)
}
printHtmlPart(7)
createTagBody(2, {->
printHtmlPart(8)
expressionOut.print(status)
printHtmlPart(9)
expressionOut.print(dateCreated)
printHtmlPart(10)
for( month in (months) ) {
printHtmlPart(11)
invokeTag('set','g',42,['var':("mth"),'value':(month.split(':'))],-1)
printHtmlPart(12)
expressionOut.print(mth[1])
printHtmlPart(13)
expressionOut.print(mth[1])
printHtmlPart(14)
expressionOut.print(mth[0])
printHtmlPart(15)
}
printHtmlPart(16)
})
invokeTag('form','g',48,['name':("filterFrm"),'controller':("project"),'action':("index")],2)
printHtmlPart(17)
for( projectInstance in (projectInstanceList) ) {
printHtmlPart(18)
out.print(request.contextPath)
printHtmlPart(19)
expressionOut.print(projectInstance.id)
printHtmlPart(20)
expressionOut.print(fieldValue(bean: projectInstance, field: "address"))
printHtmlPart(21)
expressionOut.print(fieldValue(bean: projectInstance, field: "customer"))
printHtmlPart(22)
expressionOut.print(fieldValue(bean: projectInstance, field: "contractNo"))
printHtmlPart(23)
invokeTag('formatDate','g',67,['date':(projectInstance.planStartDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(24)
invokeTag('formatDate','g',67,['date':(projectInstance.planEndDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(25)
expressionOut.print(fieldValue(bean: projectInstance.responser, field: "uname"))
printHtmlPart(26)
invokeTag('formatDate','g',71,['date':(projectInstance.realStartDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(24)
invokeTag('formatDate','g',71,['date':(projectInstance.realEndDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(27)
expressionOut.print(fieldValue(bean: projectInstance.responser, field: "telephone"))
printHtmlPart(28)
}
printHtmlPart(29)
invokeTag('paginate','g',80,['total':(projectInstanceCount ?: 0)],-1)
printHtmlPart(30)
})
invokeTag('captureBody','sitemesh',111,[:],1)
printHtmlPart(31)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403953430294L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
