import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/edit.gsp" }
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
createTagBody(2, {->
printHtmlPart(9)
createTagBody(3, {->
printHtmlPart(10)
if(true && (error in org.springframework.validation.FieldError)) {
printHtmlPart(11)
expressionOut.print(error.field)
printHtmlPart(12)
}
printHtmlPart(13)
invokeTag('message','g',16,['error':(error)],-1)
printHtmlPart(14)
})
invokeTag('eachError','g',17,['bean':(projectInstance),'var':("error")],3)
printHtmlPart(15)
})
invokeTag('hasErrors','g',19,['bean':(projectInstance)],2)
printHtmlPart(16)
createTagBody(2, {->
printHtmlPart(17)
invokeTag('hiddenField','g',23,['name':("version"),'value':(projectInstance?.version)],-1)
printHtmlPart(18)
invokeTag('render','g',29,['template':("form")],-1)
printHtmlPart(19)
invokeTag('actionSubmit','g',35,['class':("btn btn-primary"),'action':("update"),'value':("更新订单基本信息")],-1)
printHtmlPart(20)
createClosureForHtmlPart(21, 3)
invokeTag('link','g',36,['action':("show"),'resource':(projectInstance),'class':("btn btn-default")],3)
printHtmlPart(22)
})
invokeTag('form','g',42,['url':([resource:projectInstance, action:'update']),'method':("PUT"),'class':("form-horizontal")],2)
printHtmlPart(23)
})
invokeTag('captureBody','sitemesh',46,[:],1)
printHtmlPart(24)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1401028601068L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
