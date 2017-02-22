import co.yzf.Material
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_materialindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/material/index.gsp" }
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
loop:{
int i = 0
for( materialInstance in (materialInstanceList) ) {
printHtmlPart(8)
expressionOut.print((i % 2) == 0 ? 'even' : 'odd')
printHtmlPart(9)
expressionOut.print(fieldValue(bean: materialInstance, field: "name"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: materialInstance, field: "type"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: materialInstance, field: "brand"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: materialInstance, field: "modelno"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: materialInstance, field: "unit"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: materialInstance, field: "supplier"))
printHtmlPart(11)
expressionOut.print(createLink(uri: '/'))
printHtmlPart(12)
expressionOut.print(materialInstance.id)
printHtmlPart(13)
expressionOut.print(createLink(uri: '/'))
printHtmlPart(14)
expressionOut.print(materialInstance.id)
printHtmlPart(15)
i++
}
}
printHtmlPart(16)
invokeTag('paginate','g',46,['total':(materialInstanceCount ?: 0)],-1)
printHtmlPart(17)
})
invokeTag('captureBody','sitemesh',51,[:],1)
printHtmlPart(18)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402241315717L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
