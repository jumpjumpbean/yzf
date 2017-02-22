import co.yzf.Period
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_periodedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/period/edit.gsp" }
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
createTagBody(3, {->
printHtmlPart(9)
if(true && (error in org.springframework.validation.FieldError)) {
printHtmlPart(10)
expressionOut.print(error.field)
printHtmlPart(11)
}
printHtmlPart(12)
invokeTag('message','g',16,['error':(error)],-1)
printHtmlPart(13)
})
invokeTag('eachError','g',17,['bean':(periodInstance),'var':("error")],3)
printHtmlPart(14)
})
invokeTag('hasErrors','g',19,['bean':(periodInstance)],2)
printHtmlPart(15)
createTagBody(2, {->
printHtmlPart(16)
invokeTag('hiddenField','g',27,['name':("version"),'value':(periodInstance?.version)],-1)
printHtmlPart(17)
invokeTag('render','g',29,['template':("form")],-1)
printHtmlPart(18)
invokeTag('actionSubmit','g',35,['class':("btn btn-primary"),'action':("update"),'value':("更新工程阶段")],-1)
printHtmlPart(19)
createClosureForHtmlPart(20, 3)
invokeTag('link','g',36,['action':("index"),'class':("btn btn-default")],3)
printHtmlPart(21)
})
invokeTag('form','g',41,['url':([resource:periodInstance, action:'update']),'method':("PUT"),'class':("form-horizontal")],2)
printHtmlPart(22)
})
invokeTag('captureBody','sitemesh',45,[:],1)
printHtmlPart(23)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1401015053331L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
