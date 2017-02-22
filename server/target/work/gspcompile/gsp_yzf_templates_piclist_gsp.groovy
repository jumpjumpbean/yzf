import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_templates_piclist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/templates/_piclist.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (title != null)) {
printHtmlPart(1)
expressionOut.print(title)
printHtmlPart(2)
}
printHtmlPart(3)
if(true && (picList == null || picList.size() == 0)) {
printHtmlPart(4)
}
else {
printHtmlPart(5)
for( pic in (picList) ) {
printHtmlPart(6)
if(true && (pic != null)) {
printHtmlPart(7)
if(true && (editflag == 1)) {
printHtmlPart(8)
expressionOut.print(pic.id)
printHtmlPart(9)
out.print(request.contextPath)
printHtmlPart(10)
expressionOut.print(pic.picPath)
printHtmlPart(11)
expressionOut.print(pic.picName)
printHtmlPart(12)
}
else {
printHtmlPart(13)
out.print(request.contextPath)
printHtmlPart(10)
expressionOut.print(pic.picPath)
printHtmlPart(11)
expressionOut.print(pic.picName)
printHtmlPart(14)
}
printHtmlPart(6)
}
printHtmlPart(5)
}
printHtmlPart(15)
}
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403878492087L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
