import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectuploadpic_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/uploadpic.gsp" }
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
expressionOut.print(resource(dir: 'css', file: 'uploadify.css'))
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(6)
createTagBody(1, {->
printHtmlPart(7)
if(true && (flash.message)) {
printHtmlPart(8)
expressionOut.print(flash.message)
printHtmlPart(9)
}
printHtmlPart(10)
if(true && (procstep.status == 2)) {
printHtmlPart(11)
expressionOut.print(procstepid)
printHtmlPart(12)
expressionOut.print(project.id)
printHtmlPart(13)
expressionOut.print(procstepid)
printHtmlPart(14)
}
printHtmlPart(15)
invokeTag('render','g',34,['template':("/templates/piclist"),'model':(['picList':picList,'editflag':1,title:'图库'])],-1)
printHtmlPart(16)
expressionOut.print(project.id)
printHtmlPart(17)
expressionOut.print(resource(dir: 'img', file: 'uploadify.swf'))
printHtmlPart(18)
out.print(request.contextPath)
printHtmlPart(19)
})
invokeTag('captureBody','sitemesh',93,[:],1)
printHtmlPart(20)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405178593674L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
