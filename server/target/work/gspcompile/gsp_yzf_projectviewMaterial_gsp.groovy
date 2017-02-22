import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectviewMaterial_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/viewMaterial.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
if(true && (pmList != null && pmList.size() > 0)) {
printHtmlPart(9)
for( pm in (pmList) ) {
printHtmlPart(10)
expressionOut.print(pm?.material?.type)
printHtmlPart(11)
expressionOut.print(pm?.material?.brand)
printHtmlPart(11)
expressionOut.print(pm?.material?.modelno?:'-')
printHtmlPart(11)
expressionOut.print(pm?.process?.period?:'-')
printHtmlPart(11)
expressionOut.print(pm?.cnt)
printHtmlPart(12)
expressionOut.print(pm?.material?.unit)
printHtmlPart(13)
expressionOut.print(pm?.memo)
printHtmlPart(14)
}
printHtmlPart(15)
}
else {
printHtmlPart(16)
}
printHtmlPart(17)
out.print(request.contextPath)
printHtmlPart(18)
expressionOut.print(projectId)
printHtmlPart(19)
expressionOut.print(projectId)
printHtmlPart(20)
})
invokeTag('captureBody','sitemesh',60,[:],1)
printHtmlPart(21)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402830771777L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
