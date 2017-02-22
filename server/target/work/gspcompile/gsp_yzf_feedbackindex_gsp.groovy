import co.yzf.Feedback
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_feedbackindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/feedback/index.gsp" }
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
if(true && (feedbackInstanceList != null && feedbackInstanceList.size() > 0)) {
printHtmlPart(8)
loop:{
int i = 0
for( feedbackInstance in (feedbackInstanceList) ) {
printHtmlPart(9)
createTagBody(4, {->
expressionOut.print(feedbackInstance?.title)
})
invokeTag('link','g',19,['action':("show"),'class':("btn-link"),'resource':(feedbackInstance)],4)
printHtmlPart(10)
expressionOut.print(feedbackInstance?.user)
printHtmlPart(11)
invokeTag('formatDate','g',19,['date':(feedbackInstance?.dateCreated),'format':("yyyy-MM-dd")],-1)
printHtmlPart(12)
expressionOut.print(feedbackInstance?.summary)
printHtmlPart(13)
createClosureForHtmlPart(14, 4)
invokeTag('link','g',25,['action':("show"),'class':("btn btn-default btn-sm"),'resource':(feedbackInstance)],4)
printHtmlPart(15)
if(true && (curUser?.id == feedbackInstance.user.id)) {
printHtmlPart(16)
createClosureForHtmlPart(17, 5)
invokeTag('link','g',27,['action':("edit"),'class':("btn btn-default btn-sm"),'resource':(feedbackInstance)],5)
printHtmlPart(16)
createClosureForHtmlPart(18, 5)
invokeTag('link','g',28,['action':("delete"),'class':("btn btn-warning btn-sm"),'resource':(feedbackInstance),'onclick':("return confirm('确定要删除？');")],5)
printHtmlPart(15)
}
printHtmlPart(19)
expressionOut.print(feedbackInstance?.id)
printHtmlPart(20)
i++
}
}
printHtmlPart(8)
}
else {
printHtmlPart(21)
}
printHtmlPart(22)
invokeTag('paginate','g',42,['total':(feedbackInstanceCount ?: 0)],-1)
printHtmlPart(23)
})
invokeTag('captureBody','sitemesh',65,[:],1)
printHtmlPart(24)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405349701515L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
