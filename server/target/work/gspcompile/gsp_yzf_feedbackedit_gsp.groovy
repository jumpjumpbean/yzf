import co.yzf.Feedback
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_feedbackedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/feedback/edit.gsp" }
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
invokeTag('eachError','g',17,['bean':(feedbackInstance),'var':("error")],3)
printHtmlPart(14)
})
invokeTag('hasErrors','g',19,['bean':(feedbackInstance)],2)
printHtmlPart(15)
createTagBody(2, {->
printHtmlPart(16)
invokeTag('render','g',28,['template':("form")],-1)
printHtmlPart(17)
createClosureForHtmlPart(18, 3)
invokeTag('link','g',35,['action':("delete"),'class':("btn btn-danger"),'resource':(feedbackInstance),'onclick':("return confirm('确定要删除？');")],3)
printHtmlPart(19)
})
invokeTag('form','g',40,['url':([resource:feedbackInstance, action:'update']),'method':("PUT"),'class':("form-horizontal"),'id':("feedbackFrm")],2)
printHtmlPart(20)
})
invokeTag('captureBody','sitemesh',44,[:],1)
printHtmlPart(21)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402199032245L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
