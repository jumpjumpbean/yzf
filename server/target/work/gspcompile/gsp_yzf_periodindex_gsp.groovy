import co.yzf.Period
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_periodindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/period/index.gsp" }
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
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',7,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',7,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
loop:{
int i = 0
for( periodInstance in (periodInstanceList) ) {
printHtmlPart(9)
expressionOut.print(fieldValue(bean: periodInstance, field: "orderNo"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: periodInstance, field: "name"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: periodInstance, field: "memo"))
printHtmlPart(10)
if(true && (periodInstance?.hasMaterial == 1)) {
printHtmlPart(11)
}
else {
printHtmlPart(12)
}
printHtmlPart(13)
expressionOut.print(createLink(uri: '/'))
printHtmlPart(14)
expressionOut.print(periodInstance.id)
printHtmlPart(15)
expressionOut.print(createLink(uri: '/'))
printHtmlPart(16)
expressionOut.print(periodInstance.id)
printHtmlPart(17)
i++
}
}
printHtmlPart(18)
invokeTag('paginate','g',43,['total':(periodInstanceCount ?: 0)],-1)
printHtmlPart(19)
})
invokeTag('captureBody','sitemesh',48,[:],1)
printHtmlPart(20)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403707437734L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
