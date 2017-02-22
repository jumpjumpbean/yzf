import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_useredit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/user/edit.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createTagBody(3, {->
printHtmlPart(2)
expressionOut.print(edtuser?.uname)
printHtmlPart(3)
})
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
createTagBody(2, {->
printHtmlPart(6)
expressionOut.print(edtuser?.uname)
printHtmlPart(7)
expressionOut.print(edtuser.id)
printHtmlPart(8)
expressionOut.print(edtuser?.uname)
printHtmlPart(9)
expressionOut.print(edtuser?.account)
printHtmlPart(10)
expressionOut.print(edtuser?.address)
printHtmlPart(11)
expressionOut.print(edtuser?.telephone)
printHtmlPart(12)
expressionOut.print(edtuser?.description)
printHtmlPart(13)
createTagBody(3, {->
printHtmlPart(14)
for( role in (roleList) ) {
printHtmlPart(15)
if(true && (role?.authority != "ROLE_USER")) {
printHtmlPart(16)
if(true && (role?.authority == "ROLE_ADMIN")) {
printHtmlPart(17)
expressionOut.print(role?.id)
printHtmlPart(18)
}
printHtmlPart(19)
if(true && (role?.authority == "ROLE_CUSTOMER")) {
printHtmlPart(20)
expressionOut.print(role?.id)
printHtmlPart(21)
}
printHtmlPart(19)
if(true && (role?.authority == "ROLE_REPO")) {
printHtmlPart(22)
expressionOut.print(role?.id)
printHtmlPart(23)
}
printHtmlPart(19)
if(true && (role?.authority == "ROLE_QC")) {
printHtmlPart(24)
expressionOut.print(role?.id)
printHtmlPart(25)
}
printHtmlPart(19)
if(true && (role?.authority == "ROLE_SUPERVISOR")) {
printHtmlPart(26)
expressionOut.print(role?.id)
printHtmlPart(27)
}
printHtmlPart(28)
}
printHtmlPart(29)
}
printHtmlPart(30)
})
invokeTag('ifAnyGranted','sec',81,['roles':("ROLE_SUPER")],3)
printHtmlPart(31)
createClosureForHtmlPart(32, 3)
invokeTag('link','g',87,['class':("btn btn-link"),'controller':("user"),'action':("listAll")],3)
printHtmlPart(33)
})
invokeTag('form','g',93,['class':("form-horizontal"),'controller':("user"),'action':("save"),'name':("editUserFrm"),'method':("post")],2)
printHtmlPart(34)
expressionOut.print(edtuser?.account)
printHtmlPart(35)
expressionOut.print(edtuser?.account)
printHtmlPart(36)
expressionOut.print(edtuser.curAuth?.id)
printHtmlPart(37)
})
invokeTag('captureBody','sitemesh',131,[:],1)
printHtmlPart(38)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1404058586020L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
