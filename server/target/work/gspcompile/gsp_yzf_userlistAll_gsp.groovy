import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_userlistAll_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/user/listAll.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
for( user in (allUser) ) {
printHtmlPart(6)
expressionOut.print(resource(dir: 'img', file: 'avatar.png'))
printHtmlPart(7)
expressionOut.print(user.uname)
printHtmlPart(8)
if(true && (user.id == curUser.id)) {
printHtmlPart(9)
createClosureForHtmlPart(10, 4)
invokeTag('link','g',24,['class':("btn btn-warning btn-sm pull-right"),'action':("edit")],4)
printHtmlPart(8)
}
else {
printHtmlPart(9)
createTagBody(4, {->
printHtmlPart(11)
createClosureForHtmlPart(10, 5)
invokeTag('link','g',28,['class':("btn btn-warning btn-sm pull-right"),'action':("edit"),'id':(user.id)],5)
printHtmlPart(9)
})
invokeTag('ifAnyGranted','sec',29,['roles':("ROLE_SUPER,ROLE_ADMIN")],4)
printHtmlPart(8)
}
printHtmlPart(12)
expressionOut.print(user.telephone)
printHtmlPart(13)
expressionOut.print(user.address)
printHtmlPart(14)
expressionOut.print(user.account)
printHtmlPart(15)
expressionOut.print(user.description)
printHtmlPart(16)
for( auth in (user?.authorities) ) {
printHtmlPart(17)
if(true && (auth?.authority == "ROLE_ADMIN")) {
printHtmlPart(18)
}
printHtmlPart(17)
if(true && (auth?.authority == "ROLE_CUSTOMER")) {
printHtmlPart(19)
}
printHtmlPart(17)
if(true && (auth?.authority == "ROLE_REPO")) {
printHtmlPart(20)
}
printHtmlPart(17)
if(true && (auth?.authority == "ROLE_QC")) {
printHtmlPart(21)
}
printHtmlPart(17)
if(true && (auth?.authority == "ROLE_SUPERVISOR")) {
printHtmlPart(22)
}
printHtmlPart(11)
}
printHtmlPart(23)
}
printHtmlPart(24)
invokeTag('paginate','g',71,['controller':("Task"),'action':(actionname),'params':(params),'next':("下一页"),'prev':("上一页"),'total':("1")],-1)
printHtmlPart(25)
})
invokeTag('captureBody','sitemesh',76,[:],1)
printHtmlPart(26)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1404059876946L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
