import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectindex_admin_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/index_admin.gsp" }
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
expressionOut.print(resource(dir: 'css', file: 'morris.css'))
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
if(true && (flash.message)) {
printHtmlPart(7)
expressionOut.print(flash.message)
printHtmlPart(8)
}
printHtmlPart(9)
createTagBody(2, {->
printHtmlPart(10)
expressionOut.print(status)
printHtmlPart(11)
expressionOut.print(dateCreated)
printHtmlPart(12)
for( month in (months) ) {
printHtmlPart(13)
invokeTag('set','g',43,['var':("mth"),'value':(month.split(':'))],-1)
printHtmlPart(14)
expressionOut.print(mth[1])
printHtmlPart(15)
expressionOut.print(mth[1])
printHtmlPart(16)
expressionOut.print(mth[0])
printHtmlPart(17)
}
printHtmlPart(18)
})
invokeTag('form','g',49,['name':("filterFrm"),'controller':("project"),'action':("index")],2)
printHtmlPart(19)
if(true && (projectInstanceList != null && projectInstanceList.size() > 0)) {
printHtmlPart(20)
for( projectInstance in (projectInstanceList) ) {
printHtmlPart(20)
invokeTag('set','g',72,['var':("proc"),'value':(co.yzf.Process.findByProjectAndStatus(projectInstance,2))],-1)
printHtmlPart(21)
out.print(request.contextPath)
printHtmlPart(22)
expressionOut.print(projectInstance.id)
printHtmlPart(23)
expressionOut.print(projectInstance?.contractNo)
printHtmlPart(24)
expressionOut.print(projectInstance?.customer)
printHtmlPart(25)
expressionOut.print(projectInstance?.address)
printHtmlPart(25)
expressionOut.print(projectInstance?.responser?.uname)
printHtmlPart(26)
if(true && (projectInstance.status==1)) {
printHtmlPart(27)
}
else if(true && (projectInstance.status==3)) {
printHtmlPart(28)
}
else {
printHtmlPart(29)
if(true && (proc == null)) {
printHtmlPart(30)
}
else {
expressionOut.print(proc?.period?.name)
}
printHtmlPart(31)
}
printHtmlPart(32)
if(true && (projectInstance.status==1 || projectInstance.status==3)) {
printHtmlPart(33)
}
else if(true && (proc?.planEndDate > new java.util.Date())) {
printHtmlPart(34)
}
else {
printHtmlPart(35)
}
printHtmlPart(32)
if(true && (projectInstance.status==2)) {
printHtmlPart(36)
expressionOut.print(projectInstance.id)
printHtmlPart(37)
}
else {
printHtmlPart(38)
}
printHtmlPart(39)
expressionOut.print(projectInstance.id)
printHtmlPart(40)
}
printHtmlPart(13)
}
else {
printHtmlPart(41)
}
printHtmlPart(42)
invokeTag('paginate','g',122,['total':(projectInstanceCount ?: 0)],-1)
printHtmlPart(43)
out.print(request.contextPath)
printHtmlPart(44)
out.print(request.contextPath)
printHtmlPart(45)
})
invokeTag('captureBody','sitemesh',172,[:],1)
printHtmlPart(46)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403969767020L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
