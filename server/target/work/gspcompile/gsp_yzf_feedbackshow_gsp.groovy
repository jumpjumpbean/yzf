import co.yzf.Feedback
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_feedbackshow_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/feedback/show.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createTagBody(3, {->
expressionOut.print(feedbackInstance?.title)
})
invokeTag('captureTitle','sitemesh',7,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',7,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
if(true && (flash.message)) {
printHtmlPart(5)
expressionOut.print(flash.message)
printHtmlPart(6)
}
printHtmlPart(7)
expressionOut.print(feedbackInstance?.title)
printHtmlPart(8)
expressionOut.print(feedbackInstance?.user)
printHtmlPart(9)
invokeTag('formatDate','g',21,['date':(feedbackInstance?.dateCreated),'format':("yyyy-MM-dd")],-1)
printHtmlPart(10)
if(true && (curUser?.id == feedbackInstance.user.id)) {
printHtmlPart(11)
createClosureForHtmlPart(12, 3)
invokeTag('link','g',24,['action':("edit"),'class':("btn btn-default btn-sm pull-right"),'resource':(feedbackInstance)],3)
printHtmlPart(13)
createClosureForHtmlPart(14, 3)
invokeTag('link','g',25,['action':("delete"),'class':("btn btn-warning btn-sm pull-right"),'resource':(feedbackInstance),'onclick':("return confirm('确定要删除？');")],3)
printHtmlPart(15)
}
printHtmlPart(16)
expressionOut.print(feedbackInstance?.content)
printHtmlPart(17)
expressionOut.print(feedbackInstance?.id)
printHtmlPart(18)
for( reply in (replyList) ) {
printHtmlPart(19)
expressionOut.print(reply?.content)
printHtmlPart(20)
expressionOut.print(reply?.user)
printHtmlPart(9)
invokeTag('formatDate','g',52,['date':(reply?.dateCreated),'format':("yyyy-MM-dd HH:mm")],-1)
printHtmlPart(21)
}
printHtmlPart(22)
})
invokeTag('captureBody','sitemesh',66,[:],1)
printHtmlPart(23)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402212060158L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
